package com.project.communication.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.backCharge.tempCharge.model.TempCostBean;
import com.project.backMng.admin.coupon.model.CouponMngBean;
import com.project.backMng.admin.whiteListMng.model.WhiteListMngBean;
import com.project.backMng.platuser.sys.LaneInfoMng.model.LaneInfoBean;
import com.project.common.tool.StringUtil;
import com.project.communication.model.CommAreaInfoBean;
import com.project.communication.model.InputDataBean;
import com.project.communication.model.InputFindPlaceBean;
import com.project.communication.model.InsideJsonBean;
import com.project.communication.model.OutBaseJsonBean;
import com.project.communication.model.OutsideJsonBean;
import com.project.communication.model.ParkPlaceInfoBean;
import com.project.communication.model.PayResultBean;
import com.project.communication.model.PaymentChannelBean;
import com.project.communication.model.ReceiveModelChargeBean;
import com.project.communication.model.ReceiveModelPayBean;
import com.project.communication.model.ReceivePayBean;
import com.project.communication.model.ResponseExecuteStatus;
import com.project.communication.model.ResponseModelCargeBean;
import com.project.communication.model.SpecialResultBean;
import com.project.communication.model.TempCostInfoBean;
import com.project.communication.model.WhiteListInfoBean;
import com.project.communication.service.CommunicationService;
import com.project.communication.service.TrafficRecordService;
import com.project.tools.DateUtil;
import com.project.tools.PostOrGet;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.springmvc.action.BaseController;
import com.redoak.jar.util.PropertiesUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("valid")
public class CommunicationAction extends BaseController {
	
	private final static Logger log = LogManager.getLogger(CommunicationAction.class);
	/**
	 * service
	 */
	private CommunicationService communicationService;

	private TrafficRecordService trafficRecordService;

	public TrafficRecordService getTrafficRecordService() {
		return trafficRecordService;
	}

	public void setTrafficRecordService(TrafficRecordService trafficRecordService) {
		this.trafficRecordService = trafficRecordService;
	}

	/**
	 * 
	 * @Title : validIn
	 * @功能描述: 车辆进入车道时，服务端接受车辆数据进行校验
	 * @传入参数：@param request
	 * @传入参数：@param response
	 * @传入参数：@return
	 * @返回类型：String
	 * @throws ：
	 */
	@ResponseBody
	@RequestMapping(value = "/validIn")
	public String validIn(HttpServletRequest request, HttpServletResponse response) {
		log.info("收到进入车辆消息");
		try {
			InputDataBean bean = receivePost(request);
			log.info(DateUtil.get4yMdHms(new Date()) + "车辆进入" + responseJsonText(bean));

			// 判断obu或者车牌号是否都为空
			if ((bean.getObu_id() == null || StringUtil.isEmpty(bean.getObu_id()))
					&& (bean.getMv_license() == null || StringUtil.isEmpty(bean.getMv_license()))) {
				log.info(DateUtil.get4yMdHms(new Date()) + "进入道口返回信息："
						+ responseJsonText(ResultBean.newErrorResult("mv_license & obu_id:null")));
				return responseJsonText(ResultBean.newErrorResult("mv_license & obu_id:null"));
			}
			String mvLicense = bean.getMv_license();
			InsideJsonBean insideJsonBean = new InsideJsonBean();
			insideJsonBean.setType("D1");
			insideJsonBean.setMv_license(mvLicense);// 增加返回车牌
			insideJsonBean.setObu_id(bean.getObu_id());// 返回oubId
			insideJsonBean.setUser_type("1");
			insideJsonBean.setExecute_status("1");

			insideJsonBean.setMv_license(mvLicense);// 增加返回车牌

			insideJsonBean.setNow_time(String.valueOf(System.currentTimeMillis() / 1000));

			insideJsonBean.setValid_type("00");// 赋值默认的用户类型00
			
			insideJsonBean.setCar_id(bean.getCard_id());//赋值卡编号

			// 默认赋值传入的车型
			insideJsonBean.setVehicle_class(String.valueOf(bean.getVehicle_class()));
			// 判断是否需要校验入口是否存在该车牌
			if ('1' == (PropertiesUtil.get("ENTRY_VALID").charAt(0))) {
				
				// 根据停车场区域车牌获取数量
				ObjectMap objectMap=ObjectMap.newInstance();
				objectMap.put("park_id", bean.getPark_id());
				objectMap.put("area_id", bean.getArea_id());
				objectMap.put("mv_license", bean.getMv_license());
				// 查询是否存在该车牌临时计费
				Integer count = communicationService.findMvlicenseLiTempCost(objectMap);
				if (count > 0) {
					insideJsonBean.setExecute_status("2");
				}
			}

			if ('1' == (PropertiesUtil.get("ENTRY_VALID").charAt(1))) {
				// ①验证黑名单（公安/本地黑名单）
				String valid_type = communicationService.validCard(bean.getMv_license(),
						bean.getCar_color().toString());
				if (valid_type != null) {
					insideJsonBean.setValid_type(valid_type);
					log.info(DateUtil.get4yMdHms(new Date()) + "进入道口返回信息：黑名单用户-" + responseJsonText(insideJsonBean));
					return responseJsonText(insideJsonBean);
				}
			}

			// ②验证白名单
			// String valid_type_white =
			// communicationService.validWhiteList(bean.getMv_license(),
			// bean.getCar_color());
			// log.info(valid_type_white);
			// if (valid_type_white != null) {
			// insideJsonBean.setValid_type(valid_type_white);
			// log.info(DateUtil.get4yMdHms(new Date()) + "入口车道返回:" +
			// responseJsonText(insideJsonBean));
			// return responseJsonText(insideJsonBean);
			// }

			// 判断白名单
			// boolean flag = communicationService.findExistsWhite(mvLicense,
			// bean.getCar_color(), bean.getObu_id());

			// boolean flag = false;
			if ('1' == (PropertiesUtil.get("ENTRY_VALID").charAt(2)) 
					|| '2' == (PropertiesUtil.get("ENTRY_VALID").charAt(2))) {
				insideJsonBean.setWl_type("0");
				WhiteListMngBean whiteListMngBean = communicationService.findAllVaildWhiteByLicense(mvLicense,
						bean.getObu_id(),bean.getArea_id(),bean.getLane_id());
				if (whiteListMngBean != null) {
					
					// 赋值为失效的白名单
					insideJsonBean.setWl_type("2");
					
					
					// 判断是否过期
					Date now = new Date();
					if(whiteListMngBean.getValid_start_time().getTime() <= now.getTime()
							&& whiteListMngBean.getValid_end_time().getTime() >= now.getTime())
					{
						// 判断用户类型，5为免费白名单
						if(whiteListMngBean.getToll_type() == 1)
						{
							insideJsonBean.setValid_type("5");
						}
						
						// 8为一户多车白名单
						if(whiteListMngBean.getToll_type() == 2)
						{
							insideJsonBean.setValid_type("8");
						}
						
						// 一人多车白名单不存在剩余车位
						if(whiteListMngBean.getToll_type() == 2 && whiteListMngBean.getSpare2() <= 0)
						{
							insideJsonBean.setWl_type("3");
						}
					}
					insideJsonBean.setVehicle_class(String.valueOf(whiteListMngBean.getVehicle_class()));
					log.info(DateUtil.get4yMdHms(new Date()) + "入口车道返回:" + responseJsonText(insideJsonBean));
					return responseJsonText(insideJsonBean);
				}
			}

			// ③验证是否为会员
			if ('1' == (PropertiesUtil.get("ENTRY_VALID").charAt(3))) {
				String valid_member = communicationService.validMember(bean.getMv_license(), bean.getCar_color());
				log.info(valid_member);
				if (valid_member != null) {
					insideJsonBean.setValid_type(valid_member);
					log.info(DateUtil.get4yMdHms(new Date()) + "入口车道返回:" + responseJsonText(insideJsonBean));
					return responseJsonText(insideJsonBean);
				}
			}

			log.info(DateUtil.get4yMdHms(new Date()) + "入口车道返回:" + responseJsonText(insideJsonBean));
			return responseJsonText(insideJsonBean);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return responseJsonText(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title : validOut
	 * @功能描述: 1.车辆出停车场时，校验车辆 2.可根据车辆信息查询计费结果
	 * @传入参数：@param request
	 * @传入参数：@param response
	 * @传入参数：@return
	 * @返回类型：String
	 * @throws ：
	 */
	@ResponseBody
	@RequestMapping(value = "/validOut")
	public String validOut(HttpServletRequest request, HttpServletResponse response) {

		Date nowTime = new Date();
		String mvLicense = null;

		try {
			InputDataBean bean = receivePost(request);

			// 暂时写死颜色，后期修改
			OutsideJsonBean outsideJsonBean = new OutsideJsonBean();

			if (bean.getType() != null && "E2".equals(bean.getType())) {
				log.info(DateUtil.get4yMdHms(new Date()) + "车辆离开：" + responseJsonText(bean));
				outsideJsonBean.setType("D2");
			} else if (bean.getType() != null && "F2".equals(bean.getType())) {
				log.info(DateUtil.get4yMdHms(new Date()) + "移动支付查询计费信息：" + responseJsonText(bean));
				outsideJsonBean.setType("D2");
			} else if (bean.getType() != null && "E5".equals(bean.getType())) {
				log.info(DateUtil.get4yMdHms(new Date()) + "集中支付信息：" + responseJsonText(bean));
				outsideJsonBean.setType("D5");
			}
			else {
				log.info(DateUtil.get4yMdHms(new Date()) + "查询计费信息：" + responseJsonText(bean));
			}
			
			
			// 判断是否为白名单一户多车牌校验
			if('2' == (PropertiesUtil.get("EXIT_VALID").charAt(2)))
			{
				Map map = new HashMap();
				if(bean.getMv_license() !=null){
					map.put("mv_license", bean.getMv_license());
				}
				if(bean.getObu_id() !=null){
					map.put("obu_id", bean.getObu_id());
				}
				if(bean.getCar_color() !=null){
					map.put("car_color", bean.getCar_color());
				}
				if(bean.getVehicle_class() !=null){
					map.put("vehicle_class",bean.getVehicle_class());
				}
				if(bean.getCard_id() !=null){
					map.put("card_id", bean.getCard_id());
				}
//				String strInput = responseJsonText(map);
				String strInput = responseJsonText(bean);
//				String strInput = "{'mv_license':'苏"+req.getParameter("mv_license")+"','obu_id':'"+req.getParameter("obu_id")+"'}";
				return specialCharge_OneManAndLotsOfCars(strInput);
			}

			// 获得车牌或者obu_id
			mvLicense = bean.getMv_license();
			outsideJsonBean.setMv_license(mvLicense);
			outsideJsonBean.setObu_id(bean.getObu_id());
			outsideJsonBean.setExecute_status("1");
			outsideJsonBean.setValid_type("00"); // 赋值默认用户类型00
			// 赋值默认的车型,出口车道传入
			outsideJsonBean.setVehicle_class(String.valueOf(bean.getVehicle_class()));
			outsideJsonBean.setCoupon_type("");
			outsideJsonBean.setCoupon("");
			// 赋值当前时间
			outsideJsonBean.setNow_time(String.valueOf(nowTime.getTime() / 1000));
			
			// 通行费形式：1.免费，2.计费规则。3.计票
			int toll_type = 0;
			// 计费规则
			String charge_code = "";
			// 免费次数
			int freeCount = 0;

			// 判断临时计费里是否存在
			TempCostBean tempCostBean = null;
			if ('1' == (PropertiesUtil.get("EXIT_VALID").charAt(0))
					|| '2' == (PropertiesUtil.get("EXIT_VALID").charAt(0))) {
				int count = 0;
				// 判断是否为E5，E5为按车道和入口时间查询临时计费信息
				if (bean.getType() != null && "E5".equals(bean.getType())) {
					ObjectMap objectMap=ObjectMap.newInstance();
					objectMap.put("park_id", bean.getPark_id());
					objectMap.put("area_id", bean.getArea_id());
					objectMap.put("entry_lane", bean.getLane_id());
					objectMap.put("entry_time", bean.getEntry_time());
					//判断根据入口时间和车道查询临时计费是否存在
					tempCostBean = communicationService.findTempCostByLaneTime(objectMap);
					
					if(tempCostBean == null)
					{
						outsideJsonBean.setExecute_status("0");
						outsideJsonBean.setCar_color(String.valueOf(bean.getCar_color()));
					}
					else
					{
						//判断是否传入区域编号
						if(bean.getArea_id() == null || "".equals(bean.getArea_id()) )
						{
							bean.setArea_id(tempCostBean.getAreaId());
						}
						// 判断车辆类型是否为0，如果为0更新为入口车道车型
						if(bean.getVehicle_class() == null || bean.getVehicle_class() == 0)
						{
							bean.setVehicle_class(tempCostBean.getVehicleClass());
							outsideJsonBean.setVehicle_class(String.valueOf(tempCostBean.getVehicleClass()));
						}
						
						//赋值临时计费中的信息
						outsideJsonBean.setExecute_status("1");
						outsideJsonBean.setPay_status(String.valueOf(tempCostBean.getPayStatus()));
						outsideJsonBean.setPay_method(String.valueOf(tempCostBean.getPayMethod()));
						outsideJsonBean.setCar_color(String.valueOf(tempCostBean.getCarColor()));
						if(tempCostBean.getCardNetwork() != null && !"".equals(tempCostBean.getCardNetwork())
								&& tempCostBean.getCardId() != null && !"".equals(tempCostBean.getCardId()))
						{
							// 增加卡编号字段
							outsideJsonBean.setCard_id(tempCostBean.getCardNetwork()+tempCostBean.getCardId());
						}
						if(tempCostBean.getObuId() != null && !"".equals(tempCostBean.getObuId()))
						{
							// 增加obu编号字段
							outsideJsonBean.setObu_id(tempCostBean.getObuId());
						}
						long payTime = 0;
						if (tempCostBean.getPayTime() != null) {
							payTime = tempCostBean.getPayTime().getTime() / 1000;
						}
						outsideJsonBean.setPay_time(String.valueOf(payTime));
					}
				} else {
					// 根据停车场区域车牌获取数量
					ObjectMap objectMap=ObjectMap.newInstance();
					objectMap.put("park_id", bean.getPark_id());
					objectMap.put("area_id", bean.getArea_id());
					objectMap.put("mv_license", bean.getMv_license());
					count = communicationService.findMvlicenseLiTempCost(objectMap);
					if (count <= 0) {
						if ('2' == (PropertiesUtil.get("EXIT_VALID").charAt(0))) {
							outsideJsonBean.setExecute_status("1");
						} else {
							outsideJsonBean.setExecute_status("0");
						}
						outsideJsonBean.setCar_color(String.valueOf(bean.getCar_color()));
						// log.info(DateUtil.get4yMdHms(new Date()) + "计费返回信息："
						// +
						// responseJsonText(outsideJsonBean));
						// return responseJsonText(outsideJsonBean);
					} else {
						// 根据停车场区域车牌获取临时计费表中记录
						// 根据车牌查询数据
						tempCostBean = communicationService.findTempCost(objectMap);
						//判断是否传入区域编号
						if(bean.getArea_id() == null || "".equals(bean.getArea_id()) )
						{
							bean.setArea_id(tempCostBean.getAreaId());
						}
						// 判断车辆类型是否为0，如果为0更新为入口车道车型
						if(bean.getVehicle_class() == null || bean.getVehicle_class() == 0)
						{
							bean.setVehicle_class(tempCostBean.getVehicleClass());
							outsideJsonBean.setVehicle_class(String.valueOf(tempCostBean.getVehicleClass()));
						}
						outsideJsonBean.setPay_status(String.valueOf(tempCostBean.getPayStatus()));
						outsideJsonBean.setPay_method(String.valueOf(tempCostBean.getPayMethod()));
						outsideJsonBean.setCar_color(String.valueOf(tempCostBean.getCarColor()));
						if(tempCostBean.getCardNetwork() != null && !"".equals(tempCostBean.getCardNetwork())
								&& tempCostBean.getCardId() != null && !"".equals(tempCostBean.getCardId()))
						{
							// 增加卡编号字段
							outsideJsonBean.setCard_id(tempCostBean.getCardNetwork()+tempCostBean.getCardId());
						}
						if(tempCostBean.getObuId() != null && !"".equals(tempCostBean.getObuId()))
						{
							// 增加obu编号字段
							outsideJsonBean.setObu_id(tempCostBean.getObuId());
						}
						
						long payTime = 0;
						if (tempCostBean.getPayTime() != null) {
							payTime = tempCostBean.getPayTime().getTime() / 1000;
						}
						outsideJsonBean.setPay_time(String.valueOf(payTime));
					}
				}
			}

			// 判断白名单
			// boolean flag = communicationService.findExistsWhite(mvLicense,
			// bean.getCar_color(), bean.getObu_id());

			// 黑名单
			// 判断是否校验，并且传入省份不为0
			if ('1' == (PropertiesUtil.get("EXIT_VALID").charAt(1)) && !"0".equals(bean.getBlack_list_province())
					&& !"E5".equals(bean.getType()) && bean.getCard_id() != null && !"".equals(bean.getCard_id().trim())) {
				// 校验黑名单
				String valid_type = communicationService.validBlackListOut(bean.getCard_id(), bean.getMv_license(),
						bean.getObu_id(), bean.getBlack_list_province());
				if (valid_type != null) {
					outsideJsonBean.setValid_type(valid_type);
					// log.info(DateUtil.get4yMdHms(new Date()) +
					// "计费返回信息：黑名单用户-" + responseJsonText(outsideJsonBean));
					// log.info("计费用时:" + (System.currentTimeMillis() -
					// startTime.getTime()) + "毫秒");
					// return responseJsonText(outsideJsonBean);
				}
			}

			boolean flag = false;
			// 白名单
			if ('1' == (PropertiesUtil.get("EXIT_VALID").charAt(2))) {
				
				WhiteListMngBean whiteListMngBean = communicationService.findWhiteByLicense(mvLicense,
						bean.getObu_id(),bean.getArea_id(),bean.getLane_id());

				// 白名单
				if (whiteListMngBean != null) {
					// 判断是否完全免费
					if(whiteListMngBean.getToll_type() == 1)
					{
						flag = true;
					}
					
					outsideJsonBean.setValid_type("05");
					outsideJsonBean.setVehicle_class(String.valueOf(whiteListMngBean.getVehicle_class()));
					toll_type = whiteListMngBean.getToll_type();
					charge_code = whiteListMngBean.getCharge_code();
					freeCount = whiteListMngBean.getSpare1();
					log.info("计费规则："+charge_code);
					// 重新赋值车型
					bean.setVehicle_class(whiteListMngBean.getVehicle_class());
					
					// 判断tempcost中没有车辆信息，并且是白名单的车辆
					if(("05".equals(outsideJsonBean.getValid_type()) || "07".equals(outsideJsonBean.getValid_type())) 
							&& "0".equals(outsideJsonBean.getExecute_status()))
					{
						outsideJsonBean.setExecute_status("1");
						outsideJsonBean.setEntry_time(String.valueOf(nowTime.getTime() / 1000));
					}
					
				}
				
			}

			String finalToll = "";
			long totalToll = 0;
			long baseToll = 0;
			// 计算计费金额
			if ('1' == (PropertiesUtil.get("EXIT_VALID").charAt(0))
					|| '2' == (PropertiesUtil.get("EXIT_VALID").charAt(0))) {
				// 判断临时表中有记录
				if (tempCostBean != null) {
//					String park_id = tempCostBean.getParkId();
//					String area_id = tempCostBean.getAreaId();
					//获得当前区域免费停车时长
					CommAreaInfoBean commAreaInfoBean = communicationService.findArea(bean.getPark_id(),bean.getArea_id());
					long pay_free_time = commAreaInfoBean.getPay_free_time();
					// 判断是否为白名单，并且赋值计费编号
					if("".equals(charge_code))
					{
						charge_code = commAreaInfoBean.getCharge_code();
					}
					//传入支付后的免费停车时长，秒数
					outsideJsonBean.setPay_free_time(String.valueOf(pay_free_time));
					//判断是否为免费白名单
					if (!flag) {
						// 获得计算金额，收费类型应该为按规则计费或者不为免费或者计票
						if (toll_type == 2 || (toll_type != 1 && toll_type != 3)) {
							
							//赋值车型
							int vehicle_class = tempCostBean.getVehicleClass();
							if(bean.getVehicle_class() != null)
							{
								vehicle_class = bean.getVehicle_class();
							}
							else
							{
								outsideJsonBean.setVehicle_class(String.valueOf(tempCostBean.getVehicleClass()));
							}
							
							// 按当前时间计算费用
							/*baseToll = communicationService.calculateToll(tempCostBean.getEntryTime(), nowTime,
									vehicle_class, charge_code,freeCount);*/
							Map map = communicationService.calculateToll(tempCostBean.getEntryTime(), nowTime,
									vehicle_class, charge_code,freeCount);
							baseToll = (long)map.get("toll");
							// 判断优惠次数是否为空
							if(map.get("count") != null 
									&& bean.getType() != null && "E2".equals(bean.getType()) && freeCount < 5)
							{
								// 根据停车场区域车牌获取数量
								ObjectMap objectMap=ObjectMap.newInstance();
								objectMap.put("mv_license", bean.getMv_license());
								objectMap.put("count", Integer.parseInt(map.get("count").toString()));
								communicationService.updateFreeCount(objectMap);
							}
							
							/*if(tempCostBean.getPayStatus()==0)
							{
								baseToll = communicationService.calculateToll(tempCostBean.getEntryTime(), new Date(),
										bean.getVehicle_class(), charge_code);
							}
							else
							{
								//判断当前时间是否超过已支付加免费时间
								if(System.currentTimeMillis() > payTime.getTime() + pay_free_time)
								{
									baseToll = communicationService.calculateToll(tempCostBean.getEntryTime(), new Date(),
											bean.getVehicle_class(), charge_code);
								}
								else
								{
									baseToll = communicationService.calculateToll(tempCostBean.getEntryTime(), tempCostBean.getPayTime(),
											bean.getVehicle_class(), charge_code);
								}
							}*/
						}

						boolean memberFlag = false;
						//判断会员是否校验
//						if('1' == (PropertiesUtil.get("EXIT_VALID").charAt(3)))
//						{
//							memberFlag = communicationService.findExistMemberSale(mvLicense, bean.getCar_color());
//						}
						if (memberFlag) {
							outsideJsonBean.setValid_type("06");
						} else { 
							// 理论金额赋值真实金额
							totalToll = baseToll;
							// 判断是否存在优惠券
//							if(totalToll != 0)
//							{
							CouponMngBean couponMngBean = null;
							// 优惠券验证码
							if(bean.getVerify_code() != null && !"".equals(bean.getVerify_code()) 
									&& !"F2".equals(bean.getType()))
							{
							     couponMngBean = communicationService.findCouponByVerifyCode(bean.getVerify_code());
							}
							if("F2".equals(bean.getType()) 
									&& bean.getPark_id() != null && !"".equals(bean.getPark_id())
									&& bean.getArea_id() != null && !"".equals(bean.getArea_id())
									&& bean.getLane_id() != null && !"".equals(bean.getLane_id()))
							{
								ObjectMap objectMap=ObjectMap.newInstance();
								objectMap.put("park_id", bean.getPark_id());
								objectMap.put("area_id", bean.getArea_id());
								objectMap.put("lane_id", bean.getLane_id());
								objectMap.put("veh_plate", bean.getMv_license());
								couponMngBean = communicationService.findCouponByVerifyCode(objectMap);
							}
							
								if(couponMngBean != null)
								{
									// 赋值优惠券类型
									outsideJsonBean.setCoupon_type(couponMngBean.getCoupon_type());
									
									// 判断优惠类型,金额优惠类型
									if("J".equals(couponMngBean.getCoupon_type()))
									{
										// 赋值优惠券内容
										outsideJsonBean.setCoupon(String.valueOf(couponMngBean.getCoupon_toll()));
										// 优惠券金额
										if(couponMngBean.getCoupon_toll() >= baseToll)
										{
											totalToll = 0;
										}
										else
										{
											totalToll = baseToll - couponMngBean.getCoupon_toll();
										}
									}
									// 时长优惠类型
									if("S".equals(couponMngBean.getCoupon_type()))
									{
										// 赋值优惠券内容
										outsideJsonBean.setCoupon(String.valueOf(couponMngBean.getCoupon_toll()*60));
										// 判断时长是否超过停车时长
										if((nowTime.getTime()-tempCostBean.getEntryTime().getTime()) <= couponMngBean.getCoupon_toll() * 1000 * 60)
										{
											totalToll = 0;
										}
										else
										{
											Date afterCoupon = new Date(tempCostBean.getEntryTime().getTime()+couponMngBean.getCoupon_toll() * 1000 * 60);
//											totalToll = communicationService.calculateToll(afterCoupon, nowTime,
//													tempCostBean.getVehicleClass(), charge_code,freeCount);
											Map map =  communicationService.calculateToll(afterCoupon, nowTime,
													tempCostBean.getVehicleClass(), charge_code,freeCount);
											totalToll = (long)map.get("toll");
										}
									}
								}
//							}
						}
					}
					else
					{
						CouponMngBean couponMngBean = null;
						// 优惠券验证码
						if(bean.getVerify_code() != null && !"".equals(bean.getVerify_code()) 
								&& !"F2".equals(bean.getType()))
						{
						     couponMngBean = communicationService.findCouponByVerifyCode(bean.getVerify_code());
						}
						if("F2".equals(bean.getType())
								&& bean.getPark_id() != null && !"".equals(bean.getPark_id())
								&& bean.getArea_id() != null && !"".equals(bean.getArea_id())
								&& bean.getLane_id() != null && !"".equals(bean.getLane_id()))
						{
							ObjectMap objectMap=ObjectMap.newInstance();
							objectMap.put("park_id", bean.getPark_id());
							objectMap.put("area_id", bean.getArea_id());
							objectMap.put("lane_id", bean.getLane_id());
							objectMap.put("veh_plate", bean.getMv_license());
							couponMngBean = communicationService.findCouponByVerifyCode(objectMap);
						}
						if(couponMngBean != null && couponMngBean.getStatus() == 1)
						{
							// 赋值优惠券类型
							outsideJsonBean.setCoupon_type(couponMngBean.getCoupon_type());
							
							// 时长优惠类型
							if("J".equals(couponMngBean.getCoupon_type()))
							{
								// 赋值优惠券内容
								outsideJsonBean.setCoupon(String.valueOf(couponMngBean.getCoupon_toll()));
							}
							
							// 时长优惠类型
							if("S".equals(couponMngBean.getCoupon_type()))
							{
								// 赋值优惠券内容
								outsideJsonBean.setCoupon(String.valueOf(couponMngBean.getCoupon_toll()*60));
							}
						}
					}
					
					finalToll = String.valueOf(totalToll);
					
					/*if (tempCostBean.getPayStatus() == 1) {
						finalToll = String.valueOf(tempCostBean.getPrepayBill());
					} else {
						finalToll = String.valueOf(totalToll);
					}*/

					outsideJsonBean.setFinal_toll(finalToll);
					outsideJsonBean.setBase_toll(String.valueOf(baseToll));
					outsideJsonBean.setPrepay_toll(String.valueOf(tempCostBean.getPrepayBill()));
					outsideJsonBean.setEntry_time(String.valueOf(tempCostBean.getEntryTime().getTime() / 1000));
					outsideJsonBean.setEntry_lane(tempCostBean.getEntryLane().toString());
					
					String path = "";
					if (tempCostBean.getEntryImage() != null) {
						path = tempCostBean.getEntryImage().toString();
					}
					outsideJsonBean.setImage(path);
				}
			}

			// 会员
			if ('1' == (PropertiesUtil.get("EXIT_VALID").charAt(3))) {
				// 判断不是为白名单时判断会员
				if (!flag) {
					// ③验证是否为会员
					String valid_member = communicationService.validMember(bean.getMv_license(), bean.getCar_color());
					if (valid_member != null) {
						outsideJsonBean.setValid_type(valid_member);
						// log.info(DateUtil.get4yMdHms(new Date()) +
						// "计费返回信息：会员用户-" + responseJsonText(outsideJsonBean));
						// log.info("计费用时:" + (System.currentTimeMillis() -
						// startTime.getTime()) + "毫秒");
						// return responseJsonText(outsideJsonBean);
					}
				}
			}

			// ①验证黑名单（公安/ETC黑名单）
			/*
			 * String valid_type =
			 * communicationService.validCardOut(bean.getMv_license(),
			 * bean.getCar_color().toString(), bean.getObu_id()); if (valid_type
			 * != null) { outsideJsonBean.setValid_type(valid_type);
			 * log.info(DateUtil.get4yMdHms(new Date()) + "计费返回信息：黑名单用户-" +
			 * responseJsonText(outsideJsonBean)); return
			 * responseJsonText(outsideJsonBean); }
			 */

			// ②验证白名单
			/*
			 * String valid_type_white =
			 * communicationService.validWhiteList(bean.getMv_license(),
			 * bean.getCar_color()); if (valid_type_white != null) {
			 * outsideJsonBean.setValid_type(valid_type_white);
			 * log.info(DateUtil.get4yMdHms(new Date()) + "计费返回信息：白名单用户-" +
			 * responseJsonText(outsideJsonBean)); return
			 * responseJsonText(outsideJsonBean); }
			 */

			/**
			 * 重复 // 判断不是为白名单时判断会员 if (!flag) { // ③验证是否为会员 String valid_member
			 * = communicationService.validMember(bean.getMv_license(),
			 * bean.getCar_color()); if (valid_member != null &&
			 * !"00".equals(valid_member)) {
			 * outsideJsonBean.setValid_type(valid_member);
			 * log.info(DateUtil.get4yMdHms(new Date()) + "计费返回信息：会员用户-" +
			 * responseJsonText(outsideJsonBean)); log.info("计费用时:" +
			 * (System.currentTimeMillis() - startTime.getTime()) + "毫秒");
			 * return responseJsonText(outsideJsonBean); } if (valid_member !=
			 * null && "00".equals(valid_member)) {
			 * outsideJsonBean.setValid_type(valid_member); } }
			 **/

			log.info("计费用时:" + (System.currentTimeMillis() - nowTime.getTime()) + "毫秒");
			log.info(DateUtil.get4yMdHms(new Date()) + "计费返回信息:" + responseJsonText(outsideJsonBean));
			return responseJsonText(outsideJsonBean);
		} catch (IOException e) {
			e.printStackTrace();
			return responseJsonText(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title : validOut
	 * @功能描述: 省人民医院立体车库查询接口
	 * @传入参数：@param request
	 * @传入参数：@param response
	 * @传入参数：@return
	 * @返回类型：String
	 * @throws ：
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/currentLicenseInfo")
	public String currentLicenseInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> reply = new HashMap<String,String>();
//			InputDataBean bean = receivePost(request);
			Map<String,String> params = receiveLicense(request);
			String mvLicense = params.get("mv_license");
			// 暂时写死颜色，后期修改
			Date now = new Date();

			if (params.get("type") != null && "E8".equals(params.get("type"))) {
				log.info(DateUtil.get4yMdHms(new Date()) + "车辆离开：" + responseJsonText(params));
				reply.put("type", "F8");
				reply.put("mv_license", mvLicense);
				//返回当前时间
				reply.put("now_time", String.valueOf(now.getTime() / 1000));
			} 
			else {
				log.info(DateUtil.get4yMdHms(new Date()) + "查询计费信息：" + responseJsonText(params));
			}
			
			ObjectMap objectMap=ObjectMap.newInstance();
			objectMap.put("mv_license", mvLicense);
			// 判断是否
		    int count = communicationService.findMvlicenseLiTempCost(objectMap);
			if (count <= 0) {
				reply.put("execut_status", "0");
			} else {
				// 根据车牌查询数据
				TempCostBean tempCostBean = communicationService.findTempCost(objectMap);
				//支付状态
				if(tempCostBean.getPayStatus()!=null)
				{
					reply.put("pay_status", String.valueOf(tempCostBean.getPayStatus()));
				}
				
				//支付时间
				if(tempCostBean.getPayTime()!=null)
				{
					long payTime = 0;
					if (tempCostBean.getPayTime() != null) {
						payTime = tempCostBean.getPayTime().getTime() / 1000;
					}
					reply.put("pay_time", String.valueOf(payTime));
				}
				else
				{
					reply.put("pay_time", "");
				}
				
				// 判断obuid是否存在,不存在为非etc
				if(tempCostBean.getObuId() != null && !"".equals(tempCostBean.getObuId()))
				{
					reply.put("valid_type","1");
				}
				else
				{
					reply.put("valid_type","0");
				}
				
				// 判断是否为免费
				WhiteListMngBean whiteListMngBean = communicationService.findWhiteByLicense(mvLicense,
						"");
				if(whiteListMngBean != null)
				{
					// 通行费类型
					int tollType = whiteListMngBean.getToll_type();
					
					// 判断通行费类型是否为2
					if(tollType == 2 || (tollType != 1 && tollType != 3))
					{
						long toll = 0;
						if(whiteListMngBean.getCharge_code() == null 
								|| "".equals(whiteListMngBean.getCharge_code()))
						{
							Map map = communicationService.calculateToll(tempCostBean.getEntryTime(), now,
									1, PropertiesUtil.get("CHARGE_TYPE"),whiteListMngBean.getSpare1());
							toll = (long)map.get("toll");
						}
						else
						{
							Map map = communicationService.calculateToll(tempCostBean.getEntryTime(), now,
									1, whiteListMngBean.getCharge_code(),whiteListMngBean.getSpare1());
							toll = (long)map.get("toll");
						}
						if(toll == 0)
						{
							reply.put("valid_type","2");
						}
					}
					
					if(tollType == 1 || tollType == 3)
					{
						reply.put("valid_type","2");
					}
				}
				else
				{
					Map map = communicationService.calculateToll(tempCostBean.getEntryTime(), now,
							1, PropertiesUtil.get("CHARGE_TYPE"),0);
					Long totalToll = (long)map.get("toll");
					if(totalToll == 0)
					{
						reply.put("valid_type","2");
					}
				}
				
				if(tempCostBean.getParkId() != null && tempCostBean.getAreaId() != null)
				{
					CommAreaInfoBean commAreaInfoBean = communicationService.findArea(tempCostBean.getParkId(),tempCostBean.getAreaId());
					// 获得免费时长
					long pay_free_time = commAreaInfoBean.getPay_free_time();
					reply.put("pay_free_time",String.valueOf(pay_free_time*1000));
				}
				else
				{
					reply.put("pay_free_time","900");
				}
				reply.put("execut_status", "1");
			}
			log.info(DateUtil.get4yMdHms(new Date()) + "计费返回信息:" + responseJsonText(reply));
			return responseJsonText(reply);
	}

	
	
	/**
	 * 
	 * @Title : confirmIn
	 * @功能描述: 车道发送接口，确认车辆进入停车场，计入临时计费表
	 * @传入参数：@param request
	 * @传入参数：@return
	 * @传入参数：@throws UnsupportedEncodingException
	 * @传入参数：@throws IOException
	 * @返回类型：String
	 * @throws ：
	 */
	@ResponseBody
	@RequestMapping("/confirmIn")
	public String confirmIn(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		log.info(DateUtil.get4yMdHms(new Date()) + "确认进入停车场");
		InputDataBean bean = receivePost(request);
		log.info(DateUtil.get4yMdHms(new Date()) + "收到来自车道的确认进入消息:" + responseJsonText(bean));

		// 初始化tempcost临时计费对象
		TempCostBean tempCostBean = new TempCostBean();

		// 可拥有车位数
		int canUserPlace = 0;
		
		
		// 判断车辆类型
		// 8:一人多车
//		if (bean.getValid_type() != null && Integer.parseInt(bean.getValid_type()) == 8) {

			// 查询白名单，判断是否为一人多车白名单
			WhiteListMngBean whiteListMngBean = communicationService.findWhiteByLicense(bean.getMv_license(),
					bean.getObu_id(), bean.getArea_id(), bean.getLane_id());

			// 一人多车白名单toll_type=2
			if (whiteListMngBean != null && whiteListMngBean.getToll_type() == 2) {

				// 存在拥有车位数
				if (!StringUtil.isEmpty(whiteListMngBean.getUser_number())) {
					// 临时计费中赋值车位编号
					tempCostBean.setSpare4(whiteListMngBean.getUser_number());

					// 可拥有车位数
					canUserPlace = whiteListMngBean.getSpare1();
					
					// 更新vaild_type字段，判断为一人多车车辆
					bean.setValid_type("8");
					
				}

				// 判断剩余车位书
				// 小于等于0，临时计费车位状态为1（计费）
				// 大于0，临时计费车位状态为0（免费）
				if (whiteListMngBean.getSpare2() <= 0) {

					tempCostBean.setSpare1(1);

				} else {

					tempCostBean.setSpare1(0);
				}
			}
//		}
		Date entryTime = null;
		if (bean.getEntry_time() == null) {
			entryTime = new Date();
		} else {
			entryTime = new Date(bean.getEntry_time().getTime());
		}

		// 车牌
		tempCostBean.setMvLicense(bean.getMv_license());

		// 进入停车场时间
		tempCostBean.setEntryTime(entryTime);
		// 入口时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowStr = sdf.format(entryTime);
		tempCostBean.setEntryDate(Integer.parseInt(nowStr));
		// tempCostBean.setIncomeBill(0);//理论金额
		// tempCostBean.setRealBill(0);//实际金额
		tempCostBean.setIncomeBill(0);// 理论金额
		tempCostBean.setRealBill(0);// 实际金额
		tempCostBean.setIsRebate(0);// 是否优惠
		tempCostBean.setEntryTime(entryTime);// 进入车道时间
		tempCostBean.setDateTime(entryTime);// 记录时间

		LaneInfoBean laneInfoBean = trafficRecordService.findLaneInfo(bean.getLane_id());// 获取停车场id和区域id
		tempCostBean.setParkId(laneInfoBean.getpark_id());// 停车场
		tempCostBean.setAreaId(laneInfoBean.getarea_id());// 区域
		tempCostBean.setCarColor(bean.getCar_color());
		tempCostBean.setEntryLane(Integer.valueOf(bean.getLane_id()));// 车道
		tempCostBean.setVehicleClass(bean.getVehicle_class());// 车型
		tempCostBean.setObuId(bean.getObu_id()); // obu编号
		tempCostBean.setIsModel(0);// 是否为模拟计费，否



		// 判断卡编号是否合法
		if (bean.getCard_id() != null && bean.getCard_id().length() == 20) {

			tempCostBean.setCardNetwork(Integer.parseInt(bean.getCard_id().substring(0, 4)));
			tempCostBean.setCardId(bean.getCard_id().substring(4, 20));
		}

		try {
			// ENTRY_VALID 第三位0表示不校验白名单 1.表示校验白名单 2.表示校验一人多车的白名单,并对一人多车进行特殊处理
			// 一人多车白名单时调用
			if ('2' == (PropertiesUtil.get("ENTRY_VALID").charAt(2)) && bean.getValid_type() != null
					&& Integer.parseInt(bean.getValid_type()) == 8) {
				communicationService.whiteListEntry(tempCostBean, canUserPlace);

			} else {

				// 更新入口信息
				communicationService.addOrUpdateTempCost(tempCostBean);
			}
			log.info(DateUtil.get4yMdHms(new Date()) + "车道确认进入，数据新增，车牌：" + tempCostBean.getMvLicense() + "车牌颜色："
					+ tempCostBean.getCarColor());
			return responseJsonText(ResultBean.newSuccessResult("入口车道更新一条数据"));
		} catch (Exception e) {
			return responseJsonText(ResultBean.newErrorResult("入口车道更新失败"));
		}
	}

	/**
	 * 
	 * @Title : confirmOut
	 * @功能描述: 车辆离开停车场时，收到出口的确认消息,删除临时计费表中信息
	 * @传入参数：@param request
	 * @传入参数：@return
	 * @传入参数：@throws UnsupportedEncodingException
	 * @传入参数：@throws IOException
	 * @返回类型：String
	 * @throws ：
	 */
	@ResponseBody
	@RequestMapping("/confirmOut")
	public String confirmOut(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		log.info(DateUtil.get4yMdHms(new Date()) + "确认离开停车场");
		InputDataBean bean = receivePost(request);
		log.info(DateUtil.get4yMdHms(new Date()) + "收到来自车道的确认离开消息:" + responseJsonText(bean));
		try {
			ObjectMap objectMap = ObjectMap.newInstance();
			// 判断车辆类型
			// 8:一人多车
			if (bean.getValid_type() != null) {

				// 放入车辆的类型，5为免费白名单，8为一人多车白名单
				objectMap.put("valid_type", bean.getValid_type());
			}
			// 计费的车辆离场时间
			if (bean.getExit_time() != null) {

				objectMap.put("exit_time", bean.getExit_time());
			}
			objectMap.put("mv_license", bean.getMv_license());
			// objectMap.put("car_color", bean.getCar_color());
			// objectMap.put("entry_lane", bean.getLane_id());
			objectMap.put("entry_time", bean.getEntry_time());
			objectMap.put("verify_code", bean.getVerify_code());
			objectMap.put("park_id", bean.getPark_id());
			objectMap.put("area_id", bean.getArea_id());
			if ('2' == (PropertiesUtil.get("EXIT_VALID").charAt(2))) {
				communicationService.whiteListExit(objectMap);

			} else {

				communicationService.deleteTempCostBymvlicense(objectMap);
			}
			log.info(DateUtil.get4yMdHms(new Date()) + "车道确认离开，数据删除，车牌：" + bean.getMv_license() + "车牌颜色："
					+ bean.getCar_color());
			return responseJsonText(ResultBean.newSuccessResult("出口车道更新一条数据"));
		} catch (Exception e) {
			// TODO: handle exception
			return responseJsonText(ResultBean.newErrorResult("出口车道更新失败"));
		}
	}

	
	
	/**
	 * 
	 * @Title : findParkPlace
	 * @功能描述: 查询停车位信息
	 * @传入参数：@param request
	 * @传入参数：@return
	 * @传入参数：@throws UnsupportedEncodingException
	 * @传入参数：@throws IOException
	 * @返回类型：String
	 * @throws ：
	 */
	@ResponseBody
	@RequestMapping("/findParkPlace")
	public String findParkPlace(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		InputFindPlaceBean bean = receiveFindPlacePost(request);
		// 查询车位的日志太频繁
//		log.info(DateUtil.get4yMdHms(new Date()) + "车道查询停车位信息" + responseJsonText(bean));
		// InputDataBean bean = receivePost(request);
		// log.info(DateUtil.get4yMdHms(new
		// Date())+"收到来自车道的确认离开消息:"+responseJsonText(bean));
		ParkPlaceInfoBean parkPlaceInfoBean = new ParkPlaceInfoBean();
		if (StringUtil.isEmpty(bean.getPark_id()) || StringUtil.isEmpty(bean.getArea_id())) {
			parkPlaceInfoBean.setType("D5");
			parkPlaceInfoBean.setExecute_status("0");
		} else {
			ObjectMap map = ObjectMap.newInstance();
			map.put("park_id", bean.getPark_id());
			map.put("area_id", bean.getArea_id());
			parkPlaceInfoBean = communicationService.findParkPlace(map);
		}
		try {
			// 查询车位的日志太频繁
			// log.info(DateUtil.get4yMdHms(new Date()) + "车道查询停车场信息返回:" +
			// responseJsonText(parkPlaceInfoBean));
			return responseJsonText(parkPlaceInfoBean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return responseJsonText(e.getMessage());
		}

	}

	/**
	 * 
	 * @Title : mobilePay
	 * @功能描述: 处理停车场移动服务的处理结果
	 * @传入参数：@param request
	 * @传入参数：@return
	 * @传入参数：@throws UnsupportedEncodingException
	 * @传入参数：@throws IOException 
	 * @返回类型：String
	 * @throws ：
	 */
	@ResponseBody
	@RequestMapping("/mobilePay")
	public String updateParkPay(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		log.info("接收移动支付处理结果!");
		PayResultBean bean = payResultPost(request);
		log.info(DateUtil.get4yMdHms(new Date()) + "接收到支付结果" + responseJsonText(bean));
		// ParkPlaceInfoBean parkPlaceInfoBean = new ParkPlaceInfoBean();
		ReceivePayBean receivePayBean = communicationService.confirmPay(bean);
		
		// 判断是否携带出口车道
		if(bean.getLane_id() != 0)
		{
			log.info("收到出口车道编号"+bean.getLane_id());
			// 获取ip
			String ip = communicationService.findTriggerIp(bean.getLane_id());
			// 获取路径
			String port = PropertiesUtil.get("COMM.LANEPORT");
			// 获取路径 
			String path = PropertiesUtil.get("COMM.LANEPATH");
			// 封装车牌
			Map<Object, Object> params = new HashMap<Object, Object>();
			params.put("mv_license", bean.getMv_license());
			params.put("lane_id", String.valueOf(bean.getLane_id()));
			JSONObject json = JSONObject.fromObject(params);
			
			// 车道端通信地址
			log.info(DateUtil.get4yMdHms(new Date()) + "车道端通信地址:" + ip+":"+port+path);
			log.info(DateUtil.get4yMdHms(new Date()) + "车道端传输车牌:"+ responseJsonText(json));
			String responseJson = PostOrGet.sendPost("http://"+ip+":"+port+path,json.toString(),null);
			log.info(DateUtil.get4yMdHms(new Date()) + "车道返回信息："+ responseJson.trim());
//			String responseJson = "";
			if(!responseJson.isEmpty())
			{
				log.info(DateUtil.get4yMdHms(new Date()) + "车道返回信息："+ responseJson.trim());
			}
			
			// 判断是否为空
			if(responseJson.isEmpty() || "error".equals(responseJson.trim()))
			{
				receivePayBean.setExecute_status("0");
			}
		}
		try {
			log.info(DateUtil.get4yMdHms(new Date()) + "车道查询停车场信息返回:" + responseJsonText(receivePayBean));
			return responseJsonText(receivePayBean);
		} catch (Exception e) {
			e.printStackTrace();
			return responseJsonText(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title : payToll
	 * @功能描述: 支付通行费接口
	 * @传入参数：@param request
	 * @传入参数：@return
	 * @返回类型：String
	 * @throws ：
	 */
	@ResponseBody
	@RequestMapping("/pay_toll")
	public String payToll(HttpServletRequest request) {
		PaymentChannelBean bean;
		try {
			bean = receivePaymentChannel(request);
			log.info(DateUtil.get4yMdHms(new Date()) + "收到支付信息" + responseJsonText(bean));
			OutBaseJsonBean outBaseJsonBean = null;
			if("E6".equals(bean.getType()))
			{
				ObjectMap objectMap = ObjectMap.newInstance();
				objectMap.put("pay_method", bean.getPay_method());
				objectMap.put("pay_status", bean.getTerminal_type());
//				objectMap.put("payTime", new Date());
				
				//支付时间
				//判断支付金额为负数
				if(bean.getPrepay_bill() <= 0)
				{
					objectMap.put("pay_time",null);
				}

				//支付时间
				//判断支付金额为正数
				if(bean.getPrepay_bill() > 0)
				{
					objectMap.put("pay_time", bean.getPay_time());
				}
				
				objectMap.put("prepay_bill", bean.getPrepay_bill());
				objectMap.put("mv_license", bean.getMv_license());
				objectMap.put("park_id", bean.getPark_id());
				objectMap.put("area_id", bean.getArea_id());
				objectMap.put("entry_lane", bean.getEntry_lane());
				objectMap.put("entry_time", bean.getEntry_time());
				objectMap.put("trml_park_id", bean.getTrml_park_id());
				objectMap.put("trml_area_id", bean.getTrml_area_id());
				objectMap.put("terminal_id", bean.getTerminal_id());
				
				outBaseJsonBean = communicationService.updateTempCostPayStatus(objectMap);
				log.info(DateUtil.get4yMdHms(new Date()) + "返回支付结构:" + responseJsonText(outBaseJsonBean));
				return responseJsonText(outBaseJsonBean);
			}
			
			//不为E6帧返回失败
			outBaseJsonBean = new OutBaseJsonBean();
			outBaseJsonBean.setType("D6");
			outBaseJsonBean.setExecute_status("0");
			outBaseJsonBean.setNow_time(String.valueOf(System.currentTimeMillis()/1000));
			return responseJsonText(outBaseJsonBean);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return responseJsonText(e.getMessage());
		}
	}

	/**
	 * 
	   * @Title : modelToll 
	   * @功能描述: 模拟费用查询
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	@ResponseBody
	@RequestMapping("/modelChargeSearch")
	public String modelChargeSearch(HttpServletRequest request)
	{
		ReceiveModelChargeBean receiveModelChargeBean = receiveModelCharge(request);
		ResponseModelCargeBean responseModelCargeBean = modelChargeSearch(receiveModelChargeBean);
		return responseJsonText(responseModelCargeBean);
	}
	
	
	private ResponseModelCargeBean modelChargeSearch(ReceiveModelChargeBean receiveModelChargeBean) {
		ResponseModelCargeBean responseModelCargeBean = new ResponseModelCargeBean();
		if (receiveModelChargeBean.getType() != null && "E7".equals(receiveModelChargeBean.getType())) {
			log.info(DateUtil.get4yMdHms(new Date()) + "集中支付查询信息：" + responseJsonText(receiveModelChargeBean));
			responseModelCargeBean.setType("D7");
		}
		else {
			log.info(DateUtil.get4yMdHms(new Date()) + "集中支付查询信息：" + responseJsonText(receiveModelChargeBean));
		}
		
		// 赋值车牌
		responseModelCargeBean.setMv_license(receiveModelChargeBean.getMv_license());
		// 车牌颜色
		responseModelCargeBean.setCar_color(String.valueOf(receiveModelChargeBean.getCar_color()));
		// 车型
		responseModelCargeBean.setVehicle_class(String.valueOf(receiveModelChargeBean.getVehicle_class()));
		// 入口车道
//		responseModelCargeBean.setEntry_lane(receiveModelChargeBean.getLane_id());
		// 入口时间
		responseModelCargeBean.setEntry_time(String.valueOf(receiveModelChargeBean.getEntry_time().getTime()/1000));
		
		// 优惠券类型
		responseModelCargeBean.setCoupon_type("0");
		// 优惠内容
		responseModelCargeBean.setCoupon("0");
		// 默认用户0
		responseModelCargeBean.setValid_type("0"); 
		
		// 通行费形式：1.免费，2.计费规则。3.计票
		int toll_type = 0;
		// 计费规则
		String charge_code = "";
		
		// 停车场编号
//		String park_id = receiveModelChargeBean.getPark_id();
		
		// 区域编号
//		String area_id = receiveModelChargeBean.getArea_id();
		
		/*// 判断停车场是否存在该车牌
		int count = communicationService.findMvlicenseLiTempCost(receiveModelChargeBean.getMv_license(), receiveModelChargeBean.getCar_color());
		
		// 停车场存在该车牌
		if(count > 0)
		{
			responseModelCargeBean.setExecute_status("0");
			log.info(DateUtil.get4yMdHms(new Date()) + "停车场存在该车牌:" + responseJsonText(responseModelCargeBean));
			return responseJsonText(responseModelCargeBean);
		}*/
		
		// 黑名单
		// 判断是否校验，并且传入省份不为0

		
		// 判断特殊用户游标
		boolean flag = false;
		
		// 免费次数
		int count = 0;
		// 白名单
		if ('1' == (PropertiesUtil.get("EXIT_VALID").charAt(2))) {
			WhiteListMngBean whiteListMngBean = communicationService.findWhiteByLicense(receiveModelChargeBean.getMv_license(),
					"");
			// 白名单
			if (whiteListMngBean != null) {
				flag = true;
				responseModelCargeBean.setValid_type("05");
				toll_type = whiteListMngBean.getToll_type();
				charge_code = whiteListMngBean.getCharge_code();
				count = whiteListMngBean.getSpare1();
			}
		}
		
		String finalToll = "";
		long totalToll = 0;
		long baseToll = 0;
		
		//获得当前区域免费停车时长
		CommAreaInfoBean commAreaInfoBean = communicationService.findArea(receiveModelChargeBean.getPark_id(),receiveModelChargeBean.getArea_id());
		// 获得免费时长
		long pay_free_time = commAreaInfoBean.getPay_free_time();
		//传入支付后的免费停车时长，秒数
		responseModelCargeBean.setPay_free_time(String.valueOf(pay_free_time));

		// 返回区域计费规则
		if("".equals(charge_code))
		{
			charge_code = commAreaInfoBean.getCharge_code();
		}
		Date now= new Date();
		// 判断是否白名单
		if (toll_type != 1) {
			// 获得计算金额，收费类型应该为按规则计费或者不为免费或者计票
				if (toll_type == 2) {
				// 应收通信费
				Map map = communicationService.calculateToll(receiveModelChargeBean.getEntry_time(), now,
						receiveModelChargeBean.getVehicle_class(), charge_code,count);
				baseToll = (long)map.get("toll");
				totalToll = baseToll;
				
				// 实收通行费
				/*totalToll = communicationService.calculateToll(receiveModelChargeBean.getEntry_time(), now,
						receiveModelChargeBean.getVehicle_class(), charge_code);*/
			}
			// 普通通行类型
			if(toll_type == 0)
			{
				Map map = communicationService.calculateToll(receiveModelChargeBean.getEntry_time(), now,
						receiveModelChargeBean.getVehicle_class(), charge_code,count);
				baseToll = (long)map.get("toll");
				totalToll = baseToll;
			}
		}
		
		
		finalToll = String.valueOf(totalToll);
		// 实收金额
		responseModelCargeBean.setFinal_toll(finalToll);
		// 应收金额
		responseModelCargeBean.setBase_toll(String.valueOf(baseToll));
		// 当前时间
		responseModelCargeBean.setNow_time(String.valueOf(now.getTime() / 1000));
		// 执行状态
		responseModelCargeBean.setExecute_status("1");
		log.info(DateUtil.get4yMdHms(new Date()) + "停车场返回模拟计费查询：" + responseJsonText(responseModelCargeBean));
		return responseModelCargeBean;
	}
	
	@ResponseBody
	@RequestMapping("/specialTest")
	public String specialTest(HttpServletRequest req){
		String strInput = "{'mv_license':'"+req.getParameter("mv_license")+"','obu_id':'"+req.getParameter("obu_id")+"'}";
		String str = specialCharge_OneManAndLotsOfCars(strInput);
		System.out.println("json字符串："+str);
		return str;
	}
	
	public String specialCharge_OneManAndLotsOfCars(String strInput){
		Date now = new Date();
		System.out.println("当前时间："+now);
		String charge_code = "15";
		int pay_free_time = 1800;//免费时长，秒
		log.info("特殊计费-----参数字符串："+strInput);
		SpecialResultBean result = new SpecialResultBean();
//		result.setType("D2");
		result.setCoupon_type("0");
		JSONObject paramJson = null;
		boolean execute = false;
		String type = null;
		String mv_license = null;
		String obu_id = null;
		if(strInput!=null && !"".equals(strInput)){
			paramJson = JSONObject.fromObject(strInput);
			if(paramJson!=null){
				if(strInput.indexOf("type")>=0){
					type = paramJson.getString("type");
					if("E2".equals(type) || "E5".equals(type)){
						if(strInput.indexOf("mv_license")>=0){
							execute = true;
							mv_license = paramJson.getString("mv_license");
							result.setMv_license(mv_license);
						}
//						if("E5".equals(type)){
//							if(strInput.indexOf("entry_time")>=0 && !"".equals(paramJson.get("entry_time"))){
//								entry_second = paramJson.getLong("entry_time");
//								execute = true;
//							}else{
//								execute = false;
//							}
//						}
					}
				}
				if(strInput.indexOf("car_color")>=0){
					result.setCar_color(paramJson.getString("car_color"));
				}
				if(strInput.indexOf("vehicle_class")>=0){
					result.setVehicle_class(paramJson.getString("vehicle_class"));
				}
				if(strInput.indexOf("obu_id")>=0){
					obu_id = paramJson.getString("obu_id");
					result.setObu_id(obu_id);
				}
				if(strInput.indexOf("card_id")>=0){
					result.setCard_id(paramJson.getString("card_id"));
				}
//				result.setVehicle_class(paramJson.getString("vehicle_class"));
//				result.setObu_id(paramJson.getString("obu_id"));
//				result.setCard_id(paramJson.getString("card_id"));
			}
		}
		if(!execute){
			result.setExecute_status("0");
			log.warn("特殊计费-----获取参数失败！");
			return JSONObject.fromObject(result).toString();
		}
		if("E5".equals(type)){
			result.setType("D5");
		}else{
			result.setType("D2");
		}
		if("E5".equals(type) && "无牌车".equals(mv_license)){
			if(strInput.indexOf("entry_time")>=0){
				JSONObject json = paramJson.getJSONObject("entry_time");
				Date entry = new Date();
				entry.setTime(json.getLong("time"));
				Map map = communicationService.calculateToll(entry, now, paramJson.getInt("vehicle_class"), charge_code, 0);
				if(map==null){
					result.setExecute_status("0");
					result.setValid_type("0");
					log.warn("特殊计费-----E5计费失败！");
					return JSONObject.fromObject(result).toString();
				}else{
					long toll = (long) map.get("toll");
					result.setExecute_status("1");
					result.setValid_type("0");
					result.setEntry_time(String.valueOf(entry.getTime()/1000));
					result.setBase_toll(String.valueOf(toll));
					result.setPrepay_toll("0");
					result.setPay_free_time(String.valueOf(pay_free_time));
					result.setFinal_toll(String.valueOf(toll));
					result.setNow_time(String.valueOf(now.getTime()/1000));
					log.info("特殊计费-----返回值："+result);
					return JSONObject.fromObject(result).toString();
				}
			}else{
				result.setExecute_status("0");
				result.setValid_type("0");
				log.warn("特殊计费-----E5未获取到入口时间！");
				return JSONObject.fromObject(result).toString();
			}
		}
		// 根据车牌查询白名单表
		WhiteListInfoBean white = communicationService.queryWhiteByLicense(mv_license,null);
		log.info("特殊计费-----白名单表查询结果："+white);
		// 是否为有效白名单
		boolean valid_flag = false;
		boolean free = false;
//		boolean charge = false;
		// 记录存在，判断白名单类型及是否有效
		if(white!=null){
			if(white.getToll_type()!=null){
				if(white.getToll_type()==1){
					valid_flag = true;
					free = true;
					log.info("特殊计费-----免费白名单！");
				}else if(white.getToll_type()==2){
					if(white.getCharge_code()!=null && white.getCharge_code().equals(charge_code)){
						valid_flag = true;
//						charge = true;
						log.info("特殊计费-----计费白名单！");
					}
				}
			}
		}
		// 计费白名单
		/*if(charge){
			if(white.getSpare2()!=null && white.getSpare2()>=0){
				free = true;
				log.info("特殊计费-----计费白名单，免费通行！");
			}
		}*/
		// 查询临时计费表
		TempCostInfoBean temp = communicationService.queryTempByLicense(mv_license);
		log.info("特殊计费-----临时计费表查询结果："+temp);
		if(valid_flag && temp!=null && temp.getSpare1()!=null && temp.getSpare1()==0){
			free = true;
			log.info("特殊计费-----临时计费表spare1字段显示，免费！");
		}
		// 免费
		if(free){
			result.setExecute_status("1");
			result.setValid_type("5");
			result.setPay_status("0");
			result.setPay_method("0");
			result.setPay_time("0");
			result.setPay_free_time(String.valueOf(pay_free_time));
			result.setBase_toll("0");
			result.setFinal_toll("0");
			result.setPrepay_toll("0");
			if(temp!=null){
				if(temp.getEntry_time()!=null){
					result.setEntry_time(String.valueOf(temp.getEntry_time().getTime()/1000));
				}else{
					result.setEntry_time(String.valueOf(now.getTime()/1000));
				}
				if(temp.getEntry_lane()!=null){
					result.setEntry_lane(temp.getEntry_lane().toString());
				}
				result.setImage(temp.getEntry_image());
			}else{
				result.setEntry_time(String.valueOf(now.getTime()/1000));
			}
			result.setNow_time(String.valueOf(now.getTime()/1000));
			log.info("特殊计费-----车辆免费通行！返回值："+result);
			return JSONObject.fromObject(result).toString();
		}
		if(!valid_flag){
			//临时车辆
			log.warn("特殊计费-----白名单无效，临时车辆！");
			result.setValid_type("0");
		}else{
			//计费白名单
			result.setValid_type("8");
		}
		if(temp==null){
			result.setExecute_status("0");
			log.warn("特殊计费-----临时计费表无记录！");
			return JSONObject.fromObject(result).toString();
		}
		/*CommAreaInfoBean commAreaInfoBean = null;
		if(!valid_flag){
			commAreaInfoBean = communicationService.findArea(temp.getPark_id(),temp.getArea_id());
			if(commAreaInfoBean!=null){
				charge_code = commAreaInfoBean.getCharge_code();
			}
		}else{
			charge_code = white.getCharge_code();
		}*/
		long toll = 0;
		Map map = null;
		if(valid_flag && temp.getSpare1()!=null && temp.getSpare1()==2){
			//半免费
			map = communicationService.calculateToll(temp.getEntry_time(), temp.getBegin_time(), temp.getVehicle_class(), charge_code, 0);
		}else{
			//计费
			map = communicationService.calculateToll(temp.getEntry_time(), now, temp.getVehicle_class(), charge_code, 0);
		}
		if(map!=null){
			toll = (long) map.get("toll");
		}else{
			result.setExecute_status("0");
			log.warn("特殊计费-----未获得计费信息！");
			return JSONObject.fromObject(result).toString();
		}
		result.setExecute_status("1");
		if(temp.getPay_status()!=null){
			result.setPay_status(String.valueOf(temp.getPay_status()));
		}
		if(temp.getPay_method()!=null){
			result.setPay_method(String.valueOf(temp.getPay_method()));
		}
		result.setPay_free_time(String.valueOf(pay_free_time));
		result.setBase_toll(String.valueOf(toll));
		if(temp.getEntry_time()!=null){
			result.setEntry_time(String.valueOf(temp.getEntry_time().getTime()/1000));
		}else{
			result.setEntry_time("0");
		}
		if(temp.getEntry_lane()!=null){
			result.setEntry_lane(String.valueOf(temp.getEntry_lane()));
		}
		result.setImage(temp.getEntry_image());
		result.setNow_time(String.valueOf(now.getTime()/1000));
		if(temp.getPay_time()!=null){
			result.setPay_time(String.valueOf(temp.getPay_time().getTime()/1000));
		}
		if(temp.getPrepay_bill()!=null){
			result.setPrepay_toll(String.valueOf(temp.getPrepay_bill()));
		}
		result.setFinal_toll(String.valueOf(toll));
		//判断是否有预支付
//		if(temp.getPay_status()==null || temp.getPay_status()==0){
//			//未支付
//			result.setFinal_toll(String.valueOf(toll));
//			result.setPrepay_toll("0");
//			log.info("特殊计费-----未支付！");
//		}else{
//			//已支付
//			result.setPay_time(String.valueOf(temp.getPay_time().getTime()/1000));
//			result.setPrepay_toll(String.valueOf(temp.getPrepay_bill()));
//			//判断是否在免费出场时间内
//			if((now.getTime()-temp.getPay_time().getTime())<=(pay_free_time*1000)){
//				//在免费时间内
//				result.setFinal_toll("0");
//				log.info("特殊计费-----已支付，在免费出场时间内！");
//			}else{
//				long final_toll = toll-temp.getPrepay_bill();
//				result.setFinal_toll(String.valueOf(final_toll));
//				log.info("特殊计费-----已支付，超出免费出场时间！");
//			}
//		}
		log.info("特殊计费-----计费返回值："+result);
		return JSONObject.fromObject(result).toString();
		
		/*ReceiveModelChargeBean param = new ReceiveModelChargeBean();
		param.setType("E7");
		param.setPark_id(temp.getPark_id());
		param.setArea_id(temp.getArea_id());
		param.setLane_id(temp.getEntry_lane().toString());
		param.setMv_license(temp.getMv_license());
		param.setCar_color(temp.getCar_color());
		param.setVehicle_class(temp.getVehicle_class());
		param.setEntry_time(temp.getEntry_time());
		ResponseModelCargeBean bean = modelChargeSearch(param);
		if(bean==null){
			log.warn("特殊计费-----未获得计费信息！");
			result.setExecute_status("0");
			return JSONObject.fromObject(result).toString();
		}else{
			result.setExecute_status("1");
			result.setValid_type("5");
			result.setPay_status("0");
			result.setPay_method("0");
			result.setPay_time("0");
			result.setPay_free_time(bean.getPay_free_time());
			result.setBase_toll(bean.getBase_toll());
			result.setFinal_toll(bean.getFinal_toll());
			result.setPrepay_toll("0");
			result.setEntry_time(String.valueOf(temp.getEntry_time().getTime()/1000));
			result.setEntry_lane(temp.getEntry_lane().toString());
			result.setImage(temp.getEntry_image());
			result.setNow_time(String.valueOf(new Date().getTime()/1000));
			log.info("特殊计费-----计费白名单！返回值："+result);
			return JSONObject.fromObject(result).toString();
		}*/
	}
	
	
	
	/**
	 * 
	   * @Title : modelPay 
	   * @功能描述: 确认模拟计费
	   * @传入参数：@param request
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	@ResponseBody
	@RequestMapping("/modelPay")
	public String modelPay(HttpServletRequest request)
	{
		ReceiveModelPayBean receiveModelPayBean = receiveModelPay(request);
		ResponseExecuteStatus bean = new ResponseExecuteStatus();
		if (receiveModelPayBean.getType() != null && "E8".equals(receiveModelPayBean.getType())) {
			log.info(DateUtil.get4yMdHms(new Date()) + "集中支付信息：" + responseJsonText(receiveModelPayBean));
			bean.setType("D8");
		}
		
		/*// 判断停车场是否存在该车牌
		int count = communicationService.findMvlicenseLiTempCost(receiveModelPayBean.getMv_license(), receiveModelPayBean.getCar_color());
				
		// 停车场存在该车牌
		if(count > 0)
		{
			bean.setExecute_status("0");
			log.info(DateUtil.get4yMdHms(new Date()) + "停车场存在该车牌:" + responseJsonText(receiveModelPayBean));
			return responseJsonText(bean);
		}*/
		
		
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("pay_method", receiveModelPayBean.getPay_method());
		objectMap.put("pay_status", receiveModelPayBean.getTerminal_type());
		objectMap.put("pay_time", receiveModelPayBean.getPay_time());
		objectMap.put("entry_time", receiveModelPayBean.getEntry_time());
		objectMap.put("prepay_bill", receiveModelPayBean.getPrepay_toll());
		objectMap.put("mv_license", receiveModelPayBean.getMv_license());
		objectMap.put("car_color", receiveModelPayBean.getCar_color());
		objectMap.put("park_id", receiveModelPayBean.getPark_id());
		objectMap.put("area_id", receiveModelPayBean.getArea_id());
		objectMap.put("entry_lane", receiveModelPayBean.getLane_id());
		objectMap.put("trml_park_id", receiveModelPayBean.getTrml_park_id());
		objectMap.put("trml_area_id", receiveModelPayBean.getTrml_area_id());
		objectMap.put("terminal_id", receiveModelPayBean.getTerminal_id());
		objectMap.put("vehicle_class", receiveModelPayBean.getVehicle_class());
		
		// 入口日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowStr = sdf.format(receiveModelPayBean.getEntry_time());
		objectMap.put("entry_date", Integer.parseInt(nowStr));
		objectMap.put("is_vaild", 1);
		objectMap.put("is_model", 1);
		boolean flag = communicationService.saveModelPayTempCost(objectMap);
		if(flag)
		{
			bean.setExecute_status("1");
			log.info(DateUtil.get4yMdHms(new Date()) + "返回集中支付信息:" + responseJsonText(bean));
			return responseJsonText(bean);
		}
		bean.setExecute_status("0");
		log.info(DateUtil.get4yMdHms(new Date()) + "返回集中支付信息:" + responseJsonText(bean));
		return responseJsonText(bean);
	}
	
	
	/**
	 * 
	   * @Title : nowLaneLicense 
	   * @功能描述: 根据车道编号返回车牌信息
	   * @传入参数：@param request
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	@ResponseBody
	@RequestMapping("/lane_license")
	public String laneLicense(HttpServletRequest request)
	{
		//获取车道编号
		Map<String,String> map = receiveLane(request);
		// 根据车道编号查询车牌名称
		String mv_license = communicationService.findTriggerLicense(Integer.parseInt(map.get("lane_id")));
		// 判断车牌是否为空
		if(mv_license == null || "".equals(mv_license.trim()))
		{
			// 返回失败
			map.put("execute_status", "0");
		}
		else
		{
			// 返回成功
			map.put("execute_status", "1");
		}
		map.put("mv_license", mv_license);
		String result = responseJsonText(map);
		log.info(DateUtil.get4yMdHms(new Date()) + "返回车牌信息:" + result);
		return result;
	}
	
	/**
	 * 
	   * @Title : licenseImage 
	   * @功能描述: 根据车牌返回入口车道信息
	   * @传入参数：@param request
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
	@ResponseBody
	@RequestMapping("/entry_image")
	public String licenseImage(HttpServletRequest request)
	{
		Map<String,String> param = receiveLicense(request);
		log.info(DateUtil.get4yMdHms(new Date()) + "车牌接收信息，用于查询入口图片:" + responseJsonText(param));
		String mv_license = param.get("mv_license");
		String entryImage = communicationService.findEntryImage(mv_license);
		param.put("entry_image", entryImage);
		String result = responseJsonText(param);
		log.info(DateUtil.get4yMdHms(new Date()) + "返回图片信息:" + result);
		return result;
	}
	
	
	public InputDataBean receivePost(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
		InputDataBean bean = new InputDataBean();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			line = new String(line.getBytes());
			sb.append(line);
		}

		String reqBody = sb.toString();
		br.close();
		br = null;
		log.info("reqBody"+reqBody);
		JSONObject o = JSONObject.fromObject(reqBody);

		if (reqBody.indexOf("obu_id") > 0) {
			bean.setObu_id(o.getString("obu_id"));
		}
		if (reqBody.indexOf("mv_license") > 0) {
			bean.setMv_license(o.getString("mv_license"));
		}
		if (reqBody.indexOf("car_color") > 0) {
			if(StringUtil.isEmpty(o.getString("car_color")))
			{
				bean.setCar_color(0);
			}
			else
			{
				bean.setCar_color(Integer.parseInt(o.getString("car_color")));
			}
		}
		if (reqBody.indexOf("park_id") > 0) {
			bean.setPark_id(o.getString("park_id"));
		}
		if (reqBody.indexOf("area_id") > 0) {
			bean.setArea_id(o.getString("area_id"));
		}
		if (reqBody.indexOf("lane_id") > 0) {
			bean.setLane_id(o.getString("lane_id"));
		}
		if (reqBody.indexOf("vehicle_class") > 0) {
			if(StringUtil.isEmpty(o.getString("vehicle_class")))
			{
				bean.setVehicle_class(1);
			}
			else
			{
				bean.setVehicle_class(Integer.parseInt(o.getString("vehicle_class")));
			}
		}
		if (reqBody.indexOf("now_time") > 0) {
			bean.setNow_time(o.getString("now_time"));
		}
		if (reqBody.indexOf("type") > 0) {
			bean.setType(o.getString("type"));
		}
		// 黑名单省份
		if (reqBody.indexOf("black_list_province") > 0) {
			bean.setBlack_list_province(o.getString("black_list_province"));
		}
		// 卡编号
		if (reqBody.indexOf("card_id") > 0) {
			bean.setCard_id(o.getString("card_id"));
		}
		// 入口时间
		if(reqBody.indexOf("entry_time") > 0)
		{
			bean.setEntry_time(new Date(o.getLong("entry_time")*1000));
		}
		// 优惠券验证码
		if (reqBody.indexOf("verify_code") > 0) {
			bean.setVerify_code(o.getString("verify_code"));
		}
		// 用户类型
		if (reqBody.indexOf("valid_type") > 0) {
			bean.setValid_type(o.getString("valid_type"));
		}
		// 出口时间
		if (reqBody.indexOf("exit_time") > 0) {
			bean.setExit_time(new Date(o.getLong("exit_time")*1000));
		}
		return bean;
	}
	
	/**
	 * 
	   * @Title : receiveLicense 
	   * @功能描述: 查询计费
	   * @传入参数：@param request
	   * @传入参数：@return
	   * @传入参数：@throws IOException
	   * @传入参数：@throws UnsupportedEncodingException
	   * @返回类型：Map<String,String> 
	   * @throws ：
	 */
	private Map<String,String> receiveLicense(HttpServletRequest request){
		Map<String,String> map = new HashMap<String,String>();
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			line = new String(line.getBytes());
			sb.append(line);
		}
		String reqBody = sb.toString();
		br.close();
		br = null;
		log.info("reqBody"+reqBody);
		JSONObject o = JSONObject.fromObject(reqBody);
		if (reqBody.indexOf("type") > 0) {
			map.put("type",o.getString("type"));
		}
		if (reqBody.indexOf("mb_license") > 0) {
			map.put("mv_license",o.getString("mb_license"));
		}
		if (reqBody.indexOf("mv_license") > 0) {
			map.put("mv_license",o.getString("mv_license"));
		}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	
	/**
	 * 
	   * @Title : receiveLicense 
	   * @功能描述: 接受车道id
	   * @传入参数：@param request
	   * @传入参数：@return
	   * @传入参数：@throws IOException
	   * @传入参数：@throws UnsupportedEncodingException
	   * @返回类型：Map<String,String> 
	   * @throws ：
	 */
	private Map<String,String> receiveLane(HttpServletRequest request){
		Map<String,String> map = new HashMap<String,String>();
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			line = new String(line.getBytes());
			sb.append(line);
		}
		String reqBody = sb.toString();
		br.close();
		br = null;
		log.info("reqBody"+reqBody);
		JSONObject o = JSONObject.fromObject(reqBody);
		if (reqBody.indexOf("park_id") > 0) {
			map.put("park_id",o.getString("park_id"));
		}
		if (reqBody.indexOf("area_id") > 0) {
			map.put("area_id",o.getString("area_id"));
		}
		if (reqBody.indexOf("lane_id") > 0) {
			map.put("lane_id",o.getString("lane_id"));
		}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public PayResultBean payResultPost(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
		PayResultBean bean = new PayResultBean();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			line = new String(line.getBytes());
			sb.append(line);
		}

		String reqBody = sb.toString();
		br.close();
		br = null;
		JSONObject o = JSONObject.fromObject(reqBody);
		if (reqBody.indexOf("mv_license") > 0) {
			bean.setMv_license(o.getString("mv_license"));
		}
		if (reqBody.indexOf("park_id") > 0) {
			bean.setPark_id(o.getString("park_id"));
		}
		if (reqBody.indexOf("area_id") > 0) {
			bean.setArea_id(o.getString("area_id"));
		}

		if (reqBody.indexOf("car_color") > 0) {
			bean.setCar_color(Integer.parseInt(o.getString("car_color")));
		}
		if (reqBody.indexOf("execute_status") > 0) {
			bean.setExecute_status(Integer.parseInt(o.getString("execute_status")));
		}
		if (reqBody.indexOf("out_trade_no") > 0) {
			bean.setOut_trade_no(o.getString("out_trade_no"));
		}
		if (reqBody.indexOf("trade_no") > 0) {
			bean.setTrade_no(o.getString("trade_no"));
		}
		if (reqBody.indexOf("buyer_pay_amount") > 0) {
			bean.setBuyer_pay_amount(Integer.parseInt(o.getString("buyer_pay_amount")));
		}
		if (reqBody.indexOf("receipt_amount") > 0) {
			bean.setReceipt_amount(Integer.parseInt(o.getString("receipt_amount")));
		}
		if (reqBody.indexOf("gmt_payment") > 0) {
			bean.setGmt_payment(o.getString("gmt_payment"));
		}
		if (reqBody.indexOf("trade_type") > 0) {
			bean.setTrade_type(o.getString("trade_type"));
		}
		if (reqBody.indexOf("pay_method") > 0) {
			bean.setPay_method(Integer.parseInt(o.getString("pay_method")));
		}
		if (reqBody.indexOf("deal_time") > 0) {
			bean.setDeal_time(o.getString("deal_time"));
		}
		if (reqBody.indexOf("lane_id") > 0) {
			bean.setLane_id(Integer.parseInt(o.getString("lane_id")));
		}
		
		return bean;
	}

	public InputFindPlaceBean receiveFindPlacePost(HttpServletRequest request)
			throws IOException, UnsupportedEncodingException {
		InputFindPlaceBean bean = new InputFindPlaceBean();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			line = new String(line.getBytes());
			sb.append(line);
		}

		String reqBody = sb.toString();
		br.close();
		br = null;
		JSONObject o = JSONObject.fromObject(reqBody);

		if (reqBody.indexOf("park_id") > 0) {
			bean.setPark_id(o.getString("park_id"));
		}
		if (reqBody.indexOf("area_id") > 0) {
			bean.setArea_id(o.getString("area_id"));
		}
		if (reqBody.indexOf("type") > 0) {
			bean.setType(o.getString("type"));
		}
		return bean;
	}

	/**
	 * 
	 * @Title : InputPaymentChannelBean
	 * @功能描述: 封装支付消息
	 * @传入参数：@param request
	 * @传入参数：@return
	 * @传入参数：@throws IOException
	 * @传入参数：@throws UnsupportedEncodingException
	 * @返回类型：PayResultBean
	 * @throws ：
	 */
	public PaymentChannelBean receivePaymentChannel(HttpServletRequest request)
			throws IOException, UnsupportedEncodingException {
		PaymentChannelBean bean = new PaymentChannelBean();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			line = new String(line.getBytes());
			sb.append(line);
		}

		String reqBody = sb.toString();
		br.close();
		br = null;
		JSONObject o = JSONObject.fromObject(reqBody);

		if (reqBody.indexOf("type") > 0) {
			bean.setType(o.getString("type"));
		}
		if (reqBody.indexOf("terminal_type") > 0) {
			bean.setTerminal_type(Integer.parseInt(o.getString("terminal_type")));
		}

		if (reqBody.indexOf("trml_park_id") > 0) {
			bean.setTrml_park_id(o.getString("trml_park_id"));
		}

		if (reqBody.indexOf("trml_area_id") > 0) {
			bean.setTrml_area_id(o.getString("trml_area_id"));
		}

		if (reqBody.indexOf("terminal_id") > 0) {
			bean.setTerminal_id(Integer.parseInt(o.getString("terminal_id")));
		}
		if (reqBody.indexOf("pay_method") > 0) {
			bean.setPay_method(Integer.parseInt(o.getString("pay_method")));
		}
		if (reqBody.indexOf("mv_license") > 0) {
			bean.setMv_license(o.getString("mv_license"));
		}
		if (reqBody.indexOf("car_color") > 0) {
			bean.setCar_color(Integer.parseInt(o.getString("car_color")));
		}
		if (reqBody.indexOf("vehicle_class") > 0) {
			bean.setVehicle_class(Integer.parseInt(o.getString("vehicle_class")));
		}
		if (reqBody.indexOf("entry_time") > 0) {
			bean.setEntry_time(new Date(Long.parseLong(o.getString("entry_time")) * 1000));
		}
		if (reqBody.indexOf("park_id") > 0) {
			bean.setPark_id(o.getString("park_id"));
		}
		// 区域编号
		if (reqBody.indexOf("area_id") > 0) {
			bean.setArea_id(o.getString("area_id"));
		}
		// 入口车道
		if (reqBody.indexOf("lane_id") > 0) {
			bean.setEntry_lane(Integer.parseInt(o.getString("lane_id")));
		}
		
		//支付金额
		if(reqBody.indexOf("prepay_bill") > 0)
		{
			bean.setPrepay_bill(Integer.parseInt(o.getString("prepay_bill")));
		}
		
		//支付时间
		if(reqBody.indexOf("pay_time") > 0)
		{
			bean.setPay_time(new Date(Long.parseLong(o.getString("pay_time")) * 1000));
		}
		return bean;
	}
	
	
	/**
	 * 
	   * @Title : receiveModelCharg 
	   * @功能描述: 获得模拟计费字段
	   * @传入参数：@param request
	   * @传入参数：@return
	   * @返回类型：ReceiveModelChargeBean 
	   * @throws ：
	 */
	public ReceiveModelChargeBean receiveModelCharge(HttpServletRequest request)
	{
		ReceiveModelChargeBean bean = new ReceiveModelChargeBean();
		BufferedReader br = null;
		JSONObject o = null;
		String reqBody = null;
		try {
		br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			line = new String(line.getBytes());
			sb.append(line);
		}
		reqBody = sb.toString();
		o = JSONObject.fromObject(reqBody);
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// 判断接口字段是否为空
		if(o == null || reqBody == null)
		{
			return bean;
		}
		// type字段
		if (reqBody.indexOf("type") > 0) {
			bean.setType(o.getString("type"));
		}
		// 停车场编号
		if (reqBody.indexOf("park_id") > 0) {
			bean.setPark_id(o.getString("park_id"));
		}
		// 区域编号
		if (reqBody.indexOf("area_id") > 0) {
			bean.setArea_id(o.getString("area_id"));
		}
		// 车道编号
		if (reqBody.indexOf("lane_id") > 0) {
			bean.setLane_id(o.getString("lane_id"));
		}
		// 车牌号
		if (reqBody.indexOf("mv_license") > 0) {
			bean.setMv_license(o.getString("mv_license"));
		}
		
		// 车牌颜色
		if (reqBody.indexOf("car_color") > 0) {
			bean.setCar_color(Integer.parseInt(o.getString("car_color")));
		}
		
		// 车型
		if (reqBody.indexOf("vehicle_class") > 0) {
			bean.setVehicle_class(Integer.parseInt(o.getString("vehicle_class")));
		}
		
		// 入口时间
		if (reqBody.indexOf("entry_time") > 0) {
			bean.setEntry_time(new Date(Long.parseLong(o.getString("entry_time"))*1000));
		}
		return bean;
	}
	
	/**
	 * 
	   * @Title : receiveModelCharg 
	   * @功能描述: 获得模拟计费字段
	   * @传入参数：@param request
	   * @传入参数：@return
	   * @返回类型：ReceiveModelChargeBean 
	   * @throws ：
	 */
	public ReceiveModelPayBean receiveModelPay(HttpServletRequest request)
	{
		ReceiveModelPayBean bean = new ReceiveModelPayBean();
		BufferedReader br = null;
		JSONObject o = null;
		String reqBody = null;
		try {
		br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			line = new String(line.getBytes());
			sb.append(line);
		}
		reqBody = sb.toString();
		o = JSONObject.fromObject(reqBody);
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// 判断接口字段是否为空
		if(o == null || reqBody == null)
		{
			return bean;
		}
		// type字段
		if (reqBody.indexOf("type") > 0) {
			bean.setType(o.getString("type"));
		}
		// 车牌号
		if (reqBody.indexOf("mv_license") > 0) {
			bean.setMv_license(o.getString("mv_license"));
		}
		
		// 车牌颜色
		if (reqBody.indexOf("car_color") > 0) {
			bean.setCar_color(Integer.parseInt(o.getString("car_color")));
		}
		
		// 车型
		if (reqBody.indexOf("vehicle_class") > 0) {
			bean.setVehicle_class(Integer.parseInt(o.getString("vehicle_class")));
		}
		
		// 停车场编号
		if (reqBody.indexOf("park_id") > 0) {
			bean.setPark_id(o.getString("park_id"));
		}
		// 区域编号
		if (reqBody.indexOf("area_id") > 0) {
			bean.setArea_id(o.getString("area_id"));
		}
		// 车道编号
		if (reqBody.indexOf("lane_id") > 0) {
			bean.setLane_id(o.getString("lane_id"));
		}
		
		// 收费终端类型
		if (reqBody.indexOf("terminal_type") > 0) {
			bean.setTerminal_type(Integer.parseInt(o.getString("terminal_type")));
		}
		// 收费终端停车场编号
		if (reqBody.indexOf("trml_park_id") > 0) {
			bean.setTrml_park_id(o.getString("trml_park_id"));
		}
		// 收费终端区域编号
		if (reqBody.indexOf("trml_area_id") > 0) {
			bean.setTrml_area_id(o.getString("trml_area_id"));
		}
		// 收费终端车道编号
		if (reqBody.indexOf("terminal_id") > 0) {
			bean.setTerminal_id(o.getString("terminal_id"));
		}
		
		// 支付类型
		if (reqBody.indexOf("pay_method") > 0) {
			bean.setPay_method(Integer.parseInt(o.getString("pay_method")));
		}
		
		// 支付费用
		if (reqBody.indexOf("prepay_toll") > 0) {
			bean.setPrepay_toll(Integer.parseInt(o.getString("prepay_toll")));
		}
		
		// 支付时间
		if (reqBody.indexOf("pay_time") > 0) {
			bean.setPay_time(new Date(Long.parseLong(o.getString("pay_time"))*1000));
		}
		
		// 支付时间
		if (reqBody.indexOf("entry_time") > 0) {
			bean.setEntry_time(new Date(Long.parseLong(o.getString("entry_time"))*1000));
		}
		return bean;
	}
	
	

	// @RequestMapping("/testF2F")
	// @ResponseBody
	public String testF2F(HttpServletRequest request) {
		Date d1 = new Date();

		String post = null;
		try {
			post = PostOrGet.sendPost(
					"http://192.168.4.250:8080/dnzn_pay_platform/communication/paymentPlatform/general/payByQRCode.shtml",
					// "{ 'auth_code': '288636906939369738', 'station_id':
					// '110',
					// 'subject': '测试月票', 'total_amount': '1'}",
					URLEncoder.encode(
							"{'function_type':'PAY','auth_code':'286698139218770485','total_amount':'1','server_type':'1','scan_device_code':'123456','mv_license':'苏A12345学','plate_color':'1','apply_time':'2017-08-25 16:25:00','operator_id':'971','out_trade_no':'3208821990909012356248','network':'3201','station_id':'1234567','lane_id':'1','subject':'车道收费'}",
							"utf-8"),
					null);
			if (post != null) {
				log.info(new Date().getTime() - d1.getTime());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return post;
	}

	@RequestMapping("/testIn")
	@ResponseBody
	public String test(HttpServletRequest request) {
		Date d1 = new Date();

		String post = null;
		// try {
//		String code = "{'black_list_province':'32','car_color':0,'card_id':'','lane_id':'1001','mv_license':'测A12345','now_time':'','obu_id':'1234567','type':'E1','vehicle_class':1}";
		
		String code ="{    'area_id': '001',    'car_color': '0',    'card_id': '',    'lane_id': '1002',    'mv_license': '苏H99999',    'obu_id': '',    'park_id': '3201000622',    'type': 'E1',    'vehicle_class': '1'}";
		post = PostOrGet.sendPost("http://localhost:8080/etc_parking/valid/validIn.shtml",
				// "{ 'auth_code': '288636906939369738', 'station_id': '110',
				// 'subject': '测试月票', 'total_amount': '1'}",
				// URLEncoder.encode(
				code
				// , "utf-8"),
				, null);
		if (post != null) {
			log.info(new Date().getTime() - d1.getTime());
		}
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return post;
	}

	@RequestMapping("/testOutSmall")
	@ResponseBody
	public String test2(HttpServletRequest request) {
		Date d1 = new Date();
		log.info("testOutSmall时间：" + d1.getTime());
		String post = PostOrGet.sendPost("http://localhost:8080/etc_parking/valid/validOut.shtml",
				"{'black_list_province':'32','car_color':0,'card_id':'','lane_id':'1101','mv_license':'测A66666','now_time':'','obu_id':'61e3a496','type':'E2','vehicle_class':'1','park_id':'3208260001','area_id':'001','verify_code':''}",
				null);
		if (post != null) {
			log.info(new Date().getTime() - d1.getTime());
		}
		return post;
	}
	

//	@RequestMapping("/testF2")
//	@ResponseBody
	public String testF2(HttpServletRequest request) {
		Date d1 = new Date();
		log.info("testF2时间：" + d1.getTime());
		String post = PostOrGet.sendPost("http://localhost:8080/etc_parking/valid/validOut.shtml",
//				"{'black_list_province':'0','car_color':0,'card_id':'','lane_id':'','mv_license':'苏H88888','now_time':'','obu_id':'','park_id':'3208260001','type':'F2','vehicle_class':0,'verify_code':''}",
				"{'area_id':'001','black_list_province':'0','car_color':0,'card_id':'','lane_id':'','mv_license':'苏H88888','now_time':'','obu_id':'','park_id':'3208260001','type':'F2','vehicle_class':0,'verify_code':''}",
				
				null);
		// "{'type' :
		// 'E2','mv_license':'苏A12345','car_color':'0','vehicle_class':'1','black_list_province':'32','card_id':'2','obu_id':'100'}",
		// null);
		if (post != null) {
			log.info(new Date().getTime() - d1.getTime());
		}
		return post;
	}
	
	
	
//	@RequestMapping("/testMobilePay")
//	@ResponseBody
	public String test15(HttpServletRequest request) {
		Date d1 = new Date();
		log.info("testOutSmall时间：" + d1.getTime());
		String post = PostOrGet.sendPost("http://172.16.1.250:8160/etc_parking/valid/mobilePay.shtml",
				"{'area_id':'001','buyer_pay_amount':600,'car_color':0,'deal_time':'2018-05-12 11:02:31','execute_status':1,'gmt_payment':'2018-05-12 11:02:24','mv_license':'苏H88888','out_trade_no':'WX1526094125342283586','park_id':'3201000622','pay_method':3,'receipt_amount':600,'trade_no':'4200000152201805127993501154','trade_type':'1'}",
				null);
		// "{'type' :
		// 'E2','mv_license':'苏A12345','car_color':'0','vehicle_class':'1','black_list_province':'32','card_id':'2','obu_id':'100'}",
		// null);
		if (post != null) {
			log.info(new Date().getTime() - d1.getTime());
		}
		return post;
	}


//	@RequestMapping("/testOutLarge")
//	@ResponseBody
	public String testOutLarge(HttpServletRequest request) {
		Date d1 = new Date();
		log.info("testOutLarge时间：" + d1.getTime());
		String post = PostOrGet.sendPost("http://localhost:8080/etc_parking/valid/validOut.shtml",
				"{'black_list_province':'32','car_color':0,'card_id':'','lane_id':'1101','mv_license':'测A12345','now_time':'','obu_id':'54321','type':'E2','vehicle_class':2,'verify_code':''}",
				null);
		// "{'type' :
		// 'E2','mv_license':'苏A12345','car_color':'0','vehicle_class':'1','black_list_province':'32','card_id':'2','obu_id':'100'}",
		// null);
		if (post != null) {
			log.info(new Date().getTime() - d1.getTime());
		}
		return post;
	}

//	@RequestMapping("/test3")
//	@ResponseBody
	public String test3(HttpServletRequest request) {
		Date d1 = new Date();

		String post = PostOrGet.sendPost("http://localhost:8080/etc_parking/valid/findParkPlace.shtml", "", null);
		if (post != null) {
			log.info(new Date().getTime() - d1.getTime());
		}
		return post;
	}

	@ResponseBody
	@RequestMapping("/test4")
	public String test4(HttpServletRequest request) {
		Date d1 = new Date();
		String post = PostOrGet.sendPost("http://localhost:8080/etc_parking/valid/confirmIn.shtml",
				"{'type' : 'E3','mv_license':'苏A12436','car_color':'0','area_id':'001','lane_id':'1002','valid_type':'8','entry_time':'1530666979'}", null);
		if (post != null) {
			log.info(new Date().getTime() - d1.getTime());
		}
		return post;
	}
	
	
	@ResponseBody
	@RequestMapping("/test5")
	public String test5(HttpServletRequest request) {
		Date d1 = new Date();
		String post = PostOrGet.sendPost("http://localhost:8080/etc_parking/valid/confirmOut.shtml",
				"{'type':'E4','mv_license':'苏A12436','car_color':'0','lane_id':'1001',"
				+ "'park_id':'3201000003','area_id':'001','vheilce_class':'0','obu_id':'','entry_time':'1532595597'}", null);
		if (post != null) {
			log.info(new Date().getTime() - d1.getTime());
		}
		return post;
	}
	
//	@RequestMapping("/testE8")
//	@ResponseBody
	public String testE8(HttpServletRequest request) {
		Date d1 = new Date();
		String post = PostOrGet.sendPost("http://localhost:8080/etc_parking/valid/currentLicenseInfo.shtml",
				"{'type' : 'E8','mv_license':'苏A12345'}", null);
		if (post != null) {
			log.info(new Date().getTime() - d1.getTime());
		}
		return post;
	}
	
//	@RequestMapping("/testE9")
//	@ResponseBody
	public String testE9(HttpServletRequest request) {
		Date d1 = new Date();
		ReceiveModelChargeBean bean = new ReceiveModelChargeBean();
		
		bean.setType("E9");
		bean.setPark_id("3201060001");
		bean.setArea_id("001");
		bean.setLane_id("9999");
		bean.setMv_license("苏A777777");
		bean.setCar_color(0);
		
		Map map = new HashMap();
		map.put("type", "E9");
		map.put("park_id", "3201060001");
		map.put("area_id", "001");
		map.put("lane_id", "9999");
		map.put("mv_license", "苏A777777");
		map.put("car_color", "0");
		map.put("vehicle_class", "1");
		Date startTime = DateUtil.parse4yMdHms("2018-03-15 19:33:46");
//		map.put("entry_time", String.valueOf((System.currentTimeMillis()-(2*24*60*60*1000))/1000));
		map.put("entry_time", String.valueOf(startTime.getTime()/1000));
		String str = JSONObject.fromObject(map).toString();
		String post = PostOrGet.sendPost("http://localhost:8080/etc_parking/valid/modelChargeSearch.shtml",
				str, null);
		if (post != null) {
			log.info(new Date().getTime() - d1.getTime());
		}
		return post;
	}
	
//	@RequestMapping("/testE10")
//	@ResponseBody
	public String testE10(HttpServletRequest request) {
		Date d1 = new Date();
		String post = PostOrGet.sendPost("http://localhost:8080/etc_parking/valid/entry_image.shtml",
				"{'mv_license':'苏A88888'}", null);
		if (post != null) {
			log.info("使用时间"+(new Date().getTime() - d1.getTime()));
		}
		return post;
	}
	
	/**
	 *   
	   * @Title : testE11 
	   * @功能描述: 查询
	   * @传入参数：@param request
	   * @传入参数：@return
	   * @返回类型：String 
	   * @throws ：
	 */
//	@RequestMapping("/testE11")
//	@ResponseBody
	public String testE11(HttpServletRequest request) {
		Date d1 = new Date();
		String post = PostOrGet.sendPost("http://localhost:8080/etc_parking/valid/lane_license.shtml",
				"{'lane_id':10101}", null);
		if (post != null) {
			log.info("使用时间"+(new Date().getTime() - d1.getTime()));
		}
		return post;
	}
	
	public CommunicationService getCommunicationService() {
		return communicationService;
	}

	public void setCommunicationService(CommunicationService communicationService) {
		this.communicationService = communicationService;
	}

}
