package com.project.backMng.admin.report.service;

import java.util.List;

import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface PaymentStatementService {
	
	public List<ObjectMap> findSummaryList(ObjectMap queryParam,Page page);

	public List<ObjectMap> findDetailList(ObjectMap queryParam,Page page);

	public List<ObjectMap> findLaneList();
	
	public int totalDetailList(ObjectMap queryParam);
	
	public int receivablesDetailList(ObjectMap queryParam);
}
