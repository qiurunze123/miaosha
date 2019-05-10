<?php
// wait a second to simulate a some latency
usleep(500000);
$user = $_REQUEST['user'];
$pw = $_REQUEST['password'];
if($user && $pw && $pw == "foobar")
	echo "Hi $user, welcome back.";
else
	echo "Your password is wrong (must be foobar).";
?>