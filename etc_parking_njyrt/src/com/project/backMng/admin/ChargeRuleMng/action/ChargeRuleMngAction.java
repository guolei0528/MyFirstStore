package com.project.backMng.admin.ChargeRuleMng.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.ChargeRuleMng.model.ChargeRuleBean;
import com.project.backMng.admin.ChargeRuleMng.service.ChargeRuleMngService;
import com.project.common.tool.AppConst;
import com.project.common.tool.StringUtil;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/sys/ChargeRuleMng", method = { RequestMethod.GET, RequestMethod.POST })
public class ChargeRuleMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(ChargeRuleMngAction.class);
	
	/**
	 * service
	 */
	private ChargeRuleMngService chargeRuleMngService;
	
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"query_charge_id");
		queryParam.putRequestString(request,"query_begin_time");
		queryParam.putRequestString(request,"query_end_time");
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/sys/ChargeRuleMng/ChargeRuleMng_list.jsp");
		view.addObject(LIST, chargeRuleMngService.findList(queryParam, page));
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
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/sys/ChargeRuleMng/ChargeRuleMng_add.jsp");
		
		return view;
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ChargeRuleBean bean){
		try {
			if(chargeRuleMngService.findInfo(bean.getcharge_id())!=null){
				return responseJsonText(ResultBean.newErrorResult("计费规则编号重复"));
			}
			if(StringUtil.isEmpty(bean.getmoney_limit())){
				bean.setmoney_limit(null);
			}
			if(StringUtil.isEmpty(bean.getFree_time())){
				bean.setFree_time(null);
			}
			if(StringUtil.isEmpty(bean.getperiod())){
				bean.setperiod(null);
			}
			bean.setDelete_flag(AppConst.DELETE_FLAG.NO);
			chargeRuleMngService.save(bean,null);
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
		ChargeRuleBean bean=chargeRuleMngService.findInfo(id);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/sys/ChargeRuleMng/ChargeRuleMng_edit.jsp");
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
		ChargeRuleBean bean=chargeRuleMngService.findInfo(ID);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/sys/ChargeRuleMng/ChargeRuleMng_view.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,ChargeRuleBean bean){
		try {
			if(StringUtil.isEmpty(bean.getmoney_limit())){
				bean.setmoney_limit(null);
			}
			if(StringUtil.isEmpty(bean.getFree_time())){
				bean.setFree_time(null);
			}
			if(StringUtil.isEmpty(bean.getperiod())){
				bean.setperiod(null);
			}
			chargeRuleMngService.update(bean,null);
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
			chargeRuleMngService.delete(id,null);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	public ChargeRuleMngService getChargeRuleMngService() {
		return chargeRuleMngService;
	}
	public void setChargeRuleMngService(ChargeRuleMngService chargeRuleMngService) {
		this.chargeRuleMngService = chargeRuleMngService;
	}
	
}