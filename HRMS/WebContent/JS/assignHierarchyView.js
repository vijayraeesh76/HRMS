$(document).ready(function() {
	$("#msgDiv").hide();
	
	$('#projectSelect').on('change', function() {
		var projectId = $('#projectSelect').val();
		if(projectId==""){
			return
		}
		
		$.post("assignHierarchyFlow.do", {
			projectId:projectId
		}, function(data) {
			var obj = jQuery.parseJSON(data);
			var status = obj.status;
			var message = obj.message;
			var modelList = obj.modelList;
			if(status=="Success"){
				
				// remove all select options
				$("#designationsSelect").find('option').remove();
				
				// append new options from server
				$.each( modelList, function( key, value ) {
					  /*console.log( key + ": " + value );*/
					  $("#designationsSelect").append('<option value="'+value+'">'+value+'</option>');
				});
			}else{
				showStatus(message);
			}
		});

	});
	
	$('#designationsSelect').on('change', function() {
		// Remove existing options
		$("#designatedEmployee").find('option').remove();
		$("#designatedEmployeeSuperiors").find('option').remove();
		
		var projectId = $('#projectSelect').val();
		var designation = $('#designationsSelect').val();
		if(projectId=="" || designation==""){
			return
		}
		
		$.post("assignHierarchyFlow.do", {
			projectId:projectId,
			designation:designation
		}, function(data) {
			var obj = jQuery.parseJSON(data);
			var status = obj.status;
			var message = obj.message;
			if(status=="Success"){

				var modelList = obj.modelList;
				
				// remove all select options
				$("#designatedEmployees").find('option').remove();
				// append new options from server
				$.each( modelList, function( key, value ) {
					var firstName = modelList[key]['firstName'];
					var empID = modelList[key]['empID'];
					  /*console.log( " firstName : " +firstName);*/
					  $("#designatedEmployee").append('<option value="'+empID+'">'+firstName+'</option>');
				});
				
				// Populate Supervisors of the designated employee
				$.post("getDesignatedEMployeeSuperiors.do", {
					projectId:projectId,
					designation:designation
				}, function(data) {
					
					obj = jQuery.parseJSON(data);
					status = obj.status;
					message = obj.message;
					
					if(status=="Success"){
						
						var designatedEmployeeSuperiors = obj.modelList;
						
						// remove all select options
						$("#designatedEmployeeSuperiors").find('option').remove();
						// append new options from server
						$.each( designatedEmployeeSuperiors, function( key, value ) {
							var firstName = designatedEmployeeSuperiors[key]['firstName'];
							var empID = designatedEmployeeSuperiors[key]['empID'];
							var designation = designatedEmployeeSuperiors[key]['designation'];
							  /*console.log( " firstName : " +firstName);*/
							  $("#designatedEmployeeSuperiors").append('<option value="'+empID+'">'+firstName+' [' +designation+']'+ '</option>');
						});
					}else{
						showStatus(message);
					}
				});
			}else{
				showStatus(message);
			}
		});

	});
	
	$("#submit").click(function(event) {
		
		var project = $('#projectSelect').val();
		var designation = $('#designationsSelect').val();
		var designatedEmployees = $('#designatedEmployee').val();
		var designatedEmployeesSuperiors = $('#designatedEmployeeSuperiors').val();
		
		if(designatedEmployees=="" || designatedEmployeesSuperiors==""){
			var uiValidationMessage = "Please select all aprropriate fields";
			showStatus(uiValidationMessage);
			return;
		}
		
		console.log("project - " + project + " designatedEmployees - "
				+ designatedEmployees + " designatedEmployeesSuperiors - " + designatedEmployeesSuperiors);
		
		$.post("assignHierarchy.do", {
			designatedEmployees:designatedEmployees.toString(),
			designatedEmployeesSuperiors:designatedEmployeesSuperiors.toString()
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

	});
	
	function showStatus(msg){
		console.log("msg : " + msg);
		$("#msgDiv").html(msg);
		$("#msgDiv").show().delay(3000).fadeOut();
	}

});