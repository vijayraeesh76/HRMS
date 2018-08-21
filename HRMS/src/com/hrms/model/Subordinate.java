package com.hrms.model;

public class Subordinate {
	private int id;
	private Employee employee;
	private String subordinateID;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getSubordinateID() {
		return subordinateID;
	}

	public void setSubordinateID(String subordinateID) {
		this.subordinateID = subordinateID;
	}

}
