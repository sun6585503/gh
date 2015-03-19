package com.sunkun.gh.crawler.parser;

import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sunkun.gh.domain.Category;
import com.sunkun.gh.domain.Hospital;
import com.sunkun.gh.domain.Register;

public class CategoryParser {
	
	public Category parser(Element element,Hospital hospital) throws IOException
	{
		Category category=new Category();
		
		String categoryName=element.getElementsByClass("yyksdl").get(0).text();
		
		category.setName(categoryName);
		
		Elements departMentElements = element.getElementsByClass("islogin");
		
		for(Element departMentelement:departMentElements)
		{
			DepartmentParser parser=new DepartmentParser();
			
			parser.parser(hospital, category, departMentelement);
		}
		hospital.getCategories().add(category);
		return category;
	}
}
