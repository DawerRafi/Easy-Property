<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	//input: property id, output: database delete
	$response = array();
	if(isset($_GET['id'])) {
		$id = $_GET['id'];
		$result = mysql_query("DELETE FROM `Property_info` WHERE `Property_ID` = '$id'") or die(mysql_error);
		if($result) {
			$response["success"] = 1;
			$response["message"] = "Property successfully deleted.";
			echo json_encode($response);
		} else {
			$response["success"] = 0;
			$response["message"] = "An error occurred.";
			echo json_encode($response);
		}
	} else {
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing";
		echo json_encode($response);
	}
	mysql.close();
?>