<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Leave Status</title>
<link rel="stylesheet" href="/HRMS/CSS/body.css">
<link rel="stylesheet" href="/HRMS/CSS/welcomeDiv.css">
<link rel="stylesheet" href="/HRMS/CSS/menu.css">
<link rel="stylesheet" href="/HRMS/CSS/content.css">
<link rel="stylesheet" href="/HRMS/CSS/updateLeaveStatusView.css">
<link rel="stylesheet" href="/HRMS/CSS/table.css">
<script type="text/javascript" src="/HRMS/JS/jquery-3.3.1.js"></script>
<script type="text/javascript"
	src="/HRMS/JS/updateLeaveStatusView.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>
	<form id="form" method="post" action="updateLeaveStatusView.do"></form>
	<div id="welcomeDiv">
		<div id="welcomeDivImage">
			<img id="welcomeImage" alt=""
				src="/HRMS/Images/HRMS/human_resource_management_crop.jpg">
		</div>
	</div>
	<div id="menu">
		<div id="leftMenu">
			<div class="leftMenuItems menuItems" onclick="window.location='leaveManagementView'">Back</div>
		</div>
		<div id="rightMenu">
			<div class="rightMenuItems menuItems" onclick="window.location='signOut.do'">Sign
				Out</div>
			<div class="rightMenuItems menuItems">Hi ${sessionScope.firstName}</div>
		</div>
	</div>

	<div id="leavesDiv" class="contentDiv">
		<table id="leavesTable">
			<tr>
				<th>Employee Name</th>
				<th>Employee ID</th>
				<th>Leave Date</th>
				<th>Reason</th>
				<th>Leave Eligibility</th>
				<th>Comment</th>
				<th>Status</th>
				<th>Update Status</th>
			</tr>
			<c:forEach items="${leaves}" var="leave" varStatus="loop">
				<tr class="toRemove">
					<td class="toRemove" id="firstName${loop.index}">${leave.firstName}</td>
					<td class="toRemove" id="empID${loop.index}">${leave.empID}</td>
					<td class="toRemove" id="leaveDate${loop.index}">${leave.leaveDate}</td>
					<td class="toRemove"><textarea id="reason${loop.index}"
							class="reasonTextArea" maxLength="200" readonly="readonly">${leave.reason}</textarea>
					</td>
					<td class="toRemove" id="eligibleLeaveDays${loop.index}">${leave.eligibleLeaveDays}</td>
					<td class="toRemove"><textarea id="comment${loop.index}"
							class="commentTextArea" maxLength="200">${leave.comment}</textarea>
					</td>
					<td><select class="toRemove" id="leaveStatus${loop.index}">
							<c:choose>
								<c:when test="${leave.status=='WAITING'}">
									<option value="WAITING" selected="selected">WAITING</option>
								</c:when>
								<c:otherwise>
									<option value="WAITING">WAITING</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${leave.status=='APPROVED'}">
									<option value="APPROVED" selected="selected">APPROVED</option>
								</c:when>
								<c:otherwise>
									<option value="APPROVED">APPROVED</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${leave.status=='REJECTED'}">
									<option value="REJECTED" selected="selected">REJECTED</option>
								</c:when>
								<c:otherwise>
									<option value="REJECTED">REJECTED</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${leave.status=='PENDING'}">
									<option selected="selected" value="PENDING">PENDING</option>
								</c:when>
								<c:otherwise>
									<option value="PENDING">PENDING</option>
								</c:otherwise>
							</c:choose>
					</select></td>
					<td class="toRemove"><input type="submit" value="submit"
						onClick="updateLeave(${loop.index})" class="submit"></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div id="msgDiv"></div>
</body>
</html>