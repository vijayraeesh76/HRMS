package com.hrms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;
import com.hrms.model.Employee;
import com.hrms.model.ResponseModel;
import com.hrms.utilities.HashPassword;

/**
 * Servlet implementation class AdminRegister
 */
@WebServlet("/adminRegister.do")
public class AdminRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminRegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ResponseModel responseStatus = new ResponseModel();
		String status = null;
		String responseMessage = null;
		
		try {
			String firstName = request.getParameter("firstname");
			String lastName = request.getParameter("lastname");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String designation = request.getParameter("designation");
			String doj = request.getParameter("doj");
			int employeeCount = 0;
			String passwordHash = null;
			String saltHash = null;
			
			Configuration cfg = new Configuration();
			cfg = cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tr = session.beginTransaction();
			
			// Check no of employees in db for ID generation
			String countHql = "SELECT COUNT(*) FROM Employee";
			Query query = session.createQuery(countHql);
			employeeCount = (int) (long) query.getSingleResult();	
			
			if(employeeCount==0){
				throw new RuntimeException("Employee count can't be 0");
			}
			
			employeeCount++;
			
			// Hash the Password
			Map<String, String> hashes = HashPassword.getPasswordHashAndSaltHash(password);
			if(hashes == null || hashes.isEmpty()) {
				throw new RuntimeException("Error while Hashing password");
			}
			passwordHash = hashes.get("PasswordHash");
			saltHash = hashes.get("SaltHash");
			
			// populate employee details
			Employee user = new Employee();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setUserName(username);
			user.setPassword(passwordHash);
			user.setDesignation(designation);
			user.setDoj(LocalDate.parse(doj));
			user.setEmpID("DC_"+employeeCount);
			user.setSalt(saltHash);
			
			session.save(user);
			tr.commit();
			
			status = "Success";
		}catch(Exception e) {
			status="Error";
			responseMessage=ExceptionUtils.getRootCauseMessage(e);
		}
		
		responseStatus.setStatus(status);
		responseStatus.setMessage(responseMessage);
		String jsonString = new Gson().toJson(responseStatus);
		response.getWriter().append(jsonString);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
