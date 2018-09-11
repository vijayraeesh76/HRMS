package com.hrms.dao.employee;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hrms.model.Employee;

public class EmployeeDAOImpl implements EmployeeDAO{
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void saveEmployee(Employee user) {
		
		String query = null;
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String userName = user.getUserName();
		String password = user.getPassword();
		String designation = user.getDesignation();
		LocalDate doj = user.getDoj();
		String salthash = user.getSalt();
		String empID = user.getEmpID();
		
		query = "INSERT INTO Employee (First_Name,Last_Name,User_Name,Emp_ID,Password,Designation,DOJ,Salt )"
				+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
		
		 // define query arguments
		 Object[] params = new Object[] { firstName, lastName, userName, empID, password, designation, doj, salthash };
		
		jdbcTemplate.update(query, params);
	}

	@Override
	public List<String> getDistinctDesignationInProject(String projectId) {
		String query = "SELECT DISTINCT Designation FROM EMPLOYEE where Project_Id="+projectId;
		
		return jdbcTemplate.queryForList(query, String.class);
	}

}
