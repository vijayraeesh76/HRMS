<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/HRMS/CSS/body.css">
<link rel="stylesheet" href="/HRMS/CSS/welcomeDiv.css">
<link rel="stylesheet" href="/HRMS/CSS/menu.css">
<link rel="stylesheet" href="/HRMS/CSS/manageAccount.css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<div class="leftMenuItems"
				onclick="window.location='updateAccount.jsp'">Update Account</div>
			<div class="leftMenuItems"
				onclick="window.location='changePassword.jsp'">Change Password</div>
			<div class="leftMenuItems" id="store">Delete Account</div>
		</div>
		<div id="rightMenu">
			<div class="rightMenuItems" onclick="window.location='signOut.do'">Sign
				Out</div>
			<div class="rightMenuItems" onclick="window.location='home'">Home</div>
		</div>
	</div>

</body>
</html>