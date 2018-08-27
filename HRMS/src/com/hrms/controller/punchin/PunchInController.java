package com.hrms.controller.punchin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

public interface PunchInController {
	public ModelAndView showPunchIn(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public ResponseEntity recordAttendance(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public ModelAndView showAttendanceCalendar(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
