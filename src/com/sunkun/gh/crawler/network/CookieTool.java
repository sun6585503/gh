package com.sunkun.gh.crawler.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.sunkun.gh.util.LogUtil;

public class CookieTool {
	
	public static void getCookies(String url) throws IOException
	{
		if(url==null||url.equalsIgnoreCase(""))
			url=LoginTool.getVeriCodeURL;
		Connection conn = Jsoup.connect(url.toString()).header("UserAgent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36").header("Accept", "image/webp,*/*;q=0.8").header("Referer", "http://www.bjguahao.gov.cn/comm/index.html").timeout(8000); 
		conn.ignoreContentType(true);
		conn.method(Method.GET); 
		conn.followRedirects(true); 
		LogUtil.printLog(LoginTool.class, "开始读取验证码图片");
		Response responses= conn.execute(); 
		LogUtil.printLog(LoginTool.class, "完成读取验证码图片");
		LoginTool.cookies.putAll(responses.cookies());
	}

}
