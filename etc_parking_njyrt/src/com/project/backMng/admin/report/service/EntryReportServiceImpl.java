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

public class EntryReportServiceImpl  extends BaseService implements EntryReportService{

	
	public EntryReportServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.report.EntryReport");
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
	
	
	// 根据条件查询出口和集中收费报表信息
	@Override
	public List<StatisticsReportBean> findList(ObjectMap queryParam) {
		List<StatisticsReportBean> statisticalReportBean = super.findList(ns("findReport"), queryParam);
		return statisticalReportBean;
	}
	
	
	@Override
	public List<ObjectMap> findLaneList() {
		return super.findList(ns("queryLaneList"));
	}
	
	
	@Override
	public List<Map<String, Object>> exportTotalSheet(ObjectMap queryParam) {
		List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
		List<StatisticsReportBean> Statisticslist = super.findList(ns("findReport"), queryParam);
		Integer[] totalColumnWidthArray = new Integer[] { 20, 20, 20, 20, 20, 20, 20, 20};
		String[] totalHeader = new String[] { "停车场","区域", "车道", "日期", "总流量(辆)", "ETC流量(辆)","车牌识别流量(辆)", "人工放行流量(辆)"};
		List totalRow = new ArrayList();
		for (StatisticsReportBean bean : Statisticslist) {
			String mapyType = "";
			String totalToll = "";
			totalRow.add(new String[] { bean.getPark_name(),bean.getArea_name(),
					bean.getLane_name(), 
					String.valueOf(bean.getStatistics_date()),
					String.valueOf(bean.getFlow_total()),
					String.valueOf(bean.getFlow_etc()),
					String.valueOf(bean.getFlow_plate()),
					String.valueOf(bean.getFlow_manual())
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
	
	
	@Override
	public List<Map<String, Object>> exportAllSheet(ObjectMap queryParam) {
		List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
		List<StatisticsReportBean> Statisticslist = super.findList(ns("findReport"), queryParam);
		Integer[] totalColumnWidthArray = new Integer[] { 20, 20, 20, 20, 20, 20, 20, 20};
		String[] totalHeader = new String[] { "停车场","区域", "车道", "日期", "总流量(辆)", "ETC流量(辆)","车牌识别流量(辆)", "人工放行流量(辆)"};
		List totalRow = new ArrayList();
		for (StatisticsReportBean bean : Statisticslist) {
			String mapyType = "";
			String totalToll = "";
			totalRow.add(new String[] { bean.getPark_name(),bean.getArea_name(),
					bean.getLane_name(), 
					String.valueOf(bean.getStatistics_date()),
					String.valueOf(bean.getFlow_total()),
					String.valueOf(bean.getFlow_etc()),
					String.valueOf(bean.getFlow_plate()),
					String.valueOf(bean.getFlow_manual())
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
		List<ObjectMap> entryList = super.findList(ns("findEntryDetialList"), queryParam);
				Map detial = new HashMap();
				String sheetName = "";
				boolean flag = false;
				for (ObjectMap entry : entryList) {
					// 获取页签名称
					sheetName = (entry.get("entrydate") == null? "无":entry.get("entrydate")) + "-" + (entry.get("lanename") == null? "无":entry.get("lanename"));
//					sheetName = exit.get("exitdate")  + "-" + exit.get("lanename") + "-" + exit.get("operatorname");
					flag = false;
					for (Map<String, Object> param : params) {
						// 判断是否存在该页签
						if (sheetName.equals(param.get("sheetName"))) {
							List list = (List) param.get("value");
							list.add(new String[] { entry.get("entrydate").toString(), (String) entry.get("lanename"),
								(String) entry.get("mvlicense"),
								DateUtil.get4yMdHms((Date)entry.get("entrytime"))});
							flag = true;
							param.put("value", list);
							break;
						}
					}

					// 不存在该页签，增加页签
					if (!flag) {
						Map map = new HashMap();
						map.put("sheetName", sheetName);
						map.put("columnWidth", new Integer[] { 20, 20, 20, 20});
						map.put("header", new String[] { "日期", "收费点", "车牌","入口时间"});
						List row = new ArrayList();
						row.add(new String[] { entry.get("entrydate").toString(), (String) entry.get("lanename"),
								(String) entry.get("mvlicense"),
								DateUtil.get4yMdHms((Date)entry.get("entrytime"))});
						map.put("value", row);
						params.add(map);
					}
				}

				return params;
	}
	
	/**
	 * 
	 */
	@Override
	public List findEntryList(ObjectMap queryParam, Page page) {
		int count = super.findInteger(ns("findCountEntryList"), queryParam);
		page.setRecordCount(count);
		List<ObjectMap> map = super.findList(ns("findEntryList"), queryParam, page);
		return map;
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
		List<ObjectMap> entryList = super.findList(ns("findEntryDetialList"), queryParam);
		Map detial = new HashMap();
		boolean flag = false;
		for (ObjectMap entry : entryList) {
			// 获取页签名称
			sheetName = (entry.get("entrydate") == null? "无":entry.get("entrydate")) + "-" + (entry.get("lanename") == null? "无":entry.get("lanename"));
//			sheetName = exit.get("exitdate")  + "-" + exit.get("lanename") + "-" + exit.get("operatorname");
			flag = false;
			for (Map<String, Object> param : params) {
				// 判断是否存在该页签
				if (sheetName.equals(param.get("sheetName"))) {
					List list = (List) param.get("value");
					list.add(new String[] { entry.get("entrydate").toString(), (String) entry.get("lanename"),
						(String) entry.get("mvlicense"),
						DateUtil.get4yMdHms((Date)entry.get("entrytime"))});
					flag = true;
					param.put("value", list);
					break;
				}
			}

			// 不存在该页签，增加页签
			if (!flag) {
				Map map = new HashMap();
				map.put("sheetName", sheetName);
				map.put("columnWidth", new Integer[] { 20, 20, 20, 20});
				map.put("header", new String[] { "日期", "收费点", "车牌","入口时间"});
				List row = new ArrayList();
				row.add(new String[] { entry.get("entrydate").toString(), (String) entry.get("lanename"),
						(String) entry.get("mvlicense"),
						DateUtil.get4yMdHms((Date)entry.get("entrytime"))});
				map.put("value", row);
				params.add(map);
			}
		}

		return params;
	}
	
	
	
}
