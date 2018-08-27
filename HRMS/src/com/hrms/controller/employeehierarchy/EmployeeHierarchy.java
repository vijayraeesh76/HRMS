package com.hrms.controller.employeehierarchy;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

public interface EmployeeHierarchy {
	public ModelAndView assignHierarchyView(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public ResponseEntity getDesignationsAndEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public ResponseEntity getSuperiors(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public ResponseEntity assignHierarchy(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
