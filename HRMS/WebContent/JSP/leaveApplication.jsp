<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/HRMS/CSS/body.css">
<link rel="stylesheet" href="/HRMS/CSS/welcomeDiv.css">
<link rel="stylesheet" href="/HRMS/CSS/menu.css">
<link rel="stylesheet" href="/HRMS/CSS/leaveApplication.css">
<title>Insert title here</title>
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
			<div class="leftMenuItems menuItems" onclick="window.location='applyForLeave.do'">
				Apply For Leave</div>
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
</body>
</html>