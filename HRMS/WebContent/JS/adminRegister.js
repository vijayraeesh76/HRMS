$(document).ready(function() {
	$("#adminRegisterMsg").hide();

	$("#submit").click(function(event) {
		
		var firstname1 = $('#firstname').val();
		var lastname1 = $('#lastname').val();
		var username1 = $('#username').val();
		var password1 = $('#password').val();
		var designation1 = $('#employeeType').val();
		var doj1 = $('#datePicker').val();
		
		console.log("firstname - " + firstname1 + " lastname - " + lastname1 + " username - " + username1 + " password - " + password);
		
		$.post("adminRegister.do", {
			firstname : firstname1,
			lastname: lastname1,
			username:username1,
			password:password1,
			designation:designation1,
			doj:doj1
		}, function(data) {
			var obj = jQuery.parseJSON(data);
			var status = obj.status;
			var message = obj.message;
			if(status=="Success"){
				var msg = "User successfully registered!";
				showStatus(msg);
				
				setTimeout(function() {  
					console.log("Inside redirect");
						$("#form").submit();
					}, 3000);
			}else{
				showStatus(message);
			}
		});

	});
	
	function showStatus(msg){
		console.log("msg : " + msg);
		$("#adminRegisterMsg").html(msg);
		$("#adminRegisterMsg").show().delay(3000).fadeOut();
	}

});