package com.hrms.controller.leave;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hrms.manager.leave.LeaveManager;
import com.hrms.model.Employee;
import com.hrms.model.LeaveBean;
import com.hrms.model.LeaveStatusEmployee;
import com.hrms.model.LeavesHRView;
import com.hrms.model.ResponseModel;
import com.hrms.model.Superior;

@RequestMapping("/leave")
public class LeaveControllerImpl implements LeaveController {

	private LeaveManager leaveManager;

	public LeaveManager getLeaveManager() {
		return leaveManager;
	}

	public void setLeaveManager(LeaveManager leaveManager) {
		this.leaveManager = leaveManager;
	}

	@RequestMapping("/leaveApplication.do")
	@Override
	public ResponseEntity applyLeave(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ResponseModel resStatus = new ResponseModel();
		String resStatusStr = null;
		String resMess = null;

		try {
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			String supervisor = request.getParameter("supervisor");
			String reason = request.getParameter("reason");

			// Request param validation
			if (StringUtils.isBlank(fromDate) || StringUtils.isBlank(toDate) || StringUtils.isBlank(supervisor)
					|| StringUtils.isBlank(reason)) {
				throw new RuntimeException("Required Parameters Not Present");
			}

			LocalDate fDate = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			LocalDate tDate = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

			String userName = (String) request.getSession().getAttribute("userName");
			String empID = (String) request.getSession().getAttribute("empID");

			String status = "PENDING";

			List<LeaveBean> existingLeaves = null;

			// Check whether from date and to date are same
			Period period = fDate.until(tDate);
			Long days = period.get(ChronoUnit.DAYS);
			Boolean leaveDaysSame = Boolean.FALSE;
			Boolean leavesOverlapping = Boolean.FALSE;
			if (days == 0) {
				leaveDaysSame = Boolean.TRUE;
			}

			// Check whether leaves are overlapping
			if (leaveDaysSame) {
				
				existingLeaves = leaveManager.getLeavesByEmpIDAndLeaveDate(empID, fDate);

				if (existingLeaves != null && !existingLeaves.isEmpty()) {
					leavesOverlapping = Boolean.TRUE;
				}
			} else {

				existingLeaves = leaveManager.getLeavesByEmpIDAndLeaveDateRange(empID, fDate, tDate);

				if (existingLeaves != null && !existingLeaves.isEmpty()) {
					leavesOverlapping = Boolean.TRUE;
				}
			}

			// Generate Leaves and insert in DB
			if (!leavesOverlapping) {
				if (leaveDaysSame) {

					LeaveBean newLeave = new LeaveBean();
					newLeave.setEmpID(empID);
					newLeave.setLeaveDate(fDate);
					newLeave.setLeaveStatus(status);
					newLeave.setUserName(userName);
					newLeave.setReason(reason);
					newLeave.setSuperiorEmpID(supervisor);

					leaveManager.saveLeave(newLeave);
				} else {
					long leaveLength = ChronoUnit.DAYS.between(fDate, tDate);
					for (int i = 0; i <= leaveLength; i++) {
						LocalDate leaveDate = fDate.plusDays(i);

						LeaveBean newLeave = new LeaveBean();
						newLeave.setEmpID(empID);
						newLeave.setLeaveDate(leaveDate);
						newLeave.setLeaveStatus(status);
						newLeave.setUserName(userName);
						newLeave.setReason(reason);
						newLeave.setSuperiorEmpID(supervisor);

						leaveManager.saveLeave(newLeave);
					}
				}

				resStatusStr = "Success";

			} else {
				// error

				resStatusStr = "Error";
				resMess = "Leave days overlapping";
			}

		} catch (Exception e) {
			resStatusStr = "Error";
			resMess = ExceptionUtils.getRootCauseMessage(e);
		}

		// Prepare response
		resStatus.setStatus(resStatusStr);
		resStatus.setMessage(resMess);
		String jsonString = new Gson().toJson(resStatus);

		return ResponseEntity.ok(jsonString);
	}

	@RequestMapping("/applyForLeave.do")
	@Override
	public ModelAndView leaveApplicationView(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		ModelAndView model = new ModelAndView();

		String empID = (String) request.getSession().getAttribute("empID");

		if (StringUtils.isBlank(empID)) {
			response.sendRedirect("employeeSignIn");
		}

		Employee emp = null;

		// Hibernate Configuration
		Configuration cfg = new Configuration();
		cfg = cfg.configure();
		SessionFactory sf = cfg.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();

		// Get employee
		String eHql = "FROM Employee e where e.empID=:empID";
		Query eQuery = session.createQuery(eHql);
		eQuery.setParameter("empID", empID);
		emp = (Employee) eQuery.getSingleResult();

		Set<Superior> superiors = emp.getSuperiors();

		if (superiors == null || superiors.isEmpty()) {
			throw new RuntimeException("No superiors found for employee");
		}

		List<String> superiorEmpIDs = new ArrayList<String>();
		List<Employee> employeeSuperiors = null;
		for (Superior sup : superiors) {
			superiorEmpIDs.add(sup.getSuperiorID());
		}

		// Get superiors
		String sHql = "FROM Employee e where e.empID in (:empIDs)";
		Query sQuery = session.createQuery(sHql);
		sQuery.setParameter("empIDs", superiorEmpIDs);
		employeeSuperiors = (List<Employee>) sQuery.getResultList();

		request.getSession().setAttribute("employeeSuperiors", employeeSuperiors);

		model.setViewName("applyForLeave");

		return model;
	}

	@RequestMapping("/employeeLeaveStatus.do")
	@Override
	public ModelAndView employeeLeaveStatusView(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		ModelAndView model = new ModelAndView();

		String userName = (String) request.getSession().getAttribute("userName");

		Configuration cfg = new Configuration();
		cfg = cfg.configure();
		SessionFactory sf = cfg.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();

		String hql = "FROM LeaveBean l where l.userName=:userName";
		Query query = session.createQuery(hql);
		query.setParameter("userName", userName);
		List<LeaveBean> leaveBeans = query.getResultList();

		if (leaveBeans == null || leaveBeans.isEmpty()) {
			throw new RuntimeException("No Leaves to view");
		}

		List<LeaveStatusEmployee> leaves = new ArrayList<LeaveStatusEmployee>();
		for (LeaveBean leave : leaveBeans) {
			String ehql = "FROM Employee l where l.empID=:empID";
			Query eQuery = session.createQuery(ehql);
			eQuery.setParameter("empID", leave.getSuperiorEmpID());
			Employee supervisor = (Employee) eQuery.getSingleResult();

			if (supervisor == null) {
				throw new RuntimeException("Supervisor not present");
			}

			LeaveStatusEmployee leaveStatus = new LeaveStatusEmployee();
			leaveStatus.setLeaveDate(leave.getLeaveDate());
			leaveStatus.setLeaveStatus(leave.getLeaveStatus());
			leaveStatus.setSupervisor(supervisor.getFirstName());
			leaveStatus.setSupervisorComment(leave.getSupervisorComment());

			leaves.add(leaveStatus);
		}

		request.getSession().setAttribute("leaves", leaves);

		model.setViewName("leaveStatus");

		return model;
	}

	@RequestMapping("/updateLeaveStatusView.do")
	@Override
	public ModelAndView updateLeaveStatusView(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ModelAndView model = new ModelAndView();

		HttpSession session = request.getSession();
		String userName = null;
		List<LeaveBean> pendingLeaves = null;
		List<LeaveBean> approvedLeavesList = null;
		Employee user = null;
		LocalDate doj = null;
		LocalDate leaveDate = null;
		LocalDate currentDate = null;
		LocalDate firstDateOfMonth = null;
		LocalDate lastDateOfMonth = null;
		Period interval = null;
		int monthsFromJoining = 0;
		Boolean leaveEligibility = Boolean.FALSE;
		int approvedLeaves = 0;
		int eligibleLeaveDays = 0;
		int eligibleLeavesPerMonth = 1;
		List<LeavesHRView> leavesForHR = null;
		String empID = (String) request.getSession().getAttribute("empID");

		// redirect to login if session does not have empID
		if (StringUtils.isBlank(empID)) {
			response.sendRedirect("employeeSignIn");
		}

		// Hibernate Configurations
		Configuration cfg = new Configuration();
		cfg = cfg.configure();
		SessionFactory sf = cfg.buildSessionFactory();
		Session hibSession = sf.openSession();
		Transaction tr = hibSession.beginTransaction();

		// Get pending leaves
		String hql = "FROM LeaveBean l where l.leaveStatus in (:status) and l.superiorEmpID=:supEmpID order by date(leaveDate) asc";
		Query query = hibSession.createQuery(hql);
		query.setParameterList("status", new String[] { "PENDING", "WAITING" });
		query.setParameter("supEmpID", empID);
		pendingLeaves = (List<LeaveBean>) query.getResultList();

		if (pendingLeaves == null) {
			throw new NullPointerException("No Pending Leaves");
		}

		leavesForHR = new ArrayList<LeavesHRView>();

		// Populate Pending leaves for HR view(LeavesHRView.Class) for each
		// pending leave
		for (LeaveBean pendingLeave : pendingLeaves) {
			userName = pendingLeave.getUserName();
			// Get date of joining
			String hql1 = "FROM Employee u where u.userName=:userName";
			Query query1 = hibSession.createQuery(hql1);
			query1.setParameter("userName", userName);
			user = (Employee) query1.getSingleResult();

			if (user == null) {
				throw new NullPointerException("Unable to fetch user");
			}

			// Check eligibility for leave
			doj = user.getDoj();
			leaveDate = pendingLeaves.get(0).getLeaveDate();

			if (null == doj || null == leaveDate) {
				throw new NullPointerException("Can't find leave eligibility");
			}

			interval = Period.between(doj, leaveDate);
			monthsFromJoining = interval.getMonths();

			if (monthsFromJoining >= 3) {
				leaveEligibility = Boolean.TRUE;
			} else {
				leaveEligibility = Boolean.FALSE;
			}

			// Find the no of leaves the employee is eligible to take
			currentDate = LocalDate.now();
			firstDateOfMonth = currentDate.withDayOfMonth(1);
			lastDateOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
			if (leaveEligibility) {
				String hql2 = "FROM LeaveBean l where l.userName=:userName and l.leaveStatus=:status and l.leaveDate BETWEEN '"
						+ firstDateOfMonth.toString() + "' AND '" + lastDateOfMonth.toString() + "'";
				Query query2 = hibSession.createQuery(hql2);
				query2.setParameter("userName", userName);
				query2.setParameter("status", "APPROVED");
				approvedLeavesList = (List<LeaveBean>) query2.getResultList();

				if (approvedLeavesList == null || approvedLeavesList.isEmpty()) {
					approvedLeaves = 0;
				} else {
					approvedLeaves = approvedLeavesList.size();
				}

				eligibleLeaveDays = eligibleLeavesPerMonth - approvedLeaves;
			} else {
				eligibleLeaveDays = 0;
			}

			if (eligibleLeaveDays < 0) {
				eligibleLeaveDays = 0;
			}

			// populate LeavesHRView model for HR convenient view
			LeavesHRView leaveHR = new LeavesHRView();
			leaveHR.setEmpID(user.getEmpID());
			leaveHR.setFirstName(user.getFirstName());
			leaveHR.setLeaveDate(pendingLeave.getLeaveDate().toString());
			leaveHR.setEligibleLeaveDays(eligibleLeaveDays);
			leaveHR.setStatus(pendingLeave.getLeaveStatus());
			leaveHR.setReason(pendingLeave.getReason());
			leaveHR.setComment(pendingLeave.getSupervisorComment());

			leavesForHR.add(leaveHR);
		}

		request.getSession().setAttribute("leaves", leavesForHR);

		model.setViewName("updateLeaveStatusView");

		return model;
	}

	@RequestMapping("/updateLeaveStatus.do")
	@Override
	public ResponseEntity updateLeaveStatus(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ResponseModel resModel = new ResponseModel();
		String resStatus = null;
		String resMess = null;
		try {
			String firstName = request.getParameter("firstName");
			String empID = request.getParameter("empID");
			String leaveDateString = request.getParameter("leaveDate");
			String leaveStatus = request.getParameter("leaveStatus");
			String eligibleLeaveDays = request.getParameter("eligibleLeaveDays");
			String comment = request.getParameter("comment");
			Employee employee = null;
			LeaveBean leave = null;

			Configuration cfg = new Configuration();
			cfg = cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tr = session.beginTransaction();

			// Get leave to update
			String hql = "FROM LeaveBean u where u.empID=:empID and leaveDate=:leaveDate";
			Query query = session.createQuery(hql);
			query.setParameter("empID", empID);
			query.setParameter("leaveDate", LocalDate.parse(leaveDateString));
			leave = (LeaveBean) query.getSingleResult();

			if (leave == null) {
				throw new NullPointerException("Unable to fetch leave to update.");
			}

			// update leave
			leave.setLeaveStatus(leaveStatus);
			leave.setSupervisorComment(comment);

			// save updated leave in DB
			session.save(leave);
			tr.commit();

			resStatus = "Success";
			resMess = "Leave successfully Updated!";
		} catch (Exception e) {
			resStatus = "Error";
			resMess = ExceptionUtils.getRootCauseMessage(e);
		}

		resModel.setStatus(resStatus);
		resModel.setMessage(resMess);

		String jsonString = new Gson().toJson(resModel);

		// TODO Auto-generated method stub
		return ResponseEntity.ok(jsonString);
	}

}
