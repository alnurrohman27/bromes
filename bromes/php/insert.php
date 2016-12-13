<?php
	include('connect.php');

	if(isset($_POST['table']) && $_POST['table'] == 'user'){
		user($conn);
	}
	
	else{
		die('No direct script access allowed'); 
	}

	function user($conn){
		$username = $_POST['username'];
		$password = $_POST['password'];
		$name = $_POST['name'];

		$sql = 'INSERT INTO pengguna VALUES("'.$username.'", "'.$password.'", "'.$name.'")';
		$result = $conn->query($sql);

		if($result){
			echo 'Data berhasil dimasukkan';
		}
	}
?>