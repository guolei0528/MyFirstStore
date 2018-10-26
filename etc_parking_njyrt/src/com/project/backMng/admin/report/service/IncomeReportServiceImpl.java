package com.project.backMng.admin.report.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.project.backMng.admin.report.model.IncomeReportBean;
import com.project.backMng.admin.report.model.IncomeReportTempBean;
import com.project.backMng.admin.report.model.IncomeReportTempBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

public class IncomeReportServiceImpl extends BaseService implements IncomeReportService {

	// 初始化小车数量
	private final static int COUNT_INIT = 0;

	private final static Logger log = LogManager.getLogger(IncomeReportServiceImpl.class);

	public IncomeReportServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.report.IncomeReport");
		// TODO Auto-generated constructor stub
	}
	
	@Transactional
	@Override
	public List<IncomeReportBean> findList(ObjectMap queryParam, Page page) {
		
		 //删除
//		super.delete(ns("deleteIncomeReport"),null);
		int exitCount = super.findInteger(ns("findCountIncomeReport"), queryParam);
		page.setRecordCount(exitCount);
		page.setPageSize(15);
		List<IncomeReportBean> exitIncomeReport = exitIncomeReportFindList(queryParam,page);
		return exitIncomeReport;
	}
	
	
	/**
	 * 
	   * @Title : entryIncomeReportFindList 
	   * @功能描述: 统计入口收入报表
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回类型：List<IncomeReportBean> 
	   * @throws ：
	 */
	private List<IncomeReportBean> entryIncomeReportFindList(ObjectMap queryParam)
	{
//		page.setRecordCount(super.findInteger(ns("findCountIncomeReport"), queryParam));
		List<IncomeReportBean> entryIncomeReportListMain = super.findList(ns("findListIncomeReport"), queryParam);
		List<IncomeReportTempBean> incomeReportListDetail = super.findList(ns("findListIncomeReportDetail"), queryParam);

		for (IncomeReportBean incomeReportMain : entryIncomeReportListMain) {

			// 小车储值初始值
			incomeReportMain.setCarStoreValue(COUNT_INIT);
			
			// 小车记账初始值
			incomeReportMain.setCarAccount(COUNT_INIT);
			
			// 小车现金初始值
			incomeReportMain.setCarCash(COUNT_INIT);
			// 小车移动初始值
			incomeReportMain.setCarMobile(COUNT_INIT);
			// 大车储值初始值
			incomeReportMain.setCartStoreValue(COUNT_INIT);
			// 大车记账初始值
			incomeReportMain.setCartAccount(COUNT_INIT);
			// 大车现金初始值
			incomeReportMain.setCartCash(COUNT_INIT);
			// 大车移动初始值
			incomeReportMain.setCartMobile(COUNT_INIT);

			for (IncomeReportTempBean incomeReportDetail : incomeReportListDetail) {
				if (incomeReportMain.getDate() == incomeReportDetail.getDate()
						&& incomeReportMain.getLaneId() == incomeReportDetail.getLaneId()) {
					// 判断大车还是小车
					if (incomeReportDetail.getVehicleclass() == 1 || incomeReportDetail.getVehicleclass() == 2) {
						// 判断小车储值
						if (incomeReportDetail.getPaymethod() == 2 && incomeReportDetail.getEcardtype() == 22) {
							incomeReportMain.setCarStoreValue(incomeReportDetail.getSumIncome());
							continue;
						}

						// 判断小车记账
						if (incomeReportDetail.getPaymethod() == 2 && incomeReportDetail.getEcardtype() == 23) {
							incomeReportMain.setCarAccount(incomeReportDetail.getSumIncome());
							continue;
						}
						// 判断小车现金
						if (incomeReportDetail.getPaymethod() == 0) {
							incomeReportMain.setCarCash(incomeReportDetail.getSumIncome());
							continue;
						}

						// 判断小车会员
						// if(incomeReportDetail.getPaymethod() == 1)
						// {
						// incomeReportMain.setCarMember(incomeReportDetail.getCountIncome());
						// continue;
						// }

						// 判断小车移动
						if (incomeReportDetail.getPaymethod() == 1) {
							incomeReportMain.setCarMobile(incomeReportDetail.getSumIncome());
							continue;
						}

					} else if (incomeReportDetail.getVehicleclass() == 3 || incomeReportDetail.getVehicleclass() == 4) {
						// 判断小车储值
						if (incomeReportDetail.getPaymethod() == 2 && incomeReportDetail.getEcardtype() == 22) {
							incomeReportMain.setCartStoreValue(incomeReportDetail.getSumIncome());
							continue;
						}

						// 判断小车记账
						if (incomeReportDetail.getPaymethod() == 2 && incomeReportDetail.getEcardtype() == 23) {
							incomeReportMain.setCartAccount(incomeReportDetail.getSumIncome());
							continue;
						}
						// 判断小车现金
						if (incomeReportDetail.getPaymethod() == 0) {
							incomeReportMain.setCartCash(incomeReportDetail.getSumIncome());
							continue;
						}

						// 判断小车会员
						// if(incomeReportDetail.getPaymethod() == 1)
						// {
						// incomeReportMain.setCartMember(incomeReportDetail.getCountIncome());
						// continue;
						// }

						// 判断小车移动
						if (incomeReportDetail.getPaymethod() == 1) {
							incomeReportMain.setCartMobile(incomeReportDetail.getSumIncome());
							continue;
						}
					}

				}
			}
			// 更新收入表
			super.insert(ns("insertIncomeReport"), incomeReportMain);
		}
		
		return entryIncomeReportListMain;
	}
	
	
	/**
	 * 
	* @Title: exitIncomeReportFindList 
	* @Description: 查询收入流量报表
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<IncomeReportBean>    返回类型 
	* @throws
	 */
	private List<IncomeReportBean> exitIncomeReportFindList(ObjectMap queryParam,Page page)
	{
//		page.setRecordCount(page.getPageCount() + super.findInteger(ns("findCountExitIncomeReport"), queryParam));
		List<IncomeReportBean> exitIncomeReportListMain = super.findList(ns("findListIncomeReport"), queryParam,page);
		List<IncomeReportTempBean> incomeReportListDetail = super.findList(ns("findListIncomeReportDetail"), queryParam);

		for (IncomeReportBean incomeReportMain : exitIncomeReportListMain) {

			// 小车储值初始值
//			incomeReportMain.setCarStoreValue(COUNT_INIT);
			// 小车记账初始值
//			incomeReportMain.setCarAccount(COUNT_INIT);
			
			//小车ETC
			incomeReportMain.setCarETC(COUNT_INIT);
			
			// 小车现金初始值
			incomeReportMain.setCarCash(COUNT_INIT);
			// 小车移动初始值
			incomeReportMain.setCarMobile(COUNT_INIT);
			// 大车储值初始值
//			incomeReportMain.setCartStoreValue(COUNT_INIT);
			// 大车记账初始值
//			incomeReportMain.setCartAccount(COUNT_INIT);
			
			//大车ETC
			incomeReportMain.setCartETC(COUNT_INIT);
			
			// 大车现金初始值
			incomeReportMain.setCartCash(COUNT_INIT);
			// 大车移动初始值
			incomeReportMain.setCartMobile(COUNT_INIT);
			
			// 小车人工放行
			incomeReportMain.setCarArtificial(COUNT_INIT);
			
			// 大车人工放行
			incomeReportMain.setCartArtificial(COUNT_INIT);

			for (IncomeReportTempBean incomeReportDetail : incomeReportListDetail) {
				if (incomeReportMain.getDate() == incomeReportDetail.getDate()
						&& incomeReportMain.getLaneId() == incomeReportDetail.getLaneId()) {
					// 判断大车还是小车
					if (incomeReportDetail.getVehicleclass() == 1) {
						// 判断小车储值
//						if (incomeReportDetail.getPaymethod() == 2 && incomeReportDetail.getEcardtype() == 22) {
//							incomeReportMain.setCarStoreValue(incomeReportDetail.getSumIncome());
//							continue;
//						}

						// 判断小车记账
//						if (incomeReportDetail.getPaymethod() == 2 && incomeReportDetail.getEcardtype() == 23) {
//							incomeReportMain.setCarAccount(incomeReportDetail.getSumIncome());
//							continue;
//						}
						
						// 判断小车ETC
						if (incomeReportDetail.getPaymethod() == 2) {
							incomeReportMain.setCarETC(incomeReportMain.getCarETC()+incomeReportDetail.getSumIncome());
							continue;
						}
						
						// 判断小车现金
						if (incomeReportDetail.getPaymethod() == 0) {
							incomeReportMain.setCarCash(incomeReportMain.getCarCash()+incomeReportDetail.getSumIncome());
							continue;
						}

						// 判断小车会员
						// if(incomeReportDetail.getPaymethod() == 1)
						// {
						// incomeReportMain.setCarMember(incomeReportDetail.getCountIncome());
						// continue;
						// }

						// 判断小车移动
						if (incomeReportDetail.getPaymethod() == 3 
								|| incomeReportDetail.getPaymethod() == 4) {
							incomeReportMain.setCarMobile(incomeReportMain.getCarMobile()+incomeReportDetail.getSumIncome());
							continue;
						}
						
						// 判断小车人工放行
						if (incomeReportDetail.getPaymethod() == 9) {
							incomeReportMain.setCarArtificial(incomeReportMain.getCarArtificial()+incomeReportDetail.getSumIncome());
							continue;
						}

					} else if (incomeReportDetail.getVehicleclass() == 2) {
						// 判断小车储值
//						if (incomeReportDetail.getPaymethod() == 2 && incomeReportDetail.getEcardtype() == 22) {
//							incomeReportMain.setCartStoreValue(incomeReportDetail.getSumIncome());
//							continue;
//						}

						// 判断小车记账
//						if (incomeReportDetail.getPaymethod() == 2 && incomeReportDetail.getEcardtype() == 23) {
//							incomeReportMain.setCartAccount(incomeReportDetail.getSumIncome());
//							continue;
//						}
						
						// 判断大车ETC
						if (incomeReportDetail.getPaymethod() == 2) {
							incomeReportMain.setCartETC(incomeReportMain.getCartETC()+incomeReportDetail.getSumIncome());
							continue;
						}
						
						// 判断小车现金
						if (incomeReportDetail.getPaymethod() == 0) {
							incomeReportMain.setCartCash(incomeReportMain.getCartCash()+incomeReportDetail.getSumIncome());
							continue;
						}

						// 判断小车会员
						// if(incomeReportDetail.getPaymethod() == 1)
						// {
						// incomeReportMain.setCartMember(incomeReportDetail.getCountIncome());
						// continue;
						// }

						// 判断小车移动
						if (incomeReportDetail.getPaymethod() == 3 
								|| incomeReportDetail.getPaymethod() == 4) {
							incomeReportMain.setCartMobile(incomeReportMain.getCartMobile()+incomeReportDetail.getSumIncome());
							continue;
						}
						
						// 判断大车人工放行
						if (incomeReportDetail.getPaymethod() == 9) {
							incomeReportMain.setCartArtificial(incomeReportMain.getCartArtificial()+incomeReportDetail.getSumIncome());
							continue;
						}
					}

				}
			}
			// 更新收入表
//			super.insert(ns("insertIncomeReport"), incomeReportMain);
		}
		return exitIncomeReportListMain;
	}
	
}
