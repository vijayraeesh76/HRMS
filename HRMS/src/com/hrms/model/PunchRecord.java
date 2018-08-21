package com.hrms.model;

import java.time.LocalDate;
import java.util.Set;

public class PunchRecord {
	private int id;
	private String empID;
	private String userName;
	private Set<PunchTime> punches;
	private LocalDate date;

	public Set<PunchTime> getPunches() {
		return punches;
	}

	public void setPunches(Set<PunchTime> punches) {
		this.punches = punches;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
