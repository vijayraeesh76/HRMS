package com.hrms.controller.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hrms.model.Employee;
import com.hrms.model.Project;
import com.hrms.model.ResponseModel;

@Controller
public class ProjectControllerImpl implements ProjectController{

	@RequestMapping("/createProjectHRView.do")
	@Override
	public ModelAndView createProjectView(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView();
		
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
		
		model.setViewName("createProjectHRView");
		
		return model;
	}

	@RequestMapping("/createProject.do")
	@Override
	public ResponseEntity createProject(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
		
		
		return ResponseEntity.ok(jsonString);
	}

}
