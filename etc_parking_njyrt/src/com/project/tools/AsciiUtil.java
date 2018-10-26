package com.project.tools;

public class AsciiUtil {  
	  
  
    // 将字母转换成数字  
    public static String letterToNum(String input) {  
    	StringBuffer sb = new StringBuffer();
        for (byte b : input.getBytes()) {  
        	if(b >= 'a' && b<='z')
        	{
        		sb.append(b-97);
        	}
        	else if(b >= 'A' && b<='Z')
        	{
        		sb.append(b-65);
        	}
        	else
        	{
        		sb.append((char)b);
        	}
        }  
        return sb.toString();
    }  
  
    // 将数字转换成字母  
    public static void numToLetter(String input) {  
        for (byte b : input.getBytes()) {  
            System.out.print((char) (b + 48));  
        }  
    }  
  
    public static void main(String[] args) {  
        String i1 = "772e2ea40fd37654ABCD";  
//        String i2 = "123456";  
        String str = letterToNum(i1);  
        System.out.println(str);  
//        numToLetter(i2);  
    }  
}  

