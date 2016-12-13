<?php
	include('connect.php');

	$table = $_POST['table'];

	if($table == 'user'){
		$sql = 'DELETE FROM pengguna WHERE username = "'.$key.'"';
	}

	$result = $conn->query($sql);
	if($result){
		echo json_encode(1);
	}
	else{
		echo json_encode(0);
	}
?>