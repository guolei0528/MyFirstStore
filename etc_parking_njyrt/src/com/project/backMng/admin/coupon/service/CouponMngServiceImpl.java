package com.project.backMng.admin.coupon.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.admin.coupon.model.CouponMngBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

public class CouponMngServiceImpl extends BaseService implements CouponMngService{

	private final static Logger log = LogManager.getLogger(CouponMngServiceImpl.class);

	public CouponMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.coupon.CouponMng");
	}
	
	/**
	 * 
	   * @Title : findList 
	   * @功能描述: 查询
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<CouponMngBean> findList(ObjectMap queryParam, Page page) {
		page.setRecordCount(super.findInteger(ns("findCount"), queryParam));
		return super.findList(ns("findList"), queryParam, page);
	}

	/**
	 * 
	   * @Title : findInfo 
	   * @功能描述: 根据主键验证码返回信息
	   * @传入参数：@param id
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public CouponMngBean findInfo(String id) {
		return super.findObj(ns("findInfo"), id);
	}

	
}
