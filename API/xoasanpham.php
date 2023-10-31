<?php
include "connect.php";
$masanpham = $_POST['masanpham'];

$query= 'DELETE FROM `sanpham` WHERE `masanpham` = '.$masanpham.'';
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