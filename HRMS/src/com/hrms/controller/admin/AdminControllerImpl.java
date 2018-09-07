package com.hrms.controller.admin;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hrms.model.Employee;
import com.hrms.utilities.HashPassword;

@Controller
public class AdminControllerImpl implements AdminController{
	
	@Override
	@RequestMapping("/adminRegister.do")
	public ModelAndView adminRegister(Employee employee) throws IOException{

		ModelAndView model = new ModelAndView();
		
		// AJAX
		/*ResponseModel responseStatus = new ResponseModel();
		String status = null;
		String responseMessage = null;*/
		
		try {
			String firstName = employee.getFirstName();
			String lastName = employee.getLastName();
			String userName = employee.getUserName();
			String password = employee.getPassword();
			String designation = employee.getDesignation();
			String doj = employee.getDojString();
			
			if(StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName) 
					|| StringUtils.isBlank(userName) || StringUtils.isBlank(password) 
					|| StringUtils.isBlank(designation) || null==doj) {
				String errMsg = "Required request param not found";
				
				System.out.println(errMsg);
				throw new RuntimeException(errMsg);
			}
			
			
			int employeeCount = 0;
			String passwordHash = null;
			String saltHash = null;
			
			Configuration cfg = new Configuration();
			cfg = cfg.configure();
			SessionFactory sf = cfg.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tr = session.beginTransaction();
			
			// Check no of employees in db for ID generation
			String countHql = "SELECT COUNT(*) FROM Employee";
			Query query = session.createQuery(countHql);
			employeeCount = (int) (long) query.getSingleResult();	
			
			if(employeeCount==0){
				throw new RuntimeException("Employee count can't be 0");
			}
			
			employeeCount++;
			
			// Hash the Password
			Map<String, String> hashes = HashPassword.getPasswordHashAndSaltHash(password);
			if(hashes == null || hashes.isEmpty()) {
				throw new RuntimeException("Error while Hashing password");
			}
			passwordHash = hashes.get("PasswordHash");
			saltHash = hashes.get("SaltHash");
			
			// populate employee details
			Employee user = new Employee();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setUserName(userName);
			user.setPassword(passwordHash);
			user.setDesignation(designation);
			user.setDoj(LocalDate.parse(doj));
			user.setEmpID("DC_"+employeeCount);
			user.setSalt(saltHash);
			
			session.save(user);
			tr.commit();
			
			//status = "Success";
		}catch(Exception e) {
			//status="Error";
			//responseMessage=ExceptionUtils.getRootCauseMessage(e);
			
			System.out.println("Exception Root Cause : " +ExceptionUtils.getRootCauseMessage(e));
			
			throw e;
		}
		
		// AJAX
		/*responseStatus.setStatus(status);
		responseStatus.setMessage(responseMessage);
		String jsonString = new Gson().toJson(responseStatus);*/
		
		model.setViewName("home");
		
		return model;
	}

	@Override
	@RequestMapping("/adminRegisterView.do")
	public ModelAndView adminRegisterView(@ModelAttribute("employee") Employee employee) throws IOException {
		ModelAndView model = new ModelAndView("adminRegister");
		return model;
	}
}
