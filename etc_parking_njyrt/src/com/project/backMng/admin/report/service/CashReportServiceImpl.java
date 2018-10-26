package com.project.backMng.admin.report.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.backMng.admin.report.model.StatisticsReportBean;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

public class CashReportServiceImpl  extends BaseService implements CashReportService{

	
	public CashReportServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.report.CashReport");
	}
	/**
	 * 
	   * @Title : findList 
	   * @功能描述: 查找入口流水
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<StatisticsReportBean> findList(ObjectMap queryParam, Page page) {
		int count = super.findInteger(ns("findCountReport"), queryParam);
		page.setRecordCount(count);
		List<StatisticsReportBean> statisticalReportBean = super.findList(ns("findReport"), queryParam,page);
		return statisticalReportBean;
	}
	@Override
	public List<StatisticsReportBean> findList(ObjectMap queryParam) {
		List<StatisticsReportBean> statisticalReportBean = super.findList(ns("findReport"), queryParam);
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
		// TODO Auto-generated method stub
		return super.findList(ns("queryUserList"));
	}
	
	
	@Override
	public List<Map<String, Object>> exportExcelData(ObjectMap queryParam) {
		List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
		List<StatisticsReportBean> Statisticslist = super.findList(ns("findReport"), queryParam);
		Integer[] totalColumnWidthArray = new Integer[] { 20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		String[] totalHeader = new String[] {"停车场","区域","收费点","日期","收费员","工班","总流量(辆)","总金额(元)","全额笔数(辆)","全额金额(辆)","优惠流量 (辆)","优惠金额 (元)","退款流量 (辆)","退款金额 (元)","放行流量(辆)","0元放行"};
		List totalRow = new ArrayList();
		DecimalFormat df = new DecimalFormat("0.00");
		for(StatisticsReportBean bean:Statisticslist){
			totalRow.add(new String[]{bean.getPark_name(),bean.getArea_name(),bean.getLane_name(),String.valueOf(bean.getStatistics_date()),bean.getOperator_name(),String.valueOf(bean.getShift()),
					String.valueOf(bean.getFlow_cash_total()+bean.getFlow_cash_coupon()+bean.getFlow_cash_refund()+bean.getFlow_cash_manual()+bean.getFlow_cash_free()),
					df.format((bean.getToll_cash_total()+bean.getToll_cash_coupon_ea()+bean.getToll_cash_refund())/100),
					String.valueOf(bean.getFlow_cash_total()),df.format((bean.getToll_cash_total()/100)),
					String.valueOf(bean.getFlow_cash_coupon()),df.format((bean.getToll_cash_coupon_ea()/100)),
					String.valueOf(bean.getFlow_cash_refund()),df.format((bean.getToll_cash_refund()/100)),
					String.valueOf(bean.getFlow_cash_manual()),String.valueOf(bean.getFlow_cash_free())
					});
		}
		Map total = new HashMap();
		total.put("sheetName", "总表");
		total.put("columnWidth", totalColumnWidthArray);
		total.put("header", totalHeader);
		total.put("value", totalRow);
		// List list = super.findList(ns("queryExitDetialList"),queryParam);
		params.add(total);

		// 多张出口明细表
		List<ObjectMap> exitList = super.findList(ns("findExitList"), queryParam);
		String sheetName = "";
		boolean flag = false;
		for (ObjectMap exit : exitList) {
			// 获取页签名称
			sheetName = (exit.get("date") == null? "无":exit.get("date")) + "-" + (exit.get("lanename") == null? "无":exit.get("lanename")) + "-" + (exit.get("operatorname")== null? "无":exit.get("operatorname"));
//			sheetName = exit.get("exitdate")  + "-" + exit.get("lanename") + "-" + exit.get("operatorname");
			flag = false;
			for (Map<String, Object> param : params) {
				// 判断是否存在该页签
				if (sheetName.equals(param.get("sheetName"))) {
					List list = (List) param.get("value");
					list.add(new String[] { exit.get("date").toString(), (String) exit.get("lanename"),
							(String) exit.get("operatorname"), (String) exit.get("mvlicense"),
							DateUtil.get4yMdHms((Date)exit.get("entrytime")),
							DateUtil.get4yMdHms((Date)exit.get("time")),
							namebyMethod(Integer.parseInt(exit.get("paymethod").toString()),Integer.parseInt(exit.getStr("pdiscounttoll")),Integer.parseInt(exit.getStr("totaltoll"))),
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
				map.put("columnWidth", new Integer[] { 20, 20, 20, 20, 20, 20, 20,20,20,20,20});
				map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间" ,"收费方式", "应收金额(元)", "实收金额 (元)" });
				List row = new ArrayList();
				row.add(new String[] { exit.get("date").toString(), (String) exit.get("lanename"),
						(String) exit.get("operatorname"), (String) exit.get("mvlicense"),
						DateUtil.get4yMdHms((Date)exit.get("entrytime")),
						DateUtil.get4yMdHms((Date)exit.get("time")),
						namebyMethod(Integer.parseInt(exit.get("paymethod").toString()),Integer.parseInt(exit.getStr("pdiscounttoll")),Integer.parseInt(exit.getStr("totaltoll"))),
						BigDecimal.valueOf(Long.valueOf(exit.get("pdiscounttoll").toString()))
								.divide(new BigDecimal(100)).toString(),
						BigDecimal.valueOf(Long.valueOf(exit.get("totaltoll").toString())).divide(new BigDecimal(100))
								.toString() });
				map.put("value", row);
				params.add(map);
			}
		}

		// 多张出口明细表
		List<ObjectMap> centerList = super.findList(ns("findCenterList"), queryParam);
		flag = false;
		for (ObjectMap center : centerList) {
			// 获取页签名称
			sheetName = (center.get("date") == null? "无":center.get("date")) + "-" + (center.get("lanename") == null? "无":center.get("lanename")) + "-" + (center.get("operatorname")== null? "无":center.get("operatorname"));
//			sheetName = center.get("terminaldate")  + "-" + center.get("lanename") + "-" + center.get("operatorname");
			flag = false;
			for (Map<String, Object> param : params) {
				// 判断是否存在该页签
				if (sheetName.equals(param.get("sheetName"))) {
					List list = (List) param.get("value");
					list.add(new String[] { center.get("date").toString(), (String) center.get("lanename"),
							(String) center.get("operatorname"), (String) center.get("mvlicense"),
							DateUtil.get4yMdHms((Date)center.get("entrytime")),
							DateUtil.get4yMdHms((Date)center.get("time")),
							namebyMethod(Integer.parseInt(center.get("paymethod").toString()),Integer.parseInt(center.getStr("pdiscounttoll")),Integer.parseInt(center.getStr("totaltoll"))),
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
				map.put("columnWidth", new Integer[] { 20, 20, 20, 20, 20,20, 20, 20,20 });
				map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间", "收费方式", "应收金额(元)", "实收金额 (元)" });
				List row = new ArrayList();
				row.add(new String[] { center.get("date").toString(), (String) center.get("lanename"),
						(String) center.get("operatorname"), (String) center.get("mvlicense"),
						DateUtil.get4yMdHms((Date)center.get("entrytime")),
						DateUtil.get4yMdHms((Date)center.get("time")),
						namebyMethod(Integer.parseInt(center.get("paymethod").toString()),Integer.parseInt(center.getStr("pdiscounttoll")),Integer.parseInt(center.getStr("totaltoll"))),
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
	   * @Title : exportDetailData 
	   * @功能描述: 导出
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<Map<String, Object>> exportDetailData(ObjectMap queryParam) {
		List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
		String sheetName = "";
		
		// 多张出口明细表
				List<ObjectMap> exitList = super.findList(ns("findExitList"), queryParam);
				boolean flag = false;
				for (ObjectMap exit : exitList) {
					// 获取页签名称
					sheetName = (exit.get("date") == null? "无":exit.get("date")) + "-" + (exit.get("lanename") == null? "无":exit.get("lanename")) + "-" + (exit.get("operatorname")== null? "无":exit.get("operatorname"));
//					sheetName = exit.get("exitdate")  + "-" + exit.get("lanename") + "-" + exit.get("operatorname");
					flag = false;
					for (Map<String, Object> param : params) {
						// 判断是否存在该页签
						if (sheetName.equals(param.get("sheetName"))) {
							List list = (List) param.get("value");
							list.add(new String[] { exit.get("date").toString(), (String) exit.get("lanename"),
									(String) exit.get("operatorname"), (String) exit.get("mvlicense"),
									DateUtil.get4yMdHms((Date)exit.get("entrytime")),
									DateUtil.get4yMdHms((Date)exit.get("time")),
									namebyMethod(Integer.parseInt(exit.get("paymethod").toString()),Integer.parseInt(exit.getStr("pdiscounttoll")),Integer.parseInt(exit.getStr("totaltoll"))),
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
						map.put("columnWidth", new Integer[] { 20, 20, 20, 20, 20, 20, 20,20,20,20,20});
						map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间" ,"收费方式", "应收金额(元)", "实收金额 (元)" });
						List row = new ArrayList();
						row.add(new String[] { exit.get("date").toString(), (String) exit.get("lanename"),
								(String) exit.get("operatorname"), (String) exit.get("mvlicense"),
								DateUtil.get4yMdHms((Date)exit.get("entrytime")),
								DateUtil.get4yMdHms((Date)exit.get("time")),
								namebyMethod(Integer.parseInt(exit.get("paymethod").toString()),Integer.parseInt(exit.getStr("pdiscounttoll")),Integer.parseInt(exit.getStr("totaltoll"))),
								BigDecimal.valueOf(Long.valueOf(exit.get("pdiscounttoll").toString()))
										.divide(new BigDecimal(100)).toString(),
								BigDecimal.valueOf(Long.valueOf(exit.get("totaltoll").toString())).divide(new BigDecimal(100))
										.toString() });
						map.put("value", row);
						params.add(map);
					}
				}

				// 多张出口明细表
				List<ObjectMap> centerList = super.findList(ns("findCenterList"), queryParam);
				flag = false;
				for (ObjectMap center : centerList) {
					// 获取页签名称
					sheetName = (center.get("date") == null? "无":center.get("date")) + "-" + (center.get("lanename") == null? "无":center.get("lanename")) + "-" + (center.get("operatorname")== null? "无":center.get("operatorname"));
//					sheetName = center.get("terminaldate")  + "-" + center.get("lanename") + "-" + center.get("operatorname");
					flag = false;
					for (Map<String, Object> param : params) {
						// 判断是否存在该页签
						if (sheetName.equals(param.get("sheetName"))) {
							List list = (List) param.get("value");
							list.add(new String[] { center.get("date").toString(), (String) center.get("lanename"),
									(String) center.get("operatorname"), (String) center.get("mvlicense"),
									DateUtil.get4yMdHms((Date)center.get("entrytime")),
									DateUtil.get4yMdHms((Date)center.get("time")),
									namebyMethod(Integer.parseInt(center.get("paymethod").toString()),Integer.parseInt(center.getStr("pdiscounttoll")),Integer.parseInt(center.getStr("totaltoll"))),
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
						map.put("columnWidth", new Integer[] { 20, 20, 20, 20, 20,20, 20, 20,20 });
						map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间", "收费方式", "应收金额(元)", "实收金额 (元)" });
						List row = new ArrayList();
						row.add(new String[] { center.get("date").toString(), (String) center.get("lanename"),
								(String) center.get("operatorname"), (String) center.get("mvlicense"),
								DateUtil.get4yMdHms((Date)center.get("entrytime")),
								DateUtil.get4yMdHms((Date)center.get("time")),
								namebyMethod(Integer.parseInt(center.get("paymethod").toString()),Integer.parseInt(center.getStr("pdiscounttoll")),Integer.parseInt(center.getStr("totaltoll"))),
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
	private String namebyMethod(int payMethod,int pdiscounttoll,int totaltoll) {
		if (payMethod == 0 && pdiscounttoll == totaltoll && pdiscounttoll > 0) {
			return "全额付款";
		}

		if (payMethod == 0 && pdiscounttoll != totaltoll &&  pdiscounttoll > 0) {
			return "优惠付款";
		}

		if (payMethod == 0 && pdiscounttoll < 0) {
			return "退款";
		}

		if (payMethod == 9 || payMethod == 10) {
			return "人工放行";
		}
		
		if (payMethod == 0 && pdiscounttoll == 0) {
			return "0元放行";
		}
		return "其他";
	}

	
	@Override
	public int findLaneType(ObjectMap queryParam) {
		return super.findInteger(ns("queryLaneType"),queryParam);
	}
	
	@Override
	public List findExitList(ObjectMap queryParam, Page page) {
		int count = super.findInteger(ns("findCountExitList"), queryParam);
		page.setRecordCount(count);
		List<ObjectMap> map = super.findList(ns("findExitList"), queryParam, page);
		return map;
	}
	
	@Override
	public List findCenterList(ObjectMap queryParam, Page page) {
		int count = super.findInteger(ns("findCountCenterList"), queryParam);
		page.setRecordCount(count);
		List<ObjectMap> map = super.findList(ns("findCenterList"), queryParam, page);
		return map;
	}
	
}
