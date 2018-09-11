package com.hrms.controller.employeehierarchy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hrms.model.Employee;
import com.hrms.model.FirmDesignations;
import com.hrms.model.Project;
import com.hrms.model.ResponseModel;
import com.hrms.model.Subordinate;
import com.hrms.model.Superior;

@Controller
public class EmployeeHierarchyImpl implements EmployeeHierarchy{

	@RequestMapping("assignHierarchyView.do")
	@Override
	public ModelAndView assignHierarchyView(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ModelAndView model = new ModelAndView();
		try {
			List<Project> projects = null;
			
			// Hibernate Configurations
			Configuration cfg = new Configuration();
			cfg = cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tr = session.beginTransaction();
			
			// Get all Projects
			String hql = "FROM Project";
			Query query = session.createQuery(hql);
			projects = (List<Project>) query.getResultList();
			
			// set Projects in session
			request.getSession().setAttribute("projects", projects);
			
			model.setViewName("assignHierarchyView");
		}catch(Exception e) {
			// error page
			throw new RuntimeException(ExceptionUtils.getRootCauseMessage(e));
		}
		
		return model;
	}

	@RequestMapping("/getDesignatedEMployeeSuperiors.do")
	@Override
	public ResponseEntity getSuperiors(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			List<Employee> designatedEmployeeSuperiors = null;

			// Hibernate Configurations
			Configuration cfg = new Configuration();
			cfg = cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tr = session.beginTransaction();

			if (StringUtils.isBlank(designation)) {
				throw new RuntimeException("No designation selected");
			}
			// Fetch relevant employees and subordinates if designation is not blank
			else {
				
				// Populate Superior Designations
				List<String> superiorDesignations = new ArrayList<String>();
				for(FirmDesignations des : FirmDesignations.values()){
					if(des.ordinal()<FirmDesignations.valueOf(designation).ordinal()){
						superiorDesignations.add(des.name());
					}
				}
				
				if(superiorDesignations == null || superiorDesignations.isEmpty()){
					throw new RuntimeException("No superiors available for designated employee");
				}
				
				String desHql = "FROM Employee e where e.project.id=:projectId and e.designation in (:designations)";
				Query desQuery = session.createQuery(desHql);
				desQuery.setParameter("projectId", Integer.parseInt(projectId));
				desQuery.setParameter("designations", superiorDesignations);
				designatedEmployeeSuperiors = (List<Employee>) desQuery.getResultList();
				
				if(designatedEmployeeSuperiors == null || designatedEmployeeSuperiors.isEmpty()){
					throw new RuntimeException("No superiors available for designated employee");
				}
				
				// To avoid cyclic reference in GSON
				for(Employee e : designatedEmployeeSuperiors){
					e.setProject(null);
					e.setSuperiors(null);
					e.setSubordinates(null);
				}
				
				resModel.setModelList(designatedEmployeeSuperiors);
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

	@RequestMapping("/assignHierarchy.do")
	@Override
	public ResponseEntity assignHierarchy(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ResponseModel resModel = new ResponseModel();
		String resStatus = null;
		String resMess = null;

		try {
			String designatedEmployeesString = request.getParameter("designatedEmployees");
			String designatedEmployeesSuperiorsString = request.getParameter("designatedEmployeesSuperiors");

			if (StringUtils.isBlank(designatedEmployeesString)
					|| StringUtils.isBlank(designatedEmployeesSuperiorsString)) {
				throw new RuntimeException("Required request parameters not present");
			}

			// Hibernate Configuration
			Configuration cfg = new Configuration();
			cfg = cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tr = session.beginTransaction();

			String[] employeesArray = designatedEmployeesString.split(",");
			String[] superiorsArray = designatedEmployeesSuperiorsString.split(",");

			List<String> employeeIds = Arrays.asList(employeesArray);
			List<String> superiorIds = Arrays.asList(superiorsArray);

			List<Employee> employees = null;
			List<Employee> employeeSuperiors = null;

			// Get all employees and superiors
			String eHql = "FROM Employee e where e.empID in (:empIDs)";
			Query eQuery = session.createQuery(eHql);
			eQuery.setParameter("empIDs", employeeIds);
			employees = eQuery.getResultList();

			if (employees == null || employees.isEmpty()) {
				throw new RuntimeException("No such Employee in DB");
			}

			String sHql = "FROM Employee e where e.empID in (:empIDs)";
			Query sQuery = session.createQuery(sHql);
			sQuery.setParameter("empIDs", superiorIds);
			employeeSuperiors = sQuery.getResultList();

			if (employeeSuperiors == null || employeeSuperiors.isEmpty()) {
				throw new RuntimeException("No such Employee in DB");
			}

			// populate superiors in map
			Map<String, Employee> employeeSuperiorMap = new HashMap<String, Employee>();
			for (Employee e : employeeSuperiors) {
				employeeSuperiorMap.put(e.getEmpID(), e);
			}

			Set<Superior> superiorsSet = null;

			// update employee and superiors
			for (Employee e : employees) {
				superiorsSet = e.getSuperiors();

				if (superiorsSet == null || superiorsSet.isEmpty()) {
					superiorsSet = new HashSet<Superior>();
				}

				List<String> superiorEmpIDs = new ArrayList<String>();
				for (Superior s : superiorsSet) {
					superiorEmpIDs.add(s.getSuperiorID());
				}

				for (Map.Entry<String, Employee> entry : employeeSuperiorMap.entrySet()) {

					// check if superior already exists
					if (superiorEmpIDs.contains(entry.getKey())) {
						throw new RuntimeException("Employee : " + e.getFirstName() + " already has "
								+ entry.getValue().getFirstName() + " as superior");
					}

					// update Superiors
					Superior sup = new Superior();
					sup.setSuperiorID(entry.getKey());

					superiorsSet.add(sup);

					e.setSuperiors(superiorsSet);
					session.update(e);
					sup.setEmployee(e);
					session.saveOrUpdate(sup);

					// update Subordinates
					Employee employeeSuperior = entry.getValue();
					Subordinate sub = new Subordinate();
					sub.setSubordinateID(e.getEmpID());

					Set<Subordinate> subordinates = employeeSuperior.getSubordinates();

					if (subordinates == null || subordinates.isEmpty()) {
						subordinates = new HashSet<Subordinate>();
					}
					subordinates.add(sub);
					employeeSuperior.setSubordinates(subordinates);

					session.update(employeeSuperior);
					sub.setEmployee(e);
					session.saveOrUpdate(sub);
				}

			}

			tr.commit();

			resStatus = "Success";
			resMess = "Successfully assigned Superiors!";
		} catch (Exception e) {
			// for development environment only
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
