function redirectHome() {
	location.href = "/DosaCorner/HTML/home.html";
}

$(document).ready(function() {

	$("#submit").click(function(event) {

		var username1 = $('#username').val();
		var password1 = $('#password').val();

		console.log(" username - " + username1 + " password - " + password);

		$.post("employeeSignIn.do", {
			username : username1,
			password : password1
		}, function(data) {
			if (data == "Status:Success") {
				window.location.href = "home";
			}
		});

	});

	// Submit form on enter click
	
	// Get the password input field
	var passwordInput = document.getElementById("password");

	// Execute a function when the user releases a key on the keyboard
	passwordInput.addEventListener("keyup", function(event) {
		// Cancel the default action, if needed
		event.preventDefault();
		// Number 13 is the "Enter" key on the keyboard
		if (event.keyCode === 13) {
			// Trigger the button element with a click
			document.getElementById("submit").click();
		}
	});

});