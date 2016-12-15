<?php
	include('connect.php');

	if(isset($_POST['table']) && $_POST['table'] == 'contacts'){
		contacts($conn);
	}

	function contacts($conn){
		$no = $_POST['no'];
		$old_no = $_POST['old_no'];
		$name = ucwords($_POST['name']);
		$email = strtolower($_POST['email']);
		$address = ucwords($_POST['address']);
		$privileges = $_POST['privileges'];

	    $sql = 'UPDATE kontak SET no_hp = "'.$no.'", nama = "'.$name.'", email = "'.$email.'", alamat = "'.$address.'", hak_akses = "'.$privileges.'" WHERE no_hp = "'.$old_no.'"';
	    $result = $conn->query($sql);
	    if($result){
	    	echo json_encode('Data berhasil dimasukkan');
	    }
	    else{
	    	echo json_encode(0);
	    }
	}
?>