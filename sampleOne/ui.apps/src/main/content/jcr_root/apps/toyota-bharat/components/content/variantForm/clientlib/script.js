window.onload = function() {

	var selectedmodel = document.getElementById("modelname");
	var selectmodeltype = document.getElementById("modeltype");	
	var seater = document.getElementById("seater");
	var transmission = document.getElementById("transmission");
	
	//Fetching selected values from drop-down
	var selectedModel = selectedmodel.options[selectedmodel.selectedIndex].value;	
	var selectedSeater = seater.options[seater.selectedIndex].value;
	var selectedTransmission = transmission.options[transmission.selectedIndex].value;
	
	//Based on the selected Model, fetch it's Fuel Type and Populate the Model Type Field on Form
	selectedmodel.onchange = function() {
		var selectedModel = selectedmodel.options[selectedmodel.selectedIndex].value;
		var selectmodeltype = document.getElementById("modeltype");
		var option = [];
		document.getElementById("modeltype").options.length = 0;
		//Calling Servlet i.e. FindRelatedFuelTypes, to fetch model types related to selected model
		   $.ajax({
	            type: 'POST',    
	            url:'/bin/findRelatedFuelTypes',
	            data:'selectedModel='+ selectedModel,
	            success: function(msg){
	            	var result = msg.substring(1, msg.length-1);
	            	var str = result;
	            	var str_array = str.split(',');

	            	for(var i = 0; i < str_array.length; i++) {
	            	   // Trim the excess whitespace.
	            	   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
	            	    var x = selectmodeltype;
	            	    option = document.createElement("option");
	            	    option.text = str_array[i];
	            	    x.add(option);
	            	}
	            }
	        });
	};
	
	//On Form Submission
	$('#modelVariantForm').submit(function() {
		var selectedModel = selectedmodel.options[selectedmodel.selectedIndex].value;
		var selectedModelType = selectmodeltype.options[selectmodeltype.selectedIndex].value;
		var selectedSeater = seater.options[seater.selectedIndex].value;
		var selectedTransmission = transmission.options[transmission.selectedIndex].value;
		var grade = document.getElementById("grade").value;
		//Calling Servlet i.e. VariantFormData (Business Logic)
		   $.ajax({
	            type: 'POST',    
	            url:'/bin/variantFormData',
	            data:'selectedModel='+ selectedModel+'&selectedModelType='+ selectedModelType+'&selectedSeater='+ selectedSeater+'&selectedTransmission='+ selectedTransmission+'&grade='+ grade,
	            success: function(msg){
	            	alert("S U C C E S S");
	            }
	        });
		
	    });
}