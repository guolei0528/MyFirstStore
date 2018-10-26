package com.project.backMng.admin.coupon.service;

import java.util.List;

import com.project.backMng.admin.coupon.model.CouponMngBean;
import com.project.backMng.admin.coupon.model.IssueBatchMngBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface CouponMngService {

	public List<CouponMngBean> findList(ObjectMap queryParam,Page page);
	
	public CouponMngBean findInfo(String id);
	
}
