<?php 
class tashfer{

//dynamic assign 
var $keyarray=array();
var $key;
var $blocksize;
var $shiftsize;

//function keys
var $sysbase=256;
var $keybase=27;


function tashferstr($str,$keyval){

$this->generatekey($keyval);

$strencod="";
$looper=strlen($str);
$offset=0;
$blockedarry=array();

while($looper>$offset){

$val = ord(substr($str, $offset,1));

if($offset%$this->blocksize==0){

if(count($blockedarry)>0)
  $strencod=$strencod.$this->tashferblock($blockedarry);

  $blockedarry=array();
  }

 $blockedarry[]=$val;
 $offset++;

}


if(count($blockedarry)>0)
  $strencod=$strencod.$this->tashferblock($blockedarry);

return $strencod;

}




function tashferblock($blockarray){

$shiftedarry=$this->shiftarray($blockarray,$this->shiftsize);
$arrlen=count($shiftedarry);
$blockstr="";
$offset=0;

while($arrlen>$offset){

$valchar=($this->sysbase-$shiftedarry[$offset])+$this->keyarray[$offset];

if($valchar>$this->sysbase)
    $valchar-=$this->sysbase;




$blockstr=$blockstr.$this->base64($valchar);
$offset++;

}


return $blockstr;

}






function fakstr($str,$keyval){

$this->generatekey($keyval);
$strencod="";

$looper=strlen($str);
$offset=0;
$blockedarry=array();
$offsetkey=0;
while($looper>$offset){

$val =$this->debase64(substr($str, $offset,2));

if($offset%$this->blocksize==0){

if(count($blockedarry)>0)
  $strencod=$strencod.$this->fakblock($blockedarry);

  $blockedarry=array();
  $offsetkey=0;
  }

 $valchar=$val-$this->keyarray[$offsetkey];

 $blockedarry[]=$this->sysbase-$valchar;
 $offset+=2;
 $offsetkey++;

}

if(count($blockedarry)>0)
  $strencod=$strencod.$this->fakblock($blockedarry);

return $strencod;

}





function fakblock($blockarray){

$shiftedarry=$this->shiftarray($blockarray,(count($blockarray)-$this->shiftsize));

$arrlen=count($shiftedarry);
$blockstr="";
$offset=0;

while($arrlen>$offset){

$valchar=$shiftedarry[$offset];
if($valchar<0)
    $valchar+=$this->sysbase;


$blockstr=$blockstr.chr($valchar);
$offset++;

}

return $blockstr;

}





function shiftarray($array,$shiftval=1){

$shiftedarry=array();

$arrlen=count($array);
$looper=0;
while($arrlen>$looper){
$vastr=$array[(($looper+$shiftval)%$arrlen)];
$shiftedarry[]=$vastr;

$looper++;

}

return $shiftedarry;

}





function generatekey($keyval){

$this->key=$keyval;
$this->keyarray=array();

$this->keyarray[]=19;
$modval=0;
$this->shiftsize=1;
while($keyval>1){

$modval=$keyval%$this->keybase;
$this->keyarray[]=$modval+1;
$keyval=($keyval-$modval)/$this->keybase;
$this->shiftsize+=$modval;
$this->keyarray[]=($this->shiftsize%$this->keybase);
}
$this->keyarray[]=29;
$this->keyarray[]=37;

$this->blocksize=count($this->keyarray);
$this->shiftsize=1;//$this->shiftsize%$this->blocksize;

return $this->keyarray;

}



function generaterandamkey(){

$randkey=5846247925;

$key1=rand(1,9999);
$key2=rand(1, 9999);
$key3=rand(1, 999);
$randkey=$key1.$key2.$key3;

return $randkey;

}




function base64($valchar){

$base64table=array('00','01','02','03','04','05','06','07','08','09','0q','0w','0e','0r','0t','0y','10','11','12','13','14','15','16','17','18','19','1q','1w','1e','1r','1t','1y','20','21','22','23','24','25','26','27','28','29','2q','2w','2e','2r','2t','2y','30','31','32','33','34','35','36','37','38','39','3q','3w','3e','3r','3t','3y','40','41','42','43','44','45','46','47','48','49','4q','4w','4e','4r','4t','4y','50','51','52','53','54','55','56','57','58','59','5q','5w','5e','5r','5t','5y','60','61','62','63','64','65','66','67','68','69','6q','6w','6e','6r','6t','6y','70','71','72','73','74','75','76','77','78','79','7q','7w','7e','7r','7t','7y','80','81','82','83','84','85','86','87','88','89','8q','8w','8e','8r','8t','8y','90','91','92','93','94','95','96','97','98','99','9q','9w','9e','9r','9t','9y','q0','q1','q2','q3','q4','q5','q6','q7','q8','q9','qq','qw','qe','qr','qt','qy','w0','w1','w2','w3','w4','w5','w6','w7','w8','w9','wq','ww','we','wr','wt','wy','e0','e1','e2','e3','e4','e5','e6','e7','e8','e9','eq','ew','ee','er','et','ey','r0','r1','r2','r3','r4','r5','r6','r7','r8','r9','rq','rw','re','rr','rt','ry','t0','t1','t2','t3','t4','t5','t6','t7','t8','t9','tq','tw','te','tr','tt','ty','y0','y1','y2','y3','y4','y5','y6','y7','y8','y9','yq','yw','ye','yr','yt','yy');

return $base64table[$valchar];


}



function debase64($valchar){
$base64table=array('00','01','02','03','04','05','06','07','08','09','0q','0w','0e','0r','0t','0y','10','11','12','13','14','15','16','17','18','19','1q','1w','1e','1r','1t','1y','20','21','22','23','24','25','26','27','28','29','2q','2w','2e','2r','2t','2y','30','31','32','33','34','35','36','37','38','39','3q','3w','3e','3r','3t','3y','40','41','42','43','44','45','46','47','48','49','4q','4w','4e','4r','4t','4y','50','51','52','53','54','55','56','57','58','59','5q','5w','5e','5r','5t','5y','60','61','62','63','64','65','66','67','68','69','6q','6w','6e','6r','6t','6y','70','71','72','73','74','75','76','77','78','79','7q','7w','7e','7r','7t','7y','80','81','82','83','84','85','86','87','88','89','8q','8w','8e','8r','8t','8y','90','91','92','93','94','95','96','97','98','99','9q','9w','9e','9r','9t','9y','q0','q1','q2','q3','q4','q5','q6','q7','q8','q9','qq','qw','qe','qr','qt','qy','w0','w1','w2','w3','w4','w5','w6','w7','w8','w9','wq','ww','we','wr','wt','wy','e0','e1','e2','e3','e4','e5','e6','e7','e8','e9','eq','ew','ee','er','et','ey','r0','r1','r2','r3','r4','r5','r6','r7','r8','r9','rq','rw','re','rr','rt','ry','t0','t1','t2','t3','t4','t5','t6','t7','t8','t9','tq','tw','te','tr','tt','ty','y0','y1','y2','y3','y4','y5','y6','y7','y8','y9','yq','yw','ye','yr','yt','yy');

$looperv=count($base64table);
$index=0;
while($index<$looperv){

if($valchar==$base64table[$index])return $index;

$index++;
}

return -1;

}



}//end of class



?>
