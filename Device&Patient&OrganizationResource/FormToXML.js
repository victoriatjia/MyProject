var searchID;
function formInputsToXML(formID, type) {
    var thisform = document.getElementById(formID);
    var elements = thisform.elements;
	if ( type == "Organization" ){
		var output = '<Organization>';
		for (i = 0; i < elements.length; i++) {
			// alert(i + elements[i].name + elements[i].value);
			form_elem = elements[i];
			if (form_elem.type === "text") {
				if ( form_elem.name == "id" ){
					output += '<' + form_elem.name + ' value="NDH.' + form_elem.value + '"/><active value="true"/>';
					searchID="NDH." +form_elem.value;
				}
				if ( form_elem.name == "identifier" &&  form_elem.value != ""){
					output += '<' + form_elem.name + '><value value="' + form_elem.value + '"/></' + form_elem.name + '>';
				}
				if ( form_elem.name == "name" ){
					output += '<' + form_elem.name + ' value="' + form_elem.value + '"/>';
				}
				if ( form_elem.name == "alias" &&  form_elem.value != "" ){
					var res = form_elem.value.split(",");
					res.forEach(function(value) {
					   output += '<' + form_elem.name + ' value="' + value + '"/>';
					});
				}
				if ( form_elem.name == "address" ){
					output += '<' + form_elem.name + '>';
					output += '<line value="' +  form_elem.value + '"/>';
				}
				if ( form_elem.name == "postalCode" ){
					output += '<postalCode value="' +  form_elem.value + '"/></address>';
					output += '<contact>';
				}
				if ( form_elem.name == "given"){
					output += '<name><given value="' +  form_elem.value + '"/></name>';
				}
			}
			if (form_elem.type === "email") {
				if(form_elem.value != ""){
					output += '<telecom><system value="' + form_elem.name + '"/>';
					output +='<value value="' + form_elem.value + '"/></telecom>';
				}
			}
			
			if (form_elem.type === "number") {
				if (form_elem.name == "phone" && form_elem.value != ""){
					output += '<telecom><system value="' + form_elem.name + '"/>';
					output +='<value value="+886' + form_elem.value.slice(1) + '"/></telecom>';
				}
			}
			
			//---select
			if (form_elem.type === "select-one"  &&  form_elem.value != "--Select--") {
				var selectTitle = form_elem.options[form_elem.selectedIndex].title;
				if ( form_elem.name == "code" ){
					output += '<type><coding><system value="http://hl7.org/fhir/organization-type"/><' + form_elem.name;
					output += ' value="' + selectTitle + '"/>';	
					output += '<display value="' + form_elem.value + '"/></coding></type>';
				}
			}
		}
		output += '</contact></Organization>';
	}
	
	if ( type == "Patient" ){
		var output = '<Patient>';
		for (i = 0; i < elements.length; i++) {
			form_elem = elements[i];
			if (form_elem.type === "text") {
				if ( form_elem.name == "id" ){
					output += '<' + form_elem.name + ' value="NDH.' + form_elem.value + '"/>';
					searchID="NDH." +form_elem.value;
				}
				if ( form_elem.name == "identifier" && form_elem.value != ""){
					output += '<' + form_elem.name + '><value value="' + form_elem.value + '"/><assigner><display value="病歷號"/></assigner>';
					output += '</' + form_elem.name + '><active value="true"/>';
				}
				if ( form_elem.name == "name" ){
					output += '<' + form_elem.name + '><given value="' + form_elem.value + '"/>';
					output += '</' + form_elem.name + '>';
				}
				if ( form_elem.name == "address" && form_elem.value != ""){
					output += '<' + form_elem.name + '>';
					output += '<line value="' +  form_elem.value + '"/>';
				}
				if ( form_elem.name == "postalCode" && form_elem.value != ""){
					output += '<postalCode value="' +  form_elem.value + '"/></address>';
				}
			}
			
			if (form_elem.type === "email" && form_elem.value != "") {
				output += '<telecom><system value="' + form_elem.name + '"/>';
				output +='<value value="' + form_elem.value + '"/></telecom>';
			}
			
			if (form_elem.type === "number") {
				if (form_elem.name == "phone" && form_elem.value != ""){
					output += '<telecom><system value="' + form_elem.name + '"/>';
					output +='<value value="+886' + form_elem.value.slice(1) + '"/></telecom>';
				}
			}
			
			//---radio and checkbox
			if (form_elem.type === "radio" && form_elem.checked === true) { 
				output += '<' + form_elem.name;
				output += ' value="' + form_elem.value + '"/>';
			}
			if (form_elem.type === "date") { 
				output += '<' + form_elem.name;
				output += ' value="' + form_elem.value + '"/>';
			}
		}
		output += '</Patient>';
	}
	
	if ( type == "Device" ){
		var output = '<Device>';
		for (i = 0; i < elements.length; i++) {
			form_elem = elements[i];
			if (form_elem.type === "text") {
				if ( form_elem.name == "id" ){
					output += '<' + form_elem.name + ' value="NDH.' + form_elem.value + '"/>';
					searchID="NDH." +form_elem.value;
				}
				if ( form_elem.name == "deviceIdentifier"){
					if (form_elem.value==""){
						output += '<udi>';
					}
					if (form_elem.value!=""){
					output += '<udi><' + form_elem.name +　' value="' + form_elem.value + '"/>';
					}
				}
				if ( form_elem.name == "name"){
					output += '<' + form_elem.name + ' value="' + form_elem.value + '"/>';
					output += '</udi><status value="unknown"/>';
				}
				if ( form_elem.name == "type" ){
					output += '<' + form_elem.name + '><coding><code value="' + form_elem.value + '"/></coding>';
					output += '</' + form_elem.name + '>';
				}
				if ( form_elem.name == "model" && form_elem.value!=""){
					output += '<' + form_elem.name + ' value="' + form_elem.value + '"/>';
				}
			}
			if (form_elem.type === "datetime-local") { 
				output += '<' + form_elem.name + ' value="' + form_elem.value + ':00"/>';
			}
		}
		output += '</Device>';
	}
	//alert(output);
	postData(output, type);
	thisform.reset();
}

function csvToxml(type, arry){
	var output='';
	searchID="NDH." +arry[0];
	if(type=='Patient'){
		output+= '<Patient><id value="NDH.' + arry[0] + '"/>';
		output+= '<identifier><value value="' + arry[1] + '"/><assigner><display value="病歷號"/></assigner></identifier><active value="true"/>';
		output+= '<name><given value="' + arry[2] + '"/></name>';
		output+= '<telecom><system value="phone"/><value value="+886' + arry[3] + '"/></telecom>';
		output+= '<telecom><system value="email"/><value value="' + arry[4] + '"/></telecom>';
		output+= '<address><line value="' + arry[5] + '"/><postalCode value="' + arry[6] + '"/></address>';
		output+= '<gender value="' + arry[7] + '"/><birthDate value="' + arry[8] + '"/>';
		output+= '</Patient>';
	}
	//<managingOrganization><reference value="Organization/22793"/></managingOrganization>
	if(type=='Organization'){
		output+= '<Organization><id value="NDH.' + arry[0] + '"/>';
		output+= '<identifier><value value="' + arry[1] + '"/></identifier><active value="true"/>';
		output+= '<type><coding><system value="http://hl7.org/fhir/organization-type"/><code value="prov"/><display value="Healthcare Provider"/></coding></type>';
		output+= '<name value="' + arry[2] + '"/><alias value="' + arry[3] + '"/>';
		output+= '<address><line value="' + arry[4] + '"/><postalCode value="' + arry[5] + '"/></address>';
		output+= '<contact><name><given value="' + arry[6] + '"/></name>';
		output+= '<telecom><system value="phone"/><value value="+886' + arry[7] + '"/></telecom>';
		output+= '<telecom><system value="email"/><value value="' + arry[8] + '"/></telecom></contact></Organization>';
	}
	if(type=="Device"){
		output+= '<Device><id value="NDH.' + arry[0] + '"/>';
		output+= '<udi><deviceIdentifier value="' + arry[1] + '"/><name value="' + arry[2] + '"/></udi><status value="unknown"/>';
		output+= '<type><coding><code value="' + arry[3] + '"/></coding></type>';
		output+= '<manufactureDate value="' + arry[4] + ':00"/>';
		output+= '<model value="' + arry[5] + '"/></Device>';
	}
	//alert(output);
	postData(output, type);
}

function postData(xmlString, type) {
    var xhttp = new XMLHttpRequest();
	var api='http://hapi.fhir.org/baseDstu3/' + type + '/' +  searchID;
	alert(api);
	xhttp.open("PUT", api, true);
	xhttp.setRequestHeader("Content-type", 'text/xml');
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) // && this.status == 201) 
        {
            ret=this.responseText;	
			//alert(ret);
        }
    };
    var postData;
    postData = xmlString;
    xhttp.send(postData);
}

