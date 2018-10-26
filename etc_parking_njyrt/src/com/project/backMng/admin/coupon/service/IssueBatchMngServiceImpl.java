package com.project.backMng.admin.coupon.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.project.backMng.admin.coupon.model.CouponMngBean;
import com.project.backMng.admin.coupon.model.IssueBatchMngBean;
import com.project.tools.AsciiUtil;
import com.project.tools.DateUtil;
import com.project.tools.MD5Util;
import com.project.tools.UuidGenerator;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class IssueBatchMngServiceImpl extends BaseService implements IssueBatchMngService{

	private final static Logger log = LogManager.getLogger(IssueBatchMngServiceImpl.class);
	
	public IssueBatchMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.coupon.IssueBatchMng");
	}
	
	/**
	 * 
	   * @Title : findList 
	   * @功能描述: 返回批次信息
	   * @传入参数：@param queryParam
	   * @传入参数：@param page
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<IssueBatchMngBean> findList(ObjectMap queryParam, Page page) {
		page.setRecordCount(super.findInteger(ns("findCount"), queryParam));
		return super.findList(ns("findList"), queryParam, page);
	}
	
	/**
	 * 
	   * @Title : findInfo 
	   * @功能描述: 返回发行view
	   * @传入参数：@param id
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public IssueBatchMngBean findInfo(String id) {
		return super.findObj(ns("findInfo"), id);
	}
	
	
	/**
	 * 
	   * @Title : save 
	   * @功能描述: 保存优惠券
	   * @传入参数：@param bean
	   * @返回值：
	   * @throws ：
	 */
	@Override
	@Transactional
	public boolean save(IssueBatchMngBean bean) { 
		
		Date now = new Date();
		String now_str = DateUtil.get4yMdHms(now);
		int monthDay= Integer.parseInt(now_str.substring(2, 4)+now_str.substring(5, 7));
		int batchId = 0;
		int count = super.findObj(ns("findCountByIssueDate"),monthDay);
		if(count > 999)
		{
			return false;
		}
		if(count != 0)
		{
			// 查询批次号
			int batch_id = super.findObj(ns("findBatchByIssueDate"), monthDay);
			batchId = batch_id+1;
		}
		bean.setIssue_date(monthDay);
		bean.setBatch_id(batchId);
		// 判断是否金额类型的优惠券
		if("J".equals(bean.getCoupon_type()))
		{
			bean.setCoupon_toll(bean.getCoupon_toll());
		}
		// 判断是否时间类型的优惠券
		if("S".equals(bean.getCoupon_type()))
		{
			bean.setCoupon_toll(bean.getCoupon_toll());
		}
		// 使用张数
		bean.setUse_count(0);
		// 剩余张数
		bean.setRemain_count(bean.getIssue_count());
		// 作废张数
		bean.setInvalid_count(0);
		// 状态：生效
		bean.setStatus(0);
		// 创建时间
		bean.setCreate_time(now);
		super.insert(ns("insertIssueBatch"), bean);
		int issue_id = super.findObj(ns("findIdByIssueDate"),bean);
		
		// 封装优惠券
		CouponMngBean couponMngBean = new CouponMngBean();
		couponMngBean.setIssuer_id(issue_id);
		couponMngBean.setCoupon_type(bean.getCoupon_type());
		couponMngBean.setUse_restrict(bean.getUse_restrict());
		couponMngBean.setIssuer_code(bean.getIssuer_code());
		couponMngBean.setIssue_date(bean.getIssue_date());
		couponMngBean.setBatch_id(bean.getBatch_id());
		couponMngBean.setCoupon_toll(bean.getCoupon_toll());
		couponMngBean.setStatus(bean.getStatus());
		couponMngBean.setStart_time(bean.getStart_time());
		couponMngBean.setEnd_time(bean.getEnd_time());
		couponMngBean.setCreate_user(bean.getCreate_user());
		couponMngBean.setCreate_time(bean.getCreate_time());
		
		String batch_id = String.valueOf(bean.getBatch_id());
		StringBuffer sb = new StringBuffer();
		if(batch_id.length() < 3)
		{
			for(int i=0;i<3-batch_id.length();i++)
			{
				sb.append("0");
			}
			sb.append(batch_id);
		}
		else
		{
			sb.append(batch_id);
		}
		
		// 迭代发行数量
		for(int i=0;i<bean.getIssue_count();i++)
		{
			String issue_index = String.valueOf(i);
			if(i<10)
			{
				issue_index = "00"+i;
			}
			if(i>=10 && i<100)
			{
				issue_index = "0"+String.valueOf(i);
			}
//			String coupon_code = "";
			String random = UuidGenerator.getFixLenthString(6);
			String random_org = MD5Util.encrypt16(random);
			String verify_code = AsciiUtil.letterToNum(random_org);
			if(super.findInteger("backMng.admin.coupon.CouponMng.countByVerifyCode",verify_code) > 0)
			{
				i--;
				continue;
			}
			
			String coupon_code =bean.getCoupon_type()+String.valueOf(bean.getUse_restrict())
				+bean.getIssuer_code()+bean.getIssue_date()+sb.toString()+issue_index;
			couponMngBean.setCoupon_code(coupon_code);
			couponMngBean.setVerify_code(verify_code);
			super.insert("backMng.admin.coupon.CouponMng.insertCoupon", couponMngBean);
		}
		return true;
	}

	/**
	 * 
	   * @Title : findByIssueId 
	   * @功能描述: 根据发行方id获取优惠券
	   * @传入参数：@param data
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public List<CouponMngBean> findByIssueId(String data) { 
		// 判断发行id是否为null
		if(data == null || "".equals(data))
		{
			return new ArrayList<CouponMngBean>();
		}
		String[] dataArray = data.split(",");
		List<Integer> issue_id = new ArrayList<Integer>();
		for(int i=0;i<dataArray.length;i++)
		{
			issue_id.add(Integer.parseInt(dataArray[i]));
		}
		ObjectMap o = ObjectMap.newInstance();
		o.put("issue_id", issue_id);
		return super.findList(ns("findByIssueId"), o); 
	}

	/**
	 * 
	   * @Title : deleteByIssueId 
	   * @功能描述: 根据发行编号删除
	   * @传入参数：@param data
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public void exprotByIssueId(String data) {
		// 判断发行id是否为null
		if(data == null)
		{
			return;
		}
		String[] dataArray = data.split(",");
		List<Integer> issue_id = new ArrayList<Integer>();
		for(int i=0;i<dataArray.length;i++)
		{
			issue_id.add(Integer.parseInt(dataArray[i]));
		}
		ObjectMap o = ObjectMap.newInstance();
		o.put("issue_id", issue_id);
		o.put("status", 1);
		super.update(ns("updateCouponStatusById"), o);
		super.update(ns("updateIssueStatusById"), o);
	}

	/**
	 * 
	   * @Title : searchCouponCodeByBatch 
	   * @功能描述: 根据批次号等信息查询
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回值：
	   * @throws:
	 */
	@Override
	@Transactional
	public List<String> searchCouponCodeByBatch(ObjectMap queryParam) {
		return super.findList("backMng.admin.coupon.CouponMng.searchCouponCodeByBatch", queryParam);
	}

	/**
	 * 
	   * @Title : invaildCoupon 
	   * @功能描述: 作废优惠券
	   * @传入参数：@param queryParam
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public String invaildCoupon(ObjectMap objectMap) {
		
		String invaild_type = objectMap.get("invaild_type").toString();
		// 作废状态2
		objectMap.put("status",2);
		// 作废类型为单张优惠券作废
		if(invaild_type != null && "0".equals(invaild_type))
		{
			String invaild_coupons = objectMap.get("invaild_coupons").toString();
			JSONArray jsonArray = JSONArray.fromObject(invaild_coupons);
			// 判断作废优惠券是否有效
			if(jsonArray.size() > 0)
			{
				List<String> invaild_coupon = new ArrayList<String>();
				for(int i = 0;i<jsonArray.size();i++)
				{
					invaild_coupon.add(jsonArray.getString(i));
				}
				objectMap.put("invaild_coupon", invaild_coupon);
				int invaildCount = super.update("backMng.admin.coupon.CouponMng.invaildManyCouponByCode", objectMap);
				if(invaildCount == 0)
				{
					return "作废失败，不存在可作废的优惠券！";
				}
				else
				{
					return "作废成功，成功作废"+invaildCount+"张!";
				}
				
			}
		}
		// 按照优惠券编号区间作废
		if(invaild_type != null && "1".equals(invaild_type))
		{
			objectMap.put("invaild_batch", objectMap.get("invaild_start_coupon").toString().substring(0, 11));
			objectMap.put("invaild_coupon_num", Integer.parseInt(objectMap.get("invaild_coupon_num").toString()));
			int invaildCount = super.update("backMng.admin.coupon.CouponMng.invaildBetweenCouponCode", objectMap);
			if(invaildCount == 0)
			{
				return "作废失败，不存在可作废的优惠券！";
			}
			else
			{
				return "作废成功，成功作废"+invaildCount+"张!";
			}
		}
		// 按照发行批次作废
		if(invaild_type != null && "2".equals(invaild_type))
		{
			
			String invaild_batch = objectMap.get("invaild_batch").toString();
			
			String coupon_type = invaild_batch.substring(0, 1);
			String use_restrict = invaild_batch.substring(1, 2);
			String issuer_code = invaild_batch.substring(2,4);
			String issue_date = invaild_batch.substring(4,8);
			String batch_id = invaild_batch.substring(8,11);
			
			// 封装批次
			objectMap.put("coupon_type", coupon_type);
			objectMap.put("use_restrict", use_restrict);
			objectMap.put("issuer_code", issuer_code);
			objectMap.put("issue_date", issue_date);
			objectMap.put("batch_id", Integer.parseInt(batch_id));
			// 判断是否没有非作废状态的优惠券,没有表示作废失败
			int num = super.findInteger("backMng.admin.coupon.CouponMng.existsVaildByBatch", objectMap);
			if(num == 0)
			{
				return "作废失败，不存在此批次优惠券，请核对！";
			}
			int executeCount = super.update("backMng.admin.coupon.CouponMng.invaildCoupon", objectMap);
			if(executeCount == 0)
			{
				return "作废失败，此批次优惠券已作废！";
			}
			// 判断是否没有非作废状态的优惠券，没有会作废优惠批次
			int count = super.findInteger("backMng.admin.coupon.CouponMng.countVaildByBatch", objectMap);
			if(count == 0)
			{
				super.update(ns("invaildBatch"), objectMap);
			}
			return "作废成功，"+"作废优惠券"+executeCount+"张！";
		}
		
		return "作废失败";
	}
	
}
