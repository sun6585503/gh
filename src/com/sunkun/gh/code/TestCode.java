package com.sunkun.gh.code;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TestCode {
	public static void main(String[] args) throws IOException 
	{           
		BufferedImage bi = (BufferedImage)ImageIO.read(new File("code.gif"));  
		//获取图像的宽度和高度      
		int width = bi.getWidth();       
		int height = bi.getHeight();    
		//扫描图片       
		for(int i=0;i<height;i++){      
			for(int j=0;j<width;j++){//行扫描       
				int dip = bi.getRGB(j, i);      
				if(isInColor(dip,Color.red.getRGB(),100)) System.out.print("*");  
				else          
					System.out.print(" ");          
				}             
			System.out.println();//换行         }        }   
			}
		}
	
	private static boolean isInColor(int target,int rgb,int fw)
	{
		if(target>rgb+fw)
			return false;
		if(target<rgb-fw)
			return false;
		return true;
	}
	
}
