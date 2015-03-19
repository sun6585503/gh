package com.sunkun.gh.crawler.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.sunkun.gh.code.OCR;
import com.sunkun.gh.util.LogUtil;

public class LoginTool {

	public static String homePageURL="http://www.bjguahao.gov.cn/comm/index.html";
	
	public static String getVeriCodeURL="http://www.bjguahao.gov.cn/comm/code.php";
	
	public static String logonURL="http://www.bjguahao.gov.cn/comm/logon.php";
	
	
	public static Map<String, String> cookies = new HashMap<String, String>();
	
	public static  Map<String, String> loginRetry(String name,String id,int times)
	{
		int time=0;
	while(time<times)
	{
		time++;
		Map<String, String> cookie = login(name,id);
		
		if(cookie==null)
			continue;
		else
			return cookies;
	}
	
	return null; 
	}
	
	
	public static  Map<String, String> login(String name,String id)
	{
		//Map<String, String> cookies = new HashMap<String, String>();
		
		if(!cookies.isEmpty())
			return cookies;

		try {
			//读取验证码，实际不使用这个验证码，只是加载Cookie
			CookieTool.getCookies(getVeriCodeURL);
			
			String code=VerificationCodeTool.getVerificationCode(name, id);
			
			if(code.equalsIgnoreCase(""))
				{
				LogUtil.printLog(LoginTool.class, "验证码读取失败");
				System.exit(0);
				}
			LoginTool.post(logonURL, name, id,code, "http://www.bjguahao.gov.cn/comm/yyks-1.html", "GBK");
			HtmlGetter.getHtmlDoc("http://www.bjguahao.gov.cn/comm/plus/ajax_user.php?act=top_loginform", "http://www.bjguahao.gov.cn/comm/index.html");
			
		} catch (MalformedURLException e) {
			LogUtil.printLog(LoginTool.class, "登陆失败!");
			cookies.clear();
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.printLog(LoginTool.class, "登陆失败!");
			cookies.clear();
			return null;
		}

		   
		return cookies;
	}
	
	private static String getCode() throws MalformedURLException, IOException,
			FileNotFoundException, Exception {
		Connection conn = Jsoup.connect(getVeriCodeURL).header("UserAgent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36").header("Accept", "image/webp,*/*;q=0.8").header("Referer", "http://www.bjguahao.gov.cn/comm/index.html").timeout(8000); 
		conn.ignoreContentType(true);
		conn.method(Method.GET); 
		conn.followRedirects(true); 
		LogUtil.printLog(LoginTool.class, "开始读取验证码图片");
		Response responses= conn.execute(); 
		LogUtil.printLog(LoginTool.class, "完成读取验证码图片");
		cookies.putAll(responses.cookies());
		
		byte[] bytes = responses.bodyAsBytes();
		
		   File file2 = new File("code.gif");
		   if(!file2.exists())
			   file2.createNewFile();
		   FileOutputStream fos =new FileOutputStream(file2);
		  fos.write(bytes);
		   fos.flush();
		   fos.close();
		
		   String valCode = new OCR().recognizeText(new File("code.gif"), "gif");
		   valCode=valCode.substring(0, 4);
		return valCode;
	}
	public static void main(String args[])
	{
		/*try {
			LoginTool.login("孙鲲", "23100419831104121X");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				LoginTool.logout();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}*/
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://www.163.com");
			httpPost.setHeader("Origin", "http://www.bjguahao.gov.cn");
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			nvps.add(new BasicNameValuePair("username", "vip"));
			nvps.add(new BasicNameValuePair("password", "secret"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse response2 = httpclient.execute(httpPost);

			try {
			    System.out.println(response2.getStatusLine());
			    HttpEntity entity2 = response2.getEntity();
			    // do something useful with the response body
			    // and ensure it is fully consumed
			    EntityUtils.consume(entity2);
			} finally {
			    response2.close();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void post(String url, String name, String sfzhm,String yzm,String ref,String charset) throws Exception {
		  URL urls = new URL(url);
		  HttpURLConnection connection = null;
		  connection = (HttpURLConnection)urls.openConnection();//建立链接
		  
		  Iterator<String> it = cookies.keySet().iterator();
		  String cookie="";
		  
		  while(it.hasNext())
		  {
			  String key = it.next();
			  String value=cookies.get(key);
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
				  String login="&"+"sfzhm="+sfzhm+"&yzm="+yzm;
				  output.write(login.getBytes());
				  //out.write(login);
				  output.flush();
				  output.close();
				  InputStream inputStream = connection.getInputStream();
				  /*
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
			        */
			        
				  BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				  //InputStreamReader reader=new InputStreamReader(inputStream,"UTF-8");
				   String inputLine; 
		            while((inputLine = reader.readLine())!= null){  
		            	//System.out.println(VerificationCodeTool.uncompress(inputLine.getBytes()));
		            	if(inputLine.length()==26)
		            		LogUtil.printLog(LoginTool.class, "登陆成功");
		            	else
		            		{
		            		//LogUtil.printLog(LoginTool.class, "登陆失败");
		            		//cookies.clear();
		            		
		            		
		            		throw new Exception();
		            		}
		            }
				//  BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputStream1,"utf-8"));
				//  String line1 = reader1.readLine();
				//  while(line1 != null){
				//   System.out.println(line1);
				//   line1 = reader1.readLine();
				//  }
				//  reader1.close();
	
	}
	
	public static String post(String url,String data,String ref) throws Exception {
		//System.setProperty("sun.net.http.allowRestrictedHeaders", "true");  
		URL urls = new URL(url);
		  HttpURLConnection connection = null;
		  connection = (HttpURLConnection)urls.openConnection();//建立链接
		  
		  Iterator<String> it = cookies.keySet().iterator();
		  String cookie="";
		  
		  while(it.hasNext())
		  {
			  String key = it.next();
			  String value=cookies.get(key);
			  cookie+=key+"="+value+";";
		  }
		  connection.addRequestProperty("Cookie",cookie);
		  
		  connection.setInstanceFollowRedirects(false);
		  connection.setRequestProperty("Connection","keep-alive");
		  
		
		  connection.setDoOutput(true);
		  connection.setRequestMethod("POST");
		  connection.addRequestProperty("User-Agent", 
				    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
	  
		  connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				  connection.setRequestProperty("Referer",ref);
				  connection.setRequestProperty("Accept-Encoding", "gzip, deflate"); 
				  connection.setRequestProperty("Cache-Control", "max-age=0"); 
				  connection.addRequestProperty("Origin", "http://www.bjguahao.gov.cn"); 
					 
				  connection.setRequestProperty("Accept", "*/*"); 
				  connection.setRequestProperty("X-Requested-With", "XMLHttpRequest"); 
				  
				  //OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
				  OutputStream output = connection.getOutputStream();
				  //name=new String(name.getBytes("UTF-8"));
				  output.write(data.getBytes());
				 
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
			        
			        try {
						String result=VerificationCodeTool.uncompress(str);
						return result;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						return new String(str);
					}
				  
				//  BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputStream1,"utf-8"));
				//  String line1 = reader1.readLine();
				//  while(line1 != null){
				//   System.out.println(line1);
				//   line1 = reader1.readLine();
				//  }
				//  reader1.close();
	
	}
	
	 public static void postWithHttpClient(String url,List <NameValuePair> nvps,String ref)
	 {
		 try {
				CloseableHttpClient httpclient = HttpClients.createDefault();
				HttpPost httpPost = new HttpPost(url);
				
				Iterator<String> it = cookies.keySet().iterator();
				  String cookie="";
				  
				  while(it.hasNext())
				  {
					  String key = it.next();
					  String value=cookies.get(key);
					  cookie+=key+"="+value+";";
				  }
				  cookie=cookie.substring(0, cookie.length()-1);
				  
				
				httpPost.addHeader("Cookie", cookie);
				httpPost.addHeader("User-Agent", 
					    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
		  
				httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
				httpPost.addHeader("Referer",ref);
				httpPost.addHeader("Accept-Encoding", "gzip, deflate"); 
				httpPost.addHeader("Cache-Control", "max-age=0"); 
				httpPost.addHeader("Origin", "http://www.bjguahao.gov.cn"); 
				httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8"); 
				httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"); 
				httpPost.addHeader("X-Requested-With", "XMLHttpRequest"); 
					 
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				CloseableHttpResponse response2 = httpclient.execute(httpPost);

				try {
				    System.out.println(response2.getStatusLine());
				    HttpEntity entity2 = response2.getEntity();
				    // do something useful with the response body
				    // and ensure it is fully consumed
				    EntityUtils.consume(entity2);
				} finally {
				    response2.close();
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	
	public static void logout() throws IOException
	{
		
		URL url=new URL("http://www.bjguahao.gov.cn/comm/logout.php");
		
		Connection conn = Jsoup.connect(url.toString()).cookies(cookies).header("UserAgent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36").header("Accept", "image/webp,*/*;q=0.8").header("Referer", "http://www.bjguahao.gov.cn/comm/index.html").timeout(8000); 
		//conn.execute();
		conn.method(Method.GET); 
		conn.followRedirects(true); 
		Response responses= conn.execute(); 
		LogUtil.printLog(LoginTool.class, "退出登陆");
	}
}
