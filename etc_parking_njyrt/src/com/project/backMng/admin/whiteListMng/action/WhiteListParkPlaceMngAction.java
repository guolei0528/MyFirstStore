package com.project.backMng.admin.whiteListMng.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.backCharge.tempCharge.model.TempCostBean;
import com.project.backMng.admin.whiteListMng.model.WhiteListMngBean;
import com.project.backMng.admin.whiteListMng.service.WhiteListParkPlaceMngService;
import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;
import com.project.sys.admin.login.model.LoginUserBean;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;

@Controller
@RequestMapping(value = "backMng/admin/whiteList/WhiteListParkPlaceMng", method = { RequestMethod.GET, RequestMethod.POST })
public class WhiteListParkPlaceMngAction extends BaseController{
	
	
	private final static Logger log=LogManager.getLogger(WhiteListParkPlaceMngAction.class);
	
	public WhiteListParkPlaceMngService whiteListParkPlaceMngService;
	
	
	/**
	 * 确定查询条件数据列表
	 * @return
	 */
	@RequestMapping(value = "/listindex", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView listindex(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/whiteList/WhiteListParkPlaceMng/WhiteListParkPlaceMng_index.jsp");
		return view;
	}

	
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		
		queryParam.put("query_mv_license", request.getParameter("query_mv_license").toString().trim());
		queryParam.put("query_usernumber", request.getParameter("query_usernumber").toString().trim());

		Page page=getPage(request);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/whiteList/WhiteListParkPlaceMng/WhiteListParkPlaceMng_list.jsp");
		if ("".equals(request.getParameter("query_usernumber").toString().trim())
				&& "".equals(request.getParameter("query_mv_license").toString().trim())) {
			view=new ModelAndView("/jsp/backMng/admin/whiteList/WhiteListParkPlaceMng/WhiteListParkPlaceMng_index.jsp");
			return view;
		}
		
		List<WhiteListMngBean>  whilteUserParksList = whiteListParkPlaceMngService.findList(queryParam, page);
		
		if(whilteUserParksList.size()>0){
			for(WhiteListMngBean bean : whilteUserParksList ){
				if(bean.getUser_number() != null || !"".equals(bean.getUser_number())){
					view.addObject("user_number", bean.getUser_number());
					view.addObject("sum_parks", bean.getSpare1());
					view.addObject("canuse_parks", bean.getSpare2());
					view.addObject("tolltype", bean.getToll_type());
					//纠正当前车主车位数
					correctParking(bean.getSpare1(),bean.getUser_number());
					break;
				}
			}
			
		}
		
//		if(whilteUserParksList.size()>0){
//			view.addObject("user_number", whilteUserParksList.get(0).getUser_number());
//			view.addObject("sum_parks", whilteUserParksList.get(0).getSpare1());
//			view.addObject("canuse_parks", whilteUserParksList.get(0).getSpare2());
//		}
		
		
		view.addObject(LIST, whilteUserParksList);
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		
		return view;
	}
	
	
	/**
	 * 根据用户编号纠正剩余车辆
	* @Title: correctParking  
	* @Description: TODO方法作用描述：  
	* @param @param sumparks 拥有车位数
	* @param @param usernum  用户编号
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public String correctParking(Integer sumparks,String usernum){
		//在库数量
		//Integer incostnum = whiteListParkPlaceMngService.getIncostNum(usernum);
		Integer incostnum = whiteListParkPlaceMngService.getIncostNumBySp4(usernum);
		//应该剩余车位
		Integer nowcanuse = sumparks - incostnum;
		//根据用户编号更新剩余车位数
		String re = whiteListParkPlaceMngService.updateCanUseParks(nowcanuse, usernum);
		return re;
	}

	/**
	 * 纠正车位数
	* @Title: correctparks  
	* @Description: TODO方法作用描述：  
	* @param @param request
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/correctparks", method = { RequestMethod.GET, RequestMethod.POST })
	public String correctparks(HttpServletRequest request){
		try{
			//根据用户编号纠正剩余车辆
			correctParking(Integer.parseInt(getStr(request,"sum_parks")),getStr(request,"user_number"));
			
			return super.responseJsonText(ResultBean.newSuccessResult()); 
			
		}catch (Exception e) {
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("纠正车位失败"));
		}
	}
	
	
	
	/**
	 * 在库---------->清库
	* @Title: outcost
	* @Description: TODO方法作用描述：  
	* @param @param request
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping(value = "/outcost", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String outcost(HttpServletRequest request){
		try{
			ObjectMap objectMap = ObjectMap.newInstance();
			objectMap.put("mv_license", getStr(request,"mv_license"));
			objectMap.put("user_number", getStr(request,"user_number"));
			
			//如果入库的车牌收费形式是免费 在temp_cost中spare4不用存值
			int toll_type = getInt(request, "tolltype");
			
			
			//先在tem_cost 进行清库
			whiteListParkPlaceMngService.outcost(objectMap);
			
			//如果清库的车牌不是收费形式进行如下逻辑处理
			if(toll_type != 1){
				// 可拥有车位数（总车位数）
				int sunparks = getInt(request, "sumparks");
				// 然后根据车位编号在temp_cost中按照入口时间升序排序(从小到大)查询
				List<TempCostBean> tempList = whiteListParkPlaceMngService.findTempCostBeanListByUsernumber(getStr(request,"user_number"));
				for(int i = 0; i < tempList.size(); i++){
					if(sunparks > i){
						whiteListParkPlaceMngService.updateTempCostSp1AndSp4ByMvlicense(tempList.get(i).getMvLicense(), 0, getStr(request,"user_number"));
					}else{
						whiteListParkPlaceMngService.updateTempCostSp1AndSp4ByMvlicense(tempList.get(i).getMvLicense(), 1, getStr(request,"user_number"));
					}
				}
				
				//根据user_number更新该用户的白名单 剩余车位数 或者说 直接调用纠正剩余车位 函数
				correctParking(Integer.parseInt(getStr(request,"sumparks")), getStr(request,"user_number"));
			}
			

			
			return super.responseJsonText(ResultBean.newSuccessResult()); 
			
		}catch (Exception e) {
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("清库失败"));
		}
	}
	
	/**
	 * 入库操作
	* @Title: incost  
	* @Description: TODO方法作用描述：  
	* @param @param request
	* @param @return    参数  
	* @return ModelAndView    返回类型  
	* @throws
	 */
	@RequestMapping(value = "/incost", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView incost(HttpServletRequest request){
		log.info("入库操作");
		
		//查询所有入口信息
		List<LaneInfoBean> inLaneInfoList = whiteListParkPlaceMngService.findInLaneInfoList();

		ModelAndView view=new ModelAndView("/jsp/backMng/admin/whiteList/WhiteListParkPlaceMng/WhiteListParkPlaceMng_incost.jsp");
		
		Date date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		view.addObject("sumparks", getStr(request,"sumparks"));
		view.addObject("usernumber", getStr(request,"usernumber"));
		view.addObject("mv_license", getStr(request,"mv_license"));
		view.addObject("nowtime", fmt.format(date));
		view.addObject("inLaneInfoList", inLaneInfoList);
		view.addObject("tolltype", getStr(request,"tolltype"));
		return view;
	}
	
	/**
	 * 确认提交入库
	* @Title: submit  
	* @Description: TODO方法作用描述：  
	* @param @param request
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/sureincost", method = { RequestMethod.GET, RequestMethod.POST })
	public String submit(HttpServletRequest request){
		log.info("sureincost.shtml");
		
		String mv_license = getStr(request,"mv_license");
		String entry_lane = getStr(request,"entry_lane");
		String entry_time =  getStr(request,"entry_time");
		String usernumber = getStr(request,"usernumber");
		
		//如果入库的车牌收费形式是免费 在temp_cost中spare4不用存值
		int toll_type = getInt(request, "tolltype");
		if(toll_type == 1){
			usernumber = "";
		}
		
		String[] temp = entry_time.split(" ");
		Integer entrydateInte = Integer.parseInt(temp[0].replace("-", ""));

		
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("entry_lane", entry_lane);
		//根据入口车道编号查询入口车道信息
		LaneInfoBean laneinfo = whiteListParkPlaceMngService.findInLaneObj(objectMap);
		Map map = new HashMap();
		map.put("entry_lane", entry_lane);
		map.put("entry_time", entry_time);
		map.put("entrydateInte", entrydateInte);
		map.put("mv_license", mv_license);
		map.put("usernumber", usernumber);
		
		whiteListParkPlaceMngService.incost(laneinfo, map);
		
		//修改temp_cost中入库的车牌对应的车位编号的所有spare1的状态值
		if(toll_type != 1){
			// 可拥有车位数（总车位数）
			int sunparks = getInt(request, "sumparks");
			// 然后根据车位编号在temp_cost中按照入口时间升序排序(从小到大)查询
			List<TempCostBean> tempList = whiteListParkPlaceMngService.findTempCostBeanListByUsernumber(usernumber);
			for(int i = 0; i < tempList.size(); i++){
				if(sunparks > i){
					whiteListParkPlaceMngService.updateTempCostSp1AndSp4ByMvlicense(tempList.get(i).getMvLicense(), 0, usernumber);
				}else{
					whiteListParkPlaceMngService.updateTempCostSp1AndSp4ByMvlicense(tempList.get(i).getMvLicense(), 1, usernumber);
				}
			}
			
			
			//根据user_number更新该用户的白名单 剩余车位数 或者说 直接调用纠正剩余车位 函数
			correctParking(Integer.parseInt(getStr(request,"sumparks")), usernumber);
		}
		

		try {
			return super.responseJsonText(ResultBean.newSuccessResult());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("保存失败,请稍后再试"));
	}
	
	
	

	public WhiteListParkPlaceMngService getWhiteListParkPlaceMngService() {
		return whiteListParkPlaceMngService;
	}


	public void setWhiteListParkPlaceMngService(WhiteListParkPlaceMngService whiteListParkPlaceMngService) {
		this.whiteListParkPlaceMngService = whiteListParkPlaceMngService;
	}
	
}
