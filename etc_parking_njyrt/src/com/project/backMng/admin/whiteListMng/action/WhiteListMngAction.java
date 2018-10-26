package com.project.backMng.admin.whiteListMng.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.project.backCharge.tempCharge.model.TempCostBean;
import com.project.backMng.admin.whiteListMng.model.WhiteListMngBean;
import com.project.backMng.admin.whiteListMng.service.WhiteListMngService;
import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.project.common.tool.ExcelPoiTools;
import com.project.sys.admin.login.model.LoginUserBean;
import com.project.tools.BigDecimalTool;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;
import com.redoak.jar.util.PropertiesUtil;

@Controller
@RequestMapping(value = "backMng/admin/whiteList/WhiteListMng", method = { RequestMethod.GET, RequestMethod.POST })
public class WhiteListMngAction extends BaseController{

	private final static Logger log=LogManager.getLogger(WhiteListMngAction.class);
	
	/**
	 * service
	 */
	private WhiteListMngService whiteListMngService;
	private String [] type={"1","2","3","1.0","2.0","3.0"};
	
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		
		if(request.getParameter("query_mv_license")!= null &&
				!"".equals(request.getParameter("query_mv_license").toString().trim()))
		{
			queryParam.put("query_mv_license", request.getParameter("query_mv_license").toString().trim());
		}
		
		
		if(request.getParameter("query_user_number")!= null &&
				!"".equals(request.getParameter("query_user_number").toString().trim()))
		{
			queryParam.put("query_user_number", request.getParameter("query_user_number").toString().trim());
			
		}
		
		if(request.getParameter("query_user_name")!= null &&
				!"".equals(request.getParameter("query_user_name").toString().trim()))
		{
			queryParam.put("query_user_name", request.getParameter("query_user_name").toString().trim());
			
		}

		
		if(request.getParameter("query_phone")!= null &&
				!"".equals(request.getParameter("query_phone").toString().trim()))
		{
			queryParam.put("query_phone", request.getParameter("query_phone").toString().trim());
			
		}

		/*queryParam.putRequestString(request,"query_mv_license");
		queryParam.putRequestString(request,"query_color");
		queryParam.putRequestString(request,"query_obu_id");

		queryParam.putRequestString(request,"query_user_number");
		queryParam.putRequestString(request,"query_user_name");
		queryParam.putRequestString(request,"query_dept_info");
		queryParam.putRequestString(request,"query_job_type");
		queryParam.putRequestString(request,"query_phone");*/
		

		Page page=getPage(request);
		page.setPageSize(20);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/whiteList/WhiteListMng/WhiteListMng_list.jsp");
		
		//权限控制
		/*LoginUserBean loginUserBean=(LoginUserBean) request.getSession().getAttribute("loginUser");
		String areaid = loginUserBean.getAreaId();
		view.addObject("areaid", areaid);
		if(!"0".equals(areaid)){
			queryParam.put("areaid", "|"+areaid+"-");
		}*/
		
		
		view.addObject(LIST, whiteListMngService.findList(queryParam, page));
		/*List<AreaInfoBean> areaInfoList = whiteListMngService.findAreaList();
		view.addObject("areaInfoList", areaInfoList);*/
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		
		

		return view;
	}
	
	
	/**
	 * 展示数据列表
	 * @return
	 */
	@RequestMapping(value = "/comfirmList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView comfirmList(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		if(request.getParameter("query_mv_license")!= null &&
				!"".equals(request.getParameter("query_mv_license").toString().trim()))
		{
			queryParam.put("query_mv_license", request.getParameter("query_mv_license").toString().trim());
		}
		
		if(request.getParameter("query_user_number")!= null &&
				!"".equals(request.getParameter("query_user_number").toString().trim()))
		{
			queryParam.put("query_user_number", request.getParameter("query_user_number").toString().trim());
			
		}
		if(request.getParameter("query_user_name")!= null &&
				!"".equals(request.getParameter("query_user_name").toString().trim()))
		{
			queryParam.put("query_user_name", request.getParameter("query_user_name").toString().trim());
			
		}
		
		if(request.getParameter("query_phone")!= null &&
				!"".equals(request.getParameter("query_phone").toString().trim()))
		{
			queryParam.put("query_phone", request.getParameter("query_phone").toString().trim());
			
		}
		Page page=getPage(request);
		page.setPageSize(100);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/whiteList/WhiteListMng/WhiteListMng_comfirmList.jsp");
		
		//权限控制
		/*LoginUserBean loginUserBean=(LoginUserBean) request.getSession().getAttribute("loginUser");
		String areaid = loginUserBean.getAreaId();
		view.addObject("areaid", areaid);
		if(!"0".equals(areaid)){
			queryParam.put("areaid", "|"+areaid+"-");
		}
		List<AreaInfoBean> areaInfoList = whiteListMngService.findAreaList();
		view.addObject("areaInfoList", areaInfoList);*/
		
		
		view.addObject(LIST, whiteListMngService.comfirmFindList(queryParam, page));
		view.addObject("queryParam", queryParam);
		view.addObject(PAGE, page);
		
		return view;
	}
	
	/**
	 * 
	   * @Title : revisableList 
	   * @功能描述: 白名单维护表
	   * @传入参数：@param request
	   * @传入参数：@return
	   * @返回类型：ModelAndView 
	   * @throws ：
	 */
	@RequestMapping(value = "/revisableList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView revisableList(HttpServletRequest request){
		ObjectMap queryParam=ObjectMap.newInstance();
		
		if(request.getParameter("query_mv_license")!= null &&
				!"".equals(request.getParameter("query_mv_license").toString().trim()))
		{
			queryParam.put("query_mv_license", request.getParameter("query_mv_license").toString().trim());
		}
		if(request.getParameter("query_color")!= null &&
				!"".equals(request.getParameter("query_color").toString().trim()))
		{
			queryParam.put("query_color", request.getParameter("query_color").toString().trim());
		}
		if(request.getParameter("query_obu_id")!= null &&
				!"".equals(request.getParameter("query_obu_id").toString().trim()))
		{
			queryParam.put("query_obu_id", request.getParameter("query_obu_id").toString().trim());
			
		}
		if(request.getParameter("query_user_number")!= null &&
				!"".equals(request.getParameter("query_user_number").toString().trim()))
		{
			queryParam.put("query_user_number", request.getParameter("query_user_number").toString().trim());
			
		}
		if(request.getParameter("query_user_name")!= null &&
				!"".equals(request.getParameter("query_user_name").toString().trim()))
		{
			queryParam.put("query_user_name", request.getParameter("query_user_name").toString().trim());
			
		}
		if(request.getParameter("query_dept_info")!= null &&
				!"".equals(request.getParameter("query_dept_info").toString().trim()))
		{
			queryParam.put("query_dept_info", request.getParameter("query_dept_info").toString().trim());
			
		}
		if(request.getParameter("query_job_type")!= null &&
				!"".equals(request.getParameter("query_job_type").toString().trim()))
		{
			queryParam.put("query_job_type", request.getParameter("query_job_type").toString().trim());
			
		}
		if(request.getParameter("query_phone")!= null &&
				!"".equals(request.getParameter("query_phone").toString().trim()))
		{
			queryParam.put("query_phone", request.getParameter("query_phone").toString().trim());
			
		}
		Page page=getPage(request);
		page.setPageSize(20);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/whiteList/WhiteListMng/WhiteListMng_revisableList.jsp");
		
		//权限控制
		LoginUserBean loginUserBean=(LoginUserBean) request.getSession().getAttribute("loginUser");
		String areaid = loginUserBean.getAreaId();
		view.addObject("areaid", areaid);
		if(!"0".equals(areaid)){
			queryParam.put("areaid", "|"+areaid+"-");
		}
		List<AreaInfoBean> areaInfoList = whiteListMngService.findAreaList();
		view.addObject("areaInfoList", areaInfoList);
		
		view.addObject(LIST, whiteListMngService.findList(queryParam, page));
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
		log.info("区域查询！！！");
		// 返回所有未删除的区域
		List<AreaInfoBean> areaInfoList = whiteListMngService.findAreaList();
		log.info("查询结果"+areaInfoList.size());
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/whiteList/WhiteListMng/WhiteListMng_add.jsp");
		
		//权限控制
		LoginUserBean loginUserBean=(LoginUserBean) request.getSession().getAttribute("loginUser");
		String areaid = loginUserBean.getAreaId();
		view.addObject("areaid", areaid);
		
		view.addObject("areaInfoList", areaInfoList);
		return view;
	}
	
	
	/**
	 * 根据用户工号查询用户信息 主要确保车主的可拥有车位数和联系方式一致 
	* @Title: checkusernumber  
	* @Description: TODO方法作用描述：  
	* @param @param request
	* @param @param bean
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/checkusernumber", method = { RequestMethod.GET, RequestMethod.POST })
	public String checkusernumber(HttpServletRequest request){
		//标记是否已经存在
		boolean hasuserflag = false;
		try {
			String usernumber = request.getParameter("usernumber");
			List<WhiteListMngBean> list = whiteListMngService.findWhiteListOfUserNumber(usernumber);
			if(list.size()>0){
				hasuserflag = true;
				return hasuserflag+"-"+list.get(0).getUser_name()+"-"+list.get(0).getSpare1()+"-"
				+list.get(0).getGender()+"-"+list.get(0).getPhone()+"-"+list.get(0).getS_comment();
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return hasuserflag+"-"+"nousernum";
	}
	
	/**
	 * 根据车牌查询在库唯一性
	* @Title: checkmvlicense  
	* @Description: TODO方法作用描述：  
	* @param @param request
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/checkmvlicense", method = { RequestMethod.GET, RequestMethod.POST })
	public String checkmvlicense(HttpServletRequest request){
		//标记是否已经存在
		String hasuserflag = "0";
		String mv_license = request.getParameter("mv_license");
		try {
			List<WhiteListMngBean> list = whiteListMngService.findWhiteListOfMvLicense(mv_license);
			if(list.size()>0){
				hasuserflag = "1"+"-"+mv_license;
				return hasuserflag;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return hasuserflag+"-"+mv_license;
	}
	
	/**
	 * 如果选择的是收费 根据车牌去temp_cost里面找当前的车牌是不是存在
	* @Title: checktempcostbymvlicense  
	* @Description: TODO方法作用描述：  
	* @param @param request
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/checktempcostbymvlicense", method = { RequestMethod.GET, RequestMethod.POST })
	public String checktempcostbymvlicense(HttpServletRequest request){

		String mv_license = request.getParameter("mv_license");
		Integer count = 0;
		try {
			 count = whiteListMngService.findTempCostCountByMvlicense(mv_license);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return count.toString();
	}
	
	/**
	 * 根据用户联系方式查询用户信息 主要确保车主的可拥有车位数和联系方式一致 
	* @Title: checkphone
	* @Description: TODO方法作用描述：  
	* @param @param request
	* @param @param bean
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/checkphone", method = { RequestMethod.GET, RequestMethod.POST })
	public String checkphone(HttpServletRequest request){
		Integer sumparks = 0;
		String usernum = "";
		boolean hasuserflag = false;
		try {
			String phone = request.getParameter("phone");
			List<WhiteListMngBean> list = whiteListMngService.findWhiteListOfPhone(phone);
			if(list.size()>0){
				hasuserflag = true;
				sumparks = list.get(0).getSpare1();
				usernum = list.get(0).getUser_number();
				return hasuserflag+"-"+sumparks.toString()+"-"+usernum;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return hasuserflag+"-"+sumparks.toString()+"-"+usernum;
	}
	
	
	
	
	
	/**
	 * 展示新增的界面
	 * @return
	 */
	@RequestMapping(value = "/comfirmAdd", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView comfirmAdd(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/whiteList/WhiteListMng/WhiteListMng_comfirmAdd.jsp");
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
	public String save(HttpServletRequest request,WhiteListMngBean bean){
		try {
			//获取用户id
			LoginUserBean loginUserBean = (LoginUserBean)request.getSession().getAttribute("loginUser");
			//获得新增校验的返回值
			String message = whiteListMngService.save(bean,loginUserBean.getUserId());
			//判断是否存在相同的白名单车辆
			if(message != null && "success".equals(message))
			{
				return super.responseJsonText(ResultBean.newSuccessResult());
			}
			return super.responseJsonText(ResultBean.newErrorResult(message));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("保存失败,请稍后再试"));
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
	@RequestMapping(value = "/comfirmSave", method = { RequestMethod.GET, RequestMethod.POST })
	public String comfirmSave(HttpServletRequest request,WhiteListMngBean bean){
		try {
			//获取用户id
			LoginUserBean loginUserBean = (LoginUserBean)request.getSession().getAttribute("loginUser");
			//获得新增校验的返回值
			String message = whiteListMngService.comfirmSave(bean,loginUserBean.getUserId());
			//判断是否存在相同的白名单车辆
			if(message != null && "success".equals(message))
			{
				return super.responseJsonText(ResultBean.newSuccessResult());
			}
			return super.responseJsonText(ResultBean.newErrorResult(message));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("启用失败,请稍后再试"));
	}
	
	
	
	/**
	 * 
	   * @Title : submit 提交白名单
	   * @功能描述: TODO
	   * @传入参数：@param request
	   * @传入参数：@param bean
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	@ResponseBody
	@RequestMapping(value = "/submit", method = { RequestMethod.GET, RequestMethod.POST })
	public String submit(HttpServletRequest request,WhiteListMngBean bean){
		log.info("submit.shtml");
		try {
			//获取用户id
			LoginUserBean loginUserBean = (LoginUserBean)request.getSession().getAttribute("loginUser");
			//获得区域id
			String area_ids = request.getParameter("area_ids");
			
			//获得新增校验的返回值
			//String message = whiteListMngService.submit(bean,loginUserBean.getUserId(),area_ids);
			
			//获得新增校验的返回值  ----------没有区域的版本
			String message = whiteListMngService.submitOfNoAreaids(bean,loginUserBean.getUserId(),area_ids);
			
			
			if(message != null && "success".equals(message))
			{
				return super.responseJsonText(ResultBean.newSuccessResult());
			}
			return super.responseJsonText(ResultBean.newErrorResult(message));
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("保存失败,请稍后再试"));
	}
	
	
	
	/**
	 * 
	   * @Title : submitVerify 提交白名单,更改为审核
	   * @功能描述: TODO
	   * @传入参数：@param request
	   * @传入参数：@param bean
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	@ResponseBody
	@RequestMapping(value = "/submitVerify", method = { RequestMethod.GET, RequestMethod.POST })
	public String submitVerify(HttpServletRequest request,WhiteListMngBean bean){
		try {
			//获取用户id
			LoginUserBean loginUserBean = (LoginUserBean)request.getSession().getAttribute("loginUser");
			//获得新增校验的返回值
			boolean flag = whiteListMngService.sumbitVerify(bean,loginUserBean.getUserId());
			if(flag)
			{
				return super.responseJsonText(ResultBean.newSuccessResult());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("提交失败,请稍后再试"));
	}
	
	
	/**
	 * 
	   * @Title : comfirmVerify 提交白名单,更改为审核
	   * @功能描述: TODO
	   * @传入参数：@param request
	   * @传入参数：@param bean
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	@ResponseBody
	@RequestMapping(value = "/comfirmVerify", method = { RequestMethod.GET, RequestMethod.POST })
	public String comfirmVerify(HttpServletRequest request,WhiteListMngBean bean){
		try {
			//获取用户id
			LoginUserBean loginUserBean = (LoginUserBean)request.getSession().getAttribute("loginUser");
			String type = request.getParameter("type");
			//获得新增校验的返回值
			boolean flag = whiteListMngService.comfirmVerify(bean,loginUserBean.getUserId(),type);
			if(flag)
			{
				return super.responseJsonText(ResultBean.newSuccessResult());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}
		return super.responseJsonText(ResultBean.newErrorResult("审核失败,请稍后再试"));
	}
	
	
	
	/**
	 * 
	   * @Title : comfirmVerifyList 提交白名单,更改为审核
	   * @功能描述: TODO
	   * @传入参数：@param request
	   * @传入参数：@param bean
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	@ResponseBody
	@RequestMapping(value = "/comfirmVerifyList", method = { RequestMethod.GET, RequestMethod.POST })
	public String comfirmVerifyList(HttpServletRequest request,String[] allowData){
		try {
			//获取用户id
			LoginUserBean loginUserBean = (LoginUserBean)request.getSession().getAttribute("loginUser");
			String type = request.getParameter("type");
			log.info(allowData);

			//获得新增校验的返回值
			boolean flag = whiteListMngService.comfirmVerifyList(allowData,loginUserBean.getUserId(),type);
			if(flag)
			{
				return super.responseJsonText(ResultBean.newSuccessResult());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("审核失败,请重新提交！"));
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String delete(HttpServletRequest request){
		try{
			ObjectMap objectMap = ObjectMap.newInstance();
			objectMap.put("mv_license", getStr(request,"mv_license"));
			objectMap.put("color", getInt(request,"color"));
			objectMap.put("create_time", DateUtil.parse4yMdHms(getStr(request,"create_time")));
//			objectMap.put("lane_info_query", getStr(request,"lane_info_query"));
			
			whiteListMngService.delete(objectMap);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	
	
	/**
	 * 作废白名单申请
	 * @return
	 */
	@RequestMapping(value = "/invalidApply", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String invalidApply(HttpServletRequest request){
		try{
			LoginUserBean loginUserBean = (LoginUserBean)request.getSession().getAttribute("loginUser");
			ObjectMap objectMap = ObjectMap.newInstance();
			objectMap.put("mv_license", getStr(request,"mv_license"));
			objectMap.put("color", getInt(request,"color"));
			objectMap.put("create_time", DateUtil.parse4yMdHms(getStr(request,"create_time")));
			objectMap.put("lane_info_query", getStr(request,"lane_info_query"));
			
			String message = whiteListMngService.invalidApply(objectMap,loginUserBean.getUserId());
			//判断是否存在相同的白名单车辆
			if(message != null && "success".equals(message))
			{
				return super.responseJsonText(ResultBean.newSuccessResult());
			}
			return super.responseJsonText(ResultBean.newErrorResult(message));
		}catch (Exception e) {
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("作废失败!"));
		}
	}
	
	
	/**
	 * 展示编辑的白名单界面
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView edit(HttpServletRequest request){
//		String memberId=getStr(request,"memberId");
//		MemberBean bean=whiteListMngService.findInfo(memberId);
		
		String mv_license = getStr(request,"mv_license");
		String color = getStr(request,"color");
		String create_time_str = getStr(request,"create_time");
		/*String lane_info = getStr(request,"laneinfo");*/
		
		Date create_time = DateUtil.parse4yMdHms(create_time_str.replace(","," "));
		ObjectMap queryParam = ObjectMap.newInstance();
		queryParam.put("mv_license", mv_license);
		queryParam.put("color", color);
		queryParam.put("create_time", create_time);
		
		/*String lane_info_query = "|"+lane_info+"-";
		queryParam.put("lane_info_query", lane_info_query);*/
		
		// 查询出所有车道
		
		
		//白名单对象
		WhiteListMngBean bean = whiteListMngService.findObj(queryParam);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/whiteList/WhiteListMng/WhiteListMng_edit.jsp");
		/*List<AreaInfoBean> areaInfoList = whiteListMngService.findAreaList();
		view.addObject("areaInfoList", areaInfoList);*/
		view.addObject(BEAN, bean);		
		return view;
	}
	
	
	/**
	 * 展示编辑的白名单界面
	 * @return
	 */
	@RequestMapping(value = "/revisableEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView revisableEdit(HttpServletRequest request){
//		String memberId=getStr(request,"memberId");
//		MemberBean bean=whiteListMngService.findInfo(memberId);
		String mv_license = getStr(request,"mv_license");
		String color = getStr(request,"color");
		String create_time_str = getStr(request,"create_time");
//		String lane_info = getStr(request,"laneinfo");
		
		
		Date create_time = DateUtil.parse4yMdHms(create_time_str.replace(","," "));
		ObjectMap queryParam = ObjectMap.newInstance();
		queryParam.put("mv_license", mv_license);
		queryParam.put("color", color);
		queryParam.put("create_time", create_time);
		
//		String lane_info_query = "|"+lane_info+"-";
//		queryParam.put("lane_info_query", lane_info_query);
		
		//白名单对象
		WhiteListMngBean bean = whiteListMngService.findObj(queryParam);
		ModelAndView view=new ModelAndView("/jsp/backMng/admin/whiteList/WhiteListMng/WhiteListMng_revisableEdit.jsp");
//		List<AreaInfoBean> areaInfoList = whiteListMngService.findAreaList();
//		view.addObject("areaInfoList", areaInfoList);
		view.addObject(BEAN, bean);	
		return view;
	}
	
	

	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(HttpServletRequest request,WhiteListMngBean bean){
		try {
			//获取用户id
			LoginUserBean loginUserBean = (LoginUserBean)request.getSession().getAttribute("loginUser");
			//获得区域id
//			String area_ids = request.getParameter("area_ids");
//			String message = whiteListMngService.update(bean, loginUserBean.getUserId(),area_ids);
			String message = whiteListMngService.updateOfNoAreaids(bean, loginUserBean.getUserId());
			
			if("success".equals(message))
			{
				return super.responseJsonText(ResultBean.newSuccessResult());
			}
			return super.responseJsonText(ResultBean.newErrorResult(message));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			e.printStackTrace();
		}		
		return super.responseJsonText(ResultBean.newErrorResult("保存失败,请稍后再试"));
	}
	

	/**
	 * 更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePersonInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public String updatePersonInfo(HttpServletRequest request,WhiteListMngBean bean){
		try {
			//获取用户id
			LoginUserBean loginUserBean = (LoginUserBean)request.getSession().getAttribute("loginUser");
			//获得区域id
			//String area_ids = request.getParameter("area_ids");
			//获取用户id
			//String message = whiteListMngService.updatePersonInfo(bean, loginUserBean.getUserId(),area_ids);
			String message = whiteListMngService.updatePersonInfoOfNoAreaids(bean, loginUserBean.getUserId());
			
			if("success".equals(message))
			{
				return super.responseJsonText(ResultBean.newSuccessResult());
			}
			return super.responseJsonText(ResultBean.newErrorResult(message));
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
	@RequestMapping(value = "/comfirmDelete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String comfirmDelete(HttpServletRequest request){
		try{
			ObjectMap objectMap = ObjectMap.newInstance();
			objectMap.put("mv_license", getStr(request,"mv_license"));
			objectMap.put("color", getInt(request,"color"));
			objectMap.put("vehicle_class", getStr(request,"vehicle_class"));
			objectMap.put("obu_id", getStr(request,"obu_id"));
			//获得最后修改时间
			String dateStr = request.getParameter("create_time");
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
			objectMap.put("create_time",date);
			whiteListMngService.deleteStatus(objectMap);
			return super.responseJsonText(ResultBean.newSuccessResult());
		}catch (Exception e) {
			log.error(e);
			return super.responseJsonText(ResultBean.newErrorResult("删除失败"));
		}
	}
	
	@RequestMapping("/downloadModule")
	public ModelAndView downloadModule(HttpServletRequest request){
		ModelAndView view=new ModelAndView("/download.jsp");
		String targetFile="白名单模板";
		String fileName=targetFile+".xls";
		targetFile=request.getRealPath("/")+targetFile+".xls";
		try {
			ExcelPoiTools tools=new ExcelPoiTools();
			int row=0;
			tools.setColWidth(new Integer[]{20,50,50,20,20,20,20,20,20,20,20,20});
			tools.writeHeader(row++, new String[]{"车辆牌照","车牌颜色(蓝牌 、黄牌、 黑牌、白牌)","车辆类型(小客车、大客车、货车)","收费形式（免费 、计票 、规则）","计费规则（时段计费A、时长计费A、次数计费A、默认）","开始时间","结束时间","部门编号","部门名称","车主","车主工号","性别(男、女)","车主联系方式","工作岗位","工作性质","车辆品牌","obu编号","车道"});
			tools.writeCell(row++, new String[]{"苏A12345","蓝牌","小客车","免费","默认","01/01/2017 08:00:00","12/31/2017 08:00:00","","江苏省人民医院-心胸外科","张三","53577","男","13819632570","外科医生","在编","1.8T帕萨特","1234GE14BA","全部"});
			tools.writeToFile(targetFile);
			view.addObject("fileUrl", targetFile);
			view.addObject("fileName", fileName);
			return view;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	   * @Title : upload 
	   * @功能描述: 导入白名单模板
	   * @传入参数：@param request
	   * @传入参数：@param file
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
       */
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(HttpServletRequest request,MultipartFile file){
		if(file!=null){
			
			try {
				List<WhiteListMngBean> list = daoruFileList(file.getInputStream());
				LoginUserBean user=(LoginUserBean) request.getSession().getAttribute("loginUser");
				
				//批量导入白名单
				boolean flag = whiteListMngService.insertList(list, user.getUserId());
				if(flag)
				{
					return super.responseJsonText(ResultBean.newSuccessResult());
				}
				
				return super.responseJsonText(ResultBean.newErrorResult("导入失败!"));
				/**for(WhiteListMngBean bean:list){
				
					bean.setCreate_time(new Date());
					whiteListMngService.save(bean, user.getUserId());
					if(whiteListMngService.findInfo(bean.getmv_license(),bean.getPlate_color())!=null){
						//核实下已经存在的黑名单用户是更新还是跳过
						//部分数据可能不用更新，需要重写一个update
						bean.setb_list_type(bean.getb_list_type().trim());
						whiteListMngService.update(bean, null);
						continue;
					}else{
						bean.setuser_id(user.getUserId());
						bean.setb_list_type(bean.getb_list_type().trim());
						whiteListMngService.save(bean, null);
					} 
				}*/
				
			} catch (IOException e) {
				e.printStackTrace();
				return super.responseJsonText(ResultBean.newErrorResult("请上传xls格式文档，格式参考导出文件"));
			}
		}
		
		return null;
	} 
	
	
	public  synchronized List<WhiteListMngBean> daoruFileList(InputStream inputStream) throws IOException{
//		List<String[]> result = new ArrayList<String[]>();
//		int rowSize = 0;
//		List<String> asList = Arrays.asList(type);
//		BufferedInputStream in=new BufferedInputStream(inputStream);
		POIFSFileSystem fs = new POIFSFileSystem(inputStream);
	       HSSFWorkbook wb = new HSSFWorkbook(fs);
	       List<WhiteListMngBean> list=new ArrayList<WhiteListMngBean>();
	       for(int i=0;i<wb.getNumberOfSheets();i++){
	    	   HSSFSheet sheet = wb.getSheetAt(i);
	    	   if(sheet==null){
	    		   continue;
	    	   }
	    	   for(int j=1;j<=sheet.getLastRowNum();j++){
	    		   WhiteListMngBean bean=new WhiteListMngBean();
	    		   HSSFRow row = sheet.getRow(j);
	    		   HSSFCell  mv_license= row.getCell(0);
	    		   HSSFCell color = row.getCell(1);
	    		   HSSFCell vehicle_class = row.getCell(2);
	    		   HSSFCell toll_type = row.getCell(3);
	    		   HSSFCell charge_code = row.getCell(4);
	    		   HSSFCell valid_start_time = row.getCell(5);
	    		   HSSFCell valid_end_time = row.getCell(6);
	    		   HSSFCell  dept_id= row.getCell(7);
	    		   HSSFCell  dept_info= row.getCell(8);
	    		   HSSFCell user_name = row.getCell(9);
	    		   HSSFCell  user_number= row.getCell(10);
	    		   HSSFCell  gender= row.getCell(11);
	    		   HSSFCell  phone= row.getCell(12);
	    		   HSSFCell  job= row.getCell(13);
	    		   HSSFCell  job_type= row.getCell(14);
	    		   HSSFCell  car_brand= row.getCell(15);
	    		   HSSFCell  obu_id= row.getCell(16);
	    		   HSSFCell  lane_info= row.getCell(17);
	    		   
	    		   bean.setMv_license(getValue(mv_license));
	    		   
	    		   String color_value = getValue(color);
	    		   if(color_value.contains("蓝牌"))
	    		   {
	    			   bean.setColor(0);
	    		   }
	    		   else if(color_value.contains("黄牌"))
	    		   {
	    			   bean.setColor(1);
	    		   }
	    		   else if(color_value.contains("黑牌"))
	    		   {
	    			   bean.setColor(2);
	    		   }
	    		   else if(color_value.contains("白牌"))
	    		   {
	    			   bean.setColor(3);
	    		   }
	    		   else if(color_value.contains("绿牌"))
	    		   {
	    			   bean.setColor(4);
	    		   }
	    		   else
	    		   {
	    			   bean.setColor(Integer.parseInt(getValue(color).replace(".0", "")));
	    		   }
	    			   
	    		   //车型:1小客车，2大客车，3货车
	    		   String vehicle_class_value = getValue(vehicle_class);
	    		   if(vehicle_class_value ==null || "".equals(vehicle_class_value))
	    		   {
	    			   bean.setVehicle_class(0);
	    		   }
	    		   else
	    		   {
	    			   /*if(asList.contains(vehicle_class_value)){
		    			   bean.setVehicle_class(Integer.parseInt(vehicle_class_value.replace(".0", "")));
		    		   }
	    			   else
	    			   {
	    				   bean.setVehicle_class(Integer.parseInt(vehicle_class_value));
	    			   }*/
	    			   if("小客车".equals(vehicle_class_value.trim()))
	    			   {
	    				   bean.setVehicle_class(1);
	    			   }
	    			   else if("大客车".equals(vehicle_class_value.trim()))
	    			   {
	    				   bean.setVehicle_class(2);
	    			   }
	    			   else if("货车".equals(vehicle_class_value.trim()))
	    			   {
	    				   bean.setVehicle_class(3);
	    			   }
	    			   else
	    			   {
	    				   bean.setVehicle_class(Integer.parseInt(vehicle_class_value.replace(".0", "")));
	    			   }
	    		   }
	    		   
	    		   //判断收费形式类型：默认：0,免费：1，规则：2，计票：3
	    		   String toll_type_value = getValue(toll_type);
	    		   if(toll_type_value ==null || "".equals(toll_type_value))
	    		   {
	    			   bean.setToll_type(0);
	    		   }
	    		   else
	    		   {
	    			   if("免费".equals(toll_type_value.trim()))
	    			   {
	    				   bean.setToll_type(1);
	    			   }
	    			   else if("规则".equals(toll_type_value.trim()))
	    			   {
	    				   bean.setToll_type(2);
	    			   }
	    			   else if("计票".equals(toll_type_value.trim()))
	    			   {
	    				   bean.setToll_type(3);
	    			   }
	    			   else
	    			   {
	    				   bean.setToll_type(Integer.parseInt(toll_type_value.replace(".0", "")));
	    			   }
	    		   }
	    		   
	    		 //判断计费规则
	    		   String charge_code_value = getValue(charge_code);
	    		   if(charge_code_value ==null || "".equals(charge_code_value))
	    		   {
	    			   bean.setCharge_code("0");
	    		   }
	    		   else
	    		   {
	    			   if("时段计费A".equals(charge_code_value.trim()))
	    			   {
	    				   bean.setCharge_code("11");
	    			   }
	    			   else if("时长计费A".equals(charge_code_value.trim()))
	    			   {
	    				   bean.setCharge_code("21");
	    			   }
	    			   else if("次数计费A".equals(charge_code_value.trim()))
	    			   {
	    				   bean.setCharge_code("31");
	    			   }
	    			   else if("默认".equals(charge_code_value.trim()))
	    			   {
	    				   bean.setCharge_code(PropertiesUtil.get("CHARGE_TYPE"));
	    			   }
	    			   else
	    			   {
		    			   bean.setCharge_code(charge_code_value.replace(".0", ""));
	    			   }
	    		   }
	    		   log.info(getValue(mv_license));
	    		   log.info(getValue(valid_start_time));
	    		   bean.setValid_start_time(DateUtil.parseExcel4MdyHms(getValue(valid_start_time)));
	    		   bean.setValid_end_time(DateUtil.parseExcel4MdyHms(getValue(valid_end_time)));
	    		   //判断部门编号是否为未填值
	    		   String dept_id_value = getValue(dept_id);
	    		   if(dept_id_value ==null || "".equals(dept_id_value))
	    		   {
	    			   bean.setDept_id(0);
	    		   }
	    		   else
	    		   {
	    			   bean.setDept_id(Integer.parseInt(dept_id_value.replace(".0", "")));
	    		   }
	    		   bean.setDept_info(getValue(dept_info));
	    		   bean.setUser_name(getValue(user_name));
	    		   if(user_number!=null)
	    		   {
	    			   bean.setUser_number(BigDecimalTool.cellGetValue(user_number));
	    		   }
	    		 //判断部门编号是否为未填值
	    		   String gender_value = getValue(gender);
	    		   if(gender_value ==null || "".equals(gender_value))
	    		   {
	    			   bean.setGender(0);
	    		   }
	    		   else
	    		   {
	    			   if("男".equals(gender_value.trim()))
	    			   {
	    				   bean.setGender(1);
	    			   }
	    			   else if("女".equals(gender_value.trim()))
	    			   {
	    				   bean.setGender(2);
	    			   }
	    			   else
	    			   {
	    			   bean.setGender(Integer.parseInt(gender_value.replace(".0", "")));
	    			   }
	    		   }
	    		   if(phone!=null)
	    		   {
	    			   bean.setPhone(BigDecimalTool.cellGetValue(phone));
	    		   }
	    		   bean.setJob(getValue(job));
	    		   bean.setJob_type(getValue(job_type));
	    		   bean.setCar_brand(getValue(car_brand));
	    		   bean.setObu_id(getValue(obu_id));
	    		   bean.setLane_info(getValue(lane_info));
	    		   list.add(bean);
	    	   }
	       }
	       return list;
	      
	    }
	
	/**
	 * 
	   * @Title : upload 
	   * @功能描述: 导入白名单模板
	   * @传入参数：@param request
	   * @传入参数：@param file
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：

	@RequestMapping("/upload")
	@ResponseBody
	public String upload(HttpServletRequest request,MultipartFile file){
		if(file!=null){
			
			try {
				List<ParkVoidCardBean> list = daoruFileList(file.getInputStream());
				LoginUserBean user=(LoginUserBean) request.getSession().getAttribute("loginUser");
				for(ParkVoidCardBean bean:list){
					if(!"1".equals(bean.getb_list_type().trim())  && !"2".equals(bean.getb_list_type().trim())){
						return super.responseJsonText(ResultBean.newErrorResult("Warning: Illegal Argument"));
					}
					if(whiteListMngService.findInfo(bean.getmv_license(),bean.getPlate_color())!=null){
						//核实下已经存在的黑名单用户是更新还是跳过
						//部分数据可能不用更新，需要重写一个update
						bean.setb_list_type(bean.getb_list_type().trim());
						whiteListMngService.update(bean, null);
						continue;
					}else{
						bean.setuser_id(user.getUserId());
						bean.setb_list_type(bean.getb_list_type().trim());
						whiteListMngService.save(bean, null);
					}
				}
				return super.responseJsonText(ResultBean.newSuccessResult());
			} catch (IOException e) {
				e.printStackTrace();
				return super.responseJsonText(ResultBean.newErrorResult("请上传xls格式文档，格式参考导出文件"));
			}
		}
		
		return null;
	} 
	

	public  synchronized List<ParkVoidCardBean> daoruFileList(InputStream inputStream) throws IOException{
//		List<String[]> result = new ArrayList<String[]>();
//		int rowSize = 0;
		List<String> asList = Arrays.asList(type);
//		BufferedInputStream in=new BufferedInputStream(inputStream);
		POIFSFileSystem fs = new POIFSFileSystem(inputStream);
	       HSSFWorkbook wb = new HSSFWorkbook(fs);
	       List<ParkVoidCardBean> list=new ArrayList<ParkVoidCardBean>();
	       for(int i=0;i<wb.getNumberOfSheets();i++){
	    	   HSSFSheet sheet = wb.getSheetAt(i);
	    	   if(sheet==null){
	    		   continue;
	    	   }
	    	   for(int j=1;j<=sheet.getLastRowNum();j++){
	    		   ParkVoidCardBean bean=new ParkVoidCardBean();
	    		   HSSFRow row = sheet.getRow(j);
	    		   HSSFCell  mv_license= row.getCell(0);
	    		   HSSFCell plate_color = row.getCell(1);
	    		   HSSFCell in_time = row.getCell(2);
	    		   HSSFCell cancel_time = row.getCell(3);
	    		   HSSFCell b_list_type = row.getCell(4);
	    		   HSSFCell park_id = row.getCell(5);
	    		   HSSFCell  s_comment= row.getCell(6);
	    		   bean.setmv_license(getValue(mv_license));
	    		   bean.setb_list_type(getValue(b_list_type));
	    		   if(asList.contains(bean.getb_list_type())){
	    			   bean.setb_list_type(bean.getb_list_type().replace(".0", ""));
	    		   }
	    		   bean.setIn_time(DateUtil.parseExcel4yMdHms(getValue(in_time)));
	    		   bean.setCancel_time(DateUtil.parseExcel4yMdHms(getValue(cancel_time)));
	    		   bean.setPark_id(getValue(park_id));
	    		   bean.sets_comment(getValue(s_comment));
	    		   bean.setPlate_color(getValue(plate_color).replace(".0", ""));
	    		   list.add(bean);
	    	   }
	       }
	       return list;
	      
	    }
		 */
	

	
	private String getValue(HSSFCell hssfCell) {
		if(hssfCell!=null){
			
			if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
	             return String.valueOf(hssfCell.getBooleanCellValue());
	         }
	         else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
	             return String.valueOf(hssfCell.getNumericCellValue());
	         }
	         else {
	             return String.valueOf(hssfCell.getStringCellValue());
	         }
		}else{
			return null;
		}
         
     }
	
	

	
	
	public WhiteListMngService getWhiteListMngService() {
		return whiteListMngService;
	}

	public void setWhiteListMngService(WhiteListMngService whiteListMngService) {
		this.whiteListMngService = whiteListMngService;
	}
	
}
