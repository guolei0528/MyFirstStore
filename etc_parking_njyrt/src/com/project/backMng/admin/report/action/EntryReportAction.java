package com.project.backMng.admin.report.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.report.model.StatisticsReportBean;
import com.project.backMng.admin.report.service.EntryReportService;
import com.project.common.tool.ExcelPoiTools;
import com.project.common.tool.StringUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.springmvc.action.BaseController;

/**
 * 
 * @类 名： EntryReportAction @功能描述： 入口总报表
 * @作者信息：吴超 @创建时间： 2018年3月11日上午10:22:03 @修改备注：
 */
@Controller
@RequestMapping(value = "backMng/admin/report/entry", method = { RequestMethod.GET, RequestMethod.POST })
public class EntryReportAction extends BaseController {

	private final static Logger log = LogManager.getLogger(EntryReportAction.class);

	private EntryReportService entryReportService;

	public EntryReportService getEntryReportService() {
		return entryReportService;
	}

	public void setEntryReportService(EntryReportService entryReportService) {
		this.entryReportService = entryReportService;
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
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/entry/entryReport_list.jsp");

		// 入口编号
		String laneId = request.getParameter("query_lane_id");
		if (laneId != null) {
			queryParam.put("query_lane_id", laneId);
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
		view.addObject(LIST, entryReportService.findList(queryParam, page));
		 view.addObject("QUERY_LANE_LIST",
				 entryReportService.findLaneList());
		
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
		if (!StringUtil.isEmpty(dateFrom)) {
			dateFrom = dateFrom.replace("-", "");
			queryParam.put("query_statistics_date_from", dateFrom);
			// 增加文件日期
			fileDate.append(dateFrom);
		}
		String dateTo = request.getParameter("query_statistics_date_to");
		if (StringUtil.isEmpty(dateTo)) {
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
		
		
		List<Map<String,Object>> param = entryReportService.exportTotalSheet(queryParam);
		String targetFile="入口流水报表"+fileDate.toString();
		String fileName=targetFile+".xls";
		targetFile=request.getSession().getServletContext().getRealPath("/")+targetFile+".xls";
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
		List<Map<String,Object>> param = entryReportService.exportAllSheet(queryParam);
		
		
		String targetFile="入口报表"+fileDate.toString();
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
	
	
	
	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/detailList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView detailList(HttpServletRequest request) {
		ObjectMap queryParam = ObjectMap.newInstance();
		Page page = getPage(request);
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/entry/entryDetailReport_list.jsp");
		// 停车场编号
		String parkId = request.getParameter("park_id");
		String areaId = request.getParameter("area_id");
		String laneId = request.getParameter("lane_id");
		// 日期
		String date = request.getParameter("date");
		// 判断是否无值
		if(StringUtil.isEmpty(laneId) && StringUtil.isEmpty(date))
		{
			
			return new ModelAndView("/backMng/admin/report/entry/list.shtml");
		}
		
		if (parkId != null) {
			queryParam.put("query_park_id", parkId);
		}
				
				
		// 区域编号
		if (areaId != null) {
			queryParam.put("query_area_id", areaId);
		}
		
		// 出口编号
		if (laneId != null) {
			queryParam.put("query_lane_id", laneId);
		}
		
		// 入口车道名称
		String laneName = request.getParameter("lane_name");
		if (laneId != null) {
			queryParam.put("lane_name", laneName);
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

		if (date != null) {
			date = date.replace("-", "");
			queryParam.put("query_date", date);
		}
		
		
		ObjectMap param = ObjectMap.newInstance();
		// 储存上一页的查询记录
		// 入口编号
		String queryLaneId = request.getParameter("query_lane_id");
		if (queryLaneId != null) {
			param.put("query_lane_id", queryLaneId);
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
				
		// 分配页面的页数，一页15行
		page.setPageSize(15);
//		view.addObject(LIST, exitAndTrmlReportService.findList(queryParam, page));
		List list =  entryReportService.findEntryList(queryParam, page);
		view.addObject("QUERY_LANE_LIST", entryReportService.findLaneList());
		view.addObject(LIST,list);
		if(!StringUtil.isEmpty(date))
		{
			queryParam.put("query_date", date.substring(0, 4)+"-"+date.substring(4, 6)+"-"+date.substring(6,8));
		}
		view.addObject("queryParam", queryParam);
		view.addObject("param", param);
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
		String laneId = request.getParameter("lane_id");
		queryParam.put("query_lane_id", laneId);
		// 收费员
		String operator = request.getParameter("operator");
		queryParam.put("query_operator", operator);

		String queryDate = request.getParameter("date");
		queryParam.put("query_statistics_date_from", queryDate.replace("-", ""));
		queryParam.put("query_statistics_date_to", queryDate.replace("-", ""));
		
	//  支付类型
			String payMethod = request.getParameter("pay_method");
			if (payMethod != null) {
				queryParam.put("pay_method", payMethod);
			}
		
		List<Map<String,Object>> param = entryReportService.exportDetailData(queryParam);
		
		String targetFile="入口明细报表"+queryDate+"-"+operator;
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
	
	
}
