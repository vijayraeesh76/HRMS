package com.hrms.controller.project;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

public interface ProjectController {
	public ModelAndView createProjectView(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public ResponseEntity createProject(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
