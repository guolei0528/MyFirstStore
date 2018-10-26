package com.project.personalCenter.user.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.personalCenter.user.service.ModifyPasswordService;
import com.project.sys.admin.login.model.LoginUserBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "personalCenter/user/ModifyPassword", method = { RequestMethod.GET, RequestMethod.POST })
public class ModifyPasswordAction extends BaseController{

	private final static Logger log = LogManager.getLogger(ModifyPasswordAction.class);

	/**
	 * Service
	 */
	private ModifyPasswordService modifyPasswordService;

	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView edit(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/personalCenter/user/ModifyPassword_edit.jsp");
		return view;
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request){
		try {
			LoginUserBean userBean=(LoginUserBean)request.getSession().getAttribute("loginUser");
			ObjectMap bean=ObjectMap.newInstance();
			bean.putRequestString(request, "old_pwd");
			bean.putRequestString(request, "new_pwd");
			bean.put("user_id", userBean.getUserId());
			
			String msg = modifyPasswordService.updatePwd(bean);
//			唯一性校验
			if(!"success".equals(msg))
			{
				return super.responseJsonText(ResultBean.newErrorResult(msg));
			}
			return super.responseJsonText(ResultBean.newSuccessResult());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("修改失败,请稍后再试"));
		
	}

	public ModifyPasswordService getModifyPasswordService() {
		return modifyPasswordService;
	}

	public void setModifyPasswordService(ModifyPasswordService modifyPasswordService) {
		this.modifyPasswordService = modifyPasswordService;
	}

	
}
