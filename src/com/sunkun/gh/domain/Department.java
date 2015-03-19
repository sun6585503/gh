package com.sunkun.gh.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.jsoup.nodes.Element;

public class Department {
	
	public static int NO_LEFT=0;
	public static int HAS_LEFT=1;
	public static int ERROR=2;
	
	private String departmentId;
	
	private String departmentName;
	
	private String categoryName;
	
	private int registerStatus=ERROR;
	//可掛號天數
	private int leftNum;
	
	public int getLeftNum() {
		return leftNum;
	}

	public void setLeftNum(int leftNum) {
		this.leftNum = leftNum;
	}

	private Date lastUpdateDate;
	
	public int getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(int registerStatus) {
		this.registerStatus = registerStatus;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDepartmentURL() {
		return departmentURL;
	}

	public void setDepartmentURL(String departmentURL) {
		this.departmentURL = departmentURL;
	}

	private int hospitalId;
	
	private String departmentURL;
	
	public String getUrl() {
		return departmentURL;
	}

	public void setUrl(String url) {
		this.departmentURL = url;
	}

	private Vector<Register> registers=new Vector<Register>();

	

	public Vector<Register> getRegisters() {
		return registers;
	}

	public void setRegisters(Vector<Register> registers) {
		this.registers = registers;
	}
	
	public String toString()
	{
		String lName="----"+hospitalId+":"+departmentName+":"+departmentURL+"\n";
		/*for(Register registerElement:registers)
		{
			lName+=registerElement.toString();
		}*/
		/*if(registers.size()==0)
			lName+="无剩余号源\n";*/
		return lName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}

}
