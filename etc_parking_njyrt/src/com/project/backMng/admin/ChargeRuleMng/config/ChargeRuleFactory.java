package com.project.backMng.admin.ChargeRuleMng.config;

import com.redoak.jar.util.PropertiesUtil;

public class ChargeRuleFactory {

		//简单工厂模式判断使用哪种计费类型：1按时间段计费规则，2按时长计费规则,3按次数计费规则
		public ChargeRuleXml createChargeRule(String id)
		{
			//去除id的空格
			id = id.trim();
			if(id == null || "".equals(id)
					|| (!id.startsWith("1") && !id.startsWith("2") && !id.startsWith("3")
							&& !id.startsWith("4") && !id.startsWith("5")))
			{
				id = PropertiesUtil.get("CHARGE_TYPE");
			}
			 String type = id.substring(0, 1);
			 switch (type) { 
			 
			 case "1":
				 return ChargeRuleStaticList.getTiChargeRuleXmlBean().get(id);
				 
			 case "2":
				 return ChargeRuleStaticList.getTlChargeRuleXmlBean().get(id);
			
			 case "3":
				 return ChargeRuleStaticList.getTcChargeRuleXmlBean().get(id);
			case "4":
			     return ChargeRuleStaticList.getSpecChgTIChargeRuleXmlBean().get(id);
		 }
			 
			return null;
		}
	
	
	
	//简单工厂模式判断使用哪种计费类型：1按时间段计费规则，2按时长计费规则,3按次数计费规则
	/*public ChargeRuleXml createChargeRule(int type)
	{
		 switch (type) { 
		 
		 case 1:
			 return new TIChargeRuleXmlBean();
			 
		 case 2:
			 return new TLChargeRuleXmlBean();
		
		 case 3:
			 return new TCChargeRuleXmlBean();
		 }
		 
		return null;
	}*/
	
	
	//简单工厂模式判断使用哪种计费类型：1按时间段计费规则，2按时长计费规则,3按次数计费规则
	//获取免费时间
		/*public int findFreeTime(int type)
		{
			 switch (type) { 
			 
			 case 1:
				 return new TIChargeRuleXmlBean().getTiSetting().getFreeTime();
				 
			 case 2:
				 return new TLChargeRuleXmlBean().getTlSetting().getFreeTime();
			
			 case 3:
				 return new TCChargeRuleXmlBean().getTcSetting().getFreeTime();
			 }
			 
			return 0;
		}*/
		
		//简单工厂模式判断使用哪种计费类型：1按时间段计费规则，2按时长计费规则,3按次数计费规则
		//获取免费时间
		public int findFreeTime(String id)
		{
			//去除id的空格
			id = id.trim();
			if(id == null || "".equals(id)
					|| (!id.startsWith("1") && !id.startsWith("2") && !id.startsWith("3")))
			{
				id = PropertiesUtil.get("CHARGE_TYPE");
			}
			 String type = id.substring(0, 1);
			 switch (type) {
			 case "1":
				 return ChargeRuleStaticList.getTiChargeRuleXmlBean().get(id).getTiSetting().getFreeTime();
				 
			 case "2":
				 return ChargeRuleStaticList.getTlChargeRuleXmlBean().get(id).getTlSetting().getFreeTime();
			
			 case "3":
				 return ChargeRuleStaticList.getTcChargeRuleXmlBean().get(id).getTcSetting().getFreeTime();
			 }
			 
			 return 0;
		}
}
