package com.hrms.controller.employee;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.hrms.model.Employee;

public interface EmployeeController {
	public ModelAndView adminRegister(Employee employee, Errors errors) throws IOException;
	public ModelAndView adminRegisterView(Employee employee) throws IOException;
	ResponseEntity getDesignationsAndEmployees(HttpServletRequest request, HttpServletResponse response)
			throws IOException;;
}
