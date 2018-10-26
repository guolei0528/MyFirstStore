package com.project.communication.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.project.backMng.platuser.CareTrafficRecordMng.model.CareTrafficRecordBean;
import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;
import com.project.communication.model.IncomeCountBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.mybatis.service.BaseService;

public class TrafficRecordServiceImpl extends BaseService implements TrafficRecordService{
	public TrafficRecordServiceImpl() {
		setIBATIS_NAME_SPACE("communication.TrafficRecord");
	}
	private SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
	@Override
	public void update(CareTrafficRecordBean bean) {
		if("1".equals(bean.getlane_flag())){
			bean.setpay_method(null);
		}
		bean.settraffic_date(sf.format(new Date()));
		super.update(ns("update"), bean);
		
	}

	@Override
	public void insert(CareTrafficRecordBean bean) {
		// TODO Auto-generated method stub
		if("1".equals(bean.getlane_flag())){
			bean.setpay_method(null);
		}
		bean.settraffic_date(sf.format(new Date()));
		super.insert(ns("insert"), bean);
	}

	@Override
	public List<CareTrafficRecordBean> findList(CareTrafficRecordBean bean) {
		// TODO Auto-generated method stub
		if("1".equals(bean.getlane_flag())){
			bean.setpay_method(null);
		}
		bean.settraffic_date(sf.format(new Date()));
		return super.findList(ns("findList"),bean);
	}
	@Override
	public void updateTrafficRecord(CareTrafficRecordBean bean){
		List<CareTrafficRecordBean> list = findList(bean);
		if(list!=null && list.size()>0){
			update(bean);
		}else{
			insert(bean);
		}
	}
	@Override
	public LaneInfoBean findLaneInfo(String lane_id) {
		// TODO Auto-generated method stub
		return super.findObj(ns("findLaneInfo"), lane_id);
	}
	@Override
	public String findMemberType(String mv_license,Integer car_color){
		ObjectMap o=ObjectMap.newInstance();
		o.put("car_color", car_color);
		o.put("mv_license", mv_license);
		String member_id = super.findObj(ns("findMemberInCarInfo"),o);
		return super.findObj(ns("validMember"),member_id);
	}

	@Override
	public void updateIncomeCount(IncomeCountBean bean) {
		bean.setIncome_date(sf.format(new Date()));
		List<Object> list = super.findList("findIncomeList",bean);
		if(list!=null && list.size()>0){
			super.update(ns("updateIncomeCount"), bean);
		}else{
			super.insert(ns("insertIncomeCount"), bean);
		}
		
	}

}
