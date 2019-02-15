<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>jQuery accordion form with validation</title>

<link rel="stylesheet" href="../assets/demo_blue.css" type="text/css" />

<script type="text/javascript" src="../../lib/jquery.js"></script>
<script type="text/javascript" src="../../jquery.validate.js"></script>
<script type="text/javascript" src="js/jquery.maskedinput-1.0.js"></script>
<script type="text/javascript" src="js/ui.core.js"></script>
<script type="text/javascript" src="js/ui.accordion.js"></script>

<script type="text/javascript">
$(document).ready(function(){

	$("#recordClientPhone").mask("(999) 999-9999");
	$("#recordClientPhoneAlt").mask("(999) 999-9999");
	$("#recordClientZip").mask("99999");
	$("#recordPropertyZip").mask("99999");	
	$("#recordPurchaseZip").mask("99999");	

	// add * to required field labels
	$('label.required').append('&nbsp;<strong>*</strong>&nbsp;');

	// accordion functions
	var accordion = $("#stepForm").accordion(); 
	var current = 0;
	
	$.validator.addMethod("pageRequired", function(value, element) {
		var $element = $(element)
		function match(index) {
			return current == index && $(element).parents("#sf" + (index + 1)).length;
		}
		if (match(0) || match(1) || match(2)) {
			return !this.optional(element);
		}
		return "dependency-mismatch";
	}, $.validator.messages.required)
	
	var v = $("#cmaForm").validate({
		errorClass: "warning",
		onkeyup: false,
		onblur: false,
		submitHandler: function() {
			alert("Submitted, thanks!");
		}
	});
	
	// back buttons do not need to run validation
	$("#sf2 .prevbutton").click(function(){
		accordion.accordion("activate", 0);
		current = 0;
	}); 
	$("#sf3 .prevbutton").click(function(){
		accordion.accordion("activate", 1);
		current = 1;
	}); 
	// these buttons all run the validation, overridden by specific targets above
	$(".open2").click(function() {
	  if (v.form()) {
	    accordion.accordion("activate", 2);
	    current = 2;
	  }
	});
	$(".open1").click(function() {
	  if (v.form()) {
	    accordion.accordion("activate", 1);
	    current = 1;
	  }
	});
	$(".open0").click(function() {
	  if (v.form()) {
	    accordion.accordion("activate", 0);
	    current = 0;
	  }
	});
 
});
</script>

<link rel="stylesheet" type="text/css" media="screen" href="style.css" />
</head>
<body>

<div id="wrap">
<div id="main">

<h1 class="top bottom"><span>Help me</span> Buy and Sell a House</h1>
<h2>This form is quick &amp; easy to complete - in only 3 steps!</h2>
<form name="cmaForm" id="cmaForm" method="post">
<input type="hidden" name="recordRequestPrimaryServiceID" id="recordRequestPrimaryServiceID" value="100" />
<input type="hidden" name="recordClientServices" id="recordClientServices" value="1,3" />
<ul id="stepForm" class="ui-accordion-container">
	<li id="sf1"><a href='#' class="ui-accordion-link"> </a>
	<div>
	<fieldset><legend> Step 1 of 3 </legend>
	<div class="requiredNotice">*Required Field</div>
	<h3 class="stepHeader">Tell us about the property you're buying</h3>
	<label for="recordPurchaseMetRealtor" class="input required">Are you currently working with a<br />
	real estate agent? </label> &nbsp;&nbsp;No: <input name="recordPurchaseMetRealtor" type="radio" checked="checked" class="inputclass" value="0" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes: <input name="recordPurchaseMetRealtor" type="radio" class="inputclass pageRequired" value="1" title="Please choose Yes or No" />
	<div class="formspacer"></div>
	<label for="recordPurchaseTimeFrameID" class="input required">When would you like to move?</label> <select name="recordPurchaseTimeFrameID" id="recordPurchaseTimeFrameID" class="inputclass pageRequired" title="Select a Time Frame">
		<option value="">-Select-</option>
		<option value="1">Less than 3 months</option>
		<option value="2">3-6 months</option>
		<option value="3">6-9 months</option>
		<option value="4">9-12 months</option>
		<option value="5">Over 12 months</option>
	</select> <br />
	<label for="recordPurchasePriceRangeID" class="input required">Purchase price range:</label> <select name="recordPurchasePriceRangeID" id="recordPurchasePriceRangeID" class="inputclass pageRequired" title="Select a Price Range">
		<option value="">-Select-</option>
		<option value="1"></option>
		<option value="2">$75,000 - $100,000</option>
		<option value="3">$100,000 - $125,000</option>
		<option value="4">$125,000 - $150,000</option>
		<option value="5">$150,000 - $200,000</option>
		<option value="6">$200,000 - $250,000</option>
		<option value="7">$250,000 - $300,000</option>
		<option value="8">$300,000 - $350,000</option>
		<option value="9">$350,000 - $400,000</option>
		<option value="10">$400,000 - $500,000</option>
		<option value="11">$500,000 - $700,000</option>
		<option value="12">$700,000 - $900,000</option>
		<option value="13">> $900,000</option>
	</select> <br />
	<label for="recordPurchaseState" class="input required">State:</label> <select name="recordPurchaseState" id="recordPurchaseState" class="inputclass pageRequired" title="Select a State">
		<option value="">-Select-</option>
		<option value="AL">Alabama</option>
		<option value="AK">Alaska</option>
		<option value="AZ">Arizona</option>
		<option value="AR">Arkansas</option>
		<option value="CA">California</option>
		<option value="CO">Colorado</option>
		<option value="CT">Connecticut</option>
		<option value="DE">Delaware</option>
		<option value="DC">Dist of Columbia</option>
		<option value="FL">Florida</option>
		<option value="GA">Georgia</option>
		<option value="HI">Hawaii</option>
		<option value="ID">Idaho</option>
		<option value="IL">Illinois</option>
		<option value="IN">Indiana</option>
		<option value="IA">Iowa</option>
		<option value="KS">Kansas</option>
		<option value="KY">Kentucky</option>
		<option value="LA">Louisiana</option>
		<option value="ME">Maine</option>
		<option value="MD">Maryland</option>
		<option value="MA">Massachusetts</option>
		<option value="MI">Michigan</option>
		<option value="MN">Minnesota</option>
		<option value="MS">Mississippi</option>
		<option value="MO">Missouri</option>
		<option value="MT">Montana</option>
		<option value="NE">Nebraska</option>
		<option value="NV">Nevada</option>
		<option value="NH">New Hampshire</option>
		<option value="NJ">New Jersey</option>
		<option value="NM">New Mexico</option>
		<option value="NY">New York</option>
		<option value="NC">North Carolina</option>
		<option value="ND">North Dakota</option>
		<option value="OH">Ohio</option>
		<option value="OK">Oklahoma</option>
		<option value="OR">Oregon</option>
		<option value="PA" selected="selected">Pennsylvania</option>
		<option value="RI">Rhode Island</option>
		<option value="SC">South Carolina</option>
		<option value="SD">South Dakota</option>
		<option value="TN">Tennessee</option>
		<option value="TX">Texas</option>
		<option value="UT">Utah</option>
		<option value="VT">Vermont</option>
		<option value="VA">Virginia</option>
		<option value="WA">Washington</option>
		<option value="WV">West Virginia</option>
		<option value="WI">Wisconsin</option>
		<option value="WY">Wyoming</option>
	</select> <br />

	<label for="recordPurchasePropertyTypeID" class="input">Desired property type:</label> <select name="recordPurchasePropertyTypeID" id="recordPurchasePropertyTypeID" class="inputclass" title="Select a Property Type">
		<option value="">-Select-</option>
		<option value="1">Single Family Detached</option>
		<option value="2">Condo</option>
		<option value="3">Townhouse</option>
		<option value="4">Rental</option>
		<option value="5">Multi-Family</option>
		<option value="6">Vacation Home</option>
		<option value="7">Other</option>
	</select> <br />
	<div class="buttonWrapper"><input name="formNext1" type="button" class="open1 nextbutton" value="Next" alt="Next" title="Next" /></div>
	</fieldset>
	</div>
	</li>
	<li id="sf2">
	<a href='#' class="ui-accordion-link">
	</a>
	<div>
	<fieldset><legend> Step 2 of 3 </legend>
	<div class="requiredNotice">*Required Field</div>
	<h3 class="stepHeader">Tell us about the property you're selling</h3>
	<label for="recordClientTimeFrameID" class="input required">When would you like to sell?</label> <select name="recordClientTimeFrameID" id="recordClientTimeFrameID" class="inputclass pageRequired" title="Select a Time Frame">
		<option value="">-Select-</option>
		<option value="1">Less than 3 months</option>
		<option value="2">3-6 months</option>
		<option value="3">6-9 months</option>
		<option value="4">9-12 months</option>
		<option value="5">Over 12 months</option>
	</select> <br />
	<label for="recordClientHomeTypeID" class="input required">Type of property you are selling:</label> <select name="recordClientHomeTypeID" id="recordClientHomeTypeID" class="inputclass pageRequired" title="Select a Property Type">
		<option value="">-Select-</option>
		<option value="1">Single Family Detached</option>
		<option value="2">Condo</option>
		<option value="3">Townhouse</option>
		<option value="4">Rental</option>
		<option value="5">Multi-Family</option>
		<option value="6">Vacation Home</option>
		<option value="7">Other</option>
	</select> <br />
	<label for="recordPropertyAddress1" class="input required">Property Street Address:</label> <input name="recordPropertyAddress1" id="recordPropertyAddress1" class="inputclass pageRequired" title="Street Address is required" maxlength="254" onblur="recordClientAddress1.value = this.value" /><br />
	<label for="recordPropertyAddress2" class="input">Address (2):</label> <input name="recordPropertyAddress2" id="recordPropertyAddress2" class="inputclass" maxlength="254" onblur="recordClientAddress2.value = this.value" /><br />
	<label for="recordPropertyCity" class="input required">City:</label> <input name="recordPropertyCity" id="recordPropertyCity" class="inputclass pageRequired" title="City is required" maxlength="254" onblur="recordClientCity.value = this.value" /><br />
	<label for="recordPropertyState" class="input required">State:</label> <select name="recordPropertyState" id="recordPropertyState" class="inputclass pageRequired" title="Select a State" onchange="recordClientState.value = this.value">
		<option value="">-Select-</option>
		<option value="AL">Alabama</option>
		<option value="AK">Alaska</option>
		<option value="AZ">Arizona</option>
		<option value="AR">Arkansas</option>
		<option value="CA">California</option>
		<option value="CO">Colorado</option>
		<option value="CT">Connecticut</option>
		<option value="DE">Delaware</option>
		<option value="DC">Dist of Columbia</option>
		<option value="FL">Florida</option>
		<option value="GA">Georgia</option>
		<option value="HI">Hawaii</option>
		<option value="ID">Idaho</option>
		<option value="IL">Illinois</option>
		<option value="IN">Indiana</option>
		<option value="IA">Iowa</option>
		<option value="KS">Kansas</option>
		<option value="KY">Kentucky</option>
		<option value="LA">Louisiana</option>
		<option value="ME">Maine</option>
		<option value="MD">Maryland</option>
		<option value="MA">Massachusetts</option>
		<option value="MI">Michigan</option>
		<option value="MN">Minnesota</option>
		<option value="MS">Mississippi</option>
		<option value="MO">Missouri</option>
		<option value="MT">Montana</option>
		<option value="NE">Nebraska</option>
		<option value="NV">Nevada</option>
		<option value="NH">New Hampshire</option>
		<option value="NJ">New Jersey</option>
		<option value="NM">New Mexico</option>
		<option value="NY">New York</option>
		<option value="NC">North Carolina</option>
		<option value="ND">North Dakota</option>
		<option value="OH">Ohio</option>
		<option value="OK">Oklahoma</option>
		<option value="OR">Oregon</option>
		<option value="PA" selected="selected">Pennsylvania</option>
		<option value="RI">Rhode Island</option>
		<option value="SC">South Carolina</option>
		<option value="SD">South Dakota</option>
		<option value="TN">Tennessee</option>
		<option value="TX">Texas</option>
		<option value="UT">Utah</option>
		<option value="VT">Vermont</option>
		<option value="VA">Virginia</option>
		<option value="WA">Washington</option>
		<option value="WV">West Virginia</option>
		<option value="WI">Wisconsin</option>
		<option value="WY">Wyoming</option>
	</select> <br />
	<label for="recordPropertyZip" class="input required">Zip:</label> <input name="recordPropertyZip" id="recordPropertyZip" class="inputclass pageRequired" title="Zip Code is required" maxlength="254" onblur="recordClientZip.value = this.value" /><br />

	<label for="recordClientPropertyValueID" class="input required">Estimated Market Value:</label> <select name="recordClientPropertyValueID" id="recordClientPropertyValueID" class="inputclass pageRequired" title="Select a Price Range">
		<option value="">-Select-</option>
		<option value="1">Less Than $75K</option>
		<option value="2">$75-$100K</option>
		<option value="3">$100-$125K</option>
		<option value="4">$125-$150K</option>
		<option value="5">$150-$200K</option>
		<option value="6">$200-$250K</option>
		<option value="7">$250-$300K</option>
		<option value="8">$300-$350K</option>
		<option value="9">$350-$400K</option>
		<option value="10">$400-$500K</option>
		<option value="11">$500-$700K</option>
		<option value="12">$700-$900K</option>
		<option value="13">Over $900K</option>
	</select> <br />
	<label for="recordPropertyBedroomsID" class="input">Bedrooms:</label> <select name="recordPropertyBedroomsID" id="recordPropertyBedroomsID" class="inputclass">
		<option value="">-Select-</option>
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5+</option>
	</select> <br />
	<label for="recordPropertyBathroomsId" class="input">Bathrooms:</label> <select name="recordPropertyBathroomsId" id="recordPropertyBathroomsId" class="inputclass">
		<option value="">-Select-</option>
		<option value="1">1</option>
		<option value="2">1.5</option>
		<option value="3">2</option>
		<option value="4">2.5</option>
		<option value="5">3</option>
		<option value="6">3.5</option>
		<option value="7">4+</option>
	</select> <br />
	<label for="recordPropertyAgeId" class="input">Approx. Age of Home:</label> <select name="recordPropertyAgeId" id="recordPropertyAgeId" class="inputclass">
		<option value="">-Select-</option>
		<option value="1">Less Than 1 year</option>
		<option value="2">1-5 years</option>
		<option value="3">6-10 years</option>
		<option value="4">11-15 years</option>
		<option value="5">More than 15 years</option>
	</select> <br />
	<label for="recordPropertySqFt" class="input">Approx. Square Footage:</label> <input name="recordPropertySqFt" id="recordPropertySqFt" class="inputclass" maxlength="254" /><br />
	<div class="buttonWrapper"><input name="formBack0" type="button" class="open0 prevbutton" value="Back" alt="Back" title="Back" /> <input name="formNext2" type="button" class="open2 nextbutton" value="Next" alt="Next" title="Next" /></div>
	</fieldset>
	</div>
	</li>
	<li id="sf3">
	<a href='#' class="ui-accordion-link">
	</a>
	<div>
	<fieldset><legend> Step 3 of 3 </legend>
	<div class="requiredNotice">*Required Field</div>
	<h3 class="stepHeader">Tell us about yourself</h3>
	<label for="recordClientNameFirst" class="input required">First Name:</label> <input name="recordClientNameFirst" id="recordClientNameFirst" class="inputclass pageRequired" title="First Name is required" maxlength="254" /> <br />
	<label for="recordClientNameLast" class="input required">Last Name:</label> <input name="recordClientNameLast" id="recordClientNameLast" class="inputclass pageRequired" maxlength="254" title="Last Name is required" /> <br />
	<label for="recordClientAddress1" class="input required">Current Address:</label> <input name="recordClientAddress1" id="recordClientAddress1" class="inputclass pageRequired" maxlength="254" title="Address is required" /> <br />
	<label for="recordClientAddress2" class="input">Address (2):</label> <input name="recordClientAddress2" id="recordClientAddress2" class="inputclass" maxlength="254" /> <br />
	<label for="recordClientCity" class="input required">City:</label> <input name="recordClientCity" id="recordClientCity" class="inputclass pageRequired" maxlength="254" title="City is required" /> <br />
	<label for="recordClientState" class="input required">State:</label> <select name="recordClientState" id="recordClientState" class="inputclass pageRequired" title="Select a State">
		<option value="">-Select-</option>
		<option value="AL">Alabama</option>
		<option value="AK">Alaska</option>
		<option value="AZ">Arizona</option>
		<option value="AR">Arkansas</option>
		<option value="CA">California</option>
		<option value="CO">Colorado</option>
		<option value="CT">Connecticut</option>
		<option value="DE">Delaware</option>
		<option value="DC">Dist of Columbia</option>
		<option value="FL">Florida</option>
		<option value="GA">Georgia</option>
		<option value="HI">Hawaii</option>
		<option value="ID">Idaho</option>
		<option value="IL">Illinois</option>
		<option value="IN">Indiana</option>
		<option value="IA">Iowa</option>
		<option value="KS">Kansas</option>
		<option value="KY">Kentucky</option>
		<option value="LA">Louisiana</option>
		<option value="ME">Maine</option>
		<option value="MD">Maryland</option>
		<option value="MA">Massachusetts</option>
		<option value="MI">Michigan</option>
		<option value="MN">Minnesota</option>
		<option value="MS">Mississippi</option>
		<option value="MO">Missouri</option>
		<option value="MT">Montana</option>
		<option value="NE">Nebraska</option>
		<option value="NV">Nevada</option>
		<option value="NH">New Hampshire</option>
		<option value="NJ">New Jersey</option>
		<option value="NM">New Mexico</option>
		<option value="NY">New York</option>
		<option value="NC">North Carolina</option>
		<option value="ND">North Dakota</option>
		<option value="OH">Ohio</option>
		<option value="OK">Oklahoma</option>
		<option value="OR">Oregon</option>
		<option value="PA" selected="selected">Pennsylvania</option>
		<option value="RI">Rhode Island</option>
		<option value="SC">South Carolina</option>
		<option value="SD">South Dakota</option>
		<option value="TN">Tennessee</option>
		<option value="TX">Texas</option>
		<option value="UT">Utah</option>
		<option value="VT">Vermont</option>
		<option value="VA">Virginia</option>
		<option value="WA">Washington</option>
		<option value="WV">West Virginia</option>
		<option value="WI">Wisconsin</option>
		<option value="WY">Wyoming</option>
	</select> <br />
	<label for="recordClientZip" class="input required">Zip:</label> <input name="recordClientZip" id="recordClientZip" class="inputclass pageRequired" maxlength="12" title="Zip Code is required" /> <br />
	<label for="recordClientPhone" class="input required">Phone Number:</label> <input name="recordClientPhone" id="recordClientPhone" class="inputclass pageRequired" maxlength="254" title="Phone Number is required" /> <br />
	<label for="recordClientPhoneAlt" class="input">Alternate Number:</label> <input name="recordClientPhoneAlt" id="recordClientPhoneAlt" class="inputclass" maxlength="254" /> <br />
	<label for="recordClientEmail" class="input required">Email Address:</label> <input name="recordClientEmail" id="recordClientEmail" class="inputclass pageRequired email" maxlength="254" title="Email address is required" /> <br />
	<label for="recordClientEmail1" class="input required">Confirm Email:</label> <input name="recordClientEmail1" id="recordClientEmail1" class="inputclass pageRequired" equalTo:"'#recordClientEmail" maxlength="254" title="Please confirm your email address" /> <br />
	<br />
	<p class="formDisclaimer">This is a sample form, no information is sent anywhere.</p>
	<div class="buttonWrapper"><input name="formBack1" type="button" class="open1 prevbutton" value="Back" alt="Back" title="Back" /> <input name="submit" type="submit" id="submit" value="Submit" class="submitbutton" alt="Submit" title="Submit"></div>
	</fieldset>
	</div>
	</li>
</ul>
</form>

</div>
</div>

</body>
</html>
