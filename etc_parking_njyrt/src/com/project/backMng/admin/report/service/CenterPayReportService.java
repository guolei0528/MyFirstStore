package com.project.backMng.admin.report.service;

import java.util.List;

import com.project.backMng.admin.report.model.CenterPayReportBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

/**
 * 
   * @类 名： PaymentTerminalReportService
   * @功能描述： 集中缴费终端报表服务
   * @作者信息：吴超
   * @创建时间： 2018年1月18日下午4:21:38
   * @修改备注：
 */
public interface CenterPayReportService {

	public List<CenterPayReportBean> findList(ObjectMap objectMap,Page page);
	
	public int receivablesList(ObjectMap queryParam);
	
	public int totalList(ObjectMap queryParam);
}
