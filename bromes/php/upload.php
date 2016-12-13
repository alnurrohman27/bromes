<?php
	if (basename($_SERVER['PHP_SELF']) == basename(__FILE__)) { 
		die('No direct script access allowed '); 
	}

	$path = "../../../document/";
	if(!is_dir($path))
	{
		mkdir($path);
	}
	$target_dir = $path;
	$file_name = md5(uniqid(rand(), true)) . '.' . substr(strrchr(($_FILES['file']['name']), '.'), 1);
	$target_file = $target_dir . $file_name;
	$flag = 1;
	$extension = pathinfo($target_file, PATHINFO_EXTENSION);

	//Ambil Data dari Form
	$nama_file = basename($_FILES['file']['name']);
	$ukuran_file = $_FILES['file']['size'];
	$tipe_file = pathinfo($target_file,PATHINFO_EXTENSION);
	$tmp_file = $_FILES['file']['tmp_name'];

	// Check if file already exists
    if (file_exists($target_file)) {
        echo "Sorry, file already exists.";
        $flag = 0;
    }
    // Check if $uploadOk is set to 0 by an error
    if ($flag == 0) {
        echo "Sorry, your file was not uploaded.";
    // if everything is ok, try to upload file
    } 
    else {
		if(move_uploaded_file($_FILES["file"]["tmp_name"], $target_file)){
			$sql = 'INSERT INTO FILE VALUES(NULL,"'.$nama_file.'","'.$ukuran_file.'","'.$tipe_file.'","/dms/document/'.$file_name.'")';
			$conn->query($sql);
			$sql = 'SELECT id_file FROM file ORDER BY id_file DESC';
			$result = $conn->query($sql);
			$file = $result->fetch_assoc()['id_file'];
			if($table == 'list'){
				$sql = 'INSERT INTO dokumen VALUES(NULL, "'.$id.'", "'.$context.'", "'.$nn.'", "'.$nomor.'", "'.$name.'", "'.$author.'", "'.$ll.'", "'.$dd.'")';
				$result = $conn->query($sql);
				$doc = $conn->query('SELECT id_dokumen FROM dokumen ORDER BY id_dokumen DESC')->fetch_assoc()['id_dokumen'];
				$conn->query('INSERT INTO edisi VALUES(NULL, 1, "'.$doc.'")');
				$edition = $conn->query('SELECT id_edisi FROM edisi ORDER BY id_edisi DESC')->fetch_assoc()['id_edisi'];
				$conn->query('INSERT INTO revisi VALUES(NULL, "'.$edition.'", "'.$file.'", 0, "'.date("y-m-d").'", NULL)');
			}
			else if($table == 'edition'){
				$conn->query('DELETE FROM edisi WHERE id_dokumen = '.$doc.' AND no_edisi = '.$edition_no);
				$edition_no = $edition_no + 1;
				$conn->query('INSERT INTO edisi VALUES(NULL, '.$edition_no.', "'.$doc.'")');
				$edition = $conn->query('SELECT id_edisi FROM edisi ORDER BY id_edisi DESC')->fetch_assoc()['id_edisi'];
				$conn->query('INSERT INTO revisi VALUES(NULL, "'.$edition.'", "'.$file.'", 0, "'.date("y-m-d").'", "'.$note.'")');
			}
			else{
				$conn->query('DELETE FROM revisi WHERE id_edisi = '.$edition.' AND no_revisi = '.$revision_no);
				$revision_no = $revision_no + 1;
				$conn->query('INSERT INTO revisi VALUES(NULL, "'.$edition.'", "'.$file.'", '.$revision_no.', "'.date("y-m-d").'", "'.$note.'")');
			}
		    
		    echo json_encode('Data berhasil dimasukkan');
		}
		else{
			echo json_encode('Dokumen gagal diunggah');
		}
	}

?>