<?php
include "connect.php";

$tencuahang = $_POST['tencuahang'];
$diachi = $_POST['diachi'];
$hinhanh = $_POST['hinhanh'];
$danhmuc = $_POST['danhmuc'];
$macuahang = $_POST['macuahang'];

$query = 'UPDATE `cuahang` SET `tencuahang`="'.$tencuahang.'",`diachi`="'.$diachi.'",`hinhanh`="'.$hinhanh.'",`danhmuc`="'.$danhmuc.'" WHERE `macuahang` = ' .$macuahang;
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