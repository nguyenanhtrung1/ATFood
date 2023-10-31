<?php
include "connect.php";
$macuahang = $_POST['macuahang'];

$queryXoaSp = 'DELETE FROM `sanpham` WHERE `macuahang` = '.$macuahang.'';
$data2 = mysqli_query($conn, $queryXoaSp);

$queryXoaCh = 'DELETE  FROM `cuahang` WHERE `macuahang` = '.$macuahang.'';
$data = mysqli_query($conn, $queryXoaCh);

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