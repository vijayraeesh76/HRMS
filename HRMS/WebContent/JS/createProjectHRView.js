$(document).ready(function() {
	$("#msgDiv").hide();

	$("#submit").click(function(event) {
		
		var projectName = $('#projectName').val();
		var projectManagers = $('#pmSelect').val();
		var techLeaders = $('#thlSelect').val().toString();
		var teamLeaders = $('#tlSelect').val().toString();
		var softwareEngineers = $('#seSelect').val().toString();
		var trainees = $('#trSelect').val().toString();
		
		/*console.log("Project Name : " + projectName);
		console.log("Project Managers : " + projectManagers);
		console.log("Tech Leadres : " + techLeaders.toString());
		console.log("Team Leaders : " + teamLeaders.toString());
		console.log("Software Engineers : " + softwareEngineers.toString());
		console.log("Trainees : " + trainees.toString());*/
		
		// input validations
		if(projectName==""){
			var msg = "Project Name should not be empty";
			showStatus(msg);
			return;
		}
		
		if(projectManagers=="" || techLeaders=="" || teamLeaders=="" || softwareEngineers=="" || trainees==""){
			var msg = "Please select atleast one employee from each category";
			showStatus(msg);
			return;
		}
		
		$.post("createProject.do", {
			projectName : projectName,
			projectManagers: projectManagers,
			techLeaders: techLeaders,
			teamLeaders: teamLeaders,
			softwareEngineers: softwareEngineers,
			trainees: trainees
		}, function(data) {
			var obj = jQuery.parseJSON(data);
			var status = obj.status;
			var message = obj.message;
			if(status=="Success"){
				var sucMsg = "Project Created Successfully!";
				showStatus(sucMsg);
				console.log("Before redirect");
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
		$("#msgDiv").html(msg);
		$("#msgDiv").show().delay(3000).fadeOut();
	}
});