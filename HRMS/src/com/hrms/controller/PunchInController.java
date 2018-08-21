package com.hrms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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

import com.google.gson.Gson;
import com.hrms.model.Attendance;
import com.hrms.model.PunchDetails;
import com.hrms.model.PunchRecord;

/**
 * Servlet implementation class PunchInController
 */
@WebServlet("/punchIn.do")
public class PunchInController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PunchInController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get required session parameters
		HttpSession session = request.getSession();
		String empID = (String) session.getAttribute("empID");
		String userName = (String) session.getAttribute("userName");

		// date of punch in
		LocalDate date = LocalDate.now();

		// Hibernate configurations
		Configuration cfg = new Configuration();
		cfg = cfg.configure();
		SessionFactory sf = cfg.buildSessionFactory();
		Session hibSession = sf.openSession();
		Transaction tr = hibSession.beginTransaction();

		// Check if PunchRecord already exists for the date
		/*String hql = "FROM PunchRecord p where p.date=:date and p.empID=:empID";
		Query query = hibSession.createQuery(hql);
		query.setParameter("date", date);
		query.setParameter("empID", empID);
		List<PunchRecord> punchRecords = query.getResultList();
		tr.commit();
		
		PunchRecord punchRecord = null;

		if (punchRecords == null || punchRecords.isEmpty()) {
			// Handled by UI
		} else {
			if (punchRecords.size() == 1) {
				punchRecord = punchRecords.get(0);

				// update PunchRecord in session
				request.getSession().setAttribute("punchRecord", punchRecord);
				
			} else {
				throw new RuntimeException("More than one punch record for a day has been detected");
			}
		}*/
		
		String hql = "FROM Attendance p where p.empID=:empID ORDER BY p.attendanceDate ASC";
		Query query = hibSession.createQuery(hql);
		query.setParameter("empID", empID);
		List<Attendance> attendances = query.getResultList();
		
		request.getSession().setAttribute("attendances", attendances);
		
		response.sendRedirect("punchIn");
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
