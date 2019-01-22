$(function(){
	$("#refreshimg").click(function(){
		$.post('newsession.php');
		$("#captchaimage").load('image_req.php');
		return false;
	});
	
	$("#captchaform").validate({
		rules: {
			captcha: {
				required: true,
				remote: "process.php"
			}
		},
		messages: {
			captcha: "Correct captcha is required. Click the captcha to generate a new one"	
		},
		submitHandler: function() {
			alert("Correct captcha!");
		},
		success: function(label) {
			label.addClass("valid").text("Valid captcha!")
		},
		onkeyup: false
	});
	
});
