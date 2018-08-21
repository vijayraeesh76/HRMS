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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;
import com.hrms.model.Employee;
import com.hrms.model.FirmDesignations;
import com.hrms.model.Project;
import com.hrms.model.ResponseModel;
import com.sun.org.apache.xalan.internal.utils.FeatureManager;

/**
 * Servlet implementation class AssignHierarchyFlowController
 */
@WebServlet("/assignHierarchyFlow.do")
public class AssignHierarchyFlowController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AssignHierarchyFlowController() {
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
				String hql = "SELECT DISTINCT e.designation FROM Employee e where e.project.id=:projectId";
				Query query = session.createQuery(hql);
				query.setParameter("projectId", Integer.parseInt(projectId));
				designations = (List<String>) query.getResultList();
				
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
