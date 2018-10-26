package com.project.communication.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.project.backCharge.tempCharge.model.TempCostBean;
import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleFactory;
import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleXml;
import com.project.backMng.admin.ChargeRuleMng.config.impl.SpecTLChargeRuleBean;
import com.project.backMng.admin.coupon.model.CouponMngBean;
import com.project.backMng.admin.voidCard.EtcBlackListMng.model.EtcBlackListBean;
import com.project.backMng.admin.whiteListMng.model.WhiteListMngBean;
import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.project.common.tool.StringUtil;
import com.project.communication.model.CommAreaInfoBean;
import com.project.communication.model.CommunicationCarInfoBean;
import com.project.communication.model.CommunicationTempCostBean;
import com.project.communication.model.OutBaseJsonBean;
import com.project.communication.model.ParkPlaceInfoBean;
import com.project.communication.model.PayResultBean;
import com.project.communication.model.ReceivePayBean;
import com.project.communication.model.TempCostInfoBean;
import com.project.communication.model.WhiteListInfoBean;
import com.project.tools.DateUtil;
import com.project.tools.ImgHelper;
import com.project.tools.RedisConst;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.mybatis.service.BaseService;
import com.redoak.jar.util.OakRedisUtil;
import com.redoak.jar.util.PropertiesUtil;

public class CommunicationServiceImpl extends BaseService implements  CommunicationService{

	private final static Logger log=LogManager.getLogger(CommunicationServiceImpl.class);

	//加载mapper
	public CommunicationServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backCharge.tempCharge.tempCost");
	}
	
	/**
	 * 
	   * @Title : addTempCost 增加停车场临时计费
	   * @功能描述: TODO
	   * @传入参数：
	   * @返回值：
	   * @throws ：
	 */
	@Transactional
	@Override
	public void addTempCost(TempCostBean tempCostBean) {
		super.insert(ns("insertTempCost"), tempCostBean);
	}
	
	
	/**
	 * 
	   * @Title : addTempCost 增加停车场临时计费
	   * @功能描述: TODO
	   * @传入参数：
	   * @返回值：
	   * @throws ：
	 */
	@Transactional
	@Override
	public void addOrUpdateTempCost(TempCostBean tempCostBean) {
		ObjectMap map=ObjectMap.newInstance();
//		map.put("car_color", tempCostBean.getCarColor());
		map.put("mv_license", tempCostBean.getMvLicense());
		map.put("park_id", tempCostBean.getParkId());
//		map.put("area_id", tempCostBean.getAreaId());
		if(!"无牌车".equals(tempCostBean.getMvLicense()))
		{
			int count = super.findInteger(ns("countTempCostByPrimary"), map);
			if(count>0)
			{
				super.insert(ns("insertTempCostUncertain"),tempCostBean);
				super.delete(ns("deleteTempCostBylicense"), map);
//				log.info("删除车辆——"+tempCostBean.getMvLicense()+"数量："+delCount);
//				super.update(ns("updateTempCostEntryTime"), tempCostBean);
//				return;
			}
		}
		super.insert(ns("insertTempCost"), tempCostBean);
	}
	
	
	/**
	 * 
	   * @Title : 查询车牌号
	   * @功能描述: TODO
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	public CommunicationCarInfoBean findObuId(String mv_license,Integer car_color)
	{
		ObjectMap map=ObjectMap.newInstance();
		map.put("car_color", car_color);
		map.put("mv_license", mv_license);
		return super.findObj("communication.communicationCarInfo.findObuId", map);	}

	/**
	 * 
	   * @Title : findMvlicense 查询obuid
	   * @功能描述: TODO
	   * @传入参数：@param Mvlicense
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public CommunicationCarInfoBean findMvlicense(String obu_id,Integer car_color) {
		// TODO Auto-generated method stub
		ObjectMap map=ObjectMap.newInstance();
		map.put("obu_id", obu_id);
		map.put("car_color", car_color);
		return super.findObj("communication.communicationCarInfo.findMvlicense", map);
	}

	
	/**
	 * 
	   * @Title : findTempCost 查询临时计费的金额等
	   * @功能描述: TODO
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public TempCostBean findTempCost(ObjectMap objectMap) {
		return super.findObj(ns("findTempCost"), objectMap);
	}

	/**
	 * 
	   * @Title : deleteTempCostBymvlicense 
	   * @功能描述: 根据车牌删除临时计费
	   * @传入参数：@param objectMap
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public void deleteTempCostBymvlicense(ObjectMap objectMap) {
		
//		ObjectMap queryParam=ObjectMap.newInstance();
//		queryParam.put("mvLicense", objectMap.get("mv_license"));
//		queryParam.put("carColor", carColor);
//		queryParam.put("vehicleClass", vehicleClass);
//		queryParam.put("obuId", obuId);
		
	    super.delete(ns("deleteTempCostBymvlicense"), objectMap);
//	    log.info("deleteTempCostBymvlicense"+objectMap.get("verify_code"));
	    // 判断优惠券是否为空
	    if(objectMap.get("verify_code")!=null && !"".equals(objectMap.get("verify_code")))
	    {
	    	// 更新优惠券状态
	    	objectMap.put("status", 3);
	    	super.update("communication.communicationTempCost.updateCouponByVerifyCode", objectMap);
	    }
	}

	@Override
	public Integer findMvlicenseLiTempCost(ObjectMap objectMap) {
//		ObjectMap map=ObjectMap.newInstance();
//		map.put("car_color", car_color);
//		map.put("mv_license", mv_license);
//		map.put("vehicle_class", vehicle_class);
		
		Integer count = super.findInteger("communication.communicationCarInfo.findMvlicenseLiTempCost",objectMap);
		return count;
	}

	@Override
	public String validCard(String mv_license,String plate_color) {
		ObjectMap map=ObjectMap.newInstance();
		map.put("mv_license", mv_license);
		map.put("plate_color", plate_color);
		int policeVoidcount = super.findInteger("backMng.admin.voidCard.PoliceVoidCardMng.findCountByLicense",map);
		if(policeVoidcount > 0)
		{
			return "01";
		}
		
		int parkVoidcount = super.findInteger("backMng.admin.voidCard.ParkVoidCardMng.findCountByLicense",map);
		if(parkVoidcount > 0)
		{
			return "04";
		}
		
		// TODO Auto-generated method stub
		/*String mvc=mv_license.trim()+"|"+plate_color.trim();
		List<TxbVoidCardBean> TXB_VOID=OakRedisUtil.getObject(RedisConst.voidCard.TXB);
//		List<EtcVoidCardBean> ETC_VOID = OakRedisUtil.getObject(RedisConst.voidCard.ETC);
		List<ParkVoidCardBean> PARK_VOID = OakRedisUtil.getObject(RedisConst.voidCard.PARK);
		List<PoliceVoidCardBean> POLICE_VOID=OakRedisUtil.getObject(RedisConst.voidCard.POLICE);
		if(POLICE_VOID.contains(mvc)){
			return "01";
		}
		if(PARK_VOID.contains(mvc)){
			return "04";
		}
//		if(ETC_VOID.contains(mvc)){
//			return "03";
//		}
		if(TXB_VOID.contains(mvc)){
			return "02";
		}*/
		
		return null;
	}
	
	
	/**
	 *  出口校验黑名单
	 */
	@Override
	public String validCardOut(String mv_license,String plate_color,String obuId) {
		EtcBlackListBean ETC_VOID = (EtcBlackListBean)OakRedisUtil.getObject(RedisConst.voidCard.ETC+":"+mv_license!=null?mv_license:""+"|"+obuId!=null?obuId:"");
		if(ETC_VOID == null && mv_license!=null)
		{
			ETC_VOID = (EtcBlackListBean)OakRedisUtil.getObject(RedisConst.voidCard.ETC+":"+mv_license+"|");
		}
		if(ETC_VOID == null && obuId!=null)
		{
			ETC_VOID = (EtcBlackListBean)OakRedisUtil.getObject(RedisConst.voidCard.ETC+":|"+obuId);
		}
//		List<String> TXB_VOID=OakRedisUtil.getObject(RedisConst.voidCard.TXB);
		if(ETC_VOID != null && ETC_VOID.getValidFlag() != 0 && ETC_VOID.getStartTime().getTime() <= new Date().getTime()){
			return "03";
		}
//		if(TXB_VOID != null && TXB_VOID.contains(mvc)){
//			return "02";
//		}
		return null;
	}

	/**
	 * 校验白名单
	 */
	@Override
	public String validWhiteList(String mv_license, Integer car_color) {
//		ObjectMap map=ObjectMap.newInstance();
//		map.put("mv_license", mv_license);
//		map.put("car_color", car_color);
//		
//		Integer count = super.findInteger("communication.communicationCarInfo.validWhiteList", map);
		List<String> list = OakRedisUtil.getObject(RedisConst.voidCard.WHITE_LIST);
		if(list != null && list.contains(mv_license+"|"+car_color)){
			return "05";
		}
		return null;
	}

	@Override
	public String validMember(String mv_license, Integer car_color) {
		ObjectMap map=ObjectMap.newInstance();
		map.put("mv_license", mv_license);
		map.put("car_color", car_color);
		String  member_id= super.findObj("communication.communicationCarInfo.findMemberInCarInfo",map);
		if(member_id == null)
		{
			return "00";
		}
		String validExsitedMember = validExsitedMember(member_id);
		if(validExsitedMember!=null){
			return validExsitedMember;
		}
		List<String> member_list = super.findList("communication.communicationCarInfo.validMember",member_id);
		
		if(member_list!=null&& member_list.size()>0){
			return "06";
		}
		return "00";
	}
	
	public String validExsitedMember(String member_id){
		Integer count = super.findInteger("communication.communicationCarInfo.validExistMember",member_id);
		if(count>0){
			return "08";
		}
		return null;
	}

	/**
	 * 
	   * @Title : findParkPlace 
	   * @功能描述: 查询车位数量
	   * @传入参数：@param bean
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public ParkPlaceInfoBean findParkPlace(ObjectMap map) {
//		int allParkPlace = super.findInteger("NO_LOG.communication.communicationParkPlace.findAllSpace",map);
//		int parkCarNumber = super.findInteger("NO_LOG.communication.communicationParkPlace.countParkSpace");
		ObjectMap result = super.findObj("NO_LOG.communication.communicationParkPlace.findAreaSpace",map);
		//返回停车场停车位信息
		ParkPlaceInfoBean parkPlaceInfoBean = new ParkPlaceInfoBean();
		parkPlaceInfoBean.setType("D5");
		parkPlaceInfoBean.setExecute_status("1");
		parkPlaceInfoBean.setAll_space(String.valueOf(result.getInt("totalspace")));
		parkPlaceInfoBean.setRest_space(String.valueOf(result.getInt("totalspace")-result.getInt("fixcar")-result.getInt("tmpcar")));
		parkPlaceInfoBean.setFixed_car(String.valueOf(result.getInt("fixcar")));
		parkPlaceInfoBean.setTemp_car(String.valueOf(result.getInt("tmpcar")));
		
		/*parkPlaceInfoBean.setAll_space(String.valueOf(allParkPlace));
		parkPlaceInfoBean.setRest_space(String.valueOf(allParkPlace-parkCarNumber));
		parkPlaceInfoBean.setFixed_car(String.valueOf(0));
		parkPlaceInfoBean.setTemp_car(String.valueOf(parkCarNumber));*/
		return parkPlaceInfoBean;
	}

	
	/**
	 * 
	   * @Title : confirmPay 
	   * @功能描述: 确认移动支付功能
	   * @传入参数：@param bean
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	@Transactional
	public ReceivePayBean confirmPay(PayResultBean bean) {
		ReceivePayBean receivePayBean = new ReceivePayBean();
		if (bean.getExecute_status() != 1 || StringUtil.isEmpty(bean.getMv_license())) {
			receivePayBean.setExecute_status("2");
			return receivePayBean;
		}
		ObjectMap objectMap = ObjectMap.newInstance();
		//支付时间
		objectMap.put("pay_time",bean.getGmt_payment());
		//金额
		objectMap.put("prepay_bill",bean.getBuyer_pay_amount());
		//车牌
		objectMap.put("mv_license",bean.getMv_license());
		//车牌颜色
//		objectMap.put("carColor",bean.getCar_color());
		
		//赋值交易类型:3微信，4支付宝
		objectMap.put("pay_method", bean.getPay_method());
		/*if(bean.getOut_trade_no().startsWith("WX") || bean.getOut_trade_no().startsWith("wx"))
		{
			objectMap.put("payMethod", 3);
		}
		if(bean.getOut_trade_no().startsWith("ZB") || bean.getOut_trade_no().startsWith("zb"))
		{
			objectMap.put("payMethod", 4);
		}*/
		objectMap.put("pay_status", 1);
		objectMap.put("order_id",bean.getOut_trade_no());
		//判断是否已经存在订单号
		int orderCount = super.findObj(("communication.communicationOrderCar.findCountByOrderId"), objectMap);
		if(orderCount >= 1)
		{
			receivePayBean.setMv_license(bean.getMv_license());
			receivePayBean.setCar_color(String.valueOf(bean.getCar_color()));
			receivePayBean.setPark_id(bean.getPark_id());
			receivePayBean.setExecute_status("1");
			return receivePayBean;
		}
		
		int count = super.update("communication.communicationTempCost.updateStatus", objectMap);
		
		//是否成功更新
		if(count < 1)
		{
			receivePayBean.setExecute_status("2");
			//赋值会送给移动服务
			receivePayBean.setMv_license(bean.getMv_license());
			receivePayBean.setCar_color(String.valueOf(bean.getCar_color()));
			receivePayBean.setPark_id(bean.getPark_id());
			return receivePayBean;
		}
		
		//赋值会送给移动服务
		receivePayBean.setMv_license(bean.getMv_license());
		receivePayBean.setCar_color(String.valueOf(bean.getCar_color()));
		receivePayBean.setPark_id(bean.getPark_id());
		
		//判断是否已经存在订单号
		/*int orderCount = super.findObj(("communication.communicationOrderCar.findCountByOrderId"), objectMap);
		if(orderCount >= 1)
		{
			receivePayBean.setExecute_status("1");
			return receivePayBean;
		}*/
		
		//查询临时计费
		CommunicationTempCostBean tempCostBean = super.findObj("communication.communicationTempCost.findParkInfo", objectMap);
//		objectMap.put("order_id",bean.getOut_trade_no());
		objectMap.put("trade_no",bean.getTrade_no());
		objectMap.put("trade_type",bean.getTrade_type());
		objectMap.put("mv_license",bean.getMv_license());
		objectMap.put("plate_color",tempCostBean.getCar_color());
		objectMap.put("park_id",tempCostBean.getPark_id());
		objectMap.put("area_id",tempCostBean.getArea_id());
		objectMap.put("entry_lane",tempCostBean.getEntry_lane());
		objectMap.put("entry_time",tempCostBean.getEntry_time());
		objectMap.put("pay_bill", bean.getBuyer_pay_amount());
		objectMap.put("pay_time", bean.getGmt_payment());
		objectMap.put("deal_time", bean.getDeal_time());
		objectMap.put("pay_method", bean.getPay_method());
		objectMap.put("service_method", 1);
		objectMap.put("is_synchro", 0);
		objectMap.put("is_refund", 0);
		objectMap.put("is_vaild", 1);
		int insertCount = super.insert(("communication.communicationOrderCar.insert"), objectMap);
		if(insertCount > 0)
		{
			// 按照车牌的查询条件
			objectMap.put("mvLicense", bean.getMv_license());
			WhiteListMngBean whiteListMngBean = super.findObj("communication.communicationTempCost.findWhiteByLicense", objectMap);
			
			// 判断支付类型为内部计费，并且计费编号为42（第二种员工内部计费）
			if(whiteListMngBean != null && whiteListMngBean.getToll_type() == 2 && "42".equals(whiteListMngBean.getCharge_code()))
			{
//				int exitToll = super.findInteger("communication.communicationTempCost.findExitToll", queryParam);
//				int tempcostToll = super.findInteger("communication.communicationTempCost.findTempCostToll", queryParam);
				
				// 支付金额
				objectMap.put("pay_toll", bean.getBuyer_pay_amount());
				// 停车时长
				objectMap.put("park_time", (DateUtil.parse4yMdHms(bean.getGmt_payment()).getTime()-tempCostBean.getEntry_time().getTime())/1000);
				// 支付时间
				objectMap.put("paytime", bean.getGmt_payment());
				
				super.update("communication.communicationTempCost.whiteListMonthPay", objectMap);
			}
		}
		// 判断插入成功
		receivePayBean.setExecute_status("1");
		return receivePayBean;
	}

	/**
	 * 
	   * @Title : calculateToll 
	   * @功能描述: 返回计费
	   * @传入参数：@param startTime
	   * @传入参数：@param endTime
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public Map calculateToll(Date startTime, Date endTime,int carType,String charge_code,int count) {
		
		Map map = new HashMap();
//		long toll = 0;
		// 判断是否为苏大内部计费
		if(charge_code != null && charge_code.equals("23"))
		{
			SpecTLChargeRuleBean spec = new SpecTLChargeRuleBean();
			map = spec.calculateToll(startTime, endTime, carType, count);
//			toll = Long.parseLong(map.get("toll").toString());
		}
		else{
			//获得按时间规则方式1
			ChargeRuleFactory chargeRuleFactory = new ChargeRuleFactory();
			ChargeRuleXml chargeRuleXml = chargeRuleFactory.createChargeRule(charge_code);
			long toll = chargeRuleXml.calculateToll(startTime, endTime,carType);
			map.put("toll", toll);
		}
		return map;
	}

	/**
	 * 
	   * @Title : findExistsWhite 
	   * @功能描述: 判断是否为白名单
	   * @传入参数：@param mvLicense
	   * @传入参数：@param carColor
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public boolean findExistsWhite(String mvLicense, int carColor,String obuId) {
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.put("mvLicense", mvLicense);
//		queryParam.put("carColor", carColor);
//		queryParam.put("vehicleClass", vehicleClass);
		queryParam.put("obuId", obuId);
		// 查询白名单是否存在相应车牌
		int countByLicense = super.findInteger(ns("findExistsWhiteByLicense"), queryParam);
		if(countByLicense > 0)
		{
			return true;
		}
		
		if(obuId!=null && !"".equals(obuId))
		{
			// 查询白名单是否存在相应obu
			int countByOBUId = super.findInteger(ns("findExistsWhiteByOBUId"), queryParam);
			if(countByOBUId > 0)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean findExistMemberSale(String mvLicense, int carColor) {
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.put("mvLicense", mvLicense);
		queryParam.put("carColor", carColor);
		int count = super.findInteger(ns("findExistsWhitefindExistsWhite"), queryParam);
		if(count > 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * 
	   * @Title : validBlackListOut 
	   * @功能描述: 黑名单查询
	   * @传入参数：@param card_id
	   * @传入参数：@param mv_license
	   * @传入参数：@param obuId
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public String validBlackListOut(String card_id, String mv_license, String obuId,String black_list_province) {
		// TODO Auto-generated method stub
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.put("query_License", mv_license);
		if(card_id != null && card_id.length() == 20)
		{
			queryParam.put("query_CardID", card_id.substring(4, card_id.length()));
		}
		else
		{
			queryParam.put("query_CardID", "");
		}
		queryParam.put("query_OBUID", obuId);
		queryParam.put("query_black_list_province", black_list_province);
		
		long start = System.currentTimeMillis();
//		logger.info(start+"查询ETC黑名单数据库开始时间");
		int count = super.findInteger("backMng.admin.voidCard.EtcBlackListMng.findEtcListByCardLicenseOBU", queryParam);
		long end = System.currentTimeMillis();
		if(count > 0)
		{
			logger.info("黑名单查询时间:"+(end-start)+"毫秒");
			//etc黑名单
			return "03";
		}
//		int count2 = super.findInteger(ns("findEtcList"), query2);
//		int count3 = super.findInteger(ns("findEtcList"), query3);
//		logger.info(end+"查询ETC黑名单数据库结束时间"+",count1="+count);
//		logger.info(end+"查询ETC黑名单数据库结束时间"+",count1="+count1+"count2="+count2+"count3="+count3);
		logger.info("黑名单查询时间:"+(end-start)+"毫秒");
		return null;
	}

	/**
	 * 
	   * @Title : findWhiteVehicleClass 
	   * @功能描述: 获得白名单车辆类型
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public WhiteListMngBean findWhiteByLicense(String mvLicense,String obuId) {
		// TODO Auto-generated method stub
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.put("mvLicense", mvLicense);
//		queryParam.put("carColor", carColor);
//		queryParam.put("vehicleClass", vehicleClass);
		queryParam.put("obuId", obuId);
		// 查询白名单是否存在相应车牌
		WhiteListMngBean whiteListMngBean = super.findObj("communication.communicationTempCost.findWhiteByLicense", queryParam);
		
		// 根据obuid查询
		if(whiteListMngBean == null && obuId != null && !"".equals(obuId))
		{
			return (WhiteListMngBean)super.findObj("communication.communicationTempCost.findWhiteByOBUId", queryParam);
		}
		else
		{
			return whiteListMngBean;
		}
	}
	
	/**
	 * 
	   * @Title : findWhiteVehicleClass 
	   * @功能描述: 获得白名单车辆类型
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public WhiteListMngBean findAllVaildWhiteByLicense(String mvLicense,String obuId,String areaId,String laneId) {
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.put("mvLicense", mvLicense);
		queryParam.put("obuId", obuId);
		// 查询白名单是否存在相应车牌
		WhiteListMngBean whiteListMngBean = super.findObj("communication.communicationTempCost.findAllVaildWhiteByLicense", queryParam);
		
		// 根据obuid查询
		if(whiteListMngBean == null && obuId != null && !"".equals(obuId))
		{
			return (WhiteListMngBean)super.findObj("communication.communicationTempCost.findAllVaildWhiteByOBUId", queryParam);
		}
		else
		{
			return whiteListMngBean;
		}
	}
	

	
	/**
	 * 
	   * @Title : findWhiteVehicleClass 
	   * @功能描述: 获得白名单车辆类型
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public WhiteListMngBean findWhiteByLicense(String mvLicense,String obuId,String areaId,String laneId) {
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.put("mvLicense", mvLicense);
		// 判断车道是否存在，不存在则只判断区域
		/*if(laneId != null && !"".equals(laneId))
		{
			queryParam.put("laneInfo", '|'+areaId+'-'+laneId+"|");
		}
		else
		{
			queryParam.put("laneInfo", '|'+areaId);
		}*/
//		queryParam.put("carColor", carColor);
//		queryParam.put("vehicleClass", vehicleClass);
		queryParam.put("obuId", obuId);
		// 查询白名单是否存在相应车牌
		WhiteListMngBean whiteListMngBean = super.findObj("communication.communicationTempCost.findWhiteByLicense", queryParam);
		
		// 根据obuid查询
		if(whiteListMngBean == null && obuId != null && !"".equals(obuId))
		{
			return (WhiteListMngBean)super.findObj("communication.communicationTempCost.findWhiteByOBUId", queryParam);
		}
		else
		{
			return whiteListMngBean;
		}
	}
	
	/**
	 * 
	   * @Title : updateTempCostPayStatus 
	   * @功能描述: 更新临时计费支付状态
	   * @传入参数：@param tempCostBean
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public OutBaseJsonBean updateTempCostPayStatus(ObjectMap objectMap) {
		OutBaseJsonBean bean = new OutBaseJsonBean();
		bean.setNow_time(String.valueOf(System.currentTimeMillis()));
		bean.setType("D6");
		
		// 获得临时计费计费信息
		TempCostBean tempCostBean = super.findObj(ns("findTempCost"), objectMap);
		
		// 判断金额是否一致
		if(tempCostBean.getIsModel() != null && tempCostBean.getIsModel() == 1 &&
				Integer.parseInt(objectMap.get("prepay_bill").toString()) < 0	&&
				tempCostBean.getPrepayBill() == Math.abs(Integer.parseInt(objectMap.get("prepay_bill").toString())))
		{
			int count = super.delete(ns("deleteTempCostBymvlicense"), objectMap);
			if(count > 0)
			{
				//退款成功
				bean.setExecute_status("1");
				return bean;
			}
			//退款失败
			bean.setExecute_status("0");
			return bean;
		}
			
		// 判断金额是否一致
		if(Integer.parseInt(objectMap.get("prepay_bill").toString()) < 0	&&
			tempCostBean.getPrepayBill() < Math.abs(Integer.parseInt(objectMap.get("prepay_bill").toString())))
		{
			//退款失败
			bean.setExecute_status("0");
			return bean;
		}
		int count = super.update("communication.communicationTempCost.updateStatus", objectMap);
		//更新成功
		if(count > 0)
		{
			bean.setExecute_status("1");
			return bean;
		}
		//更新失败
		bean.setExecute_status("0");
		return bean;
	}

	/**
	 *   
	   * @Title : findTempCostByLaneTime 
	   * @功能描述: 按照入口时间和车道查询临时计费信息
	   * @传入参数：@param tempCostBean
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public TempCostBean findTempCostByLaneTime(ObjectMap objectMap) {
		int count = super.findInteger(ns("countTempCostByLaneTime"),objectMap);
		if(count <= 0)
		{
			return null;
		}
		List<TempCostBean> list = super.findList(ns("findTempCostByLaneTime"),objectMap);
		return list.get(0);
	}

	/**
	 * 
	   * @Title : findAreaPayFreeTime 
	   * @功能描述: 根据停车场编号和区域编号查询剩余毫秒数
	   * @传入参数：@param parkId
	   * @传入参数：@param areaId
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public CommAreaInfoBean findArea(String parkId, String areaId) {
		ObjectMap objectMap=ObjectMap.newInstance();
		objectMap.put("park_id", parkId);
		objectMap.put("area_id", areaId);
		List<AreaInfoBean> areaInfoBeans = super.findList("communication.communicationTempCost.findArea", objectMap);
		if(areaInfoBeans == null || areaInfoBeans.size() <= 0)
		{
			return null;
		}
		AreaInfoBean areaInfoBean = areaInfoBeans.get(0);
		String payTime = areaInfoBean.getPay_free_time();
		String[] payTimeArray = payTime.split(":");
		
		long hour = Long.parseLong(payTimeArray[0]);
		long min = Long.parseLong(payTimeArray[1]);
		long second = Long.parseLong(payTimeArray[2]);
		CommAreaInfoBean bean = new CommAreaInfoBean();
		bean.setPay_free_time(hour*60*60+min*60+second);
		bean.setCharge_code(areaInfoBean.getCharge_code());
		//返回秒数
		return bean;
	}

	/**
	 * 
	   * @Title : savePayTempCost 
	   * @功能描述: 新增收费
	   * @传入参数：@param objectMap
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public boolean saveModelPayTempCost(ObjectMap objectMap) {
		// 保存数据库
		super.insert("communication.communicationTempCost.savePayTempCost", objectMap);
		
		// 根据车牌查询是否存在
		ObjectMap map=ObjectMap.newInstance();
		map.put("mv_license", objectMap.get("mv_license"));
		int count = super.findInteger("communication.communicationCarInfo.findMvlicenseLiTempCost",map);
		if(count > 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * 
	   * @Title : findEntryImage 
	   * @功能描述: 返回入口图片
	   * @传入参数：@param mv_license
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public String findEntryImage(String mv_license) {
		// 根据车牌查询是否存在
		ObjectMap map=ObjectMap.newInstance();
		map.put("mv_license", mv_license);
		// 查询入口图片路径
		List<String> entryImages = super.findList("communication.communicationTempCost.findEntryImage", map);
		// 返回第一条图片信息
		String entryImage = entryImages.get(0);
		String[] entryImageArray= entryImage.split("-");
		String filePath = PropertiesUtil.get("IMAGE.FILEPATH")+entryImageArray[0]+"//"+entryImageArray[1].substring(0, 8)+"//"+entryImage;
		String str = "";
		try {
			str = ImgHelper.encodeImage(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	
	/**
	 * 
	   * @Title : findTriggerLicense 
	   * @功能描述: 根据车道编号返回车牌信息
	   * @传入参数：@param laneid
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public String findTriggerLicense(int lane_id) {
		// 根据车牌查询是否存在
		ObjectMap map=ObjectMap.newInstance();
		map.put("lane_id", lane_id);
		String mv_license = super.findString("communication.communicationTempCost.findTriggerLicense",map);
		return mv_license;
	}

	/**
	 * 
	   * @Title : findTriggerIp 
	   * @功能描述: 根据车道查询当前车道ip
	   * @传入参数：@param laneid
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public String findTriggerIp(int lane_id) {
		ObjectMap map=ObjectMap.newInstance();
		map.put("lane_id", lane_id);
		String ip = super.findString("communication.communicationTempCost.findTriggerIp",map);
		return ip;
	}

	/**
	 * 
	   * @Title : findCouponByVerify_code 
	   * @功能描述: 根据验证码返回优惠券信息
	   * @传入参数：@param verifyCode
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public CouponMngBean findCouponByVerifyCode(String verify_code) {
		return super.findObj("communication.communicationTempCost.findCouponByVerifyCode",verify_code);
	}

	
	/**
	 * 
	   * @Title : findCouponByVerifyCode 
	   * @功能描述: TODO
	   * @传入参数：@param objectMap
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public CouponMngBean findCouponByVerifyCode(ObjectMap objectMap) {
		String verify_code = super.findString("communication.communicationTempCost.findVerifyCodeByTmpTrigger",objectMap);
		if(verify_code == null || verify_code == "")
		{
			return null;
		}
		return super.findObj("communication.communicationTempCost.findCouponByVerifyCode",verify_code);
	}

	@Override
	public WhiteListInfoBean queryWhiteByLicense(String mv_license,String obu_id) {
		ObjectMap map=ObjectMap.newInstance();
		map.put("mv_license", mv_license);
		map.put("obu_id", obu_id);
		List<WhiteListInfoBean> list = super.findList("communication.communicationTempCost.queryWhiteByLicense", map);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public TempCostInfoBean queryTempByUserNumber(String user_number) {
		return super.findObj("communication.communicationTempCost.queryTempByUserNumber", user_number);
	}
	
	@Override
	public TempCostInfoBean queryTempByLicense(String mv_license) {
		List<TempCostInfoBean> list = super.findList("communication.communicationTempCost.queryTempByLicense", mv_license);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
//		return super.findObj("communication.communicationTempCost.queryTempByLicense", mv_license);
	}
	
	@Override
	public void updateFreeCount(ObjectMap objectMap) {
		super.update("communication.communicationTempCost.updateFreeCount", objectMap);
	}

	/**
	 * 
	   * @Title : updateWhiteListCarport 
	   * @功能描述: 更新白名单停车库存
	   * @传入参数：@param map
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public void whiteListEntry(TempCostBean tempCostBean,int canUserPlace) {
		// 根据车牌查询是否存在
		ObjectMap map=ObjectMap.newInstance();
		map.put("mv_license", tempCostBean.getMvLicense());
		map.put("obu_id", tempCostBean.getObuId());
		// 更新临时计费表
		map.put("park_id", tempCostBean.getParkId());
//		map.put("area_id", tempCostBean.getAreaId());
		
		// 如果存在已有改车牌的车位，是否增加车位数
		boolean existsFlag = false;
		
		if(!"无牌车".equals(tempCostBean.getMvLicense()))
		{
			int count = super.findInteger(ns("countTempCostByPrimary"), map);
			if(count>0)
			{
//				String userNum = super.findString("communication.communicationTempCost.userNumTempCostByLicense",map);
				// 判断原先存放的车位编号是否一致
				/*if(!StringUtil.isEmpty(userNum) && userNum.equals(tempCostBean.getSpare4()))
				{
					existsFlag = false;
				}*/
				existsFlag = true;
				super.insert(ns("insertTempCostUncertain"),tempCostBean);
				super.delete(ns("deleteTempCostBylicense"), map);
			}
		}
		// 存在原有数据，去除spare1的车位状态
		/*if(existsFlag)
		{
			// 去除spare1的车位状态
			tempCostBean.setSpare1(null);
		}*/
		super.insert(ns("insertTempCost"), tempCostBean);
		// 存在工号则更新临时计费表的剩余可用车位数
		if(!StringUtil.isEmpty(tempCostBean.getSpare4()) && existsFlag)
		{
			// 纠正车位数
			// 根据入口时间递增排列更新拥有车位数的相同车位编号
			// 存在车位则查找temp_cost记录个数
			map.put("spare1", 1);
			map.put("spare4", tempCostBean.getSpare4());
			super.update("communication.communicationTempCost.updateTCUsePlaceByNum",map);
			
			// 根据入口时间递增排列更新拥有车位数的相同车位编号
			map.put("spare1", 0);
			map.put("canuse_place", canUserPlace);
			super.update("communication.communicationTempCost.updateTCUsePlaceByNumCanUsePlace",map);
			
			// 根据车位编号查询tempcost个数
			int useParks = super.findInteger("communication.communicationTempCost.countTempCostByUserName",map);
			
			// 更新到白名单
			map.put("use_place", useParks);
			map.put("user_number", tempCostBean.getSpare4());
			super.update("communication.communicationTempCost.updateWhiteSpaceByUserName",map);
			return;
		}
		map.put("user_number", tempCostBean.getSpare4());
		map.put("step", -1);
		super.update("communication.communicationTempCost.updateWhiteSpaceByUserNum", map);
		return;
	}

	
	/**
	 *  
	   * @Title : whiteListExit 
	   * @功能描述: 白名单车辆出去，更新剩余车位数
	   * @传入参数：@param map
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public void whiteListExit(ObjectMap objectMap) {
		// 判断车辆类型,8为一人多车白名单
//		if(objectMap.get("valid_type") != null && Integer.parseInt(objectMap.get("valid_type").toString()) == 8)
//		{
			// 根据车牌获得车位编号spare4和车位状态spare1
			Map map = super.findObj("communication.communicationTempCost.findTCNumStatusByLicense",objectMap);
			// 判断车位编号不为空
			if(map != null && map.get("spare4")!=null 
					&& !StringUtil.isEmpty(map.get("spare4").toString()))
			{
				// 车位状态
				int useStatus = Integer.parseInt(map.get("spare1").toString());
				// 车位编号
				String spare4 = map.get("spare4").toString();
				// 判断车位编号不为空，并且车位状态为免费或则部分免费
				if(!StringUtil.isEmpty(spare4) && (useStatus == 0 || useStatus == 2))
				{
					objectMap.put("spare4", spare4);
					// 根据车位编号、车位状态排序修改入口时间第一条数据,spare1=2,begin_time等于出去时间
					super.update("communication.communicationTempCost.updateTCNumStatusByNum", objectMap);
				}
				
				// 判断车位编号是否为空
				if(!StringUtil.isEmpty(spare4))
				{
					map.put("user_number", spare4);
					map.put("step", 1);
					// 修改白名单剩余车位，剩余车位加一
					super.update("communication.communicationTempCost.updateWhiteSpaceByUserNum", map);
				}
			}
//		}
		// 根据车牌删除临时计费表
		super.delete(ns("deleteTempCostBymvlicense"), objectMap);
	}
}
