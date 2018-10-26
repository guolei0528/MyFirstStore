package com.project.backMng.admin.whiteListMng.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.project.backCharge.tempCharge.model.TempCostBean;
import com.project.backMng.admin.whiteListMng.model.WhiteListMngBean;
import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface WhiteListMngService {

	public List<WhiteListMngBean> findList(ObjectMap queryParam,Page page);
	
	
	/**
	 * 
	   * @Title : findObj 
	   * @功能描述: TODO
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回类型：WhiteListMngBean 
	   * @throws ：
	 */
	public WhiteListMngBean findObj(ObjectMap queryParam);
	
	
	/**
	 * 
	   * @Title : comfirmFindList 
	   * @功能描述: 审批查询
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回类型：List<WhiteListMngBean> 
	   * @throws ：
	 */
	public List<WhiteListMngBean> comfirmFindList(ObjectMap queryParam,Page page);
	
	
	/**
	 * 
	   * @Title : save 
	   * @功能描述: 保存白名单，草稿状态
	   * @传入参数：@param bean
	   * @传入参数：@param userId
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	public String save(WhiteListMngBean bean,String userId);
	
	
	/**
	 * 
	   * @Title : save 
	   * @功能描述: 保存白名单，草稿状态
	   * @传入参数：@param bean
	   * @传入参数：@param userId
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	@Transactional
	public boolean insertList(List<WhiteListMngBean> list,String userId);
	
	
	public String comfirmSave(WhiteListMngBean bean,String userId);
	/**
	 * 
	   * @Title : submit 
	   * @功能描述: 提交白名单
	   * @传入参数：@param bean
	   * @传入参数：@param userId
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	public String submit(WhiteListMngBean bean,String userId,String area_ids);
	
	/**
	 * 
	* @Title: submitOfNoAreaids  
	* @Description: TODO方法作用描述：新增白名单，没有区域的版本  
	* @param @param bean
	* @param @param userId
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public String submitOfNoAreaids(WhiteListMngBean bean,String userId,String area_ids);
	
	
	/**
	 * 
	   * @Title : sumbitVerify 
	   * @功能描述: 提交白名单状态为审核中
	   * @传入参数：@param bean
	   * @传入参数：@param userId
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	public boolean sumbitVerify(WhiteListMngBean bean,String userId);
	
	/**
	 * 
	   * @Title : comfirmVerify 
	   * @功能描述: 审核白名单状态为审核中
	   * @传入参数：@param bean
	   * @传入参数：@param userId
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	public boolean comfirmVerify(WhiteListMngBean bean,String userId,String type);
	
	
	
	/**
	 * 
	   * @Title : comfirmVerify 
	   * @功能描述: 审核白名单状态为审核中
	   * @传入参数：@param bean
	   * @传入参数：@param userId
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	public boolean comfirmVerifyList(String[] whiteList,String userId,String type);
	
	/**
	 * 
	   * @Title : update 
	   * @功能描述: 更新白名单数据
	   * @传入参数：@param bean
	   * @传入参数：@param userId
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	public String update(WhiteListMngBean bean,String userId,String area_ids);
	
	/**
	 *  更新白名单数据 没有区域编号版本
	* @Title: updateOfNoAreaids  
	* @Description: TODO方法作用描述：  
	* @param @param bean
	* @param @param userId
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public String updateOfNoAreaids(WhiteListMngBean bean,String userId);
	
	
	public String delete(ObjectMap objectMap);
	
	/**
	 * 
	   * @Title : deleteStatus 
	   * @功能描述: 逻辑删除白名单
	   * @传入参数：@param objectMap
	   * @返回类型：void 
	   * @throws ：
	 */
	public void deleteStatus(ObjectMap objectMap);
	
	
	/**
	 * 白名单作废申请
	   * @Title : invalid 
	   * @功能描述: TODO
	   * @传入参数：@param objectMap
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	public String invalidApply(ObjectMap objectMap,String userId);


	/**
	 * 
	   * @Title : updatePersonInfo 
	   * @功能描述: 修改非必填字段，人员信息
	   * @传入参数：@param bean
	   * @传入参数：@param userId
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	public String updatePersonInfo(WhiteListMngBean bean, String userId,String area_ids);
	/**
	 * 白名单维护--修改  没有区域的版本
	* @Title: updatePersonInfoOfNoAreaids  
	* @Description: TODO方法作用描述：  
	* @param @param bean
	* @param @param userId
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public String updatePersonInfoOfNoAreaids(WhiteListMngBean bean, String userId);
	
	
	/**
	 * 
	   * @Title : findAreaList 
	   * @功能描述: TODO
	   * @传入参数：@return
	   * @返回类型：List<AreaInfoBean> 
	   * @throws ：
	 */
	public List<AreaInfoBean> findAreaList();
	
	
	
	/**
	 * 
	   * @Title : findList 
	   * @功能描述: 获取车道信息
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回类型：List<LaneInfoBean> 
	   * @throws ：
	 */
	public List<LaneInfoBean> findLaneList();
	
	/**
	 * 根据用户工号查询用户信息  
	* @Title: findWhiteListOfUserNumber  
	* @Description: TODO方法作用描述：  
	* @param @param usernumber
	* @param @return    参数  
	* @return List<WhiteListMngBean>    返回类型  
	* @throws
	 */
	public List<WhiteListMngBean> findWhiteListOfUserNumber(String usernumber);
	
	/**
	 * 根据车牌查询在库唯一性
	* @Title: findWhiteListOfMvLicense  
	* @Description: TODO方法作用描述：  
	* @param @param mv_license
	* @param @return    参数  
	* @return List<WhiteListMngBean>    返回类型  
	* @throws
	 */
	public List<WhiteListMngBean> findWhiteListOfMvLicense(String mv_license);
	
	
	
	/**
	 * 根据用户联系方式查询用户信息 主要确保车主的可拥有车位数和联系方式一致 
	* @Title: findWhiteListOfUserNumber  
	* @Description: TODO方法作用描述：  
	* @param @param usernumber
	* @param @return    参数  
	* @return List<WhiteListMngBean>    返回类型  
	* @throws
	 */
	public List<WhiteListMngBean> findWhiteListOfPhone(String phone);
	
	/**
	 * 根据车主工号或者联系方式查询
	* @Title: findWhiteListOfUserNumberAndPhone  
	* @Description: TODO方法作用描述：  
	* @param @param usernumber
	* @param @param phone
	* @param @return    参数  
	* @return List<WhiteListMngBean>    返回类型  
	* @throws
	 */
	public List<WhiteListMngBean> findWhiteListOfUserNumberAndPhone(String usernumber,String phone);
	
	/**
	 * 根据车牌查询在temp_cost
	* @Title: findTempCostCountByMvlicense  
	* @Description: TODO方法作用描述：  
	* @param @return    参数  
	* @return Integer    返回类型  
	* @throws
	 */
	public Integer findTempCostCountByMvlicense(String mv_license);
	
	/**
	 * 根据业主编号查询在计费表中的所有记录
	* @Title: findTempCostCountByUsernumber  
	* @Description: TODO方法作用描述：  
	* @param @param usernumber
	* @param @return    参数  
	* @return Integer    返回类型  
	* @throws
	 */
	public Integer findTempCostCountByUsernumber(String usernumber);
	
	/**
	 * 根据车位编号更新可拥有车位数和剩余车位数
	* @Title: updatSp1AndSp2ByUsernumber  
	* @Description: TODO方法作用描述：  
	* @param @param usernumber
	* @param @param sumparks
	* @param @param remainparks
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public String updateSp1AndSp2ByUsernumber(String usernumber,int sumparks,int remainparks);
	
	/**
	 * 根据车牌更新temp_cost中的计费状态和车位编号
	* @Title: updateTempCostSp1AndSp4ByMvlicense  
	* @Description: TODO方法作用描述：  
	* @param @param mvlicense
	* @param @param sp1
	* @param @param sp4
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public String updateTempCostSp1AndSp4ByMvlicense(String mvlicense,int sp1,String sp4);
	
	/**
	 * 根据车牌查询在temp_cost中的信息
	* @Title: findTempCostBeanByMvlicense  
	* @Description: TODO方法作用描述：  
	* @param @param mvlicense
	* @param @return    参数  
	* @return List<TempCostBean>    返回类型  
	* @throws
	 */
	public List<TempCostBean> findTempCostBeanByMvlicense(String mvlicense);
	
	/**
	 * 根据车位编号在temp_cost中查询信息按照入口时间降序排（从小到大）
	* @Title: findTempCostBeanByUsernumber  
	* @Description: TODO方法作用描述：  
	* @param @param usernumber
	* @param @return    参数  
	* @return List<TempCostBean>    返回类型  
	* @throws
	 */
	public List<TempCostBean> findTempCostBeanListByUsernumber(String usernumber);
	
	/**
	 * 根据车位编号在temp_cost中更新该车位编号的spare1
	* @Title: updateTempcostSp1ByUsernumber  
	* @Description: TODO方法作用描述：  
	* @param @param usernumber
	* @param @param sumparks
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public String updateTempcostSp1ByUsernumber(String usernumber,int sumparks);
	
	/**
	 * 在temp_cost中更新该车牌对应的spare4的值（车位编号）
	* @Title: writeTempcostSp4ByMvlicense  
	* @Description: TODO方法作用描述：  
	* @param @param mvlicense
	* @param @param usernumber
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public String updateTempcostSp4ByMvlicense(String mvlicense,String usernumber);
	
}
