package com.sunkun.gh.crawler.parser;

import java.io.IOException;
import java.util.Vector;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sunkun.gh.crawler.network.HtmlGetter;
import com.sunkun.gh.domain.Department;
import com.sunkun.gh.domain.Doctor;
import com.sunkun.gh.domain.Register;
import com.sunkun.gh.element.PageElement;

public class DoctorsParser {
	
	public Vector<Doctor> parser(Department department,Register register)
	{
		
		Vector<Doctor> doctors=new Vector<Doctor>();
		
		
		String doctorURL=register.getUrl();
		String ref=department.getUrl();
		
		try {
			Document doc = HtmlGetter.getHtmlDoc(doctorURL,ref);
			
			Element table= doc.getElementsByTag("table").get(1);
			
			Elements rows = table.getElementsByTag("tr");
			
			for(int i=1;i<rows.size();i++)
			{
				
				
				Element row=rows.get(i);
				
				Doctor doctor=new Doctor(rows.get(0),row);
				if(doctor.getUrl().equalsIgnoreCase(""))
					continue;
				doctors.add(doctor);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		register.setRegisters(doctors);
		
		return doctors;
		
	}
}
