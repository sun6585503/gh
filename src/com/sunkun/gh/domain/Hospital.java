package com.sunkun.gh.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class Hospital {

	private int hospitalId;
	
	private String hospitalName;
	
	private String pageURl;
	//预约周期
	private int appointmentCycle;
	//放号时间，24小时制
	private Date startTime;
	//医院等级
	private String level;
	//区域
	private String area;
	//类别
	private String manage;
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getManage() {
		return manage;
	}

	public void setManage(String manage) {
		this.manage = manage;
	}

	public int getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public int getAppointmentCycle() {
		return appointmentCycle;
	}

	public void setAppointmentCycle(int appointmentCycle) {
		this.appointmentCycle = appointmentCycle;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getSpicalRule() {
		return spicalRule;
	}

	public void setSpicalRule(String spicalRule) {
		this.spicalRule = spicalRule;
	}

	public Vector<Category> getCategories() {
		return categories;
	}

	public void setCategories(Vector<Category> categories) {
		this.categories = categories;
	}
	private String spicalRule;
	
	private Vector<Category> categories=new Vector<Category>();

	public int getId() {
		return hospitalId;
	}

	public void setId(int id) {
		this.hospitalId = id;
	}

	public String getName() {
		return hospitalName;
	}

	public void setName(String name) {
		this.hospitalName = name;
	}

	public String getPageURl() {
		return pageURl;
	}

	public void setPageURl(String pageURl) {
		this.pageURl = pageURl;
	}
	public String toString()
	{
		
		String disPlayStr=hospitalName+"\n";
		for(Category category:categories)
		{
			disPlayStr+=category.toString();
		}
		return disPlayStr;
	}
	
}
