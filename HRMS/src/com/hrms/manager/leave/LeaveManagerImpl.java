package com.hrms.manager.leave;

import java.time.LocalDate;
import java.util.List;

import com.hrms.dao.leave.LeaveDAO;
import com.hrms.model.LeaveBean;

public class LeaveManagerImpl implements LeaveManager{
	
	private LeaveDAO leaveDAO;

	public LeaveDAO getLeaveDAO() {
		return leaveDAO;
	}

	public void setLeaveDAO(LeaveDAO leaveDAO) {
		this.leaveDAO = leaveDAO;
	}

	@Override
	public void saveLeave(LeaveBean newLeave) {
		leaveDAO.saveLeave(newLeave);
	}

	@Override
	public List<LeaveBean> getLeavesByEmpIDAndLeaveDate(String empID, LocalDate fDate) {
		
		return leaveDAO.getLeavesByEmpIDAndLeaveDate(empID, fDate);
	}

	@Override
	public List<LeaveBean> getLeavesByEmpIDAndLeaveDateRange(String empID, LocalDate fDate, LocalDate tDate) {
		// TODO Auto-generated method stub
		return leaveDAO.getLeavesByEmpIDAndLeaveDateRange(empID, fDate, tDate);
	}
	
	
}
