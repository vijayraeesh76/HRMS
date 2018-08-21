$(document).ready(
		function() {

			var debug = 'TRUE';

			var datepicker = $('#datepicker');

			// disable arrow buttons in datepicker

			var datepickerForEvent = document.getElementById('datepicker');
			datepickerForEvent.addEventListener('keydown', function(event) {
				if (event.keyIdentifier == "Down") {
					event.preventDefault()
				}
			}, false);

			// format date for input
			function formatDate(date) {
				var monthNames = [ "01", "02", "03", "04", "05", "06", "07",
						"08", "09", "10", "11", "12" ];

				var day = date.getDate();
				day = "" + day;
				// console.log("day before - " +day );
				if (day.length == 1) {
					// console.log("manipulation");
					day = "0" + day
				}
				// console.log("day after - " +day );
				var monthIndex = date.getMonth();
				var year = date.getFullYear();

				return year + '-' + monthNames[monthIndex] + '-' + day;
			}

			datepicker.val(formatDate(new Date()));

			// make datepicker readonly
			$('#datepicker').attr("readonly", "readonly");

			// Morning punch in
			$("#mPunchIn").click(function() {

				if (debug == 'FALSE') {
					var existingPunchRecord = $("#mPunchInMsg").html();
					if (existingPunchRecord != '00:00') {
						return;
					}
				}

				var punchTimeHours = new Date().getHours();
				var punchTimeMinutes = new Date().getMinutes();
				var punchDate = $("#datepicker").val();

				/*
				 * console.log("punched in : " + punchTimeHours + " " +
				 * punchTimeMinutes + " " + punchAction + " " + punchSession);
				 */

				$.post("attendanceRecorder.do", {
					punchTimeHours : punchTimeHours,
					punchTimeMinutes : punchTimeMinutes,
					punchDate : punchDate
				}, function(data) {
					var obj = jQuery.parseJSON(data);
					var status = obj.status;
					var message = obj.message;
					if (status == "Success") {
						showStatus(message);
						console.log("Before redirect");
						setTimeout(function() {
							console.log("Inside redirect");
							$("#form").submit();
						}, 3000);

						//window.location.href = "employeeLeaveStatus.do";
					} else {
						showStatus(message);
					}
				});
			});

			function showStatus(msg) {
				console.log("msg : " + msg);
				$("#msgDiv").html(msg);
				$("#msgDiv").show().delay(3000).fadeOut();
			}
		})

$(document).ready(function() {

	// Display digital clock
	function startTime() {
		var today = new Date();
		var h = today.getHours();
		var m = today.getMinutes();
		var s = today.getSeconds();
		var time = "00:00";
		m = checkTime(m);
		s = checkTime(s);
		time = h + ":" + m + ":" + s;
		$('#mPunchInMsg').html(time);
		var t = setTimeout(startTime, 500);
	}

	function checkTime(i) {
		if (i < 10) {
			i = "0" + i
		}
		; // add zero in front of numbers < 10
		return i;
	}

	startTime();
})