<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	//input: photo details, output: database entry
	$response = array();
	if(isset($_POST['agent_ID'], $_POST['property_ID'], $_POST['url'])) {
		$agent = $_POST['agent_ID'];
		$property = $_POST['property_ID'];
		$url = $_POST['url'];
		$result = mysql_query("INSERT INTO `photos` (`url`, `agent_id`, `property_id`) VALUES ('$url', '$agent', '$property')") or die(mysql_error());
		if($result) {
			$response["success"] = 1;
			$response["message"] = "Photo uploaded.";
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