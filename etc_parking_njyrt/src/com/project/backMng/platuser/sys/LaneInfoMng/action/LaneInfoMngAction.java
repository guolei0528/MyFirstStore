package com.project.backMng.platuser.sys.LaneInfoMng.action;

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
import com.project.common.tool.AppConst;
import com.project.common.tool.StringUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/platuser/sys/LaneInfoMng", method = { RequestMethod.GET, RequestMethod.POST })
public class LaneInfoMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(LaneInfoMngAction.class);
	/**
	 * service
	 */
	private LaneInfoMngService laneInfoMngService;
	private AreaInfoMngService areaInfoMngService;
	public AreaInfoMngService getAreaInfoMngService() {
		return areaInfoMngService;
	}
	public void setAreaInfoMngService(AreaInfoMngService areaInfoMngService) {
		this.areaInfoMngService = areaInfoMngService;
	}
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/LaneInfoMng/LaneInfoMng_list.jsp");
		String area_id = getStr(request, "area_id");
		if(!StringUtil.isEmpty(area_id)){
			AreaInfoBean area = areaInfoMngService.findInfo(area_id);
			queryParam.put("area_id", area.getarea_id());
			queryParam.put("park_id", area.getpark_id());
		}
		queryParam.putRequestString(request, "QUERY_LANE_ID");
		queryParam.putRequestString(request, "QUERY_LANE_FLAG");
		view.addObject("queryParam",queryParam);
		view.addObject(LIST, laneInfoMngService.findList(queryParam, null));
		return view;
	}
	/**
	 * 展示新增的界面
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView add(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/LaneInfoMng/LaneInfoMng_add.jsp");
		
		return view;
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,LaneInfoBean bean){
		try {
			if(laneInfoMngService.findInfo(bean.getlane_id())!=null){
				return super.responseJsonText(ResultBean.newErrorResult("车道编号不可以重复"));
			}
			bean.setDelete_flag(AppConst.DELETE_FLAG.NO);
			laneInfoMngService.save(bean,null);
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
		LaneInfoBean bean=laneInfoMngService.findInfo(id);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/LaneInfoMng/LaneInfoMng_edit.jsp");
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
		LaneInfoBean bean=laneInfoMngService.findInfo(ID);
		
		ModelAndView view=new ModelAndView("/jsp/backMng/platuser/sys/LaneInfoMng/LaneInfoMng_view.jsp");
		view.addObject(BEAN, bean);
		
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,LaneInfoBean bean){
		try {
			laneInfoMngService.update(bean,null);
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
			laneInfoMngService.delete(id,null);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	public LaneInfoMngService getLaneInfoMngService() {
		return laneInfoMngService;
	}
	public void setLaneInfoMngService(LaneInfoMngService laneInfoMngService) {
		this.laneInfoMngService = laneInfoMngService;
	}
	
}