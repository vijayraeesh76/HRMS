package com.hrms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.hrms.model.Employee;

/**
 * Servlet implementation class UpdateAccountController
 */
@WebServlet("/updateAccount.do")
public class UpdateAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateAccountController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		Employee user = null;

		Configuration cfg = new Configuration();
		cfg = cfg.configure();
		SessionFactory sf = cfg.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();

		String hql = "Update User set firstName=:firstName, lastName=:lastName where username=:username";
		Query query = session.createQuery(hql);
		query.setParameter("username", request.getSession().getAttribute("userName"));
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);

		int updateCount = query.executeUpdate();
		
		tr.commit();

		if(updateCount>0) {
			request.getSession().setAttribute("firstName", firstName);
			request.getSession().setAttribute("lastName", lastName);

			response.getWriter().append("Status:Success");
		}else {
			System.out.println("Update failed");
			response.getWriter().append("Status:Fail");
		}
		
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
