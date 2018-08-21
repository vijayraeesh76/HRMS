package com.hrms.model;

import java.time.LocalDate;
import java.util.List;

public class AttendanceCalendarModel {
	private List<LocalDate> presentDays;
	private List<LocalDate> waitingDays;
	private List<LocalDate> pendingDays;
	private List<LocalDate> approvedDays;
	private List<LocalDate> rejectedDays;

	public List<LocalDate> getPresentDays() {
		return presentDays;
	}

	public void setPresentDays(List<LocalDate> presentDays) {
		this.presentDays = presentDays;
	}

	public List<LocalDate> getWaitingDays() {
		return waitingDays;
	}

	public void setWaitingDays(List<LocalDate> waitingDays) {
		this.waitingDays = waitingDays;
	}

	public List<LocalDate> getPendingDays() {
		return pendingDays;
	}

	public void setPendingDays(List<LocalDate> pendingDays) {
		this.pendingDays = pendingDays;
	}

	public List<LocalDate> getApprovedDays() {
		return approvedDays;
	}

	public void setApprovedDays(List<LocalDate> approvedDays) {
		this.approvedDays = approvedDays;
	}

	public List<LocalDate> getRejectedDays() {
		return rejectedDays;
	}

	public void setRejectedDays(List<LocalDate> rejectedDays) {
		this.rejectedDays = rejectedDays;
	}

}
