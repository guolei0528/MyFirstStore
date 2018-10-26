package com.project.backMng.admin.report.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleFactory;
import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleXml;
import com.project.backMng.admin.report.model.FlowIncomeBaseBean;
import com.project.backMng.admin.report.model.FlowIncomeReportBean;
import com.project.backMng.admin.report.model.FlowIncomeReportTempBean;
import com.project.backMng.admin.report.model.FlowLineChartBean;
import com.project.backMng.admin.report.model.ParkRunDetailBean;
import com.project.backMng.admin.report.model.StockReportBean;
import com.project.backMng.admin.report.model.TollLineChartBean;
import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;
import com.project.backMng.platuser.sys.ParkInfoMng.model.ParkInfoBean;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;
import com.redoak.jar.util.PropertiesUtil;

public class FlowIncomeReportServiceImpl extends BaseService implements FlowIncomeReportService {

	// 初始化小车数量
	private final static int COUNT_INIT = 0;

	private final static Logger log = LogManager.getLogger(FlowIncomeReportServiceImpl.class);

	public FlowIncomeReportServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.report.FlowIncomeReport");
	}

	@Transactional
	@Override
	public List<FlowIncomeReportBean> findList(ObjectMap queryParam, Page page) {

		// super.delete(ns("deleteFlowReport"),null);
		int entryCount = super.findInteger(ns("findCountFlowReport"), queryParam);
		int exitCount = super.findInteger(ns("findCountExitFlowReport"), queryParam);
		page.setRecordCount(entryCount + exitCount);
		List<FlowIncomeReportBean> entryFlowReport = entryFlowReportFindList(queryParam);
		List<FlowIncomeReportBean> exitFlowReport = exitFlowReportFindList(queryParam);
		entryFlowReport.addAll(exitFlowReport);
		return entryFlowReport;
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
	private List<FlowIncomeReportBean> entryFlowReportFindList(ObjectMap queryParam) {
		// page.setRecordCount(super.findInteger(ns("findCountFlowReport"),
		// queryParam));
		List<FlowIncomeReportBean> entryFlowReportListMain = super.findList(ns("findListFlowReport"), queryParam);
		List<FlowIncomeReportTempBean> flowReportListDetail = super.findList(ns("findListFlowReportDetail"),
				queryParam);

		for (FlowIncomeReportBean flowReportMain : entryFlowReportListMain) {

			// 小车ETC初始值
			flowReportMain.setCarETC(COUNT_INIT);
			// 小车储值初始值
			// flowReportMain.setCarStoreValue(COUNT_INIT);
			// 小车记账初始值
			// flowReportMain.setCarAccount(COUNT_INIT);
			// 小车现金初始值
			flowReportMain.setCarCash(COUNT_INIT);
			// 小车移动初始值
			flowReportMain.setCarMobile(COUNT_INIT);
			// 大车ETC初始值
			flowReportMain.setCartETC(COUNT_INIT);
			// 大车储值初始值
			// flowReportMain.setCartStoreValue(COUNT_INIT);
			// 大车记账初始值
			// flowReportMain.setCartAccount(COUNT_INIT);
			// 大车现金初始值
			flowReportMain.setCartCash(COUNT_INIT);
			// 大车移动初始值
			flowReportMain.setCartMobile(COUNT_INIT);

			for (FlowIncomeReportTempBean flowReportDetail : flowReportListDetail) {
				if (flowReportMain.getDate() == flowReportDetail.getDate()
						&& flowReportMain.getLaneId() == flowReportDetail.getLaneId()) {
					// 判断大车还是小车
					if (flowReportDetail.getVehicleclass() == 1 || flowReportDetail.getVehicleclass() == 2) {
						// 判断小车储值
						// if (flowReportDetail.getPaymethod() == 2 &&
						// flowReportDetail.getEcardtype() == 22) {
						// flowReportMain.setCarStoreValue(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断小车记账
						// if (flowReportDetail.getPaymethod() == 2 &&
						// flowReportDetail.getEcardtype() == 23) {
						// flowReportMain.setCarAccount(flowReportDetail.getCountFlow());
						// continue;
						// }
						// 判断小车ETC
						if (flowReportDetail.getPaymethod() == 2) {
							flowReportMain.setCarETC(flowReportMain.getCarETC() + flowReportDetail.getCountFlow());
							continue;
						}
						// 判断小车现金
						if (flowReportDetail.getPaymethod() == 0) {
							flowReportMain.setCarCash(flowReportMain.getCarCash() + flowReportDetail.getCountFlow());
							continue;
						}

						// 判断小车会员
						// if(flowReportDetail.getPaymethod() == 1)
						// {
						// flowReportMain.setCarMember(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断小车移动
						if (flowReportDetail.getPaymethod() == 3 || flowReportDetail.getPaymethod() == 4
								|| flowReportDetail.getPaymethod() == 5) {
							flowReportMain
									.setCarMobile(flowReportMain.getCarMobile() + flowReportDetail.getCountFlow());
							continue;
						}

					} else if (flowReportDetail.getVehicleclass() == 3 || flowReportDetail.getVehicleclass() == 4) {
						// 判断小车储值
						// if (flowReportDetail.getPaymethod() == 2 &&
						// flowReportDetail.getEcardtype() == 22) {
						// flowReportMain.setCartStoreValue(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断小车记账
						// if (flowReportDetail.getPaymethod() == 2 &&
						// flowReportDetail.getEcardtype() == 23) {
						// flowReportMain.setCartAccount(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断大车ETC
						if (flowReportDetail.getPaymethod() == 2) {
							flowReportMain.setCartETC(flowReportMain.getCartETC() + flowReportDetail.getCountFlow());
							continue;
						}

						// 判断小车现金
						if (flowReportDetail.getPaymethod() == 0) {
							flowReportMain.setCartCash(flowReportMain.getCartCash() + flowReportDetail.getCountFlow());
							continue;
						}

						// 判断小车会员
						// if(flowReportDetail.getPaymethod() == 1)
						// {
						// flowReportMain.setCartMember(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断大车移动
						if (flowReportDetail.getPaymethod() == 3 || flowReportDetail.getPaymethod() == 4
								|| flowReportDetail.getPaymethod() == 5) {
							flowReportMain
									.setCartMobile(flowReportMain.getCartMobile() + flowReportDetail.getCountFlow());
							continue;
						}
					}

				}
			}
			// 更新流量表
			// super.insert(ns("insertFlowReport"), flowReportMain);
		}

		return entryFlowReportListMain;
	}

	private List<FlowIncomeReportBean> exitFlowReportFindList(ObjectMap queryParam) {
		// page.setRecordCount(page.getPageCount() +
		// super.findInteger(ns("findCountExitFlowReport"), queryParam));
		List<FlowIncomeReportBean> exitFlowReportListMain = super.findList(ns("findListExitFlowReport"), queryParam);
		List<FlowIncomeReportTempBean> flowReportListDetail = super.findList(ns("findListExitFlowReportDetail"),
				queryParam);

		for (FlowIncomeReportBean flowReportMain : exitFlowReportListMain) {

			// 小车ETC初始值
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
			// 大车ETC初始值
			flowReportMain.setCartETC(COUNT_INIT);
			// 大车记账初始值
			flowReportMain.setCartAccount(COUNT_INIT);
			// 大车现金初始值
			flowReportMain.setCartCash(COUNT_INIT);
			// 大车移动初始值
			flowReportMain.setCartMobile(COUNT_INIT);

			for (FlowIncomeReportTempBean flowReportDetail : flowReportListDetail) {
				if (flowReportMain.getDate() == flowReportDetail.getDate()
						&& flowReportMain.getLaneId() == flowReportDetail.getLaneId()) {
					// 判断大车还是小车
					if (flowReportDetail.getVehicleclass() == 1 || flowReportDetail.getVehicleclass() == 2) {
						// 判断小车储值
						// if (flowReportDetail.getPaymethod() == 2 &&
						// flowReportDetail.getEcardtype() == 22) {
						// flowReportMain.setCarStoreValue(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断小车记账
						// if (flowReportDetail.getPaymethod() == 2 &&
						// flowReportDetail.getEcardtype() == 23) {
						// flowReportMain.setCarAccount(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断小车ETC
						if (flowReportDetail.getPaymethod() == 2) {
							flowReportMain.setCarETC(flowReportMain.getCarETC() + flowReportDetail.getCountFlow());
							continue;
						}

						// 判断小车现金
						if (flowReportDetail.getPaymethod() == 0) {
							flowReportMain.setCarCash(flowReportMain.getCarCash() + flowReportDetail.getCountFlow());
							continue;
						}

						// 判断小车会员
						// if(flowReportDetail.getPaymethod() == 1)
						// {
						// flowReportMain.setCarMember(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断小车移动
						if (flowReportDetail.getPaymethod() == 3 || flowReportDetail.getPaymethod() == 4
								|| flowReportDetail.getPaymethod() == 5) {
							flowReportMain
									.setCarMobile(flowReportMain.getCarMobile() + flowReportDetail.getCountFlow());
							continue;
						}

					} else if (flowReportDetail.getVehicleclass() == 3 || flowReportDetail.getVehicleclass() == 4) {
						// 判断大车储值
						// if (flowReportDetail.getPaymethod() == 2 &&
						// flowReportDetail.getEcardtype() == 22) {
						// flowReportMain.setCartStoreValue(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断小车记账
						// if (flowReportDetail.getPaymethod() == 2 &&
						// flowReportDetail.getEcardtype() == 23) {
						// flowReportMain.setCartAccount(flowReportDetail.getCountFlow());
						// continue;
						// }
						// 判断大车ETC
						if (flowReportDetail.getPaymethod() == 2) {
							flowReportMain.setCartETC(flowReportMain.getCarETC() + flowReportDetail.getCountFlow());
							continue;
						}

						// 判断小车现金
						if (flowReportDetail.getPaymethod() == 0) {
							flowReportMain.setCartCash(flowReportMain.getCartCash() + flowReportDetail.getCountFlow());
							continue;
						}

						// 判断小车会员
						// if(flowReportDetail.getPaymethod() == 1)
						// {
						// flowReportMain.setCartMember(flowReportDetail.getCountFlow());
						// continue;
						// }

						// 判断大车移动
						if (flowReportDetail.getPaymethod() == 3 || flowReportDetail.getPaymethod() == 4
								|| flowReportDetail.getPaymethod() == 5) {
							flowReportMain
									.setCartMobile(flowReportMain.getCartMobile() + flowReportDetail.getCountFlow());
							continue;
						}
					}

				}
			}
			// 更新流量表
			// super.insert(ns("insertFlowReport"), flowReportMain);
		}
		return exitFlowReportListMain;
	}

	/**
	 * 
	 * @Title : findParkInfoList
	 * @功能描述: 查询所有停车场信息
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public List<ParkInfoBean> findParkInfoList() {
		return super.findList(ns("findParkInfoList"));
	}

	/**
	 * 
	 * @Title : findAreaInfoList
	 * @功能描述: 查询区域列表
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public List<AreaInfoBean> findAreaInfoList() {
		return super.findList(ns("findAreaInfoList"));
	}

	/**
	 * 
	 * @Title : findLaneInfoList
	 * @功能描述: 查询车道列表
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public List<LaneInfoBean> findLaneInfoList() {
		return super.findList(ns("findLaneInfoList"));
	}

	/**
	 * 
	 * @Title : findTollReport
	 * @功能描述: 查询存储过程获得金额
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public List<TollLineChartBean> findTollReport(int type, Date startTime, Date endTime) {
		ObjectMap queryParam = ObjectMap.newInstance();
		if (startTime == null || endTime == null) {
			// 判断查询节点
			if (type == 1) {
				startTime = new Date();
				endTime = startTime;
			} else {
				endTime = new Date();
				String nowDay = DateUtil.get4yMd(endTime);
				Calendar c = Calendar.getInstance();
				c.setTime(DateUtil.parse4yMd(nowDay));
				c.add(Calendar.DAY_OF_MONTH, 1);
				endTime = c.getTime();
				c.add(Calendar.DAY_OF_MONTH, -7);
				startTime = c.getTime();
			}
		}
		queryParam.put("start_time", startTime);
		queryParam.put("end_time", endTime);
		queryParam.put("type", type);
		List<TollLineChartBean> tollReportBeans = super.findList(ns("findTollReport"), queryParam);
		return tollReportBeans;
	}

	/**
	 * 
	 * @Title : findTollReport
	 * @功能描述: 查询存储过程获得金额
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public List<FlowLineChartBean> findEntryFlowReport(int type, Date startTime, Date endTime) {
		ObjectMap queryParam = ObjectMap.newInstance();
		if (startTime == null || endTime == null) {
			// 判断查询节点
			if (type == 1) {
				startTime = new Date();
				String nowDay = DateUtil.get4yMd(startTime);
				Calendar c = Calendar.getInstance();
				c.setTime(DateUtil.parse4yMd(nowDay));
				startTime = c.getTime();
				c.add(Calendar.DAY_OF_MONTH, 1);
				endTime = c.getTime();
			} else {
				endTime = new Date();
				String nowDay = DateUtil.get4yMd(endTime);
				Calendar c = Calendar.getInstance();
				c.setTime(DateUtil.parse4yMd(nowDay));
				c.add(Calendar.DAY_OF_MONTH, 1);
				endTime = c.getTime();
				c.add(Calendar.DAY_OF_MONTH, -7);
				startTime = c.getTime();
			}
		}
		queryParam.put("start_time", startTime);
		queryParam.put("end_time", endTime);
		queryParam.put("type", type);
		List<FlowLineChartBean> entryFlowBeans = super.findList(ns("findEntryFlowReport"), queryParam);
		return entryFlowBeans;
	}

	@Override
	public List<FlowLineChartBean> findExitFlowReport(int type, Date startTime, Date endTime) {
		ObjectMap queryParam = ObjectMap.newInstance();
		if (startTime == null || endTime == null) {
			// 判断查询节点
			if (type == 1) {
				startTime = new Date();
				String nowDay = DateUtil.get4yMd(startTime);
				Calendar c = Calendar.getInstance();
				c.setTime(DateUtil.parse4yMd(nowDay));
				startTime = c.getTime();
				c.add(Calendar.DAY_OF_MONTH, 1);
				endTime = c.getTime();
			} else {
				endTime = new Date();
				String nowDay = DateUtil.get4yMd(endTime);
				Calendar c = Calendar.getInstance();
				c.setTime(DateUtil.parse4yMd(nowDay));
				c.add(Calendar.DAY_OF_MONTH, 1);
				endTime = c.getTime();
				c.add(Calendar.DAY_OF_MONTH, -8);
				startTime = c.getTime();
			}
		}
		queryParam.put("start_time", startTime);
		queryParam.put("end_time", endTime);
		queryParam.put("type", type);
		List<FlowLineChartBean> exitFlowBeans = super.findList(ns("findExitFlowReport"), queryParam);
		return exitFlowBeans;
	}

	/**
	 * 
	 * @Title : todayBaseInfo
	 * @功能描述: 今日收入及流量
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public FlowIncomeBaseBean todayBaseInfo() {
		return super.findObj(ns("findFlowIncomeInfo"), 0);
	}

	/**
	 * 
	 * @Title : yesterdayBaseInfo
	 * @功能描述: 昨日收入及流量
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public FlowIncomeBaseBean yesterdayBaseInfo() {
		return super.findObj(ns("findFlowIncomeInfo"), 1);
	}

	/**
	 * 
	 * @Title : countFailRecognitionByToday
	 * @功能描述: 获得今天的无牌车个数
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public int countFailRecognitionByToday() {
		return super.findObj(ns("countFailRecognition"), 0);
	}

	/**
	 * 
	 * @Title : findParkRunDetail
	 * @功能描述: 根据类型（自然日、公班日）、时间周期（小时、天、月）查询停车场运营明细
	 * @传入参数：@param type
	 * @传入参数：@param cycle
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public List<ParkRunDetailBean> findParkRunDetail(ObjectMap queryParam) {
		// 获得抽象工厂
		ChargeRuleFactory chargeRuleFactory = new ChargeRuleFactory();
		//获得免费时间时长，单位：分
		int freeTime = chargeRuleFactory
				.findFreeTime("");
		queryParam.put("freeTime", freeTime);
		return super.findList(ns("findParkRunDetail"),queryParam);
	}

	/**
	 * 
	   * @Title : findParkStock 
	   * @功能描述: 查询
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public StockReportBean findParkStock() {
		return (StockReportBean) super.findList(ns("findParkStock")).get(0);
	}

}
