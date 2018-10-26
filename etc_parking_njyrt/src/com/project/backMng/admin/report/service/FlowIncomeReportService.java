package com.project.backMng.admin.report.service;

import java.util.Date;
import java.util.List;

import com.project.backMng.admin.report.model.FlowIncomeBaseBean;
import com.project.backMng.admin.report.model.FlowIncomeReportBean;
import com.project.backMng.admin.report.model.FlowLineChartBean;
import com.project.backMng.admin.report.model.ParkRunDetailBean;
import com.project.backMng.admin.report.model.StockReportBean;
import com.project.backMng.admin.report.model.TollLineChartBean;
import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;
import com.project.backMng.platuser.sys.ParkInfoMng.model.ParkInfoBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface FlowIncomeReportService {

	public List<FlowIncomeReportBean> findList(ObjectMap queryParam,Page page);
	
	public List<ParkInfoBean> findParkInfoList();
	
	public List<AreaInfoBean> findAreaInfoList();

	public List<LaneInfoBean> findLaneInfoList();
	
	public List<TollLineChartBean> findTollReport(int type,Date startTime,Date endTime);

	public List<FlowLineChartBean> findEntryFlowReport(int type, Date startTime, Date endTime);

	public List<FlowLineChartBean> findExitFlowReport(int type, Date startTime, Date endTime);
	
	public FlowIncomeBaseBean todayBaseInfo();
	
	public FlowIncomeBaseBean yesterdayBaseInfo();
	
	public int countFailRecognitionByToday();
	
	public StockReportBean findParkStock();
	
	public List<ParkRunDetailBean> findParkRunDetail(ObjectMap queryParam);
}
