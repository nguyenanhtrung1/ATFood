<?php
include "connect.php";

$tenloaisanpham = $_POST['tenloaisanpham'];
$hinhanh = $_POST['hinhanh'];
$maloaisanpham = $_POST['maloaisanpham'];

$query = 'UPDATE `loaisanpham` SET `tenloaisanpham`="'.$tenloaisanpham.'",`hinhanh`="'.$hinhanh.'" WHERE `maloaisanpham` = ' .$maloaisanpham;
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