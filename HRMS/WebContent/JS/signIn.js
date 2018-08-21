function redirectHome(){
	location.href = "/DosaCorner/HTML/home.html";
}

$(document).ready(function() {

	$("#submit").click(function(event) {
		
		var username1 = $('#username').val();
		var password1 = $('#password').val();
		
		console.log(" username - " + username1 + " password - " + password);
		
		$.post("signIn.do", {
			username:username1,
			password:password1
		}, function(data) {
			if(data=="Status:Success"){
				window.location.href = "home";
			}
		});

	});

});