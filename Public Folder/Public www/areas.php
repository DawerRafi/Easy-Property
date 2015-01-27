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
	$result = mysql_query("SELECT * FROM Property_info WHERE Area != 'Pechs' AND Area != 'Gulshan' AND Area != 'Bahadurabad' AND Area != 'Clifton' AND Area != 'Defence' AND Area != 'Gulistan-e-Jauhar' AND Area != 'Malir' AND Area != 'North Karachi'") or die(mysql_error());
        /*$result = mysql_query("SELECT * FROM Property_info WHERE Area != 'Pechs' AND 'Gulshan' AND 'Bahadurabad' AND 'Clifton' AND 'Defence' AND 'Gulistan-e-Jauhar' AND 'Malir' AND 'North Karachi'") or die(mysql_error());*/
	if (mysql_num_rows($result) > 0) {
		$response["areas"] = array();
		while ($row = mysql_fetch_array($result)) {
			$Area = array();
			$Area["Area"] = $row["Area"];
			array_push($response["areas"], $Area);
		}
		$response["success"] = 1;
		echo json_encode($response);
	} else {
	    $response["success"] = 0;
		$response["message"] = "No products found";
		echo json_encode($response);
    }
?>