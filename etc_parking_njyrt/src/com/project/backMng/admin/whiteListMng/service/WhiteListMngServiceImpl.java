package com.project.backMng.admin.whiteListMng.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.project.backCharge.tempCharge.model.TempCostBean;
import com.project.backMng.admin.whiteListMng.model.WhiteListMngBean;
import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;
import com.project.backMng.platuser.sys.ParkInfoMng.model.ParkInfoBean;
import com.project.common.tool.StringUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.mybatis.service.BaseService;

@Transactional
public class WhiteListMngServiceImpl extends BaseService implements WhiteListMngService {

	private final static Logger log = LogManager.getLogger(WhiteListMngServiceImpl.class);

	public WhiteListMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.whiteList.WhiteListMng");
	}

	@Override
	public List<WhiteListMngBean> findList(ObjectMap queryParam, Page page) {
		page.setRecordCount(super.findInteger(ns("findCount"), queryParam));
		List<WhiteListMngBean> list = super.findList(ns("findList"), queryParam, page);
		return list;
	}
	
	@Override
	public List<WhiteListMngBean> comfirmFindList(ObjectMap queryParam, Page page) {
		page.setRecordCount(super.findInteger(ns("comfirmFindCount"), queryParam));
		List<WhiteListMngBean> list = super.findList(ns("comfirmFindList"), queryParam, page);
		return list;
	}

	/**
	 * 
	 * @Title : save
	 * @功能描述: 保存新建黑名单
	 * @传入参数：@param bean
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public String save(WhiteListMngBean bean, String userId) {
		Date now = new Date();
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("mv_license", bean.getMv_license());
		objectMap.put("color", bean.getColor());
		objectMap.put("vehicle_class", bean.getVehicle_class());
		objectMap.put("initiate_id", userId);
		objectMap.put("initiate_time", now);
		objectMap.put("dept_id",bean.getDept_id());
		objectMap.put("dept_info",bean.getDept_info());
		objectMap.put("obu_id", bean.getObu_id());
		objectMap.put("status", 1);
		objectMap.put("valid_start_time", bean.getValid_start_time());
		objectMap.put("valid_end_time", bean.getValid_end_time());
		objectMap.put("create_time", now);
		objectMap.put("user_name",bean.getUser_name());
		objectMap.put("gender",bean.getGender());
		objectMap.put("user_number",bean.getUser_number());
		objectMap.put("phone",bean.getPhone());
		objectMap.put("job", bean.getJob());
		objectMap.put("job_type", bean.getJob_type());
		objectMap.put("s_comment",bean.getS_comment());
		objectMap.put("toll_type", bean.getToll_type());
		objectMap.put("charge_code", bean.getCharge_code());

		super.insert(ns("insertWhiteList"), objectMap);
		return "success";
	}
	
	@Override
	@Transactional
	public boolean insertList(List<WhiteListMngBean> list, String userId) {
		// TODO Auto-generated method stub
		
		if(list == null || list.size() == 0)
		{
			return false;
		}
		for(WhiteListMngBean bean : list)
		{
			Date now = new Date();
			ObjectMap objectMap = ObjectMap.newInstance();
			objectMap.put("mv_license", bean.getMv_license());
			objectMap.put("color", bean.getColor());
			objectMap.put("vehicle_class", bean.getVehicle_class());
			objectMap.put("initiate_id", userId);
			objectMap.put("initiate_time", now);
			objectMap.put("dept_id",bean.getDept_id());
			objectMap.put("dept_info",bean.getDept_info());
			objectMap.put("obu_id", bean.getObu_id());
			objectMap.put("status", 1);
			objectMap.put("valid_start_time", bean.getValid_start_time());
			objectMap.put("valid_end_time", bean.getValid_end_time());
			objectMap.put("create_time", now);
			objectMap.put("user_name",bean.getUser_name());
			objectMap.put("gender",bean.getGender());
			objectMap.put("user_number",bean.getUser_number());
			objectMap.put("phone",bean.getPhone());
			objectMap.put("job", bean.getJob());
			objectMap.put("job_type", bean.getJob_type());
			objectMap.put("s_comment",bean.getS_comment());
			objectMap.put("toll_type", bean.getToll_type());
			objectMap.put("charge_code", bean.getCharge_code());
			objectMap.put("car_brand", bean.getCar_brand());
			objectMap.put("obu_id", bean.getObu_id());
			String parkAreaLane = addLaneInfo(bean.getLane_info());
			objectMap.put("lane_info", parkAreaLane);
			super.insert(ns("insertWhiteList"), objectMap);
		}
		return true;
	}
	
	/**
	 * 
	 * @Title : save
	 * @功能描述: 保存新建黑名单
	 * @传入参数：@param bean
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public String comfirmSave(WhiteListMngBean bean, String userId) {
		Date now = new Date();
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("mv_license", bean.getMv_license());
		objectMap.put("color", bean.getColor());
		objectMap.put("vehicle_class", bean.getVehicle_class());
		objectMap.put("initiate_id", userId);
		objectMap.put("initiate_time", now);
		objectMap.put("comfirm_id", userId);
		objectMap.put("comfirm_time", now);
		objectMap.put("obu_id", bean.getObu_id());
		objectMap.put("status", 1);
		objectMap.put("valid_start_time", bean.getValid_start_time());
		objectMap.put("valid_end_time", bean.getValid_end_time());
		objectMap.put("create_time", now);
		super.insert(ns("insertWhiteList"), objectMap);
		return "success";
	}

	
	/**
	 * 
	 * @Title : submit
	 * @功能描述: 保存新建黑名单
	 * @传入参数：@param bean
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public String submit(WhiteListMngBean bean, String userId,String area_ids) {
		Date now = new Date();
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("mv_license", bean.getMv_license());
		objectMap.put("color", bean.getColor());
		objectMap.put("vehicle_class", bean.getVehicle_class());
		objectMap.put("initiate_id", userId);
		objectMap.put("initiate_time", now);
		objectMap.put("obu_id", bean.getObu_id());
		objectMap.put("status", 1);
		// 附属信息
		objectMap.put("user_name",bean.getUser_name());
		objectMap.put("gender",bean.getGender());
		objectMap.put("user_number",bean.getUser_number());
		objectMap.put("phone",bean.getPhone());
		objectMap.put("job", bean.getJob());
		objectMap.put("job_type", bean.getJob_type());
		objectMap.put("dept_id",bean.getDept_id());
		objectMap.put("dept_info",bean.getDept_info());
		objectMap.put("s_comment",bean.getS_comment());
		objectMap.put("spare2",bean.getSpare2());
		objectMap.put("spare1",bean.getSpare1());
		/*if (bean.getCreate_time() != null) {
			objectMap.put("create_time", bean.getCreate_time());
			objectMap.put("obu_id", "");
			// 相同车牌是否存在
			int countByLicense = super.findInteger(ns("countWhiteList"), objectMap);
			if (countByLicense > 0) {
				super.update(ns("updateWhiteListByLicense"), objectMap);
				return "success";
			}
			objectMap.put("obu_id", bean.getObu_id());
			objectMap.put("mv_license", "");
			objectMap.put("color", "");
			// 相同oubId是否存在
			int countByOBU = super.findInteger(ns("countWhiteList"), objectMap);
			if (countByOBU > 0) {
				super.update(ns("updateWhiteListByObuId"), objectMap);
				return "success";
			}
		} else {*/
			objectMap.put("toll_type", bean.getToll_type());
			objectMap.put("charge_code", bean.getCharge_code());
			objectMap.put("valid_start_time", bean.getValid_start_time());
			objectMap.put("valid_end_time", bean.getValid_end_time());
			objectMap.put("create_time", now);
		
			//原是将所有区域拼接成一起插入一条
			// String parkAreaLane = addLaneInfo(area_ids);
			//objectMap.put("lane_info",parkAreaLane);
			//super.insert(ns("insertWhiteList"), objectMap);
			//return "success";
			
			
			//现在：根据区域插入 即：每个区域的白名单在lane_info 单独插入一条
			List<LaneInfoBean> laneInfoBeans =super.findList(ns("findLaneList"));
			String[] areaIdArray = area_ids.split(",");
			for (int i = 0; i < areaIdArray.length; i++) {
				if (!areaIdArray[i].equals("")) {
					StringBuffer sb = new StringBuffer();
					int count =0;
					for (LaneInfoBean laneinfo : laneInfoBeans) {
						count++;
						// 判断区域编号是否相等
						if (areaIdArray[i].equals(laneinfo.getarea_id())) {
							sb.append("|");
							// 增加区域编号
							sb.append(areaIdArray[i] + "-");
							// 增加lane编号
							sb.append(laneinfo.getlane_id());
						}
						if(count == laneInfoBeans.size()){
							sb.append("|");
						}
					}
	
					objectMap.put("lane_info", sb.toString());
					if (!sb.toString().equals("")) {
						super.insert(ns("insertWhiteList"), objectMap);
					}
				}
	
			}
			return "success";
//		}
//		return "error";
	}
	
	
	/**
	 * 
	   * @Title : addLaneInfo 
	   * @功能描述: 根据区域id返回|停车场编号-区域编号-车道编号|
	   * @传入参数：@param area_ids
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	private String addLaneInfo(String area_ids)
	{

//***********************************原来的**********************begin			
/*		//判断是否为空
		if(area_ids == null || area_ids.length() == 0)
		{
			return area_ids;
		}
		String[] areaIdArray = area_ids.split(",");
//		List<ParkInfoBean> parkInfoBeans = super.findList(ns("findParkList"));
		List<LaneInfoBean> laneInfoBeans =super.findList(ns("findLaneList"));
		StringBuffer sb = new StringBuffer();
		
		
		// 获取停车id
		String park_id = "";
		if(parkInfoBeans != null && parkInfoBeans.size() != 0)
		{
			park_id = parkInfoBeans.get(0).getpark_id()+"-";
//			sb.append(park_id+"-");
		}
		// 循环迭代区域id
		for(int i = 0;i<areaIdArray.length;i++)
		{
			// 循环迭代车道编号
			for(LaneInfoBean bean : laneInfoBeans)
			{
				// 判断区域编号是否相等
				if(areaIdArray[i].equals(bean.getarea_id()))
				{
					sb.append("|");
					// 增加停车场id
//					sb.append(bean.getpark_id()+"-");
					// 增加区域编号
					sb.append(areaIdArray[i]+"-");
					// 增加lane编号
					sb.append(bean.getlane_id());
					
				}
			}
		}
		// 结束
		if(sb.length() != 0)
		{
			sb.append("|");
		}
		// 返回迭代结果
		return sb.toString();*/
//***********************************原来的**********************end	
		//现在使用的
		return makeLane_infoForAddList(area_ids);
		
	}

	
	/**
	 * 拼接lane_info 的处理
	* @Title: makeLane_infoForAddList  
	* @Description: TODO方法作用描述：  
	* @param @param area_ids
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public String makeLane_infoForAddList(String area_ids) {
		StringBuffer sb = new StringBuffer();

		List<AreaInfoBean> allAreas = findAreaList();
		List<LaneInfoBean> laneInfoBeans = findLaneList();
		
		
		//如果导入的车道没填或者填了‘全部’，lane_info 用全部区域+车道号 填充
		if (null == area_ids || "全部".equals(area_ids) || area_ids.length() == 0 || "".equals(area_ids)) {

			for (AreaInfoBean areas : allAreas) {
				for (LaneInfoBean bean : laneInfoBeans) {
					// 判断区域编号是否相等
					if (areas.getarea_id().equals(bean.getarea_id())) {
						sb.append("|");
						// 增加区域编号
						sb.append(areas.getarea_id() + "-");
						// 增加lane编号
						sb.append(bean.getlane_id());
					}
				}

			}

		} else {
			String[] areaIdArray = area_ids.split(",");

			// 循环迭代区域id
			for (int i = 0; i < areaIdArray.length; i++) {
				// 循环迭代车道编号
				for (LaneInfoBean bean : laneInfoBeans) {
					// 判断区域编号是否相等
					if (areaIdArray[i].equals(bean.getarea_id())) {
						sb.append("|");
						// 增加区域编号
						sb.append(areaIdArray[i] + "-");
						// 增加lane编号
						sb.append(bean.getlane_id());
					}
				}
			}
		}

		// 最后结束拼接|
		if (sb.length() != 0) {
			sb.append("|");
		}
		return sb.toString();
	}
	
	
	
	
	
	/**
	 * 
	   * @Title : update 
	   * @功能描述: 修改白名单数据
	   * @传入参数：@param bean
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public String update(WhiteListMngBean bean,String userId,String area_ids) {
		Date now = new Date();
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("mv_license", bean.getMv_license());
		objectMap.put("old_mv_license", bean.getOld_mv_license());
		objectMap.put("color", bean.getColor());
		objectMap.put("old_color", bean.getOld_color());
		
		objectMap.put("create_time", bean.getCreate_time());
		objectMap.put("lane_info_query", bean.getLane_info());
		
		
		WhiteListMngBean whiteListMngBean = super.findObj(ns("findWhiteListForEdit"), objectMap);
		if(whiteListMngBean == null)
		{
			return "不存在此白名单！";
		}
		if(whiteListMngBean.getStatus() != 1)
		{
			 return "白名单已生效！";
		}
		
		objectMap.put("obu_id", bean.getObu_id());
		objectMap.put("vehicle_class", bean.getVehicle_class());
		objectMap.put("initiate_id", userId);
		objectMap.put("initiate_time", now);
		objectMap.put("dept_id",bean.getDept_id());
		objectMap.put("dept_info",bean.getDept_info());
		objectMap.put("obu_id", bean.getObu_id());
		objectMap.put("status", 1);
		objectMap.put("valid_start_time", bean.getValid_start_time());
		objectMap.put("valid_end_time", bean.getValid_end_time());
		objectMap.put("user_name",bean.getUser_name());
		objectMap.put("gender",bean.getGender());
		objectMap.put("user_number",bean.getUser_number());
		objectMap.put("phone",bean.getPhone());
		objectMap.put("job", bean.getJob());
		objectMap.put("job_type", bean.getJob_type());
		objectMap.put("s_comment",bean.getS_comment());
		objectMap.put("toll_type", bean.getToll_type());
		objectMap.put("charge_code", bean.getCharge_code());
//		String parkAreaLane = addLaneInfo(area_ids);
//		objectMap.put("lane_info",parkAreaLane);
		objectMap.put("lane_info",bean.getLane_info());
		objectMap.put("spare2",bean.getSpare2());
		objectMap.put("spare1",bean.getSpare1());
		
		//在修改之前做判断：车主工号---联系方式----可拥有车位数三者是否与 已经存在的相等,即始终保证在库中的三个字段信息一致!
		if(!bean.getUser_number().equals("")|| !bean.getPhone().equals("")){
			List<WhiteListMngBean> listnum = findWhiteListOfUserNumber(bean.getUser_number());
			List<WhiteListMngBean> listpho = findWhiteListOfPhone(bean.getPhone());
			
			if(listnum.size()>0 && listpho.size()>0){
				if(listnum.get(0).getUser_number().equals(listpho.get(0).getUser_number())
						&&listnum.get(0).getPhone().equals(listpho.get(0).getPhone())
						&&listnum.get(0).getSpare1() == bean.getSpare1()
						&&listpho.get(0).getSpare1() == bean.getSpare1()
						){
					
				}else{
					if(listnum.size()>0 ){
						return "该车主工号已经存在!请统一信息:联系方式为 "+listnum.get(0).getPhone()+"  可拥有车位数为 "+listnum.get(0).getSpare1();
					}
					if(listpho.size()>0){
						return "该联系方式已经在用!请统一信息:车主工号为 "+listpho.get(0).getUser_number()+"  可拥有车位数为 "+listpho.get(0).getSpare1();
					}
				}
			}else{
				if(listnum.size()>0 ){
					return "该车主工号已经存在!请统一信息:联系方式为 "+listnum.get(0).getPhone()+"  可拥有车位数为 "+listnum.get(0).getSpare1();
				}
				if(listpho.size()>0){
					return "该联系方式已经在用!请统一信息:车主工号为 "+listpho.get(0).getUser_number()+"  可拥有车位数为 "+listpho.get(0).getSpare1();
				}
			}

		}

		int count = super.update(ns("updateWhiteList"), objectMap);
		if(count <= 0)
		{
			return "修改失败";
		}
		return "success";
	}

	
	

	/**
	 * 
	   * @Title : update
	   * @功能描述: 修改白名单数据
	   * @传入参数：@param bean
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public String updatePersonInfo(WhiteListMngBean bean,String userId,String area_ids) {
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("mv_license", bean.getMv_license());
		objectMap.put("old_mv_license", bean.getOld_mv_license());
		objectMap.put("color", bean.getColor());
		objectMap.put("old_color", bean.getOld_color());
		
		objectMap.put("create_time", bean.getCreate_time());
		objectMap.put("lane_info_query", bean.getLane_info());
		
		WhiteListMngBean whiteListMngBean = super.findObj(ns("findWhiteListForEdit"), objectMap);
		if(whiteListMngBean == null)
		{
			return "不存在此白名单！";
		}

		
		objectMap.put("obu_id", bean.getObu_id());
		objectMap.put("vehicle_class", bean.getVehicle_class());
		objectMap.put("toll_type", bean.getToll_type());
		objectMap.put("charge_code", bean.getCharge_code());
		objectMap.put("user_name",bean.getUser_name());
		objectMap.put("gender",bean.getGender());
		objectMap.put("user_number",bean.getUser_number());
		objectMap.put("phone",bean.getPhone());
		objectMap.put("job", bean.getJob());
		objectMap.put("job_type", bean.getJob_type());
		objectMap.put("dept_id",bean.getDept_id());
		objectMap.put("dept_info",bean.getDept_info());
		objectMap.put("s_comment",bean.getS_comment());
		objectMap.put("spare2",bean.getSpare2());
		objectMap.put("spare1",bean.getSpare1());
		// 修改开始时间和结束时间
		objectMap.put("valid_start_time", bean.getValid_start_time());
		objectMap.put("valid_end_time", bean.getValid_end_time());
//		String parkAreaLane = addLaneInfo(area_ids);
//		objectMap.put("lane_info",parkAreaLane);
		
		objectMap.put("lane_info",bean.getLane_info());
		
		
		//在修改之前做判断：车主工号---联系方式----可拥有车位数三者是否与 已经存在的相等,即始终保证在库中的三个字段信息一致!
		if(!bean.getUser_number().equals("")|| !bean.getPhone().equals("")){
			List<WhiteListMngBean> listnum = findWhiteListOfUserNumber(bean.getUser_number());
			List<WhiteListMngBean> listpho = findWhiteListOfPhone(bean.getPhone());
			
			if(listnum.size()>0 && listpho.size()>0){
				if(listnum.get(0).getUser_number().equals(listpho.get(0).getUser_number())
						&&listnum.get(0).getPhone().equals(listpho.get(0).getPhone())
						&&listnum.get(0).getSpare1() == bean.getSpare1()
						&&listpho.get(0).getSpare1() == bean.getSpare1()
						){
					
				}else{
					if(listnum.size()>0 ){
						return "该车主工号已经存在!请统一信息:联系方式为 "+listnum.get(0).getPhone()+"  可拥有车位数为 "+listnum.get(0).getSpare1();
					}
					if(listpho.size()>0){
						return "该联系方式已经在用!请统一信息:车主工号为 "+listpho.get(0).getUser_number()+"  可拥有车位数为 "+listpho.get(0).getSpare1();
					}
				}
			}else{
				if(listnum.size()>0 ){
					return "该车主工号已经存在!请统一信息:联系方式为 "+listnum.get(0).getPhone()+"  可拥有车位数为 "+listnum.get(0).getSpare1();
				}
				if(listpho.size()>0){
					return "该联系方式已经在用!请统一信息:车主工号为 "+listpho.get(0).getUser_number()+"  可拥有车位数为 "+listpho.get(0).getSpare1();
				}
			}

		}

		
		
		int count = super.update(ns("updateWhiteListPersonInfo"), objectMap);
		logger.info("用户"+userId+"修改白名单车牌"+bean.getMv_license());
		if(count <= 0)
		{
			return "修改失败";
		}
		return "success";
	}
	
	/**
	 * 
	 * @Title : delete
	 * @功能描述: 删除白名单
	 * @传入参数：@param bean @返回值：
	 * @throws ：
	 */
	@Override
	public String delete(ObjectMap objectMap) {
		
		WhiteListMngBean whiteListMngBean = super.findObj(ns("findWhiteList"), objectMap);
		if(whiteListMngBean == null)
		{
			return "不存在此白名单！";
		}
		if(whiteListMngBean.getStatus() == 3)
		{
			 return "白名单已生效！";
		}
		/*if(whiteListMngBean.getStatus() != 1)
		{
			 return "白名单状态已变更！";
		}*/
		if(whiteListMngBean.getStatus() != 1 && whiteListMngBean.getStatus() != 5)
		{
			 return "白名单状态已变更！";
		}

		//删除白名单，返回成功
		super.delete(ns("deleteWhiteList"), objectMap);
		return "success";
	}
	
	/**
	 * 
	 * @Title : invalid
	 * @功能描述: 作废白名单申请
	 * @传入参数：@param bean @返回值：
	 * @throws ：
	 */
	@Override
	public String invalidApply(ObjectMap objectMap,String userId)
	{
		WhiteListMngBean whiteListMngBean = super.findObj(ns("findWhiteList"), objectMap);
		if(whiteListMngBean == null)
		{
			return "不存在此白名单！";
		}
		if(whiteListMngBean.getStatus() == 5)
		{
			 return "作废已申请！";
		}
		if(whiteListMngBean.getStatus() != 3)
		{
			 return "白名单状态已变更！";
		}
		objectMap.put("status", 5);
		//作废发起用户编号
		objectMap.put("invalid_initiate_id", userId);
//		objectMap.put("create_time_now", now);
		//当前时间为作废发起时间
		objectMap.put("invalid_initiate_time", new Date());
		int num= super.update(ns("updateWhiteListStatus"), objectMap);
		if(num > 0)
		{
			return "success";
		}
		return "白名单作废出错";
		
	}

	/**
	 * 
	   * @Title : sumbitVerify 
	   * @功能描述: 提交白名单，草稿改为审核中
	   * @传入参数：@param bean
	   * @传入参数：@param userId
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public boolean sumbitVerify(WhiteListMngBean bean, String userId) {
		Date now = new Date();
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("mv_license", bean.getMv_license());
		objectMap.put("color", bean.getColor());
		objectMap.put("vehicle_class", bean.getVehicle_class());
		objectMap.put("initiate_id", userId);
		objectMap.put("initiate_time", now);
		objectMap.put("obu_id", bean.getObu_id());
		objectMap.put("status", 1);
//		objectMap.put("create_time_now", now);
		//获得最后修改时间
		objectMap.put("create_time", bean.getCreate_time());
		int num= super.update(ns("updateWhiteListStatus"), objectMap);
		if(num > 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * 
	   * @Title : comfirmVerify 
	   * @功能描述: 
	   * @传入参数：@param bean
	   * @传入参数：@param userId
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public boolean comfirmVerify(WhiteListMngBean bean, String userId,String type) {
		Date now = new Date();
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("mv_license", bean.getMv_license());
		objectMap.put("color", bean.getColor());
//		objectMap.put("lane_info_query", bean.getLane_info());
		objectMap.put("vehicle_class", bean.getVehicle_class());
//		objectMap.put("obu_id", bean.getObu_id());
		objectMap.put("create_time", bean.getCreate_time());
		
		//判断是否只是3生效、4驳回的状态
		if("3".equals(type))
		{
			objectMap.put("status", 3);
			objectMap.put("comfirm_id", userId);
			objectMap.put("comfirm_time", now);
			
			//白名单申请审核通过一系列逻辑处理
			boolean flagresult= whiteListStatue1DoOk(bean.getUser_number(),bean.getToll_type(),bean.getMv_license(),bean.getSpare1());
			if(flagresult == false){
				return false;
			}
	
		}
		else if("4".equals(type))
		{
			//之前的状态4 更改状态
//			objectMap.put("status", 4);
//			objectMap.put("comfirm_id", userId);
//			objectMap.put("comfirm_time", now);
			
			//现在 状态4 直接删除
			delete(objectMap);
			return true;
		}
		else if("6".equals(type))
		{
			//之前的状态6 更改状态
//			objectMap.put("status", 6);
//			objectMap.put("invalid_comfirm_id", userId);
//			objectMap.put("invalid_comfirm_time", now);
			
			
			//已经生效的白名单删除操作的一系列逻辑处理
			boolean flagresult = whiteListStatue5DoOk(bean.getMv_license(),bean.getToll_type(),bean.getUser_number(),bean.getSpare1());
			if(flagresult == false){
				return false;
			}
	
			//现在 状态6 直接删除
			delete(objectMap);
			return true;
		}
		else if("7".equals(type))
		{	//不会到这 在前台如果状态为7 已经用状态3表示了
			objectMap.put("status", 7);
			objectMap.put("invalid_comfirm_id", userId);
			objectMap.put("invalid_comfirm_time", now);
		}
		else{
			return false;
		}
		
		//获得最后修改时间
//		objectMap.put("create_time_now", now);
		
		
		

		int num= super.update(ns("updateWhiteListStatus"), objectMap);
		if(num > 0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 
	   * @Title : comfirmVerify 
	   * @功能描述: 
	   * @传入参数：@param bean
	   * @传入参数：@param userId
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	@Transactional
	public boolean comfirmVerifyList(String[] whiteList, String userId,String type) {
		
		//判断白名单列表是否为空
		if(whiteList == null || whiteList.length == 1)
		{
			return false;
		}
		
		// 判断保存类型是否为空
		if(StringUtil.isEmpty(type))
		{
			return false;
		}
		
		Date now = new Date();
		// 迭代白名单主键，批量更新审核状态
		for(int i=0;i<whiteList.length;i++)
		{
			if(whiteList[i] == "")
			{
				continue;
			}
			String [] strArray = whiteList[i].split(",");
			String status = strArray[3];
			ObjectMap objectMap = ObjectMap.newInstance();
			//赋值车牌、颜色、日期
			objectMap.put("mv_license", strArray[0]);
			objectMap.put("color",strArray[1]);
			
			//本项目只有type=1这种情况 即：只有批量通过
			//1通过，2驳回
			if("1".equals(type))
			{
				//判断状态，1新建申请，5作废申请
				if("1".equals(status))
				{
					objectMap.put("status", 3);
					objectMap.put("comfirm_id", userId);
					objectMap.put("comfirm_time", now);
					
					//白名单申请审核通过一系列逻辑处理
					boolean flagresult= whiteListStatue1DoOk(strArray[4],Integer.parseInt(strArray[5]),strArray[0],Integer.parseInt(strArray[6]));
					if(flagresult == false){
						return false;
					}
					
					objectMap.put("create_time", strArray[2]);
					int num= super.update(ns("updateWhiteListStatus"), objectMap);
					if(num < 1)
					{
						return false;
					}
					
				}

				if("5".equals(status))
				{
//					objectMap.put("status", 6);
//					objectMap.put("invalid_comfirm_id", userId);
//					objectMap.put("invalid_comfirm_time", now);
					
					objectMap.put("create_time",strArray[2]);
					
					//已经生效的白名单删除操作的一系列逻辑处理
					boolean flagresult = whiteListStatue5DoOk(strArray[0],Integer.parseInt(strArray[5]),strArray[4],Integer.parseInt(strArray[6]));
					if(flagresult == false){
						return false;
					}
					
					//现在作废申请 审核通过 状态6不用了  而是 直接删除
					delete(objectMap);

				}
				
			}
			else if("2".equals(type))
			{
				//判断状态，1新建申请，5作废申请
				if("1".equals(status))
				{
					objectMap.put("status", 4);
					objectMap.put("comfirm_id", userId);
					objectMap.put("comfirm_time", now);
				}

				if("5".equals(status))
				{
					objectMap.put("status", 7);
					objectMap.put("invalid_comfirm_id", userId);
					objectMap.put("invalid_comfirm_time", now);
				}
			}
			else{
				return false;
			}
			
//			objectMap.put("create_time_now", now);
			//获得最后修改时间
//			objectMap.put("create_time", strArray[2]);
//			int num= super.update(ns("updateWhiteListStatus"), objectMap);
//			if(num < 1)
//			{
//				return false;
//			}
		}
		return true;
	}

	/**
	 * 
	   * @Title : deleteStatus 
	   * @功能描述: 删除白名单状态
	   * @传入参数：@param objectMap
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public void deleteStatus(ObjectMap objectMap) {
		objectMap.put("delete_flag", 1);
		objectMap.put("create_time_now", new Date());
		super.delete(ns("deleteStatusWhiteList"), objectMap);
	}

	/**
	 * 
	   * @Title : findObj 
	   * @功能描述: TODO
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public WhiteListMngBean findObj(ObjectMap queryParam) {
		return super.findObj(ns("findWhiteList"), queryParam);
	}

	/**
	 * 
	   * @Title : findAreaList 
	   * @功能描述: 获取区域信息
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<AreaInfoBean> findAreaList() {
		return super.findList(ns("findAreaList"));
	}

	@Override
	public List<LaneInfoBean> findLaneList() {
		return  super.findList(ns("findLaneList"));
	}

	@Override
	public List<WhiteListMngBean> findWhiteListOfUserNumber(String usernumber) {
		ObjectMap queryParam = ObjectMap.newInstance();
		queryParam.put("usernumber", usernumber);
		List<WhiteListMngBean> list = super.findList(ns("findWhiteListOfUserNumber"), queryParam);
		return list;
	}

	@Override
	public List<WhiteListMngBean> findWhiteListOfPhone(String phone) {
		ObjectMap queryParam = ObjectMap.newInstance();
		queryParam.put("phone", phone);
		List<WhiteListMngBean> list = super.findList(ns("findWhiteListOfPhone"), queryParam);
		return list;
	}

	@Override
	public List<WhiteListMngBean> findWhiteListOfUserNumberAndPhone(String usernumber, String phone) {
		ObjectMap queryParam = ObjectMap.newInstance();
		queryParam.put("usernumber", usernumber);
		queryParam.put("phone", phone);
		List<WhiteListMngBean> list = super.findList(ns("findWhiteListOfUserNumberAndPhone"), queryParam);
		return list;
	}

	@Override
	@Transactional
	public String submitOfNoAreaids(WhiteListMngBean bean, String userId,String area_ids) {
		Date now = new Date();
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("mv_license", bean.getMv_license());
		objectMap.put("color", bean.getColor());
		objectMap.put("vehicle_class", bean.getVehicle_class());
		objectMap.put("valid_start_time", bean.getValid_start_time());
		objectMap.put("valid_end_time", bean.getValid_end_time());
		objectMap.put("toll_type", bean.getToll_type());
		objectMap.put("charge_code", bean.getCharge_code());
		objectMap.put("user_number",bean.getUser_number());
		objectMap.put("user_name",bean.getUser_name());
		objectMap.put("spare1",bean.getSpare1());
		objectMap.put("gender",bean.getGender());
		objectMap.put("phone",bean.getPhone());
		objectMap.put("s_comment",bean.getS_comment());
		
		
		// 附属信息
		objectMap.put("initiate_id", userId);
		objectMap.put("initiate_time", now);
		objectMap.put("status", 1);
		objectMap.put("create_time", now);
		
		
		/*if (bean.getCreate_time() != null) {
			objectMap.put("create_time", bean.getCreate_time());
			objectMap.put("obu_id", "");
			// 相同车牌是否存在
			int countByLicense = super.findInteger(ns("countWhiteList"), objectMap);
			if (countByLicense > 0) {
				super.update(ns("updateWhiteListByLicense"), objectMap);
				return "success";
			}
			objectMap.put("obu_id", bean.getObu_id());
			objectMap.put("mv_license", "");
			objectMap.put("color", "");
			// 相同oubId是否存在
			int countByOBU = super.findInteger(ns("countWhiteList"), objectMap);
			if (countByOBU > 0) {
				super.update(ns("updateWhiteListByObuId"), objectMap);
				return "success";
			}
		} else {*/

		//原是将所有区域拼接成一起插入一条
		String parkAreaLane = addLaneInfo(area_ids);
		objectMap.put("lane_info",parkAreaLane);
		
		
		super.insert(ns("insertWhiteList"), objectMap);
		return "success";

	}

	@Override
	public String updateOfNoAreaids(WhiteListMngBean bean, String userId) {
		Date now = new Date();
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("mv_license", bean.getMv_license());
		objectMap.put("old_mv_license", bean.getOld_mv_license());
		objectMap.put("color", bean.getColor());
		objectMap.put("old_color", bean.getOld_color());
		
		objectMap.put("create_time", bean.getCreate_time());
		
		WhiteListMngBean whiteListMngBean = super.findObj(ns("findWhiteListForEdit"), objectMap);
		if(whiteListMngBean == null)
		{
			return "不存在此白名单！";
		}
		if(whiteListMngBean.getStatus() != 1)
		{
			 return "白名单已生效！";
		}

		objectMap.put("vehicle_class", bean.getVehicle_class());
		objectMap.put("initiate_id", userId);
		objectMap.put("initiate_time", now);
		
		objectMap.put("status", 1);
		objectMap.put("valid_start_time", bean.getValid_start_time());
		objectMap.put("valid_end_time", bean.getValid_end_time());
		objectMap.put("user_name",bean.getUser_name());
		objectMap.put("gender",bean.getGender());
		objectMap.put("user_number",bean.getUser_number());
		objectMap.put("phone",bean.getPhone());
		objectMap.put("s_comment",bean.getS_comment());
		objectMap.put("toll_type", bean.getToll_type());
		objectMap.put("charge_code", bean.getCharge_code());
//		String parkAreaLane = addLaneInfo(area_ids);
//		objectMap.put("lane_info",parkAreaLane);
//		objectMap.put("lane_info",bean.getLane_info());
		objectMap.put("spare1",bean.getSpare1());

		int count = super.update(ns("updateWhiteList"), objectMap);
		if(count <= 0)
		{
			return "修改失败";
		}
		return "success";
	}

	@Override
	public String updatePersonInfoOfNoAreaids(WhiteListMngBean bean, String userId) {
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("mv_license", bean.getMv_license());
		objectMap.put("old_mv_license", bean.getOld_mv_license());
		objectMap.put("color", bean.getColor());
		objectMap.put("old_color", bean.getOld_color());
		objectMap.put("create_time", bean.getCreate_time());

		WhiteListMngBean whiteListMngBean = super.findObj(ns("findWhiteListForEdit"), objectMap);
		if(whiteListMngBean == null)
		{
			return "不存在此白名单！";
		}

		objectMap.put("vehicle_class", bean.getVehicle_class());
		objectMap.put("toll_type", bean.getToll_type());
		objectMap.put("charge_code", bean.getCharge_code());
		objectMap.put("user_name",bean.getUser_name());
		objectMap.put("gender",bean.getGender());
		objectMap.put("user_number",bean.getUser_number());
		objectMap.put("phone",bean.getPhone());
		
		objectMap.put("s_comment",bean.getS_comment());
		objectMap.put("spare1",bean.getSpare1());
		// 修改开始时间和结束时间
		objectMap.put("valid_start_time", bean.getValid_start_time());
		objectMap.put("valid_end_time", bean.getValid_end_time());
//		String parkAreaLane = addLaneInfo(area_ids);
//		objectMap.put("lane_info",parkAreaLane);

		int count = super.update(ns("updateWhiteListPersonInfo"), objectMap);
		logger.info("用户"+userId+"修改白名单车牌"+bean.getMv_license());
		if(count <= 0)
		{
			return "修改失败";
		}
		
		//******************************已经生效的白名单的修改的一系列逻辑判断处理************************begin
//方案一：通用流程图解决所有可能性，通用流程图联系作者查看		if(count > 0 ){
//			//如果是已经生效的白名单进入如下处理
//			if(bean.getStatus() == 3 || bean.getStatus() == 5){
//				boolean judgeMvlicenst = !bean.getMv_license().equals(bean.getOld_mv_license());
//				boolean judgeTolltype = bean.getToll_type() != bean.getOld_toll_type();
//				boolean judgeUsernumber = !bean.getUser_number().equals(bean.getOld_user_number());
//				boolean judgeSpare1 = bean.getSpare1() != bean.getOld_spare1();
//				//如果以上四个主要观察字段有任意一对做修改了
//				if(judgeMvlicenst || judgeTolltype || judgeUsernumber || judgeSpare1){
//					
//					//一、判断原车牌是否在temp_cost中
//					if(findTempCostCountByMvlicense(bean.getOld_mv_license()) > 0){
//						//判断原收费形式
//						if(bean.getOld_toll_type() != 1){
//							//如果修改了车牌
//							if(judgeMvlicenst){
//								updateTempCostSp1AndSp4ByMvlicense(bean.getOld_mv_license(),-1,"");
//							}else{
//								updateTempCostSp1AndSp4ByMvlicense(bean.getOld_mv_license(),-1,bean.getUser_number());
//							}
//							
//						}else{
//							updateTempCostSp1AndSp4ByMvlicense(bean.getOld_mv_license(),-1,"");
//						}
//						
//					}else{
//						updateTempCostSp1AndSp4ByMvlicense(bean.getOld_mv_license(),-1,"");
//					}
//					
//					
//					//二、判断新车牌是否在temp_cost中
//					if(findTempCostCountByMvlicense(bean.getMv_license()) > 0){
//						//判断新收费形式
//						if(bean.getToll_type() != 1){
//							updateTempCostSp1AndSp4ByMvlicense(bean.getMv_license(),-1,bean.getUser_number());
//						}else{
//							updateTempCostSp1AndSp4ByMvlicense(bean.getMv_license(),-1,"");
//						}
//						
//					}else{
//						updateTempCostSp1AndSp4ByMvlicense(bean.getMv_license(),-1,"");
//					}
//					
//					//三、最后统一执行更新spare1操作,纠正剩余车位数
//					//1.根据原拥有车位数更新原车位编号的sp1
//					
//					// 然后根据车位编号在temp_cost中按照入口时间升序排序(从小到大)查询
//					List<TempCostBean> tempListOld = findTempCostBeanListByUsernumber(bean.getOld_user_number());
//					// 可拥有车位数（总车位数）
//					int sunparksOld = bean.getOld_spare1();
//					for(int i = 0; i < tempListOld.size(); i++){
//						if(sunparksOld > i){
//							updateTempCostSp1AndSp4ByMvlicense(tempListOld.get(i).getMvLicense(), 0, bean.getOld_user_number());
//						}else{
//							updateTempCostSp1AndSp4ByMvlicense(tempListOld.get(i).getMvLicense(), 1, bean.getOld_user_number());
//						}
//					}
//					
//					//2.根据新拥有车位数更新新车位编号的sp1
//					// 然后根据车位编号在temp_cost中按照入口时间升序排序(从小到大)查询
//					List<TempCostBean> tempListNew = findTempCostBeanListByUsernumber(bean.getUser_number());
//					// 可拥有车位数（总车位数）
//					int sunparksNew = bean.getSpare1();
//					for(int i = 0; i < tempListNew.size(); i++){
//						if(sunparksNew > i){
//							updateTempCostSp1AndSp4ByMvlicense(tempListNew.get(i).getMvLicense(), 0, bean.getUser_number());
//						}else{
//							updateTempCostSp1AndSp4ByMvlicense(tempListNew.get(i).getMvLicense(), 1, bean.getUser_number());
//						}
//					}
//					
//					//3.纠正剩余车位数
//					//3.1纠正原车位编号的剩余车位数
//					//在库数量
//					int incostOld = findTempCostCountByUsernumber(bean.getOld_user_number());
//					//应该剩余车位
//					Integer nowcanuseOld = bean.getOld_spare1() - incostOld;
//					//根据用户编号更新剩余车位数
//					 updateSp1AndSp2ByUsernumber(bean.getOld_user_number(),bean.getOld_spare1(),nowcanuseOld);
//					
//					//3.2纠正新车位编号的剩余车位数
//					//在库数量
//					int incostNew = findTempCostCountByUsernumber(bean.getUser_number());
//					//应该剩余车位
//					Integer nowcanuseNew = bean.getSpare1() - incostNew;
//					//根据用户编号更新剩余车位数
//					 updateSp1AndSp2ByUsernumber(bean.getUser_number(),bean.getSpare1(),nowcanuseNew);
//					
//					
//				}
//			}
//			
//		}
		
		//方案二：单独修改 车牌、收费形式、车位编号、可拥有车位数的情况 用到的方法拎出来，汇总起来，判断可能  组合使用方法
		if(count > 0){
			//如果是已经生效的白名单进入如下处理
			if(bean.getStatus() == 3 || bean.getStatus() == 5){
				boolean judgeMvlicenst = !bean.getMv_license().equals(bean.getOld_mv_license());
				boolean judgeTolltype = bean.getToll_type() != bean.getOld_toll_type();
				boolean judgeUsernumber = !bean.getUser_number().equals(bean.getOld_user_number());
				boolean judgeSpare1 = bean.getSpare1() != bean.getOld_spare1();
				//如果以上四个主要观察字段有任意一对做修改了
				if(judgeMvlicenst || judgeTolltype || judgeUsernumber || judgeSpare1){
					//如果修改了车牌
					if(judgeMvlicenst){
						//根据原车牌清空spare4
						updateTempCostSp1AndSp4ByMvlicense(bean.getOld_mv_license(),-1,"");
					}
					//如果新收费形式为收费
					if(bean.getToll_type() != 1 ){
						//根据新车牌更新spare4为新车位编号
						updateTempCostSp1AndSp4ByMvlicense(bean.getMv_license(),-1,bean.getUser_number());
					}
					//如果新收费形式为免费
					if(bean.getToll_type() == 1 ){
						//根据新车牌清空spare4
						updateTempCostSp1AndSp4ByMvlicense(bean.getMv_license(),-1,"");
					}
					
				//调整原车位编号和新车位编号的在temp_cost中的spare1的值
					//最后统一执行更新spare1操作,纠正剩余车位数
					//1.原车位编号 新拥有车位数
					
					// 然后根据车位编号在temp_cost中按照入口时间升序排序(从小到大)查询
					List<TempCostBean> tempListOld = findTempCostBeanListByUsernumber(bean.getOld_user_number());
					// 可拥有车位数（总车位数）
					int sunparksOld = bean.getSpare1();
					for(int i = 0; i < tempListOld.size(); i++){
						if(sunparksOld > i){
							updateTempCostSp1AndSp4ByMvlicense(tempListOld.get(i).getMvLicense(), 0, bean.getOld_user_number());
						}else{
							updateTempCostSp1AndSp4ByMvlicense(tempListOld.get(i).getMvLicense(), 1, bean.getOld_user_number());
						}
					}
					
					//2.新车位编号 新拥有车位数
					// 然后根据车位编号在temp_cost中按照入口时间升序排序(从小到大)查询
					List<TempCostBean> tempListNew = findTempCostBeanListByUsernumber(bean.getUser_number());
					// 可拥有车位数（总车位数）
					int sunparksNew = bean.getSpare1();
					for(int i = 0; i < tempListNew.size(); i++){
						if(sunparksNew > i){
							updateTempCostSp1AndSp4ByMvlicense(tempListNew.get(i).getMvLicense(), 0, bean.getUser_number());
						}else{
							updateTempCostSp1AndSp4ByMvlicense(tempListNew.get(i).getMvLicense(), 1, bean.getUser_number());
						}
					}
					
					//3.纠正剩余车位数
					//3.1纠正原车位编号的剩余车位数
					//在库数量
					int incostOld = findTempCostCountByUsernumber(bean.getOld_user_number());
					//应该剩余车位
					Integer nowcanuseOld = bean.getOld_spare1() - incostOld;
					//根据用户编号更新剩余车位数
					 updateSp1AndSp2ByUsernumber(bean.getOld_user_number(),bean.getOld_spare1(),nowcanuseOld);
					
					//3.2纠正新车位编号的剩余车位数
					//在库数量
					int incostNew = findTempCostCountByUsernumber(bean.getUser_number());
					//应该剩余车位
					Integer nowcanuseNew = bean.getSpare1() - incostNew;
					//根据用户编号更新剩余车位数
					 updateSp1AndSp2ByUsernumber(bean.getUser_number(),bean.getSpare1(),nowcanuseNew);

				}
				
				
			}
		}
		
		
		
		//******************************已经生效的白名单的修改的一系列逻辑判断处理************************end
		
		return "success";
	}

	@Override
	public List<WhiteListMngBean> findWhiteListOfMvLicense(String mv_license) {
		ObjectMap queryParam = ObjectMap.newInstance();
		queryParam.put("mv_license", mv_license);
		List<WhiteListMngBean> list = super.findList(ns("findWhiteListOfMvLicense"), queryParam);
		return list;
	}

	@Override
	public Integer findTempCostCountByMvlicense(String mv_license) {
		return super.findInteger(ns("findTempCostCountByMvlicense"), mv_license);
	}

	@Override
	public Integer findTempCostCountByUsernumber(String usernumber) {
		return super.findInteger(ns("findTempCostCountByUsernumber"), usernumber);
	}

	@Override
	public String updateSp1AndSp2ByUsernumber(String usernumber, int sumparks, int remainparks) {
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("usernumber", usernumber);
		objectMap.put("sumparks", sumparks);
		objectMap.put("remainparks", remainparks);

		int count = super.update(ns("updateSp1AndSp2ByUsernumber"), objectMap);
		if(count > 0){
			return "success";
		}
		return "error";
	}

	@Override
	public String updateTempCostSp1AndSp4ByMvlicense(String mvlicense, int sp1, String sp4) {
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("mvlicense", mvlicense);
		if(sp1 != -1){
			objectMap.put("sp1", sp1);
		}
		objectMap.put("sp4", sp4);
		
		int count = super.update(ns("updateTempCostSp1AndSp4ByMvlicense"), objectMap);
		if(count > 0){
			return "success";
		}
		return "error";
		
	}

	@Override
	public List<TempCostBean> findTempCostBeanByMvlicense(String mvlicense) {
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("mvlicense", mvlicense);
		List<TempCostBean> list = super.findList(ns("findTempCostBeanByMvlicense"), objectMap);
		return list;
	}

	@Override
	public List<TempCostBean> findTempCostBeanListByUsernumber(String usernumber) {
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("usernumber", usernumber);
		List<TempCostBean> list = super.findList(ns("findTempCostBeanListByUsernumber"), objectMap);
		return list;
	}
	
	@Transactional
	@Override
	public String updateTempcostSp1ByUsernumber(String usernumber, int sumparks) {
		List<TempCostBean> tempcostList = findTempCostBeanListByUsernumber(usernumber);
		for(int i = 0; i < tempcostList.size(); i++){
			if(sumparks > i){
				updateTempCostSp1AndSp4ByMvlicense(tempcostList.get(i).getMvLicense(), 0, usernumber);
			}else{
				updateTempCostSp1AndSp4ByMvlicense(tempcostList.get(i).getMvLicense(), 1, usernumber);
			}
		}
		return "success";
		
	}
	
	@Transactional
	@Override
	public String updateTempcostSp4ByMvlicense(String mvlicense, String usernumber) {
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("mvlicense", mvlicense);
		objectMap.put("usernumber", usernumber);
		int count = super.update(ns("updateTempcostSp4ByMvlicense"), objectMap);
		if(count <= 0){
			return "error";
		}
		return "success";
	}

	
	
	
	
//**************************************其它方法*************************************begin
	
	/**
	 * 白名单申请审核通过一系列逻辑处理
	* @Title: whiteListStatue1DoOk  
	* @Description: TODO方法作用描述：  
	* @param @param userNumber
	* @param @param tollType
	* @param @param mvLicense
	* @param @param spare1
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws
	 */
	public boolean whiteListStatue1DoOk(String userNumber,int tollType,String mvLicense,int spare1){
		// 1：保证 车位编号——可拥有车位数——剩余车位数 三者必须一致
		// 2：开始进行temp_cost表的处理

		// 该车位编号的在temp_cost中的车位数量.......
		int incostcount = 0;

		List<WhiteListMngBean> havedWhiteList = findWhiteListOfUserNumber(userNumber);
		if (havedWhiteList.size() > 0) {

			// 判断新增的白名单收费形式 如果是收费
			if (tollType != 1) {

				// 判断该车牌是否在temp_cost中
				int mvlicensecount = findTempCostCountByMvlicense(mvLicense);
				if (mvlicensecount > 0) {// 在temp_cost中
					// 先在temp_cost中将该车牌对应的spare4改为当前的车位编号
					updateTempCostSp1AndSp4ByMvlicense(mvLicense, -1, userNumber);
					
				} else {// 不在temp_cost中
						// incostcount = 0;

				}
				
				// 然后根据车位编号在temp_cost中按照入口时间升序排序(从小到大)查询
				List<TempCostBean> tempList = findTempCostBeanListByUsernumber(userNumber);
				// 可拥有车位数（总车位数）
				int sunparks = spare1;
				for(int i = 0; i < tempList.size(); i++){
					if(sunparks > i){
						updateTempCostSp1AndSp4ByMvlicense(tempList.get(i).getMvLicense(), 0, userNumber);
					}else{
						updateTempCostSp1AndSp4ByMvlicense(tempList.get(i).getMvLicense(), 1, userNumber);
					}
				}
				
			}

			// 该车位编号对应的在temp_cost中的数量（在库数量）
			incostcount = findTempCostCountByUsernumber(userNumber);
			// 根据车位编号更新可拥有车位数和剩余车位数 剩余车位数=总车位数-在库数量
			int remainparks = spare1 - incostcount;
			String updateresult = updateSp1AndSp2ByUsernumber(userNumber, spare1, remainparks);
			if (!updateresult.equals("success")) {
				return false;
			}
		} else {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 已经生效的白名单删除操作的一系列逻辑处理
	* @Title: whiteListStatue5DoOk  
	* @Description: TODO方法作用描述：  
	* @param @param mvLicense
	* @param @param tollType
	* @param @param userNumber
	* @param @param spare1
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws
	 */
	public boolean whiteListStatue5DoOk(String mvLicense,int tollType,String userNumber,int spare1){
		//判断删除的白名单的收费形式 如果为收费
		if(tollType != 1){
			int incostcount = 0;
			//判断是否在temp_cost中
			int mvlicensecount = findTempCostCountByMvlicense(mvLicense);
			if(mvlicensecount > 0){
				updateTempCostSp1AndSp4ByMvlicense(mvLicense, -1, "");
				
				
				// 然后根据车位编号在temp_cost中按照入口时间升序排序(从小到大)查询
				List<TempCostBean> tempList = findTempCostBeanListByUsernumber(userNumber);
				// 可拥有车位数（总车位数）
				int sunparks = spare1;
				for(int i = 0; i < tempList.size(); i++){
					if(sunparks > i){
						updateTempCostSp1AndSp4ByMvlicense(tempList.get(i).getMvLicense(), 0, userNumber);
					}else{
						updateTempCostSp1AndSp4ByMvlicense(tempList.get(i).getMvLicense(), 1, userNumber);
					}
				}
				
				//该车位编号对应的在temp_cost中的数量（在库数量）
				incostcount = findTempCostCountByUsernumber(userNumber);
				//根据车位编号更新可拥有车位数和剩余车位数  剩余车位数=总车位数-在库数量
				int remainparks = spare1 - incostcount ;
				String updateresult = updateSp1AndSp2ByUsernumber(userNumber,spare1, remainparks);
				if(!updateresult.equals("success")){
					return false;
				}
			}
		}
		return true;
	}

	
//**************************************其它方法*************************************end
	

}
