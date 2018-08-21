$(document).ready(function() {
	$("#applyLeaveMsg").hide();

	$("#submit").click(function(event) {
		
		var fromDate = $('#fromDate').val();
		var toDate = $('#toDate').val();
		var supervisor = $('#superiorSelect').val();
		var reason = $('#reasonTextArea').val();
		
		// appropriate date error msg
		if(fromDate=="" || toDate=="" || new Date(fromDate).getTime()>new Date(toDate).getTime()){
			var msg = "Please select appropriate date range";
			showStatus(msg);
			return;
		}
		
		// appropriate error msg
		if(fromDate == "" || reason==""){
			var msg = "Please fill all required fields";
			showStatus(msg);
			return;
		}
		
		//console.log("fromDate - " + fromDate + " toDate - " + toDate);
		
		$.post("leaveApplication.do", {
			fromDate:fromDate,
			toDate:toDate,
			supervisor:supervisor,
			reason:reason
		}, function(data) {
			var obj = jQuery.parseJSON(data);
			var status = obj.status;
			var message = obj.message;
			if(status=="Success"){
				var msg = "Leave Applied Successfully!";
				showStatus(msg);
				
				//window.location.href = "employeeLeaveStatus.do";
			}else{
				showStatus(message);
			}
		});

	});
	
	jQuery('.calendarInput').datepicker({ dateFormat: 'dd/mm/yy' });

	function showStatus(msg){
		console.log("msg : " + msg);
		$("#applyLeaveMsg").html(msg);
		$("#applyLeaveMsg").show().delay(3000).fadeOut();
	}
});