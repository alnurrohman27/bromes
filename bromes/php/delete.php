<?php
	include('connect.php');

	$table = $_POST['table'];
	$key = $_POST['key'];

	if($table == 'contacts'){
		$sql = 'DELETE FROM kontak WHERE no_hp = "'.$key.'"';
	}
	else if($table == 'events'){
		$sql = 'DELETE FROM kirim_acara WHERE id_acara = '.$key;
		$conn->query($sql);
		$sql = 'DELETE FROM acara WHERE id = "'.$key.'"';
	}
	$result = $conn->query($sql);
	if($result){
		echo json_encode(1);
	}
	else{
		echo json_encode(0);
	}
?>