package com.hrms.model;

public class LeavesHRView {
	private String firstName;
	private String empID;
	private String leaveDate;
	private String status;
	private Integer eligibleLeaveDays;
	private String reason;
	private String comment;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getEligibleLeaveDays() {
		return eligibleLeaveDays;
	}

	public void setEligibleLeaveDays(Integer eligibleLeaveDays) {
		this.eligibleLeaveDays = eligibleLeaveDays;
	}

}
