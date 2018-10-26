package com.project.backMng.admin.report.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.project.backMng.admin.report.model.FlowReportBean;
import com.project.backMng.admin.report.model.FlowReportTempBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

public class FlowReportServiceImpl extends BaseService implements FlowReportService {

	// 初始化小车数量
	private final static int COUNT_INIT = 0;

	private final static Logger log = LogManager.getLogger(FlowReportServiceImpl.class);

	public FlowReportServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.report.FlowReport");
		// TODO Auto-generated constructor stub
	}
	
	@Transactional
	@Override
	public List<FlowReportBean> findList(ObjectMap queryParam, Page page) {
		
//		super.delete(ns("deleteFlowReport"),null);
		int entryCount = super.findInteger(ns("findCountFlowReport"), queryParam);
		int exitCount = super.findInteger(ns("findCountExitFlowReport"), queryParam);
		page.setRecordCount(entryCount+exitCount);
		page.setPageSize(15);
		List<FlowReportBean> entryFlowReport = entryFlowReportFindList(queryParam);
		List<FlowReportBean> exitFlowReport = exitFlowReportFindList(queryParam);
		entryFlowReport.addAll(exitFlowReport);
		
		List<FlowReportBean> flowReport = new ArrayList<FlowReportBean>();
		int currentNum = page.getCurrentPage()-1;
		if(entryFlowReport.size()==0)
		{
			return flowReport;
		}
		for(int i = 0;i<page.getPageSize()
				&&(currentNum*15+i)<entryFlowReport.size();i++)
		{
			flowReport.add(entryFlowReport.get(currentNum*15+i));
		}
		return flowReport;
	}
	
	
	/**
	 * 
	   * @Title : entryFlowReportFindList 
	   * @功能描述: 统计入口流量报表
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回类型：List<FlowReportBean> 
	   * @throws ：
	 */
	private List<FlowReportBean> entryFlowReportFindList(ObjectMap queryParam)
	{
//		page.setRecordCount(super.findInteger(ns("findCountFlowReport"), queryParam));
		List<FlowReportBean> entryFlowReportListMain = super.findList(ns("findListFlowReport"), queryParam);
		
		List<FlowReportTempBean> flowReportListDetail = super.findList(ns("findListFlowReportDetail"), queryParam);

		for (FlowReportBean flowReportMain : entryFlowReportListMain) {

			//小车ETC初始值
			flowReportMain.setCarETC(COUNT_INIT);
			// 小车储值初始值
//			flowReportMain.setCarStoreValue(COUNT_INIT);
			// 小车记账初始值
//			flowReportMain.setCarAccount(COUNT_INIT);
			// 小车现金初始值
			flowReportMain.setCarCash(COUNT_INIT);
			// 小车移动初始值
			flowReportMain.setCarMobile(COUNT_INIT);
			//大车ETC初始值
			flowReportMain.setCartETC(COUNT_INIT);
			// 大车储值初始值
//			flowReportMain.setCartStoreValue(COUNT_INIT);
			// 大车记账初始值
//			flowReportMain.setCartAccount(COUNT_INIT);
			// 大车现金初始值
			flowReportMain.setCartCash(COUNT_INIT);
			// 大车移动初始值
			flowReportMain.setCartMobile(COUNT_INIT);

			//小车人工放行
			flowReportMain.setCarArtificial(COUNT_INIT);
			//大车人工放行
			flowReportMain.setCartArtificial(COUNT_INIT);
			for (FlowReportTempBean flowReportDetail : flowReportListDetail) {
				if (flowReportMain.getDate() == flowReportDetail.getDate()
						&& flowReportMain.getLaneId() == flowReportDetail.getLaneId()) {
					// 判断大车还是小车
					if (flowReportDetail.getVehicleclass() == 1) {
						// 判断小车储值
//						if (flowReportDetail.getPaymethod() == 2 && flowReportDetail.getEcardtype() == 22) {
//							flowReportMain.setCarStoreValue(flowReportDetail.getCountFlow());
//							continue;
//						}

						// 判断小车记账
//						if (flowReportDetail.getPaymethod() == 2 && flowReportDetail.getEcardtype() == 23) {
//							flowReportMain.setCarAccount(flowReportDetail.getCountFlow());
//							continue;
//						}
						// 判断小车ETC
						if (flowReportDetail.getPaymethod() == 2) {
							flowReportMain.setCarETC(flowReportMain.getCarETC()+flowReportDetail.getCountFlow());
							continue;
						}
						// 判断小车现金
						if (flowReportDetail.getPaymethod() == 0) {
							flowReportMain.setCarCash(flowReportMain.getCarCash()+flowReportDetail.getCountFlow());
							continue;
						}

						// 判断小车会员
						// if(flowReportDetail.getPaymethod() == 1)
						// {
						// flowReportMain.setCarMember(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断小车移动
						if (flowReportDetail.getPaymethod() == 3 
								|| flowReportDetail.getPaymethod() == 4 
								|| flowReportDetail.getPaymethod() == 5) {
							flowReportMain.setCarMobile(flowReportMain.getCarMobile()+flowReportDetail.getCountFlow());
							continue;
						}
						
						// 判断小车人工放行
						if (flowReportDetail.getPaymethod() == 9) {
							flowReportMain.setCarArtificial(flowReportMain.getCarArtificial()+flowReportDetail.getCountFlow());
							continue;
						}

					} else if (flowReportDetail.getVehicleclass() == 2) {
						// 判断小车储值
//						if (flowReportDetail.getPaymethod() == 2 && flowReportDetail.getEcardtype() == 22) {
//							flowReportMain.setCartStoreValue(flowReportDetail.getCountFlow());
//							continue;
//						}

						// 判断小车记账
//						if (flowReportDetail.getPaymethod() == 2 && flowReportDetail.getEcardtype() == 23) {
//							flowReportMain.setCartAccount(flowReportDetail.getCountFlow());
//							continue;
//						}
						
					    // 判断大车ETC
						if (flowReportDetail.getPaymethod() == 2) {
							flowReportMain.setCartETC(flowReportMain.getCartETC()+flowReportDetail.getCountFlow());
							continue;
						}
						
						// 判断小车现金
						if (flowReportDetail.getPaymethod() == 0) {
							flowReportMain.setCartCash(flowReportMain.getCartCash()+flowReportDetail.getCountFlow());
							continue;
						}

						// 判断小车会员
						// if(flowReportDetail.getPaymethod() == 1)
						// {
						// flowReportMain.setCartMember(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断大车移动
						if (flowReportDetail.getPaymethod() == 3 
								|| flowReportDetail.getPaymethod() == 4 
								|| flowReportDetail.getPaymethod() == 5) {
							flowReportMain.setCartMobile(flowReportMain.getCartMobile()+flowReportDetail.getCountFlow());
							continue;
						}
						
						// 判断小车现金
						if (flowReportDetail.getPaymethod() == 9) {
							flowReportMain.setCartArtificial(flowReportMain.getCartArtificial()+flowReportDetail.getCountFlow());
							continue;
						}
					}

				}
			}
			// 更新流量表
//			super.insert(ns("insertFlowReport"), flowReportMain);
		}
		
		return entryFlowReportListMain;
	}
	
	
	private List<FlowReportBean> exitFlowReportFindList(ObjectMap queryParam)
	{
//		page.setRecordCount(page.getPageCount() + super.findInteger(ns("findCountExitFlowReport"), queryParam));
		List<FlowReportBean> exitFlowReportListMain = super.findList(ns("findListExitFlowReport"), queryParam);
		List<FlowReportTempBean> flowReportListDetail = super.findList(ns("findListExitFlowReportDetail"), queryParam);

		for (FlowReportBean flowReportMain : exitFlowReportListMain) {

			//小车ETC初始值
			flowReportMain.setCarETC(COUNT_INIT);
			// 小车储值初始值
			flowReportMain.setCarStoreValue(COUNT_INIT);
			// 小车记账初始值
			flowReportMain.setCarAccount(COUNT_INIT);
			// 小车现金初始值
			flowReportMain.setCarCash(COUNT_INIT);
			// 小车移动初始值
			flowReportMain.setCarMobile(COUNT_INIT);
			// 大车储值初始值
			flowReportMain.setCartStoreValue(COUNT_INIT);
			//大车ETC初始值
			flowReportMain.setCartETC(COUNT_INIT);
			// 大车记账初始值
			flowReportMain.setCartAccount(COUNT_INIT);
			// 大车现金初始值
			flowReportMain.setCartCash(COUNT_INIT);
			// 大车移动初始值
			flowReportMain.setCartMobile(COUNT_INIT);

			for (FlowReportTempBean flowReportDetail : flowReportListDetail) {
				if (flowReportMain.getDate() == flowReportDetail.getDate()
						&& flowReportMain.getLaneId() == flowReportDetail.getLaneId()) {
					// 判断大车还是小车
					if (flowReportDetail.getVehicleclass() == 1 || flowReportDetail.getVehicleclass() == 2) {
						// 判断小车储值
//						if (flowReportDetail.getPaymethod() == 2 && flowReportDetail.getEcardtype() == 22) {
//							flowReportMain.setCarStoreValue(flowReportDetail.getCountFlow());
//							continue;
//						}

						// 判断小车记账
//						if (flowReportDetail.getPaymethod() == 2 && flowReportDetail.getEcardtype() == 23) {
//							flowReportMain.setCarAccount(flowReportDetail.getCountFlow());
//							continue;
//						}
						
						
						// 判断小车ETC
						if (flowReportDetail.getPaymethod() == 2) {
							flowReportMain.setCarETC(flowReportMain.getCarETC()+flowReportDetail.getCountFlow());
							continue;
						}
						
						// 判断小车现金
						if (flowReportDetail.getPaymethod() == 0) {
							flowReportMain.setCarCash(flowReportMain.getCarCash()+flowReportDetail.getCountFlow());
							continue;
						}

						// 判断小车会员
						// if(flowReportDetail.getPaymethod() == 1)
						// {
						// flowReportMain.setCarMember(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断小车移动
						if (flowReportDetail.getPaymethod() == 3 
								|| flowReportDetail.getPaymethod() == 4 
								|| flowReportDetail.getPaymethod() == 5) {
							flowReportMain.setCarMobile(flowReportMain.getCarMobile()+flowReportDetail.getCountFlow());
							continue;
						}

					} else if (flowReportDetail.getVehicleclass() == 3 || flowReportDetail.getVehicleclass() == 4) {
						// 判断大车储值
//						if (flowReportDetail.getPaymethod() == 2 && flowReportDetail.getEcardtype() == 22) {
//							flowReportMain.setCartStoreValue(flowReportDetail.getCountFlow());
//							continue;
//						}

						// 判断小车记账
//						if (flowReportDetail.getPaymethod() == 2 && flowReportDetail.getEcardtype() == 23) {
//							flowReportMain.setCartAccount(flowReportDetail.getCountFlow());
//							continue;
//						}
						// 判断大车ETC
						if (flowReportDetail.getPaymethod() == 2) {
							flowReportMain.setCartETC(flowReportMain.getCarETC()+flowReportDetail.getCountFlow());
							continue;
						}
						
						
						// 判断小车现金
						if (flowReportDetail.getPaymethod() == 0) {
							flowReportMain.setCartCash(flowReportMain.getCartCash()+flowReportDetail.getCountFlow());
							continue;
						}
						

						// 判断小车会员
						// if(flowReportDetail.getPaymethod() == 1)
						// {
						// flowReportMain.setCartMember(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断大车移动
						if (flowReportDetail.getPaymethod() == 3 
								|| flowReportDetail.getPaymethod() == 4 
								|| flowReportDetail.getPaymethod() == 5) {
							flowReportMain.setCartMobile(flowReportMain.getCartMobile()+flowReportDetail.getCountFlow());
							continue;
						}
					}

				}
			}
			// 更新流量表
//			super.insert(ns("insertFlowReport"), flowReportMain);
		}
		return exitFlowReportListMain;
	}
	
}
