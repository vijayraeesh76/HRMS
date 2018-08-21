package com.hrms.controller;

import java.io.IOException;
import java.util.Map;

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

import com.hrms.model.Customer;
import com.hrms.utilities.HashPassword;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/register.do")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String passwordHash = null;
		String saltHash = null;

		if (StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName) || StringUtils.isBlank(userName)
				|| StringUtils.isBlank(password)) {
			throw new RuntimeException("Required parameters not found");
		}

		// Hash the Password
		Map<String, String> hashes = HashPassword.getPasswordHashAndSaltHash(password);
		if (hashes == null || hashes.isEmpty()) {
			throw new RuntimeException("Error while Hashing password");
		}
		passwordHash = hashes.get("PasswordHash");
		saltHash = hashes.get("SaltHash");

		Configuration cfg = new Configuration();
		cfg = cfg.configure();
		SessionFactory sf = cfg.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();

		Customer user = new Customer();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUserName(userName);
		user.setPassword(passwordHash);
		user.setSalt(saltHash);

		session.save(user);
		tr.commit();

		request.getSession().setAttribute("firstName", firstName);
		request.getSession().setAttribute("lastName", user.getLastName());
		request.getSession().setAttribute("userName", user.getUserName());

		// TODO Auto-generated method stub
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
