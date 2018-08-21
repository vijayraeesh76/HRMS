<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/HRMS/CSS/body.css">
<link rel="stylesheet" href="/HRMS/CSS/welcomeDiv.css">
<link rel="stylesheet" href="/HRMS/CSS/menu.css">
<link rel="stylesheet" href="/HRMS/CSS/updateAccount.css">
<script type="text/javascript" src="/HRMS/JS/jquery-3.3.1.js"></script>
<script type="text/javascript" src="/HRMS/JS/updateAccount.js"></script>
<title>Update Account</title>
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
			<div class="leftMenuItems" onclick="window.location='manageAccount.jsp'">Back</div>
		</div>
		<div id="rightMenu">
			<div class="rightMenuItems" onclick="window.location='signOut.do'">Sign
				Out</div>
			<div class="rightMenuItems">Welcome ${sessionScope.firstName}</div>
		</div>
	</div>

	<div id="updateDiv">
		<div class="updateElements">
			<div class="updatetext">UserName:</div>
			<div class="inputDiv">
				<input type="text" value="${sessionScope.userName}" readonly="readonly">
			</div>
		</div>
		<div class="updateElements">
			<div class="updatetext">Firstname:</div>
			<div class="inputDiv">
				<input type="text" id="firstName" value="${sessionScope.firstName}">
			</div>
		</div>
		<div class="updateElements">
			<div class="updatetext">Lastname:</div>
			<div class="inputDiv">
				<input type="text" id="lastName" value="${sessionScope.lastName}">
			</div>
		</div>
		<div class="updateElements" id="submitDiv">
			<input type="submit" id="submit" value="submit">
		</div>
	</div>

</body>
</html>