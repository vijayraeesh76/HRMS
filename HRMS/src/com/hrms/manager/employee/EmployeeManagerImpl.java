package com.hrms.manager.employee;

import java.util.List;

import com.hrms.dao.employee.EmployeeDAO;
import com.hrms.model.Employee;

public class EmployeeManagerImpl implements EmployeeManager {
	
	private EmployeeDAO employeeDAO;

	@Override
	public void saveEmployee(Employee user) {
		employeeDAO.saveEmployee(user);
	}

	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	@Override
	public List<String> getDistinctDesignationInProject(String projectId) {
		
		return employeeDAO.getDistinctDesignationInProject(projectId);
	}

}
