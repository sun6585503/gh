package com.sunkun.gh.domain;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Doctor {
	
	/*private String url;
	
	private String _1date;
	private String _2week;
	private String _3noon;
	private String _4keshi;
	private String _5name;
	private String _6zhicheng;
	private String _7cost;
	private String _8good;
	
	private String _9all;
	private String _10still;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String get_1date() {
		return _1date;
	}
	public void set_1date(String _1date) {
		this._1date = _1date;
	}
	public String get_2week() {
		return _2week;
	}
	public void set_2week(String _2week) {
		this._2week = _2week;
	}
	public String get_3noon() {
		return _3noon;
	}
	public void set_3noon(String _3noon) {
		this._3noon = _3noon;
	}
	public String get_4keshi() {
		return _4keshi;
	}
	public void set_4keshi(String _4keshi) {
		this._4keshi = _4keshi;
	}
	public String get_5name() {
		return _5name;
	}
	public void set_5name(String _5name) {
		this._5name = _5name;
	}
	public String get_6zhicheng() {
		return _6zhicheng;
	}
	public void set_6zhicheng(String _6zhicheng) {
		this._6zhicheng = _6zhicheng;
	}
	public String get_7cost() {
		return _7cost;
	}
	public void set_7cost(String _7cost) {
		this._7cost = _7cost;
	}
	public String get_8good() {
		return _8good;
	}
	public void set_8good(String _8good) {
		this._8good = _8good;
	}
	public String get_9all() {
		return _9all;
	}
	public void set_9all(String _9all) {
		this._9all = _9all;
	}
	public String get_10still() {
		return _10still;
	}
	public void set_10still(String _10still) {
		this._10still = _10still;
	}
	
	public Doctor(Element tr)
	{
		Elements td=tr.getElementsByTag("td");
		this._1date=td.get(0).text();
		this._2week=td.get(1).text();
		this._3noon=td.get(2).text();
		this._4keshi=td.get(3).text();
		this._5name=td.get(4).text();
		this._6zhicheng=td.get(5).text();
		this._7cost=td.get(6).text();
		this._8good=td.get(7).text();
		this._9all=td.get(8).text();
		this._10still=td.get(9).text();
		
	}*/
	
	public String url;
	
	public Hashtable<String,String> properties=new Hashtable<String,String>();
	
	public Doctor(Element title,Element row)
	{
		Elements titles=title.getElementsByTag("td");
		Elements values=row.getElementsByTag("td");
		
		for(int i=0;i<titles.size();i++)
		{
			String key=titles.get(i).text();
			String value=values.get(i).text();
			
			properties.put(key, value);
		}
		
		try {
			url="http://www.bjguahao.gov.cn/comm"+row.getElementsByTag("a").get(0).attr("href").substring(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			/*e.printStackTrace();
			System.out.println(row);*/
			url="";
		}
		
	}
	
	public String toString() {
		// TODO Auto-generated method stub
		
		String str="";
		
		Iterator<String> keys = properties.keySet().iterator();
		
		while(keys.hasNext())
		{
			String key=keys.next();
			String value=properties.get(key);
			
			str+=key+":"+value+"|";
		}
		return str;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Hashtable<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Hashtable<String, String> properties) {
		this.properties = properties;
	}

}
