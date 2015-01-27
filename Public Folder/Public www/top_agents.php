<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host,$db_uid,$db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	$response = array();
	$result = mysql_query("SELECT `ID`, `Name`, `Username`, `Rating` FROM `account_info` ORDER BY `Rating` DESC LIMIT 5") or die(mysql_error());
	if (mysql_num_rows($result) > 0) {
		$response["agents"] = array();
		while ($row = mysql_fetch_array($result)) {
			$agent = array();
			$agent["id"] = $row["ID"];
			$agent["name"] = $row["Name"];
			$agent["user"] = $row["Username"];
			$agent["rating"] = $row["Rating"];
			array_push($response["agents"], $agent);
		}
		$response["success"] = 1;
		echo json_encode($response);
	} else {
	    $response["success"] = 0;
		$response["message"] = "No products found";
		echo json_encode($response);
	}
	mysql.close();
?>


