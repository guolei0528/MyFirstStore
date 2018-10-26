package com.project.backMng.admin.member.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.member.model.CarInfoBean;
import com.project.backMng.admin.member.model.MemberBean;
import com.project.backMng.admin.member.service.CarInfoMngService;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;


@Controller
@RequestMapping(value = "backMng/admin/member/CarInfoMng", method = { RequestMethod.GET, RequestMethod.POST })
public class CarInfoMngAction extends BaseController{
	
	private final static Logger log = LogManager.getLogger(CarInfoMngAction.class);
	
	private CarInfoMngService carInfoMngService;
	
	
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"query_mv_license");
		queryParam.putRequestString(request,"query_obu_id");
		queryParam.putRequestString(request,"query_member_id");
		queryParam.putRequestString(request,"query_car_color");
		queryParam.putRequestString(request,"query_type");
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/member/CarInfoMng/CarInfoMng_list.jsp");
		view.addObject(LIST, carInfoMngService.findList(queryParam, page));
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
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/member/CarInfoMng/CarInfoMng_add.jsp");
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
	public String save(HttpServletRequest request,CarInfoBean bean){
		try {
			String msg = carInfoMngService.save(bean);
			//唯一性校验
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
		return super.responseJsonText(ResultBean.newErrorResult("保存失败,请稍后再试"));
	}
	
	
	/**
	 * 展示编辑的界面
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView edit(HttpServletRequest request){
		String mvLicense=getStr(request,"mvLicense");
		String carColor=getStr(request,"carColor");
		CarInfoBean bean=carInfoMngService.findInfo(mvLicense,carColor);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/member/CarInfoMng/CarInfoMng_edit.jsp");
		
		view.addObject(BEAN, bean);
		return view;
	}
	
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,CarInfoBean bean){
		try {
//			String ROLE_ID=getStr(request, "role_id");
			String msg = carInfoMngService.update(bean);
			//唯一性校验
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
			String mvLicense=getStr(request,"mvLicense");
			String carColor=getStr(request,"carColor");
			carInfoMngService.delete(mvLicense,carColor);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	
	
	/**
	 * 展示查看的页面
	 * @return
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request){
		String mvLicense=getStr(request,"mvLicense");
		String carColor=getStr(request,"carColor");
		CarInfoBean bean=carInfoMngService.findInfo(mvLicense,carColor);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/member/CarInfoMng/CarInfoMng_view.jsp");
		view.addObject(BEAN, bean);
		return view;
	}
	
	
	public CarInfoMngService getCarInfoMngService() {
		return carInfoMngService;
	}


	public void setCarInfoMngService(CarInfoMngService carInfoMngService) {
		this.carInfoMngService = carInfoMngService;
	}
	
}
