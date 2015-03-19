package com.sunkun.gh.crawler;

import java.io.IOException;
import java.util.Map;

import org.jsoup.nodes.Document;

import com.sunkun.gh.crawler.network.HtmlGetter;
import com.sunkun.gh.crawler.network.LoginTool;
import com.sunkun.gh.crawler.parser.HopitalPageParser;
import com.sunkun.gh.domain.Hospital;
import com.sunkun.gh.element.PageElement;
import com.sunkun.gh.util.LogUtil;

public class CrawlerThread implements Runnable{

	public int getHospitalID() {
		return id;
	}

	public void setHospitalID(int id) {
		this.id = id;
	}

	int id=1;
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			try {
				Map<String, String> cookies = LoginTool.loginRetry("Ëïöï", "23100419831104121X",10);
				if(cookies==null)
					{
					return;
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("µÇÂ½Ê§°Ü");
				LoginTool.logout();
				return;
			}
			/*PageElement pageElement=new PageElement();
			
			pageElement.setDoc(HtmlGetter.getHtmlDoc(url,""));
			pageElement.setReferer(url);*/
			
			Hospital hospital=new Hospital();
			hospital.setId(id);
			HopitalPageParser parser=new HopitalPageParser();
			parser.parser(hospital);
			//System.out.println(hospital);
			LogUtil.printLog(this, hospital.toString());
			LoginTool.logout();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			try {
				LoginTool.logout();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				LoginTool.logout();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static void main(String args[])
	{
		
		CrawlerThread task=new CrawlerThread();
		task.setHospitalID(1);
		Thread t=new Thread(task);
		t.start();
	}

}
