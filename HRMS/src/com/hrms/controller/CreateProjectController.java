package com.hrms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;
import com.hrms.model.Employee;
import com.hrms.model.Project;
import com.hrms.model.ResponseModel;

/**
 * Servlet implementation class CreateProjectController
 */
@WebServlet("/createProject.do")
public class CreateProjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateProjectController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ResponseModel resModel = new ResponseModel();
		String resStatus = null;
		String resMess = null;
		try {

			String projectName = request.getParameter("projectName");
			String projectManager = request.getParameter("projectManagers");
			String techLeadersString = request.getParameter("techLeaders");
			String teamLeadersString = request.getParameter("teamLeaders");
			String softwareEngineersString = request.getParameter("softwareEngineers");
			String traineesString = request.getParameter("trainees");

			if (StringUtils.isBlank(projectName) || StringUtils.isBlank(projectManager)
					|| StringUtils.isBlank(techLeadersString) || StringUtils.isBlank(teamLeadersString)
					|| StringUtils.isBlank(softwareEngineersString) || StringUtils.isBlank(traineesString)) {
				throw new RuntimeException("Required parameters not present");
			}

			// Hibernate Configurations
			Configuration cfg = new Configuration();
			cfg = cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tr = session.beginTransaction();

			String[] techLeaders = techLeadersString.split(",");
			String[] teamLeaders = teamLeadersString.split(",");
			String[] softwareEngineers = softwareEngineersString.split(",");
			String[] trainees = traineesString.split(",");

			List<Employee> employees = null;
			Set<Employee> employeeSet = null;

			// Populate all employee ID
			List<String> employeeIds = new ArrayList<String>();
			employeeIds.addAll(Arrays.asList(techLeaders));
			employeeIds.addAll(Arrays.asList(teamLeaders));
			employeeIds.addAll(Arrays.asList(softwareEngineers));
			employeeIds.addAll(Arrays.asList(trainees));
			employeeIds.add(projectManager);

			// Get All employees
			String eHql = "FROM Employee u where u.empID in :empIDs";
			Query eQuery = session.createQuery(eHql);
			eQuery.setParameter("empIDs", employeeIds);
			employees = (List<Employee>) eQuery.getResultList();

			if (employees == null || employees.isEmpty()) {
				throw new RuntimeException("No employee record found");
			}

			employeeSet = new HashSet<Employee>();
			employeeSet.addAll(employees);

			// Create new project
			Project project = new Project();

			project.setEmployees(employeeSet);
			project.setProjectName(projectName);

			session.save(project);

			for (Employee employee : employees) {
				employee.setProject(project);
				session.save(employee);
			}

			tr.commit();
			
			resStatus = "Success";
		} catch (Exception e) {
			// Must create error page
			resStatus = "Error";
			resMess=ExceptionUtils.getRootCauseMessage(e);
		}

		// Prepare response
		resModel.setStatus(resStatus);
		resModel.setMessage(resMess);
		String jsonString = new Gson().toJson(resModel);
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
