<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	//input: review, output: database entry
	$response = array();
	if(isset($_POST['text'], $_POST['rating'], $_POST['id'])) {
		$text = $_POST['text'];
		$rating = $_POST['rating'];
		$id = $_POST['id'];
		$result1 = mysql_query("INSERT INTO `reviews` (`review_text`, `rating`, `agent_review_ID`) VALUES ('$text', '$rating', '$id')") or die(mysql_error());
		if($result1) {
			$response["success"] = 1;
			$response["message"] = "Review successfully created.";
			echo json_encode($response);
		} else {
			$response["success"] = 0;
			$response["message"] = "An error occurred.";
			echo json_encode($response);
		}
		$temp = mysql_query("SELECT `rating` FROM `reviews` WHERE `agent_review_ID` = '$id'") or die(mysql_error());
		$rate = 0;
		$num = 0;
		while ($row = mysql_fetch_array($temp)) {
			$rate += $row['rating'];
			$num++;
		}
		echo "<br>" . $rate . "<br>" . $num;
		$avg = $rate / $num;
		echo "<br>" . $avg;
		$result2 = mysql_query("UPDATE `account_info` SET `Rating` = '$avg' WHERE `ID` = '$id'") or die(mysql_error());
		if($result2) {
			$response["success"] = 1;
			$response["message"] = "Rating successfully updated.";
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