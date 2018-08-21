<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/HRMS/CSS/body.css">
<link rel="stylesheet" href="/HRMS/CSS/welcomeDiv.css">
<link rel="stylesheet" href="/HRMS/CSS/menu.css">
<link rel="stylesheet" href="/HRMS/CSS/attendanceCalendarView.css">
<link rel="stylesheet" href="/HRMS/CSS/jquery-ui.css">
<script type="text/javascript" src="/HRMS/JS/jquery-3.3.1.js"></script>
<script type="text/javascript" src="/HRMS/JS/jquery-ui.js"></script>
<script type="text/javascript">

var presentDays =JSON.parse('<%=new Gson().toJson(request.getSession().getAttribute("presentDays"))%>');
var rejectedDays =JSON.parse('<%=new Gson().toJson(request.getSession().getAttribute("rejectedDays"))%>');
var approvedDays =JSON.parse('<%=new Gson().toJson(request.getSession().getAttribute("approvedDays"))%>');
var waitingDays =JSON.parse('<%=new Gson().toJson(request.getSession().getAttribute("waitingDays"))%>');
var pendingDays =JSON.parse('<%=new Gson().toJson(request.getSession().getAttribute("pendingDays"))%>');

</script>
<script type="text/javascript"
	src="/HRMS/JS/attendanceCalendarView.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Attendance Calendar</title>
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
			<div class="leftMenuItems"
				onclick="window.location='attendanceManagement'">Back</div>
		</div>
		<div id="rightMenu">
			<div class="rightMenuItems" onclick="window.location='signOut.do'">Sign
				Out</div>
			<div class="rightMenuItems"
				onclick="window.location='manageAccount.jsp'">Manage Account</div>
			<div class="rightMenuItems">Welcome ${sessionScope.firstName}</div>
		</div>
	</div>
	<div id="calendarDiv">
		<!-- <input type="date" id="datepicker"> -->
	</div>
</body>
</html>