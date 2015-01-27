<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	//input: agent details, output: database entry
	$response = array();
	if(isset($_POST['name'], $_POST['user'], $_POST['pass1'], $_POST['pass2'], $_POST['email'], $_POST['add'], $_POST['numb'])) {
		$name = $_POST['name'];
		$username = $_POST['user'];
		$pass1 = $_POST['pass1'];
		$pass2 = $_POST['pass2'];
		$email = $_POST['email'];
		$address = $_POST['add'];
		$number = $_POST['numb'];
		if($pass1 === $pass2) {
			$pass = $pass1;
			$result = mysql_query("INSERT INTO `account_info` (`Name`, `Username`, `Password`, `Email`, `Address`, `Number`)
		VALUES ('$name', '$username', '$pass', '$email', '$address', '$number')") or die(mysql_error());
			if($result) {
				$response["success"] = 1;
				$response["message"] = "Account successfully created.";
				echo json_encode($response);
			} else {
				$response["success"] = 0;
				$response["message"] = "An error occurred.";
				echo json_encode($response);
			} 
		}
		else {
			$response['success'] = 0;
			$response['message'] = "Password do not match.";
			echo json_encode($response);
		}
		
		 
	} else {
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing";
		echo json_encode($response);
	}
	mysql.close();
?>