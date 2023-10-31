<?php
include "connect.php";

$tencuahang = $_POST['tencuahang'];
$diachi = $_POST['diachi'];
$hinhanh = $_POST['hinhanh'];
$danhmuc = $_POST['danhmuc'];

$query = 'INSERT INTO `cuahang`( `tencuahang`, `diachi`, `hinhanh`,`danhmuc`) VALUES ("'.$tencuahang.'","'.$diachi.'","'.$hinhanh.'",'.$danhmuc.')';
	$data = mysqli_query($conn, $query);

	if($data == true){

		$arr = [
			'success' => true,
			'message' => "thanh cong"
		];
	}else{
		$arr = [
			'success' => false,
			'message' => "khong thanh cong"
		];
	}

print_r(json_encode($arr));

?>