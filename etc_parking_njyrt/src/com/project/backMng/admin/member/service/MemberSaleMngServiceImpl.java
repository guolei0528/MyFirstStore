package com.project.backMng.admin.member.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.admin.member.model.MemberSaleBean;
import com.project.common.tool.AppConst;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

public class MemberSaleMngServiceImpl extends BaseService implements MemberSaleMngService {
	private final static Logger log = LogManager.getLogger(MemberSaleMngServiceImpl.class);

	
	public MemberSaleMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.member.MemberSaleMng");
	}
	
	@Override
	public List<MemberSaleBean> findList(ObjectMap queryParam, Page page) {
		page.setRecordCount(super.findInteger(ns("findCount"), queryParam));
		return super.findList(ns("findList"), queryParam, page);
	}

	@Override
	public MemberSaleBean findInfo(ObjectMap queryParam) {
		return super.findObj(ns("findInfo"), queryParam);
	}

	@Override
	public String save(MemberSaleBean bean) {
		// 检查系统中是否存在相同会员编号
		if(bean.getMemberId() == null || "".equals(bean.getMemberId().trim()))
		{
			return "请输入员工编号";
		}
		
		// 查询该会员是否存在
		int count = super.findInteger("backMng.admin.member.MemberInfoMng.findCountByMemberId",
				bean.getMemberId().trim());
		if (count == 0) {
			return "系统中不存在该员工编号！";
		}
		
		bean.setValid(AppConst.DELETE_FLAG.NO);
		// 保存到T_LOGIN_USER数据表
		ObjectMap o = ObjectMap.newInstance();
		o.put("member_id", bean.getMemberId());
		o.put("member_type", bean.getMemberType());
		o.put("card_id", bean.getCardId());
		Date now = new Date();
		o.put("issue_time", now);
		o.put("begin_time", bean.getBeginTime());
		o.put("end_time", bean.getEndTime());
		o.put("money", bean.getMoney());
		o.put("count", bean.getCount());
		o.put("park_id", bean.getParkId());
		o.put("area_id", bean.getAreaId());
		
		super.insert(ns("insertMemberSale"), o);
		return "success";
	}

	@Override
	public void update(MemberSaleBean bean) {
		// TODO Auto-generated method stub
		bean.setValid(AppConst.DELETE_FLAG.NO);
		// 保存到T_LOGIN_USER数据表
		ObjectMap o = ObjectMap.newInstance();
		o.put("memberId", bean.getMemberId());
		o.put("memberType", bean.getMemberType());
		o.put("cardId", bean.getCardId());
//		Date now = new Date();
		//日期封装成date
		DateUtil dateUtil = new DateUtil();
		Date issueTime = dateUtil.parse4yMdHms(bean.getIssueTime());
		Date beginTime = dateUtil.parse4yMd(bean.getBeginTime());
		Date endTime = dateUtil.parse4yMd(bean.getEndTime());
		o.put("issueTime", issueTime);
		o.put("beginTime", beginTime);
		o.put("endTime", endTime);
		
		o.put("money", bean.getMoney());
		o.put("count", bean.getCount());
		o.put("parkId", bean.getParkId());
		o.put("areaId", bean.getAreaId());
		
		super.update(ns("update"), o);
	}

	/**
	 * 
	   * @Title : delete 删除员工售卖信息
	   * @功能描述: TODO
	   * @传入参数：@param deleteParam
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public void delete(ObjectMap deleteParam) {
		// TODO Auto-generated method stub
		super.delete(ns("deleteMemberSale"), deleteParam);		
	}

}
