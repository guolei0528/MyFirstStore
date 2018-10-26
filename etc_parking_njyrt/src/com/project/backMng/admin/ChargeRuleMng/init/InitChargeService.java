package com.project.backMng.admin.ChargeRuleMng.init;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleStaticList;
import com.project.backMng.admin.ChargeRuleMng.config.impl.SpecChgTIChargeRuleNodeBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.SpecChgTIChargeRuleSettingBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.SpecChgTIChargeRuleXmlBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.TCChargeRuleNodeBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.TCChargeRuleSettingBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.TCChargeRuleXmlBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.TIChargeRuleNodeBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.TIChargeRuleSettingBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.TIChargeRuleXmlBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.TLChargeRuleNodeBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.TLChargeRuleSettingBean;
import com.project.backMng.admin.ChargeRuleMng.config.impl.TLChargeRuleXmlBean;

public class InitChargeService {

	private final static Logger log = LogManager.getLogger(InitChargeService.class);

	/**
	 * 
	 * @Title : init
	 * @功能描述: 初始化加载计费规则 @传入参数：
	 * @返回类型：void
	 * @throws ：
	 */
	public void init() {
		parseChargRuleXml();
	}

	private void parseChargRuleXml() {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(this.getClass().getResource("/").getPath() + "charge_rule.xml"));
			// Document document = reader.read(new
			// File("F:\\Workspaces\\etc_parking\\resources\\charge_rule.xml"));
			// SAXBuilder builder=new SAXBuilder();
			Element root = document.getRootElement();
			List<Element> chargeRules = root.elements("chargeRule");
			// String type = chargeRule.attribute("type").getText();
			
//			root.elements("vaildType");
			Element vaild = root.element("vaild");
			String vaildType = vaild.elementText("vaildType");
			List<Element> elements = vaild.elements();
			
			List<Integer> vaildTypes = new ArrayList<Integer>();
			for(Element element:elements)
			{
				vaildTypes.add(Integer.parseInt(element.getTextTrim()));
			}
			
			for (Element chargeRule : chargeRules) {
				String type = chargeRule.attribute("type").getText();

				if (Integer.parseInt(type) == 1 && vaildTypes.contains(Integer.parseInt(type))) {
					parseXmlByTimeInterval(chargeRule);
					// int cycle =
					// TIChargeRuleXmlBean.getSetting().getMaxCycle();
					// int toll =
					// TIChargeRuleXmlBean.getNode().get(0).getSmallToll();
					// System.out.println(cycle); 
					// System.out.println(toll);
				} else if (Integer.parseInt(type) == 2 && vaildTypes.contains(Integer.parseInt(type))) {
					parseXmlByTimeLong(chargeRule);

				} else if (Integer.parseInt(type) == 3 && vaildTypes.contains(Integer.parseInt(type))) {
					parseXmlByTimeCount(chargeRule);
				} 
				else if (Integer.parseInt(type) == 4 && vaildTypes.contains(Integer.parseInt(type)))
				{
					parseXmlBySpecChgTimeInterval(chargeRule);
				}

			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: parseChargRuleByTLXml @Description: TODO(这里用一句话描述这个方法的作用) @param
	 *         设定文件 @return void 返回类型 @throws
	 */
	private void parseXmlByTimeInterval(Element chargeRule) {
		Element setting = chargeRule.element("setting");
		String id = setting.element("id").getText().trim();
		int type = Integer.parseInt(setting.element("type").getText().trim());
		int num = Integer.parseInt(setting.element("num").getText().trim());
		int freeTime = Integer.parseInt(setting.element("freeTime").getText().trim());

		// 取消最大周期时长和金额
		// int maxCycle =
		// Integer.parseInt(setting.element("maxCycle").getText().trim());
		// int maxToll =
		// Integer.parseInt(setting.element("maxToll").getText().trim());
		int version = Integer.parseInt(setting.element("version").getText().trim());
		String sComment = setting.element("sComment").getText().trim();

		TIChargeRuleSettingBean settingBean = new TIChargeRuleSettingBean();

		// 赋值基础数据
		settingBean.setId(id);
		settingBean.setType(type);
		settingBean.setNum(num);
		settingBean.setFreeTime(freeTime);
		// 取消最大周期时长和金额
		// settingBean.setMaxCycle(maxCycle);
		// settingBean.setMaxToll(maxToll);
		settingBean.setVersion(version);
		settingBean.setsComment(sComment);

		// 获得node节点的数据
		Element node = null;
		List<TIChargeRuleNodeBean> tiChargeRuleNodes = new ArrayList<TIChargeRuleNodeBean>();
		for (int i = 1; i <= num; i++) {
			node = chargeRule.element("node" + i);
			String startTime = node.element("startTime").getText();
			String endTime = node.element("endTime").getText();
			String period = node.element("period").getText();
			int limitType = Integer.parseInt(node.element("limitType").getText().trim());
			String smallLimitTime = node.element("smallLimitTime").getText();
			String largeLimitTime = node.element("largeLimitTime").getText();
			String smallLimitToll = node.element("smallLimitToll").getText();
			String largeLimitToll = node.element("largeLimitToll").getText();
			// String limitToll = node.element("limitToll").getText();
			String smallToll = node.element("smallToll").getText();
			String largeToll = node.element("largeToll").getText();

			// 创建node对象
			TIChargeRuleNodeBean nodeBean = new TIChargeRuleNodeBean();
			nodeBean.setId(i);
			nodeBean.setStartTime(startTime);
			nodeBean.setEndTime(endTime);
			nodeBean.setPeriod(Integer.parseInt(period));
			nodeBean.setLimitType(limitType);
			nodeBean.setSmallLimitTime(Integer.parseInt(smallLimitTime));
			nodeBean.setLargeLimitTime(Integer.parseInt(largeLimitTime));
			nodeBean.setSmallLimitToll(Integer.parseInt(smallLimitToll));
			nodeBean.setLargeLimitToll(Integer.parseInt(largeLimitToll));
			// nodeBean.setLimitToll(Integer.parseInt(limitToll));
			nodeBean.setSmallToll(Integer.parseInt(smallToll));
			nodeBean.setLargeToll(Integer.parseInt(largeToll));
			tiChargeRuleNodes.add(nodeBean);
		}

		TIChargeRuleXmlBean tiChargeRuleXmlBean = new TIChargeRuleXmlBean();
		tiChargeRuleXmlBean.setTiSetting(settingBean);
		tiChargeRuleXmlBean.setTiNode(tiChargeRuleNodes);

		Map<String, TIChargeRuleXmlBean> map = ChargeRuleStaticList.getTiChargeRuleXmlBean();
		// 判断是否创建
		if (map == null || map.size() == 0) {
			map = new HashMap<String, TIChargeRuleXmlBean>();
		}
		map.put(id, tiChargeRuleXmlBean);
		ChargeRuleStaticList.setTiChargeRuleXmlBean(map);

	}

	/**
	 * 
	 * @Title : parseXmlByTimeLong
	 * @功能描述: 初始化加载按时间长度解析规则xml
	 * @传入参数：@param chargeRule
	 * @返回类型：void
	 * @throws ：
	 */
	private void parseXmlByTimeLong(Element chargeRule) {
		Element setting = chargeRule.element("setting");
		String id = setting.element("id").getText().trim();
		int type = Integer.parseInt(setting.element("type").getText().trim());
		int num = Integer.parseInt(setting.element("num").getText().trim());
		int useFreeTime = Integer.parseInt(setting.element("useFreeTime").getText().trim());
		int reset = Integer.parseInt(setting.element("reset").getText().trim());
		int freeTime = Integer.parseInt(setting.element("freeTime").getText().trim());
		String freeStartTime = setting.element("freeStartTime").getText().trim();
		String freeEndTime = setting.element("freeEndTime").getText().trim();
		int maxCycle = Integer.parseInt(setting.element("maxCycle").getText().trim());
		int smallMaxToll = Integer.parseInt(setting.element("smallMaxToll").getText().trim());
		int largeMaxToll = Integer.parseInt(setting.element("largeMaxToll").getText().trim());
		int beyoneChargeFlag = Integer.parseInt(setting.element("beyoneChargeFlag").getText().trim());
		int version = Integer.parseInt(setting.element("version").getText().trim());
		String sComment = setting.element("sComment").getText().trim();

		TLChargeRuleSettingBean settingBean = new TLChargeRuleSettingBean();
		// 赋值基础数据
		settingBean.setId(id);
		settingBean.setType(type);
		settingBean.setNum(num);
		settingBean.setReset(reset);
		settingBean.setFreeTime(freeTime);
		settingBean.setMaxCycle(maxCycle);
		settingBean.setSmallMaxToll(smallMaxToll);
		settingBean.setLargeMaxToll(largeMaxToll);
		settingBean.setVersion(version);
		settingBean.setsComment(sComment);
		settingBean.setUseFreeTime(useFreeTime);
		settingBean.setBeyoneChargeFlag(beyoneChargeFlag);
		settingBean.setFreeStartTime(freeStartTime);
		settingBean.setFreeEndTime(freeEndTime);

		// 获得node节点的数据
		Element node = null;

		List<TLChargeRuleNodeBean> tlChargeRuleNodes = new ArrayList<TLChargeRuleNodeBean>();
		for (int i = 1; i <= num; i++) {
			node = chargeRule.element("node" + i);
			String startLong = node.element("startLong").getText();
			String period = node.element("period").getText();
			String limitToll = node.element("limitToll").getText();
			String smallToll = node.element("smallToll").getText();
			String largeToll = node.element("largeToll").getText();

			// 创建node对象
			TLChargeRuleNodeBean nodeBean = new TLChargeRuleNodeBean();
			nodeBean.setId(i);
			nodeBean.setStartLong(Integer.parseInt(startLong));
			nodeBean.setPeriod(Integer.parseInt(period));
			nodeBean.setLimitToll(Integer.parseInt(limitToll));
			nodeBean.setSmallToll(Integer.parseInt(smallToll));
			nodeBean.setLargeToll(Integer.parseInt(largeToll));
			tlChargeRuleNodes.add(nodeBean);
		}

		TLChargeRuleXmlBean tlChargeRuleXmlBean = new TLChargeRuleXmlBean();
		// 封装静态类--按时间长度计费规则
		tlChargeRuleXmlBean.setTlSetting(settingBean);
		tlChargeRuleXmlBean.setTlNode(tlChargeRuleNodes);

		Map<String, TLChargeRuleXmlBean> map = ChargeRuleStaticList.getTlChargeRuleXmlBean();
		// 判断是否创建
		if (map == null || map.size() == 0) {
			map = new HashMap<String, TLChargeRuleXmlBean>();
		}
		map.put(id, tlChargeRuleXmlBean);
		ChargeRuleStaticList.setTlChargeRuleXmlBean(map);
	}

	/**
	 * 
	 * @Title : parseXmlByTimeCount
	 * @功能描述: 根据停车次数计费
	 * @传入参数：@param chargeRule
	 * @返回类型：void
	 * @throws ：
	 */
	private void parseXmlByTimeCount(Element chargeRule) {
		Element setting = chargeRule.element("setting");
		String id = setting.element("id").getText().trim();
		int type = Integer.parseInt(setting.element("type").getText().trim());
		int num = Integer.parseInt(setting.element("num").getText().trim());
		int freeTime = Integer.parseInt(setting.element("freeTime").getText().trim());
		int maxCycle = Integer.parseInt(setting.element("maxCycle").getText().trim());
		int maxToll = Integer.parseInt(setting.element("maxToll").getText().trim());
		int timesUserMax = Integer.parseInt(setting.element("timesUserMax").getText().trim());
		int maxChargeNode = Integer.parseInt(setting.element("maxChargeNode").getText().trim());
		String version = setting.element("maxToll").getText().trim();
		String sComment = setting.element("sComment").getText().trim();

		TCChargeRuleSettingBean settingBean = new TCChargeRuleSettingBean();
		settingBean.setId(id);
		settingBean.setType(type);
		settingBean.setNum(num);
		settingBean.setFreeTime(freeTime);
		settingBean.setMaxCycle(maxCycle);
		settingBean.setMaxToll(maxToll);
		settingBean.setTimesUserMax(timesUserMax);
		settingBean.setMaxChargeNode(maxChargeNode);
		settingBean.setVersion(version);
		settingBean.setsComment(sComment);

		// 获得node节点的数据
		Element node = null;

		List<TCChargeRuleNodeBean> tcChargeRuleNodes = new ArrayList<TCChargeRuleNodeBean>();

		for (int i = 1; i <= num; i++) {
			node = chargeRule.element("node" + i);
			String startTime = node.element("startTime").getText();
			;
			String endTime = node.element("endTime").getText();
			;
			String smallToll = node.element("smallToll").getText();
			;
			String largeToll = node.element("largeToll").getText();
			;

			// 创建node对象
			TCChargeRuleNodeBean nodeBean = new TCChargeRuleNodeBean();
			nodeBean.setId(i);
			nodeBean.setStartTime(startTime);
			nodeBean.setEndTime(endTime);
			nodeBean.setSmallToll(Integer.parseInt(smallToll));
			nodeBean.setLargeToll(Integer.parseInt(largeToll));
			tcChargeRuleNodes.add(nodeBean);
		}

		TCChargeRuleXmlBean tcchargeRuleXmlBean = new TCChargeRuleXmlBean();
		// 增加setting和node
		tcchargeRuleXmlBean.setTcSetting(settingBean);
		tcchargeRuleXmlBean.setTcNode(tcChargeRuleNodes);

		Map<String, TCChargeRuleXmlBean> map = ChargeRuleStaticList.getTcChargeRuleXmlBean();
		// 判断是否创建
		if (map == null || map.size() == 0) {
			map = new HashMap<String, TCChargeRuleXmlBean>();
		}
		map.put(id, tcchargeRuleXmlBean);
		ChargeRuleStaticList.setTcChargeRuleXmlBean(map);
	}
	
	
	/**
	 * 
	 * @Title : parseXmlByTimeCount
	 * @功能描述: 特殊计费规则：省人民医院享受优惠计费
	 * @传入参数：@param chargeRule
	 * @返回类型：void
	 * @throws ：
	 */
	private void parseXmlBySpecChgTimeInterval(Element chargeRule) {
		Element setting = chargeRule.element("setting");
		String id = setting.element("id").getText().trim();
		int type = Integer.parseInt(setting.element("type").getText().trim());
		int num = Integer.parseInt(setting.element("num").getText().trim());
		int freeTime = Integer.parseInt(setting.element("freeTime").getText().trim());

		String freeWeekday = setting.element("freeWeekday").getText().trim();
		int changeCycle = Integer.parseInt(setting.element("changeCycle").getText().trim());
		String changeId = setting.element("changeId").getText().trim();
		
		// 取消最大周期时长和金额
		// int maxCycle =
		// Integer.parseInt(setting.element("maxCycle").getText().trim());
		// int maxToll =
		// Integer.parseInt(setting.element("maxToll").getText().trim());
		int version = Integer.parseInt(setting.element("version").getText().trim());
		String sComment = setting.element("sComment").getText().trim();

		SpecChgTIChargeRuleSettingBean settingBean = new SpecChgTIChargeRuleSettingBean();

		// 赋值基础数据
		settingBean.setId(id);
		settingBean.setType(type);
		settingBean.setNum(num);
		settingBean.setFreeTime(freeTime);
		// 取消最大周期时长和金额
		// settingBean.setMaxCycle(maxCycle);
		// settingBean.setMaxToll(maxToll);
		if(freeWeekday!=null && !"".equals(freeWeekday))
		{
			String[] freeDayArray = freeWeekday.split("\\|");
			
			// 获得免费一周中的天数
			List<Integer> freeDay = new ArrayList<Integer>();
			for(int i=0;i<freeDayArray.length;i++)
			{
				freeDay.add(Integer.parseInt(freeDayArray[i]));
			}
			
			settingBean.setFreeWeekday(freeDay);
//			settingBean.setFreeWeekday(freeWeekday);
		}
		
		settingBean.setChangeCycle(changeCycle);
		settingBean.setChangeId(changeId);
		settingBean.setVersion(version);
		settingBean.setsComment(sComment);

		// 获得node节点的数据
		Element node = null;
		List<SpecChgTIChargeRuleNodeBean> tiChargeRuleNodes = new ArrayList<SpecChgTIChargeRuleNodeBean>();
		for (int i = 1; i <= num; i++) {
			node = chargeRule.element("node" + i);
			String startTime = node.element("startTime").getText();
			String endTime = node.element("endTime").getText();
			String period = node.element("period").getText();
			int limitType = Integer.parseInt(node.element("limitType").getText().trim());
			String smallLimitTime = node.element("smallLimitTime").getText();
			String largeLimitTime = node.element("largeLimitTime").getText();
			String smallLimitToll = node.element("smallLimitToll").getText();
			String largeLimitToll = node.element("largeLimitToll").getText();
			// String limitToll = node.element("limitToll").getText();
			String smallToll = node.element("smallToll").getText();
			String largeToll = node.element("largeToll").getText();

			// 创建node对象
			SpecChgTIChargeRuleNodeBean nodeBean = new SpecChgTIChargeRuleNodeBean();
			nodeBean.setId(i);
			nodeBean.setStartTime(startTime);
			nodeBean.setEndTime(endTime);
			nodeBean.setPeriod(Integer.parseInt(period));
			nodeBean.setLimitType(limitType);
			nodeBean.setSmallLimitTime(Integer.parseInt(smallLimitTime));
			nodeBean.setLargeLimitTime(Integer.parseInt(largeLimitTime));
			nodeBean.setSmallLimitToll(Integer.parseInt(smallLimitToll));
			nodeBean.setLargeLimitToll(Integer.parseInt(largeLimitToll));
			// nodeBean.setLimitToll(Integer.parseInt(limitToll));
			nodeBean.setSmallToll(Integer.parseInt(smallToll));
			nodeBean.setLargeToll(Integer.parseInt(largeToll));
			tiChargeRuleNodes.add(nodeBean);
		}

		SpecChgTIChargeRuleXmlBean tiChargeRuleXmlBean = new SpecChgTIChargeRuleXmlBean();
		tiChargeRuleXmlBean.setTiSetting(settingBean);
		tiChargeRuleXmlBean.setTiNode(tiChargeRuleNodes);

		Map<String, SpecChgTIChargeRuleXmlBean> map = ChargeRuleStaticList.getSpecChgTIChargeRuleXmlBean();
		// 判断是否创建
		if (map == null || map.size() == 0) {
			map = new HashMap<String, SpecChgTIChargeRuleXmlBean>();
		}
		map.put(id, tiChargeRuleXmlBean);
		ChargeRuleStaticList.setSpecChgTIChargeRuleXmlBean(map);
	}
	

	/*
	 * public static void main(String[] args) { parseChargRuleXml(); }
	 */
}
