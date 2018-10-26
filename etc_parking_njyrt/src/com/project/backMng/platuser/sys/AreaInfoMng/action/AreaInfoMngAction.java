package com.project.backMng.platuser.sys.AreaInfoMng.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.project.backMng.platuser.sys.AreaInfoMng.service.AreaInfoMngService;
import com.project.common.tool.AppConst;
import com.project.common.tool.StringUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/platuser/sys/AreaInfoMng", method = { RequestMethod.GET, RequestMethod.POST })
public class AreaInfoMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(AreaInfoMngAction.class);
	/**
	 * service
	 */
	private AreaInfoMngService areaInfoMngService;
	/**
	 * 展示数据列表
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request) throws UnsupportedEncodingException{
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/AreaInfoMng/AreaInfoMng_list.jsp");
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request, "park_id");
		queryParam.putRequestString(request, "QUERY_AREA_ID");
		queryParam.putRequestString(request, "QUERY_AREA_NAME");
		queryParam.putRequestString(request, "QUERY_SERVER_IP");
		String QUERY_AREA_NAME = getStr(request, "QUERY_AREA_NAME");
		if(queryParam.get("QUERY_AREA_NAME")!=null){
			queryParam.put("QUERY_AREA_NAME", new String(QUERY_AREA_NAME.getBytes("iso8859-1"),"utf-8"));
		}
		queryParam.putRequestString(request, "QUERY_DB_NAME");
		List<AreaInfoBean> list = areaInfoMngService.findList(queryParam, null);
		view.addObject(LIST, list);
		view.addObject("queryParam",queryParam);
		return view;
	}
	/**
	 * 展示新增的界面
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView add(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/AreaInfoMng/AreaInfoMng_add.jsp");
		
		return view;
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,AreaInfoBean bean){
		try {
			if(StringUtil.isEmpty(bean.getday_number())){
				bean.setday_number(null);
			}
			if(areaInfoMngService.findSameArea(bean)>0){
				return super.responseJsonText(ResultBean.newErrorResult("片区编号不可以重复"));
			}
			bean.setDelete_flag(AppConst.DELETE_FLAG.NO);
			areaInfoMngService.save(bean,null);
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
		AreaInfoBean bean=areaInfoMngService.findInfo(id);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/AreaInfoMng/AreaInfoMng_edit.jsp");
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
		AreaInfoBean bean=areaInfoMngService.findInfo(ID);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/AreaInfoMng/AreaInfoMng_view.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,AreaInfoBean bean){
		try {
			if(StringUtil.isEmpty(bean.getday_number())){
				bean.setday_number(null);
			}
			areaInfoMngService.update(bean,null);
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
			areaInfoMngService.delete(id,null);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	public AreaInfoMngService getAreaInfoMngService() {
		return areaInfoMngService;
	}
	public void setAreaInfoMngService(AreaInfoMngService areaInfoMngService) {
		this.areaInfoMngService = areaInfoMngService;
	}
	
}