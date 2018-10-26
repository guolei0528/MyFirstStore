package com.project.backMng.admin.report.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.report.model.FlowIncomeBaseBean;
import com.project.backMng.admin.report.service.FlowIncomeReportService;
import com.project.common.tool.StringUtil;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "backMng/admin/report/FlowIncome", method = { RequestMethod.GET, RequestMethod.POST })
public class FlowIncomeReportAction extends BaseController {

	private final static Logger log = LogManager.getLogger(FlowIncomeReportAction.class);

	/**
	 * service
	 */
	private FlowIncomeReportService flowIncomeReportService;

	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/report", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request) {
		ObjectMap queryParam = ObjectMap.newInstance();

		Page page = getPage(request);
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/flowIncome/FlowIncomeReport.jsp");
		// view.addObject(LIST, memberSaleMngService.findList(queryParam,
		// page));
		
		view.addObject(LIST, flowIncomeReportService.findList(queryParam, page));
		view.addObject("QUERY_PARK_LIST", flowIncomeReportService.findParkInfoList());
		view.addObject("QUERY_AREA_LIST", flowIncomeReportService.findAreaInfoList());
		view.addObject("QUERY_LANE_LIST", flowIncomeReportService.findLaneInfoList());
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);

		return view;
	}
	
	
	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/baseInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView baseInfo(HttpServletRequest request) {
		ObjectMap queryParam = ObjectMap.newInstance();
		Page page = getPage(request);
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/flowIncome/ParkRunBaseInfo.jsp");
		
		//判断是否有值，如果没有给入默认值，默认查询自然日和天
		if(StringUtil.isEmpty(request.getParameter("type")))
		{
			queryParam.put("type", 1);
		}
		else{
			
			queryParam.putRequestInt(request, "type");
		}
		if(StringUtil.isEmpty(request.getParameter("cycle")))
		{
			queryParam.put("cycle", 2);
		}
		else{
			
			queryParam.putRequestInt(request, "cycle");
		}
		
		view.addObject("today_info", flowIncomeReportService.todayBaseInfo());
		view.addObject("yesterday_info", flowIncomeReportService.yesterdayBaseInfo());
		flowIncomeReportService.findParkStock();
		view.addObject("stock",flowIncomeReportService.findParkStock());
		view.addObject("fail_recognition", flowIncomeReportService.countFailRecognitionByToday());
		view.addObject("detail",flowIncomeReportService.findParkRunDetail(queryParam));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		return view;
	}
	
	
	
	
	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/baseInfo_base", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView baseInfoBase(HttpServletRequest request) {
		ObjectMap queryParam = ObjectMap.newInstance();
		Page page = getPage(request);
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/flowIncome/ParkRunBaseInfo_base.jsp");
		
		//判断是否有值，如果没有给入默认值，默认查询自然日和天
		if(StringUtil.isEmpty(request.getParameter("type")))
		{
			queryParam.put("type", 1);
		}
		else{
			
			queryParam.putRequestInt(request, "type");
		}
		if(StringUtil.isEmpty(request.getParameter("cycle")))
		{
			queryParam.put("cycle", 2);
		}
		else{
			
			queryParam.putRequestInt(request, "cycle");
		}
		
		view.addObject("today_info", flowIncomeReportService.todayBaseInfo());
		view.addObject("yesterday_info", flowIncomeReportService.yesterdayBaseInfo());
		flowIncomeReportService.findParkStock();
		view.addObject("stock",flowIncomeReportService.findParkStock());
		view.addObject("fail_recognition", flowIncomeReportService.countFailRecognitionByToday());
		view.addObject("detail",flowIncomeReportService.findParkRunDetail(queryParam));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		return view;
	}
	
	
	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search_run_detail", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String detailInfo(HttpServletRequest request) {
		ObjectMap queryParam = ObjectMap.newInstance();
		//判断是否有值，如果没有给入默认值，默认查询自然日和天
		if(StringUtil.isEmpty(request.getParameter("type")))
		{
			queryParam.put("type", 1);
		}
		else{
			
			queryParam.putRequestInt(request, "type");
		}
		if(StringUtil.isEmpty(request.getParameter("cycle")))
		{
			queryParam.put("cycle", 2);
		}
		else{
			
			queryParam.putRequestInt(request, "cycle");
		}
		return super.responseJsonText(flowIncomeReportService.findParkRunDetail(queryParam));
	}
	
	

	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/lineChart", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView lineChart(HttpServletRequest request) {
		ObjectMap queryParam = ObjectMap.newInstance();
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/flowIncome/FlowIncomeLineChart.jsp");
		// view.addObject(LIST, memberSaleMngService.findList(queryParam,
		// page));

		// 判断类型，1按小时，2按8天周期
		int type = 1;
		String query_toll_cycle = request.getParameter("query_toll_cycle");
		if (query_toll_cycle != null && !"".equals(query_toll_cycle.trim())) {
			type = Integer.parseInt(query_toll_cycle);
		}
		Date now = new Date();
		String nowStr = DateUtil.get4yMd(now);
		queryParam.put("query_toll_start_time", nowStr);
		queryParam.put("query_toll_end_time", nowStr);
		queryParam.put("query_flow_start_time", nowStr);
		queryParam.put("query_flow_end_time", nowStr);
		// 赋值当前时间
		view.addObject("queryParam", queryParam);

		// 根据时间周期类型、开始时间、结束时间获得json格式的周期金额数据
		// JSONArray jsons =
		// JSONArray.fromObject(flowIncomeReportService.findTollReport(type,startTime,endTime));
		// System.out.println(jsons.toString());
		// view.addObject("TOLL_REPORTS", jsons);
		/*
		 * view.addObject(LIST,flowIncomeReportService.findList(queryParam,
		 * page)); view.addObject("QUERY_PARK_LIST",
		 * flowIncomeReportService.findParkInfoList());
		 * view.addObject("QUERY_AREA_LIST",
		 * flowIncomeReportService.findAreaInfoList());
		 * view.addObject("QUERY_LANE_LIST",
		 * flowIncomeReportService.findLaneInfoList());
		 * view.addObject("queryParam", queryParam); view.addObject(PAGE, page);
		 */
		return view;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search_toll_report", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String searchTollReport(HttpServletRequest request) {
		try {
			// 判断类型，1按小时，2按8天周期
			int type = 1;
			String query_toll_cycle = request.getParameter("query_toll_cycle");
			// 获得查询通行费的开始时间
			String query_toll_start_time = request.getParameter("query_toll_start_time");
			// 获得查询通行费的结束时间
			String query_toll_end_time = request.getParameter("query_toll_end_time");

			// 获得查询类型:小时/8天
			if (query_toll_cycle != null && !"".equals(query_toll_cycle.trim())) {
				type = Integer.parseInt(query_toll_cycle);
			}

			// 开始时间句柄
			Date startTime = null;
			// 结束时间句柄
			Date endTime = null;
			// 判断开始时间是否存在
			if (!StringUtil.isEmpty(query_toll_start_time)) {
				startTime = DateUtil.parse4yMd(query_toll_start_time);
			}
			// 判断结束时间是否存在
			if (!StringUtil.isEmpty(query_toll_end_time)) {
				endTime = DateUtil.parse4yMd(query_toll_end_time);
			}
			return super.responseJsonText(flowIncomeReportService.findTollReport(type, startTime, endTime));
		} catch (Exception e) {
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}

	/**
	 * 查询流量报表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search_flow_report", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String searchFlowReport(HttpServletRequest request) {
		try {
			// 判断类型，1按小时，2按8天周期
			int type = 1;
			String query_flow_cycle = request.getParameter("query_flow_cycle");
			// 获得查询流量的开始时间
			String query_flow_start_time = request.getParameter("query_flow_start_time");
			// 获得查询流量的结束时间
			String query_flow_end_time = request.getParameter("query_flow_end_time");

			// 获得查询类型:小时/8天
			if (query_flow_cycle != null && !"".equals(query_flow_cycle.trim())) {
				type = Integer.parseInt(query_flow_cycle);
			}

			// 开始时间句柄
			Date startTime = null;
			// 结束时间句柄
			Date endTime = null;
			// 判断开始时间是否存在
			if (!StringUtil.isEmpty(query_flow_start_time)) {
				startTime = DateUtil.parse4yMd(query_flow_start_time);
			}
			// 判断结束时间是否存在
			if (!StringUtil.isEmpty(query_flow_end_time)) {
				endTime = DateUtil.parse4yMd(query_flow_end_time);
			}
			JSONArray jsons = new JSONArray();
			jsons.add(flowIncomeReportService.findEntryFlowReport(type, startTime, endTime));
			jsons.add(flowIncomeReportService.findExitFlowReport(type, startTime, endTime));
			
			return jsons.toString();
		} catch (Exception e) {
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("失败"));
		}
	}

	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ETCreport", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView listEtc(HttpServletRequest request) {
		ObjectMap queryParam = ObjectMap.newInstance();
		Page page = getPage(request);
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/flow/ETCFlowReport.jsp");
		// view.addObject(LIST, memberSaleMngService.findList(queryParam,
		// page));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);

		return view;
	}
	
	
	public FlowIncomeReportService getFlowIncomeReportService() {
		return flowIncomeReportService;
	}

	public void setFlowIncomeReportService(FlowIncomeReportService flowIncomeReportService) {
		this.flowIncomeReportService = flowIncomeReportService;
	}

}
