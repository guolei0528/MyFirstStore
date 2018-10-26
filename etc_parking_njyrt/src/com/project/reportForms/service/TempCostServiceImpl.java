package com.project.reportForms.service;

import java.util.List;

import com.project.reportForms.model.ParkingSpaceInfo;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.mybatis.service.BaseService;

public class TempCostServiceImpl extends BaseService implements TempCostService {

	public TempCostServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("reportForms.tempcost");
	}

	@Override
	public List<ParkingSpaceInfo> getParkingSpaceInfo(String parkid) {
		ObjectMap map = ObjectMap.newInstance();
		map.put("parkid", parkid);
		List<ParkingSpaceInfo> list = super.findList(ns("getParkingSpaceInfo"),map);
		for(ParkingSpaceInfo space:list){
			int leftNum = space.getTotalNum()-space.getUsedNum();
			space.setLeftNum(leftNum);
		}
		return list;
	}

	@Override
	public List<String> getLicensesByDay(ObjectMap param) {
		List<String> list = super.findList(ns("getLicensesByDay"), param);
		return list;
	}

	@Override
	public int countFlow(ObjectMap param) {
		return super.findInteger(ns("countFlow"), param);
	}

}
