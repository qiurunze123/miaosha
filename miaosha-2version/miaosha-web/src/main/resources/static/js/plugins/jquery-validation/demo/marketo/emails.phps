<?php
$request = trim(strtolower($_REQUEST['value']));
$emails = array('glen@marketo.com', 'george@bush.gov', 'me@god.com', 'aboutface@cooper.com', 'steam@valve.com', 'bill@gates.com');
$valid = 'true';
foreach($emails as $email) {
	if( strtolower($email) == $request )
		$valid = 'false';
}
echo $valid;
?>