package com.hrms.model;

import java.time.LocalDate;

public class LeaveStatusEmployee {

	private LocalDate leaveDate;
	private String leaveStatus;
	private String supervisor;
	private String supervisorComment;

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

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getSupervisorComment() {
		return supervisorComment;
	}

	public void setSupervisorComment(String supervisorComment) {
		this.supervisorComment = supervisorComment;
	}

}
