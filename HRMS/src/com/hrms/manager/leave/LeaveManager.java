package com.hrms.manager.leave;

import java.time.LocalDate;
import java.util.List;

import com.hrms.model.LeaveBean;

public interface LeaveManager {

	void saveLeave(LeaveBean newLeave);

	List<LeaveBean> getLeavesByEmpIDAndLeaveDate(String empID, LocalDate fDate);

	List<LeaveBean> getLeavesByEmpIDAndLeaveDateRange(String empID, LocalDate fDate, LocalDate tDate);	
	
}
