package com.hrms.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;
import com.hrms.model.Employee;
import com.hrms.model.LeaveBean;
import com.hrms.model.ResponseModel;

/**
 * Servlet implementation class UpdateLeaveStatus
 */
@WebServlet("/updateLeaveStatus.do")
public class UpdateLeaveStatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateLeaveStatusController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ResponseModel resModel = new ResponseModel();
		String resStatus = null;
		String resMess = null;
		try{
			String firstName = request.getParameter("firstName");
			String empID = request.getParameter("empID");
			String leaveDateString = request.getParameter("leaveDate");
			String leaveStatus = request.getParameter("leaveStatus");
			String eligibleLeaveDays = request.getParameter("eligibleLeaveDays");
			String comment = request.getParameter("comment");
			Employee employee = null;
			LeaveBean leave = null;

			Configuration cfg = new Configuration();
			cfg = cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tr = session.beginTransaction();
			
			// Get leave to update
			String hql = "FROM LeaveBean u where u.empID=:empID and leaveDate=:leaveDate";
			Query query = session.createQuery(hql);
			query.setParameter("empID", empID);
			query.setParameter("leaveDate", LocalDate.parse(leaveDateString));
			leave = (LeaveBean) query.getSingleResult();
			
			if(leave==null){
				throw new NullPointerException("Unable to fetch leave to update.");
			}
			
			//update leave
			leave.setLeaveStatus(leaveStatus);
			leave.setSupervisorComment(comment);
			
			//save updated leave in DB
			session.save(leave);
			tr.commit();
			
			resStatus = "Success";
			resMess = "Leave successfully Updated!";
		}catch(Exception e){
			resStatus = "Error";
			resMess = ExceptionUtils.getRootCauseMessage(e);
		}
		
		resModel.setStatus(resStatus);
		resModel.setMessage(resMess);
		
		String jsonString = new Gson().toJson(resModel);

		// TODO Auto-generated method stub
		response.getWriter().append(jsonString);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
