package com.project.backMng.admin.report.service;

import java.util.List;

import com.project.backMng.admin.report.model.FlowReportBean;
import com.project.backMng.admin.report.model.IncomeReportBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface IncomeReportService {

	public List<IncomeReportBean> findList(ObjectMap queryParam,Page page);
	
}
