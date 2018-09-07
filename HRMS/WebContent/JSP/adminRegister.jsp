<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
			<div class="leftMenuItems" onclick="window.location='adminRegister'">Register
				New User</div>
		</div>
		<div id="rightMenu">
			<div class="rightMenuItems" onclick="window.location='signOut.do'">Sign
				Out</div>
			<div class="rightMenuItems">Hi ${sessionScope.firstName}</div>
		</div>
	</div>

	<div id="register" class="contentDiv">
		<form:form method="POST" action="adminRegister.do" modelAttribute="employee">
			<div id="firstnameDiv" class="registerDivs contentFirstChild">
				<div class="spanDiv contentSecondChilda">
					<span>FirstName : </span>
				</div>
				<div class="textInputDiv contentSecondChildb">
					<form:input id="firstname" type="text" class="textInput"
						placeholder="Firstname" path="firstName"></form:input>
				</div>
			</div>
			<div id="lastnameDiv" class="registerDivs contentFirstChild">
				<div class="spanDiv contentSecondChilda">
					<span>LastName : </span>
				</div>
				<div class="textInputDiv contentSecondChildb">
					<form:input id="lastname" type="text" class="textInput"
						placeholder="Lastname" path="lastName"></form:input>
				</div>
			</div>
			<div id="employeeTypeDiv" class="registerDivs contentFirstChild">
				<div class="spanDiv contentSecondChilda">
					<span>Designation : </span>
				</div>
				<div class="textInputDiv contentSecondChildb">
					<form:select id="employeeType" path="designation">
						<option value="HR">HR</option>
						<option value="PROJECT_MANAGER">PROJECT MANAGER</option>
						<option value="TECH_LEADER">TECH LEADER</option>
						<option value="TEAM_LEADER">TEAM LEADER</option>
						<option value="SOFTWARE_ENGINEER">SOFTWARE ENGINEER</option>
						<option value="TRAINEE">TRAINEE</option>
					</form:select>
				</div>
			</div>
			<div id="dojDiv" class="registerDivs contentFirstChild">
				<div class="spanDiv contentSecondChilda">
					<span>Date of Joining : </span>
				</div>
				<div class="textInputDiv contentSecondChildb">
					<form:input type="date" id="datePicker" path="dojString"/>
				</div>
			</div>
			<div id="userNameDiv" class="registerDivs contentFirstChild">
				<div class="spanDiv contentSecondChilda">
					<span>Username : </span>
				</div>
				<div class="textInputDiv contentSecondChildb">
					<form:input id="username" type="text" class="textInput"
						placeholder="Username" path="userName"/>
				</div>
			</div>
			<div id="passwordDiv" class="registerDivs contentFirstChild">
				<div class="spanDiv contentSecondChilda">
					<span>Password : </span>
				</div>
				<div class="textInputDiv contentSecondChildb">
					<form:input id="password" type="password" class="textInput"
						placeholder="Password" path="password"/>
				</div>
			</div>
			<hr>
			<div id="submitDiv" class="submitDiv">
				<input type="submit" value="Register">
			</div>
		</form:form>
	</div>

	<div id="adminRegisterMsg" class="msgDiv"></div>
</body>
</html>