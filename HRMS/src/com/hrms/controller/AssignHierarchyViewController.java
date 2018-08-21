package com.hrms.controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.hrms.model.Customer;
import com.hrms.model.Project;

/**
 * Servlet implementation class AssignHierarchyViewController
 */
@WebServlet("/assignHierarchyView.do")
public class AssignHierarchyViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignHierarchyViewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Project> projects = null;
			
			// Hibernate Configurations
			Configuration cfg = new Configuration();
			cfg = cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tr = session.beginTransaction();
			
			// Get all Projects
			String hql = "FROM Project";
			Query query = session.createQuery(hql);
			projects = (List<Project>) query.getResultList();
			
			// set Projects in session
			request.getSession().setAttribute("projects", projects);
		
			response.sendRedirect("assignHierarchyView");
		}catch(Exception e) {
			// error page
			throw new RuntimeException(ExceptionUtils.getRootCauseMessage(e));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
