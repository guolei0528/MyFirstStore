package com.project.backMng.admin.sys.action;

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
import com.project.backMng.admin.sys.service.UserMngService;
import com.project.backMng.platuser.sys.AreaInfoMng.service.AreaInfoMngService;
import com.project.backMng.pub.sys.service.RoleMngService;
import com.project.sys.admin.login.model.LoginUserBean;
import com.project.backMng.admin.sys.model.PlatUserBean;
import com.project.backMng.admin.sys.model.UserBean;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/sys/UserMng", method = { RequestMethod.GET, RequestMethod.POST })
public class UserMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(UserMngAction.class);
	/**
	 * service
	 */
	private UserMngService userMngService;
	/**
	 * 角色管理的SERVICE
	 */
	private RoleMngService roleMngService;
	
	/**
	 * 区域SERVICE
	 */
	private AreaInfoMngService areaInfoMngService;
	
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"query_login_name");
		queryParam.putRequestString(request,"query_name");

		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/sys/UserMng/UserMng_list.jsp");
		view.addObject(LIST, userMngService.findList(queryParam, page));
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
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/sys/UserMng/UserMng_add.jsp");
		
//	    if (request.getSession().getAttribute("loginUser") == null) {
//	        return null;
//	      }
//	      LoginUserBean bean = (LoginUserBean)request.getSession().getAttribute("loginUser");
		view.addObject("AREA_LIST", areaInfoMngService.findAllArea());
		view.addObject("ROLE_LIST", roleMngService.findRoleById());
		return view;
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,UserBean bean){
		try {
		    String message = userMngService.save(bean);
			if("success".equals(message))
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
	 * 展示编辑的界面
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView edit(HttpServletRequest request){
		String userId=getStr(request,"userId");
		UserBean bean=userMngService.findInfo(userId);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/sys/UserMng/UserMng_edit.jsp");
		
		view.addObject(BEAN, bean);
		
		view.addObject("ROLE_LIST", roleMngService.findRoleById());
		view.addObject("AREA_LIST", areaInfoMngService.findAllArea());
//		view.addObject("USER_ROLE", roleMngService.findByRoleUserID(userId));
		return view;
	}
	/**
	 * 展示查看的页面
	 * @return
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request){
		String userId=getStr(request, "userId");
		UserBean bean=userMngService.findInfo(userId);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/sys/UserMng/UserMng_view.jsp");
		view.addObject("AREA_LIST", areaInfoMngService.findAllArea());
		view.addObject(BEAN, bean);
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,UserBean bean){
		try {
		    String message = userMngService.update(bean);
			if("success".equals(message))
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
			String userId=getStr(request,"userId");
			userMngService.delete(userId);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	public UserMngService getUserMngService() {
		return userMngService;
	}
	public void setUserMngService(UserMngService userMngService) {
		this.userMngService = userMngService;
	}
	public RoleMngService getRoleMngService() {
		return roleMngService;
	}
	public void setRoleMngService(RoleMngService roleMngService) {
		this.roleMngService = roleMngService;
	}
	public AreaInfoMngService getAreaInfoMngService() {
		return areaInfoMngService;
	}
	public void setAreaInfoMngService(AreaInfoMngService areaInfoMngService) {
		this.areaInfoMngService = areaInfoMngService;
	}
	
	
}