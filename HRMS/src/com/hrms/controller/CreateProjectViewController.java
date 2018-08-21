package com.hrms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;
import com.hrms.model.Attendance;
import com.hrms.model.CreateProjectModel;
import com.hrms.model.Employee;
import com.hrms.model.ResponseModel;

/**
 * Servlet implementation class CreateProjectController
 */
@WebServlet("/createProjectHRView.do")
public class CreateProjectViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateProjectViewController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			HttpSession httpSession = request.getSession();
			
			List<Employee> projectManagers = null;
			List<Employee> techLeaders = null;
			List<Employee> teamLeaders = null;
			List<Employee> softwareEngineers = null;
			List<Employee> trainees = null;

			// Hibernate Configurations
			Configuration cfg = new Configuration();
			cfg = cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tr = session.beginTransaction();

			// Get Project Managers
			/*String pmHql = "SELECT * FROM Employee where designation='PROJECT MANAGER' and project_Id is NULL";
			Query pmQuery = session.createNativeQuery(pmHql);
			projectManagers = (List<Employee>) pmQuery.getResultList();*/
			
			// Get Project Managers
			String pmHql = "FROM Employee where designation=:designation and project is NULL";
			Query pmQuery = session.createQuery(pmHql);
			pmQuery.setParameter("designation", "PROJECT_MANAGER");
			projectManagers = pmQuery.getResultList();

			// Get Tech Leaders
			String thHql = "FROM Employee where designation=:designation and project is NULL";
			Query thQuery = session.createQuery(thHql);
			thQuery.setParameter("designation", "TECH_LEADER");
			techLeaders = thQuery.getResultList();
			
			
			/*String thHql = "SELECT * FROM Employee where designation='TECH LEADER' and project_Id is NULL";
			Query thQuery = session.createNativeQuery(thHql);
			techLeaders = (List<Employee>) thQuery.getResultList();*/

			// Get Team Leaders
			String tmHql = "FROM Employee where designation=:designation and project is NULL";
			Query tmQuery = session.createQuery(tmHql);
			tmQuery.setParameter("designation", "TEAM_LEADER");
			teamLeaders = tmQuery.getResultList();
			
			/*String tmHql = "SELECT * FROM Employee where designation='TEAM LEADER' and project_Id is NULL";
			Query tmQuery = session.createNativeQuery(tmHql);
			teamLeaders = (List<Employee>) tmQuery.getResultList();*/

			// Get Software Engineers
			String seHql = "FROM Employee where designation=:designation and project is NULL";
			Query seQuery = session.createQuery(seHql);
			seQuery.setParameter("designation", "SOFTWARE_ENGINEER");
			softwareEngineers = seQuery.getResultList();
			
			/*String seHql = "SELECT * FROM Employee where designation='SOFTWARE ENGINEER' and project_Id is NULL";
			Query seQuery = session.createNativeQuery(seHql);
			softwareEngineers = (List<Employee>) seQuery.getResultList();*/

			// Get Trainees
			String trHql = "FROM Employee where designation=:designation and project is NULL";
			Query trQuery = session.createQuery(trHql);
			trQuery.setParameter("designation", "TRAINEE");
			trainees = trQuery.getResultList();
			
			
			/*String trHql = "SELECT * FROM Employee where designation='TRAINEE' and project_Id is NULL";
			Query trQuery = session.createNativeQuery(trHql);
			trainees = (List<Employee>) trQuery.getResultList();*/
			
			httpSession.setAttribute("projectManagers",projectManagers);
			httpSession.setAttribute("softwareEngineers",softwareEngineers);
			httpSession.setAttribute("teamLeaders",teamLeaders);
			httpSession.setAttribute("techLeaders",techLeaders);
			httpSession.setAttribute("trainees",trainees);
		} catch (Exception e) {
			
			// Must create error page
			throw new RuntimeException(ExceptionUtils.getRootCauseMessage(e));
		}
		
		response.sendRedirect("createProjectHRView");
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
