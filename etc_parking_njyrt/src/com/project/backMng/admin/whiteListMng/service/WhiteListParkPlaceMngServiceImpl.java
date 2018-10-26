package com.project.backMng.admin.whiteListMng.service;

import java.util.List;
import java.util.Map;

import com.project.backCharge.tempCharge.model.TempCostBean;
import com.project.backMng.admin.whiteListMng.model.WhiteListMngBean;
import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

public class WhiteListParkPlaceMngServiceImpl extends BaseService implements WhiteListParkPlaceMngService{
	
	public WhiteListParkPlaceMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.whiteList.WhiteListParkPlaceMng");
	}
	
	/**
	 * 
	   * @Title : findList 
	   * @功能描述: 查找列表
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<WhiteListMngBean> findList(ObjectMap queryParam, Page page) {
		page.setRecordCount(super.findInteger(ns("findCount"), queryParam));
		List<WhiteListMngBean> list = super.findList(ns("findList"), queryParam, page);
		return list;
	}

	@Override
	public String outcost(ObjectMap objectMap) {
		super.delete(ns("outcost"), objectMap);
		return "success";
	}

	@Override
	public List<LaneInfoBean> findInLaneInfoList() {
		return super.findList(ns("findInLaneInfoList"));
	}

	@Override
	public LaneInfoBean findInLaneObj(ObjectMap queryParam) {
		return  super.findObj(ns("findInLaneObj"), queryParam);
	}

	@Override
	public String incost(LaneInfoBean laneinfo, Map map) {
		
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("park_id", laneinfo.getpark_id());
		objectMap.put("area_id", laneinfo.getarea_id());
		objectMap.put("entry_lane", map.get("entry_lane"));
		objectMap.put("entry_time", map.get("entry_time"));
		objectMap.put("entry_date", map.get("entrydateInte"));
		objectMap.put("mv_license", map.get("mv_license"));
		objectMap.put("usernumber", map.get("usernumber"));
		super.insert(ns("incost"), objectMap);
		return "success";
	}

	@Override
	public List<WhiteListMngBean> findUserParksList(ObjectMap queryParam) {
		List<WhiteListMngBean> list = super.findList(ns("findUserParksList"), queryParam);
		return list;
	}

	@Override
	public Integer getIncostNum(String usernumber) {
		return super.findInteger(ns("getIncostNum"), usernumber);
	}

	@Override 
	public String updateCanUseParks(Integer canuseparks,String usernumber) {
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("canuseparks", canuseparks);
		objectMap.put("usernumber", usernumber);
		
		super.update(ns("updateCanUseParks"), objectMap);
		return "success";
	}

	@Override
	public Integer getIncostNumBySp4(String usernumber) {
		return super.findInteger(ns("getIncostNumBySp4"), usernumber);
	}

	@Override
	public List<TempCostBean> findTempCostBeanListByUsernumber(String usernumber) {
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("usernumber", usernumber);
		List<TempCostBean> list = super.findList(ns("findTempCostBeanListByUsernumber"), objectMap);
		return list;
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

	

	
	
}
