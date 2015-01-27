<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	$response = array();
	if(isset($_GET['area'])) {
		$area = $_GET['area'];
		$result = mysql_query("SELECT * FROM `Property_info` WHERE `Area` = '$area'");
		if(!empty($result)) {
			$response["properties"] = array();
			if(mysql_num_rows($result) > 0) {
				while ($row = mysql_fetch_array($result)) {
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