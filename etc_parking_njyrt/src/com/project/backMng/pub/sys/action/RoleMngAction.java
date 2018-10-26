package com.project.backMng.pub.sys.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.pub.sys.service.RoleMngService;
import com.project.common.tool.AppConst;
import com.project.tools.UuidGenerator;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/pub/sys/RoleMng", method = { RequestMethod.GET, RequestMethod.POST })
public class RoleMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(RoleMngAction.class);
	/**
	 * service
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
		ModelAndView view=new ModelAndView("/jsp/backMng/pub/sys/RoleMng/RoleMng_list.jsp");
		view.addObject(LIST, roleMngService.findList(queryParam, page));
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
		ModelAndView view=new ModelAndView("/jsp/backMng/pub/sys/RoleMng/RoleMng_add.jsp");
		return view;
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request){
		try {
			ObjectMap bean=ObjectMap.newInstance();
			bean.put("role_id", UuidGenerator.generateUUID());
			bean.putRequestString(request,"role_name");
			bean.putRequestString(request,"s_comment");
//			bean.putRequestString(request, "USER_TYPE");
//			bean.putRequestString(request, "OWNER_ID");
			bean.putRequestString(request, "NODE_ID_STR");
			bean.putRequestString(request, "WX_PAGE_IDS");
			roleMngService.save(bean);
			return super.responseJsonText(ResultBean.newSuccessResult());
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		String role_id=getStr(request,"role_id");
		ObjectMap bean=roleMngService.findInfo(role_id);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/pub/sys/RoleMng/RoleMng_edit.jsp");
		view.addObject(BEAN, bean);
		return view;
	}
	/**
	 * 展示查看的页面
	 * @return
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request){
		String role_id=getStr(request, "role_id");
		ObjectMap bean=roleMngService.findInfo(role_id);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/pub/sys/RoleMng/RoleMng_view.jsp");
		view.addObject(BEAN, bean);
		view.addObject("role_id", role_id);
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request){
		try {
			ObjectMap bean=ObjectMap.newInstance();
			bean.putRequestString(request,"role_id");
			bean.putRequestString(request,"role_name");
			bean.putRequestString(request,"s_comment");
			bean.putRequestString(request,"last_modify_time");
			
			bean.putRequestString(request, "NODE_ID_STR");
			bean.putRequestString(request, "WX_PAGE_IDS");
			
			roleMngService.update(bean);
			return super.responseJsonText(ResultBean.newSuccessResult());
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
			roleMngService.delete(id);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			// TODO: handle exception
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	
	@RequestMapping(value = "/loadMenuTree", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String loadMenuTree(HttpServletRequest request){
		String query_type=getStr(request,"query_type");
		String parent_id=getStr(request,"parent_id");
		String role_id=getStr(request,"role_id");
//		Integer type=null;
//		if(getLoginUser(request).getUSER_TYPE()==AppConst.USER_TYPE.SYS_ADMIN){
//			type=AppConst.USER_TYPE.PLAT_USER;
//		}
		
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.put("QUERY_TYPE", query_type);
		queryParam.put("PARENT_ID", parent_id);
		queryParam.put("ROLE_ID", role_id);
//		queryParam.put("USER_TYPE", type);
		return super.responseJsonText(roleMngService.findTreeList(queryParam));
	}
	
	public RoleMngService getRoleMngService() {
		return roleMngService;
	}
	public void setRoleMngService(RoleMngService roleMngService) {
		this.roleMngService = roleMngService;
	}
	
}