<?php
include "connect.php";

$id = $_POST['id'];
$token = $_POST['token'];

$query = 'UPDATE `user` SET `token`="'.$token.'" WHERE `id` = '.$id;
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