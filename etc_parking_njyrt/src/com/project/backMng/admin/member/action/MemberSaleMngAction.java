package com.project.backMng.admin.member.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.member.model.MemberBean;
import com.project.backMng.admin.member.model.MemberSaleBean;
import com.project.backMng.admin.member.service.MemberSaleMngService;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/member/MemberSaleMng", method = { RequestMethod.GET, RequestMethod.POST })
public class MemberSaleMngAction extends BaseController{

	private final static Logger log = LogManager.getLogger(MemberSaleMngAction.class);
	
	/**
	 * service
	 */
	private MemberSaleMngService memberSaleMngService;
	
	
	
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"query_member_id");
		queryParam.putRequestString(request,"query_member_type");
		
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/member/MemberSaleMng/MemberSaleMng_list.jsp");
		view.addObject(LIST, memberSaleMngService.findList(queryParam, page));
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
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/member/MemberSaleMng/MemberSaleMng_add.jsp");
		return view;
	}
	
	/**
	 * 展示查看的页面
	 * @return
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request){
		ObjectMap queryParam = ObjectMap.newInstance();
		queryParam.putRequestString(request,"memberId");
		queryParam.putRequestString(request,"memberType");
		queryParam.putRequestString(request,"parkId");
		queryParam.putRequestString(request,"areaId");
		//日期封装成date
		DateUtil dateUtil = new DateUtil();
		Date issueTime = dateUtil.parse4yMdHms(getStr(request, "issueTime"));
		Date beginTime = dateUtil.parse4yMdHms(getStr(request, "beginTime"));
		Date endTime = dateUtil.parse4yMdHms(getStr(request, "endTime"));
		
		queryParam.put("issueTime", issueTime);
		queryParam.put("beginTime", beginTime);
		queryParam.put("endTime", endTime);
		
		MemberSaleBean bean=memberSaleMngService.findInfo(queryParam);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/member/MemberSaleMng/MemberSaleMng_view.jsp");
		view.addObject(BEAN, bean);
		return view;
	}
	
	
	
	/**
	 * 展示编辑的界面
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView edit(HttpServletRequest request){
		ObjectMap queryParam = ObjectMap.newInstance();
		queryParam.putRequestString(request,"memberId");
		queryParam.putRequestString(request,"memberType");
		queryParam.putRequestString(request,"parkId");
		queryParam.putRequestString(request,"areaId");
		//日期封装成date
		DateUtil dateUtil = new DateUtil();
		Date issueTime = dateUtil.parse4yMdHms(getStr(request, "issueTime"));
		Date beginTime = dateUtil.parse4yMdHms(getStr(request, "beginTime"));
		Date endTime = dateUtil.parse4yMdHms(getStr(request, "endTime"));
		
		queryParam.put("issueTime", issueTime);
		queryParam.put("beginTime", beginTime);
		queryParam.put("endTime", endTime);
		MemberSaleBean bean=memberSaleMngService.findInfo(queryParam);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/member/MemberSaleMng/MemberSaleMng_edit.jsp");
		
		view.addObject(BEAN, bean);
		return view;
	}
	
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,MemberSaleBean bean){
		try {
			memberSaleMngService.update(bean);
			return super.responseJsonText(ResultBean.newSuccessResult());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("保存失败,请稍后再试"));
	}
	
	
	/**
	 * 
	   * @Title : save 保存新增的会员
	   * @功能描述: TODO
	   * @传入参数：@param request
	   * @传入参数：@param bean
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,MemberSaleBean bean){
		try {
			//获得新增校验的返回值
			String message = memberSaleMngService.save(bean);
			//判断是否存在会员信息
			if(message != null && "success".equals(message))
			{
				return super.responseJsonText(ResultBean.newSuccessResult());
			}
			return super.responseJsonText(ResultBean.newErrorResult(message));
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
			ObjectMap deleteParam = ObjectMap.newInstance();
			deleteParam.putRequestString(request,"memberId");
			deleteParam.putRequestString(request,"memberType");
			deleteParam.putRequestString(request,"parkId");
			deleteParam.putRequestString(request,"areaId");
			//日期封装成date
			DateUtil dateUtil = new DateUtil();
			Date issueTime = dateUtil.parse4yMdHms(getStr(request, "issueTime"));
			Date beginTime = dateUtil.parse4yMdHms(getStr(request, "beginTime"));
			Date endTime = dateUtil.parse4yMdHms(getStr(request, "endTime"));
			
			deleteParam.put("issueTime", issueTime);
			deleteParam.put("beginTime", beginTime);
			deleteParam.put("endTime", endTime);
			
			memberSaleMngService.delete(deleteParam);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	
	public MemberSaleMngService getMemberSaleMngService() {
		return memberSaleMngService;
	}
	public void setMemberSaleMngService(MemberSaleMngService memberSaleMngService) {
		this.memberSaleMngService = memberSaleMngService;
	}
	
	
	
}
