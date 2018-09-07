package com.hrms.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Transient;

public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String designation;
	@Transient
	private String dojString;
	private LocalDate doj;
	private String empID;
	private String salt;
	private Project project;
	private Set<Superior> superiors;
	private Set<Subordinate> subordinates;

	public String getDojString() {
		return dojString;
	}

	public void setDojString(String dojString) {
		this.dojString = dojString;
	}

	public Set<Superior> getSuperiors() {
		return superiors;
	}

	public void setSuperiors(Set<Superior> superiors) {
		this.superiors = superiors;
	}

	public Set<Subordinate> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(Set<Subordinate> subordinates) {
		this.subordinates = subordinates;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public LocalDate getDoj() {
		return doj;
	}

	public void setDoj(LocalDate doj) {
		this.doj = doj;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
