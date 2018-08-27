package com.hrms.controller.leave;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

public interface LeaveController {
	public ModelAndView leaveApplicationView(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public ResponseEntity applyLeave(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public ModelAndView employeeLeaveStatusView(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public ModelAndView updateLeaveStatusView(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public ResponseEntity updateLeaveStatus(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
