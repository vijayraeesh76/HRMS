<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/HRMS/CSS/body.css">
<link rel="stylesheet" href="/HRMS/CSS/welcomeDiv.css">
<link rel="stylesheet" href="/HRMS/CSS/menu.css">
<link rel="stylesheet" href="/HRMS/CSS/punchIn.css">
<!-- <link rel="stylesheet" href="/HRMS/CSS/content.css"> -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="/HRMS/JS/jquery-3.3.1.js"></script>
<script type="text/javascript" src="/HRMS/JS/punchIn.js"></script>
<title>PunchIn</title>
</head>
<body>
<form id="form" method="post" action="punchIn.do"></form>
	<div id="welcomeDiv">
		<div id="welcomeDivImage">
			<img id="welcomeImage" alt=""
				src="/HRMS/Images/HRMS/human_resource_management_crop.jpg">
		</div>
	</div>

	<div id="menu">
		<div id="leftMenu">
			<div class="leftMenuItems menuItems"
				onclick="window.location='attendanceManagement'">Back</div>
		</div>
		<div id="rightMenu">
			<div class="rightMenuItems menuItems" onclick="window.location='signOut.do'">Sign
				Out</div>
			<div class="rightMenuItems menuItems">Welcome ${sessionScope.firstName}</div>
		</div>
	</div>
	<div id="contentDiv">
		<div id="punchInDiv">
			<div id="dateDiv">
				<input type="date" id="datepicker" class="unstyled" />
			</div>
			<div id="timerDiv">
				<div class="timerDivs">
					<div class="punchButtons" id="mPunchIn">Punch In</div>
					<div class="pMessages" id="mPunchInMsg">00:00</div>
				</div>
			</div>
		</div>
		<div id="punchRecordsDiv">
			<c:forEach items="${attendances}" var="attendance">
				<div class="punchRecords">Date : ${attendance.attendanceDate}, WorkHours :
					${attendance.workHoursString}</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>