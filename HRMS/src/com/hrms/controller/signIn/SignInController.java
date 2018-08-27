package com.hrms.controller.signIn;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

public interface SignInController {
	public ResponseEntity signIn(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public ModelAndView signOut(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
