package com.hrms.dao.leave;

import java.time.LocalDate;
import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.hrms.model.LeaveBean;

@Transactional
public class LeaveDAOImpl implements LeaveDAO {
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public void saveLeave(LeaveBean newLeave) {
		hibernateTemplate.save("LeaveBean", newLeave);
	}

	@Override
	public List<LeaveBean> getLeavesByEmpIDAndLeaveDate(String empID, LocalDate fDate) {
		String hql = "FROM LeaveBean u where u.empID=:empID and leaveDate=:leaveDate";

		String[] paramNames = { "empID", "leaveDate" };
		Object[] paramValues = { empID, fDate };

		return (List<LeaveBean>) hibernateTemplate.findByNamedParam(hql, paramNames, paramValues);
	}

	@Override
	public List<LeaveBean> getLeavesByEmpIDAndLeaveDateRange(String empID, LocalDate fDate, LocalDate tDate) {
		String hql = "FROM LeaveBean u where u.empID=:empID and u.leaveDate BETWEEN :fDate AND :tDate";

		String[] paramNames = { "empID", "fDate", "tDate" };
		Object[] paramValues = { empID, fDate, tDate };

		return (List<LeaveBean>) hibernateTemplate.findByNamedParam(hql, paramNames, paramValues);
	}

}
