package com.hrms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.hrms.model.Employee;
import com.hrms.model.LeaveBean;
import com.hrms.model.LeavesHRView;

/**
 * Servlet implementation class UpdateLeaveController
 */
@WebServlet("/updateLeaveStatusView.do")
public class UpdateLeaveViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateLeaveViewController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
		if(StringUtils.isBlank(empID)){
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
		query.setParameterList("status", new String[] {"PENDING","WAITING"});
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
			}else{
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
			}else{
				eligibleLeaveDays=0;
			}
			
			if(eligibleLeaveDays<0) {
				eligibleLeaveDays=0;
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

		// TODO Auto-generated method stub
		response.sendRedirect("updateLeaveStatusView");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
