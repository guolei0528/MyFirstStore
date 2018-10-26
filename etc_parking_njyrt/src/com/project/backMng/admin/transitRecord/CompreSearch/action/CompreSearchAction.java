package com.project.backMng.admin.transitRecord.CompreSearch.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.transitRecord.CompreSearch.model.CompreRecordBean;
import com.project.backMng.admin.transitRecord.CompreSearch.model.RecordDetailBean;
import com.project.backMng.admin.transitRecord.CompreSearch.service.CompreSearchService;
import com.project.sys.admin.login.model.LoginUserBean;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/transitRecord/CompreSearch", method = { RequestMethod.GET, RequestMethod.POST })
public class CompreSearchAction extends BaseController {

	private static final Logger log = LogManager.getLogger(CompreSearchAction.class);
	
	private CompreSearchService compreSearchService;

	public CompreSearchService getCompreSearchService() {
		return compreSearchService;
	}

	public void setCompreSearchService(CompreSearchService compreSearchService) {
		this.compreSearchService = compreSearchService;
	}
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/CompreSearch/CompreSearch_list.jsp");
		page.setCurrentPage(0);
		page.setRecordCount(0);
		view.addObject(PAGE, page);
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryPlates", method = { RequestMethod.GET, RequestMethod.POST })
	public String queryPlates(HttpServletRequest request){
		String plate = request.getParameter("plate");
		List<String> list = compreSearchService.queryPlates(plate);
		if(list!=null){
			return super.responseJsonText(list);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryRecords", method = { RequestMethod.GET, RequestMethod.POST })
	public String queryRecords(HttpServletRequest request){
		LoginUserBean loginUserBean = (LoginUserBean)request.getSession().getAttribute("loginUser");
		String username = loginUserBean.getLoginName();
		String orderid = request.getParameter("orderid");
		String plate = request.getParameter("plate");
		String begindate = request.getParameter("begindate");
		String enddate = request.getParameter("enddate");
		List<CompreRecordBean> list = compreSearchService.queryRecords(orderid, username, plate, begindate, enddate);
		System.out.println(list.get(0));
		return super.responseJsonText(list);
	}
	
	@RequestMapping(value = "/queryDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView queryDetail(HttpServletRequest request) throws UnsupportedEncodingException{
		ModelAndView view = null;
		String type = request.getParameter("type");
		String jsp = "/jsp/backMng/admin/transitRecord/CompreSearch/CompreSearch_detail_";
		if("1".equals(type)){
			view = new ModelAndView(jsp+"exit.jsp");
		}else if("2".equals(type)){
			view = new ModelAndView(jsp+"entry.jsp");
		}else if("3".equals(type)){
			view = new ModelAndView(jsp+"centerpay.jsp");
		}else if("4".equals(type)){
			view = new ModelAndView(jsp+"order.jsp");
		}
		String license = URLDecoder.decode(request.getParameter("license"),"UTF-8");
		String time = request.getParameter("time");
		List<RecordDetailBean> list = compreSearchService.queryDetail(license, time, type);
		log.info("明细："+list);
		if(list.size()>0){
			view.addObject("bean", list.get(0));
		}
		return view;
	}
}
