package com.hrms.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

@Entity
@Table(name="leave_table")
public class LeaveBean {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="User_Name")
	private String userName;
	
	@Column(name="Emp_ID")
	private String empID;
	
	@Column(name="Leave_Date")
	private LocalDate leaveDate;
	
	@Column(name="Leave_Status")
	private String leaveStatus;
	
	@Column(name="Reason")
	private String reason;
	
	@Column(name="Superior_Emp_Id")
	private String superiorEmpID;
	
	@Column(name="Supervisor_Comment")
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
