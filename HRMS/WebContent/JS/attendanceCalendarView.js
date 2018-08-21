$(document).ready(function() {
	
	if(presentDays == null){
		presentDays=[];
	}
	
	if(rejectedDays == null){
		rejectedDays=[];
	}
	
	if(approvedDays == null){
		approvedDays=[];
	}
	
	if(waitingDays == null){
		waitingDays=[];
	}
	
	if(pendingDays == null){
		pendingDays=[];
	}
	
	var javaDays = presentDays;
	var jsDays = [];
	for (i = 0; i < javaDays.length; i++) {
	    /* console.log("presentDay : " + javaDays[i].year); */
	    jsDays[i] = new Date(javaDays[i].year,javaDays[i].month-1,javaDays[i].day);
	}
	presentDays = jsDays;

	var javaDays = rejectedDays;
	var jsDays = [];
	for (i = 0; i < javaDays.length; i++) {
	    /* console.log("presentDay : " + javaDays[i].year); */
	    jsDays[i] = new Date(javaDays[i].year,javaDays[i].month-1,javaDays[i].day);
	}
	rejectedDays = jsDays;

	var javaDays = approvedDays;
	var jsDays = [];
	for (i = 0; i < javaDays.length; i++) {
	    /* console.log("presentDay : " + javaDays[i].year); */
	    jsDays[i] = new Date(javaDays[i].year,javaDays[i].month-1,javaDays[i].day);
	}
	approvedDays = jsDays;

	var javaDays = waitingDays;
	var jsDays = [];
	for (i = 0; i < javaDays.length; i++) {
	    /* console.log("presentDay : " + javaDays[i].year); */
	    jsDays[i] = new Date(javaDays[i].year,javaDays[i].month-1,javaDays[i].day);
	}
	waitingDays = jsDays;

	var javaDays = pendingDays;
	var jsDays = [];
	for (i = 0; i < javaDays.length; i++) {
	    /* console.log("presentDay : " + javaDays[i].year); */
	    jsDays[i] = new Date(javaDays[i].year,javaDays[i].month-1,javaDays[i].day);
	}
	pendingDays = jsDays;

	// highlight days
	jQuery('#calendarDiv').datepicker({
		beforeShowDay : function(date) {
			
			var currentDate = new Date();
			
			// highlight present days
			if (presentDays.toString().indexOf(date) != -1) {
				return [ true, "present", "Present" ];
			} else if (approvedDays.toString().indexOf(date) != -1) {
				return [ true, "approved", "Leave Approved" ];
			} else if ((date.getTime() < currentDate.getTime()) && (rejectedDays.toString().indexOf(date) != -1)) {
				return [ true, "rejected", "Leave Rejected, Absent: Loss of Pay" ];
			} else if (waitingDays.toString().indexOf(date) != -1) {
				return [ true, "waiting", "Leave Action Waiting" ];
			} else if (pendingDays.toString().indexOf(date) != -1) {
				return [ true, "pending", "Leave Action Pending" ];
			} else if ((date.getTime() < currentDate.getTime()) && (date.getDay() == 6 || date.getDay() == 0)) {
				return [ true, "holiday", "Holiday" ];
			} else if (date.getTime() < currentDate.getTime()){
				return [ true, "lossOfPay", "Absent: Loss of Pay" ];
			}else{
				return [ true, '', '' ];
			}
		}
	});

});