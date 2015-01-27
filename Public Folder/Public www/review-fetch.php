<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	//input: agent id, output: agent reviews
	$response = array();
	if(isset($_GET['id'])) {
		$id = $_GET['id'];
		$result = mysql_query("SELECT * FROM `reviews` WHERE `agent_review_ID` = '$id'") or die(mysql_error);
		if(!empty($result)) {
			if(mysql_num_rows($result) > 0) {
				//$row = mysql_fetch_array($result);
				$response["reviews"] = array();
				while($row = mysql_fetch_array($result)) {
					$review = array();
					$review["text"] = $row["review_text"];
					$review["rating"] = $row["rating"];
					array_push($response["reviews"], $review);
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
				