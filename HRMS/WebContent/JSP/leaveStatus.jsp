<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/HRMS/CSS/body.css">
<link rel="stylesheet" href="/HRMS/CSS/welcomeDiv.css">
<link rel="stylesheet" href="/HRMS/CSS/menu.css">
<link rel="stylesheet" href="/HRMS/CSS/leaveStatus.css">
<link rel="stylesheet" href="/HRMS/CSS/table.css">
<%@ page import="com.hrms.model.LeaveBean"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>Leave Status</title>
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
				onclick="window.location='applyForLeave.do'">Apply For Leave</div>
			<div class="leftMenuItems menuItems"
				onclick="window.location='employeeLeaveStatus.do'">Leave
				Status</div>
		</div>
		<div id="rightMenu">
			<div class="rightMenuItems menuItems" onclick="window.location='signOut.do'">Sign
				Out</div>
			<div class="rightMenuItems menuItems" onclick="window.location='home'">Home</div>
		</div>
	</div>

	<div id="leaves">
		<table id="leavesTable">
			<tr>
				<th>Leave Date</th>
				<th>Leave Status</th>
				<th>Supervisor</th>
				<th>Supervisor Comment</th>
			</tr>
			<c:forEach items="${leaves}" var="leave">
				<tr>
					<td class="leaveDates">${leave.leaveDate}</td>
					<td class="leaveStatuses">${leave.leaveStatus}</td>
					<td class="leaveSupervisors">${leave.supervisor}</textarea>
					</td>
					<td class="leaveComments textAreaTd"><textarea readonly="readonly">${leave.supervisorComment}</textarea>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>