package com.hrms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.hrms.model.ResponseModel;
import com.hrms.model.Subordinate;
import com.hrms.model.Superior;

/**
 * Servlet implementation class AssignHierarchyController
 */
@WebServlet("/assignHierarchy.do")
public class AssignHierarchyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AssignHierarchyController() {
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
