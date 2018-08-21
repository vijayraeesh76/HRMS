$(document).ready(function() {

	$("#submit").click(function(event) {
		
		var firstname1 = $('#firstName').val();
		var lastname1 = $('#lastName').val();
		
		console.log(" firstName - " + firstname1 + " lastName - " + lastname1);
		
		$.post("updateAccount.do", {
			firstName:firstname1,
			lastName:lastname1
		}, function(data) {
			if(data=="Status:Success"){
				window.location.href = "updateAccount.jsp";
			}
		});

	});

});