package com.project.tools;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import com.project.common.util.RandomUtil;

public class ValidCodeUtil {
	
	private final static String SMS_VALID_CODE="SMS_VALID_CODE";
	/**
	 * 生成
	 * @return
	 */
	public static String generate(HttpServletRequest request){
		DecimalFormat df=new DecimalFormat("0000");
		String result=df.format(RandomUtil.nextInt(9999));
		System.out.println("验证码为===="+result);
		request.getSession().setAttribute(SMS_VALID_CODE, result);
		return result;
	}
	/**
	 * 保存CODE的信息
	 * @param request
	 * @param VALID_CODE
	 * @return
	 */
	public static boolean compareCode(HttpServletRequest request,String VALID_CODE){
		if(VALID_CODE!=null&&VALID_CODE.length()>0&&request.getSession().getAttribute(SMS_VALID_CODE)!=null){
			String SESSION_CODE=(String)request.getSession().getAttribute(SMS_VALID_CODE);
			return VALID_CODE.equals(SESSION_CODE);
		}else{
			return false;
		}
		
	}
}
