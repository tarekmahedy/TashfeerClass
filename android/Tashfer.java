package tarekmahedy.app.Trusted.classes;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import android.util.Log;


public class Tashfer {

	ArrayList<Integer> keyarray;

	public double key;
	int blocksize;
	int shiftsize;
	//function keys
	public int sysbase=256;
	public int keybase=27;


	public String  tashferstr(char[] str,double keyval){

		this.generatekey(keyval);

		String strencod="";
		int looper=str.length;
		int offset=0;
		ArrayList<Integer>blockedarry=new ArrayList<Integer>();

		while(looper>offset){

			int val =(int)str[offset];
			if(offset%this.blocksize==0){

				if(blockedarry.size()>0)
					strencod=strencod+this.tashferblock(blockedarry);

				blockedarry=new ArrayList<Integer>();
			}

			blockedarry.add(val);
			offset++;

		}
		if(blockedarry.size()>0)
			strencod=strencod.concat(this.tashferblock(blockedarry));

		return strencod;

	}



	String tashferblock(ArrayList<Integer> blockarray){

		ArrayList<Integer> shiftedarry=this.shiftarray(blockarray,this.shiftsize);
		int arrlen=shiftedarry.size();
		int offset=0;
		StringBuilder blockstrbuilder=new StringBuilder();
		while(arrlen>offset){

			int valchar=(this.sysbase-shiftedarry.get(offset))+this.keyarray.get(offset);

			if(valchar>this.sysbase)
				valchar-=this.sysbase;

			blockstrbuilder.append(base64(valchar));
			offset++;

		}

		return blockstrbuilder.toString();

	}



	public String  fakstr(String str,double keyval){

		this.generatekey(keyval);

		String strencod="";
		int looper=str.length();
		int offset=0;
		ArrayList<Integer>blockedarry=new ArrayList<Integer>();

		int offsetkey=0;
		while(looper>offset){
			
			
			int val =debase64(str.substring(offset, (offset+2)));
			
			if(offset%this.blocksize==0){

				if(blockedarry.size()>0)
					strencod=strencod+this.fakblock(blockedarry);

				blockedarry=new ArrayList<Integer>();
				offsetkey=0;
			}

			int valchar=val-this.keyarray.get(offsetkey);

			blockedarry.add((this.sysbase-valchar));
			offset+=2;
			offsetkey++;

		}

		if(blockedarry.size()>0)
			strencod=strencod.concat(this.fakblock(blockedarry));
		
		Log.v("terfsg:", strencod);
		
		return strencod;

	}

	

	String fakblock(ArrayList<Integer> blockarray){

		ArrayList<Integer> shiftedarry=this.shiftarray(blockarray,(blockarray.size()-this.shiftsize));
		int arrlen=shiftedarry.size();
		
		byte[] blockbytes =new byte[arrlen];
		String blockstr="";
		int offset=0;
		while(arrlen>offset){

			int valchar=shiftedarry.get(offset);
			if(valchar<0)
				valchar+=this.sysbase;

			blockbytes[offset]=(byte) valchar;
			offset++;

		}
	try {
			blockstr=new String(blockbytes,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blockstr;
	}



	public String base64(int valchar){

	String []base64table=new String []{"00","01","02","03","04","05","06","07","08","09","0q","0w","0e","0r","0t","0y","10","11","12","13","14","15","16","17","18","19","1q","1w","1e","1r","1t","1y","20","21","22","23","24","25","26","27","28","29","2q","2w","2e","2r","2t","2y","30","31","32","33","34","35","36","37","38","39","3q","3w","3e","3r","3t","3y","40","41","42","43","44","45","46","47","48","49","4q","4w","4e","4r","4t","4y","50","51","52","53","54","55","56","57","58","59","5q","5w","5e","5r","5t","5y","60","61","62","63","64","65","66","67","68","69","6q","6w","6e","6r","6t","6y","70","71","72","73","74","75","76","77","78","79","7q","7w","7e","7r","7t","7y","80","81","82","83","84","85","86","87","88","89","8q","8w","8e","8r","8t","8y","90","91","92","93","94","95","96","97","98","99","9q","9w","9e","9r","9t","9y","q0","q1","q2","q3","q4","q5","q6","q7","q8","q9","qq","qw","qe","qr","qt","qy","w0","w1","w2","w3","w4","w5","w6","w7","w8","w9","wq","ww","we","wr","wt","wy","e0","e1","e2","e3","e4","e5","e6","e7","e8","e9","eq","ew","ee","er","et","ey","r0","r1","r2","r3","r4","r5","r6","r7","r8","r9","rq","rw","re","rr","rt","ry","t0","t1","t2","t3","t4","t5","t6","t7","t8","t9","tq","tw","te","tr","tt","ty","y0","y1","y2","y3","y4","y5","y6","y7","y8","y9","yq","yw","ye","yr","yt","yy"};

	return base64table[valchar];


	}


	public int debase64(String valchar){
		
		String []base64table=new String []{"00","01","02","03","04","05","06","07","08","09","0q","0w","0e","0r","0t","0y","10","11","12","13","14","15","16","17","18","19","1q","1w","1e","1r","1t","1y","20","21","22","23","24","25","26","27","28","29","2q","2w","2e","2r","2t","2y","30","31","32","33","34","35","36","37","38","39","3q","3w","3e","3r","3t","3y","40","41","42","43","44","45","46","47","48","49","4q","4w","4e","4r","4t","4y","50","51","52","53","54","55","56","57","58","59","5q","5w","5e","5r","5t","5y","60","61","62","63","64","65","66","67","68","69","6q","6w","6e","6r","6t","6y","70","71","72","73","74","75","76","77","78","79","7q","7w","7e","7r","7t","7y","80","81","82","83","84","85","86","87","88","89","8q","8w","8e","8r","8t","8y","90","91","92","93","94","95","96","97","98","99","9q","9w","9e","9r","9t","9y","q0","q1","q2","q3","q4","q5","q6","q7","q8","q9","qq","qw","qe","qr","qt","qy","w0","w1","w2","w3","w4","w5","w6","w7","w8","w9","wq","ww","we","wr","wt","wy","e0","e1","e2","e3","e4","e5","e6","e7","e8","e9","eq","ew","ee","er","et","ey","r0","r1","r2","r3","r4","r5","r6","r7","r8","r9","rq","rw","re","rr","rt","ry","t0","t1","t2","t3","t4","t5","t6","t7","t8","t9","tq","tw","te","tr","tt","ty","y0","y1","y2","y3","y4","y5","y6","y7","y8","y9","yq","yw","ye","yr","yt","yy"};

	int looperv=base64table.length;
	int index=0;
	while(index<looperv){

	if(valchar.equals(base64table[index]))return index;

	index++;
	}

	return -1;

	}

	
	
	ArrayList<Integer> shiftarray(ArrayList<Integer> array,int shiftval){

		ArrayList<Integer>shiftedarry=new ArrayList<Integer>();
		int arrlen=array.size();
		int looper=0;
		while(arrlen>looper){
			int vastr=array.get(((looper+shiftval)%arrlen));
			shiftedarry.add(vastr);
			looper++;

		}

		return shiftedarry;
	}


	

	ArrayList<Integer> generatekey(double keyval){

		this.key=keyval;
		this.keyarray=new ArrayList<Integer>();
		this.keyarray.add(19);

		int modval=0;
		this.shiftsize=1;
		while(keyval>1){

			modval=(int) (keyval%this.keybase);
			this.keyarray.add(modval+1);
			keyval=(keyval-modval)/this.keybase;
			this.shiftsize+=modval;
			this.keyarray.add((this.shiftsize%this.keybase));

		}
		this.keyarray.add(29);
		this.keyarray.add(37);

		this.blocksize=this.keyarray.size();
		this.shiftsize=1;//this.shiftsize%this.blocksize;

		return this.keyarray;

	}



}
