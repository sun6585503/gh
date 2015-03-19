package com.sunkun.gh.util;

import java.util.Date;

public class LogUtil {
	
	public static void printLog(Object target,String log)
	{
		String date=new Date().toString();
		
		String className=target.getClass().getName();
		System.out.println("*********************************log***********************");
		System.out.println("Time:"+date+":"+className+"\n");
		System.out.println(log);
		System.out.println("*********************************log***********************");
		
	}
	
	public static void printLog(Class target,String log)
	{
		String date=new Date().toString();
		
		String className=target.getName();
		System.out.println("*********************************log***********************");
		System.out.println("Time:"+date+":"+className+"\n");
		System.out.println(log);
		System.out.println("*********************************log***********************");
		
	}

}
