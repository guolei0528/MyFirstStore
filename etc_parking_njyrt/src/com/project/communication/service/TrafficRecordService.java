package com.project.communication.service;

import java.util.List;

import com.project.backMng.platuser.CareTrafficRecordMng.model.CareTrafficRecordBean;
import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;
import com.project.communication.model.IncomeCountBean;

public interface TrafficRecordService {
	public void update(CareTrafficRecordBean bean);
	public void insert(CareTrafficRecordBean bean);
	public List<CareTrafficRecordBean> findList(CareTrafficRecordBean bean);
	public LaneInfoBean findLaneInfo(String lane_id);
	public String findMemberType(String mv_license, Integer car_color);
	public void updateTrafficRecord(CareTrafficRecordBean bean);
	public void updateIncomeCount(IncomeCountBean bean);
}
