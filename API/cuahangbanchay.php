<?php
include "connect.php";
$danhmuc = $_POST['danhmuc'];

$query = 'SELECT cuahang.tencuahang, COUNT(*) AS tongSoLuong
        FROM chitietdonhang
        JOIN sanpham ON chitietdonhang.masanpham = sanpham.masanpham
        JOIN cuahang ON sanpham.macuahang = cuahang.macuahang
        GROUP BY cuahang.tencuahang
        ORDER BY tongSoLuong DESC
        LIMIT 5';
$data = mysqli_query($conn, $query);
$result = array();
while($row = mysqli_fetch_assoc($data)){
	$result[] = ($row);
}

if(!empty($result)){

	$arr = [
		'success' => true,
		'message' => "thanh cong",
		'result' => $result
	]; 
}else{
	$arr = [
		'success' => false,
		'message' => "khong thanh cong",
		'result' => $result
	];
 }
print_r(json_encode($arr));

?>