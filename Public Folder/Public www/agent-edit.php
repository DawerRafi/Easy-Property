<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	//input: agent details, output: database entry
	$response = array();
	if(isset($_POST['id'], $_POST['name'], $_POST['add'], $_POST['numb'])) {
		$id = $_POST['id'];
		$name = $_POST['name'];
		$address = $_POST['add'];
		$number = $_POST['numb'];
		$result = mysql_query("UPDATE `account_info` SET `Name` = '$name', `Address` = '$address', `Number` = '$number' WHERE `ID` = '$id'") or die(mysql_error());
		if($result) {
			$response["success"] = 1;
			$response["message"] = "Account successfully updated.";
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