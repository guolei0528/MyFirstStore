package com.project.common.util;

import java.text.DecimalFormat;
import java.util.Random;
/**
 * 获取整形数据的工具类
 * @author OAK
 *
 */
public class RandomUtil {
	
	public static int nextInt(int maxInt){
		Random random=new Random();
		return random.nextInt(maxInt);
	}
	
	public static double nextDouble(){
		return Math.random();
	}
	
	public static void main(String[] args){
		DecimalFormat df=new DecimalFormat("000");
		System.out.println(RandomUtil.nextInt(999));
		System.out.println(df.format(RandomUtil.nextInt(99)));
		System.out.println(System.currentTimeMillis());
	}
}
