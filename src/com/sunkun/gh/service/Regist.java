package com.sunkun.gh.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sunkun.gh.crawler.network.HtmlGetter;
import com.sunkun.gh.crawler.network.LoginTool;
import com.sunkun.gh.crawler.network.VerificationCodeTool;
import com.sunkun.gh.crawler.parser.DoctorsParser;
import com.sunkun.gh.domain.Department;
import com.sunkun.gh.domain.Doctor;
import com.sunkun.gh.domain.Hospital;
import com.sunkun.gh.domain.Register;

public class Regist implements RegisterListener{

	Hospital hospital;
	Department department;
	Register register;
	

	public Regist(Hospital hospital, Department department) {
		super();
		this.hospital = hospital;
		this.department = department;
	}


	public void processRegisterInfor(Register register) {
		// TODO Auto-generated method stub
		DoctorsParser parser=new DoctorsParser();
		parser.parser(department, register);
		department.getRegisters().add(register);
		
		Vector<Doctor> doctors = register.getRegisters();
if(doctors.size()==0)
	return;
        Doctor doctor=doctors.get(0);
        
        String registURL=doctor.getUrl()+"&jiuz=&ybkh=&hzname=&hzsfz=";
        String refURL=register.getUrl();
        
        try {
        	Document doc = getDxCode(doctor,register);
			
			Elements inputs = doc.getElementsByTag("input");
			
			String logData="";
			
			InputStream is = new BufferedInputStream(new FileInputStream(new File("code.properties")));
			Properties properties = new Properties();
			properties.load(is);
			String code=(String) properties.get("code");
			
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			
			String referPrifix="";
			
			for(Element input:inputs)
			{
				String name=input.attr("name");
				String value=input.attr("value");
				
				if(name.equalsIgnoreCase(""))
					continue;
				if(name.equalsIgnoreCase("words"))
					continue;
				/*if(name.equalsIgnoreCase("dxcode"))
					logData+="dxcode="+code+"&";
				else
					logData+=name+"="+value+"&";*/
				
				if(name.equalsIgnoreCase("dxcode"))
				{
					
					nvps.add(new BasicNameValuePair("baoxiao","0"));
				nvps.add(new BasicNameValuePair("dxcode",code));
				}else
			     {
					nvps.add(new BasicNameValuePair(name,value));	
			}
			}
			logData+="baoxiao=1";
			String logURL="http://www.bjguahao.gov.cn/comm/TG/ghdown.php";
			
			
			
			//Document doc3 = HtmlGetter.getHtmlDoc(logURL,registURL);
			//HtmlGetter.getHtmlDoc("http://www.bjguahao.gov.cn/comm/plus/ajax_user.php?act=top_loginform", registURL);
			
			ghHttpClient(registURL, nvps, logURL);
			//System.out.println(doc2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void gh(String registURL, String logData, String logURL) {
		try {
			System.out.print(LoginTool.post(logURL, logData, registURL));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void ghHttpClient(String registURL, List <NameValuePair> nvps, String logURL) {
		try {
			LoginTool.postWithHttpClient(logURL, nvps, registURL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Document getDxCode(Doctor doctor,Register register) throws IOException
	{
		String registURL=doctor.getUrl();
        String refURL=register.getUrl();
        
			Document doc = HtmlGetter.getHtmlDoc(registURL,refURL);
			
			Pattern pattern = Pattern.compile("\\S*dx_code.php\\S*");
			
			Matcher matcher = pattern.matcher(new String(doc.html().getBytes(),"UTF-8"));
			
			matcher.find();
			String urlPrifix=matcher.group(0).substring(10);
			
			//"+hpid+"
			//"+"
			//"+ksid+"
			//"+datid+"
			//"+jiuz+"
			//"+ybkh+"
			//"+baoxiao
			String code_datid = doc.getElementById("code_datid").attr("value");
			
			urlPrifix=urlPrifix.replace("\"+\"","");
			urlPrifix=urlPrifix.replace("\"+hpid+\"",""+hospital.getHospitalId());
			urlPrifix=urlPrifix.replace("\"+ksid+\"",department.getDepartmentURL().split("=")[department.getDepartmentURL().split("=").length-1]);
			urlPrifix=urlPrifix.replace("\"+datid+\"",code_datid);
			urlPrifix=urlPrifix.replace("\"+jiuz+\"","");
			urlPrifix=urlPrifix.replace("\"+ybkh+\"","");
			urlPrifix=urlPrifix.replace("\"+baoxiao","0");
			urlPrifix=urlPrifix.replace(",null,callback);","");
			urlPrifix="http://www.bjguahao.gov.cn/comm/"+urlPrifix;
			
			Document doc2 = HtmlGetter.getHtmlDoc(urlPrifix,registURL);
			//HtmlGetter.getHtmlDoc("http://www.bjguahao.gov.cn/comm/plus/ajax_user.php?act=top_loginform", urlPrifix);
			
			return doc;
	}
	
	public static void main(String args[])
	{
		try {
		InputStream is = new BufferedInputStream(new FileInputStream(new File("code.properties")));
				Properties properties = new Properties();

			properties.load(is);
			System.out.println(properties.get("code"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*File file = null;
	        BufferedReader br = null;
	        StringBuffer buffer = null;
	        String childPath = "test.txt";
	        String data = "";
	        try {
	            file = new File(childPath);
	            buffer = new StringBuffer();
	            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
	            br = new BufferedReader(isr);
	            int s;
	            while ((s = br.read()) != -1) {
	                buffer.append((char) s);
	            }
	            data = buffer.toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        Pattern pattern = Pattern.compile("\\S*dx_code.php\\S*");
	        Matcher matcher = pattern.matcher(data); 
	        while (matcher.find()) {
	            System.out.println(matcher.group());
	        }*/
}
	
}
