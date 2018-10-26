package com.project.backMng.admin.report.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.backMng.admin.report.model.OrderCarReportBean;
import com.project.backMng.admin.report.model.StatisticsReportBean;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

public class MobileReportServiceImpl extends BaseService implements MobileReportService{
	
	public MobileReportServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.report.OrderCarReport");
	}
	
	/**
	 * 
	   * @Title : findList 
	   * @功能描述: 获取数据
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<OrderCarReportBean> findOrderList(ObjectMap queryParam, Page page) {
		int count = super.findInteger(ns("findCountReport"), queryParam);
		page.setRecordCount(count);
		List<OrderCarReportBean> paymentStatement = super.findList(ns("findReport"), queryParam,page);
		return paymentStatement;
	}
	
	
	/**
	 * 
	   * @Title : findList 
	   * @功能描述: 获取查询报表收费金额
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public int totalList(ObjectMap queryParam) {
		// 获得查询总金额
		return super.findInteger(ns("totalList"), queryParam);
	}

	/**
	 * 
	   * @Title : findList 
	   * @功能描述: 查询
	   * @传入参数：@param objectMap
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<StatisticsReportBean> findList(ObjectMap queryParam, Page page) {
		int count = super.findInteger("backMng.admin.report.mobileReport.findCountReport", queryParam);
		page.setRecordCount(count);
		List<StatisticsReportBean> statisticalReportBean = super.findList("backMng.admin.report.mobileReport.findReport", queryParam,page);
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
		return super.findList("backMng.admin.report.mobileReport.queryLaneList");
	}

	@Override
	public List<ObjectMap> findUserList() {
		return super.findList("backMng.admin.report.mobileReport.queryUserList");
	}

	@Override
	public List<Map<String, Object>> exportTotalSheet(ObjectMap queryParam) {
		List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
		List<StatisticsReportBean> Statisticslist = super.findList("backMng.admin.report.mobileReport.findReport", queryParam);
		Integer[] totalColumnWidthArray = new Integer[] { 20, 20, 20,20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20 };
		String[] totalHeader = new String[] { "停车场","区域", "收费点", "日期","收费员", "工班", "收款方式","总金额 (元)", "全部收款：笔数", "全部收款：金额",
				"优惠收款：笔数", "优惠收款：应收金额","优惠收款：实收金额", "退款收款：笔数", "退款收款：笔数"};
		List totalRow = new ArrayList();
		DecimalFormat df = new DecimalFormat("0.00");
		for (StatisticsReportBean bean : Statisticslist) {
			
			String mapyType = "";
			String totalToll = "0.00";
			// 初始化流量与收费金额
			String flowMpayFull = "0";
			String tollMpayFull = "0.00";
			String flowMpayCoupon = "0";
			String tollMpayCouponea = "0.00";
			String tollMpayCouponeb = "0.00";
			String flowMpayRefund = "0";
			String tollMpayRefund = "0";
			// 微信
			if(bean.getMpay_type() == 3)
			{
				mapyType = "微信";
				totalToll = String.valueOf(df.format(bean.getToll_wx_total() / 100));
				flowMpayFull = String.valueOf(bean.getFlow_wx_full());
				tollMpayFull =  String.valueOf(df.format(bean.getToll_wx_full() / 100));
				flowMpayCoupon = String.valueOf(bean.getFlow_wx_coupon());
				tollMpayCouponea = String.valueOf(df.format(bean.getToll_wx_coupon_ea() / 100));
				tollMpayCouponeb = String.valueOf(df.format(bean.getToll_wx_coupon_eb() / 100));
				flowMpayRefund = String.valueOf(bean.getFlow_wx_refund());
				tollMpayRefund =  String.valueOf(df.format(bean.getToll_wx_refund()/ 100));
			}
			// 支付宝
			if(bean.getMpay_type() == 4)
			{
				mapyType = "支付宝";
				totalToll = String.valueOf(df.format(bean.getToll_zfb_total() / 100));
				flowMpayFull = String.valueOf(bean.getFlow_zfb_full());
				tollMpayFull =  String.valueOf(df.format(bean.getToll_zfb_full() / 100));
				flowMpayCoupon = String.valueOf(bean.getFlow_zfb_coupon());
				tollMpayCouponea = String.valueOf(df.format(bean.getToll_zfb_coupon_ea() / 100));
				tollMpayCouponeb = String.valueOf(df.format(bean.getToll_zfb_coupon_eb() / 100));
				flowMpayRefund = String.valueOf(bean.getFlow_zfb_refund());
				tollMpayRefund =  String.valueOf(df.format(bean.getToll_zfb_refund()/ 100));
			}
			totalRow.add(new String[] { bean.getPark_name(),bean.getArea_name(),
					bean.getLane_name(), 
					String.valueOf(bean.getStatistics_date()),bean.getOperator_name(),String.valueOf(bean.getShift()),
					mapyType,
					totalToll,
					flowMpayFull,tollMpayFull,
					flowMpayCoupon,tollMpayCouponea,tollMpayCouponeb,
					flowMpayRefund,tollMpayRefund
		});
		}
		Map total = new HashMap();
		total.put("sheetName", "总表");
		total.put("columnWidth", totalColumnWidthArray);
		total.put("header", totalHeader);
		total.put("value", totalRow);
		// List list = super.findList(ns("queryExitDetialList"),queryParam);
		params.add(total);
		return params;
	}

	
	/**
	 * 打包下载所有移动支付数据
	 */
	@Override
	public List<Map<String, Object>> exportAllSheet(ObjectMap queryParam) {
		List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
		List<StatisticsReportBean> Statisticslist = super.findList("backMng.admin.report.mobileReport.findReport", queryParam);
		Integer[] totalColumnWidthArray = new Integer[] { 20, 20, 20, 20, 20,20, 20, 20, 20, 20, 20, 20, 20, 20, 20 };
		String[] totalHeader = new String[] { "停车场","区域", "收费点", "日期","收费员", "工班","收款方式", "总金额 (元)", "全部收款：笔数", "全部收款：金额",
				"优惠收款：笔数", "优惠收款：应收金额", "优惠收款：实收金额", "退款收款：笔数", "退款收款：笔数"};
		List totalRow = new ArrayList();
		DecimalFormat df = new DecimalFormat("0.00");
		for (StatisticsReportBean bean : Statisticslist) {
			String mapyType = "";
			String totalToll = "0.00";
			String flowMpayFull = "0";
			String tollMpayFull = "0.00";
			String flowMpayCoupon = "0";
			String tollMpayCouponea = "0.00";
			String tollMpayCouponeb = "0.00";
			String flowMpayRefund = "0";
			String tollMpayRefund = "0";
			// 微信
			if(bean.getMpay_type() == 3)
			{
				mapyType = "微信";
				totalToll = String.valueOf(df.format(bean.getToll_wx_total() / 100));
				flowMpayFull = String.valueOf(bean.getFlow_wx_full());
				tollMpayFull =  String.valueOf(df.format(bean.getToll_wx_full() / 100));
				flowMpayCoupon = String.valueOf(bean.getFlow_wx_coupon());
				tollMpayCouponea = String.valueOf(df.format(bean.getToll_wx_coupon_ea() / 100));
				tollMpayCouponeb = String.valueOf(df.format(bean.getToll_wx_coupon_eb() / 100));
				flowMpayRefund = String.valueOf(bean.getFlow_wx_refund());
				tollMpayRefund =  String.valueOf(df.format(bean.getToll_wx_refund()/ 100));
			}
			// 支付宝
			if(bean.getMpay_type() == 4)
			{
				mapyType = "支付宝";
				totalToll = String.valueOf(df.format(bean.getToll_zfb_total() / 100));
				flowMpayFull = String.valueOf(bean.getFlow_zfb_full());
				tollMpayFull =  String.valueOf(df.format(bean.getToll_zfb_full() / 100));
				flowMpayCoupon = String.valueOf(bean.getFlow_zfb_coupon());
				tollMpayCouponea = String.valueOf(df.format(bean.getToll_zfb_coupon_ea() / 100));
				tollMpayCouponeb = String.valueOf(df.format(bean.getToll_zfb_coupon_eb() / 100));
				flowMpayRefund = String.valueOf(bean.getFlow_zfb_refund());
				tollMpayRefund =  String.valueOf(df.format(bean.getToll_zfb_refund()/ 100));
			}
			totalRow.add(new String[] { bean.getPark_name(),bean.getArea_name(),
					bean.getLane_name(), 
					String.valueOf(bean.getStatistics_date()),bean.getOperator_name(),String.valueOf(bean.getShift()),
					mapyType,
					totalToll,
					flowMpayFull,tollMpayFull,
					flowMpayCoupon,tollMpayCouponea,tollMpayCouponeb,
					flowMpayRefund,tollMpayRefund
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
		List<ObjectMap> exitList = super.findList("backMng.admin.report.mobileReport.findExitDetialList", queryParam);
				Map detial = new HashMap();
				String sheetName = "";
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
							String payMethod = "";
							if(Integer.parseInt(exit.get("paymethod").toString()) == 3)
							{
								payMethod = "微信";
							}
							if(Integer.parseInt(exit.get("paymethod").toString())== 4)
							{
								payMethod = "支付宝";
							}
							list.add(new String[] { exit.get("exitdate").toString(), (String) exit.get("lanename"),
									(String) exit.get("operatorname"), (String) exit.get("mvlicense"),
									DateUtil.get4yMdHms((Date)exit.get("entrytime")),
									DateUtil.get4yMdHms((Date)exit.get("exittime")),
									payMethod,
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
						map.put("columnWidth", new Integer[] { 20, 20, 20, 20, 20, 20, 20,20,20});
						map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间" ,"收费方式", "应收金额(元)", "实收金额 (元)" });
						List row = new ArrayList();
						String payMethod = "";
						if(Integer.parseInt(exit.get("paymethod").toString()) == 3)
						{
							payMethod = "微信";
						}
						if(Integer.parseInt(exit.get("paymethod").toString())== 4)
						{
							payMethod = "支付宝";
						}
						row.add(new String[] { exit.get("exitdate").toString(), (String) exit.get("lanename"),
								(String) exit.get("operatorname"), (String) exit.get("mvlicense"),
								DateUtil.get4yMdHms((Date)exit.get("entrytime")),
								DateUtil.get4yMdHms((Date)exit.get("exittime")),
								payMethod,
								BigDecimal.valueOf(Long.valueOf(exit.get("pdiscounttoll").toString()))
										.divide(new BigDecimal(100)).toString(),
								BigDecimal.valueOf(Long.valueOf(exit.get("totaltoll").toString())).divide(new BigDecimal(100))
										.toString() });
						map.put("value", row);
						params.add(map);
					}
				}

				// 多张出口明细表
				List<ObjectMap> centerList = super.findList("backMng.admin.report.mobileReport.findCenterDetialList", queryParam);
				flag = false;
				for (ObjectMap center : centerList) {
					// 获取页签名称
					sheetName = (center.get("terminaldate") == null? "无":center.get("terminaldate")) + "-" + (center.get("lanename") == null? "无":center.get("lanename")) + "-" + (center.get("operatorname")== null? "无":center.get("operatorname"));
//					sheetName = center.get("terminaldate")  + "-" + center.get("lanename") + "-" + center.get("operatorname");
					flag = false;
					for (Map<String, Object> param : params) {
						// 判断是否存在该页签
						if (sheetName.equals(param.get("sheetName"))) {
							List list = (List) param.get("value");
							String payMethod = "";
							if(Integer.parseInt(center.get("paymethod").toString()) == 3)
							{
								payMethod = "微信";
							}
							if(Integer.parseInt(center.get("paymethod").toString())== 4)
							{
								payMethod = "支付宝";
							}
							list.add(new String[] { center.get("terminaldate").toString(), (String) center.get("lanename"),
									(String) center.get("operatorname"), (String) center.get("mvlicense"),
									DateUtil.get4yMdHms((Date)center.get("entrytime")),
									DateUtil.get4yMdHms((Date)center.get("terminaltime")),
									payMethod,
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
						String payMethod = "";
						if(Integer.parseInt(center.get("paymethod").toString()) == 3)
						{
							payMethod = "微信";
						}
						if(Integer.parseInt(center.get("paymethod").toString())== 4)
						{
							payMethod = "支付宝";
						}
						row.add(new String[] { center.get("terminaldate").toString(), (String) center.get("lanename"),
								(String) center.get("operatorname"), (String) center.get("mvlicense"),
								DateUtil.get4yMdHms((Date)center.get("entrytime")),
								DateUtil.get4yMdHms((Date)center.get("terminaltime")),
								payMethod,
								BigDecimal.valueOf(Long.valueOf(center.get("pdiscounttoll").toString()))
										.divide(new BigDecimal(100)).toString(),
								BigDecimal.valueOf(Long.valueOf(center.get("totaltoll").toString())).divide(new BigDecimal(100))
										.toString() });
						map.put("value", row);
						params.add(map);
					}
				}
				
				// 多张出口明细表
				List<ObjectMap>  mobileList = super.findList("backMng.admin.report.mobileReport.findMobileDetialList", queryParam);
				flag = false;
				for (ObjectMap mobile : mobileList) {
					// 获取页签名称
					sheetName = mobile.get("pay_date") == null? "无":mobile.get("pay_date").toString() + "移动支付";
//					sheetName = center.get("terminaldate")  + "-" + center.get("lanename") + "-" + center.get("operatorname");
					flag = false;
					for (Map<String, Object> param : params) {
						// 判断是否存在该页签
						if (sheetName.equals(param.get("sheetName"))) {
							List list = (List) param.get("value");
							String payMethod = "";
							if(Integer.parseInt(mobile.get("pay_method").toString()) == 3)
							{
								payMethod = "微信";
							}
							if(Integer.parseInt(mobile.get("pay_method").toString())== 4)
							{
								payMethod = "支付宝";
							}
							list.add(new String[] { mobile.get("pay_date").toString(), (String) mobile.get("mv_license"),
									DateUtil.get4yMdHms((Date)mobile.get("entry_time")),
									DateUtil.get4yMdHms((Date)mobile.get("pay_time")),
									payMethod,
									BigDecimal.valueOf(Long.valueOf(mobile.get("pay_bill").toString())).divide(new BigDecimal(100))
											.toString() });
							flag = true;
							param.put("value", list);
							break;
						}
					}

					// 不存在该页签，增加页签
					if (!flag) {
						Map map = new HashMap();
						map.put("sheetName", sheetName);
						map.put("columnWidth", new Integer[] { 20, 20, 20, 20, 20,20, 20});
						map.put("header", new String[] { "日期", "车牌","入口时间","收费时间", "收费方式", "实收金额 (元)" });
						List row = new ArrayList();
						String payMethod = "";
						if(Integer.parseInt(mobile.get("pay_method").toString()) == 3)
						{
							payMethod = "微信";
						}
						if(Integer.parseInt(mobile.get("pay_method").toString())== 4)
						{
							payMethod = "支付宝";
						}
						row.add(new String[] { mobile.get("pay_date").toString(), (String) mobile.get("mv_license"),
								DateUtil.get4yMdHms((Date)mobile.get("entry_time")),
								DateUtil.get4yMdHms((Date)mobile.get("pay_time")),
								payMethod,
								BigDecimal.valueOf(Long.valueOf(mobile.get("pay_bill").toString())).divide(new BigDecimal(100))
										.toString() });
						map.put("value", row);
						params.add(map);
					}
				}

				return params;
	}
	
	
	
	/**
	 * 打包下载所有移动支付数据
	 */
	@Override
	public List<Map<String, Object>> exportDetailSheet(ObjectMap queryParam) {
		List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
		// 多张出口明细表
		List<ObjectMap> exitList = super.findList("backMng.admin.report.mobileReport.findExitDetialList", queryParam);
				Map detial = new HashMap();
				String sheetName = "";
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
							String payMethod = "";
							if(Integer.parseInt(exit.get("paymethod").toString()) == 3)
							{
								payMethod = "微信";
							}
							if(Integer.parseInt(exit.get("paymethod").toString())== 4)
							{
								payMethod = "支付宝";
							}
							list.add(new String[] { exit.get("exitdate").toString(), (String) exit.get("lanename"),
									(String) exit.get("operatorname"), (String) exit.get("mvlicense"),
									DateUtil.get4yMdHms((Date)exit.get("entrytime")),
									DateUtil.get4yMdHms((Date)exit.get("exittime")),
									payMethod,
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
						map.put("columnWidth", new Integer[] { 20, 20, 20, 20, 20, 20, 20,20,20});
						map.put("header", new String[] { "日期", "收费点", "收费员", "车牌","入口时间","收费时间" ,"收费方式", "应收金额(元)", "实收金额 (元)" });
						List row = new ArrayList();
						String payMethod = "";
						if(Integer.parseInt(exit.get("paymethod").toString()) == 3)
						{
							payMethod = "微信";
						}
						if(Integer.parseInt(exit.get("paymethod").toString())== 4)
						{
							payMethod = "支付宝";
						}
						row.add(new String[] { exit.get("exitdate").toString(), (String) exit.get("lanename"),
								(String) exit.get("operatorname"), (String) exit.get("mvlicense"),
								DateUtil.get4yMdHms((Date)exit.get("entrytime")),
								DateUtil.get4yMdHms((Date)exit.get("exittime")),
								payMethod,
								BigDecimal.valueOf(Long.valueOf(exit.get("pdiscounttoll").toString()))
										.divide(new BigDecimal(100)).toString(),
								BigDecimal.valueOf(Long.valueOf(exit.get("totaltoll").toString())).divide(new BigDecimal(100))
										.toString() });
						map.put("value", row);
						params.add(map);
					}
				}

				// 多张出口明细表
				List<ObjectMap> centerList = super.findList("backMng.admin.report.mobileReport.findCenterDetialList", queryParam);
				flag = false;
				for (ObjectMap center : centerList) {
					// 获取页签名称
					sheetName = (center.get("terminaldate") == null? "无":center.get("terminaldate")) + "-" + (center.get("lanename") == null? "无":center.get("lanename")) + "-" + (center.get("operatorname")== null? "无":center.get("operatorname"));
//					sheetName = center.get("terminaldate")  + "-" + center.get("lanename") + "-" + center.get("operatorname");
					flag = false;
					for (Map<String, Object> param : params) {
						// 判断是否存在该页签
						if (sheetName.equals(param.get("sheetName"))) {
							List list = (List) param.get("value");
							String payMethod = "";
							if(Integer.parseInt(center.get("paymethod").toString()) == 3)
							{
								payMethod = "微信";
							}
							if(Integer.parseInt(center.get("paymethod").toString())== 4)
							{
								payMethod = "支付宝";
							}
							list.add(new String[] { center.get("terminaldate").toString(), (String) center.get("lanename"),
									(String) center.get("operatorname"), (String) center.get("mvlicense"),
									DateUtil.get4yMdHms((Date)center.get("entrytime")),
									DateUtil.get4yMdHms((Date)center.get("terminaltime")),
									payMethod,
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
						String payMethod = "";
						if(Integer.parseInt(center.get("paymethod").toString()) == 3)
						{
							payMethod = "微信";
						}
						if(Integer.parseInt(center.get("paymethod").toString())== 4)
						{
							payMethod = "支付宝";
						}
						row.add(new String[] { center.get("terminaldate").toString(), (String) center.get("lanename"),
								(String) center.get("operatorname"), (String) center.get("mvlicense"),
								DateUtil.get4yMdHms((Date)center.get("entrytime")),
								DateUtil.get4yMdHms((Date)center.get("terminaltime")),
								payMethod,
								BigDecimal.valueOf(Long.valueOf(center.get("pdiscounttoll").toString()))
										.divide(new BigDecimal(100)).toString(),
								BigDecimal.valueOf(Long.valueOf(center.get("totaltoll").toString())).divide(new BigDecimal(100))
										.toString() });
						map.put("value", row);
						params.add(map);
					}
				}
				
				// 多张出口明细表
				List<ObjectMap>  mobileList = super.findList("backMng.admin.report.mobileReport.findMobileDetialList", queryParam);
				flag = false;
				for (ObjectMap mobile : mobileList) {
					// 获取页签名称
					sheetName = mobile.get("pay_date") == null? "无":mobile.get("pay_date").toString() + "移动支付";
//					sheetName = center.get("terminaldate")  + "-" + center.get("lanename") + "-" + center.get("operatorname");
					flag = false;
					for (Map<String, Object> param : params) {
						// 判断是否存在该页签
						if (sheetName.equals(param.get("sheetName"))) {
							List list = (List) param.get("value");
							String payMethod = "";
							if(Integer.parseInt(mobile.get("pay_method").toString()) == 3)
							{
								payMethod = "微信";
							}
							if(Integer.parseInt(mobile.get("pay_method").toString())== 4)
							{
								payMethod = "支付宝";
							}
							list.add(new String[] { mobile.get("pay_date").toString(), (String) mobile.get("mv_license"),
									DateUtil.get4yMdHms((Date)mobile.get("entry_time")),
									DateUtil.get4yMdHms((Date)mobile.get("pay_time")),
									payMethod,
									BigDecimal.valueOf(Long.valueOf(mobile.get("pay_bill").toString())).divide(new BigDecimal(100))
											.toString() });
							flag = true;
							param.put("value", list);
							break;
						}
					}

					// 不存在该页签，增加页签
					if (!flag) {
						Map map = new HashMap();
						map.put("sheetName", sheetName);
						map.put("columnWidth", new Integer[] { 20, 20, 20, 20, 20,20, 20});
						map.put("header", new String[] { "日期", "车牌","入口时间","收费时间", "收费方式", "实收金额 (元)" });
						List row = new ArrayList();
						String payMethod = "";
						if(Integer.parseInt(mobile.get("pay_method").toString()) == 3)
						{
							payMethod = "微信";
						}
						if(Integer.parseInt(mobile.get("pay_method").toString())== 4)
						{
							payMethod = "支付宝";
						}
						row.add(new String[] { mobile.get("pay_date").toString(), (String) mobile.get("mv_license"),
								DateUtil.get4yMdHms((Date)mobile.get("entry_time")),
								DateUtil.get4yMdHms((Date)mobile.get("pay_time")),
								payMethod,
								BigDecimal.valueOf(Long.valueOf(mobile.get("pay_bill").toString())).divide(new BigDecimal(100))
										.toString() });
						map.put("value", row);
						params.add(map);
					}
				}

				return params;
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
		return super.findInteger("backMng.admin.report.mobileReport.queryLaneType",queryParam);
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
		int count = super.findInteger("backMng.admin.report.mobileReport.findCountExitList", queryParam);
		page.setRecordCount(count);
		List<ObjectMap> map = super.findList("backMng.admin.report.mobileReport.findExitList", queryParam, page);
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
		int count = super.findInteger("backMng.admin.report.mobileReport.findCountCenterList", queryParam);
		page.setRecordCount(count);
		List<ObjectMap> map = super.findList("backMng.admin.report.mobileReport.findCenterList", queryParam, page);
		return map;
	}

	@Override
	public List findOrderDetailList(ObjectMap queryParam, Page page) {
		int count = super.findInteger("backMng.admin.report.mobileReport.findCountOrderDetialList", queryParam);
		page.setRecordCount(count);
		List<ObjectMap> map = super.findList("backMng.admin.report.mobileReport.findOrderDetialList", queryParam, page);
		return map;
	}

}
