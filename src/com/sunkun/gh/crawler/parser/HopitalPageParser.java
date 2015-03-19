package com.sunkun.gh.crawler.parser;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.sunkun.gh.crawler.network.HtmlGetter;
import com.sunkun.gh.domain.Department;
import com.sunkun.gh.domain.Hospital;
import com.sunkun.gh.domain.operator.DBTool;
import com.sunkun.gh.element.PageElement;
import com.sunkun.gh.util.LogUtil;

public class HopitalPageParser {

	public void parser(Hospital hospital) throws Exception
	{
		String url = "http://www.bjguahao.gov.cn/comm/yyks-"+hospital.getId()+".html";
		
		hospital.setPageURl(url);
		
		Document doc = HtmlGetter.getHtmlDoc(url,"");
		//
		Elements hospitalElements = doc.getElementsByClass("hospital");
		
		Element hospitalElement = hospitalElements.get(0);
		
		Elements nameElements=hospitalElement.getElementsByTag("p");
		
		String name=nameElements.get(0).text();
		
		hospital.setName(name);
		
		Elements labelElements=hospitalElement.getElementsByTag("label").get(0).children();
		 List<TextNode> textNodes = hospitalElement.getElementsByTag("label").get(0).textNodes();
        
		 int labelIndex=0;
        
        for(;labelIndex<labelElements.size();labelIndex++)
        {
        	String key=labelElements.get(labelIndex).text();
        	
        	String value=textNodes.get(labelIndex).text();
        	matchKeyValue(hospital,key,value);
        }

	
		Elements categoryMentElements = doc.getElementsByClass("yyksbox");
		
		for(Element element:categoryMentElements)
		{
			CategoryParser parser=new CategoryParser();			
			
			parser.parser(element, hospital);
		}
	}
	private static void matchKeyValue(Hospital hospital,String key,String value)
	{
		if(key.contains("等级"))
			hospital.setLevel(value);
		else if(key.contains("区域"))
			hospital.setArea(value);
		else if(key.contains("分类"))
			hospital.setManage(value);
			
	}
}
