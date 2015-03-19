package com.sunkun.gh.domain;

import java.util.Vector;

public class Category {
	
	private String categoryName;
	
	private int hospitalID;
	
	public int getHospitalID() {
		return hospitalID;
	}

	public void setHospitalID(int hospitalID) {
		this.hospitalID = hospitalID;
	}

	private Vector<Department> departments=new Vector<Department>();

	public String getName() {
		return categoryName;
	}

	public void setName(String name) {
		this.categoryName = name;
	}

	public Vector<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Vector<Department> departments) {
		this.departments = departments;
	}
	
	public String toString()
	{
		String str="--"+categoryName+"\n";
		for(Department department:departments)
		{
			str+=department.toString();
		}
		return str;
	}

}
