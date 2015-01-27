<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	//input: agent property id, output: photos
	$response = array();
	if(isset($_POST['agent_ID'], $_POST['property_ID'])) {
		$agent = $_POST['agent_ID'];
		$property = $_POST['property_ID'];
		$result = mysql_query("SELECT `url` FROM `photos` WHERE `agent_ID` = '$agent' AND `property_ID` = '$property'") or die(mysql_error());
		if(!empty($result)) {
			if(mysql_num_rows($result) > 0) {
				$response["photos"] = array();
				while($row = mysql_fetch_array($result)) {
					$photo = array();
					$photo["url"] = $row["url"];
					array_push($response["photos"], $photo);
				}
				$response["success"] = 1;
				echo json_encode($response);
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