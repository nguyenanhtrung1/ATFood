<?php  
$target_dir = "images/";  
$target_file_name = $target_dir .basename($_FILES["file"]["name"]);    

if (isset($_FILES["file"]))  
   {  
   if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name)) 

      {  
           $arr = [
            'success' => true,
            'message' => "thanh cong"
            
         ]; 
      }  
   else  
      {  
         $arr = [
         'success' => false,
         'message' => "khong thanh cong"
         ];
      }  
   }  
else  
   {  
      $arr = [
         'success' => false,
         'message' => "Loi"
      ];
   }
   echo json_encode($arr);  
?>  