package com.project.backMng.admin.report.action;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.report.service.PaymentStatementService;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/report/statement", method = { RequestMethod.GET, RequestMethod.POST })
public class PaymentStatementAction extends BaseController {

	/**
	 * service
	 */
	private PaymentStatementService paymentStatementService;

	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/paymentSummary", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView paymentSummarylist(HttpServletRequest request) {
		ObjectMap queryParam = ObjectMap.newInstance();

		Page page = getPage(request);
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/statement/paymentSummary.jsp");
		// view.addObject(LIST, memberSaleMngService.findList(queryParam,
		// page));
		queryParam.putRequestString(request, "query_start_date");
		queryParam.putRequestString(request, "query_end_date");
		
		view.addObject(LIST, paymentStatementService.findSummaryList(queryParam, page));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);

		return view;
	}

	/**
	 * 展示数据列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/paymentDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView paymentDetailList(HttpServletRequest request) {
		ObjectMap queryParam = ObjectMap.newInstance();
		String start = request.getParameter("query_start_date");
		if (start != null && !"".equals(start.trim())) {
			queryParam.put("query_start_date", start.replaceAll("-", ""));
		}
		// 获得查询结束时间
		String end = request.getParameter("query_end_date");
		if (end != null && !"".equals(end.trim())) {
			queryParam.put("query_end_date", end.replaceAll("-", ""));
		}

		/*
		 * if((start == null || "".equals(start.trim())) && (end == null ||
		 * "".equals(end.trim()))) { Date now = new Date(); String nowStr =
		 * DateUtil.get4yMd(now); queryParam.put("query_start_date",
		 * nowStr.replaceAll("-", "")); queryParam.put("query_end_date",
		 * nowStr.replaceAll("-", "")); start = nowStr; end = nowStr; }
		 */

		String disFree = request.getParameter("dis_free");
		// 1为所有数据
		if (disFree != null && !("1".equals(disFree))) {
			queryParam.put("dis_free", disFree);
		}

		String exitLane = request.getParameter("query_exit_lane");
		if (exitLane != null) {
			queryParam.put("query_exit_lane", exitLane);
		}

		String payMethod = request.getParameter("query_pay_method");
		if (payMethod != null) {
			queryParam.put("query_pay_method", payMethod);
		}
		
		//入口查询时间和出口查询时间
		String query_entry_start_date = request.getParameter("query_entry_start_date");
		String query_entry_end_date = request.getParameter("query_entry_end_date");
		String query_exit_start_date = request.getParameter("query_exit_start_date");
		String query_exit_end_date = request.getParameter("query_exit_end_date");

		if (query_entry_start_date != null && !"".equals(query_entry_start_date.trim())) {
			queryParam.put("query_entry_start_date",query_entry_start_date);
		}
		if (query_entry_end_date != null && !"".equals(query_entry_end_date.trim())) {
			queryParam.put("query_entry_end_date", query_entry_end_date);
		}

		if ((query_exit_start_date == null || "".equals(query_exit_start_date.trim()))
				&& (query_exit_end_date == null || "".equals(query_exit_end_date.trim()))) {
			Date now = new Date();
			String nowStr = DateUtil.get4yMd(now);
			query_exit_start_date = nowStr+" 00:00:00";
			
			Calendar c = Calendar.getInstance();
	        c.setTime(now);
	        c.add(Calendar.DAY_OF_MONTH, 1);
			query_exit_end_date = DateUtil.get4yMd(c.getTime())+" 00:00:00";
		}
		// 出口时间查询条件
		queryParam.put("query_exit_start_date", query_exit_start_date);
		queryParam.put("query_exit_end_date", query_exit_end_date);

		queryParam.putRequestString(request, "query_mvlicense");
		queryParam.putRequestString(request, "query_exitoperator");
		
		Page page = getPage(request);
		ModelAndView view = new ModelAndView("/jsp/backMng/admin/report/statement/paymentDetail.jsp");
		// view.addObject(LIST, memberSaleMngService.findList(queryParam,
		// page));

		// 分配页面的页数，一页15行
		page.setPageSize(15);
		view.addObject(LIST, paymentStatementService.findDetailList(queryParam, page));
		// 应收金额总计
		view.addObject("receivablesSum", paymentStatementService.receivablesDetailList(queryParam));
		// 实收金额总计
		view.addObject("totalSum", paymentStatementService.totalDetailList(queryParam));
		view.addObject("QUERY_LANE_LIST", paymentStatementService.findLaneList());
		queryParam.put("query_start_date", start);
		queryParam.put("query_end_date", end);
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);

		return view;
	}

	public PaymentStatementService getPaymentStatementService() {
		return paymentStatementService;
	}

	public void setPaymentStatementService(PaymentStatementService paymentStatementService) {
		this.paymentStatementService = paymentStatementService;
	}

}
