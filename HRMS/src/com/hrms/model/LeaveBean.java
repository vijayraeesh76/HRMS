package com.hrms.model;

import java.time.LocalDate;

public class LeaveBean {
	private int id;
	private String userName;
	private String empID;
	private LocalDate leaveDate;
	private String leaveStatus;
	private String reason;
	private String superiorEmpID;
	private String supervisorComment;

	public String getSupervisorComment() {
		return supervisorComment;
	}

	public void setSupervisorComment(String supervisorComment) {
		this.supervisorComment = supervisorComment;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSuperiorEmpID() {
		return superiorEmpID;
	}

	public void setSuperiorEmpID(String superiorEmpID) {
		this.superiorEmpID = superiorEmpID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public LocalDate getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(LocalDate leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

}
