package com.project.communication.service;

import java.util.Date;
import java.util.Map;

import com.project.backCharge.tempCharge.model.TempCostBean;
import com.project.backMng.admin.coupon.model.CouponMngBean;
import com.project.backMng.admin.whiteListMng.model.WhiteListMngBean;
import com.project.communication.model.CommAreaInfoBean;
import com.project.communication.model.CommunicationCarInfoBean;
import com.project.communication.model.OutBaseJsonBean;
import com.project.communication.model.ParkPlaceInfoBean;
import com.project.communication.model.PayResultBean;
import com.project.communication.model.ReceivePayBean;
import com.project.communication.model.TempCostInfoBean;
import com.project.communication.model.WhiteListInfoBean;
import com.redoak.jar.base.model.ObjectMap;

public interface CommunicationService {

	
	public void addTempCost(TempCostBean tempCostBean);
	
	public CommunicationCarInfoBean findMvlicense(String mv_license,Integer car_color);
	
	public CommunicationCarInfoBean findObuId(String mv_license,Integer car_color);
	
	public TempCostBean findTempCost(ObjectMap objectMap);
	
	public void deleteTempCostBymvlicense(ObjectMap objectMap);
	
	public Integer findMvlicenseLiTempCost(ObjectMap objectMap);
	
	public String validCard(String mv_license,String plate_color);
	
	public String validWhiteList(String mv_license,Integer car_color);
	
	public String validMember(String mv_license, Integer car_color);

	public String validCardOut(String mv_license,String plate_color,String obuId) ;
	
	public String validBlackListOut(String card_id,String mv_license,String obuId,String black_list_province);
	
	public ParkPlaceInfoBean findParkPlace(ObjectMap map);
	
	public ReceivePayBean confirmPay(PayResultBean bean);
	
	public void addOrUpdateTempCost(TempCostBean tempCostBean);
	
	public void whiteListEntry(TempCostBean tempCostBean,int canUserPlace);
	
	public void whiteListExit(ObjectMap map);
	
	public Map calculateToll(Date startTime,Date endTime,int carType,String charge_code,int count);
	
	public void updateFreeCount(ObjectMap objectMap);
	
	public boolean findExistsWhite(String mvLicense, int carColor,String obuId);
	
	public WhiteListMngBean findWhiteByLicense(String mvLicense,String obuId);
	
	public WhiteListMngBean findWhiteByLicense(String mvLicense,String obuId,String areaId,String laneId);
	
	// 入口时根据车牌或者obuid查询是否存在白名单，可查询出不在时效期间内的数据
	public WhiteListMngBean findAllVaildWhiteByLicense(String mvLicense,String obuId,String areaId,String laneId);
	
	public boolean findExistMemberSale(String mvLicense, int carColor);
	
	public OutBaseJsonBean updateTempCostPayStatus(ObjectMap tempCostBean);
	
	public TempCostBean findTempCostByLaneTime(ObjectMap objectMap);
	
	public CommAreaInfoBean findArea(String parkId,String areaId);
	
	public boolean saveModelPayTempCost(ObjectMap objectMap);
	
	public String findEntryImage(String mv_license);
	
	public String findTriggerLicense(int lane_id);
	
	public String findTriggerIp(int lane_id);
	
	public CouponMngBean findCouponByVerifyCode(String verifyCode);
	// 根据车道车牌信息返回优惠券是否有效
	public CouponMngBean findCouponByVerifyCode(ObjectMap objectMap);
	/**
	 * 根据车牌查询白名单信息
	 * @param mv_license 车牌
	 * @return
	 */
	public WhiteListInfoBean queryWhiteByLicense(String mv_license,String obu_id);
	/**
	 * 根据业主编号查询最新的临时计费信息
	 * @param user_number 业主编号
	 * @return
	 */
	public TempCostInfoBean queryTempByUserNumber(String user_number);
	/**
	 * 根据车牌查询临时计费信息
	 * @param mv_license 车牌号
	 * @return
	 */
	public TempCostInfoBean queryTempByLicense(String mv_license);
}
