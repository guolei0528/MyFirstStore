package com.project.base.interceptor;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.project.sys.admin.login.model.LoginUserBean;
import com.project.sys.admin.login.model.UserSessionTree;
import com.redoak.jar.base.model.OakTreeModel;

public class SessionInterceptor implements HandlerInterceptor {

	private List<String> filterUrl = new ArrayList<String>(); // 过滤不check

	private List<String> filterUrlqinatai = new ArrayList<String>(); // 前台过滤不check

	private final static String LOGIN_USER = "loginUser";

	private final static String WX_LOGIN_USER = "wxLoginUser";

	public SessionInterceptor() {
		filterUrl.add("/login.shtml");
		filterUrl.add("/logout.shtml");
		filterUrl.add("/wx/login/WxLogin/login.shtml");
		filterUrl.add("/wx/login/WxLogin/validCode.shtml");
		filterUrl.add("/valid/validIn.shtml");
		filterUrl.add("/valid/validOut.shtml");
		filterUrl.add("/valid/confirmOut.shtml");
		filterUrl.add("/valid/confirmIn.shtml");

		filterUrlqinatai.add("/wx/Wxpay/payResult.shtml"); // 支付回调

	}

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String PROJECT_NAME = request.getContextPath();
		String URL = request.getRequestURI();

		// 过滤相应的URL
		String COMPARE_URL = URL.substring(PROJECT_NAME.length());

		if (COMPARE_URL.startsWith("/backMng") || COMPARE_URL.endsWith("main.shtml")
				|| COMPARE_URL.startsWith("/personalCenter")) {

			if (filterUrl.contains(COMPARE_URL)) {
				return true;
			}

			// 判断用户是否
			Object object = request.getSession().getAttribute("loginUser");
			if (object == null) {
				for (String s : filterUrl) {
					if (COMPARE_URL.startsWith(s)) {
						return true;
					}
				}

				response.sendRedirect(PROJECT_NAME + "/sessionTimeOut.jsp");
				return false;
			} else {
				// request.getSession().setAttribute("USER_SESSION_TREEMENU",
				// treeList);
				List<UserSessionTree> treeList = (List<UserSessionTree>) request.getSession()
						.getAttribute("USER_SESSION_TREEMENU");
				// 判断树结构是否为空
				if (treeList == null || treeList.size() == 0) {
					return false;
				}

				// 判断是否为main.shtml
				if (COMPARE_URL.equals("/main.shtml")) {
					return true;
				}

				// 判断视图中是否存在运营总表，存在时跳转
				/*LoginUserBean loginUserBean = (LoginUserBean)object;
				if("1001".equals(loginUserBean.getLoginName()))
				{
					response.sendRedirect(PROJECT_NAME + "/backMng/admin/report/FlowIncome/baseInfo.shtml.jsp");
					return true;
				}*/
				
				/*
				 * for(UserSessionTree tree:treeList) {
				 * if(tree.getChildNodeList() != null) { for(OakTreeModel
				 * treeModel : tree.getChildNodeList()) {
				 * if(COMPARE_URL.startsWith("/"+treeModel.getUrlAction().trim()
				 * )) { return true; } } } } return false;
				 */
				return true;
			}
		} else {
			return true;
		}
	}

}
