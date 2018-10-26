package com.project.backMng.admin.report.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.report.model.StatisticsReportBean;
import com.project.backMng.admin.report.service.MobileReportService;
import com.project.backMng.admin.report.service.PaymentStatementService;
import com.project.common.tool.ExcelPoiTools;
import com.project.common.tool.StringUtil;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.springmvc.action.BaseController;

/**
 * 
 * @类 名： MobileReportAction @功能描述： 移动支付报表action
 * @作者信息：吴超 @创建时间： 2018年2月6日下午7:00:08 @修改备注：
 */
@Controller
@RequestMapping(value = "backMng/admin/report/mobile", method = { RequestMethod.GET, RequestMethod.POST })
public class MobileReportAction extends BaseController {

	private MobileReportService mobileReportService;

	public MobileReportService getMobileReportService() {
		return mobileReportService;
	}

	public void setMobileReportService(MobileReportService mobileReportService) {
		this.mobileReportService = mobileReportService;
	}

	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/mobileWebReport", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView mobileWebReport(HttpServletRequest request) {
		ObjectMap queryParam = ObjectMap.newInstance();
		/*
		 * String start = request.getParameter("query_deal_start_time"); if
		 * (start != null && !"".equals(start.trim())) {
		 * queryParam.put("query_deal_start_time", start.replaceAll("-", "")); }
		 * // 获得查询结束时间 String end = request.getParameter("query_end_date"); if
		 * (end != null && !"".equals(end.trim())) {
		 * queryParam.put("query_end_date", end.replaceAll("-", "")); }
		 */

		// query_order_id

		String query_deal_start_time = request.getParameter("query_deal_start_time");
		String query_deal_end_time = request.getParameter("query_deal_end_time");
		if ((query_deal_start_time == null || "".equals(query_deal_start_time.trim()))
				&& (query_deal_end_time == null || "".equals(query_deal_end_time.trim()))) {
			Date now = new Date();
			String nowStr = DateUtil.get4yMd(now);
			query_deal_start_time = nowStr + " 00:00:00";

			Calendar c = Calendar.getInstance();
			c.setTime(now);
			c.add(Calendar.DAY_OF_MONTH, 1);
			query_deal_end_time = DateUtil.get4yMd(c.getTime()) + " 00:00:00";
		}

		// 成交时间查询
		queryParam.put("query_deal_start_time", query_deal_start_time);
		queryParam.put("query_deal_end_time", query_deal_end_time);

		// 支付方式
		String payMethod = request.getParameter("query_pay_method");
		if (!StringUtil.isEmpty(payMethod)) {
			queryParam.put("query_pay_method", payMethod);
		}
		
		// 获取查询车牌
		String query_mvlicense = request.getParameter("query_mvlicense");
		if (query_mvlicense != null) {
			queryParam.put("query_mvlicense", query_mvlicense);
		}

		// 订单号
		String orderId = request.getParameter("query_order_id");
		if (orderId != null) {
			queryParam.put("query_order_id", orderId);
		}

		Page page = getPage(request);
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/mobile/MobileWebReport.jsp");

		// 分配页面的页数，一页15行
		page.setPageSize(15);
		view.addObject(LIST, mobileReportService.findOrderList(queryParam, page));
		// 缴费金额总计
		view.addObject("totalSum", mobileReportService.totalList(queryParam));

		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		return view;
	}

	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request) {
		ObjectMap queryParam = ObjectMap.newInstance();
		Page page = getPage(request);
		// 支付方式
		String payMethod = request.getParameter("query_pay_method");
		if (!StringUtil.isEmpty(payMethod)) {
			queryParam.put("query_pay_method", Integer.parseInt(payMethod));
		}
		if(StringUtil.isEmpty(payMethod))
		{
			queryParam.put("query_pay_method", 3);
		}
		
		// 出口编号
		String laneId = request.getParameter("query_lane_id");
		if (laneId != null && !"".equals(laneId)) {
			queryParam.put("query_lane_id", laneId);
		}

		// 收费员
		String operator = request.getParameter("query_operator");
		if (operator != null && !"".equals(operator)) {
			queryParam.put("query_operator", operator);
		}

		// 日期
		String dateFrom = request.getParameter("query_statistics_date_from");
		if (dateFrom != null && !"".equals(dateFrom)) {
			dateFrom = dateFrom.replace("-", "");
			queryParam.put("query_statistics_date_from", dateFrom);
		}
		String dateTo = request.getParameter("query_statistics_date_to");
		if (dateTo != null && !"".equals(dateTo)) {
			dateTo = dateTo.replace("-", "");
			queryParam.put("query_statistics_date_to", dateTo);
		}
		
		// 判断开始时间和结束时间都为空
		if(StringUtil.isEmpty(dateFrom) && StringUtil.isEmpty(dateTo))
		{
			Date dt = new Date();  
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
			String today = sdf.format(dt);   
			queryParam.put("query_statistics_date_from", today);
			queryParam.put("query_statistics_date_to", today);
		}

		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/mobile/mobileReport_list.jsp");

		view.addObject("QUERY_LANE_LIST", mobileReportService.findLaneList());
		view.addObject("QUERY_USER_LIST", mobileReportService.findUserList());


		// 分配页面的页数，一页15行
		page.setPageSize(15);
		view.addObject(LIST, mobileReportService.findList(queryParam, page));
		
		String startDate = queryParam.get("query_statistics_date_from").toString().substring(0,4)+"-"+queryParam.get("query_statistics_date_from").toString().substring(4,6)+"-"+queryParam.get("query_statistics_date_from").toString().substring(6,8);
		String endDate = queryParam.get("query_statistics_date_to").toString().substring(0,4)+"-"+queryParam.get("query_statistics_date_to").toString().substring(4,6)+"-"+queryParam.get("query_statistics_date_to").toString().substring(6,8);
		// 还原开始时间的赋值
		queryParam.put("query_statistics_date_from", startDate);
		// 还原结束时间的赋值
		queryParam.put("query_statistics_date_to", endDate);
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		return view;

	}

	/**
	 * 
	 * @Title : downLoad
	 * @功能描述: 下载
	 * @传入参数：@param request
	 * @传入参数：@return
	 * @返回类型：ModelAndView
	 * @throws ：
	 */
	@RequestMapping("/downLoad")
	public ModelAndView downLoad(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/download.jsp");
		ObjectMap queryParam = ObjectMap.newInstance();
		// 支付方式
		String payMethod = request.getParameter("query_pay_method");
		if (payMethod != null && !"".equals(payMethod)) {
			queryParam.put("query_pay_method", Integer.parseInt(payMethod));
		}
		// 出口编号
		String laneId = request.getParameter("query_lane_id");
		if (laneId != null && !"".equals(laneId)) {
			queryParam.put("query_lane_id", laneId);
		}

		// 收费员
		String operator = request.getParameter("query_operator");
		if (operator != null && !"".equals(operator)) {
			queryParam.put("query_operator", operator);
		}

		StringBuffer fileDate = new StringBuffer();
		// 日期
		String dateFrom = request.getParameter("query_statistics_date_from");
		if (dateFrom != null) {
			dateFrom = dateFrom.replace("-", "");
			queryParam.put("query_statistics_date_from", dateFrom);
			// 增加文件日期
			fileDate.append(dateFrom);
		}
		String dateTo = request.getParameter("query_statistics_date_to");
		if (dateTo != null) {
			dateTo = dateTo.replace("-", "");
			queryParam.put("query_statistics_date_to", dateTo);
			// 判断是否为空
			if (fileDate.length() != 0) {
				fileDate.append("-" + dateTo);
			} else {
				fileDate.append(dateTo + "前");
			}
		} else {
			if (fileDate.length() != 0) {
				fileDate.append("后");
			} else {
				fileDate.append("全部");
			}
		}
		List<Map<String, Object>> param = mobileReportService.exportTotalSheet(queryParam);
		String targetFile = "移动支付汇总报表" + fileDate.toString();
		String fileName = targetFile + ".xls";
		targetFile = request.getSession().getServletContext().getRealPath("/") + targetFile + ".xls";
		// 生成excel表格
		HSSFWorkbook workbook = new HSSFWorkbook();
		ExcelPoiTools tools = new ExcelPoiTools(workbook);
		tools.writeList(param);
		tools.writeToFile(targetFile);
		view.addObject("fileUrl", targetFile);
		view.addObject("fileName", fileName);
		return view;
	}

	/**
	 * 
	 * @Title : downLoad
	 * @功能描述: 下载
	 * @传入参数：@param request
	 * @传入参数：@return
	 * @返回类型：ModelAndView
	 * @throws ：
	 */
	@RequestMapping("/downLoadAll")
	public ModelAndView downLoadAll(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/download.jsp");
		ObjectMap queryParam = ObjectMap.newInstance();
		// 支付方式
		String payMethod = request.getParameter("query_pay_method");
		if (payMethod != null && !"".equals(payMethod)) {
			queryParam.put("query_pay_method", Integer.parseInt(payMethod));
		}
		// 出口编号
		String laneId = request.getParameter("query_lane_id");
		if (laneId != null && !"".equals(laneId)) {
			queryParam.put("query_lane_id", laneId);
		}

		// 收费员
		String operator = request.getParameter("query_operator");
		if (operator != null && !"".equals(operator)) {
			queryParam.put("query_operator", operator);
		}

		StringBuffer fileDate = new StringBuffer();
		// 日期
		String dateFrom = request.getParameter("query_statistics_date_from");
		if (dateFrom != null && !"".equals(dateFrom)) {
			dateFrom = dateFrom.replace("-", "");
			queryParam.put("query_statistics_date_from", dateFrom);
			// 增加文件日期
			fileDate.append(dateFrom);
		}
		String dateTo = request.getParameter("query_statistics_date_to");
		if (dateTo != null && !"".equals(dateTo)) {
			dateTo = dateTo.replace("-", "");
			queryParam.put("query_statistics_date_to", dateTo);
			// 判断是否为空
			if (fileDate.length() != 0) {
				fileDate.append("-" + dateTo);
			} else {
				fileDate.append(dateTo + "前");
			}
		} else {
			if (fileDate.length() != 0) {
				fileDate.append("后");
			} else {
				fileDate.append("全部");
			}
		}
		List<Map<String, Object>> param = mobileReportService.exportAllSheet(queryParam);
		
		String targetFile = "移动支付报表" + fileDate.toString();
		String fileName = targetFile + ".xls";
		targetFile = request.getSession().getServletContext().getRealPath("/") + targetFile + ".xls";
		// 生成excel表格
		HSSFWorkbook workbook = new HSSFWorkbook();
		ExcelPoiTools tools = new ExcelPoiTools(workbook);
		tools.writeList(param);
		tools.writeToFile(targetFile);
		view.addObject("fileUrl", targetFile);
		view.addObject("fileName", fileName);
		return view;
	}
	

	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/detailList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView detailList(HttpServletRequest request) {
		ObjectMap queryParam = ObjectMap.newInstance();
		Page page = getPage(request);
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/mobile/mobileDetailReport_list.jsp");
		
		// 停车场编号
		String parkId = request.getParameter("park_id");
		if (parkId != null) {
			queryParam.put("query_park_id", parkId);
		}
		
		// 区域编号
		String areaId = request.getParameter("area_id");
		if (areaId != null) {
			queryParam.put("query_area_id", areaId);
		}
		
		// 出口编号
		String laneId = request.getParameter("lane_id");
		if (laneId != null) {
			queryParam.put("query_lane_id", laneId);
		}

		// 收费员
		String operator = request.getParameter("operator");
		if (operator != null) {
			queryParam.put("query_operator", operator);
		}
		
		//  支付类型
		String payMethod = request.getParameter("pay_method");
		if (payMethod != null) {
			queryParam.put("query_pay_method", payMethod);
		}
		
		// 流量类型
		String flowType = request.getParameter("flow_type");
		if (flowType != null) {
			queryParam.put("query_flow_type", flowType);
		}
		
		// 日期
		String date = request.getParameter("date");
		if (date != null) {
			date = date.replace("-", "");
			queryParam.put("query_date", date);
		}
		
		ObjectMap param = ObjectMap.newInstance();
		// 储存上一页的查询记录
		// 入口编号
		String queryPayMethod = request.getParameter("query_pay_method");
		if (queryPayMethod != null) {
			param.put("query_pay_method", queryPayMethod);
		}
		// 日期
		String dateFrom = request.getParameter("query_statistics_date_from");
		if (!StringUtil.isEmpty(dateFrom)) {
			param.put("query_statistics_date_from", dateFrom);
		}
		String dateTo = request.getParameter("query_statistics_date_to");
		if (!StringUtil.isEmpty(dateTo)) {
			param.put("query_statistics_date_to", dateTo);
		}
		String queryLaneId = request.getParameter("query_lane_id");
		if (!StringUtil.isEmpty(queryLaneId)) {
			param.put("query_lane_id", queryLaneId);
		}
		String queryOperator = request.getParameter("query_operator");
		if (!StringUtil.isEmpty(queryOperator)) {
			param.put("query_operator", queryOperator);
		}
		
		// 分配页面的页数，一页15行
		page.setPageSize(15);
//		view.addObject(LIST, exitAndTrmlReportService.findList(queryParam, page));
		int type = 0;
		List list = null;
		
		if("1199".equals(laneId))
		{
			list = mobileReportService.findOrderDetailList(queryParam, page);
		}
		if(!"1199".equals(laneId))
		{
			type = mobileReportService.findLaneType(queryParam);
		}
		if(type == 2)
		{
			list = mobileReportService.findExitList(queryParam, page);
		}
		
		if(type == 3)
		{
			list = mobileReportService.findCenterList(queryParam, page);
		}
//		// 判断是否查询移动支付
//		if(type == 1199)
//		{
//			
//		}
		view.addObject(LIST,list);
		queryParam.put("query_date", date.substring(0, 4)+"-"+date.substring(4, 6)+"-"+date.substring(6,8));
		view.addObject("queryParam", queryParam);
		view.addObject("param", param);
		view.addObject(PAGE, page);
		return view;
	}
	
	
	/**
	 * 
	 * @Title : downLoad
	 * @功能描述: 下载
	 * @传入参数：@param request
	 * @传入参数：@return
	 * @返回类型：ModelAndView
	 * @throws ：
	 */
	@RequestMapping("/downLoadDetail")
	public ModelAndView downLoadDetail(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/download.jsp");
		ObjectMap queryParam = ObjectMap.newInstance();
		// 支付方式
		String payMethod = request.getParameter("pay_method");
		if (payMethod != null && !"".equals(payMethod)) {
			queryParam.put("query_pay_method", Integer.parseInt(payMethod));
		}
		// 出口编号
		String laneId = request.getParameter("lane_id");
		if (laneId != null && !"".equals(laneId)) {
			queryParam.put("query_lane_id", laneId);
		}

		// 收费员
		String operator = request.getParameter("operator");
		if (operator != null && !"".equals(operator)) {
			queryParam.put("query_operator", operator);
		}
		
		String operatorName = request.getParameter("operator_name");

		// 日期
		String date = request.getParameter("date");
		queryParam.put("query_statistics_date_from", date.replace("-", ""));
		queryParam.put("query_statistics_date_to", date.replace("-", ""));
		
		// 流量类型
		String flowType = request.getParameter("flow_type");
		if (flowType != null) {
			queryParam.put("query_flow_type", flowType);
		}
		
		List<Map<String, Object>> param = mobileReportService.exportDetailSheet(queryParam);
		
		String payStr = "";
		if("3".equals(payMethod))
		{
			payStr = "微信";
		}
		if("4".equals(payMethod))
		{
			payStr = "支付宝";
		}
		String targetFile = "移动明细支付报表" + date+operatorName+payStr;
		String fileName = targetFile + ".xls";
		targetFile = request.getSession().getServletContext().getRealPath("/") + targetFile + ".xls";
		// 生成excel表格
		HSSFWorkbook workbook = new HSSFWorkbook();
		ExcelPoiTools tools = new ExcelPoiTools(workbook);
		tools.writeList(param);
		tools.writeToFile(targetFile);
		view.addObject("fileUrl", targetFile);
		view.addObject("fileName", fileName);
		return view;
	}
}
