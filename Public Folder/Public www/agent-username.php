<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	//input: agent id, output: username update
	$response = array();
	if(isset($_POST['id'], $_POST['username'])) {
		$id = $_POST['id'];
		$username = $_POST['username'];
		$temp = mysql_query("SELECT `Username` FROM `account_info`") or die(mysql_error());
		if(!empty($temp)) {
			if(mysql_num_rows($temp) > 0) {
				$check = false;
				while($row = mysql_fetch_array($temp)) {
					//$old_username = $row['Username'];
					if($row['Username'] === $username) {
						$check = true;
						break;
					}
				}
				if(!$check) {
					$result = mysql_query("UPDATE `account_info` SET `Username` = '$username' WHERE `ID` = '$id'") or die(mysql_error());
					if($result) {
						$response["success"] = 1;
						$response["message"] = "Username successfully updated";
						echo json_encode($response);
					} else {
						$response["success"] = 0;
						$response["message"] = "An error occurred.";
						echo json_encode($response);
					}
				} else {
					$response['success'] = 0;
					$response['message'] = "Username already exists";
					echo json_encode($response);
				}
			} else {
				$response["success"] = 0;
				$response["message"] = "Fetching Error";
				echo json_encode($response);
			}
		} else {
			$response["success"] = 0;
			$response["message"] = "Table Empty";
			echo json_encode($response);
		}
	} else {
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing";
		echo json_encode($response);
	}
	mysql.close();
?>