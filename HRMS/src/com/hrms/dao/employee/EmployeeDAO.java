package com.hrms.dao.employee;

import java.util.List;

import com.hrms.model.Employee;

public interface EmployeeDAO {
	public void saveEmployee(Employee user);

	public List<String> getDistinctDesignationInProject(String projectId);
}
