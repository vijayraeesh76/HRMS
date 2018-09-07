package com.hrms.controller.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.hrms.model.Employee;

public interface AdminController {
	public ModelAndView adminRegister(Employee employee) throws IOException;
	public ModelAndView adminRegisterView(Employee employee) throws IOException;;
}
