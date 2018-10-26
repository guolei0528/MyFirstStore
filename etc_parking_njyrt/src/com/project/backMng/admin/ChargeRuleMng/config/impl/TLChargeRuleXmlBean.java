package com.project.backMng.admin.ChargeRuleMng.config.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleConstant;
import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleXml;
import com.project.tools.DateUtil;


public class TLChargeRuleXmlBean implements ChargeRuleXml {
	// 基础配置信息
	private TLChargeRuleSettingBean tlSetting;

	// 节点配置信息
	private List<TLChargeRuleNodeBean> tlNode;

	
	public TLChargeRuleSettingBean getTlSetting() {
		return tlSetting;
	}


	public void setTlSetting(TLChargeRuleSettingBean tlSetting) {
		this.tlSetting = tlSetting;
	}


	public List<TLChargeRuleNodeBean> getTlNode() {
		return tlNode;
	}


	public void setTlNode(List<TLChargeRuleNodeBean> tlNode) {
		this.tlNode = tlNode;
	}


	/**
	 * 
	 * @Title : calculateToll
	 * @功能描述: 根据时间长度计算车辆费用
	 * @传入参数：@param startTime
	 * @传入参数：@param endTime
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public long calculateToll(Date startTime, Date endTime, int carType) {
		// 获得基本信息
		TLChargeRuleSettingBean setting = this.getTlSetting();
		// 获得计费时间长度节点
		List<TLChargeRuleNodeBean> nodes = this.getTlNode();
		// 接受时间-开始时间=总时长
		long totalTime = endTime.getTime() - startTime.getTime();

		// 判断是否小于免费时间,小于免费时间金额为0，分*60*1000=毫秒
		if (totalTime < setting.getFreeTime() * ChargeRuleConstant.SIXTY * ChargeRuleConstant.ONE_THOUSAND) {
			return 0;
		}

		// 判断是否是涟水模式的具有夜间免费时间，在免费时段内时金额为0
		if (setting.getUseFreeTime() == 1) {
			// 免费开始时间
			Date freeStartTime = DateUtil.parse4Hms(setting.getFreeStartTime());
			// 免费结束时间
			Date freeEndTime = DateUtil.parse4Hms(setting.getFreeEndTime());
			// 结束时间转换成1970年的Date时间点
			String endTimeStr = DateUtil.get4Hms(endTime);
			Date endTimeHms = DateUtil.parse4Hms(endTimeStr);
			// 免费时间段中免费停车
			if (freeEndTime.getTime() > freeStartTime.getTime() && freeStartTime.getTime() <= endTimeHms.getTime()
					&& endTimeHms.getTime() <= freeEndTime.getTime()) {
				return 0;
			}

			// 判断结束时间小于开始时间，比对是否在免费时间段内
			if (freeEndTime.getTime() <= freeStartTime.getTime() && freeStartTime.getTime() <= endTimeHms.getTime()
					&& endTimeHms.getTime() <= ChargeRuleConstant.TWENTY_FOUR_CLOCK.getTime()) {
				return 0;
			}

			// 判断结束时间小于开始时间，比对是否在免费时间段内
			if (freeEndTime.getTime() <= freeStartTime.getTime()
					&& ChargeRuleConstant.ZERO_CLOCK.getTime() <= endTimeHms.getTime()
					&& endTimeHms.getTime() <= freeEndTime.getTime()) {
				return 0;
			}
		}

		// 序列id
//		int indexId = 0;

		// 标记是否超过一个周期节点的计费方式
//		boolean indexFlag = true;

		// 最大周期
		int maxCycle = setting.getMaxCycle();

		// 总时长减去免费时间
//		long excludeFreeTime = totalTime
//				- setting.getFreeTime() * ChargeRuleConstant.SIXTY * ChargeRuleConstant.ONE_THOUSAND;
		// 获得BigDecimal的非免费时间毫秒数和最大周期毫秒数
		BigDecimal excludeFreeTimeBD = new BigDecimal(totalTime);
		
		// 获得最大周期时长，如果没有0点重置为设置MaxCycle时长，如果有0点重置为当天剩余时长
		BigDecimal maxCycleBD = new BigDecimal(ChargeRuleConstant.ZERO);
		// 判断是否需要0点重置
		// 需要0点重置
		if(ChargeRuleConstant.RESET == setting.getReset())
		{
			// 获取当天0点的时间
			String ymdStartTime = DateUtil.get4yMd(startTime);
			Date StartDate = DateUtil.parse4yMd(ymdStartTime);
			// 当天的剩余时长=一天时长-(开始时间-当天0点)
			maxCycleBD = maxCycleBD.add(new BigDecimal(ChargeRuleConstant.ONE_DAY - (startTime.getTime()-StartDate.getTime())));
		}
		// 不需要0点重置
		else
		{
			// 获得BigDecimal的非免费时间毫秒数和最大周期毫秒数
			maxCycleBD = new BigDecimal(maxCycle).multiply(new BigDecimal(ChargeRuleConstant.SIXTY)).multiply(new BigDecimal(ChargeRuleConstant.ONE_THOUSAND));
		}
		
		// 最大周期金额
//		BigDecimal maxTollBD = new BigDecimal(setting.getMaxToll());

		// 减去免费时间长度/最大周期=最大周期次数,向下取整
//		BigDecimal maxCycleTimeBD = excludeFreeTimeBD.divide(maxCycleBD, 0, RoundingMode.DOWN);
		// 减去免费时间长度%最大周期=最大周期次数的余数
//		BigDecimal excludeFreeRemainder = excludeFreeTimeBD.divideAndRemainder(maxCycleBD)[1];

		// 收费金额
		BigDecimal totalToll = new BigDecimal(ChargeRuleConstant.ZERO);
		
		// 最大周期金额总计
		BigDecimal nodeCycleToll = new BigDecimal(ChargeRuleConstant.ZERO);
		// 最大周期时间总计
		BigDecimal nodeCycleTime = new BigDecimal(ChargeRuleConstant.ZERO);
		
		// 增加节点周期时间的免费时间
		nodeCycleTime = nodeCycleTime.add(new BigDecimal(setting.getFreeTime() * ChargeRuleConstant.SIXTY * ChargeRuleConstant.ONE_THOUSAND));
		// 序列id
		int indexId = 1;

		// 标记是否超过一个周期节点的计费方式
		boolean indexFlag = true;
		
		// 最大上限金额
		BigDecimal maxTollDB = new BigDecimal(ChargeRuleConstant.ZERO); 
		
		// 判断最大上限金额(小车)
		if (carType == ChargeRuleConstant.SMALL_CAR) {
			maxTollDB= new BigDecimal(setting.getSmallMaxToll());
		}

		// 判断最大上限金额(大车) 
		if (carType == ChargeRuleConstant.LARGE_CAR || carType == ChargeRuleConstant.LORRY) {
			maxTollDB= new BigDecimal(setting.getLargeMaxToll());
		}
		
		if (carType != ChargeRuleConstant.SMALL_CAR && 
				carType != ChargeRuleConstant.LARGE_CAR && carType != ChargeRuleConstant.LORRY) {
			maxTollDB= new BigDecimal(setting.getSmallMaxToll());
		}
		
		// 迭代费用节点
		for (int i = 0; i < nodes.size(); i++) {
			// 获得节点对象
			TLChargeRuleNodeBean node = nodes.get(i);

			// 判断游标是否与id一致
			if (indexId == node.getId()) {
				// 获得节点时长的极限毫秒数
				BigDecimal nodeStartLongMillis = new BigDecimal(node.getStartLong())
						.multiply(new BigDecimal(ChargeRuleConstant.ONE_THOUSAND))
						.multiply(new BigDecimal(ChargeRuleConstant.SIXTY));
				// 节点计费周期的毫秒数
				BigDecimal nodePeriodMillis = new BigDecimal(node.getPeriod())
						.multiply(new BigDecimal(ChargeRuleConstant.ONE_THOUSAND))
						.multiply(new BigDecimal(ChargeRuleConstant.SIXTY));
				// 节点金额
				BigDecimal nodeSmallToll = new BigDecimal(node.getSmallToll());
				BigDecimal nodeLargeToll = new BigDecimal(node.getLargeToll());
				BigDecimal nodeLimitToll = new BigDecimal(node.getLimitToll());
				BigDecimal nodeToll = new BigDecimal(ChargeRuleConstant.ZERO);
				
				BigDecimal remainCycleTime = new BigDecimal(ChargeRuleConstant.ZERO); 
				//判断最大金额是否有效
				if(maxCycleBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
						&& maxTollDB.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0)
				{
					// 判断最大周期是否大于剩余周期时间
					if(maxCycleBD.compareTo(nodeCycleTime) > 0)
					{
						//周期剩余时间
						remainCycleTime = maxCycleBD.subtract(nodeCycleTime);
					}
					else
					{
						nodeCycleTime = maxCycleBD;
						remainCycleTime = new BigDecimal(ChargeRuleConstant.ZERO);
					}
					
				}
				
				
				// 计算节点金额
				// 小车类型
				if (carType == ChargeRuleConstant.SMALL_CAR) {
					// 剩余时长判断是否小于节点长度
					if (excludeFreeTimeBD.compareTo(nodeStartLongMillis) <= 0) {
						
						// 判断是否有效,并且小于剩余时长
						if((maxCycleBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
								&& maxTollDB.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0)
								&& remainCycleTime.compareTo(excludeFreeTimeBD) < 0)
						{
							// 通信费=(节点的剩余时间/节点累计时长)*小车停车费
							nodeToll = remainCycleTime.divide(nodePeriodMillis, 0, RoundingMode.UP)
							.multiply(nodeSmallToll);
							
							nodeCycleTime = nodeCycleTime.add(remainCycleTime);
							
						}
						else
						{
							// 通信费=(剩余时间/节点累计时长)*小车停车费
							nodeToll = excludeFreeTimeBD.divide(nodePeriodMillis, 0, RoundingMode.UP)
									.multiply(nodeSmallToll);

							// 大周期计费时长+剩余时间
							nodeCycleTime = nodeCycleTime.add(excludeFreeTimeBD);
							// 剩余时间=0
							// excludeFreeTimeBD =
							// excludeFreeTimeBD.subtract(excludeFreeTimeBD);
						}
					} else {
						// 判断是否有效,并且小于剩余时长
						if((maxCycleBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
								&& maxTollDB.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0)
								&& remainCycleTime.compareTo(nodeStartLongMillis) < 0)
						{
							// 通信费=(节点的剩余时间/节点累计时长)*小车停车费
							nodeToll = remainCycleTime.divide(nodePeriodMillis, 0, RoundingMode.UP)
							.multiply(nodeSmallToll);
							
							nodeCycleTime = nodeCycleTime.add(remainCycleTime);
						}
						else
						{
						// 通信费=(节点计费周期的毫秒数/节点累计时长)*小车停车费
						nodeToll = nodeStartLongMillis.divide(nodePeriodMillis, 0, RoundingMode.UP)
								.multiply(nodeSmallToll);
						
						//大周期计费时长+节点最大时间
						nodeCycleTime = nodeCycleTime.add(nodeStartLongMillis);
						// 剩余时间=剩余时间-节点最大时间
//						excludeFreeTimeBD = excludeFreeTimeBD.subtract(nodeStartLongMillis);
						}
					}
				}

				// 大车类型
				if (carType == ChargeRuleConstant.LARGE_CAR || carType == ChargeRuleConstant.LORRY) {
					// 判断是否小于节点极限长度
					if (excludeFreeTimeBD.compareTo(nodeStartLongMillis) <= 0) {
						
						// 判断是否有效,并且小于剩余时长
						if((maxCycleBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
								&& maxTollDB.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0)
								&& remainCycleTime.compareTo(excludeFreeTimeBD) < 0)
						{
							// 通信费=(节点的剩余时间/节点累计时长)*小车停车费
							nodeToll = remainCycleTime.divide(nodePeriodMillis, 0, RoundingMode.UP)
							.multiply(nodeLargeToll);
							
							nodeCycleTime = nodeCycleTime.add(remainCycleTime);
							
						}
						else
						{
						// 通信费=(剩余时间/节点累计时长)*小车停车费
						nodeToll = excludeFreeTimeBD.divide(nodePeriodMillis, 0, RoundingMode.UP)
								.multiply(nodeLargeToll);
						
						//大周期计费时长+剩余时间 
						nodeCycleTime = nodeCycleTime.add(excludeFreeTimeBD);
						
						// 剩余时间=0
//						excludeFreeTimeBD = excludeFreeTimeBD.subtract(excludeFreeTimeBD);
						
						}
					} else {
						
						// 判断是否有效,并且小于剩余时长
						if((maxCycleBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
								&& maxTollDB.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0)
								&& remainCycleTime.compareTo(nodeStartLongMillis) < 0)
						{
							// 通信费=(节点的剩余时间/节点累计时长)*大车停车费
							nodeToll = remainCycleTime.divide(nodePeriodMillis, 0, RoundingMode.UP)
							.multiply(nodeLargeToll);
							
							nodeCycleTime = nodeCycleTime.add(remainCycleTime);
						}
						else
						{
						// 通信费=(节点计费周期的毫秒数/节点累计时长)*小车停车费
						nodeToll = nodeStartLongMillis.divide(nodePeriodMillis, 0, RoundingMode.UP)
								.multiply(nodeLargeToll);
						
						//大周期计费时长+节点最大时间
						nodeCycleTime = nodeCycleTime.add(nodeStartLongMillis);
						
						// 剩余时间=剩余时间-节点最大时间
//						excludeFreeTimeBD = excludeFreeTimeBD.subtract(nodeStartLongMillis);
						}
					}
				}

				// 判断是否大于节点最大金额
				if (nodeLimitToll.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
						&& nodeToll.compareTo(nodeLimitToll) >= 0) {
					// 通信费加上节点极限金额
					nodeCycleToll = nodeCycleToll.add(nodeLimitToll);
				} else {
					// 通信费加上节点计算的金额
					nodeCycleToll = nodeCycleToll.add(nodeToll);
				}
				
				// 判断计算的最大周期时长是否超过了最大周期时长,或者计算的最大
				if((maxCycleBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0 
						&& nodeCycleTime.compareTo(maxCycleBD) >= 0)
						|| nodeCycleTime.compareTo(excludeFreeTimeBD) >= 0)
				{
					
					if(maxTollDB.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
							&& nodeCycleToll.compareTo(maxTollDB) >= 0)
					{
						totalToll = totalToll.add(maxTollDB);
					}
					
					if(maxTollDB.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
							&& nodeCycleToll.compareTo(maxTollDB) < 0)
					{
						totalToll = totalToll.add(nodeCycleToll);
					}
					
					//剩余时长=剩余时长-节点周期时长
					excludeFreeTimeBD = excludeFreeTimeBD.subtract(nodeCycleTime);
					
					// 将节点周期金额总计置为0
					nodeCycleToll = nodeCycleToll.subtract(nodeCycleToll);
					
					// 将节点周期时长设置为0 
					nodeCycleTime = nodeCycleTime.subtract(nodeCycleTime);
					
					// 需要0点重置
					if(ChargeRuleConstant.RESET == setting.getReset())
					{
//						long currentTime = endTime.getTime() - excludeFreeTimeBD.longValue();
						// 获取当天0点的时间
//						String ymdCurrent = DateUtil.get4yMd(new Date(currentTime));
//						Date ymdCurrentDate = DateUtil.parse4yMd(ymdCurrent);
						// 最大周期归零
						maxCycleBD = maxCycleBD.subtract(maxCycleBD);
						// 当天的剩余时长=一天时长-(开始时间-当天0点)
//						maxCycleBD = maxCycleBD.add(new BigDecimal(ChargeRuleConstant.ONE_DAY - (currentTime-ymdCurrentDate.getTime())));
						// 周期为1天
						maxCycleBD = maxCycleBD.add(new BigDecimal(ChargeRuleConstant.ONE_DAY));
						// 重启后进行累增
						indexFlag = true;
						// 从1重新开始计费，计0
						indexId = 0;
					}
					
				}
				
				
				//判断最大金额和最大周期时间无效
				if(maxCycleBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) <= 0
						|| maxTollDB.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) <= 0)
				{
					totalToll = totalToll.add(nodeCycleToll);
					
					//剩余时长=剩余时长-节点周期时长
					excludeFreeTimeBD = excludeFreeTimeBD.subtract(nodeCycleTime);
					
					// 将节点周期金额总计置为0
					nodeCycleToll = nodeCycleToll.subtract(nodeCycleToll);
					
					// 将节点周期时长设置为0
					nodeCycleTime = nodeCycleTime.subtract(nodeCycleTime);
					
					// 需要0点重置
					if(ChargeRuleConstant.RESET == setting.getReset())
					{
//						long currentTime = endTime.getTime() - excludeFreeTimeBD.longValue();
						// 获取当天0点的时间
//						String ymdCurrent = DateUtil.get4yMd(new Date(currentTime));
//						Date ymdCurrentDate = DateUtil.parse4yMd(ymdCurrent);
						// 最大周期归零
						maxCycleBD = maxCycleBD.subtract(maxCycleBD);
						// 当天的剩余时长=一天时长-(开始时间-当天0点)
//						maxCycleBD = maxCycleBD.add(new BigDecimal(ChargeRuleConstant.ONE_DAY - (currentTime-ymdCurrentDate.getTime())));
						// 周期为1天
						maxCycleBD = maxCycleBD.add(new BigDecimal(ChargeRuleConstant.ONE_DAY));
					}
				}
				
				

				// 判断剩余时间是否小于等于0，小于等于0时退出循环
				if (excludeFreeTimeBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) <= 0) {
					
				/*	if(maxTollDB.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
							&& nodeCycleToll.compareTo(maxTollDB) >= 0)
					{
						totalToll = totalToll.add(maxTollDB);
					}
					
					if(maxTollDB.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
							&& nodeCycleToll.compareTo(maxTollDB) < 0)
					{
						totalToll = totalToll.add(nodeCycleToll);
					}*/
					
					return totalToll.longValue();
				}

				
				
				// 判断是否需要累加
				if (indexFlag) {
					// 增加游标
					indexId++;
				}

				// 判断超过节点后的计费方式1.重复 2.累计
				if (indexId > setting.getNum() && setting.getBeyoneChargeFlag() == 1)
				 {
					if(nodeCycleTime.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) == 0)
					{
						indexId = 1;
						indexFlag = true;
					}
					else
					{
						indexId = setting.getNum();
					}
				}

				// 累计最后一次
				if ((indexId > setting.getNum() || indexFlag == false) && setting.getBeyoneChargeFlag() == 2) {
					indexId = setting.getNum();
					indexFlag = false;
				}
			}

			// 判断重新循环计费节点
			if (i == nodes.size() - 1) {
				i = -1;
			}
		}
	 return totalToll.longValue();
		
		
//		return 0;

		// 最大周期次数小于0（没有跨最大周期）
//		if (maxCycleTimeBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) <= 0) {
//
//			//获得单次周期内的计算金额
//			BigDecimal singToll = new BigDecimal(singleCycle(setting,nodes,carType,excludeFreeRemainder));
//			
//			//判断单次返回的计算金额是否大于单次周期的极限金额
//			if(maxTollBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0 
//					&& singToll.compareTo(maxTollBD) >= 0)
//			{
//				return maxTollBD.longValue();
//			}
//			
//			//返回单次周期内的金额计算
//			return singleCycle(setting,nodes,carType,excludeFreeRemainder);
//			
//		}
/*
			// 最大周期金额，次数*最大金额
			BigDecimal maxCycleToll = maxCycleTimeBD.multiply(maxTollBD);
			
			//判断maxToll是否有效
			if(maxTollBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) >0 )
			{
				// 增加最大周期金额
				totalToll = totalToll.add(maxCycleToll);
				
				// 更新剩余时长，剩余时长=剩余时长-大周期次数*大周期时时长
				excludeFreeRemainder = excludeFreeTimeBD.subtract(maxCycleTimeBD.multiply(maxCycleBD));
			}
			else
			{
				excludeFreeRemainder = excludeFreeTimeBD;
			}
			
			// 判断超过节点后的计费方式1.重复 2.累计
			if (setting.getBeyoneChargeFlag() == 1  || (setting.getBeyoneChargeFlag() == 2 && maxTollBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) <= 0)) {
				
				//返回单次周期内的金额计算
				totalToll = totalToll.add(new BigDecimal(singleCycle(setting,nodes,carType,excludeFreeRemainder)));
			}

			// 累计
			if (setting.getBeyoneChargeFlag() == 2 && maxTollBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0) {
				
				BigDecimal nodeTolls = new BigDecimal(ChargeRuleConstant.ZERO);
				//迭代所有节点
				for(TLChargeRuleNodeBean node:nodes)
				{
					//判断如果是最后一个节点
					if(node.getId() == setting.getNum())
					{
						// 获得节点时长的极限毫秒数
						BigDecimal nodeStartLongMillis = new BigDecimal(node.getStartLong())
								.multiply(new BigDecimal(ChargeRuleConstant.ONE_THOUSAND))
								.multiply(new BigDecimal(ChargeRuleConstant.SIXTY));
						// 节点计费周期的毫秒数
						BigDecimal nodePeriodMillis = new BigDecimal(node.getPeriod())
								.multiply(new BigDecimal(ChargeRuleConstant.ONE_THOUSAND))
								.multiply(new BigDecimal(ChargeRuleConstant.SIXTY));
						// 节点金额
						BigDecimal nodeSmallToll = new BigDecimal(node.getSmallToll());
						BigDecimal nodeLargeToll = new BigDecimal(node.getLargeToll());
						BigDecimal nodeLimitToll = new BigDecimal(node.getLimitToll());
						BigDecimal nodeToll = new BigDecimal(ChargeRuleConstant.ZERO);
						
						
						// 小车类型
						if (carType == 1) {
							
							// 节点最大金额是否小于等于0，小于等于0时不根据最大金额计算
							if(nodeLimitToll.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) <= 0)
							{
								//剩余金额/节点周期时长*最大节点周期金额 =增加的周期累计金额,向上取整
								nodeToll = excludeFreeRemainder.divide(nodePeriodMillis,0,RoundingMode.UP).multiply(nodeSmallToll);
							}
							else
							{
								//剩余金额/最大节点周期长度*最大节点周期金额 =增加的周期累计金额,向下取整
								nodeToll = excludeFreeRemainder.divide(nodeStartLongMillis,0,RoundingMode.DOWN).multiply(nodeLimitToll);
								//节点余数
								BigDecimal nodeRemainder = excludeFreeRemainder.divideAndRemainder(nodeStartLongMillis)[1];
								//节点余数金额
								BigDecimal nodeRemainderToll = nodeRemainder.divide(nodePeriodMillis,0,RoundingMode.UP).multiply(nodeSmallToll);
								
								//判断节点余数金额比极限节点金额大
								if(nodeRemainderToll.compareTo(nodeLimitToll) >= 0)
								{
									nodeToll = nodeToll.add(nodeLimitToll);
								}
								else
								{
									nodeToll = nodeToll.add(nodeRemainderToll);
								}
							}
						}

						// 大车类型
						if (carType == 2) {
							// 节点最大金额是否为0
							if(nodeLimitToll.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) <= 0)
							{
								//剩余金额/节点周期时长*最大节点周期金额 =增加的周期累计金额,向上取整
								nodeToll = excludeFreeRemainder.divide(nodePeriodMillis,0,RoundingMode.UP).multiply(nodeLargeToll);
							}
							else
							{
								//剩余金额/最大节点周期长度*最大节点周期金额 =增加的周期累计金额,向下取整
								nodeToll = excludeFreeRemainder.divide(nodeStartLongMillis,0,RoundingMode.DOWN).multiply(nodeLimitToll);
								//节点余数
								BigDecimal nodeRemainder = excludeFreeRemainder.divideAndRemainder(nodeStartLongMillis)[1];
								//节点余数金额
								BigDecimal nodeRemainderToll = nodeRemainder.divide(nodePeriodMillis,0,RoundingMode.UP).multiply(nodeLargeToll);
								
								//判断节点余数金额比极限节点金额大
								if(nodeRemainderToll.compareTo(nodeLimitToll) >= 0)
								{
									nodeToll = nodeToll.add(nodeLimitToll);
								}
								else
								{
									nodeToll = nodeToll.add(nodeRemainderToll);
								}
							}
						}1*/
						
						//判断是否大于最大周期金额
						/*if(maxTollBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) >=0 
								&& nodeToll.compareTo(maxTollBD)>=0)
						{
							nodeToll = maxTollBD;
						}
						
						//增加节点金额
						totalToll = totalToll.add(nodeToll);*/
						
						/*1
						nodeTolls = nodeTolls.add(nodeToll);
					}
				}
				//判断是否大于最大周期金额
				if(maxTollBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) >=0 
						&& nodeTolls.compareTo(maxTollBD)>=0)
				{
					nodeTolls = maxTollBD;
				}
				//增加节点金额
				totalToll = totalToll.add(nodeTolls);
			}
			return totalToll.longValue();
		} */
	}

	
	/**
	 * 
	   * @Title : singleCycle 
	   * @功能描述: 一个周期内的金额计算
	   * @传入参数：@param setting
	   * @传入参数：@param nodes
	   * @传入参数：@param carType
	   * @传入参数：@param excludeFreeRemainder
	   * @传入参数：@return
	   * @返回类型：long 
	   * @throws ：
	 */
	 /*private long singleCycle(TLChargeRuleSettingBean setting,List<TLChargeRuleNodeBean> nodes,int carType,BigDecimal excludeFreeRemainder)
	 {
			// 序列id
			int indexId = 1;

			// 标记是否超过一个周期节点的计费方式
			boolean indexFlag = true;
			
			// 最大周期金额
			BigDecimal maxTollDB = new BigDecimal(setting.getMaxToll());
			
			// 收费金额
			BigDecimal totalToll = new BigDecimal(ChargeRuleConstant.ZERO);
		 
			// 迭代费用次数
			for (int i = 0; i < nodes.size(); i++) {
				// 获得节点对象
				TLChargeRuleNodeBean node = nodes.get(i);

				// 判断游标是否与id一致
				if (indexId == node.getId()) {
					// 获得节点时长的极限毫秒数
					BigDecimal nodeStartLongMillis = new BigDecimal(node.getStartLong())
							.multiply(new BigDecimal(ChargeRuleConstant.ONE_THOUSAND))
							.multiply(new BigDecimal(ChargeRuleConstant.SIXTY));
					// 节点计费周期的毫秒数
					BigDecimal nodePeriodMillis = new BigDecimal(node.getPeriod())
							.multiply(new BigDecimal(ChargeRuleConstant.ONE_THOUSAND))
							.multiply(new BigDecimal(ChargeRuleConstant.SIXTY));
					// 节点金额
					BigDecimal nodeSmallToll = new BigDecimal(node.getSmallToll());
					BigDecimal nodeLargeToll = new BigDecimal(node.getLargeToll());
					BigDecimal nodeLimitToll = new BigDecimal(node.getLimitToll());
					BigDecimal nodeToll = new BigDecimal(ChargeRuleConstant.ZERO);
					// 计算节点金额
					// 小车类型
					if (carType == ChargeRuleConstant.SMALL_CAR) {
						// 判断是否小于节点极限长度
						if (excludeFreeRemainder.compareTo(nodeStartLongMillis) <= 0) {
							// 通信费=(剩余时间/节点累计时长)*小车停车费
							nodeToll = excludeFreeRemainder.divide(nodePeriodMillis, 0, RoundingMode.UP)
									.multiply(nodeSmallToll);
							// 剩余时间=0
							excludeFreeRemainder = excludeFreeRemainder.subtract(excludeFreeRemainder);
						} else {
							// 通信费=(节点计费周期的毫秒数/节点累计时长)*小车停车费
							nodeToll = nodeStartLongMillis.divide(nodePeriodMillis, 0, RoundingMode.UP)
									.multiply(nodeSmallToll);
							// 剩余时间=剩余时间-节点最大时间
							excludeFreeRemainder = excludeFreeRemainder.subtract(nodeStartLongMillis);
						}
					}

					// 大车类型
					if (carType == ChargeRuleConstant.LARGE_CAR || carType == ChargeRuleConstant.LORRY) {
						// 判断是否小于节点极限长度
						if (excludeFreeRemainder.compareTo(nodeStartLongMillis) <= 0) {
							// 通信费=(剩余时间/节点累计时长)*小车停车费
							nodeToll = excludeFreeRemainder.divide(nodePeriodMillis, 0, RoundingMode.UP)
									.multiply(nodeLargeToll);
							// 剩余时间=0
							excludeFreeRemainder = excludeFreeRemainder.subtract(excludeFreeRemainder);
						} else {
							// 通信费=(节点计费周期的毫秒数/节点累计时长)*小车停车费
							nodeToll = nodeStartLongMillis.divide(nodePeriodMillis, 0, RoundingMode.UP)
									.multiply(nodeLargeToll);
							// 剩余时间=剩余时间-节点最大时间
							excludeFreeRemainder = excludeFreeRemainder.subtract(nodeStartLongMillis);
						}
					}

					// 判断是否大于节点最大金额
					if (nodeLimitToll.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
							&& nodeToll.compareTo(nodeLimitToll) >= 0) {
						// 通信费加上节点极限金额
						totalToll = totalToll.add(nodeLimitToll);
					} else {
						// 通信费加上节点计算的金额
						totalToll = totalToll.add(nodeToll);
					}

					// 判断剩余时间是否小于等于0，小于等于0时退出循环
					if (excludeFreeRemainder.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) <= 0) {
						if(maxTollDB.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) <= 0
								&& totalToll.compareTo(maxTollDB) >= 0)
						{
							return maxTollDB.longValue();
						}
						return totalToll.longValue();
					}

					// 判断是否需要累加
					if (indexFlag) {
						// 增加游标
						indexId++;
					}

					// 判断超过节点后的计费方式1.重复 2.累计
					if (indexId > setting.getNum() && setting.getBeyoneChargeFlag() == 1) {
						indexId = 1;
						indexFlag = true;
					}

					// 累计最后一次
					if ((indexId > setting.getNum() || indexFlag == false) && setting.getBeyoneChargeFlag() == 2) {
						indexId = setting.getNum();
						indexFlag = false;
					}
				}

				// 判断重新循环计费节点
				if (i == nodes.size() - 1) {
					i = 0;
				}
			}
		 return totalToll.longValue();
	 }*/

}
