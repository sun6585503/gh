package com.sunkun.gh.crawler.parser;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sunkun.gh.crawler.network.HtmlGetter;
import com.sunkun.gh.domain.Department;
import com.sunkun.gh.domain.Hospital;
import com.sunkun.gh.domain.Register;
import com.sunkun.gh.element.PageElement;

public class RegisterParser {

	public Register parser(Element element,Department department)
	{
		Register register=new Register();
		
		String registerInfor = element.getElementsByTag("a").attr("href");
		
		String date=registerInfor.split("&")[2];
		
		String registerURL="http://www.bjguahao.gov.cn/comm/"+registerInfor;
		register.setTime(date);
		register.setUrl(registerURL);
		//发现余号直接返回挂号，先不解析医生信息
		/*DoctorsParser parser=new DoctorsParser();
		parser.parser(department, register);*/
		department.getRegisters().add(register);
		
		return register;
	}
	
}
