package com.project.backMng.platuser.sys.ParkInfoMng.action;

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
import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;
import com.project.backMng.platuser.sys.LaneInfoMng.service.LaneInfoMngService;
import com.project.backMng.platuser.sys.ParkInfoMng.model.ParkInfoBean;
import com.project.backMng.platuser.sys.ParkInfoMng.service.ParkInfoMngService;
import com.project.common.tool.AppConst;
import com.project.common.tool.StringUtil;
import com.redoak.jar.base.model.OakTreeModel;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/platuser/sys/ParkInfoMng", method = { RequestMethod.GET, RequestMethod.POST })
public class ParkInfoMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(ParkInfoMngAction.class);
	/**
	 * service
	 */
	private ParkInfoMngService parkInfoMngService;
	
	private AreaInfoMngService areaInfoMngService;
	
	public AreaInfoMngService getAreaInfoMngService() {
		return areaInfoMngService;
	}
	public void setAreaInfoMngService(AreaInfoMngService areaInfoMngService) {
		this.areaInfoMngService = areaInfoMngService;
	}
	private LaneInfoMngService laneInfoMngService;
	
	public LaneInfoMngService getLaneInfoMngService() {
		return laneInfoMngService;
	}
	public void setLaneInfoMngService(LaneInfoMngService laneInfoMngService) {
		this.laneInfoMngService = laneInfoMngService;
	}
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request,"QUERY_NAME");

		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/ParkInfoMng/ParkInfoMng_list.jsp");
		view.addObject(LIST, parkInfoMngService.findList(queryParam, page));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		view.addObject("park_id", getStr(request, "park_id"));
		view.addObject("area_id", getStr(request, "area_id"));
		return view;
	}
	/**
	 * 展示新增的界面
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView add(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/ParkInfoMng/ParkInfoMng_add.jsp");
		
		return view;
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ParkInfoBean bean){
		try {
			if(parkInfoMngService.findInfo(bean.getpark_id())!=null){
				return super.responseJsonText(ResultBean.newErrorResult("停车场编号不可以重复"));
			}
			bean.setDelete_flag(AppConst.DELETE_FLAG.NO);
			parkInfoMngService.save(bean,null);
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
		ParkInfoBean bean=parkInfoMngService.findInfo(id);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/ParkInfoMng/ParkInfoMng_edit.jsp");
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
		ParkInfoBean bean=parkInfoMngService.findInfo(ID);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/ParkInfoMng/ParkInfoMng_view.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,ParkInfoBean bean){
		try {
			parkInfoMngService.update(bean,null);
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
			parkInfoMngService.delete(id,null);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	public ParkInfoMngService getParkInfoMngService() {
		return parkInfoMngService;
	}
	public void setParkInfoMngService(ParkInfoMngService parkInfoMngService) {
		this.parkInfoMngService = parkInfoMngService;
	}
	@RequestMapping(value="/loadTree")
	@ResponseBody
	public String loadTree(HttpServletRequest request ){
		Integer LEVEL = getInt(request, "LEVEL");
		String PARENT_ID = getStr(request, "PARENT_ID");
		if( LEVEL==null){
			OakTreeModel model = new OakTreeModel();
			model.setName("停车场管理");
			model.setId("0");
			model.setIsParent("true");
			model.setParentId(null);
			model.setNodeType("0");
			return responseJsonText(model);
		}else if(LEVEL==0){
			return responseJsonText(parkInfoMngService.loadParkTree());
		}
		else if(LEVEL==1){
			return responseJsonText(areaInfoMngService.loadAreaTree(PARENT_ID));
		}else if(LEVEL==2){
			return responseJsonText(laneInfoMngService.loadLaneTree(PARENT_ID));
		}
		return null;
		
	}
	@RequestMapping("/parkList")
	public  ModelAndView parkList(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/ParkInfoMng/ParkInfoMng_park_ist.jsp");
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request, "PARENT_ID");
		List<ParkInfoBean> list = parkInfoMngService.findList(queryParam, null);
		view.addObject(LIST,list);
		return view;
	}
	@RequestMapping("/areaList")
	@ResponseBody
	public  ModelAndView areaList(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/ParkInfoMng/ParkInfoMng_area_list.jsp");
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request, "PARENT_ID");
		List<AreaInfoBean> list = areaInfoMngService.findList(queryParam, null);
		view.addObject(LIST, list);
		return view;
	}
	@RequestMapping("/laneList")
	@ResponseBody
	public  ModelAndView laneList(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/ParkInfoMng/ParkInfoMng_lane_list.jsp");
		
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.putRequestString(request, "PARENT_ID");
		List<LaneInfoBean> list = laneInfoMngService.findList(queryParam, null);
		view.addObject(LIST,list);
		return view;
	}
	
}