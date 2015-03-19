package com.sunkun.gh.domain;

import java.util.Calendar;
import java.util.Vector;

public class Register {
	
	private String time;
	
	private Vector<Doctor> doctors=new Vector<Doctor>();
	
	private String url;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Vector<Doctor> getRegisters() {
		return doctors;
	}

	public void setRegisters(Vector<Doctor> registers) {
		this.doctors = registers;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public String toString() {
		// TODO Auto-generated method stub
		
		String toStr="可预约日期："+time+"\n";
		
		for(Doctor doctor:doctors)
		{
			toStr+=doctor.toString();
		}
		
		return toStr;
	}
	
	
}
