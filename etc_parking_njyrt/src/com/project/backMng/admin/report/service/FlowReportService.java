package com.project.backMng.admin.report.service;

import java.util.List;

import com.project.backMng.admin.report.model.FlowReportBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface FlowReportService {

	public List<FlowReportBean> findList(ObjectMap queryParam,Page page);
	
}
