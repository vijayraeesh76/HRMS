package com.hrms.manager.leave;

import java.time.LocalDate;
import java.util.List;

import com.hrms.dao.leave.LeaveDAO;
import com.hrms.model.LeaveBean;

public class LeaveManagerImpl implements LeaveManager {

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

	@Override
	public LeaveBean getLeaveByEmpIDAndLeaveDate(String empID, LocalDate date) {
		List<LeaveBean> leaves = null;
		LeaveBean leave = null;

		leaves = leaveDAO.getLeavesByEmpIDAndLeaveDate(empID, date);

		if (null != leaves && !leaves.isEmpty()) {
			if (leaves.size() > 1) {
				throw new RuntimeException("More than one leave date for empID : " + empID + " on a given date : "
						+ date + " has been found");
			}else {
				leave = leaves.get(0);
			}
		} else {
			throw new RuntimeException("No Leave data found for date : " + date + " and empID : " + empID);
		}

		return leave;
	}

	@Override
	public void updateLeave(LeaveBean leave) {
		leaveDAO.updateLeave(leave);
	}

}
