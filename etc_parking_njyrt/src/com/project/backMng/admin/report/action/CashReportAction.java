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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.report.model.StatisticsReportBean;
import com.project.backMng.admin.report.service.CashReportService;
import com.project.backMng.admin.report.service.EntryReportService;
import com.project.common.tool.ExcelPoiTools;
import com.project.common.tool.StringUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.springmvc.action.BaseController;

/**
 * 
   * @类 名： EntryReportAction
   * @功能描述： 现金报表
   * @作者信息：吴超
   * @创建时间： 2018年3月11日上午10:22:03
   * @修改备注：
 */
@Controller
@RequestMapping(value = "backMng/admin/report/cash", method = { RequestMethod.GET, RequestMethod.POST })
public class CashReportAction extends BaseController{
	
	private final static Logger log = LogManager.getLogger(CashReportAction.class);
	
	private CashReportService cashReportService;
	
	public CashReportService getCashReportService() {
		return cashReportService;
	}

	public void setCashReportService(CashReportService cashReportService) {
		this.cashReportService = cashReportService;
	}




	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam = ObjectMap.newInstance();
		Page page = getPage(request);
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/cash/cashReport_list.jsp");

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
		if (dateFrom != null) {
			dateFrom = dateFrom.replace("-", "");
			queryParam.put("query_statistics_date_from", dateFrom);
		}
		String dateTo = request.getParameter("query_statistics_date_to");
		if (dateTo != null) {
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
		view.addObject("QUERY_LANE_LIST", cashReportService.findLaneList());
		view.addObject("QUERY_USER_LIST", cashReportService.findUserList());

		// 分配页面的页数，一页15行
		page.setPageSize(15);
		view.addObject(LIST, cashReportService.findList(queryParam, page));
		
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
		List<StatisticsReportBean> list = cashReportService.findList(queryParam);
		String targetFile="现金收费报表"+fileDate.toString();
		String fileName=targetFile+".xls";
		targetFile=request.getSession().getServletContext().getRealPath("/")+targetFile+".xls";
		ExcelPoiTools tools=new ExcelPoiTools("总表");
		int row=0;
		/*List<String> names = new ArrayList<String>();
		names.add("总表");
		names.add("细表");
		ExcelPoiTools tools=new ExcelPoiTools(names);
		tools.setColWidth(columnWidthArray);*/
		tools.setColWidth(new Integer[]{20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20});
		tools.writeHeader(row++, new String[]{"停车场","区域","收费点","日期","收费员","工班","总流量(辆)","总金额(元)","全额笔数(辆)","全额金额(辆)","优惠流量 (辆)","优惠金额 (元)","退款流量 (辆)","退款金额 (元)","放行流量(辆),0元放行(辆)"});
		DecimalFormat df = new DecimalFormat("0.00");
		for(StatisticsReportBean bean:list){
			tools.writeCell(row++, new String[]{bean.getPark_name(),bean.getArea_name(),bean.getLane_name(),String.valueOf(bean.getStatistics_date()),bean.getOperator_name(),String.valueOf(bean.getShift()),
					String.valueOf(bean.getFlow_cash_total()+bean.getFlow_cash_coupon()+bean.getFlow_cash_refund()),
					df.format((bean.getToll_cash_total()+bean.getToll_cash_coupon_ea()+bean.getToll_cash_refund())/100),
					String.valueOf(bean.getFlow_cash_total()),df.format((bean.getToll_cash_total()/100)),
					String.valueOf(bean.getFlow_cash_coupon()),df.format((bean.getToll_cash_coupon_ea()/100)),
					String.valueOf(bean.getFlow_cash_refund()),df.format((bean.getToll_cash_refund()/100)),
					String.valueOf(bean.getFlow_cash_manual()),String.valueOf(bean.getFlow_cash_free())
					});
		}
		tools.writeToFile(targetFile);
		view.addObject("fileUrl", targetFile);
		view.addObject("fileName", fileName);
		return view;
	}
	
	@ResponseBody
	@RequestMapping("/print")
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
		List<StatisticsReportBean> list = cashReportService.findList(queryParam);
		return super.responseJsonText(list);
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
		List<Map<String,Object>> param = cashReportService.exportExcelData(queryParam);
		
		String targetFile="现金收费报表"+fileDate.toString();
		String fileName=targetFile+".xls";
		targetFile=request.getSession().getServletContext().getRealPath("/")+targetFile+".xls";
		
//		List<Map<String,Object>> params = new LinkedList<Map<String,Object>>();
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
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/cash/cashDetailReport_list.jsp");
		
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
		
		// 出口编号
				String laneName = request.getParameter("lane_name");
				if (laneName != null) {
					queryParam.put("query_lane_name", laneName);
				}

		// 收费员
		String operator = request.getParameter("operator");
		if (operator != null) {
			queryParam.put("query_operator", operator);
		}
		
		//  支付类型
		String flowType = request.getParameter("flow_type");
		if (flowType != null) {
			queryParam.put("flow_type", flowType);
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
		String queryOperator = request.getParameter("query_operator");
		if (!StringUtil.isEmpty(queryOperator)) {
			param.put("query_operator", queryOperator);
		}
		

		// 分配页面的页数，一页15行
		page.setPageSize(15);
//		view.addObject(LIST, exitAndTrmlReportService.findList(queryParam, page));
		int type = cashReportService.findLaneType(queryParam);
		List list = null;
		if(type == 2)
		{
			list = cashReportService.findExitList(queryParam, page);
		}
		
		if(type == 3)
		{
			list = cashReportService.findCenterList(queryParam, page);
		}
		view.addObject("QUERY_LANE_LIST", cashReportService.findLaneList());
		view.addObject("QUERY_USER_LIST", cashReportService.findUserList());
		view.addObject(LIST,list);
		queryParam.put("query_date", date.substring(0, 4)+"-"+date.substring(4, 6)+"-"+date.substring(6,8));
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
				
				// 出口编号
						String laneName = request.getParameter("lane_name");
						if (laneName != null) {
							queryParam.put("query_lane_name", laneName);
						}

				// 收费员
				String operator = request.getParameter("operator");
				if (operator != null) {
					queryParam.put("query_operator", operator);
				}
				
				//  支付类型
				String flowType = request.getParameter("flow_type");
				if (flowType != null) {
					queryParam.put("flow_type", flowType);
				}
				
				
				// 日期
				String date = request.getParameter("date");
				if (date != null) {
					date = date.replace("-", "");
					queryParam.put("query_date", date);
				}
		
				List<Map<String,Object>> param  = cashReportService.exportDetailData(queryParam);
				
				
		String targetFile="现金明细报表"+date+"-"+laneName+"-"+operator;
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
