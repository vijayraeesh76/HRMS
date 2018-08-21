<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/HRMS/CSS/body.css">
<link rel="stylesheet" href="/HRMS/CSS/welcomeDiv.css">
<link rel="stylesheet" href="/HRMS/CSS/menu.css">
<link rel="stylesheet" href="/HRMS/CSS/content.css">
<link rel="stylesheet" href="/HRMS/CSS/assignHierarchyView.css">
<script type="text/javascript" src="/HRMS/JS/jquery-3.3.1.js"></script>
<script type="text/javascript" src="/HRMS/JS/assignHierarchyView.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>Insert title here</title>
</head>
<body>
<form id="form" method="post" action="assignHierarchyView.do"></form>
	<div id="welcomeDiv">
		<div id="welcomeDivImage">
			<img id="welcomeImage" alt=""
				src="/HRMS/Images/HRMS/human_resource_management_crop.jpg">
		</div>
	</div>

	<div id="menu">
		<div id="leftMenu">
			<div class="leftMenuItems menuItems"
				onclick="window.location='projectManagementView'">Back</div>
		</div>
		<div id="rightMenu">
			<div class="rightMenuItems menuItems" onclick="window.location='signOut.do'">Sign
				Out</div>
			<div class="rightMenuItems menuItems"
				onclick="window.location='manageAccount.jsp'">Manage Account</div>
			<div class="rightMenuItems menuItems" onclick="window.location='home'">Home</div>
		</div>
	</div>

	<div id="hierarchyContent" class="contentDiv">
		<div class="hierarchyChild1 contentFirstChild">
			<div class="contentLeft contentSecondChilda">Project :</div>
			<div class="contentSecondChildb">
				<select class="inputs selectInput" id="projectSelect">
					<option></option>
					<c:forEach items="${projects}" var="project">
							<option value="${project.id}">${project.projectName}</option>
						</c:forEach>
				</select>
			</div>
		</div>
		<div class="hierarchyChild1 contentFirstChild">
			<div class="contentLeft contentSecondChilda">Designation :</div>
			<div class="contentSecondChildb">
				<select class="inputs selectInput" id="designationsSelect">
					
				</select>
			</div>
		</div>
		<div class="hierarchyChild1 contentFirstChild">
			<div class="contentLeft contentSecondChilda">Employees :</div>
			<div class="contentSecondChildb">
				<select multiple="multiple" class="inputs multiselect" id="designatedEmployee">
				</select>
			</div>
		</div>
		<div class="hierarchyChild1 contentFirstChild">
			<div class="contentLeft contentSecondChilda">Superiors :</div>
			<div class="contentSecondChildb">
				<select multiple="multiple" class="inputs multiselect" id="designatedEmployeeSuperiors">
				</select>
			</div>
		</div>
		<hr>
		<div id="submitDiv">
			<input type="submit" id="submit" class="submit">
		</div>
	</div>
	
	<div id="msgDiv" class="msgDiv"></div>
</body>
</html>