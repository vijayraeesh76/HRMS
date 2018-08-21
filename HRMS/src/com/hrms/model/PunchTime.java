package com.hrms.model;

import java.time.LocalTime;

public class PunchTime {
	private int id;
	private PunchRecord punchRecord;
	private LocalTime pTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PunchRecord getPunchRecord() {
		return punchRecord;
	}

	public void setPunchRecord(PunchRecord punchRecord) {
		this.punchRecord = punchRecord;
	}

	public LocalTime getpTime() {
		return pTime;
	}

	public void setpTime(LocalTime pTime) {
		this.pTime = pTime;
	}

}
