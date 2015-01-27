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
		$result1 = mysql_query("SELECT DISTINCT `account_ID` FROM `Property_info` WHERE `Area`= '$area'") or die(mysql_error());
		if(!empty($result1)) {
			$response['agent'] = array();
			if(mysql_num_rows($result1) > 0) {
				while ($row1 = mysql_fetch_array($result1)) {
					$id = $row1["account_ID"];
					$result2 = mysql_query("SELECT * FROM `account_info` WHERE `ID` = '$id';") or die(mysql_error());
					if(!empty($result2)) {
						if(mysql_num_rows($result2) > 0) {
						$row2 = mysql_fetch_array($result2);
							$agents = array();
							$agents["id"] = $row2["ID"];
							$agents["name"] = $row2["Name"];
							$agents["add"] = $row2["Address"];
							$agents["numb"] = $row2["Number"];
							$agents["rating"] = $row2["Rating"];
							
						} else {
							$response["success"] = 0;
							$response["message"] = "Fetching Error Inside Loop";
							echo json_encode($response);
						}
					} else {
						$response["success"] = 0;
						$response["message"] = "Table Empty Inside Loop";
						echo json_encode($response);
					}
					array_push($response['agent'], $agents);

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