package com.hrms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;
import com.hrms.model.Customer;
import com.hrms.model.Employee;
import com.hrms.model.LeaveBean;
import com.hrms.model.ResponseModel;

/**
 * Servlet implementation class LeaveApplicationController
 */
@WebServlet("/leaveApplication.do")
public class LeaveApplicationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LeaveApplicationController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

			Configuration cfg = new Configuration();
			cfg = cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tr = session.beginTransaction();

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
				String hql = "FROM LeaveBean u where u.empID=:empID and leaveDate=:leaveDate";
				Query query = session.createQuery(hql);
				query.setParameter("empID", empID);
				query.setParameter("leaveDate", fDate);
				existingLeaves = query.getResultList();

				if (existingLeaves != null && !existingLeaves.isEmpty()) {
					leavesOverlapping = Boolean.TRUE;
				}
			} else {
				String hql = "FROM LeaveBean u where u.empID=:empID and u.leaveDate BETWEEN :fDate AND :tDate";
				Query query = session.createQuery(hql);
				query.setParameter("empID", empID);
				query.setParameter("fDate", fDate);
				query.setParameter("tDate", tDate);
				existingLeaves = query.getResultList();

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

					session.save(newLeave);
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
						
						session.save(newLeave);
					}
				}

				tr.commit();

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
		response.getWriter().append(jsonString);
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
