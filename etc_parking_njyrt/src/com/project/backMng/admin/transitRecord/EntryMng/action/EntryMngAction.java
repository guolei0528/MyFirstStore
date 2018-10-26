package com.project.backMng.admin.transitRecord.EntryMng.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backMng.admin.transitRecord.EntryMng.model.EntryBean;
import com.project.backMng.admin.transitRecord.EntryMng.service.EntryMngService;
import com.project.common.tool.StringUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/transitRecord/EntryMng", method = { RequestMethod.GET, RequestMethod.POST })
public class EntryMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(EntryMngAction.class);
	/**
	 * service
	 */
	private EntryMngService entryMngService;
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/EntryMng/EntryMng_list.jsp");
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
		queryParam.putRequestString(request, "query_lane_id");
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/EntryMng/EntryMng_list.jsp");
		view.addObject(LIST, entryMngService.findList(queryParam, page));
//		List<EntryBean> a = entryMngService.findList(queryParam, page);
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
		queryParam.putRequestString(request,"last_date");
		queryParam.putRequestString(request, "query_lane_id");
		String last_date = getStr(request, "last_date");
		if(StringUtil.isEmpty(last_date)){
			queryParam.put("tableName","entry"+ getLastDate(new Date()));
		}else{
			queryParam.put("tableName","entry"+last_date.replace("-","") );
		}
		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/EntryMng/EntryMng_HisList.jsp");
		try {
			view.addObject(LIST, entryMngService.findHisList(queryParam, page));
			view.addObject(PAGE, page);
		} catch (Exception e) {
			e.printStackTrace();
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
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/EntryMng/EntryMng_add.jsp");
		
		return view;
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,EntryBean bean){
		try {
			entryMngService.save(bean,null);
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
//		EntryBean bean=entryMngService.findInfo(id);
//		
//		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/EntryMng/EntryMng_edit.jsp");
//		view.addObject(BEAN, bean);
//		
//		return view;
//	}
	/**
	 * 展示查看的页面
	 * @return */
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView view(HttpServletRequest request){
		ObjectMap map=ObjectMap.newInstance();
		map.put("entrytime", getStr(request, "entrytime").replace(",", " "));
		map.putRequestString(request, "entrylane");
		map.putRequestString(request, "mvlicense");
		EntryBean bean=entryMngService.findInfo(map);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/EntryMng/EntryMng_view.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	@RequestMapping(value = "/viewHis", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView viewHis(HttpServletRequest request){
		ObjectMap map=ObjectMap.newInstance();
		map.put("entrytime", getStr(request, "entrytime").replace(",", " "));
		map.putRequestString(request, "tableName");
		map.putRequestString(request, "entrylane");
		map.putRequestString(request, "mvlicense");
		EntryBean bean=entryMngService.findHisInfo(map);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/EntryMng/EntryMng_view.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,EntryBean bean){
		try {
			entryMngService.update(bean,null);
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
			String mv_license =getStr(request,"mv_license");
			int car_color = getInt(request,"car_color");
			entryMngService.delete(mv_license,car_color);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	public EntryMngService getEntryMngService() {
		return entryMngService;
	}
	public void setEntryMngService(EntryMngService entryMngService) {
		this.entryMngService = entryMngService;
	}
	@RequestMapping("/excessiveCar")
	public ModelAndView excessiveCar(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/EntryMng/EntryMng_excessive_list.jsp");
		
		ObjectMap queryParam=ObjectMap.newInstance();
		
//		String query_entry_time_from = request.getParameter("query_entry_time_from");
//		String query_entry_time_to = request.getParameter("query_entry_time_to");
//		queryParam.put("query_entry_time_from", query_entry_time_from+" 00:00:00");
//		queryParam.put("query_entry_time_to", query_entry_time_to+" 24:00:00");
		
		queryParam.putRequestString(request,"query_mvlicense");
		queryParam.putRequestString(request,"query_entry_time_from");
		queryParam.putRequestString(request,"query_entry_time_to");
		queryParam.putRequestString(request, "query_money");
		queryParam.putRequestString(request, "query_lane_id");
		Page page=getPage(request);
		view.addObject(LIST,entryMngService.findExcessiveCarList(queryParam, page));
//		queryParam.put("query_entry_time_from", query_entry_time_from);
//		queryParam.put("query_entry_time_to", query_entry_time_to);
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		return view;
	}
	@RequestMapping("/timeoutCar")
	public ModelAndView timeoutCar(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/EntryMng/EntryMng_timeout_list.jsp");
		ObjectMap queryParam=ObjectMap.newInstance();
		
//		String query_entry_time_from = request.getParameter("query_entry_time_from");
//		String query_entry_time_to = request.getParameter("query_entry_time_to");
//		queryParam.put("query_entry_time_from", query_entry_time_from+" 00:00:00");
//		queryParam.put("query_entry_time_to", query_entry_time_to+" 24:00:00");
		
		queryParam.putRequestString(request,"query_mvlicense");
		queryParam.putRequestString(request,"query_entry_time_from");
		queryParam.putRequestString(request,"query_entry_time_to");
		queryParam.putRequestString(request, "query_time");
		queryParam.putRequestString(request, "query_lane_id");
		
		
		Page page=getPage(request);
		view.addObject(LIST,entryMngService.findTimeoutCarList(queryParam, page));
		
//		queryParam.put("query_entry_time_from", query_entry_time_from);
//		queryParam.put("query_entry_time_to", query_entry_time_to);
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		return view;
		
	}
	@RequestMapping("/emptyPositionList")
	public ModelAndView emptyPositionList(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/EntryMng/EntryMng_emptyPostion_list.jsp");
		Page page=getPage(request);
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request, "QUERY_AREA_ID");
		queryParam.putRequestString(request, "QUERY_AREA_NAME");
		view.addObject(LIST,entryMngService.findEmptyPosition(queryParam, page));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		return view;
	}
	@RequestMapping("/areaView")
	public ModelAndView areaView(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/transitRecord/EntryMng/AreaInfoMng_view.jsp");
		view.addObject(BEAN,entryMngService.findAreaInfo(getStr(request, "ID")));
		return view;
	}
	
	
	
	
	  private static String  getLastDate(Date date) {
		  	SimpleDateFormat sf=new SimpleDateFormat("yyyyMM");
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.add(Calendar.MONTH, -1);
	        return sf.format(cal.getTime());
	    }
	
	
}