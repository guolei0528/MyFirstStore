package com.project.backMng.admin.transitRecord.EntryMng.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.admin.transitRecord.EntryMng.model.EntryBean;
import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.redoak.jar.base.mybatis.service.BaseService;

public class EntryMngServiceImpl extends BaseService implements EntryMngService{

	private final static Logger log=LogManager.getLogger(EntryMngServiceImpl.class);

	public EntryMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.transitRecord.EntryMng");
	}

	public List<EntryBean> findList(ObjectMap queryParam, Page page) {
		queryParam.put("skipSize", (page.getCurrentPage()-1)*page.getPageSize());
		queryParam.put("pageSize", page.getPageSize());
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		return super.findList(ns("findList"),queryParam);
	}

	public EntryBean findInfo(ObjectMap map) {
		return (EntryBean)super.findList(ns("findInfo"),map).get(0);
	}
	@Override
	public EntryBean findHisInfo(ObjectMap map) {
		return super.findObj(ns("findHisInfo"),map);
	}

	public void save(EntryBean bean, SysLoginUserBean userBean) {
//		bean.setID(UuidGenerator.generateUUID());
		super.insert(ns("insert"), bean);
	}

	public void update(EntryBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("update"), bean);
	}

	public void delete(String id,SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("delete"), id);
	}

	public List<EntryBean> findHisList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		page.setRecordCount(super.findInteger(ns("findHisCount"),queryParam));
		return super.findList(ns("findHisList"),queryParam,page);
	}
	
	@Override
	public List<EntryBean> findTimeoutCarList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		queryParam.put("query_is_vaild", 1);
		page.setRecordCount(super.findInteger(ns("findTimeoutCount"),queryParam));
		return super.findList(ns("findTimeoutCarList"),queryParam,page);
	}
	@Override
	public List<ObjectMap> findExcessiveCarList(ObjectMap queryParam, Page page){
		queryParam.put("query_is_vaild", 1);
		page.setRecordCount(super.findInteger(ns("findExcessiveCount"),queryParam));
		return super.findList(ns("findExcessiveCarList"),queryParam,page);
	}
	
	public List<AreaInfoBean> findEmptyPosition(ObjectMap queryParam, Page page){
		page.setRecordCount(super.findInteger(ns("findPositionCount"),queryParam));
		return super.findList(ns("findEmptyPosition"),queryParam,page);
	}
	public ObjectMap findAreaInfo(String area_id){
		return super.findObj(ns("findAreaInfo"),area_id);
	}

	/**
	 * 
	   * @Title : delete 
	   * @功能描述: 根据车牌和颜色删除
	   * @传入参数：@param mv_license
	   * @传入参数：@param car_color
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public void delete(String mv_license, int car_color) {
		ObjectMap obj = ObjectMap.newInstance();
		obj.put("mv_license",mv_license);
		obj.put("car_color", car_color);
		super.update(ns("deleteTempByLisce"), obj);
	}

}