package com.project.backMng.admin.report.service;

import java.util.List;

import com.project.backMng.admin.report.model.StatisticsReportBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

public class EtcReportServiceImpl extends BaseService implements EtcReportService{

	public EtcReportServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.report.etcReport");
	}
	
	@Override
	public List<StatisticsReportBean> findList(ObjectMap queryParam, Page page) {
		int count = super.findInteger(ns("findCountReport"), queryParam);
		page.setRecordCount(count);
		List<StatisticsReportBean> statisticalReportBean = super.findList(ns("findReport"), queryParam,page);
		return statisticalReportBean;
	}



}
