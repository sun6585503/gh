package com.sunkun.gh.code;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import java.util.Base64;
import java.util.zip.GZIPOutputStream;

public class ChineseCode {
public static void main(String args[])
{

	  String name="Λοφο";

		try {
			byte[] bname = name.getBytes("UTF-8");
			new String(bname,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	  
	  
	//  name=new String(b,"ISO8859_1");

}

public static byte[] compressToByte(String str){
    if (str == null || str.length() == 0) {
        return null;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    GZIPOutputStream gzip;
    try {
        gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    //new String(new Base64().encode(out.toByteArray()));
    new String(out.toByteArray());
    return out.toByteArray();
}
}
