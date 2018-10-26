package com.project.backMng.admin.transitRecord.CompreSearch.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.admin.transitRecord.CompreSearch.model.CompreRecordBean;
import com.project.backMng.admin.transitRecord.CompreSearch.model.RecordDetailBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.mybatis.service.BaseService;

public class CompreSearchServiceImpl extends BaseService implements CompreSearchService {
	private final static Logger log=LogManager.getLogger(CompreSearchServiceImpl.class);

	public CompreSearchServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.transitRecord.CompreSearch");
	}

	@Override
	public List<String> queryPlates(String plate) {
		ObjectMap map=ObjectMap.newInstance();
		map.put("plate", plate.toUpperCase());
		return super.findList(ns("queryPlates"), plate.toUpperCase());
	}

	@Override
	public List<CompreRecordBean> queryRecords(String orderid,String username, String plate, String begindate, String enddate) {
		ObjectMap map = ObjectMap.newInstance();
		map.put("username", username);
		map.put("orderid", orderid);
		map.put("plate", plate);
		map.put("begindate", begindate);
		map.put("enddate", enddate);
		log.info("参数："+map);
		return super.findList(ns("queryCompreRecords"), map);
	}

	@Override
	public List<RecordDetailBean> queryDetail(String license, String time, String type) {
		ObjectMap map = ObjectMap.newInstance();
		map.put("license", license);
		map.put("time", time);
		map.put("type", type);
		log.info("参数："+map);
		return super.findList(ns("queryRecordDetail"), map);
	}

}
