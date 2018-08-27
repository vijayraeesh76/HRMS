package com.hrms.controller.punchin;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hrms.model.Attendance;
import com.hrms.model.AttendanceCalendarModel;
import com.hrms.model.LeaveBean;
import com.hrms.model.PunchRecord;
import com.hrms.model.PunchTime;
import com.hrms.model.ResponseModel;
import com.hrms.utilities.PunchTimeSort;

@Controller
public class PunchInControllerImpl implements PunchInController {

	@RequestMapping("/punchIn.do")
	@Override
	public ModelAndView showPunchIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView();
		
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
		/*
		 * String hql = "FROM PunchRecord p where p.date=:date and p.empID=:empID";
		 * Query query = hibSession.createQuery(hql); query.setParameter("date", date);
		 * query.setParameter("empID", empID); List<PunchRecord> punchRecords =
		 * query.getResultList(); tr.commit();
		 * 
		 * PunchRecord punchRecord = null;
		 * 
		 * if (punchRecords == null || punchRecords.isEmpty()) { // Handled by UI } else
		 * { if (punchRecords.size() == 1) { punchRecord = punchRecords.get(0);
		 * 
		 * // update PunchRecord in session
		 * request.getSession().setAttribute("punchRecord", punchRecord);
		 * 
		 * } else { throw new
		 * RuntimeException("More than one punch record for a day has been detected"); }
		 * }
		 */

		String hql = "FROM Attendance p where p.empID=:empID ORDER BY p.attendanceDate ASC";
		Query query = hibSession.createQuery(hql);
		query.setParameter("empID", empID);
		List<Attendance> attendances = query.getResultList();

		request.getSession().setAttribute("attendances", attendances);

		model.setViewName("punchIn");
		
		return model;
	}

	@RequestMapping("/attendanceRecorder.do")
	@Override
	public ResponseEntity recordAttendance(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ResponseModel resModel = new ResponseModel();
		String resStatus = null;
		String resMess = null;
		
		try {
			// get request Parameters
			String punchTimeHours = request.getParameter("punchTimeHours");
			String punchTimeMinutes = request.getParameter("punchTimeMinutes");
			String punchDate = request.getParameter("punchDate");

			if ((punchTimeHours == null || punchTimeHours.isEmpty())
					|| (punchTimeMinutes == null || punchTimeMinutes.isEmpty())
					|| (punchDate == null || punchDate.isEmpty())) {
				throw new NullPointerException("Required request parameters not present");
			}

			LocalTime punchTimeJ = LocalTime.of(Integer.parseInt(punchTimeHours), Integer.parseInt(punchTimeMinutes));

			// Get required session parameters
			HttpSession session = request.getSession();
			String empID = (String) session.getAttribute("empID");
			String userName = (String) session.getAttribute("userName");

			// date of punch in
			LocalDate date = LocalDate.parse(punchDate);

			// Hibernate configurations
			Configuration cfg = new Configuration();
			cfg = cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session hibSession = sf.openSession();
			Transaction tr = hibSession.beginTransaction();

			// Check if PunchRecord already exists for the date
			String hql = "FROM PunchRecord p where p.date=:date and p.empID=:empID";
			Query query = hibSession.createQuery(hql);
			query.setParameter("date", date);
			query.setParameter("empID", empID);
			List<PunchRecord> punchRecords = query.getResultList();
			PunchRecord punchRecord = null;
			Set<PunchTime> punches = null;
			PunchTime punchTime = null;
			List<PunchTime> punchesList = null;

			// update PunchRecord in session
			if (punchRecords != null && !punchRecords.isEmpty()) {
				if (punchRecords.size() == 1) {
					punchRecord = punchRecords.get(0);

					punchTime = new PunchTime();
					punchTime.setpTime(punchTimeJ);
					punchTime.setPunchRecord(punchRecord);

					punches = punchRecord.getPunches();
					if (punches == null) {
						punches = new TreeSet<PunchTime>();
					}
					punches.add(punchTime);
					
					punchesList = new ArrayList();
					punchesList.addAll(punches);
					
					punchRecord.setPunches(punches);

				} else {
					throw new RuntimeException("More than one PunchRecord detected");
				}
			} else {
				punchRecord = new PunchRecord();

				punchRecord.setDate(date);
				punchRecord.setEmpID(empID);
				punchRecord.setUserName(userName);

				hibSession.save(punchRecord);

				// Create PunchTime
				punchTime = new PunchTime();
				punchTime.setpTime(punchTimeJ);
				punchTime.setPunchRecord(punchRecord);

				punches = punchRecord.getPunches();
				if (punches == null) {
					punches = new HashSet<PunchTime>();
				}
				punches.add(punchTime);

				punchesList = new ArrayList();
				punchesList.addAll(punches);
				
				punchRecord.setPunches(punches);
			}

			// save PunchTime
			hibSession.save(punchTime);

			// Create or update attendance
			String hql2 = "FROM Attendance p where p.attendanceDate=:date and p.empID=:empID";
			Query query2 = hibSession.createQuery(hql2);
			query2.setParameter("date", date);
			query2.setParameter("empID", empID);
			List<Attendance> attendances = query2.getResultList();
			Attendance attendance = null;

			// Make sure only one attendance per day is present
			if (attendances != null && !attendances.isEmpty()) {
				if (attendances.size() > 1) {
					throw new RuntimeException("More than one attendance for the date is present");
				} else {
					attendance = attendances.get(0);
				}
			} else {
				attendance = new Attendance();
			}

			// Calculate work-hours
			
			// sort punch times
			Collections.sort(punchesList, new PunchTimeSort());
			
			float workHours = 0;
			long totalWorkTimeInSeconds = 0;
			int count = 1;
			LocalTime fromTime = null;
			for (PunchTime punch : punchesList) {
				if (count % 2 == 1) {
					fromTime = punch.getpTime();
				} else if (count % 2 == 0) {
					long duration = fromTime.until(punch.getpTime(), ChronoUnit.SECONDS);
					totalWorkTimeInSeconds = totalWorkTimeInSeconds + duration;
				}
				count++;
			}

			workHours = totalWorkTimeInSeconds / (60 * 60);
			if (workHours > 4 && workHours < 8) {
				attendance.setAttendance("HALF-DAY");
			} else if (workHours > 8) {
				attendance.setAttendance("FULL-DAY");
			}else{
				attendance.setAttendance("ABSENT");
			}

			//set WorkHoursString
			long workMinutes = totalWorkTimeInSeconds / 60;
			String residualMinutes = Long.toString(workMinutes % 60);
			String hours = Long.toString(workMinutes / 60);
			String workHoursString = hours + "H " + residualMinutes + "M";
			
			attendance.setAttendanceDate(date);
			attendance.setEmpID(empID);
			attendance.setUserName(userName);
			attendance.setWorkHours(workHours);
			attendance.setWorkHoursString(workHoursString);

			hibSession.save(attendance);

			tr.commit();

			resStatus = "Success";
			resMess = "Punched In Successfully!";
		} catch (Exception e) {
			// Must create error page
			resStatus = "Error";
			resMess = ExceptionUtils.getRootCauseMessage(e);
		}

		// Prepare response
		resModel.setStatus(resStatus);
		resModel.setMessage(resMess);
		String jsonString = new Gson().toJson(resModel);
		
		return ResponseEntity.ok(jsonString);
	}

	@RequestMapping("/attendanceCalendar.do")
	@Override
	public ModelAndView showAttendanceCalendar(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ModelAndView modelAndView = new ModelAndView();
		
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
		
		modelAndView.setViewName("attendanceCalendarView");
		
		return modelAndView;
	}

}
