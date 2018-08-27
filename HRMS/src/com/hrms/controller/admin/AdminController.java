package com.hrms.controller.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

public interface AdminController {
	public ResponseEntity adminRegister(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
