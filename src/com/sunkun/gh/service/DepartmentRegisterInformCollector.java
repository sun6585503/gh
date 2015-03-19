package com.sunkun.gh.service;

import java.io.IOException;
import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sunkun.gh.crawler.network.HtmlGetter;
import com.sunkun.gh.crawler.network.LoginTool;
import com.sunkun.gh.crawler.parser.RegisterParser;
import com.sunkun.gh.domain.*;
import com.sunkun.gh.domain.operator.DBTool;

public class DepartmentRegisterInformCollector implements Runnable{

	private RegisterListener listener;
	
	public void setListener(RegisterListener listener) {
		this.listener = listener;
	}

	private Department target;
	
	private Hospital hospital;
	
	private boolean stop=false;
	//轮询时间间隔，单位秒，默认30S
	private int interval=5;
	
	public DepartmentRegisterInformCollector(Hospital hospital,Department target)
	{
		this.target=target;
		this.hospital=hospital;
	}
	
	public void run() {
		
		// TODO Auto-generated method stub
		try {
			
			while(!stop)
			{
			
			Document doc = HtmlGetter.getHtmlDoc(target.getDepartmentURL(),hospital.getPageURl());
			
			Elements registers = doc.select("[class=greenbg]");
			
			for(Element registerElement:registers)
			{
				
			RegisterParser parser=new RegisterParser();
			
			parser.parser(registerElement,target);
			
			}
			
			if(registers.size()>0)
			{
				
				target.setRegisterStatus(Department.HAS_LEFT);
				target.setLeftNum(registers.size());
				if(listener!=null)
				listener.processRegisterInfor(target.getRegisters().get(0));
			}
			else
			{
				target.setRegisterStatus(Department.NO_LEFT);
				target.setLeftNum(0);
			}
			target.setLastUpdateDate(new Date());
			DBTool.update(target);
			
			
			try {
				Thread.sleep(interval*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
			try {
				LoginTool.logout();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			target.setLastUpdateDate(new Date());
			target.setRegisterStatus(Department.ERROR);
			target.setLeftNum(0);
			DBTool.update(target);
			try {
				LoginTool.logout();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	public static void main(String arg[])
	{
		
		try {
			
			LoginTool.login("孙鲲", "23100419831104121X");
			
			Hospital hospital=(Hospital) DBTool.search(Hospital.class,"Hospital","hospitalid=5").get(0);
		
			Department dep=(Department) DBTool.search(Department.class,"Department","hospitalid=5").get(0);
		

			DepartmentRegisterInformCollector coolector=new DepartmentRegisterInformCollector(hospital,dep); 
		
			Regist reg=new Regist(hospital,dep);
			coolector.setListener(reg);
			Thread t=new Thread(coolector);
		 t.start();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
