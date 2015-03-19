package com.sunkun.gh.service;

import java.io.IOException;

import com.sunkun.gh.crawler.network.LoginTool;
import com.sunkun.gh.crawler.parser.HopitalPageParser;
import com.sunkun.gh.domain.Category;
import com.sunkun.gh.domain.Department;
import com.sunkun.gh.domain.Hospital;
import com.sunkun.gh.domain.operator.DBTool;
import com.sunkun.gh.util.LogUtil;

public class HospitalInforCollecter {

	private static int MAX_HOSPITAL=300;
	
	private Thread taskThread;
	
    private class HospitalInforCollectTask implements Runnable
	{
    	int id=0;
    	public HospitalInforCollectTask(int id)
    	{
    	this.id=id;	
    	}

		public void run() {
			// TODO Auto-generated method stub
			Hospital hospital=new Hospital();
			hospital.setId(id);
			HopitalPageParser parser=new HopitalPageParser();
			try {
				parser.parser(hospital);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LogUtil.printLog(this,"不存在ID="+hospital.getId()+"的医院\n"+hospital.getPageURl());
			    return;
			}
			DBTool.save(hospital);
			
			int cateSize=hospital.getCategories().size();
			
			for(int i=0;i<cateSize;i++)
			{
				Category cate=hospital.getCategories().get(i);
				
				int departSize=cate.getDepartments().size();
				
				for(int j=0;j<departSize;j++)
				{
					Department department=cate.getDepartments().get(j);
					DBTool.save(department);
				}
			}
			
		}
		
	}
    
    public HospitalInforCollecter(int id)
    {
    	taskThread=new Thread(
				new HospitalInforCollectTask(id)
				);
    }
    
    public void start()
    {
    	taskThread.start();
    }
    
    public static void main(String args[])
    {
    	for(int i=1;i<HospitalInforCollecter.MAX_HOSPITAL;i++)
    	{
    		new HospitalInforCollecter(i).start();
    	}
    }
	
}
