<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
	//echo "first part" . "<br>";
?>

<?php
	//input: login details, output: account login
	//echo "second part" . "<br>";
	$response = array();
	if(isset($_POST['username'], $_POST['password'])) {
		//echo "third part" . "<br>";
		$user = $_POST['username'];
		$pass = $_POST['password'];
		$result = mysql_query("SELECT * FROM `account_info` WHERE `Username` = '$user' AND `Password` = '$pass'") or die(mysql_query);
		//echo "fourth part" . "<br>";
		if(!empty($result)) {
			//echo "fifth part" . "<br>";
			if(mysql_num_rows($result) > 0) {
				//echo "sixth part" . "<br>";
				$row = 0;
				$row = mysql_fetch_array($result);
				$agent = array();
				$agent["id"] = $row["ID"];
				$agent["name"] = $row["Name"];
				$agent["user"] = $row["Username"];
				$agent["email"] = $row["Email"];
				$agent["add"] = $row["Address"];
				$agent["numb"] = $row["Number"];
				$agent["rating"] = $row["Rating"];
				$response["agent"] = $agent;
				$response["success"] = 1;
				echo json_encode($response);
				//echo $agent["id"] . " " . $agent["name"] . " " . $agent["user"] . " " . $agent["email"] . " " . $agent["add"] . " " . $agent["numb"] . " " . $agent["rating"];
			} else {
				$response["success"] = 0;
				$response["message"] = "Incorrect information entered";
				echo json_encode($response);
				//echo "error";
			}
		} else {
			$response["success"] = 0;
			$response["message"] = "Table empty";
			echo json_encode($response);
			//echo "empty";
		}
	} else {
		$response["success"] = 0;
		$response["message"] = "Required field(s) is missing";
		echo json_encode($response);
		//echo "missing";
	}
	mysql.close();
?>