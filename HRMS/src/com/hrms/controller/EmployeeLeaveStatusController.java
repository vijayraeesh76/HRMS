package com.hrms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.hrms.model.Employee;
import com.hrms.model.LeaveBean;
import com.hrms.model.LeaveStatusEmployee;

/**
 * Servlet implementation class EmployeeLeaveStatus
 */
@WebServlet("/employeeLeaveStatus.do")
public class EmployeeLeaveStatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeLeaveStatusController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName = (String) request.getSession().getAttribute("userName");
		
		Configuration cfg = new Configuration();
		cfg = cfg.configure();
		SessionFactory sf = cfg.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		
		String hql = "FROM LeaveBean l where l.userName=:userName";
		Query query = session.createQuery(hql);
		query.setParameter("userName", userName);
		List<LeaveBean> leaveBeans = query.getResultList();	
		
		if(leaveBeans == null || leaveBeans.isEmpty()){
			throw new RuntimeException("No Leaves to view");
		}
		
		List<LeaveStatusEmployee> leaves = new ArrayList<LeaveStatusEmployee>();
		for(LeaveBean leave : leaveBeans){
			String ehql = "FROM Employee l where l.empID=:empID";
			Query eQuery = session.createQuery(ehql);
			eQuery.setParameter("empID", leave.getSuperiorEmpID());
			Employee supervisor = (Employee) eQuery.getSingleResult();	
			
			if(supervisor==null){
				throw new RuntimeException("Supervisor not present");
			}
			
			LeaveStatusEmployee leaveStatus = new LeaveStatusEmployee();
			leaveStatus.setLeaveDate(leave.getLeaveDate());
			leaveStatus.setLeaveStatus(leave.getLeaveStatus());
			leaveStatus.setSupervisor(supervisor.getFirstName());
			leaveStatus.setSupervisorComment(leave.getSupervisorComment());
			
			leaves.add(leaveStatus);
		}
		
		request.getSession().setAttribute("leaves", leaves);
		
		// TODO Auto-generated method stub
		response.sendRedirect("leaveStatus");;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
