package com.project.backMng.admin.ChargeRuleMng.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.tools.DateUtil;

public class ChargeRuleConstant {
	
	//零点
	public final static int ZERO =  0;

	//一天毫秒数
	public final static long ONE_DAY = 86400000;
	
	//60
	public final static long SIXTY = 60;
	
	//1千
	public final static long ONE_THOUSAND = 1000;
	
	//零点
	public final static Date ZERO_CLOCK =  DateUtil.parse4Hms("00:00:00");
	
	//24点
	public final static Date TWENTY_FOUR_CLOCK =  DateUtil.parse4Hms("24:00:00");
	
	//小车
	public final static int SMALL_CAR = 1;
	
	//大车
	public final static int LARGE_CAR = 2;
	
	//货车
	public final static int LORRY = 3;
	
	//入口节点
	public final static int ENTRY_CHARGE_NODE = 0;
	
	//出卡节点
	public final static int EXIT_CHARGE_NODE = 6;
	
	//是否0点重置,1重置
	public final static int RESET = 1;
	
	//节点规则1
//	public final static int CHARGE_NODE_1ST = 1;
//	//节点规则2
//	public final static int CHARGE_NODE_2ND = 2;
//	//节点规则3
//	public final static int CHARGE_NODE_3RD = 3;
//	//节点规则4
//	public final static int CHARGE_NODE_4TH = 4;
//	//节点规则5
//	public final static int CHARGE_NODE_5TH = 5;
	
	
}
