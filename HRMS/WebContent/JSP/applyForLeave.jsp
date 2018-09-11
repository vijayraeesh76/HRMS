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
<link rel="stylesheet" href="/HRMS/CSS/applyForLeave.css">
<link rel="stylesheet" href="/HRMS/CSS/jquery-ui.css">
<script type="text/javascript" src="/HRMS/JS/jquery-3.3.1.js"></script>
<script type="text/javascript" src="/HRMS/JS/jquery-ui.js"></script>
<script type="text/javascript" src="/HRMS/JS/applyForLeave.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>Apply for Leave</title>
</head>
<body>
	<div id="welcomeDiv">
		<div id="welcomeDivImage">
			<img id="welcomeImage" alt=""
				src="/HRMS/Images/HRMS/human_resource_management_crop.jpg">
		</div>
	</div>

	<div id="menu">
		<div id="leftMenu">
			<div class="leftMenuItems menuItems"
				onclick="window.location='leaveApplication'">Apply For Leave</div>
			<div class="leftMenuItems menuItems"
				onclick="window.location='employeeLeaveStatus.do'">Leave
				Status</div>
		</div>
		<div id="rightMenu">
			<div class="rightMenuItems menuItems" onclick="window.location='/HRMS/signOut.do'">Sign
				Out</div>
			<div class="rightMenuItems menuItems" onclick="window.location='/HRMS/home'">Home</div>
		</div>
	</div>

	<div id="applyLeave" class="contentDiv">
		<div id="supervisorDiv" class="hasInputDiv contentChild1">
			<div id="supervisorText" class="leftContent contentChild2">
				<span>Supervisor :</span>
			</div>
			<div class="contentChild2 rightContent" id="supervisorSelectDiv">
				<select class="inputs" id="superiorSelect">
					<option></option>
					<c:forEach items="${employeeSuperiors}" var="superior">
						<option value="${superior.empID}">${superior.firstName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div id="applyLeaveFirstContent" class="hasInputDiv contentChild1">
			<div class="contentChild2 leftContent" id="fromDateContentDiv">
				<div class="calendarText contentChild3">
					<span>From :</span>
				</div>
				<div class="calendarDiv contentChild3" id="fromDateInputDiv">
					<input class="calendarInput" id="fromDate">
				</div>
			</div>
			<div class="contentChild2 rightContent" id="toDateContentDiv" >
				<div class="calendarText contentChild3">
					<span>To :</span>
				</div>
				<div class="calendarDiv contentChild3" id="toDateDiv">
					<input class="calendarInput" id="toDate">
				</div>
			</div>
		</div>
		<div id="reasonDiv" class="hasInputDiv contentChild1">
			<textarea id="reasonTextArea" maxLength="200"
				placeholder="Please give a reason"></textarea>
		</div>
		<div class="applyLeaveDivs contentChild1" id="submitDiv">
			<input type="submit" id="submit" class="submit" value="Apply">
		</div>
	</div>

	<div id="applyLeaveMsg" class="msgDiv"></div>
</body>
</html>