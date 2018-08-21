package com.hrms.model;

public class Superior {
	private int id;
	private Employee employee;
	private String superiorID;

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

	public String getSuperiorID() {
		return superiorID;
	}

	public void setSuperiorID(String superiorID) {
		this.superiorID = superiorID;
	}

}
