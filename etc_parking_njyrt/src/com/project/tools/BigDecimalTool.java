package com.project.tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class BigDecimalTool {

	/**
	 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
	 */
	private static final int DEF_DIV_SCALE = 10; // 这个类不能实例化

	private BigDecimalTool() {
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	
    public static double add(String d1,String d2){  
        BigDecimal b1=new BigDecimal(d1);  
        BigDecimal b2=new BigDecimal(d2);  
        return b1.add(b2).doubleValue();  
    }  

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	
	public static double sub(String v1, String v2) {
        BigDecimal b1=new BigDecimal(v1);  
        BigDecimal b2=new BigDecimal(v2);  
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 两个数据比较
	 * @param v1
	 * @param v2
	 * @return -1: v1<v2
	 * @return  0: v1=v2
	 * @return  1: v1>v2
	 */
	public static int compare(String v1, String v2) {
        BigDecimal b1=new BigDecimal(v1);  
        BigDecimal b2=new BigDecimal(v2);  
		return b1.compareTo(b2);
	}
	
	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static void main(String[] args) {
		System.out.println(round(5.3,0));
	}
	
	/** 
     * 当使用POI处理excel的时候，遇到了比较长的数字， 
     * 虽然excel里面设置该单元格是文本类型的，但是POI的cell的类型就会变成数字类型 
     * 而且无论数字是否小数，使用cell.getNumbericCellValue() 去获取值的时候，会得到一个double， 
     * 而且当长度大一点的时候会变成科学计数法形式 
     * 使用DecimalFormat对这个double进行了格式话， 
     * 随后使用format方法获得的String就是你想要的值了。 
     * @param hssfcell 
     * @return 
     */  
    public static String cellGetValue(HSSFCell hssfcell){  
        String str;  
        DecimalFormat df = new DecimalFormat("0");    
        if(hssfcell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){  
            str = String.valueOf(hssfcell.getBooleanCellValue());  
            return str;  
        } else if(hssfcell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){  
            str = df.format(hssfcell.getNumericCellValue());    
            return str;  
        } else{  
            str = hssfcell.getStringCellValue();    
            return str;  
        }  
    }  
};