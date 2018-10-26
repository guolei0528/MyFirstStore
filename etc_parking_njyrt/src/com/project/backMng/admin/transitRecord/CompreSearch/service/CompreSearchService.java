package com.project.backMng.admin.transitRecord.CompreSearch.service;

import java.util.List;

import com.project.backMng.admin.transitRecord.CompreSearch.model.CompreRecordBean;
import com.project.backMng.admin.transitRecord.CompreSearch.model.RecordDetailBean;

public interface CompreSearchService {
	
	public List<String> queryPlates(String plate);
	
	public List<CompreRecordBean> queryRecords(String orderid,String username,String plate,String begindate,String enddate);
	
	public List<RecordDetailBean> queryDetail(String license,String time,String type);

}
