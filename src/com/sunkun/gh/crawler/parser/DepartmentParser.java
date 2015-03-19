package com.sunkun.gh.crawler.parser;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sunkun.gh.crawler.network.HtmlGetter;
import com.sunkun.gh.domain.Category;
import com.sunkun.gh.domain.Department;
import com.sunkun.gh.domain.Hospital;
import com.sunkun.gh.domain.Register;
import com.sunkun.gh.element.PageElement;
import com.sunkun.gh.util.LogUtil;

public class DepartmentParser {
	
	/*public Department parser(PageElement doc)
	{

		Department department=new Department();
		
	    Elements registers = doc.getDoc().select("[class=greenbg]");
		
	    
	    
	    for(Element registerElement:registers)
	    {
	    	
	    	
	    RegisterParser parser=new RegisterParser();
	    
	    Register register = parser.parser(registerElement,);
	    
	    department.getRegisters().add(register);
	    }
				//
		Elements hospitalElements = doc.getDoc().getElementsByClass("hospital");
		
		Elements nameElements=hospitalElements.get(0).getElementsByTag("p");
		
		String name=nameElements.get(0).text();
		
		return department;
	}
	*/
	public Department parser(Hospital hospital,Category category,Element departMentelement) throws IOException
	{

		Department department=new Department();
		
		String departMentName = departMentelement.text();
		department.setCategoryName(category.getName());
		department.setDepartmentName(departMentName);
		department.setHospitalId(hospital.getHospitalId());
		String departPageURLPrifix = departMentelement.attr("href");
		String departMentURL="http://www.bjguahao.gov.cn/comm"+departPageURLPrifix.substring(1);
		department.setUrl(departMentURL);
		/*
		Document doc = HtmlGetter.getHtmlDoc(departMentURL,hospital.getPageURl());
	    Elements registers = doc.select("[class=greenbg]");
	    
	    for(Element registerElement:registers)
	    {
	    	
	    RegisterParser parser=new RegisterParser();
	    
	    parser.parser(registerElement,department);
	    
	    //department.getRegisters().add(register);
	    }*/
	    
		category.getDepartments().add(department);
		return department;
	}
}
