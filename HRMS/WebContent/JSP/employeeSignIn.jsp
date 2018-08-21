<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/HRMS/CSS/body.css">
<link rel="stylesheet" href="/HRMS/CSS/welcomeDiv.css">
<link rel="stylesheet" href="/HRMS/CSS/menu.css">
<link rel="stylesheet" href="/HRMS/CSS/employeeSignIn.css">
<link rel="stylesheet" href="/HRMS/CSS/content.css">
<script type="text/javascript" src="/HRMS/JS/jquery-3.3.1.js"></script>
<script type="text/javascript" src="/HRMS/JS/employeeSignIn.js"></script>
<title>Employee SignIn</title>
</head>
<body>
	<div id="welcomeDiv">
		<div id="welcomeDivImage">
			<img id="welcomeImage" alt=""
				src="/HRMS/Images/HRMS/human_resource_management_crop.jpg">
		</div>
	</div>
	<div id="menu">
		<div class="rightMenuItems menuItems" id="menuHome" onClick="window.location='home'">Home</div>
	</div>
	<div id="signIn" class="contentDiv">
		<div id="userNameDiv" class="contentFirstChild">
			<div class="spanDiv contentSecondChilda" >
				<span>Username : </span>
			</div>
			<div class="textInputDiv contentSecondChildb">
				<input type="text" id="username" class="textInput">
			</div>
		</div>
		<div id="passwordDiv" class="contentFirstChild">
			<div class="spanDiv contentSecondChilda">
				<span>Password : </span>
			</div>
			<div class="textInputDiv contentSecondChildb">
				<input type="password" id="password" class="textInput">
			</div>
		</div>
		<hr>
		<div id="submitDiv">
			<input type="button" id="submit" value="Submit" class="submit">
		</div>
	</div>
</body>
</html>