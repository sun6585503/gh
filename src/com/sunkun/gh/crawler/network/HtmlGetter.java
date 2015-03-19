package com.sunkun.gh.crawler.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlGetter {

	
	public static Document getHtmlDoc(String surl,String ref) throws IOException
	{
		/*Map<String, String> cookies = new HashMap<String, String>(); 
		cookies.put("Hm_lvt_13e29334f151c8514bf6cf2533b9d9af", "1417232485"); 
		cookies.put("Hm_lpvt_65f844e6a6e140ab52d02690ed38a38b", "1425614539"); 
		cookies.put("Hm_lvt_65f844e6a6e140ab52d02690ed38a38b", "1425545508,1425569916,1425614494"); 
		cookies.put("__c_visitor", "1417232454454071"); 
		*///cookies.put("Hm_lvt_65f844e6a6e140ab52d02690ed38a38b", "1425545508,1425569916,1425614494"); 
		// res = Jsoup.connect(surl).timeout(30000).execute();
		
		//cookies.put("__c_L2A3L4v5F38482H8cai9L2A3L4v5F38482H82f9CQ9cwbn6kc", "a93ce2cd7705aeba4bb41de2c326c9e6"); 

		//Hm_lvt_13e29334f151c8514bf6cf2533b9d9af=1417232485; __c_review_45359=0; __c_last_45359=1417233003800; __c_visitor=1417232454454071; __c_dz6B30a07i2ztmYcai9dz6B30a07i2ztmY41Jo4mO36=e893945a3ca9b3aad30436cf44e8fa41; Hm_lvt_65f844e6a6e140ab52d02690ed38a38b=1425545508,1425569916,1425614494; Hm_lpvt_65f844e6a6e140ab52d02690ed38a38b=1425614539
		//Response res = Jsoup.connect("http://www.bjguahao.gov.cn/comm").timeout(30000).execute(); 
		//cookies = res.cookies(); 

		URL url = new URL(surl); 
		//URL url = new URL("http://www.bjguahao.gov.cn/comm/content.php?hpid=143&keid=01"); 
		//User-Agent: Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36\r\n
	
		Connection conn = Jsoup.connect(url.toString()).
				header("Accept", "*/*")
				.header("X-Requested-With","XMLHttpRequest")
				.header("User-Agent","Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36")
				.header("Accept-Encoding","gzip,deflate,sdch")
				.header("Accept-Language","zh-CN,zh;q=0.8")
				.cookies(LoginTool.cookies).header("Referer", ref).timeout(10000); 
		conn.method(Method.GET); 
		
		conn.followRedirects(true); 
		Response response = conn.execute(); 
		Document doc = response.parse(); 
		
		return doc;
	}

	
	public static void main(String[] args) 
	{       
		try {
			Map<String, String> cookies = LoginTool.loginRetry("Λοφο", "23100419831104121X",10);
			
			if(cookies==null)
			return;
			//;
			//System.out.println(res);
			Document doc;
			//?hpid=112&keid=GRK
			//URLEncoder.encode("&", "ASCII")
			//Hm_lvt_65f844e6a6e140ab52d02690ed38a38b=1425534893; Hm_lpvt_65f844e6a6e140ab52d02690ed38a38b=1425552523; __c_L2A3L4v5F38482H8cai9L2A3L4v5F38482H82f9CQ9cwbn6kc=a93ce2cd7705aeba4bb41de2c326c9e6
			
				String url="http://www.bjguahao.gov.cn/comm/content.php?hpid=1&keid=1130017";
				doc=getHtmlDoc(url,"http://www.bjguahao.gov.cn/comm/yyks-1.html");

				System.out.println(doc);
				LoginTool.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}    
	
}
