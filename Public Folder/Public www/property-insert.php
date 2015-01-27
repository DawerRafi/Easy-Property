<?php
	$db_host = "localhost";
	$db_uid = "user@easyproperty.tk";
	$db_pass = "Mobileapp";
	$db_name = "user@easyproperty.tk";
	$db_con = mysql_connect($db_host, $db_uid, $db_pass) or die('could not connect');
	mysql_select_db($db_name);
?>

<?php
	//input: property details, output: database entry
	$response = array();
	if(isset($_POST['id'], $_POST['name'], $_POST['beds'], $_POST['baths'], $_POST['desc'], $_POST['type'], $_POST['price'], $_POST['area'], $_POST['size'])) {
		$id = $_POST['id'];
		$name = $_POST['name'];
		$bedrooms = $_POST['beds'];
		$bathrooms = $_POST['baths'];
		$description = $_POST['desc'];
		$type = $_POST['type'];
		$price = $_POST['price'];
		$area = $_POST['area'];
		$size = $_POST['size'];
		$result = mysql_query("INSERT INTO `Property_info` (`Propert_Name`, `No_BedRooms`, `No_Bath`, `Description`, `type`, `price`, `Area`, `size`, `account_ID`) VALUES ('$name', '$bedrooms', '$bathrooms', '$description', '$type', '$price', '$area', '$size', '$id')") or die(mysql_error);
		if($result) {
			$response["success"] = 1;
			$response["message"] = "Property successfully created.";
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