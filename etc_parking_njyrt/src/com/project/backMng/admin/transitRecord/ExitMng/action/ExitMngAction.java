package com.project.backMng.admin.transitRecord.ExitMng.action;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.transitRecord.ExitMng.model.ExitBean;
import com.project.backMng.admin.transitRecord.ExitMng.service.ExitMngService;
import com.project.common.tool.StringUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/transitRecord/ExitMng", method = { RequestMethod.GET, RequestMethod.POST })
public class ExitMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(ExitMngAction.class);
	/**
	 * service
	 */
	private ExitMngService exitMngService;
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/ExitMng/ExitMng_list.jsp");
		Page page=getPage(request);
		page.setCurrentPage(0);
		page.setRecordCount(0);
		view.addObject(PAGE, page);
		return view;
	}
	
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/sreachList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView sreachList(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"query_mvlicense");
		queryParam.putRequestString(request,"query_entry_time_from");
		queryParam.putRequestString(request,"query_entry_time_to");
		queryParam.putRequestString(request,"query_exit_time_from");
		queryParam.putRequestString(request,"query_exit_time_to");
		queryParam.putRequestString(request, "query_lane_id");
		
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/ExitMng/ExitMng_list.jsp");
		view.addObject(LIST, exitMngService.findList(queryParam, page));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		
		return view;
	}
	
	
	@RequestMapping(value = "/hisList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView hisList(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"query_mvlicense");
		queryParam.putRequestString(request,"query_entry_time_from");
		queryParam.putRequestString(request,"query_entry_time_to");
		queryParam.putRequestString(request, "query_lane_id");
		queryParam.putRequestString(request,"last_date");
		String last_date = getStr(request, "last_date");
		if(StringUtil.isEmpty(last_date)){
			queryParam.put("tableName","exit"+ getLastDate(new Date()));
		}else{
			queryParam.put("tableName","exit"+last_date.replace("-","") );
		}
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/ExitMng/ExitMng_HisList.jsp");
		
		try {
			view.addObject(LIST, exitMngService.findHisList(queryParam, page));
			view.addObject(PAGE, page);
		} catch (Exception e) {
			page.setCurrentPage(1);
			page.setPageCount(0);
			page.setRecordCount(0);
			view.addObject(PAGE, page);
			view.addObject("queryParam", queryParam);
			return view;
		}
		view.addObject("queryParam", queryParam);
		return view;
	}
	/**
	 * 展示新增的界面
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView add(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/ExitMng/ExitMng_add.jsp");
		
		return view;
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ExitBean bean){
		try {
			exitMngService.save(bean,getLoginUser(request));
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
//	@RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
//	public ModelAndView edit(HttpServletRequest request){
//		String id=getStr(request,"ID");
//		ExitBean bean=exitMngService.findInfo(id);
//		
//		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/ExitMng/ExitMng_edit.jsp");
//		view.addObject(BEAN, bean);
//		
//		return view;
//	}
	/**
	 * 展示查看的页面
	 * @return
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request){
		log.info("收到来自出口页面视图的查询");
		ObjectMap map=ObjectMap.newInstance();
		log.info("exittime:"+getStr(request, "exittime"));
		log.info("exitlane:"+getStr(request, "exitlane"));
		log.info("mvlicense:"+getStr(request, "mvlicense"));
		map.put("exittime", getStr(request, "exittime").replace(",", " "));
		map.putRequestString(request, "exitlane");
		map.put("mvlicense",getStr(request, "mvlicense"));
		ExitBean bean=exitMngService.findInfo(map);
		log.info("获得查询结果");
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/ExitMng/ExitMng_view.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	@RequestMapping(value = "/viewHis", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView viewHis(HttpServletRequest request){
		ObjectMap map=ObjectMap.newInstance();
		map.put("exittime", getStr(request, "exittime").replace(",", " "));
		map.putRequestString(request, "exitlane");
		map.putRequestString(request, "tableName");
		map.put("mvlicense",getStr(request, "mvlicense"));
		ExitBean bean=exitMngService.findHisInfo(map);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/ExitMng/ExitMng_view.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,ExitBean bean){
		try {
			exitMngService.update(bean,getLoginUser(request));
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
			exitMngService.delete(id,getLoginUser(request));
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	public ExitMngService getExitMngService() {
		return exitMngService;
	}
	public void setExitMngService(ExitMngService exitMngService) {
		this.exitMngService = exitMngService;
	}
	  private static String  getLastDate(Date date) {
		  	SimpleDateFormat sf=new SimpleDateFormat("yyyyMM");
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.add(Calendar.MONTH, -1);
	        return sf.format(cal.getTime());
	    }
	
}