<?php
include "connect.php";
$maloaisanpham = $_POST['maloaisanpham'];

$queryXoaCh = 'DELETE  FROM `cuahang` WHERE `danhmuc` = '.$maloaisanpham.'';
$data = mysqli_query($conn, $queryXoaCh);

$query= 'DELETE FROM `loaisanpham` WHERE `maloaisanpham` = '.$maloaisanpham.'';
$data = mysqli_query($conn, $query);

	if($data == true){
		
		$arr = [ 
			'success' => true,
			'message' => "Xoá thành công"
		];
	}else{
		$arr = [
			'success' => false,
			'message' => "Xoá không thành công"
		];
	}
print_r(json_encode($arr));


?>