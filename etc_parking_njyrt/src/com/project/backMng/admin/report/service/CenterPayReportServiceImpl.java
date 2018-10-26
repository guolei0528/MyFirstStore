package com.project.backMng.admin.report.service;

import java.util.List;

import com.project.backMng.admin.report.model.CenterPayReportBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

public class CenterPayReportServiceImpl  extends BaseService implements CenterPayReportService{

	public CenterPayReportServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.report.CenterPayReport");
	}

	
	@Override
	public List<CenterPayReportBean> findList(ObjectMap queryParam, Page page) {
		int count = super.findInteger(ns("findCountReport"), queryParam);
		page.setRecordCount(count);
		List<CenterPayReportBean> paymentStatement = super.findList(ns("findReport"), queryParam,page);
		return paymentStatement;
	}


	/**
	 * 
	   * @Title : receivablesList 
	   * @功能描述: 应收金额
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public int receivablesList(ObjectMap queryParam) {
		//判断是否为人工放行数据
		return super.findInteger(ns("pdisTollList"), queryParam);
	}


	/**
	 * 
	   * @Title : totalList 
	   * @功能描述: TODO
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public int totalList(ObjectMap queryParam) {
		return super.findInteger(ns("totalList"), queryParam);
	}

}
