package com.project.backMng.admin.ChargeRuleMng.config;

import java.util.Date;

public interface ChargeRuleXml {

	public long calculateToll(Date startTime, Date endTime,int carType);
	
	
}
