package com.project.backMng.admin.whiteListMng.service;

import java.util.List;
import java.util.Map;

import com.project.backCharge.tempCharge.model.TempCostBean;
import com.project.backMng.admin.whiteListMng.model.WhiteListMngBean;
import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface WhiteListParkPlaceMngService {

	
	/**
	 * 
	* @Title: findList  
	* @Description: TODO方法作用描述：  业主剩余车位维护查询LIST
	* @param @param queryParam
	* @param @param page
	* @param @return    参数  
	* @return List<WhiteListMngBean>    返回类型  
	* @throws
	 */
	public List<WhiteListMngBean> findList(ObjectMap queryParam,Page page);
	
	/**
	 * 查询所有入口车道信息
	* @Title: findInLaneInfoList  
	* @Description: TODO方法作用描述：  
	* @param @return    参数  
	* @return List<LaneInfoBean>    返回类型  
	* @throws
	 */
	public List<LaneInfoBean> findInLaneInfoList();
	
	/**
	 * 根据入口车道编号查询入口车道信息
	* @Title: findInLaneObj  
	* @Description: TODO方法作用描述：  
	* @param @param queryParam
	* @param @return    参数  
	* @return LaneInfoBean    返回类型  
	* @throws
	 */
	public LaneInfoBean findInLaneObj(ObjectMap queryParam);
	
	/**
	 * 
	* @Title: incost  
	* @Description: TODO方法作用描述：入库操作  
	* @param @param laneinfo
	* @param @param map
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public String incost(LaneInfoBean laneinfo,Map map );

	/**
	 * 
	* @Title: outcost  
	* @Description: TODO方法作用描述：  根据车牌号进行清库操作
	* @param @param objectMap
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public String outcost(ObjectMap objectMap);
	
	/**
	 * 根据车主编号查询该车主下的 车牌信息
	* @Title: findUserParksList  
	* @Description: TODO方法作用描述：  
	* @param @param queryParam
	* @param @return    参数  
	* @return List<WhiteListMngBean>    返回类型  
	* @throws
	 */
	public List<WhiteListMngBean> findUserParksList(ObjectMap queryParam);
	
	/**
	 * 根据用户编号查询该用户所有车辆在库数量
	* @Title: getIncostNum  
	* @Description: TODO方法作用描述：  
	* @param @param usernumber
	* @param @return    参数  
	* @return Integer    返回类型  
	* @throws
	 */
	public Integer getIncostNum(String usernumber);
	
	/**
	 * 在tem_cost表中根据用户编号字段spare4 查询该用户在库车辆数量
	* @Title: getIncostNumBySp4  
	* @Description: TODO方法作用描述：  
	* @param @param usernumber
	* @param @return    参数  
	* @return Integer    返回类型  
	* @throws
	 */
	public Integer getIncostNumBySp4(String usernumber);
	
	/**
	 * 根据用户编号更新剩余车位数
	* @Title: updateCanUseParks  
	* @Description: TODO方法作用描述：  
	* @param @param canuseparks
	* @param @param usernumber
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public String updateCanUseParks(Integer canuseparks,String usernumber);
	
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
}
