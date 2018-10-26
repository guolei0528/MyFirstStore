package com.project.backMng.admin.voidCard.EtcBlackListMng.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.voidCard.EtcBlackListMng.service.EtcBlackListMngService;
import com.project.backMng.admin.voidCard.EtcVoidCardMng.action.EtcVoidCardMngAction;
import com.project.backMng.admin.voidCard.EtcVoidCardMng.service.EtcVoidCardMngService;
import com.project.common.tool.StringUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/voidCard/EtcBlackListMng", method = { RequestMethod.GET, RequestMethod.POST })
public class EtcBlackListMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(EtcBlackListMngAction.class);
	/**
	 * service
	 */
	private EtcBlackListMngService etcBlackListMngService;
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request, "query_CardID");
		queryParam.putRequestString(request, "query_License");
		queryParam.putRequestString(request, "query_OBUID");
		queryParam.putRequestString(request, "province");
		
		
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/voidCard/EtcBlackListMng/EtcBlackListMng_list.jsp");
		String query_CardID = request.getParameter("query_CardID");
		String query_License = request.getParameter("query_License");
		String query_OBUID = request.getParameter("query_OBUID");
		String province = request.getParameter("province");
		
		if(StringUtil.isEmpty(query_CardID)
				&& StringUtil.isEmpty(query_License) && StringUtil.isEmpty(query_OBUID) 
				&& StringUtil.isEmpty(province))
		{
			province = "32";
			queryParam.put("province", "32");
			view.addObject(LIST,etcBlackListMngService.findList(queryParam, page));
		}
		else
		{
			view.addObject(LIST,etcBlackListMngService.findList(queryParam, page));
			queryParam.putRequestString(request, "province");
		}
		
		//重新赋值省份字段
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		return view;
	}
	
	
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/findEtcList", method = { RequestMethod.GET, RequestMethod.POST })
	public void findEtcList(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.put("query_License", "3苏A22222");
		queryParam.put("query_CardID", "30847220000008332");
		queryParam.put("query_OBUID", "88598868");
		//0847220000008332
		etcBlackListMngService.findEtcList(queryParam);
	}
	
	public EtcBlackListMngService getEtcBlackListMngService() {
		return etcBlackListMngService;
	}
	public void setEtcBlackListMngService(EtcBlackListMngService etcBlackListMngService) {
		this.etcBlackListMngService = etcBlackListMngService;
	}
	
	
}
