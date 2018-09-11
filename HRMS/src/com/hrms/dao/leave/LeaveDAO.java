package com.hrms.dao.leave;

import java.time.LocalDate;
import java.util.List;

import com.hrms.model.LeaveBean;

public interface LeaveDAO {

	void saveLeave(LeaveBean newLeave);

	List<LeaveBean> getLeavesByEmpIDAndLeaveDate(String empID, LocalDate fDate);

	List<LeaveBean> getLeavesByEmpIDAndLeaveDateRange(String empID, LocalDate fDate, LocalDate tDate);

}
