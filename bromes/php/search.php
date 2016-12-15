<?php
	include('connect.php');
	if(isset($_POST['table']) && $_POST['table'] == 'contacts')
	{
		contacts($conn);
	}
	else if(isset($_POST['table']) && $_POST['table'] == 'privileges')
	{
		privileges($conn);
	}
	else if(isset($_POST['table']) && $_POST['table'] == 'events')
	{
		events($conn);
	}
	else if(isset($_POST['table']) && $_POST['table'] == 'send_events')
	{
		send_events($conn);
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
	function privileges($conn)
	{
		$sql = 'SELECT * FROM hak_akses';
		$result = $conn->query($sql);

		if($result->num_rows > 0){
			$i=0;
			while ($row = $result->fetch_assoc()) {
				$data[$i]['id'] = $row['id'];
				$i++;
			}
			echo json_encode($data);
		}
	}

	function events($conn)
	{
		$sql = 'SELECT id, nama, DATE_FORMAT(tanggal, "w") as hari, DATE_FORMAT(tanggal, "%e") as tanggal, DATE_FORMAT(tanggal, "%c") as bulan, DATE_FORMAT(tanggal, "%Y") as tahun, DATE_FORMAT(tanggal, "%H:%i") as waktu, keterangan FROM acara';
		$result = $conn->query($sql);

		if($result->num_rows > 0){
			$i=0;
			while ($row = $result->fetch_assoc()) {
				$data[$i]['i'] = $i+1;
				$data[$i]['id'] = $row['id'];
				$data[$i]['name'] = $row['nama'];
				$hari = extract_day($row['hari']);
				$bulan = extract_month($row['bulan']);
				$data[$i]['date'] = $hari.', '.$row['tanggal'].' '.$bulan.' '.$row['tahun'].' '.$row['waktu'];
				$data[$i]['note'] = $row['keterangan'];
				$i++;
			}
			echo json_encode($data);
		}
	}

	function send_events($conn)
	{
		$id = $_POST['id'];
		$sql = 'SELECT * FROM kirim_acara WHERE id_acara = '.$id;
		$result = $conn->query($sql);

		if($result->num_rows > 0){
			$i=0;
			while ($row = $result->fetch_assoc()) {
				$data[$i]['i'] = $i+1;
				$data[$i]['id'] = $row['id'];
				$data[$i]['no_hp'] = $row['no_hp'];
				$data[$i]['status'] = $row['status'];
				$i++;
			}
			echo json_encode($data);
		}
	}

	function extract_day($id)
	{
		if($id == 0)
			return "Minggu";
		else if($id == 1)
			return "Senin";
		else if($id == 2)
			return "Selasa";
		else if($id == 3)
			return "Rabu";
		else if($id == 4)
			return "Kamis";
		else if($id == 5)
			return "Jumat";
		else if($id == 6)
			return "Sabtu";
	}

	function extract_month($id)
	{
		if($id == 1)
			return "Januari";
		else if($id == 2)
			return "Februari";
		else if($id == 3)
			return "Maret";
		else if($id == 4)
			return "April";
		else if($id == 5)
			return "Mei";
		else if($id == 6)
			return "Juni";
		else if($id == 7)
			return "Juli";
		else if($id == 8)
			return "Agustus";
		else if($id == 9)
			return "September";
		else if($id == 10)
			return "Oktober";
		else if($id == 11)
			return "November";
		else if($id == 12)
			return "Desember";
	}

?>