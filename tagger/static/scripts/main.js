console.log("IT'S ALIVE");

var pageURL = window.location.href;
var appURL = pageURL.replace("/index.html","/");
var appURL = appURL.replace("#","");

var currentPic={};

var tagList= [];

document.addEventListener('DOMContentLoaded', function(){
	getTagList();
	getPicture();
	
});

function getPicture(){
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200){
			currentPic = (JSON.parse(xhttp.responseText));
			generatePic();
			checkBoxes();
			loadAndDisplayCompletion();
		}
	};
	xhttp.open("GET",appURL+"image", true);
	xhttp.send();
}

function loadAndDisplayCompletion(){
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200){
			document.getElementById("completion").innerHTML = xhttp.responseText +" %";
		}
	};
	xhttp.open("GET",appURL+"completion", true);
	xhttp.send();
}

function getTagList(){
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200){
			tagList = (JSON.parse(xhttp.responseText));
			generateForm();
		}
	};
	xhttp.open("GET",appURL+"tags", true);
	xhttp.send();
}

function generatePic(){
	var picId = currentPic.id;
	var imageNode = document.getElementById("pic");
	var url = appURL + "image/" +picId;
	imageNode.src = url;
}

function generateForm(){
	var formContainer = document.getElementById("form");
	tagList.forEach(function(tag){
		var rowDiv = document.createElement("div");
		rowDiv.className = "row";
		var inputNode = document.createElement("input");
		inputNode.type = "checkbox";
		inputNode.value=tag;
		rowDiv.appendChild(inputNode);
		rowDiv.appendChild(document.createTextNode(" "+tag));
		formContainer.appendChild(rowDiv);
	});
	var buttonDiv = document.createElement("div");
	buttonDiv.className="row";
	var buttonNode = document.createElement("button");
	buttonNode.innerHTML="Next";
	buttonDiv.appendChild(buttonNode);
	buttonDiv.addEventListener("click",handleImageTagged);
	formContainer.appendChild(buttonDiv);
}

function handleImageTagged(){
	var selectedTags = [];
	var inputs = document.getElementsByTagName("input");
	for ( var i = 0 ; i < inputs.length ; i ++ ){
		if ( inputs[i].checked){
			selectedTags.push(inputs[i].value);
		}
	}
	currentPic.tags = selectedTags;
	//clearForm();
	sendTaggedImage();
	getPicture();
}

function clearForm(){
	var selectedTags = [];
	var inputs = document.getElementsByTagName("input");
	for ( var i = 0 ; i < inputs.length ; i ++ ){
		inputs[i].checked= false;
	}
}

function sendTaggedImage(){
	console.log("TODO : send image");
	var xhttp = new XMLHttpRequest();
	xhttp.open("PUT",appURL+"image", false);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send(JSON.stringify(currentPic));
	
}

function checkBoxes(){
	var inputs = document.getElementsByTagName("input");
	for ( var i = 0 ; i < inputs.length ; i ++ ){
		var inputToBeChecked = false;
		for ( var j = 0 ; j < currentPic.tags.length ; j ++ ){
			if ( currentPic.tags[j] == inputs[i].value ){
				inputToBeChecked = true;
			}
		}
		inputs[i].checked= inputToBeChecked;		
	}
}