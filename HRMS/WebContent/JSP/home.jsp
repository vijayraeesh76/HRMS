<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/HRMS/CSS/body.css">
<link rel="stylesheet" href="/HRMS/CSS/welcomeDiv.css">
<link rel="stylesheet" href="/HRMS/CSS/menu.css">
<script type="text/javascript" src="/HRMS/JS/home.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta charset="ISO-8859-1">
<title>Dosa Corner</title>
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
			<c:choose>
				<c:when test="${sessionScope.isHR=='TRUE'}">
					<div class="leftMenuItems menuItems"
						onclick="window.location='projectManagementView'">Project
						Management</div>
					<div class="leftMenuItems menuItems"
						onclick="window.location='updateLeaveStatusView.do'">Update
						Leave's Status</div>
				</c:when>
				<c:when test="${sessionScope.isSysAdmin=='TRUE'}">
					<div class="leftMenuItems menuItems"
						onclick="window.location='adminRegisterView.do'">Register New User</div>
				</c:when>
				<c:when test="${sessionScope.isLeaveApplierAndApprover=='TRUE'}">
					<div class="leftMenuItems menuItems"
						onclick="window.location='attendanceManagement'">Attendance
						Management</div>
					<div class="leftMenuItems menuItems"
						onclick="window.location='leaveManagementView'">Leave
						Management</div>
				</c:when>
				<c:when test="${sessionScope.isLeaveApplier=='TRUE'}">
					<div class="leftMenuItems menuItems"
						onclick="window.location='attendanceManagement'">Attendance
						Management</div>
					<div class="leftMenuItems menuItems"
						onclick="window.location='leaveApplication'">Leave
						Application</div>
				</c:when>
				<c:when test="${sessionScope.isLeaveApprover=='TRUE'}">
					<div class="leftMenuItems menuItems"
						onclick="window.location='updateLeaveStatusView.do'">Update
						Leave's Status</div>
				</c:when>
			</c:choose>
		</div>
		<div id="rightMenu">
			<c:choose>
				<c:when test="${sessionScope.firstName!=null}">
					<div class="rightMenuItems menuItems" onclick="window.location='signOut.do'">Sign
						Out</div>
					<div class="rightMenuItems menuItems"
						onclick="window.location='manageAccount.jsp'">Manage Account</div>
					<div class="rightMenuItems menuItems">Welcome ${sessionScope.firstName}</div>
				</c:when>
				<c:otherwise>
					<div class="rightMenuItems menuItems"
						onclick="window.location='employeeSignIn'">SignIn</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>