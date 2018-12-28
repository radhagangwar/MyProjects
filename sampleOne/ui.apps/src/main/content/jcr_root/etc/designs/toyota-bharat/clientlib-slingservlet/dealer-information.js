$(document).ready(function () {

    var jsonData;

    //Function for fetching states
    $(function () {
        var failure = function (err) {
            console.log("Unable to retrive data " + err);
        };
        $.ajax({
            type: 'GET',
            url: '/bin/fetchStatesCities',
            success: function (msg) {
                jsonData = jQuery.parseJSON(msg),
                    $.each(jsonData, function (key, value) {
                        $('#state').append($('<option>').text(key).attr('value', key));
                    });
            }
        });
    });

    //Function for fetching facilities
    $(function () {
        var failure = function (err) {
            console.log("Unable to retrive data " + err);
        };
        $.ajax({
            type: 'GET',
            url: '/bin/getFacility',
            success: function (msg) {
                facilityJson = jQuery.parseJSON(msg),
                    $.each(facilityJson, function (key, value) {
                        $('#facility').append($('<option>').text(value).attr('value', key));
                    });
            }
        });
    });

    //Function to load cities as states is selected
    $('#state').change(function () {
        $('#city').empty().append($('<option>').text("Select City").attr('value',""));
        var stateValue = this.value;
        $.each(jsonData, function (key, value) {
            if (stateValue === key) {
                $.each(value, function (index, arrvalue) {
                    $('#city').append($('<option>').text(arrvalue).attr('value', arrvalue));
                });
            }
        });
    });
    
    //Function to validate data is entered
    $('form').validate({
        onfocusout: function(e) {
            this.element(e);
        }
    });

    //Function for sending the dealer data to sling servlet
    $('#submit').click(function () {
    	
    	var validateData = false;

        var failure = function (err) {
            console.log("Unable to retrive data " + err);
        };

        // Get the user-defined values
        var state = $('#state').val(),
            city = $('#city').val(),
            dealerName = $('#dealerName').val(),
            dealerStreetAdd = $('#street').val(),
            dealerArea = $('#area').val(),
            pincode = $('#pincode').val(),
            facility = $('#facility').val(),
            website = $('#website').val(),
            phone = $('#phone').val().replace(/\+/g,"%2B").replace(/\s/g, ""),
            fax = $('#fax').val().replace(/\+/g,"%2B").replace(/\s/g, ""),
            service = $('#service').val().replace(/\+/g,"%2B").replace(/\s/g, ""),
            helpline = $('#helpline').val().replace(/\+/g,"%2B").replace(/\s/g, "");
        
        if (!!state && !!dealerName && !!city && !!dealerStreetAdd && !!dealerArea && !!pincode && !!phone)
        	this.validateData = true;
        else
        	{alert("There are error in form. Please go through the fields to get the required fields and enter data")}
        
        if (this.validateData == true){
        	$.ajax({
                type: 'POST',
                url: '/bin/setDealerInfo',
                data: 'state=' + state + '&city=' + city + '&dealerName=' + dealerName + '&dealerArea=' + dealerArea + '&dealerStreetAdd=' + dealerStreetAdd + '&pincode=' + pincode + '&facility=' + facility + '&phone=' + phone + '&fax=' + fax + '&service=' + service + '&helpline=' + helpline + '&website=' + website,
                success: function (msg) {
                    console.log(msg)
                }
            });
        }
        
    });
});
