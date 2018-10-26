package com.project.backMng.admin.ChargeRuleMng.config;

import java.util.Map;

import com.project.backMng.admin.ChargeRuleMng.config.impl.SpecChgTIChargeRuleXmlBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.TCChargeRuleXmlBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.TIChargeRuleXmlBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.TLChargeRuleXmlBean;

public class ChargeRuleStaticList {

	private static Map<String,TCChargeRuleXmlBean> tcChargeRuleXmlBean;
	
	private static Map<String,TIChargeRuleXmlBean> tiChargeRuleXmlBean;
	
	private static Map<String,TLChargeRuleXmlBean> tlChargeRuleXmlBean;
	
	private static Map<String,SpecChgTIChargeRuleXmlBean> specChgTIChargeRuleXmlBean;

	public static Map<String, TCChargeRuleXmlBean> getTcChargeRuleXmlBean() {
		return tcChargeRuleXmlBean;
	}

	public static void setTcChargeRuleXmlBean(Map<String, TCChargeRuleXmlBean> tcChargeRuleXmlBean) {
		ChargeRuleStaticList.tcChargeRuleXmlBean = tcChargeRuleXmlBean;
	}

	public static Map<String, TIChargeRuleXmlBean> getTiChargeRuleXmlBean() {
		return tiChargeRuleXmlBean;
	}

	public static void setTiChargeRuleXmlBean(Map<String, TIChargeRuleXmlBean> tiChargeRuleXmlBean) {
		ChargeRuleStaticList.tiChargeRuleXmlBean = tiChargeRuleXmlBean;
	}

	public static Map<String, TLChargeRuleXmlBean> getTlChargeRuleXmlBean() {
		return tlChargeRuleXmlBean;
	}

	public static void setTlChargeRuleXmlBean(Map<String, TLChargeRuleXmlBean> tlChargeRuleXmlBean) {
		ChargeRuleStaticList.tlChargeRuleXmlBean = tlChargeRuleXmlBean;
	}

	public static Map<String, SpecChgTIChargeRuleXmlBean> getSpecChgTIChargeRuleXmlBean() {
		return specChgTIChargeRuleXmlBean;
	}

	public static void setSpecChgTIChargeRuleXmlBean(Map<String, SpecChgTIChargeRuleXmlBean> specChgTIChargeRuleXmlBean) {
		ChargeRuleStaticList.specChgTIChargeRuleXmlBean = specChgTIChargeRuleXmlBean;
	}
	
}
