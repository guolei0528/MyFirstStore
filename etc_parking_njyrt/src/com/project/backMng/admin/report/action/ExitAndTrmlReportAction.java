package com.project.backMng.admin.report.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.report.model.StatisticsReportBean;
import com.project.backMng.admin.report.service.ExitAndTrmlReportService;
import com.project.common.tool.ExcelPoiTools;
import com.project.common.tool.StringUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/report/exitAndTrml", method = { RequestMethod.GET, RequestMethod.POST })
public class ExitAndTrmlReportAction extends BaseController {

	private final static Logger log = LogManager.getLogger(ExitAndTrmlReportAction.class);

	private ExitAndTrmlReportService exitAndTrmlReportService;

	public ExitAndTrmlReportService getExitAndTrmlReportService() {
		return exitAndTrmlReportService;
	}

	public void setExitAndTrmlReportService(ExitAndTrmlReportService exitAndTrmlReportService) {
		this.exitAndTrmlReportService = exitAndTrmlReportService;
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
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/exitAndTrml/exitAndTrmlReport_list.jsp");

		// 出口编号
		String laneId = request.getParameter("query_lane_id");
		if (laneId != null) {
			queryParam.put("query_lane_id", laneId);
		}

		// 收费员
		String operator = request.getParameter("query_operator");
		if (operator != null) {
			queryParam.put("query_operator", operator);
		}

		// 日期
		String dateFrom = request.getParameter("query_statistics_date_from");
		if (!StringUtil.isEmpty(dateFrom)) {
			dateFrom = dateFrom.replace("-", "");
			queryParam.put("query_statistics_date_from", dateFrom);
		}
		String dateTo = request.getParameter("query_statistics_date_to");
		if (!StringUtil.isEmpty(dateTo)) {
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
		

		// 分配页面的页数，一页15行
		page.setPageSize(15);
		view.addObject(LIST, exitAndTrmlReportService.findList(queryParam, page));
		view.addObject("QUERY_LANE_LIST", exitAndTrmlReportService.findLaneList());
		view.addObject("QUERY_USER_LIST", exitAndTrmlReportService.findUserList());

		String startDate = queryParam.get("query_statistics_date_from").toString().substring(0,4)+"-"+queryParam.get("query_statistics_date_from").toString().substring(4,6)+"-"+queryParam.get("query_statistics_date_from").toString().substring(6,8);
		String endDate = queryParam.get("query_statistics_date_to").toString().substring(0,4)+"-"+queryParam.get("query_statistics_date_to").toString().substring(4,6)+"-"+queryParam.get("query_statistics_date_to").toString().substring(6,8);
		
		// 还原开始时间的赋值
		queryParam.put("query_statistics_date_from",startDate );
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
		// 出口编号
		String laneId = request.getParameter("query_lane_id");
		if (laneId != null) {
			queryParam.put("query_lane_id", laneId);
		}
		// 收费员
		String operator = request.getParameter("query_operator");
		if (operator != null) {
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
			if(fileDate.length() != 0)
			{
				fileDate.append("-"+dateTo);
			}
			else
			{
				fileDate.append(dateTo+"前");
			}
		}
		else
		{
			if(fileDate.length() != 0)
			{
				fileDate.append("后");
			}
			else
			{
				fileDate.append("全部");
			}
		}
		List<StatisticsReportBean> list = exitAndTrmlReportService.findList(queryParam);
		String targetFile="出口和集中收费报表"+fileDate.toString();
		String fileName=targetFile+".xls";
		targetFile=request.getSession().getServletContext().getRealPath("/")+targetFile+".xls";
		ExcelPoiTools tools=new ExcelPoiTools("总表");
		int row=0;
		/*List<String> names = new ArrayList<String>();
		names.add("总表");
		names.add("细表");
		ExcelPoiTools tools=new ExcelPoiTools(names);
		tools.setColWidth(columnWidthArray);*/
		tools.setColWidth(new Integer[]{15,10,10,10,10,10,10,10,10,10,10,15,15,10,10,15});
//		tools.writeHeader(row++, new String[]{"收费点","收费员","日期","总流量 (辆)","总金额 (元)","现金流量 (辆)","现金金额 (元)",
//				"ETC流量(辆)","ETC金额 (元)","微信流量(辆)","微信金额 (元)","支付宝流量(辆)","支付宝金额 (元)","放行流量","0元流量","预付费流量"});
		tools.writeHeader(row++, new String[]{"收费点","收费员","日期","总流量 ","总金额 ","现金流量","现金金额",
				"ETC流量","ETC金额 ","微信流量","微信金额 ","支付宝流量","支付宝金额 ","放行流量","0元流量","预付费流量"});
		DecimalFormat df = new DecimalFormat("0.00");
		for(StatisticsReportBean bean:list){
			tools.writeCell(row++, new String[]{bean.getLane_name(),bean.getOperator_name(),String.valueOf(bean.getStatistics_date()),
					String.valueOf((bean.getFlow_cash_total()+bean.getFlow_etc_total()+bean.getFlow_wx_total()+bean.getFlow_zfb_total())),
					String.valueOf(df.format((bean.getToll_cash_total()+bean.getToll_etc_total()+bean.getToll_wx_total()+bean.getToll_zfb_total())/100)),
					String.valueOf(bean.getFlow_cash_total()),df.format((bean.getToll_cash_total()/100)),
					String.valueOf(bean.getFlow_etc_total()),df.format((bean.getToll_etc_total()/100)),
					String.valueOf(bean.getFlow_wx_total()),df.format((bean.getToll_wx_total()/100)),
					String.valueOf(bean.getFlow_zfb_total()),df.format((bean.getToll_zfb_total()/100)),
					String.valueOf(bean.getFlow_cash_manual()),String.valueOf(bean.getFlow_cash_free()),
					String.valueOf(bean.getFlow_cash_prepay())});
		}
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
		// 出口编号
		String laneId = request.getParameter("query_lane_id");
		if (laneId != null) {
			queryParam.put("query_lane_id", laneId);
		}
		// 收费员
		String operator = request.getParameter("query_operator");
		if (operator != null) {
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
			if(fileDate.length() != 0)
			{
				fileDate.append("-"+dateTo);
			}
			else
			{
				fileDate.append(dateTo+"前");
			}
		}
		else
		{
			if(fileDate.length() != 0)
			{
				fileDate.append("后");
			}
			else
			{
				fileDate.append("全部");
			}
		}
		List<Map<String,Object>> param = exitAndTrmlReportService.exportExcelData(queryParam);
		
		
		String targetFile="出口和集中收费报表"+fileDate.toString();
		String fileName=targetFile+".xls";
		targetFile=request.getSession().getServletContext().getRealPath("/")+targetFile+".xls";
		
//		List<Map<String,Object>> params = new LinkedList<Map<String,Object>>();
		// 生成excel表格
		HSSFWorkbook workbook = new HSSFWorkbook();
		ExcelPoiTools tools=new ExcelPoiTools(workbook);
//		Integer[] totalColumnWidthArray = new Integer[]{20,20,20,20,20,20,20,20,20,20,20,20,20,20};
//		String[] totalHeader = new String[]{"收费点","收费员","日期","总流量 (辆)","总金额 (元)","现金流量 (辆)","现金金额 (元)",
//				"ETC流量(辆)","ETC金额 (元)","微信流量(辆)","微信金额 (元)","支付宝流量(辆)","支付宝金额 (元)","人工放行流量(辆)"};
//		List totalRow = new ArrayList();
//		DecimalFormat df = new DecimalFormat("0.00");
//		for(StatisticsReportBean bean:list){
//			totalRow.add(new String[]{bean.getLane_name(),bean.getOperator_name(),String.valueOf(bean.getStatistics_date()),
//					String.valueOf((bean.getFlow_cash_total()+bean.getFlow_etc_total()+bean.getFlow_wx_total()+bean.getFlow_zfb_total())),
//					String.valueOf(df.format(bean.getToll_cash_total()+bean.getToll_etc_total()+bean.getToll_wx_total()+bean.getToll_zfb_total()/100)),
//					String.valueOf(bean.getFlow_cash_total()),df.format((bean.getToll_cash_total()/100)),
//					String.valueOf(bean.getFlow_etc_total()),df.format((bean.getToll_etc_total()/100)),
//					String.valueOf(bean.getFlow_wx_total()),df.format((bean.getToll_wx_total()/100)),
//					String.valueOf(bean.getFlow_zfb_total()),df.format((bean.getToll_zfb_total()/100)),
//					String.valueOf(bean.getFlow_cash_manual())});
//		}
//		Map total = new HashMap();
//		total.put("sheetName", "总表");
//		total.put("columnWidth",totalColumnWidthArray);
//		total.put("header",totalHeader);
//		total.put("value",totalRow);
//		params.add(total);
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
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/exitAndTrml/exitAndTrmlDetailReport_list.jsp");
		
		// 停车场编号
		String parkId = request.getParameter("query_park_id");
		if (parkId != null) {
			queryParam.put("query_park_id", parkId);
		}
				
		// 区域编号
		String areaId = request.getParameter("query_area_id");
		if (areaId != null) {
			queryParam.put("query_area_id", areaId);
		}
		
		// 出口编号
		String laneId = request.getParameter("query_lane_id");
		if (laneId != null) {
			queryParam.put("query_lane_id", laneId);
		}
		
		// 出口编号
		String laneName = request.getParameter("lane_name");
		if (laneName != null) {
			queryParam.put("lane_name", laneName);
		}

		// 收费员
		String operator = request.getParameter("query_operator");
		if (operator != null) {
			queryParam.put("query_operator", operator);
		}
		
		// 收费员
		String operatorName = request.getParameter("operator_name");
		if (operator != null) {
			queryParam.put("operator_name", operatorName);
		}
		
		//  支付类型
		String payMethod = request.getParameter("query_pay_method");
		if (payMethod != null) {
			queryParam.put("query_pay_method", payMethod);
		}
		
		// 获取筛选条件
		String pdisCount = request.getParameter("query_pdis_count");
		if(pdisCount != null)
		{
			queryParam.put("query_pdis_count", pdisCount);
		}
		
		// 日期
		String date = request.getParameter("query_date");
		if (date != null) {
			date = date.replace("-", "");
			queryParam.put("query_date", date);
		}
		
		// 日期
		String query_statistics_date_from = request.getParameter("query_statistics_date_from");
		if (query_statistics_date_from != null) {
			queryParam.put("query_statistics_date_from", query_statistics_date_from);
		}
				
				// 日期
		String query_statistics_date_to = request.getParameter("query_statistics_date_to");
		if (date != null) {
			queryParam.put("query_statistics_date_to", query_statistics_date_to);
		}
		
		

		// 分配页面的页数，一页15行
		page.setPageSize(15);
//		view.addObject(LIST, exitAndTrmlReportService.findList(queryParam, page));
		int type = exitAndTrmlReportService.findLaneType(queryParam);
		List list = null;
		if(type == 2)
		{
			list = exitAndTrmlReportService.findExitList(queryParam, page);
		}
		
		if(type == 3)
		{
			list = exitAndTrmlReportService.findCenterList(queryParam, page);
		}
		view.addObject("QUERY_LANE_LIST", exitAndTrmlReportService.findLaneList());
		view.addObject("QUERY_USER_LIST", exitAndTrmlReportService.findUserList());
		view.addObject(LIST,list);
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		return view;
	}
	
	
	
	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/downLoadDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView downLoadDetail(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/download.jsp");
		ObjectMap queryParam = ObjectMap.newInstance();
		// 出口编号
		String laneId = request.getParameter("query_lane_id");
		queryParam.put("query_lane_id", laneId);
		// 收费员
		String operator = request.getParameter("query_operator");
		queryParam.put("query_operator", operator);

		String queryDate = request.getParameter("query_date");
		queryParam.put("query_statistics_date_from", queryDate);
		queryParam.put("query_statistics_date_to", queryDate);
		
	//  支付类型
			String payMethod = request.getParameter("query_pay_method");
			if (payMethod != null) {
				queryParam.put("query_pay_method", payMethod);
			}
		List<Map<String,Object>> param = exitAndTrmlReportService.exportDetailData(queryParam);
		
		
		String targetFile="出口和集中收费明细报表"+queryDate+"-"+operator;
		String fileName=targetFile+".xls";
		targetFile=request.getSession().getServletContext().getRealPath("/")+targetFile+".xls";
		
		// 生成excel表格
		HSSFWorkbook workbook = new HSSFWorkbook();
		ExcelPoiTools tools=new ExcelPoiTools(workbook);
		tools.writeList(param);
		tools.writeToFile(targetFile);
		view.addObject("fileUrl", targetFile);
		view.addObject("fileName", fileName);
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "/print", method = { RequestMethod.GET, RequestMethod.POST })
	public String print(HttpServletRequest request){
		String query_statistics_date_from = request.getParameter("query_statistics_date_from");
		String query_statistics_date_to = request.getParameter("query_statistics_date_to");
		String query_lane_id = request.getParameter("query_lane_id");
		String query_operator = request.getParameter("query_operator");
		ObjectMap queryParam = ObjectMap.newInstance();
		queryParam.put("query_lane_id", query_lane_id);
		queryParam.put("query_operator", query_operator);
		if(query_statistics_date_from!=null){
			queryParam.put("query_statistics_date_from", query_statistics_date_from.replace("-", ""));
		}
		if(query_statistics_date_to!=null){
			queryParam.put("query_statistics_date_to", query_statistics_date_to.replace("-", ""));
		}
		List<StatisticsReportBean> list = exitAndTrmlReportService.findList(queryParam);
		return super.responseJsonText(list);
	}
}
