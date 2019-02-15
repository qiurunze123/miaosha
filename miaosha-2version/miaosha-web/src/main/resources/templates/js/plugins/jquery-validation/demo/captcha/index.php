<?php

// Make the page validate
ini_set('session.use_trans_sid', '0');

// Include the random string file
require 'rand.php';

// Begin the session
session_start();

// Set the session contents
$_SESSION['captcha_id'] = $str;

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
 <title>AJAX CAPTCHA</title>
 <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
 <meta name="keywords" content="AJAX,JHR,PHP,CAPTCHA,download,PHP CAPTCHA,AJAX CAPTCHA,AJAX PHP CAPTCHA,download AJAX CAPTCHA,download AJAX PHP CAPTCHA" />
 <meta name="description" content="An AJAX CAPTCHA script, written in PHP" />
 
 <script type="text/javascript" src="../../lib/jquery.js"></script>
 <script type="text/javascript" src="../../jquery.validate.js"></script>
 <script type="text/javascript" src="captcha.js"></script>
 
 <link rel="stylesheet" type="text/css" href="style.css" />
 <style type="text/css">
  img { border: 1px solid #eee; }
  p#statusgreen { font-size: 1.2em; background-color: #fff; color: #0a0; }
  p#statusred { font-size: 1.2em; background-color: #fff; color: #a00; }
  fieldset label { display: block; }
  fieldset div#captchaimage { float: left; margin-right: 15px; }
  fieldset input#captcha { width: 25%; border: 1px solid #ddd; padding: 2px; }
  fieldset input#submit { display: block; margin: 2% 0% 0% 0%; }
  #captcha.success {
  	border: 1px solid #49c24f;
	background: #bcffbf;
  }
  #captcha.error {
  	border: 1px solid #c24949;
	background: #ffbcbc;
  }
 </style>
</head>

<body>

<h1><acronym title="Asynchronous JavaScript And XML">AJAX</acronym> <acronym title="Completely Automated Public Turing test to tell Computers and Humans Apart">CAPTCHA</acronym>, based on <a href="http://psyrens.com/captcha/">http://psyrens.com/captcha/</a></h1>

<form id="captchaform" action="">
<fieldset>
 <div id="captchaimage"><a href="<?php echo $_SERVER['PHP_SELF']; ?>" id="refreshimg" title="Click to refresh image"><img src="images/image.php?<?php echo time(); ?>" width="132" height="46" alt="Captcha image" /></a></div>
 <label for="captcha">Enter the characters as seen on the image above (case insensitive):</label>
 <input type="text" maxlength="6" name="captcha" id="captcha" />
 <input type="submit" name="submit" id="submit" value="Check" />
</fieldset>
</form>

<p>If you can&#39;t decipher the text on the image, click it to dynamically generate a new one.</p>

</body>

</html>