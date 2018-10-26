package com.project.backMng.platuser.CareTrafficRecordMng.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.platuser.CareTrafficRecordMng.model.CareTrafficRecordBean;
import com.project.backMng.platuser.CareTrafficRecordMng.service.CareTrafficRecordMngService;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/platuser/sys/CareTrafficRecordMng", method = { RequestMethod.GET, RequestMethod.POST })
public class CareTrafficRecordMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(CareTrafficRecordMngAction.class);
	/**
	 * service
	 */
	private CareTrafficRecordMngService careTrafficRecordMngService;
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"query_lane_flag");
		queryParam.putRequestString(request,"query_area_id");
		queryParam.putRequestString(request,"query_lane_id");
		queryParam.putRequestString(request,"query_date");
		queryParam.putRequestString(request,"query_pay_method");
		queryParam.putRequestString(request,"query_member_type");

		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/CareTrafficRecordMng/CareTrafficRecordMng_list.jsp");
		view.addObject(LIST, careTrafficRecordMngService.findList(queryParam, page));
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
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/CareTrafficRecordMng/CareTrafficRecordMng_add.jsp");
		
		return view;
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,CareTrafficRecordBean bean){
		try {
			careTrafficRecordMngService.save(bean,getLoginUser(request));
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
		String id=getStr(request,"ID");
		CareTrafficRecordBean bean=careTrafficRecordMngService.findInfo(id);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/CareTrafficRecordMng/CareTrafficRecordMng_edit.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 展示查看的页面
	 * @return
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request){
		String ID=getStr(request, "ID");
		CareTrafficRecordBean bean=careTrafficRecordMngService.findInfo(ID);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/CareTrafficRecordMng/CareTrafficRecordMng_view.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,CareTrafficRecordBean bean){
		try {
			careTrafficRecordMngService.update(bean,getLoginUser(request));
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
			careTrafficRecordMngService.delete(id,getLoginUser(request));
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	public CareTrafficRecordMngService getCareTrafficRecordMngService() {
		return careTrafficRecordMngService;
	}
	public void setCareTrafficRecordMngService(CareTrafficRecordMngService careTrafficRecordMngService) {
		this.careTrafficRecordMngService = careTrafficRecordMngService;
	}
	
}