package com.project.backMng.admin.coupon.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.project.backMng.admin.coupon.model.CouponMngBean;
import com.project.backMng.admin.coupon.model.IssueBatchMngBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface IssueBatchMngService {

	
	public List<IssueBatchMngBean> findList(ObjectMap queryParam,Page page);
	
	public IssueBatchMngBean findInfo(String id);
	
	public boolean save(IssueBatchMngBean bean);
	
	public List<CouponMngBean> findByIssueId(String data);
	
	public void exprotByIssueId(String data);
	
	public List<String> searchCouponCodeByBatch(ObjectMap queryParam);
	
	public String invaildCoupon(ObjectMap objectParam);
	
}
