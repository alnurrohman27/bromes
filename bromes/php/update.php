<?php
	include('connect.php');

	if(isset($_POST['table']) && $_POST['table'] == 'user'){
		user($conn);
	}

	function user($conn){
		$username = strtolower($_POST['username']);
	    $password = $_POST['password'];
	    $name = ucwords($_POST['name']);

	    $sql = 'UPDATE pengguna SET username = "'.$username.'", password = "'.$password.'", nama = "'.$name.'"';
	    $result = $conn->query($sql);
	    if($result){
	    	echo json_encode('Data berhasil dimasukkan');
	    }
	    else{
	    	echo json_encode(0);
	    }
	}
?>