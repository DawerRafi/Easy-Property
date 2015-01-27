<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	//input: agent id, output: password update
	$response = array();
	if(isset($_POST['id'], $_POST['pass'], $_POST['pass1'], $_POST['pass2'])) {
		$id = $_POST['id'];
		$pass_old_1 = $_POST['pass'];
		$pass_new_1 = $_POST['pass1'];
		$pass_new_2 = $_POST['pass2'];
		$temp = mysql_query("SELECT `Password` FROM `account_info` WHERE `ID` = '$id'") or die(mysql_error());
		$row = mysql_fetch_array($temp);
		$pass_old_2 = $row['Password'];
		if($pass_old_1 === $pass_old_2 && $pass_new_1 === $pass_new_2) {
			$password = $pass_new_1;
			$result = mysql_query("UPDATE `account_info` SET `Password` = '$password' WHERE `ID` = '$id'") or die(mysql_error());
			if($result) {
				$response["success"] = 1;
				$response["message"] = "Password successfully updated";
				echo json_encode($response);
			} else {
				$response["success"] = 0;
				$response["message"] = "An error occurred.";
				echo json_encode($response);
			} 
		}
		else {
			$response['success'] = 0;
			$response['message'] = "Passwords do not match OR Incorrect password entered";
			echo json_encode($response);
		}
		
		 
	} else {
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing";
		echo json_encode($response);
	}
	mysql.close();
?>