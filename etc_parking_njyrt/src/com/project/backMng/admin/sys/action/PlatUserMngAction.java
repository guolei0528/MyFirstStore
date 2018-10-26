package com.project.backMng.admin.sys.action;

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
import com.project.backMng.admin.sys.service.PlatUserMngService;
import com.project.backMng.pub.sys.service.RoleMngService;
import com.project.backMng.admin.sys.model.PlatUserBean;

import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

//@Controller
//@RequestMapping(value = "backMng/admin/sys/PlatUserMng", method = { RequestMethod.GET, RequestMethod.POST })
public class PlatUserMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(PlatUserMngAction.class);
	/**
	 * service
	 */
	private PlatUserMngService platUserMngService;
	/**
	 * 角色管理的SERVICE
	 */
	private RoleMngService roleMngService;
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"QUERY_NAME");

		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/sys/PlatUserMng/PlatUserMng_list.jsp");
		view.addObject(LIST, platUserMngService.findList(queryParam, page));
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
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/sys/PlatUserMng/PlatUserMng_add.jsp");
		
		view.addObject("ROLE_LIST", roleMngService.findByOwnerId(getLoginUser(request).getID()));
		return view;
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,PlatUserBean bean){
		try {
			String ROLE_ID=getStr(request, "ROLE_ID");
			platUserMngService.save(bean,ROLE_ID,getLoginUser(request));
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
		PlatUserBean bean=platUserMngService.findInfo(id);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/sys/PlatUserMng/PlatUserMng_edit.jsp");
		view.addObject(BEAN, bean);
		
		view.addObject("ROLE_LIST", roleMngService.findByOwnerId(getLoginUser(request).getID()));
		view.addObject("USER_ROLE", roleMngService.findByRoleUserID(id));
		return view;
	}
	/**
	 * 展示查看的页面
	 * @return
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request){
		String ID=getStr(request, "ID");
		PlatUserBean bean=platUserMngService.findInfo(ID);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/sys/PlatUserMng/PlatUserMng_view.jsp");
		view.addObject(BEAN, bean);
		view.addObject("USER_ROLE", roleMngService.findByRoleUserID(ID));
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,PlatUserBean bean){
		try {
			String ROLE_ID=getStr(request, "ROLE_ID");
			platUserMngService.update(bean,ROLE_ID,getLoginUser(request));
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
			platUserMngService.delete(id,getLoginUser(request));
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	public PlatUserMngService getPlatUserMngService() {
		return platUserMngService;
	}
	public void setPlatUserMngService(PlatUserMngService platUserMngService) {
		this.platUserMngService = platUserMngService;
	}
	public RoleMngService getRoleMngService() {
		return roleMngService;
	}
	public void setRoleMngService(RoleMngService roleMngService) {
		this.roleMngService = roleMngService;
	}
	
}