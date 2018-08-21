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
<link rel="stylesheet" href="/HRMS/CSS/createProjectHRView.css">
<script type="text/javascript" src="/HRMS/JS/jquery-3.3.1.js"></script>
<script type="text/javascript"
	src="/HRMS/JS/createProjectHRView.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<title>Create Project</title>
</head>
<body>
	<form id="form" method="post" action="createProjectHRView.do"></form>
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

	<div id="projectCreationDiv" class="contentDiv">
		<div class="projectDivs contentFirstChild">
			<div class="projectLeft contentSecondChilda">
				<span>Project Name :</span>
			</div>
			<div class="projectRight contentSecondChildb projectInputDiv">
				<input type="text" class="inputFields" id="projectName">
			</div>
		</div>
		<div class="projectDivs contentFirstChild">
			<div class="projectLeft contentSecondChilda">
				<span>Project Manager :</span>
			</div>
			<div class="projectRight contentSecondChildb projectInputDiv">
				<select id="pmSelect" class="inputFields">
					<c:forEach items="${projectManagers}" var="pm">
						<option value="${pm.empID}">${pm.firstName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="projectDivs contentFirstChild">
			<div class="projectLeft contentSecondChilda">
				<span>Tech Leaders :</span>
			</div>
			<div class="projectRight contentSecondChildb">
				<select multiple="multiple" id="thlSelect"
					class="inputFields multiselect">
					<c:forEach items="${techLeaders}" var="thl">
						<option value="${thl.empID}">${thl.firstName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="projectDivs contentFirstChild">
			<div class="projectLeft contentSecondChilda">
				<span>Team Leaders :</span>
			</div>
			<div class="projectRight contentSecondChildb">
				<select multiple="multiple" id="tlSelect"
					class="inputFields multiselect">
					<c:forEach items="${teamLeaders}" var="tl">
						<option value="${tl.empID}">${tl.firstName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="projectDivs contentFirstChild">
			<div class="projectLeft contentSecondChilda">
				<span>Software Engineers :</span>
			</div>
			<div class="projectRight contentSecondChildb">
				<select multiple="multiple" id="seSelect"
					class="inputFields multiselect">
					<c:forEach items="${softwareEngineers}" var="se">
						<option value="${se.empID}">${se.firstName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="projectDivs contentFirstChild">
			<div class="projectLeft contentSecondChilda">
				<span>Trainees :</span>
			</div>
			<div class="projectRight contentSecondChildb">
				<select multiple="multiple" id="trSelect"
					class="inputFields multiselect">
					<c:forEach items="${trainees}" var="tr">
						<option value="${tr.empID}">${tr.firstName}</option>
					</c:forEach>
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