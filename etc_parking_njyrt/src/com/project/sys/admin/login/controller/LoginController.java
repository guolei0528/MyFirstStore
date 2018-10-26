package com.project.sys.admin.login.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.tool.ShortMessageTemplate;
import com.project.sys.admin.login.model.LoginUserBean;
import com.project.sys.admin.login.model.UserSessionTree;
import com.project.sys.admin.login.service.LoginService;
import com.project.tools.ValidCodeUtil;
import com.redoak.jar.base.model.OakTreeModel;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;
import com.redoak.jar.util.PropertiesUtil;
import com.redoak.jar.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * 系统登录
 * 
 * @author OAK
 *
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

	private final static Logger log = LogManager.getLogger(LoginController.class);
	/**
	 * 登录的SERVICE
	 */
	private LoginService loginService;
	
	/**
	 * 显示登录的页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/login.jsp");
		return model;
	}
	
	
	@RequestMapping("/register")
	public ModelAndView register(HttpServletRequest request){
		ModelAndView model = new ModelAndView("/register.jsp");
		return model;
		
	}

	/**
	 * 检查登录的信息
	 * 
	 * @return
	 */
	@RequestMapping("/checkLogin")
	public ModelAndView checkLogin(HttpServletRequest request) {
		String LOGIN_NAME = getStr(request, "loginName");
		String PASSWORD = getStr(request, "password");
		LoginUserBean loginUserBean = loginService.findLoginUser(LOGIN_NAME, PASSWORD);

		if (loginUserBean == null) {
			log.info("用户：" + LOGIN_NAME + "登录失败");
			
			ModelAndView view = new ModelAndView("/login.jsp");
			view.addObject(ERROR_MSG, "用户名密码错误");
			return view;
		} else {
			//判断用户已经停用了
			if(loginUserBean.getInUseFlag() == null ||loginUserBean.getInUseFlag() == 0){
				ModelAndView view = new ModelAndView("/login.jsp");
				view.addObject(ERROR_MSG, "用户已停用！");
				return view;
			}
//			super.setLoginUser(request, userBean);
			request.getSession().setAttribute("loginUser", loginUserBean);
			
			ObjectMap queryParam=ObjectMap.newInstance();
//			queryParam.put("user_id", userBean.getLoginName());
			queryParam.put("role_id", loginUserBean.getRoleId());
			
			List<UserSessionTree> treeList=loginService.findAllTreeListByUserType(queryParam);
			if(treeList==null||treeList.size()==0){
				ModelAndView view = new ModelAndView("/login.jsp");
				view.addObject(ERROR_MSG, "您还没有分配权限，请联系管理员分配权限");
				return view;
			}else{				
				request.getSession().setAttribute("USER_SESSION_TREEMENU", treeList);
				ModelAndView view = new ModelAndView("redirect:main.shtml");
				
				//判断最高权限
				if(PropertiesUtil.get("MOST_AUTHOR") != null && PropertiesUtil.get("MOST_AUTHOR").equals(loginUserBean.getUserId()))
				{
//					view = new ModelAndView("redirect:backMng/admin/report/FlowIncome/lineChart.shtml");
					view = new ModelAndView("redirect:backMng/admin/report/FlowIncome/baseInfo.shtml");
					return view;
				}
				
				
				// 判断视图中是否存在运营总表，存在时跳转
				for(UserSessionTree tree:treeList)
				{
					
					if(tree.getChildNodeList() != null)
					{
						for(OakTreeModel treeModel : tree.getChildNodeList())
						{
							if("backMng/admin/report/FlowIncome/baseInfo_base.shtml".equals(treeModel.getUrlAction()))
							{
								view = new ModelAndView("redirect:backMng/admin/report/FlowIncome/baseInfo_base.shtml");
								return view;
							}
						}
					}
				}
				return view;
			}
			
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/validCode", method = { RequestMethod.POST })
	public String validCode(HttpServletRequest request){
		String MOBILE=getStr(request, "MOBILE");
		if(StringUtil.isEmpty(MOBILE)){
			String CODE=ValidCodeUtil.generate(request);
			
			ResultBean bean=ResultBean.newSuccessResult(CODE);
			return super.responseJsonText(bean);
		}
		
		String CODE=ValidCodeUtil.generate(request);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("number", CODE);
		
		ResultBean bean=ShortMessageTemplate.sendValidCode(MOBILE, ShortMessageTemplate.VALID_CODE, jsonObject);
		return super.responseJsonText(bean);
		
	}
	
	
	/**
	 * 转至主界面
	 * 
	 * @return
	 */
	@RequestMapping("main")
	public ModelAndView main(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/jsp/sys/login/main.jsp");
		return view;
	}

	/**
	 * 显示左侧树结构
	 * 
	 * @return
	 */
	@RequestMapping("left")
	public ModelAndView left() {
		ModelAndView view = new ModelAndView("/jsp/sys/login/left.jsp");
		return view;
	}

	/**
	 * 加载树
	 * 
	 * @return
	 */
	@RequestMapping("loadTree")
	public String loadTree(HttpServletRequest request) {

		return null;
	}

	/**
	 * 加载管理员的树
	 * 
	 * @return
	 */
	@RequestMapping("loadAdminTree")
	@ResponseBody
	public String loadAdminTree(HttpServletRequest request) {
		String parentId = getStr(request, "parentId");
		Integer userType = super.getLoginUser(request).getUSER_TYPE();
		List<OakTreeModel> treeNodeList = loginService.findAdminTreeList(parentId, userType,
				getLoginUser(request).getID());
		return super.responseJsonText(treeNodeList);
	}

	/**
	 * 显示右侧树结构
	 * 
	 * @return
	 */
	@RequestMapping("right")
	public ModelAndView right(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/jsp/sys/login/right.jsp");
		return view;
	}

	/**
	 * 退出系统
	 * 
	 * @return
	 */
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().removeAttribute("loginUser");
		request.getSession().invalidate();

		return new ModelAndView("redirect:login.shtml");
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	
}
