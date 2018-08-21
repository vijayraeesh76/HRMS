package com.hrms.model;

import java.time.LocalDate;

import javax.persistence.Transient;

public class Attendance {
	private int id;
	private String empID;
	private String userName;
	private String attendance;
	private LocalDate attendanceDate;
	private float workHours;
	private String workHoursString;
	
	public String getWorkHoursString() {
		return workHoursString;
	}

	public void setWorkHoursString(String workHoursString) {
		this.workHoursString = workHoursString;
	}

	public float getWorkHours() {
		return workHours;
	}

	public void setWorkHours(float workHours) {
		this.workHours = workHours;
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

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(LocalDate attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
}
