package com.project.backMng.admin.voidCard.EtcVoidCardMng.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.voidCard.EtcVoidCardMng.model.EtcVoidCardBean;
import com.project.backMng.admin.voidCard.EtcVoidCardMng.service.EtcVoidCardMngService;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/voidCard/EtcVoidCardMng", method = { RequestMethod.GET, RequestMethod.POST })
public class EtcVoidCardMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(EtcVoidCardMngAction.class);
	/**
	 * service
	 */
	private EtcVoidCardMngService etcVoidCardMngService;
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request, "query_in_time_from");
		queryParam.putRequestString(request, "query_in_time_to");
		queryParam.putRequestString(request, "query_cancel_time_from");
		queryParam.putRequestString(request, "query_cancel_time_to");
		queryParam.putRequestString(request, "query_mv_license");
		queryParam.putRequestString(request, "query_b_list_type");

		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/voidCard/EtcVoidCardMng/EtcVoidCardMng_list.jsp");
		view.addObject(LIST, etcVoidCardMngService.findList(queryParam, page));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		
		return view;
	}
	/**
	 * 展示新增的界面
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView add(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/voidCard/EtcVoidCardMng/EtcVoidCardMng_add.jsp");
		
		return view;
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,EtcVoidCardBean bean){
		try {
			etcVoidCardMngService.save(bean,getLoginUser(request));
			return super.responseJsonText(ResultBean.newSuccessResult());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("保存失败,请稍后再试"));
	}
	/**
	 * 展示编辑的界面
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView edit(HttpServletRequest request){
		String card_id = getStr(request, "card_id");
		String card_network = getStr(request, "card_network");
		EtcVoidCardBean bean=etcVoidCardMngService.findInfo(card_id,card_network);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/voidCard/EtcVoidCardMng/EtcVoidCardMng_edit.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 展示查看的页面
	 * @return
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request){
		String card_id = getStr(request, "card_id");
		String card_network = getStr(request, "card_network");
		EtcVoidCardBean bean=etcVoidCardMngService.findInfo(card_id,card_network);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/voidCard/EtcVoidCardMng/EtcVoidCardMng_view.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,EtcVoidCardBean bean){
		try {
			etcVoidCardMngService.update(bean,getLoginUser(request));
			return super.responseJsonText(ResultBean.newSuccessResult());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("保存失败,请稍后再试"));
	}
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String delete(HttpServletRequest request){
		try{
			String id=getStr(request,"ID");
			etcVoidCardMngService.delete(id,getLoginUser(request));
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/testETC", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void testETC(HttpServletRequest request){
		try{
//			String card=getStr(request,"card");
//			etcVoidCardMngService.testETC(card);
			etcVoidCardMngService.insertETC();
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e);
		}
	}
	
	
	
	public EtcVoidCardMngService getEtcVoidCardMngService() {
		return etcVoidCardMngService;
	}
	public void setEtcVoidCardMngService(EtcVoidCardMngService etcVoidCardMngService) {
		this.etcVoidCardMngService = etcVoidCardMngService;
	}
	
}