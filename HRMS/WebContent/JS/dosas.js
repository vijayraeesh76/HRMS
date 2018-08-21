function dosaSelected(variety){
	document.getElementById("dosas").style.visibility = "visible";
	
	if(variety=="veg"){
		document.getElementById("vegDosasContainer").style.visibility = "visible";
		document.getElementById("nonvegDosasContainer").style.visibility = "hidden";
		document.getElementById("specialDosasContainer").style.visibility = "hidden";
		
		document.getElementById("vegDosasContainer").style.removeProperty('position');
		document.getElementById("nonvegDosasContainer").style.position = "absolute";
		document.getElementById("specialDosasContainer").style.position = "absolute";
	}else if(variety=="nonveg"){
		document.getElementById("vegDosasContainer").style.visibility = "hidden";
		document.getElementById("nonvegDosasContainer").style.visibility = "visible";
		document.getElementById("specialDosasContainer").style.visibility = "hidden";
		
		document.getElementById("vegDosasContainer").style.position = "absolute";
		document.getElementById("nonvegDosasContainer").style.removeProperty('position');
		document.getElementById("specialDosasContainer").style.position = "absolute";
	}else if(variety=="special"){
		document.getElementById("vegDosasContainer").style.visibility = "hidden";
		document.getElementById("nonvegDosasContainer").style.visibility = "hidden";
		document.getElementById("specialDosasContainer").style.visibility = "visible";
		
		document.getElementById("vegDosasContainer").style.position = "absolute";
		document.getElementById("nonvegDosasContainer").style.position = "absolute";
		document.getElementById("specialDosasContainer").style.removeProperty('position');
	}
}

function redirectHome(){
	location.href = "/DosaCorner/HTML/home.html";
}