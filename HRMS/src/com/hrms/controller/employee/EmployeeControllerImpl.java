package com.hrms.controller.employee;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hrms.manager.employee.EmployeeManager;
import com.hrms.model.Employee;
import com.hrms.model.ResponseModel;
import com.hrms.utilities.HashPassword;

@RequestMapping("/employee")
public class EmployeeControllerImpl implements EmployeeController{
	
	private EmployeeManager employeeManager;
	
	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}

	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	@Override
	@RequestMapping("/adminRegister.do")
	public ModelAndView adminRegister(Employee employee) throws IOException{

		ModelAndView model = new ModelAndView();
		
		// AJAX
		/*ResponseModel responseStatus = new ResponseModel();
		String status = null;
		String responseMessage = null;*/
		
		try {
			String firstName = employee.getFirstName();
			String lastName = employee.getLastName();
			String userName = employee.getUserName();
			String password = employee.getPassword();
			String designation = employee.getDesignation();
			String doj = employee.getDojString();
			
			if(StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName) 
					|| StringUtils.isBlank(userName) || StringUtils.isBlank(password) 
					|| StringUtils.isBlank(designation) || null==doj) {
				String errMsg = "Required request param not found";
				
				System.out.println(errMsg);
				throw new RuntimeException(errMsg);
			}
			
			
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
			user.setUserName(userName);
			user.setPassword(passwordHash);
			user.setDesignation(designation);
			user.setDoj(LocalDate.parse(doj));
			user.setEmpID("DC_"+employeeCount);
			user.setSalt(saltHash);
			
			// save employee
			employeeManager.saveEmployee(user);
			
			//session.save(user);
			//tr.commit();
			
			//status = "Success";
		}catch(Exception e) {
			//status="Error";
			//responseMessage=ExceptionUtils.getRootCauseMessage(e);
			
			System.out.println("Exception Root Cause : " +ExceptionUtils.getRootCauseMessage(e));
			
			throw e;
		}
		
		// AJAX
		/*responseStatus.setStatus(status);
		responseStatus.setMessage(responseMessage);
		String jsonString = new Gson().toJson(responseStatus);*/
		
		model.setViewName("home");
		
		return model;
	}

	@Override
	@RequestMapping("/adminRegisterView.do")
	public ModelAndView adminRegisterView(@ModelAttribute("employee") Employee employee) throws IOException {
		ModelAndView model = new ModelAndView("adminRegister");
		return model;
	}
	
	@RequestMapping("/assignHierarchyFlow.do")
	@Override
	public ResponseEntity getDesignationsAndEmployees(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ResponseModel resModel = new ResponseModel();
		String resStatus = null;
		String resMess = null;

		try {
			String projectId = request.getParameter("projectId");
			String designation = request.getParameter("designation");

			if (StringUtils.isBlank(projectId)) {
				throw new RuntimeException("No projectId selected");
			}
			
			List<String> designations = null;
			List<Employee> designatedEmployees = null;

			// Hibernate Configurations
			Configuration cfg = new Configuration();
			cfg = cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tr = session.beginTransaction();

			// Fetch designations if designation is blank
			if (StringUtils.isBlank(designation)) {
				
				
				// Get Employees in Project
				
				
				/*String hql = "SELECT DISTINCT e.designation FROM Employee e where e.project.id=:projectId";
				Query query = session.createQuery(hql);
				query.setParameter("projectId", Integer.parseInt(projectId));*/
				designations = employeeManager.getDistinctDesignationInProject(projectId);
				
				resModel.setModelList(designations);
			}
			// Fetch relevant employees and subordinates if designation is not blank
			else {
				
				String deHql = "FROM Employee e where e.project.id=:projectId and e.designation=:designation";
				Query deQuery = session.createQuery(deHql);
				deQuery.setParameter("projectId", Integer.parseInt(projectId));
				deQuery.setParameter("designation", designation);
				designatedEmployees = (List<Employee>) deQuery.getResultList();
				
				if(designatedEmployees == null || designatedEmployees.isEmpty()){
					throw new RuntimeException("No employees in selected designation");
				}
				
				// To avoid cyclic reference in GSON
				for(Employee e : designatedEmployees){
					e.setProject(null);
					e.setSuperiors(null);
					e.setSubordinates(null);
					/*session.buildLockRequest(LockOptions.NONE).lock(e);*/
				}
				
				resModel.setModelList(designatedEmployees);
			}

			resStatus = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			resStatus = "Error";
			resMess = ExceptionUtils.getRootCauseMessage(e);
		}

		// Prepare response
		resModel.setStatus(resStatus);
		resModel.setMessage(resMess);
		String jsonString = new Gson().toJson(resModel);
		
		
		return ResponseEntity.ok(jsonString);
	}
}
