package com.hrms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.hrms.model.Attendance;
import com.hrms.model.AttendanceCalendarModel;
import com.hrms.model.LeaveBean;

/**
 * Servlet implementation class AttendanceCalendar
 */
@WebServlet("/attendanceCalendar.do")
public class AttendanceCalendarController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AttendanceCalendarController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");

		List<LocalDate> presentDays = new ArrayList<LocalDate>();
		List<LocalDate> waitingDays = new ArrayList<LocalDate>();
		List<LocalDate> pendingDays = new ArrayList<LocalDate>();
		List<LocalDate> approvedDays = new ArrayList<LocalDate>();
		List<LocalDate> rejectedDays = new ArrayList<LocalDate>();
		List<LocalDate> allDays = new ArrayList<LocalDate>();

		AttendanceCalendarModel model = new AttendanceCalendarModel();

		// Hibernate configurations
		Configuration cfg = new Configuration();
		cfg = cfg.configure();
		SessionFactory sf = cfg.buildSessionFactory();
		Session hibSession = sf.openSession();
		Transaction tr = hibSession.beginTransaction();

		// Get all Attendances for the user
		String hql = "FROM Attendance p where p.userName=:userName";
		Query query = hibSession.createQuery(hql);
		query.setParameter("userName", userName);
		List<Attendance> attendances = query.getResultList();

		// get all Leaves for the user
		String hql1 = "FROM LeaveBean p where p.userName=:userName";
		Query query1 = hibSession.createQuery(hql1);
		query1.setParameter("userName", userName);
		List<LeaveBean> leaves = query1.getResultList();

		// Populate attendance dates and their relevant status in map
		if (attendances != null && !attendances.isEmpty()) {
			for (Attendance attendance : attendances) {
				if (attendance.getAttendance().equals("PRESENT")) {
					presentDays.add(attendance.getAttendanceDate());
				} else if (attendance.getAttendance().equals("REJECTED")) {
					rejectedDays.add(attendance.getAttendanceDate());
				} else if (attendance.getAttendance().equals("WAITING")) {
					waitingDays.add(attendance.getAttendanceDate());
				} else if (attendance.getAttendance().equals("PENDING")) {
					pendingDays.add(attendance.getAttendanceDate());
				} else if (attendance.getAttendance().equals("APPROVED")) {
					approvedDays.add(attendance.getAttendanceDate());
				}
				allDays.add(attendance.getAttendanceDate());
			}
		}

		// populate Leave information if necessary
		if (leaves != null && !leaves.isEmpty()) {
			for (LeaveBean leave : leaves) {
				if (presentDays.contains(leave.getLeaveDate())) {
					continue;
				} else {
					if (leave.getLeaveStatus().equals("REJECTED")) {
						rejectedDays.add(leave.getLeaveDate());
					} else if (leave.getLeaveStatus().equals("WAITING")) {
						waitingDays.add(leave.getLeaveDate());
					} else if (leave.getLeaveStatus().equals("PENDING")) {
						pendingDays.add(leave.getLeaveDate());
					} else if (leave.getLeaveStatus().equals("APPROVED")) {
						approvedDays.add(leave.getLeaveDate());
					}
				}
			}
		}

		// Store attendance details in session
		session.setAttribute("approvedDays", approvedDays);
		session.setAttribute("presentDays", presentDays);
		session.setAttribute("rejectedDays", rejectedDays);
		session.setAttribute("pendingDays", pendingDays);
		session.setAttribute("waitingDays", waitingDays);

		response.sendRedirect("attendanceCalendarView");
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
