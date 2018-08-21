package com.hrms.controller;

import java.io.IOException;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.hrms.model.Customer;
import com.hrms.model.Employee;
import com.hrms.utilities.HashPassword;

/**
 * Servlet implementation class SignInController
 */
@WebServlet("/signIn.do")
public class SignInController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignInController() {
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
		Customer user = null;
		Boolean passwordCorrect = Boolean.FALSE;

		System.out.println("username : " + userName + " password : " + password);

		Configuration cfg = new Configuration();
		cfg = cfg.configure();
		SessionFactory sf = cfg.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();

		// Get Customer by username
		String hql = "FROM Customer u where u.userName=:userName";
		Query query = session.createQuery(hql);
		query.setParameter("userName", userName);
		user = (Customer) query.getSingleResult();

		if (user == null) {
			throw new RuntimeException("No such user in database");
		}

		// Validate Password
		passwordCorrect = HashPassword.validatePassword(user.getPassword(), user.getSalt(), password);

		if (!passwordCorrect) {
			throw new RuntimeException("Password is incorrect");
		}

		request.getSession().setAttribute("firstName", user.getFirstName());
		request.getSession().setAttribute("lastName", user.getLastName());
		request.getSession().setAttribute("userName", user.getUserName());

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
