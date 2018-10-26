package com.project.backMng.admin.report.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.project.backMng.admin.report.model.StatisticsReportBean;
import com.project.tools.CurrencyUtils;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

public class ExitAndTrmlReportServiceImpl extends BaseService implements ExitAndTrmlReportService {

	public ExitAndTrmlReportServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.report.ExitAndTrmlReport");
	}

	@Override
	public List<StatisticsReportBean> findList(ObjectMap queryParam, Page page) {
		int count = super.findInteger(ns("findCountReport"), queryParam);
		page.setRecordCount(count);
		List<StatisticsReportBean> statisticalReportBean = super.findList(ns("findReport"), queryParam, page);
		return statisticalReportBean;
	}

	/**
	 * 
	 * @Title : findLaneList
	 * @功能描述: 查询车道列表
	 * @传入参数：@param queryParam
	 * @传入参数：@param page
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public List<ObjectMap> findLaneList() {
		return super.findList(ns("queryLaneList"));
	}

	@Override
	public List<ObjectMap> findUserList() {
		return super.findList(ns("queryUserList"));
	}

	// 根据条件查询出口和集中收费报表信息
	@Override
	public List<StatisticsReportBean> findList(ObjectMap queryParam) {
		List<StatisticsReportBean> statisticalReportBean = super.findList(ns("findReport"), queryParam);
		return statisticalReportBean;
	}

	/**
	 * 
	 * @Title : findExitDetialList
	 * @功能描述: 根据收费点、收费员、日期查询收费明细
	 * @传入参数：@param queryParam
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public List<Map<String, Object>> exportExcelData(ObjectMap queryParam) {
		List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
		List<StatisticsReportBean> Statisticslist = super.findList(ns("findReport"), queryParam);
		Integer[] totalColumnWidthArray = new Integer[] { 15,10,10,10,10,10,10,10,10,10,10,15,15,10,10,15};
//		String[] totalHeader = new String[] { "收费点", "收费员", "日期", "总流量 (辆)", "总金额 (元)", "现金流量 (辆)", "现金金额 (元)",
//				"ETC流量(辆)", "ETC金额 (元)", "微信流量(辆)", "微信金额 (元)", "支付宝流量(辆)", "支付宝金额 (元)", "放行流量","0元流量","预付费流量" };
		String[] totalHeader = new String[] { "收费点", "收费员", "日期", "总流量", "总金额", "现金流量 ", "现金金额",
				"ETC流量", "ETC金额 ", "微信流量", "微信金额 ", "支付宝流量", "支付宝金额 ", "放行流量","0元流量","预付费流量" };
		List totalRow = new ArrayList();
		DecimalFormat df = new DecimalFormat("0.00");
		for (StatisticsReportBean bean : Statisticslist) {
			totalRow.add(new String[] { bean.getLane_name(), bean.getOperator_name(),
					String.valueOf(bean.getStatistics_date()),
					String.valueOf((bean.getFlow_cash_total() + bean.getFlow_etc_total() + bean.getFlow_wx_total()
							+ bean.getFlow_zfb_total()+bean.getFlow_cash_manual())),
					String.valueOf(df.format((bean.getToll_cash_total() + bean.getToll_etc_total()
							+ bean.getToll_wx_total() + bean.getToll_zfb_total()) / 100)),
					String.valueOf(bean.getFlow_cash_total()), df.format((bean.getToll_cash_total() / 100)),
					String.valueOf(bean.getFlow_etc_total()), df.format((bean.getToll_etc_total() / 100)),
					String.valueOf(bean.getFlow_wx_total()), df.format((bean.getToll_wx_total() / 100)),
					String.valueOf(bean.getFlow_zfb_total()), df.format((bean.getToll_zfb_total() / 100)),
					String.valueOf(bean.getFlow_cash_manual()),String.valueOf(bean.getFlow_cash_free()),
					String.valueOf(bean.getFlow_cash_prepay())});
		}
		Map total = new HashMap();
		total.put("sheetName", "总表");
		total.put("columnWidth", totalColumnWidthArray);
		total.put("header", totalHeader);
		total.put("value", totalRow);
		// List list = super.findList(ns("queryExitDetialList"),queryParam);
		params.add(total);

		// 多张出口明细表
		List<ObjectMap> exitList = super.findList(ns("findExitDetialList"), queryParam);
		Map detial = new HashMap();
		String sheetName = "";
		boolean flag = false;
		for (ObjectMap exit : exitList) {
			// 获取页签名称
			sheetName = (exit.get("exitdate") == null? "无":exit.get("exitdate")) + "-" + (exit.get("lanename") == null? "无":exit.get("lanename")) + "-" + (exit.get("operatorname")== null? "无":exit.get("operatorname"));
//			sheetName = exit.get("exitdate")  + "-" + exit.get("lanename") + "-" + exit.get("operatorname");
			flag = false;
			for (Map<String, Object> param : params) {
				// 判断是否存在该页签
				if (sheetName.equals(param.get("sheetName"))) {
					List list = (List) param.get("value");
					list.add(new String[] { exit.get("exitdate").toString(), (String) exit.get("lanename"),
							(String) exit.get("operatorname"), (String) exit.get("mvlicense"),
							DateUtil.get4yMdHms((Date)exit.get("entrytime")),
							DateUtil.get4yMdHms((Date)exit.get("exittime")),
							namebyMethod(Integer.parseInt(exit.get("paymethod").toString()),Integer.parseInt(exit.getStr("pdiscounttoll"))),
							BigDecimal.valueOf(Long.valueOf(exit.get("pdiscounttoll").toString()))
									.divide(new BigDecimal(100)).toString(),
							BigDecimal.valueOf(Long.valueOf(exit.get("totaltoll").toString()))
									.divide(new BigDecimal(100)).toString() });
					flag = true;
					param.put("value", list);
					break;
				}
			}

			// 不存在该页签，增加页签
			if (!flag) {
				Map map = new HashMap();
				map.put("sheetName", sheetName);
				map.put("columnWidth", new Integer[] {8, 10, 8,  10, 17,17,8,10,10});
//				map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间" ,"收费方式", "应收金额(元)", "实收金额 (元)" });
				map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间" ,"收费方式", "应收金额", "实收金额" });
				List row = new ArrayList();
				row.add(new String[] { exit.get("exitdate").toString(), (String) exit.get("lanename"),
						(String) exit.get("operatorname"), (String) exit.get("mvlicense"),
						DateUtil.get4yMdHms((Date)exit.get("entrytime")),
						DateUtil.get4yMdHms((Date)exit.get("exittime")),
						namebyMethod(Integer.parseInt(exit.get("paymethod").toString()),Integer.parseInt(exit.getStr("pdiscounttoll"))),
						BigDecimal.valueOf(Long.valueOf(exit.get("pdiscounttoll").toString()))
								.divide(new BigDecimal(100)).toString(),
						BigDecimal.valueOf(Long.valueOf(exit.get("totaltoll").toString())).divide(new BigDecimal(100))
								.toString() });
				map.put("value", row);
				params.add(map);
			}
		}

		// 多张出口明细表
		List<ObjectMap> centerList = super.findList(ns("findCenterDetialList"), queryParam);
		flag = false;
		for (ObjectMap center : centerList) {
			// 获取页签名称
			sheetName = (center.get("terminaldate") == null? "无":center.get("terminaldate")) + "-" + (center.get("lanename") == null? "无":center.get("lanename")) + "-" + (center.get("operatorname")== null? "无":center.get("operatorname"));
//			sheetName = center.get("terminaldate")  + "-" + center.get("lanename") + "-" + center.get("operatorname");
			flag = false;
			for (Map<String, Object> param : params) {
				// 判断是否存在该页签
				if (sheetName.equals(param.get("sheetName"))) {
					List list = (List) param.get("value");
					list.add(new String[] { center.get("terminaldate").toString(), (String) center.get("lanename"),
							(String) center.get("operatorname"), (String) center.get("mvlicense"),
							DateUtil.get4yMdHms((Date)center.get("entrytime")),
							DateUtil.get4yMdHms((Date)center.get("terminaltime")),
							namebyMethod(Integer.parseInt(center.get("paymethod").toString()),Integer.parseInt(center.getStr("pdiscounttoll"))),
							BigDecimal.valueOf(Long.valueOf(center.get("pdiscounttoll").toString()))
									.divide(new BigDecimal(100)).toString(),
							BigDecimal.valueOf(Long.valueOf(center.get("totaltoll").toString()))
									.divide(new BigDecimal(100)).toString() });
					flag = true;
					param.put("value", list);
					break;
				}
			}

			// 不存在该页签，增加页签
			if (!flag) {
				Map map = new HashMap();
				map.put("sheetName", sheetName);
				map.put("columnWidth", new Integer[] {8, 10, 8,  10, 17,17,8,10,10 });
//				map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间", "收费方式", "应收金额(元)", "实收金额 (元)" });
				map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间", "收费方式", "应收金额", "实收金额 " });
				List row = new ArrayList();
				row.add(new String[] { center.get("terminaldate").toString(), (String) center.get("lanename"),
						(String) center.get("operatorname"), (String) center.get("mvlicense"),
						DateUtil.get4yMdHms((Date)center.get("entrytime")),
						DateUtil.get4yMdHms((Date)center.get("terminaltime")),
						namebyMethod(Integer.parseInt(center.get("paymethod").toString()),Integer.parseInt(center.getStr("pdiscounttoll"))),
						BigDecimal.valueOf(Long.valueOf(center.get("pdiscounttoll").toString()))
								.divide(new BigDecimal(100)).toString(),
						BigDecimal.valueOf(Long.valueOf(center.get("totaltoll").toString())).divide(new BigDecimal(100))
								.toString() });
				map.put("value", row);
				params.add(map);
			}
		}

		return params;
	}

	/**
	 * 
	 * @Title : findExitDetialList
	 * @功能描述: 根据收费点、收费员、日期查询收费明细
	 * @传入参数：@param queryParam
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	private String namebyMethod(int payMethod,int pdiscounttoll) {
		if (payMethod == 0 && pdiscounttoll != 0) {
			return "现金";
		}

		if (payMethod == 2) {
			return "ETC";
		}

		if (payMethod == 3) {
			return "微信";
		}

		if (payMethod == 4) {
			return "支付宝";
		}

		if (payMethod == 9 || payMethod == 10) {
			return "人工放行";
		}
		
		if (payMethod == 0 && pdiscounttoll == 0) {
			return "0元放行";
		}

		if (payMethod == 11) {
			return "预支付";
		}
		return "其他";
	}

	/**
	 * 
	   * @Title : findLaneType 
	   * @功能描述: 查找车道类型
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public int findLaneType(ObjectMap queryParam) {
		return super.findInteger(ns("queryLaneType"),queryParam);
	}


	/**
	 * 
	   * @Title : findExitList 
	   * @功能描述: 查找出口信息
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List findExitList(ObjectMap queryParam,Page page) {
		int count = super.findInteger(ns("findCountExitList"), queryParam);
		page.setRecordCount(count);
		List<ObjectMap> map = super.findList(ns("findExitList"), queryParam, page);
		return map;
	}

	/**
	 * 
	   * @Title : findCenterList 
	   * @功能描述: 查找集中缴费
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List findCenterList(ObjectMap queryParam, Page page) {
		int count = super.findInteger(ns("findCountCenterList"), queryParam);
		page.setRecordCount(count);
		List<ObjectMap> map = super.findList(ns("findCenterList"), queryParam, page);
		return map;
	}

	@Override
	public List<Map<String, Object>> exportDetailData(ObjectMap queryParam) {
		List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
		String sheetName = "";
		// 多张出口明细表
				List<ObjectMap> exitList = super.findList(ns("findExitDetialList"), queryParam);
				Map detial = new HashMap();
				boolean flag = false;
				for (ObjectMap exit : exitList) {
					// 获取页签名称
					sheetName = (exit.get("exitdate") == null? "无":exit.get("exitdate")) + "-" + (exit.get("lanename") == null? "无":exit.get("lanename")) + "-" + (exit.get("operatorname")== null? "无":exit.get("operatorname"));
//					sheetName = exit.get("exitdate")  + "-" + exit.get("lanename") + "-" + exit.get("operatorname");
					flag = false;
					for (Map<String, Object> param : params) {
						// 判断是否存在该页签
						if (sheetName.equals(param.get("sheetName"))) {
							List list = (List) param.get("value");
							list.add(new String[] { exit.get("exitdate").toString(), (String) exit.get("lanename"),
									(String) exit.get("operatorname"), (String) exit.get("mvlicense"),
									DateUtil.get4yMdHms((Date)exit.get("entrytime")),
									DateUtil.get4yMdHms((Date)exit.get("exittime")),
									namebyMethod(Integer.parseInt(exit.get("paymethod").toString()),Integer.parseInt(exit.getStr("pdiscounttoll"))),
									BigDecimal.valueOf(Long.valueOf(exit.get("pdiscounttoll").toString()))
											.divide(new BigDecimal(100)).toString(),
									BigDecimal.valueOf(Long.valueOf(exit.get("totaltoll").toString()))
											.divide(new BigDecimal(100)).toString() });
							flag = true;
							param.put("value", list);
							break;
						}
					}

					// 不存在该页签，增加页签
					if (!flag) {
						Map map = new HashMap();
						map.put("sheetName", sheetName);
						map.put("columnWidth", new Integer[] { 10, 10, 10, 10, 10, 18, 20,20,10,10,10 });
//						map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间", "收费方式", "应收金额(元)", "实收金额 (元)" });
						map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间", "收费方式", "应收金额", "实收金额 " });
						List row = new ArrayList();
						row.add(new String[] { exit.get("exitdate").toString(), (String) exit.get("lanename"),
								(String) exit.get("operatorname"), (String) exit.get("mvlicense"),
								DateUtil.get4yMdHms((Date)exit.get("entrytime")),
								DateUtil.get4yMdHms((Date)exit.get("exittime")),
								namebyMethod(Integer.parseInt(exit.get("paymethod").toString()),Integer.parseInt(exit.getStr("pdiscounttoll"))),
								BigDecimal.valueOf(Long.valueOf(exit.get("pdiscounttoll").toString()))
										.divide(new BigDecimal(100)).toString(),
								BigDecimal.valueOf(Long.valueOf(exit.get("totaltoll").toString())).divide(new BigDecimal(100))
										.toString() });
						map.put("value", row);
						params.add(map);
					}
				}
				
		List<ObjectMap> centerList = super.findList(ns("findCenterDetialList"), queryParam);
		for (ObjectMap center : centerList) {
			// 获取页签名称
			sheetName = (center.get("terminaldate") == null? "无":center.get("terminaldate")) + "-" + (center.get("lanename") == null? "无":center.get("lanename")) + "-" + (center.get("operatorname")== null? "无":center.get("operatorname"));
//			sheetName = center.get("terminaldate")  + "-" + center.get("lanename") + "-" + center.get("operatorname");
			flag = false;
			for (Map<String, Object> param : params) {
				// 判断是否存在该页签
				if (sheetName.equals(param.get("sheetName"))) {
					List list = (List) param.get("value");
					list.add(new String[] { center.get("terminaldate").toString(), (String) center.get("lanename"),
							(String) center.get("operatorname"), (String) center.get("mvlicense"),
							DateUtil.get4yMdHms((Date)center.get("entrytime")),
							DateUtil.get4yMdHms((Date)center.get("terminaltime")),
							namebyMethod(Integer.parseInt(center.get("paymethod").toString()),Integer.parseInt(center.getStr("pdiscounttoll"))),
							BigDecimal.valueOf(Long.valueOf(center.get("pdiscounttoll").toString()))
									.divide(new BigDecimal(100)).toString(),
							BigDecimal.valueOf(Long.valueOf(center.get("totaltoll").toString()))
									.divide(new BigDecimal(100)).toString() });
					flag = true;
					param.put("value", list);
					break;
				}
			}

			// 不存在该页签，增加页签
			if (!flag) {
				Map map = new HashMap();
				map.put("sheetName", sheetName);
				map.put("columnWidth", new Integer[] { 10, 10, 10, 10, 10, 18, 20,20,10,10,10 });
//				map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间", "收费方式", "应收金额(元)", "实收金额 (元)" });
				map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间", "收费方式", "应收金额", "实收金额 " });
				List row = new ArrayList();
				row.add(new String[] { center.get("terminaldate").toString(), (String) center.get("lanename"),
						(String) center.get("operatorname"), (String) center.get("mvlicense"),
						DateUtil.get4yMdHms((Date)center.get("entrytime")),
						DateUtil.get4yMdHms((Date)center.get("terminaltime")),
						namebyMethod(Integer.parseInt(center.get("paymethod").toString()),Integer.parseInt(center.getStr("pdiscounttoll"))),
						BigDecimal.valueOf(Long.valueOf(center.get("pdiscounttoll").toString()))
								.divide(new BigDecimal(100)).toString(),
						BigDecimal.valueOf(Long.valueOf(center.get("totaltoll").toString())).divide(new BigDecimal(100))
								.toString() });
				map.put("value", row);
				params.add(map);
			}
		}

		return params;
	}
}
