package com.project.backMng.admin.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.project.backMng.admin.member.model.CarInfoBean;
import com.project.backMng.admin.member.model.MemberBean;
import com.project.backMng.admin.member.service.MemberInfoMngService;
import com.project.backMng.admin.sys.model.UserBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/member/MemberInfoMng", method = { RequestMethod.GET, RequestMethod.POST })
public class MemberInfoMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(MemberInfoMngAction.class);
	/**
	 * service
	 */
	private MemberInfoMngService memberInfoMngService;
	/**
	 * 角色管理的SERVICE
	 */
//	private RoleMngService roleMngService;
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"query_member_id");
		queryParam.putRequestString(request,"query_name");
		
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/member/MemberInfoMng/MemberInfoMng_list.jsp");
		view.addObject(LIST, memberInfoMngService.findList(queryParam, page));
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
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/member/MemberInfoMng/MemberInfoMng_add.jsp");
		
		return view;
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
	public String save(HttpServletRequest request,MemberBean bean){
		try {
			//获得新增校验的返回值
			String message = memberInfoMngService.save(bean);
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
	 * 展示查看的页面
	 * @return
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request){
		String memberId=getStr(request, "memberId");
		MemberBean bean=memberInfoMngService.findInfo(memberId);
		List<CarInfoBean> carInfoBeans = memberInfoMngService.findCarInfo(memberId);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/member/MemberInfoMng/MemberInfoMng_view.jsp");
		view.addObject(BEAN, bean);
		view.addObject("carInfoBeans", carInfoBeans);
		return view;
	}
	
	
	/**
	 * 展示编辑的界面
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView edit(HttpServletRequest request){
		String memberId=getStr(request,"memberId");
		MemberBean bean=memberInfoMngService.findInfo(memberId);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/member/MemberInfoMng/MemberInfoMng_edit.jsp");
		
		view.addObject(BEAN, bean);
		return view;
	}
	
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,MemberBean bean){
		try {
			memberInfoMngService.update(bean);
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
			String memberId=getStr(request,"memberId");
			memberInfoMngService.delete(memberId);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	
	public MemberInfoMngService getMemberInfoMngService() {
		return memberInfoMngService;
	}
	public void setMemberInfoMngService(MemberInfoMngService memberInfoMngService) {
		this.memberInfoMngService = memberInfoMngService;
	}
	
	

}