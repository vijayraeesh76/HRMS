function redirectSignIn(){
	location.href = "/DosaCorner/HTML/signIn.html";
}

function scroller(ad){
	
	/*document.getElementById("ad1").style.visibility = "visible";
	document.getElementById("ad2").style.visibility = "hidden";
	document.getElementById("ad3").style.visibility = "hidden";*/
	
	if(ad=="ad1"){
		document.getElementById("ad1").style.visibility = "visible";
		document.getElementById("ad2").style.visibility = "hidden";
		document.getElementById("ad3").style.visibility = "hidden";
		
		document.getElementById("ad1").style.position = "relative";
		document.getElementById("ad2").style.position = "absolute";
		document.getElementById("ad3").style.position = "absolute";
	}else if(ad=="ad2"){
		document.getElementById("ad1").style.visibility = "hidden";
		document.getElementById("ad2").style.visibility = "visible";
		document.getElementById("ad3").style.visibility = "hidden";
		
		document.getElementById("ad1").style.position = "absolute";
		document.getElementById("ad2").style.position = "relative";
		document.getElementById("ad3").style.position = "absolute";
	}else if(ad=="ad3"){
		document.getElementById("ad1").style.visibility = "hidden";
		document.getElementById("ad2").style.visibility = "hidden";
		document.getElementById("ad3").style.visibility = "visible";
		
		document.getElementById("ad1").style.position = "absolute";
		document.getElementById("ad2").style.position = "absolute";
		document.getElementById("ad3").style.position = "relative";
	}
}