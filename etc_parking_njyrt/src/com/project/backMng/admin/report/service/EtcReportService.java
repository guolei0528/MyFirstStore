package com.project.backMng.admin.report.service;

import java.util.List;

import com.project.backMng.admin.report.model.StatisticsReportBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface EtcReportService {

	public List<StatisticsReportBean> findList(ObjectMap objectMap,Page page);

}
