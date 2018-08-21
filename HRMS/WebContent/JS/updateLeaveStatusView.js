$(document).ready(function() {
	$("#msgDiv").hide();
})

function updateLeave(loopIndex) {

	var firstName = $("#firstName" + loopIndex).html();
	var empID = $('#empID' + loopIndex).html();
	var leaveDate = $('#leaveDate' + loopIndex).html();
	var leaveStatus = $('#leaveStatus' + loopIndex).val();
	var eligibleLeaveDays = $('#eligibleLeaveDays' + loopIndex).html();
	var reason = $('#reason' + loopIndex).val();
	var comment = $('#comment' + loopIndex).val();

	console.log("firstname - " + firstName + " empID - " + empID
			+ " leaveDate - " + leaveDate + " leaveStatus - " + leaveStatus
			+ " eligibleLeaveDays - " + eligibleLeaveDays + " reason - " +reason + " comment - " + comment);

	$.post("updateLeaveStatus.do", {
		firstname : firstName,
		empID : empID,
		leaveDate : leaveDate,
		leaveStatus : leaveStatus,
		eligibleLeaveDays : eligibleLeaveDays,
		comment:comment
	}, function(data) {
		var obj = jQuery.parseJSON(data);
		var status = obj.status;
		var message = obj.message;
		if(status=="Success"){
			showStatus(message);
			console.log("Before redirect");
			setTimeout(function() {  
				console.log("Inside redirect");
					$("#form").submit();
				}, 3000);
			
			//window.location.href = "employeeLeaveStatus.do";
		}else{
			showStatus(message);
		}
	});
	
	function showStatus(msg){
		console.log("msg : " + msg);
		$("#msgDiv").html(msg);
		$("#msgDiv").show().delay(3000).fadeOut();
	}
}