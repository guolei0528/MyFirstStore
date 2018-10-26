package com.project.common.tool;

import java.io.UnsupportedEncodingException;

/**
 * 字符串工具
 *
 */
public class StringUtil {
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null||str.trim().length()==0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 将字符串从ISO-8859-1转换为UTF8
	 * @param str
	 * @return
	 */
	public static String iso_2_utf8(String str){
		if(str==null){
			return str;
		}else{
			try {
				return new String(str.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
	/**
	 * 过滤特殊字符
	 * @param str
	 * @return
	 */
	public static String filterSQLSpecialChar(String str){
		if(str!=null){
			return str.replaceAll("_", "/_")
					  .replaceAll("%", "/%")
					  .replaceAll("'", "/'");
		}else{
			return str;
		}
	}
	/**
	 * 清空STRINGBUFFER中的内容
	 * @param buffer
	 */
	public static void clear(StringBuffer buffer){
		if(buffer!=null&&buffer.length()>0){
			buffer.delete(0,buffer.length());
		}
	}
	/**
	 * 判断字符串是否为英文字母，a-z，A-Z
	 * @param letter
	 * @return
	 */
	public static boolean isEnglishLetter(String letter){
		boolean flag=true;
		if(letter==null||letter.length()==0){
			flag=false;
		}else{
			int length=letter.length();
			int aValue=(int)'a';
			int zValue=(int)'z';
			int AValue=(int)'A';
			int ZValue=(int)'Z';
			for(int i=0;i<length;i++){
				int value=(int)letter.charAt(i);
				if((value>=aValue&&value<=zValue)||(value>=AValue&&value<=ZValue)){
					continue;
				}else{
					flag=false;
					break;
				}
			}
		}
		return flag;
	}
	/**
	 * 转换成大写
	 * @param value
	 * @return
	 */
	public static String toUpperCase(String value){
		if(value==null){
			return null;
		}else{
			return value.toUpperCase();
		}
	}
	/**
	 * 转换成小写
	 * @param value
	 * @return
	 */
	public static String toLowerCase(String value){
		if(value==null){
			return null;
		}else{
			return value.toLowerCase();
		}
	}
	/**
	 * 去除两边的空格
	 * @param value
	 * @return
	 */
	public static String trim(String value){
		if(value==null){
			return null;
		}else{
			return value.trim();
		}
	}
}
