package com.hrms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.hrms.model.Employee;
import com.hrms.model.Superior;

/**
 * Servlet implementation class ApplyLeaveController
 */
@WebServlet("/applyForLeave.do")
public class ApplyLeaveController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ApplyLeaveController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		
		response.sendRedirect("applyForLeave");
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
