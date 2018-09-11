package com.hrms.manager.employee;

import java.util.List;

import com.hrms.model.Employee;

public interface EmployeeManager {

	void saveEmployee(Employee user);

	List<String> getDistinctDesignationInProject(String projectId);

}
