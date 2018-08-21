package com.hrms.model;

import java.time.LocalTime;

public class PunchDetails {

	private String status;
	private String firstName;
	private String empID;
	private String punchTime;
	private String punchInOrOut;
	private int workingHours;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getPunchTime() {
		return punchTime;
	}

	public void setPunchTime(String punchTime) {
		this.punchTime = punchTime;
	}

	public String getPunchInOrOut() {
		return punchInOrOut;
	}

	public void setPunchInOrOut(String punchInOrOut) {
		this.punchInOrOut = punchInOrOut;
	}

	public int getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(int workingHours) {
		this.workingHours = workingHours;
	}

}
