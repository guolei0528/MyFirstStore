package com.project.backCharge.tempCharge.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Bidi;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backCharge.tempCharge.model.ChargeEntryBean;
import com.project.backCharge.tempCharge.model.ChargeRuleCostBean;
import com.project.backCharge.tempCharge.model.MemberSaleInfoBean;
import com.project.backCharge.tempCharge.model.TempCostBean;
import com.project.backCharge.tempCharge.model.WhiteListBean;
import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleFactory;
import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleXml;
import com.project.backMng.admin.ChargeRuleMng.model.ChargeRuleBean;
import com.project.backMng.admin.member.service.CarInfoMngServiceImpl;
import com.project.tools.DateUtil;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.mybatis.service.BaseService;
import com.redoak.jar.tag.model.ColEnum;
import com.redoak.jar.util.ColEnumUtil;
import com.redoak.jar.util.PropertiesUtil;

public class DynamicCalculationTempCostService extends BaseService {

	private final static Logger log = LogManager.getLogger(DynamicCalculationTempCostService.class);

	private static Date elevenHalfTime = null;

	private static Date twelTime = null;
	private static Date zeroTime = null;
	private static Date sixTime = null;

	private final static SimpleDateFormat sdfRule = new SimpleDateFormat("HH:mm:ss");

	// 按时间类型计费规则
	// private final static int CHARGE_RULE_TYPE_MINUTE = 0;

	// 获取当前时间
	// Date now = new Date();

	// 设置是否为免费标识
	boolean isFree = false;

	// 设置是否为优惠标识
	boolean isExistRebate = false;

	// 获取理想金额
	BigDecimal incomeCost = new BigDecimal(0);

	// 声明免费时长
	BigDecimal freeMinute = new BigDecimal(0);

	public DynamicCalculationTempCostService() {
		super();
		// super.setIBATIS_NAME_SPACE("sys.init.InitStart");
		// 停车场临时计费
		setIBATIS_NAME_SPACE("backCharge.tempCharge.tempCost");
		// //计费规则
		// setIBATIS_NAME_SPACE("backCharge.tempCharge.chargeRules");
		// //白名单
		// setIBATIS_NAME_SPACE("backCharge.tempCharge.whiteList");
		// //会员售卖表
		// setIBATIS_NAME_SPACE("backCharge.tempCharge.chargeMemberSaleInfo");
	}

	public void init() {

		try {
			Thread.sleep(10);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		/*=try {
			elevenHalfTime = sdfRule.parse("23:30:00");
			twelTime = sdfRule.parse("24:00:00");
			zeroTime = sdfRule.parse("00:00:00");
			sixTime = sdfRule.parse("06:00:00");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}*/

		
		// 定时任务
		/**new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						cycleCalculateToll();
						// process();
						// 涟水县计费规则（写死）
//						 processLS();

//						 processTypeSD();
						// 60秒循环一次
//						Thread.sleep(60000);
						// 1小时循环一次
						Thread.sleep(3600000);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error(e.getMessage());
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							log.error(e);
							e1.printStackTrace();
						}
					}
				} 
			}
		}).start();**/
	}

	// 周期计算通行费
	private void cycleCalculateToll() {
		// 定时任务需要处理的操作
		// 获得计费规则当前是否为免费的类型
		// ChargeRuleCostBean chargeRule = findChargeRulesByNow(2);

		// 所有计费规则
		// List<ChargeRuleCostBean> chargeRuleBeans = null;

		// 所有白名单
		List<WhiteListBean> whiteListBeans = null;

		// 所有会员
//		List<MemberSaleInfoBean> memberSaleInfoBeans = null;

		// 查询所有白名单
		whiteListBeans = super.findList("backCharge.tempCharge.whiteList.findWhiteList");
		// 查询所有会员售卖表
		/*memberSaleInfoBeans = super.findList(
				"backCharge.tempCharge.chargeMemberSaleInfo.findMemberSaleInfoTypeListByNow");*/

		// 获得临时表中车辆数据
		List<TempCostBean> tempCosts = findTempCostList();

		// 判断标志
		boolean flag = true;

		// 迭代停车场车辆
		for (TempCostBean tempCost : tempCosts) {

			flag = true;
			// 理想金额为0
			tempCost.setIncomeBill(0);
			// 实际金额为0
			tempCost.setRealBill(0);

			// 为节假日
			if (isFree) {
				// 更新金额
				update(tempCost);
				continue;
			}

			String mvLicense = tempCost.getMvLicense();
//			int color = tempCost.getCarColor();
			
			// 校验白名单
			for (WhiteListBean whiteListBean : whiteListBeans) {
				if (mvLicense.equals(whiteListBean.getMvLicense())) {
					flag = false;
					break;
				}
			}
			

			// 判断是否为白名单，如果是白名单跳过会员售卖的迭代判断
			if (flag) {
				/*for (MemberSaleInfoBean memberSaleInfoBean : memberSaleInfoBeans) {
					// 判断会员
					if (mvLicense.equals(memberSaleInfoBean.getMvLicense()) 
							 && color == memberSaleInfoBean.getCarColor()) {
						// 实际金额为0
						// tempCost.setRealBill(0);
						flag = false;
						break;
					}
				}*/
				
				String id = PropertiesUtil.get("CHARGE_TYPE");
				
				//获得按时间规则方式
				ChargeRuleFactory chargeRuleFactory = new ChargeRuleFactory();
				ChargeRuleXml chargeRuleXml = chargeRuleFactory.createChargeRule(id);
				long toll = chargeRuleXml.calculateToll(tempCost.getEntryTime(), new Date(),tempCost.getVehicleClass());
				
				tempCost.setIncomeBill((int)toll);
				if (flag) {
					tempCost.setRealBill((int)toll);
				}
			}
			//更新
			update(tempCost);
		}
		tempCosts = null;
		whiteListBeans = null;
//		memberSaleInfoBeans = null;
	}
	
	

	// 通用的
	private void process() throws ParseException {
		// 定时任务需要处理的操作
		// 获得计费规则当前是否为免费的类型
		ChargeRuleCostBean chargeRule = findChargeRulesByNow(2);

		// 所有计费规则
		List<ChargeRuleCostBean> chargeRuleBeans = null;

		// 所有白名单
		List<WhiteListBean> whiteListBeans = null;

		// 所有会员
		List<MemberSaleInfoBean> memberSaleInfoBeans = null;

		// 有节假日时计费规则
		if (chargeRule != null) {
			isFree = true;
		} else {
			// 查询所有计费规则
			chargeRuleBeans = super.findList("backCharge.tempCharge.chargeRules.findChargeRulesByNow");
			// 查询所有白名单
			whiteListBeans = super.findList("backCharge.tempCharge.whiteList.findWhiteList");
			// 查询所有会员售卖表
			memberSaleInfoBeans = super.findList(
					"backCharge.tempCharge.chargeMemberSaleInfo.findMemberSaleInfoTypeListByNow");

		}

		// 获得临时表中车辆数据
		List<TempCostBean> tempCosts = findTempCostList();

		// 判断标志
		boolean flag = true;
		// 迭代停车场车辆
		for (TempCostBean tempCost : tempCosts) {

			flag = true;
			// 理想金额为0
			tempCost.setIncomeBill(0);
			// 实际金额为0
			tempCost.setRealBill(0);

			// 更新图片
			updateImage(tempCost);

			// 为节假日
			if (isFree) {
				// 更新金额
				update(tempCost);
				continue;
			}

			String mvLicense = tempCost.getMvLicense();
			// 校验白名单
			for (WhiteListBean whiteListBean : whiteListBeans) {
				if (mvLicense.equals(whiteListBean.getMvLicense())) {
					// 理想金额为0
					// tempCost.setIncomeBill(0);
					// 实际金额为0
					// tempCost.setRealBill(0);

					flag = false;
					break;
				}
			}

			// 判断是否为白名单，如果是白名单跳过会员售卖的迭代判断
			if (flag) {
				for (MemberSaleInfoBean memberSaleInfoBean : memberSaleInfoBeans) {
					// 判断会员
					if (mvLicense.equals(memberSaleInfoBean.getMvLicense())) {
						// 实际金额为0
						// tempCost.setRealBill(0);
						flag = false;
						break;
					}
				}

				// 判断计费规则不为空
				if (chargeRuleBeans.size() != 0) {

					// 当前时间
					Date nowTime = new Date();
					// 当前时间-进入停车场时间
					// long timeOffset = nowTime.getTime()
					// tempCost.getEntryTime().getTime();
					// 计费的流水累加金额
					BigDecimal currentCost = new BigDecimal(0);

					// 免费时长初始值
					freeMinute = new BigDecimal(0);
					// 免费flag
					boolean freeFlag = true;

					// 初始开始时间
					for (long currentTime = tempCost.getEntryTime().getTime(); currentTime < nowTime.getTime();) {

						// 遍历计费规则
						for (ChargeRuleCostBean chargeRuleBean : chargeRuleBeans) {

							// 判断是否为按时间计费规则
							// if(chargeRuleBean.getChargeType() ==
							// CHARGE_RULE_TYPE_MINUTE)
							// {
							//
							// }
							// 计费开始字符串：比如08:00
							String startTime = chargeRuleBean.getBeginTime();
							// 计费结束字符串：比如16:00
							String endTime = chargeRuleBean.getEndTime();
							// 转换小时：分钟的格式类
							SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

							// 获得计算时间流的小时：分钟
							String costTime = sdf.format(new Date(currentTime));
							// 转换成小时：分钟的时间
							Date costDate = sdf.parse(costTime);

							// String nowStr = sdf.format(nowTime);
							// Date nowDate = sdf.parse(nowStr);

							// 起始时间
							Date start = sdf.parse(startTime);

							// 截止时间
							Date end = sdf.parse(endTime);

							// 判断流水时间是否在某个计费规则之内
							boolean inRuleFlag = false;
							inRuleFlag = isInRule(start, end, costDate, sdf);
							// 这个流水时间在计费规则之内
							if (inRuleFlag) {
								// 判断是否已减过免费时间
								if (freeFlag) {
									// 设置免费时长
									freeMinute = new BigDecimal(chargeRuleBean.getFreeTime());
									freeFlag = false;
								}

								long current = 0;
								// 当前时间-流水时间=相差时间
								long timeOffset = nowTime.getTime() - currentTime;
								// 计算白天不超过24点的流水时长,当前时间小于起始阶段时间
								if (end.getTime() > start.getTime()
										&& (end.getTime() - costDate.getTime()) >= timeOffset) {
									// current = nowDate.getTime() -
									// costDate.getTime();
									current = timeOffset;
								}
								// 计算白天不超过24点的流水时长,当前时间大于起始阶段时间
								if (end.getTime() > start.getTime()
										&& (end.getTime() - costDate.getTime()) < timeOffset) {
									current = end.getTime() - costDate.getTime();
								}
								// 计算夜晚超过24点的流水时长，当前时间小于夜晚长度
								if ((end.getTime() <= start.getTime())) {
									// 计算上半夜的时间长度
									long beforeMidnight = sdf.parse("24:00").getTime() - start.getTime();
									// 计算下半夜的时间长度
									long afterMidnight = end.getTime() - sdf.parse("00:00").getTime();
									// 计算过夜的总时间长度
									long countTime = beforeMidnight + afterMidnight;

									long currentMidnight;
									if (costDate.getTime() < sdf.parse("24:00").getTime()
											&& costDate.getTime() >= start.getTime()) {
										currentMidnight = sdf.parse("24:00").getTime() - costDate.getTime();
										currentMidnight = currentMidnight + afterMidnight;
									} else {
										currentMidnight = end.getTime() - costDate.getTime();
									}

									// 判断如果当前时间-上一个节点的流水时间小于过夜总时间，计算出节点时间到当前时间的差值
									if (currentMidnight >= timeOffset) {
										current = timeOffset;
									}
									// 判断如果当前时间-上一个节点的流水时间大于过夜总时间，夜晚总时间-上一个节点的时间长度
									else {
										current = currentMidnight;
									}
								}

								// 计算出流水金额
								BigDecimal cost = CalculationBill(current, chargeRuleBean);
								// 累加流水金额
								currentCost = currentCost.add(cost);
								// 累加流水时间
								currentTime = currentTime + (current);

								// 判断是否计费规则的结束时间大于开始时间
								// if(end.getTime() > start.getTime())
								// {
								// //判断结束时间是否大于流水时间
								// long current =
								// end.getTime()-costDate.getTime();
								// if(end.getTime() >= nowDate.getTime())
								// {
								// current = nowDate.getTime();
								// }
								// //计算出流水金额
								// BigDecimal cost =
								// CalculationBill(current,chargeRuleBean);
								// //累加流水金额
								// currentCost = currentCost.add(cost);
								// //累加流水时间
								// currentTime = currentTime+(current);
								// }
								// else
								// {
								// long current =
								// end.getTime()-costDate.getTime();
								// if(sdf.parse("24:00").getTime() >=
								// nowDate.getTime())
								// {
								// current = nowDate.getTime();
								// }
								// if(sdf.parse("00:00").getTime() <
								// nowDate.getTime())
								// {
								// current = nowDate.getTime();
								//
								// if(nowDate.getTime() <= end.getTime())
								// {
								// current = current + nowDate.getTime();
								// }
								// else
								// {
								//
								// }
								// }
								//
								// //计算出流水金额,计费规则超过了24点
								// BigDecimal cost =
								// CalculationBill(sdf.parse("24:00").getTime()-costDate.getTime(),chargeRuleBean);
								// //累加流水金额
								// currentCost = currentCost.add(cost);
								// //累加流水时间
								// currentTime =
								// currentTime+(sdf.parse("24:00").getTime()-costDate.getTime());
								//
								// //计算出流水金额,计费规则超过了24点
								// cost =
								// CalculationBill(end.getTime()-sdf.parse("24:00").getTime(),chargeRuleBean);
								// //累加流水金额
								// currentCost = currentCost.add(cost);
								// //累加流水时间
								// currentTime =
								// currentTime+(sdf.parse("24:00").getTime()-costDate.getTime());
								// }
							}
						}
					}
					tempCost.setIncomeBill(currentCost.intValue());
					if (flag) {
						tempCost.setRealBill(currentCost.intValue());
					}
					/**
					 * try { //遍历计费规则 for (ChargeRuleCostBean chargeRuleBean :
					 * chargeRuleBeans) { boolean inRule; inRule =
					 * isInRule(chargeRuleBean);
					 * 
					 * if (inRule) { BigDecimal bill = CalculationBill(tempCost,
					 * chargeRuleBean); tempCost.setIncomeBill(bill.intValue());
					 * if (!flag) { tempCost.setRealBill(bill.intValue());
					 * break; }
					 * 
					 * } } } catch (ParseException e) { // TODO Auto-generated
					 * catch block e.printStackTrace(); }
					 */
				}
			}

			update(tempCost);
		}
	}

	/**
	 * 
	 * @Title : processType
	 * @功能描述: 第二种计费规则（写死）
	 * @传入参数：@throws ParseException
	 * @返回类型：void
	 * @throws ：
	 */
	private void processLS() throws ParseException {
		// 定时任务需要处理的操作
		// 获得计费规则当前是否为免费的类型
		// ChargeRuleCostBean chargeRule = findChargeRulesByNow(2);

		// 所有计费规则
		// List<ChargeRuleCostBean> chargeRuleBeans = null;

		// 所有白名单
		List<WhiteListBean> whiteListBeans = null;

		// 所有会员
		List<MemberSaleInfoBean> memberSaleInfoBeans = null;

		// 查询所有白名单
		whiteListBeans = super.findList("backCharge.tempCharge.whiteList.findWhiteList");
		// 查询所有会员售卖表
		memberSaleInfoBeans = super.findList(
				"backCharge.tempCharge.chargeMemberSaleInfo.findMemberSaleInfoTypeListByNow");

		// 获得临时表中车辆数据
		List<TempCostBean> tempCosts = findTempCostList();

		// 判断标志
		boolean flag = true;
		// 迭代停车场车辆
		for (TempCostBean tempCost : tempCosts) {

			Date nowDate = new Date();
			// 判断是否支付了
			if (tempCost.getPayStatus() != null && tempCost.getPayStatus() != 0) {
				// 付完预支付停车费后
				if (tempCost.getPayTime() != null && (nowDate.getTime() - tempCost.getPayTime().getTime()) <= 900000) {
					continue;
				}
			}
			;

			flag = true;
			// 理想金额为0
			tempCost.setIncomeBill(0);
			// 实际金额为0
			tempCost.setRealBill(0);

			// 比对当前时间是否在23：30至6：00
			String nowDateStr = sdfRule.format(nowDate);
			Date nowTime = sdfRule.parse(nowDateStr);
			if (nowTime.getTime() > elevenHalfTime.getTime() && nowTime.getTime() <= twelTime.getTime()) {
				update(tempCost);
				continue;
			}
			if (nowTime.getTime() >= zeroTime.getTime() && nowTime.getTime() < sixTime.getTime()) {
				update(tempCost);
				continue;
			}

			// 更新图片
			// updateImage(tempCost);

			String mvLicense = tempCost.getMvLicense();
			// 校验白名单
			for (WhiteListBean whiteListBean : whiteListBeans) {
				if (mvLicense.equals(whiteListBean.getMvLicense())) {
					flag = false;
					break;
				}
			}

			// 判断是否为白名单，如果是白名单跳过会员售卖的迭代判断
			if (flag) {
				for (MemberSaleInfoBean memberSaleInfoBean : memberSaleInfoBeans) {
					// 判断会员
					if (mvLicense.equals(memberSaleInfoBean.getMvLicense())) {
						flag = false;
						break;
					}
				}

				Date now = new Date();
				// 判断计费规则不为空
				int cost = resultCost(tempCost, now);
				tempCost.setIncomeBill(cost);

				if (flag) {
					tempCost.setRealBill(cost);
				} else {
					tempCost.setRealBill(0);
				}
			}
			update(tempCost);
		}
	}

	/**
	 * 
	 * @Title : processType
	 * @功能描述: 第二种计费规则（写死）
	 * @传入参数：@throws ParseException
	 * @返回类型：void
	 * @throws ：
	 */
	private void processTypeSD() throws ParseException {
		// 定时任务需要处理的操作
		// 获得计费规则当前是否为免费的类型
		// ChargeRuleCostBean chargeRule = findChargeRulesByNow(2);

		// 所有计费规则
		// List<ChargeRuleCostBean> chargeRuleBeans = null;

		// 所有白名单
		List<WhiteListBean> whiteListBeans = null;

		// 所有会员
		List<MemberSaleInfoBean> memberSaleInfoBeans = null;

		// 查询所有白名单
		whiteListBeans = super.findList("backCharge.tempCharge.whiteList.findWhiteList");
		// 查询所有会员售卖表
		memberSaleInfoBeans = super.findList(
				"backCharge.tempCharge.chargeMemberSaleInfo.findMemberSaleInfoTypeListByNow");

		// 获得临时表中车辆数据
		List<TempCostBean> tempCosts = findTempCostList();

		// 判断标志
		boolean flag = true;
		// 迭代停车场车辆
		for (TempCostBean tempCost : tempCosts) {

			Date nowDate = new Date();
			// 判断是否支付了
			if (tempCost.getPayStatus() != null && tempCost.getPayStatus() != 0) {
				// 付完预支付停车费后
				if (tempCost.getPayTime() != null && (nowDate.getTime() - tempCost.getPayTime().getTime()) <= 900000) {
					continue;
				}
			}
			;

			flag = true;
			// 理想金额为0
			tempCost.setIncomeBill(0);
			// 实际金额为0
			tempCost.setRealBill(0);

			// 比对当前时间是否在23：30至6：00
			/*
			 * String nowDateStr = sdfRule.format(nowDate); Date nowTime =
			 * sdfRule.parse(nowDateStr); if (nowTime.getTime() >
			 * elevenHalfTime.getTime() && nowTime.getTime() <=
			 * twelTime.getTime()) { update(tempCost); continue; } if
			 * (nowTime.getTime() >= zeroTime.getTime() && nowTime.getTime() <
			 * sixTime.getTime()) { update(tempCost); continue; }
			 */

			// 更新图片
			// updateImage(tempCost);

			String mvLicense = tempCost.getMvLicense();
			// 校验白名单
			for (WhiteListBean whiteListBean : whiteListBeans) {
				if (mvLicense.equals(whiteListBean.getMvLicense())) {
					flag = false;
					break;
				}
			}

			// 判断是否为白名单，如果是白名单跳过会员售卖的迭代判断
			if (flag) {
				for (MemberSaleInfoBean memberSaleInfoBean : memberSaleInfoBeans) {
					// 判断会员
					if (mvLicense.equals(memberSaleInfoBean.getMvLicense())) {
						flag = false;
						break;
					}
				}

				Date now = new Date();
				// 判断计费规则不为空
				int cost = resultCostSD(tempCost, now);
				tempCost.setIncomeBill(cost);

				if (flag) {
					tempCost.setRealBill(cost);
				} else {
					tempCost.setRealBill(0);
				}
			}
			update(tempCost);
		}
	}

	/*
	 * // 返回计费结果（苏州大学） private int resultCostSD(TempCostBean tempCost, Date now)
	 * {
	 * 
	 * // 进入停车场时间毫秒数 long entryTime = tempCost.getEntryTime().getTime();
	 * 
	 * // 当前时间毫秒数 long nowTime = now.getTime();
	 * 
	 * long entryToNow = nowTime - entryTime;
	 * 
	 * // 30分钟内免费 // if(entryToNow <= 1800000) // { // return 0; // }
	 * 
	 * // 超过1个小时，收费6元 if (entryToNow > 3600000 && entryToNow <= 7200000) {
	 * return 600; }
	 * 
	 * // 超过1个小时的 if (entryToNow > 7200000) { BigDecimal entryToNowDB = new
	 * BigDecimal(entryToNow); BigDecimal twohour = new BigDecimal(7200000);
	 * BigDecimal multipleCost = new BigDecimal(0); BigDecimal oneday = new
	 * BigDecimal(86400000); BigDecimal addInit = new BigDecimal(600);
	 * BigDecimal limt = new BigDecimal(2000);
	 * 
	 * //半小时 BigDecimal halfHour = new BigDecimal(1800000);
	 * 
	 * // 取余=时间跨度%一天时间 BigDecimal remainder = entryToNowDB; //
	 * 小于一天的时间-一小时=多余一小时的毫秒数 BigDecimal period = remainder.subtract(twohour); if
	 * (entryToNow >= 86400000) { BigDecimal oneDayMultiple =
	 * entryToNowDB.divide(oneday, 0, RoundingMode.DOWN); multipleCost =
	 * oneDayMultiple.multiply(limt); addInit = new BigDecimal(0); //
	 * 取余=时间跨度%一天时间 remainder = (entryToNowDB.divideAndRemainder(oneday)[1]);
	 * period = remainder; // entryToNowDB.divide(bigdecimal) } //
	 * 多余半小时的毫秒数/半小时的毫秒数=半小时的倍数（向上取整） BigDecimal currentTime =
	 * period.divide(halfHour, 0, RoundingMode.UP); //
	 * 多余一小时的毫秒倍数*100（分）+200(分)=最终结果（分） BigDecimal costDB =
	 * currentTime.multiply(new BigDecimal(200)).add(addInit); if
	 * (costDB.compareTo(limt) >= 0) { costDB = limt; } return
	 * costDB.add(multipleCost).intValue(); } return 0; }
	 */

	// 返回计费结果（苏州大学）
	private int resultCostSD(TempCostBean tempCost, Date now) {

		// 进入停车场时间毫秒数
		long entryTime = tempCost.getEntryTime().getTime();

		// 当前时间毫秒数
		long nowTime = now.getTime();

		long entryToNow = nowTime - entryTime;

		// if(tempCost.getMvLicense().equals("苏A7Z6E5"))
		// {
		// System.out.println("苏A7Z6E5");
		// }

		// 1小时内免费
		// if(entryToNow <= 3600000)
		// {
		// return 0;
		// }

		// 超过1个小时致2小时内收费6元
		if (entryToNow > 3600000 && entryToNow <= 7200000) {
			return 600;
		}

		// if(tempCost.getMvLicense().equals("苏A12345"))
		// {
		// System.out.println("苏A12345");
		// }

		// 超过2个小时的
		if (entryToNow > 7200000) {

			BigDecimal entryToNowDB = new BigDecimal(entryToNow);
			BigDecimal twohour = new BigDecimal(7200000);
			BigDecimal multipleCost = new BigDecimal(0);
			BigDecimal oneday = new BigDecimal(86400000);
			BigDecimal addInit = new BigDecimal(600);
			BigDecimal limit = new BigDecimal(2000);

			long cyclyStart = entryTime + 3600000;
			Date cyclyStartDate = new Date(cyclyStart);
			String cyclyStartStr = DateUtil.get4yMd(cyclyStartDate);
			String cyclyEndStr = DateUtil.get4yMd(now);

			// 半小时
			BigDecimal halfHour = new BigDecimal(1800000);

			// 在一天之内
			if (cyclyStartStr.equals(cyclyEndStr)) {
				// 取余=时间跨度%一天时间
				BigDecimal remainder = entryToNowDB;
				// 小于一天的时间-一小时=多余一小时的毫秒数
				BigDecimal period = remainder.subtract(twohour);

				// 多余半小时的毫秒数/半小时的毫秒数=半小时的倍数（向上取整）
				BigDecimal currentTime = period.divide(halfHour, 0, RoundingMode.UP);
				// 多余一小时的毫秒倍数*100（分）+200(分)=最终结果（分）
				BigDecimal costDB = currentTime.multiply(new BigDecimal(200)).add(addInit);
				if (costDB.compareTo(limit) >= 0) {
					costDB = limit;
				}
				return costDB.add(multipleCost).intValue();
			}
			// 一天外
			else {
				try {
					String cyclyStartHmsStr = sdfRule.format(cyclyStartDate);
					Date cyclyStartHms = sdfRule.parse(cyclyStartHmsStr);
					long beforeCycly = twelTime.getTime() - cyclyStartHms.getTime();

					BigDecimal costDB = new BigDecimal(0);
					// // 取余=时间跨度%一天时间
					BigDecimal remainder = new BigDecimal(beforeCycly);

					BigDecimal freeTime = new BigDecimal(0);
					// 判断等于小于一小时
					if (beforeCycly <= 3600000) {
						costDB = costDB.add(addInit);
						freeTime.add(new BigDecimal(3600000 - beforeCycly));
					} else {
						BigDecimal period = remainder.subtract(new BigDecimal(3600000));
						BigDecimal currentTime = period.divide(halfHour, 0, RoundingMode.UP);
						BigDecimal currentCostDB = currentTime.multiply(new BigDecimal(200)).add(addInit);

						if (currentCostDB.compareTo(limit) >= 0) {
							currentCostDB = limit;
						}
						costDB = costDB.add(currentCostDB);
					}

					long crossStartTime = cyclyStartDate.getTime() + beforeCycly;
					Date crossStartTimeDate = new Date(crossStartTime);

					long crossTime = now.getTime() - crossStartTimeDate.getTime();
					BigDecimal crossTimeBD = new BigDecimal(crossTime);
					BigDecimal dayPeriod = new BigDecimal(0);
					;
					if (!DateUtil.get4yMd(crossStartTimeDate).equals(cyclyEndStr)) {
						dayPeriod = crossTimeBD.divide(oneday, 0, RoundingMode.DOWN);
						BigDecimal dayCost = dayPeriod.multiply(limit);
						costDB = costDB.add(dayCost);
					}

					// 剩余时间
					BigDecimal surplusTime = crossTimeBD.divideAndRemainder(oneday)[1];
					// 如果没有跨天
					if (dayPeriod.compareTo(new BigDecimal(0)) == 0) {
						surplusTime = surplusTime.subtract(freeTime);
					}

					BigDecimal currentSurplusTime = surplusTime.divide(halfHour, 0, RoundingMode.UP);

					BigDecimal currentCost = currentSurplusTime.multiply(new BigDecimal(200));

					if (currentCost.compareTo(limit) >= 0) {
						currentCost = limit;
					}
					costDB = costDB.add(currentCost);
					return costDB.intValue();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	// 返回计费结果（涟水县）
	private int resultCost(TempCostBean tempCost, Date now) {

		try {
			String nowDate = sdfRule.format(now);
			Date costDate = sdfRule.parse(nowDate);
			if (costDate.getTime() > elevenHalfTime.getTime() && costDate.getTime() < twelTime.getTime()) {
				return 0;
			}
			if (costDate.getTime() > zeroTime.getTime() && costDate.getTime() < sixTime.getTime()) {
				return 0;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}

		// 进入停车场时间毫秒数
		long entryTime = tempCost.getEntryTime().getTime();

		// 当前时间毫秒数
		long nowTime = now.getTime();

		long entryToNow = nowTime - entryTime;

		// 30分钟内免费
		// if(entryToNow <= 1800000)
		// {
		// return 0;
		// }

		// 超过30分钟，小于1个小时，收费2元
		if (entryToNow > 1800000 && entryToNow <= 3600000) {
			return 200;
		}

		// 超过1个小时的
		if (entryToNow > 3600000) {
			BigDecimal entryToNowDB = new BigDecimal(entryToNow);
			BigDecimal onehour = new BigDecimal(3600000);
			BigDecimal multipleCost = new BigDecimal(0);
			BigDecimal oneday = new BigDecimal(86400000);
			BigDecimal addInit = new BigDecimal(200);
			BigDecimal limt = new BigDecimal(500);

			// 取余=时间跨度%一天时间
			BigDecimal remainder = entryToNowDB;
			// 小于一天的时间-一小时=多余一小时的毫秒数
			BigDecimal period = remainder.subtract(onehour);
			if (entryToNow >= 86400000) {
				BigDecimal oneDayMultiple = entryToNowDB.divide(oneday, 0, RoundingMode.DOWN);
				multipleCost = oneDayMultiple.multiply(limt);
				addInit = new BigDecimal(0);
				// 取余=时间跨度%一天时间
				remainder = (entryToNowDB.divideAndRemainder(oneday)[1]);
				period = remainder;
				// entryToNowDB.divide(bigdecimal)
			}
			// 多余一小时的毫秒数/一小时的毫秒数=一小时的倍数（向上取整）
			BigDecimal currentTime = period.divide(onehour, 0, RoundingMode.UP);
			// 多余一小时的毫秒倍数*100（分）+200(分)=最终结果（分）
			BigDecimal costDB = currentTime.multiply(new BigDecimal(100)).add(addInit);
			if (costDB.compareTo(limt) >= 0) {
				costDB = limt;
			}
			return costDB.add(multipleCost).intValue();
		}
		return 0;
	}

	/**
	 * 
	 * @Title: checkVaildTime @Description: 判断是否在有效计费规则时间内 @param @return
	 *         设定文件 @return boolean 返回类型 @throws
	 */
	// private boolean checkVaildTime(ChargeRuleCostBean chargeRuleBean,long
	// currentTime)
	// {
	// if(chargeRuleBean.getBeginTime())
	//
	//
	// return false;
	// }

	/**
	 * 
	 * @Title : isInRule 判断是否在计费时间段以内
	 * @功能描述: TODO
	 * @传入参数：@param chargeRuleBean
	 * @传入参数：@return
	 * @传入参数：@throws ParseException
	 * @返回类型：boolean
	 * @throws ：
	 */
	private boolean isInRule(Date start, Date end, Date costDate, SimpleDateFormat sdf) throws ParseException {

		// 如果横跨了24点
		if (start.getTime() >= end.getTime()) {
			if ((costDate.getTime() >= start.getTime() && costDate.getTime() < sdf.parse("24:00").getTime())
					|| (costDate.getTime() < end.getTime() && costDate.getTime() >= sdf.parse("00:00").getTime())) {
				return true;
			}
		} else {
			if ((costDate.getTime() >= start.getTime() && costDate.getTime() < end.getTime())) {
				return true;
			}
		}
		return false;
	}

	// 计算理想或者实际金额
	private BigDecimal CalculationBill(long timeLong, ChargeRuleCostBean bean) {
		// long timeLong = new Date().getTime() -
		// tempCost.getEntryTime().getTime();

		BigDecimal timeLongBD = new BigDecimal(timeLong);
		BigDecimal oneMinute = new BigDecimal(1000 * 60);
		BigDecimal timeMinute = timeLongBD.divide(oneMinute, 0, RoundingMode.UP);
		// BigDecimal freeMinuteDB = new BigDecimal(freeMinute);
		// 收费时间=总时长-免费时长
		BigDecimal payTime = timeMinute.subtract(freeMinute);
		if (payTime.compareTo(new BigDecimal(0)) <= 0) {
			// 赋值免费时长
			freeMinute = freeMinute.subtract(timeMinute);
			return new BigDecimal(0);
		} else {
			freeMinute = new BigDecimal(0);
		}

		// 时长/计费时长*每计费时长金额
		BigDecimal cost = payTime.divide(new BigDecimal(bean.getPeriod()), 0, RoundingMode.UP)
				.multiply(new BigDecimal(bean.getMoney()));

		// 判断费用是否大于最高上限，如果大于最高上限按照最高上限金额计算
		if (cost.compareTo(new BigDecimal(bean.getMoneyLimit())) >= 0) {
			cost = new BigDecimal(bean.getMoneyLimit());
		}

		return cost;
	}

	/**
	 * 
	 * @Title : checkEntryTime
	 * @功能描述: TODO @传入参数：
	 * @返回类型：void
	 * @throws ：
	 */
	private boolean checkEntryTime() {
		return isExistRebate;
	}

	/**
	 * 
	 * @Title : findTempCostList
	 * @功能描述: TODO
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	private List<TempCostBean> findTempCostList() {
		// TODO Auto-generated method stub
		return super.findList(ns("findTempCostList"));
	}

	/**
	 * 
	 * @Title : findChargeRulesByNow 查询是否节假日免费
	 * @功能描述: TODO
	 * @传入参数：@param chargeType
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	private ChargeRuleCostBean findChargeRulesByNow(int chargeType) {
		// TODO Auto-generated method stub
		int charge_type = chargeType;
		return super.findObj("backCharge.tempCharge.chargeRules.findChargeRulesByNow", charge_type);
	}

	/**
	 * 
	 * @Title : update 修改理想金额和实际金额
	 * @功能描述: TODO
	 * @传入参数：@param bean @返回值：
	 * @throws ：
	 */
	private void update(TempCostBean bean) {
		// TODO Auto-generated method stub
		super.update(ns("updateTempCostBill"), bean);

	}

	/*
	 * 更新图片信息
	 */
	private void updateImage(TempCostBean tempCostBean) {
		// tempCostBean.get
		// 判断入口图片是否有值
		if (tempCostBean.getEntryImage() != null && tempCostBean.getEntryImage().trim() != "") {
			return;
		}

		// 判断是否大于5分钟，如果停车时长大于5分钟则跳过赋值图片信息
		Date entryDate = tempCostBean.getEntryTime();
		Date now = new Date();
		if (((now.getTime()) - entryDate.getTime()) >= 300000) {
			return;
		}

		// 入口车道
		int entryLane = tempCostBean.getEntryLane();
		// 车牌号
		String mvLicense = tempCostBean.getMvLicense();

		ObjectMap map = ObjectMap.newInstance();
		map.put("entrylane", entryLane);
		map.put("entrytime", entryDate);
		map.put("mvlicense", mvLicense);

		List<ChargeEntryBean> chargeEntryBeans = findList("backCharge.tempCharge.chargeEntry.findEntryListImage", map);
		ChargeEntryBean chargeEntryBean = chargeEntryBeans.get(chargeEntryBeans.size() - 1);
		// ChargeEntryBean chargeEntryBean =
		// findObj("backCharge.tempCharge.chargeEntry.findEntryImage", map);

		if (chargeEntryBean == null || chargeEntryBean.getImagepath() == null
				|| chargeEntryBean.getImagepath().trim() == "") {
			map = null;
			return;
		}
		tempCostBean.setEntryImage(chargeEntryBean.getImagepath());
		super.update(ns("updateEntryImage"), tempCostBean);
		map = null;
	}

}
