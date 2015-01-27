<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	//input: property id, output: property info
	$response = array();
	if(isset($_GET['id'])) {
		$id = $_GET['id'];
		$result1 = mysql_query("SELECT * FROM `Property_info` WHERE `Property_ID` = '$id'") or die(mysql_error());
		$temp = mysql_query("SELECT `account_ID` FROM `Property_info` WHERE `Property_ID` = '$id'") or die(mysql_error());
		$row = mysql_fetch_array($temp);
		$id = $row["account_ID"];
		$result2 = mysql_query("SELECT `ID`, `Name`, `Rating` FROM `account_info` WHERE `ID` = '$id'") or die(mysql_error());
		if(!empty($result1)) {
			if(mysql_num_rows($result1) > 0) {
				$row = 0;
				$row = mysql_fetch_array($result1);
				$property = array();
				$property["name"] = $row["Propert_Name"];
				$property["bed"] = $row["No_BedRooms"];
				$property["bath"] = $row["No_Bath"];
				$property["dscrpt"] = $row["Description"];
				$property["type"] = $row["type"];
				$property["price"] = $row["price"];
				$property["area"] = $row["Area"];
				$property["size"] = $row["size"];
				$property["agent"] = $row["account_ID"];
				$response["property"] = $property;
				//array_push($response["property"], $property);
				$row = 0;
				$row = mysql_fetch_array($result2);
				$agent = array();
				$agent["id"] = $row["ID"];
				$agent["name"] = $row["Name"];
				$agent["rating"] = $row["Rating"];
				//array_push($response["agent"], $agent);
				$response["agent"] = $agent;
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
