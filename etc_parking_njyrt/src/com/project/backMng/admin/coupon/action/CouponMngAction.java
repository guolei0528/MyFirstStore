package com.project.backMng.admin.coupon.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.coupon.model.CouponMngBean;
import com.project.backMng.admin.coupon.service.CouponMngService;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/coupon/CouponMng", method = { RequestMethod.GET, RequestMethod.POST })
public class CouponMngAction extends BaseController{

	
	private final static Logger log = LogManager.getLogger(CouponMngAction.class);
	
	
	private CouponMngService couponMngService;

	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/coupon/CouponMng/CouponMng_list.jsp");
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"query_coupon_code");
		queryParam.putRequestString(request,"query_coupon_type");
		queryParam.putRequestString(request,"query_issuer_code");
		queryParam.putRequestInt(request,"query_batch_id");
		queryParam.putRequestString(request,"query_start_time");
		queryParam.putRequestString(request,"query_end_time");
		
		// 获得状态
		String query_status = request.getParameter("query_status");
		if(query_status!=null && !"".equals(query_status))
		{
			queryParam.put("query_status", Integer.parseInt(query_status));
		}
		// 获取发行日期
		String issue_date_str = request.getParameter("query_issue_date");
		if(issue_date_str!=null && !"".equals(issue_date_str))
		{
			String issue_date = issue_date_str.substring(2, 4)+issue_date_str.substring(5, 7);
			queryParam.put("query_issue_date", issue_date);
		}
//		query_issuer_date
		Page page = getPage(request);
		view.addObject(LIST, couponMngService.findList(queryParam, page));
		queryParam.put("query_issue_date", issue_date_str);
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		return view;
	}
	
	/**
	 * 展示查看的页面
	 * @return
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request){
		String verify_code = getStr(request, "verify_code");
		CouponMngBean bean = couponMngService.findInfo(verify_code);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/coupon/CouponMng/CouponMng_view.jsp");
		view.addObject(BEAN, bean);
		return view;
	}
	
	/**
	 * 
	   * @Title : invalid 
	   * @功能描述: 优惠券作废管理页面
	   * @传入参数：@param request
	   * @传入参数：@return
	   * @返回类型：ModelAndView 
	   * @throws ：
	 */
	@RequestMapping(value = "/invalid", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView invalid(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/coupon/CouponMng/CouponMng_invalid.jsp");
		return view;
	}

	public CouponMngService getCouponMngService() {
		return couponMngService;
	}

	public void setCouponMngService(CouponMngService couponMngService) {
		this.couponMngService = couponMngService;
	}
	
	
	
}
