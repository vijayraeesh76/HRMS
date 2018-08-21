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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;
import com.hrms.model.Employee;
import com.hrms.model.FirmDesignations;
import com.hrms.model.ResponseModel;

/**
 * Servlet implementation class AssignHierarchySuperiorsController
 */
@WebServlet("/getDesignatedEMployeeSuperiors.do")
public class AssignHierarchySuperiorsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignHierarchySuperiorsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
