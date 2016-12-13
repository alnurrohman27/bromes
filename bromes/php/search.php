<?php
	include('connect.php');
	if(isset($_POST['table']) && $_POST['table'] == 'contacts')
	{
		contacts($conn);
	}

	else{
		die('No direct script access allowed'); 
	}

	function contacts($conn)
	{
		$sql = 'SELECT * FROM kontak';
		$result = $conn->query($sql);

		if($result->num_rows > 0){
			$i=0;
			while ($row = $result->fetch_assoc()) {
				$data[$i]['no'] = $row['no_hp'];
				$data[$i]['name'] = $row['nama'];
				$data[$i]['email'] = $row['email'];
				$data[$i]['address'] = $row['alamat'];
				$data[$i]['privileges'] = $row['hak_akses'];
				$i++;
			}
			echo json_encode($data);
		}
	}

?>