package com.sunkun.gh.crawler.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

public class VerificationCodeTool {

	public  static String getVerificationCode(String name,String sfzhm) throws IOException
	{
		String code="";
		URL urls = new URL(LoginTool.logonURL);
		  HttpURLConnection connection = null;
		  connection = (HttpURLConnection)urls.openConnection();//建立链接
		  
		  Iterator<String> it = LoginTool.cookies.keySet().iterator();
		  String cookie="";
		  
		  while(it.hasNext())
		  {
			  String key = it.next();
			  String value=LoginTool.cookies.get(key);
			  cookie+=key+"="+value+";";
		  }
		  connection.addRequestProperty("Cookie",cookie);
		  
		  connection.setInstanceFollowRedirects(false);
		  connection.setRequestProperty("Connection","keep-alive");
		  connection.setDoInput(true);
		  connection.setDoOutput(true);
		  connection.setRequestMethod("POST");
		  connection.setRequestProperty("User-Agent", 
				    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
				  connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				  connection.setRequestProperty("Referer", "http://www.bjguahao.gov.cn/comm/index.html");
				  connection.setRequestProperty("Accept-Encoding", "gzip, deflate"); 
				  connection.setRequestProperty("Origin", "http://www.bjguahao.gov.cn"); 
				  connection.setRequestProperty("Accept", "*/*"); 
				  connection.setRequestProperty("X-Requested-With", "XMLHttpRequest"); 
				  
				  //OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
				  OutputStream output = connection.getOutputStream();
				  //name=new String(name.getBytes("UTF-8"));
				  output.write("truename=".getBytes());
				  output.write(name.getBytes("UTF-8"));
				  String login="&"+"sfzhm="+sfzhm+"&yzm="+"1234";
				  output.write(login.getBytes());
				  //out.write(login);
				  output.flush();
				  output.close();
				  InputStream inputStream = connection.getInputStream();
				  
			        byte b[]=new byte[100]; 
			        int length;//创建合适文件大小的数组   
			        while(true)
			        {
			        	length=inputStream.read(b);    //读取文件中的内容到b[]数组 
			        break;
			        }
			        
			        byte[] str=new byte[length];
			        
			        for(int i=0;i<length;i++)
			        {
			        	str[i]=b[i];
			        }
			        
			        String result=uncompress(str);
			        
			        if(result.contains("验证码错误"))
			        {
			        	code=result.split("!")[1];
			        }
			        //Message: GET /comm/plus/ajax_user.php?act=top_loginform HTTP/1.1\r\n
			        return code;
	}
	 // 解压缩   
	 public static String uncompress(byte[] str) throws IOException {   
	/*    if (str == null || str.length() == 0) {   
	      return str;   
	  } */  
	   ByteArrayOutputStream out = new ByteArrayOutputStream();   
	   ByteArrayInputStream in = new ByteArrayInputStream(str   
	        );   
	    GZIPInputStream gunzip = new GZIPInputStream(in);   
	    byte[] buffer = new byte[256];   
	    int n;   
	   while ((n = gunzip.read(buffer))>= 0) {   
	    out.write(buffer, 0, n);   
	    }   
	    // toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)   
	    return out.toString();   
	  } 
	 
	 public static void main(String args[])
	 {
		 try {
			 CookieTool.getCookies("");
			 VerificationCodeTool.getVerificationCode("孙鲲", "23100419831104121X");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
