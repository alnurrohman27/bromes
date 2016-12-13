<?php
	include('connect.php');
	$cmd = $_POST['command'];

	if($cmd == 'login'){
		login($conn);
	}
	else{
		logout();
	}

	function login($conn){
		$no = $_POST['no'];

		$sql = 'SELECT nama, no_hp FROM kontak WHERE no_hp = "'.$no.'" AND hak_akses = "admin"';
		$result = $conn->query($sql);
		if($result->num_rows > 0){
			while($row = $result->fetch_assoc()){
				$data['name'] = $row['nama'];
			}
			echo json_encode($data);
		}
		else{
			echo json_encode(0);
		}

	}
?>