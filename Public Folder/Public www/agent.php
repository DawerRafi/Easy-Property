<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	//input: agent id, output: agent info
	$response = array();
	if(isset($_GET['id'])) {
		$id = $_GET['id'];
		$result1 = mysql_query("SELECT `Name`, `Address`, `Number`, `Email`, `Rating` FROM `account_info` WHERE `ID` = '$id'") or die(mysql_error());
		$result2 = mysql_query("SELECT `Propert_Name`, `Description`, `Property_ID` FROM `Property_info` WHERE `account_ID` = '$id'") or die(mysql_error());
		if(!empty($result1)) {
			if(mysql_num_rows($result1) > 0) {
				$row = mysql_fetch_array($result1);
				$agent = array();
				$agent["name"] = $row["Name"];
				$agent["add"] = $row["Address"];
				$agent["numb"] = $row["Number"];
                                $agent["email"]= $row["Email"];
				$agent["rating"] = $row["Rating"];
				$response["agent"] = $agent;
				$response["properties"] = array();
				$row = 0;
				while ($row = mysql_fetch_array($result2)) {
					$properties = array();
					$properties["name"] = $row["Propert_Name"];
					$properties["descrpt"] = $row["Description"];
					$properties["id"] = $row["Property_ID"];
					array_push($response["properties"], $properties);
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
