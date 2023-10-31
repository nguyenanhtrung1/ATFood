<?php
include "connect.php";

$tensanpham = $_POST['tensanpham'];
$giasanpham = $_POST['giasanpham'];
$hinhanh = $_POST['hinhanh'];
$mota = $_POST['mota'];
$macuahang = $_POST['macuahang'];
$masanpham = $_POST['masanpham'];

$query = 'UPDATE `sanpham` SET `tensanpham`="'.$tensanpham.'",`giasanpham`="'.$giasanpham.'",`hinhanh`="'.$hinhanh.'",`mota`="'.$mota.'",`macuahang`='.$macuahang.' WHERE `masanpham` = ' .$masanpham;	
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