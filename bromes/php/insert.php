<?php
	include('connect.php');

	if(isset($_POST['table']) && $_POST['table'] == 'contacts'){
		contacts($conn);
	}
	else if(isset($_POST['table']) && $_POST['table'] == 'events'){
		events($conn);
	}
	
	else{
		die('No direct script access allowed'); 
	}

	function contacts($conn){
		$no = $_POST['no'];
		$name = ucwords($_POST['name']);
		$email = $_POST['email'];
		$address = $_POST['address'];
		$privileges = $_POST['privileges'];

		$sql = 'INSERT INTO kontak VALUES("'.$no.'", "'.$name.'", "'.$email.'", "'.$address.'", "'.$privileges.'")';
		$result = $conn->query($sql);

		if($result){
			echo 'Data berhasil dimasukkan';
		}
	}
	function events($conn){
		$name = ucwords($_POST['name']);
		$date = $_POST['date'];
		$note = $_POST['note'];

		$sql = 'INSERT INTO acara VALUES("'.$name.'", "'.$date.'", "'.$note.'")';
		$result = $conn->query($sql);

		if($result){
			echo 'Data berhasil dimasukkan';
		}
	}
?>