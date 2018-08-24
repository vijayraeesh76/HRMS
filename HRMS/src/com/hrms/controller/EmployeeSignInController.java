package com.hrms.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.hrms.model.Employee;
import com.hrms.utilities.Constants;
import com.hrms.utilities.HashPassword;

/**
 * Servlet implementation class EmployeeSignIn
 */
@WebServlet("/employeeSignIn.do")
public class EmployeeSignInController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeSignInController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		Employee user = null;
		Boolean passwordCorrect = Boolean.FALSE;

		// Request param validation
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			throw new RuntimeException("Required request parameters not present");
		}

		Configuration cfg = new Configuration();
		cfg = cfg.configure();
		SessionFactory sf = cfg.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();

		// Get user from db
		String hql = "FROM Employee u where u.userName=:userName";
		Query query = session.createQuery(hql);
		query.setParameter("userName", userName);
		user = (Employee) query.getSingleResult();

		// check whether user is in db
		if (user == null) {
			throw new RuntimeException("User not in database");
		}

		// Validate Password
		passwordCorrect = HashPassword.validatePassword(user.getPassword(), user.getSalt(), password);

		if (!passwordCorrect) {
			throw new RuntimeException("Password is incorrect");
		}

		String designation = user.getDesignation();

		// Check whether Cookies are enabled
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			// cookies enabled
		}else {
			throw new RuntimeException("This site uses Cookies. Please enable cookies");
		}

		request.getSession().setAttribute("firstName", user.getFirstName());
		request.getSession().setAttribute("lastName", user.getLastName());
		request.getSession().setAttribute("userName", user.getUserName());
		request.getSession().setAttribute("empID", user.getEmpID());

		// Set PunchRecords in session
		/*
		 * String hql1 =
		 * "FROM PunchRecord u where u.userName=:userName and u.date=:date"; Query
		 * query1 = session.createQuery(hql1); query1.setParameter("userName",
		 * userName); query1.setParameter("date", LocalDate.now()); List<PunchRecord>
		 * punchRecords = (List<PunchRecord>) query1.getResultList();
		 * 
		 * if (punchRecords != null) { if (punchRecords.isEmpty()) {
		 * request.getSession().setAttribute("punchRecordPresent", "FALSE"); } else if
		 * (punchRecords.size() == 1) { request.getSession().setAttribute("punchRecord",
		 * punchRecords.get(0)); request.getSession().setAttribute("punchRecordPresent",
		 * "TRUE"); } else if (punchRecords.size() > 1) { throw new
		 * RuntimeException("More than one PunchRecord found for user for a given day"
		 * ); } } else { request.getSession().setAttribute("punchRecordPresent",
		 * "FALSE"); }
		 */

		if (Arrays.asList(Constants.leaveApprovers).contains(designation)) {
			request.getSession().setAttribute("isLeaveApprover", "TRUE");
		}
		if (Arrays.asList(Constants.leaveAppliers).contains(designation)) {
			request.getSession().setAttribute("isLeaveApplier", "TRUE");
		}
		if (Arrays.asList(Constants.leaveApprovers).contains(designation)
				&& Arrays.asList(Constants.leaveAppliers).contains(designation)) {
			request.getSession().setAttribute("isLeaveApplierAndApprover", "TRUE");
		}
		if (designation.equals("HR")) {
			request.getSession().setAttribute("isHR", "TRUE");
		}
		if (designation.equals("SYSTEM_ADMIN")) {
			request.getSession().setAttribute("isSysAdmin", "TRUE");
		}

		response.getWriter().append("Status:Success");
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
