<?php
 
 include("tashfer.php");

 $shafar=new tashfer();
 $keyva=256423;
 $textclear="test message  رسالة ببلغة العربية";

 $encclear=$shafar->tashferstr(trim($textclear),$keyva);
 echo "EnC Text : ".$encclear;

 $dataclear=$shafar->fakstr(trim($encclear),$keyva);
 echo "<br/> plain Text : ". $dataclear;
 



?>
