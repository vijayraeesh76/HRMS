<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/HRMS/CSS/body.css">
<link rel="stylesheet" href="/HRMS/CSS/welcomeDiv.css">
<link rel="stylesheet" href="/HRMS/CSS/menu.css">
<link rel="stylesheet" href="/HRMS/CSS/adminRegister.css">
<link rel="stylesheet" href="/HRMS/CSS/content.css">
<script type="text/javascript" src="/HRMS/JS/jquery-3.3.1.js"></script>
<script type="text/javascript" src="/HRMS/JS/adminRegister.js"></script>
<title>Insert title here</title>
</head>
<body>
<form id="form" method="post" action="adminRegister"></form>
	<div id="welcomeDiv">
		<div id="welcomeDivImage">
			<img id="welcomeImage" alt=""
				src="/HRMS/Images/HRMS/human_resource_management_crop.jpg">
		</div>
	</div>
	<div id="menu">
		<div id="leftMenu">
			<div class="leftMenuItems"
				onclick="window.location='adminRegister'">Register New User</div>
		</div>
		<div id="rightMenu">
			<div class="rightMenuItems" onclick="window.location='signOut.do'">Sign
				Out</div>
			<div class="rightMenuItems">Hi ${sessionScope.firstName}</div>
		</div>
	</div>

	<div id="register" class="contentDiv">
		<div id="firstnameDiv" class="registerDivs contentFirstChild">
			<div class="spanDiv contentSecondChilda">
				<span>FirstName : </span>
			</div>
			<div class="textInputDiv contentSecondChildb">
				<input id="firstname" type="text" class="textInput" placeholder="Firstname">
			</div>
		</div>
		<div id="lastnameDiv" class="registerDivs contentFirstChild">
			<div class="spanDiv contentSecondChilda">
				<span>LastName : </span>
			</div>
			<div class="textInputDiv contentSecondChildb">
				<input id="lastname" type="text" class="textInput" placeholder="Lastname">
			</div>
		</div>
		<div id="employeeTypeDiv" class="registerDivs contentFirstChild">
			<div class="spanDiv contentSecondChilda">
				<span>Designation : </span>
			</div>
			<div class="textInputDiv contentSecondChildb">
				<select id="employeeType">
					<option value="HR">HR</option>
					<option value="PROJECT_MANAGER">PROJECT MANAGER</option>
					<option value="TECH_LEADER">TECH LEADER</option>
					<option value="TEAM_LEADER">TEAM LEADER</option>
					<option value="SOFTWARE_ENGINEER">SOFTWARE ENGINEER</option>
					<option value="TRAINEE">TRAINEE</option>
				</select>
			</div>
		</div>
		<div id="dojDiv" class="registerDivs contentFirstChild">
			<div class="spanDiv contentSecondChilda">
				<span>Date of Joining : </span>
			</div>
			<div class="textInputDiv contentSecondChildb">
				<input type="date" id="datePicker" />
			</div>
		</div>
		<div id="userNameDiv" class="registerDivs contentFirstChild">
			<div class="spanDiv contentSecondChilda">
				<span>Username : </span>
			</div>
			<div class="textInputDiv contentSecondChildb">
				<input id="username" type="text" class="textInput" placeholder="Username">
			</div>
		</div>
		<div id="passwordDiv" class="registerDivs contentFirstChild">
			<div class="spanDiv contentSecondChilda">
				<span>Password : </span>
			</div>
			<div class="textInputDiv contentSecondChildb">
				<input id="password" type="password" class="textInput" placeholder="Password">
			</div>
		</div>
		<hr>
		<div id="submitDiv" class="submitDiv">
			<input type="button" value="Submit" id="submit" class="submit">
		</div>
	</div>

	<div id="adminRegisterMsg" class="msgDiv"></div>
</body>
</html>