package com.project.backMng.admin.ChargeRuleMng.config.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleConstant;
import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleFactory;
import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleXml;
import com.project.tools.DateUtil;

public class SpecChgTIChargeRuleXmlBean implements ChargeRuleXml{

	private List<SpecChgTIChargeRuleNodeBean> tiNode;
	
	private SpecChgTIChargeRuleSettingBean tiSetting;
	
	public List<SpecChgTIChargeRuleNodeBean> getTiNode() {
		return tiNode;
	}

	public void setTiNode(List<SpecChgTIChargeRuleNodeBean> tiNode) {
		this.tiNode = tiNode;
	}

	public SpecChgTIChargeRuleSettingBean getTiSetting() {
		return tiSetting;
	}

	public void setTiSetting(SpecChgTIChargeRuleSettingBean tiSetting) {
		this.tiSetting = tiSetting;
	}



	/**
	 * 
	   * @Title : calculateToll 
	   * @功能描述: 1.省人民医院，享受优惠计费规则
	   * @传入参数：@param startTime
	   * @传入参数：@param endTime
	   * @传入参数：@param carType
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	@Override
	public long calculateToll(Date startTime, Date endTime, int carType) {
		SpecChgTIChargeRuleSettingBean setting = this.getTiSetting();
		List<SpecChgTIChargeRuleNodeBean> nodes = this.getTiNode();

		// 接受时间-开始时间=总时长
		long totalTime = endTime.getTime() - startTime.getTime();

		// 免费时长换成毫秒
		long freeTime = setting.getFreeTime() * ChargeRuleConstant.SIXTY * ChargeRuleConstant.ONE_THOUSAND;
		
		// 判断是否小于免费时间,小于免费时间金额为0，分*60*1000=毫秒
		if (totalTime < freeTime) {
			return 0;
		}
		
		// 开始时间减免费时间
		startTime = new Date(startTime.getTime());

		// 最大周期时长：
		// BigDecimal maxCycleBD = new
		// BigDecimal(setting.getMaxCycle()).multiply(
		// new BigDecimal(ChargeRuleConstant.SIXTY).multiply(new
		// BigDecimal(ChargeRuleConstant.ONE_THOUSAND)));
		// 最大周期金额:
		// BigDecimal maxTollBD = new BigDecimal(setting.getMaxToll());
		// 总时长减去免费时间=剩余时间
		// long excludeFreeTime = totalTime
		// - setting.getFreeTime() * ChargeRuleConstant.SIXTY *
		// ChargeRuleConstant.ONE_THOUSAND;
		// 获得BigDecimal的非免费时间毫秒数和最大周期毫秒数
		// BigDecimal excludeFreeTimeBD = new BigDecimal(excludeFreeTime);
		// 总的通行费用
		BigDecimal totalToll = new BigDecimal(ChargeRuleConstant.ZERO);

		// 判断最大周期金额是否有效
		/*
		 * if (maxTollBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0)
		 * { // 减去免费时间长度/最大周期=最大周期次数,向下取整 BigDecimal maxCycleTimeBD =
		 * excludeFreeTimeBD.divide(maxCycleBD, 0, RoundingMode.DOWN);
		 * 
		 * // 最大周期次数*最大金额 = 最大周期通信费 BigDecimal maxCycleToll =
		 * maxCycleTimeBD.multiply(maxTollBD);
		 * 
		 * // 累加上总的通行费用 totalToll = totalToll.add(maxCycleToll);
		 * 
		 * // 剩余时间+使用时间 = 剩余开始时间点 startTime = new Date(startTime.getTime() +
		 * maxCycleTimeBD.multiply(maxCycleBD).longValue()); }
		 */

		// 获得节点数
		int num = setting.getNum();

		// 如果节点为0
		/*
		 * if (num == 0) { // 减去免费时间长度/最大周期=最大周期次数,向下取整 BigDecimal
		 * maxCycleTimeBD = excludeFreeTimeBD.divide(maxCycleBD, 0,
		 * RoundingMode.UP);
		 * 
		 * // 最大周期次数*最大金额 = 最大周期通信费 BigDecimal maxCycleToll =
		 * maxCycleTimeBD.multiply(maxTollBD);
		 * 
		 * // 累加上总的通行费用 totalToll = totalToll.add(maxCycleToll);
		 * 
		 * // 返回金额 return totalToll.longValue(); }
		 */

		// 累计节点的通行费
		// BigDecimal nodeToll = new BigDecimal(ChargeRuleConstant.ZERO);

		// 累计节点增加的时间+免费时间
		// BigDecimal addNodesTime = new BigDecimal(setting.getFreeTime() *
		// ChargeRuleConstant.SIXTY * ChargeRuleConstant.ONE_THOUSAND);
		
		// 周免费时间段
		List<Integer> freeWeekdate = setting.getFreeWeekday();
		
		// 判断周免费的周几：如周六周日免费
		List<Integer> freeDayOfWeek = new ArrayList<Integer>();
		// 周一
		if(freeWeekdate.contains(1))
		{
			freeDayOfWeek.add(Calendar.MONDAY);
		}
		// 周二
		if(freeWeekdate.contains(2))
		{
			freeDayOfWeek.add(Calendar.TUESDAY);
		}
		
		// 周三
		if(freeWeekdate.contains(3))
		{
			freeDayOfWeek.add(Calendar.WEDNESDAY);
		}
		// 周四
		if(freeWeekdate.contains(4))
		{
			freeDayOfWeek.add(Calendar.THURSDAY);
		}
		// 周五
		if(freeWeekdate.contains(5))
		{
			freeDayOfWeek.add(Calendar.FRIDAY);
		}
		// 周六
		if(freeWeekdate.contains(6))
		{
			freeDayOfWeek.add(Calendar.SATURDAY);
		}
		// 周日
		if(freeWeekdate.contains(7))
		{
			freeDayOfWeek.add(Calendar.SUNDAY);
		}
		
		// 可使用的计费周期
		int changeCycle = setting.getChangeCycle();
		
		// 获得跳转的编号
		String changeId = setting.getChangeId();

		// 判断是否存在计费周期
		boolean changeStatus = false;
		if(changeCycle != 0)
		{
			changeStatus = true;
		}
		
		
		// 判断起始时间小于开始时间时循环
		// 结束的BigDecimal
		BigDecimal endTimeBD = new BigDecimal(endTime.getTime());
		// while (startTime.getTime() < endTime.getTime()) {
		// 迭代节点
		for (int i = 0; i < num; i++) {
			// 获得开始节点时间
			String startHmsStr = DateUtil.get4Hms(startTime);
			Date startHms = DateUtil.parse4Hms(startHmsStr);
			// 获得当前节点数据
			SpecChgTIChargeRuleNodeBean node = nodes.get(i);
			// 判断节点与
			// 获得节点开始时间
			Date nodeStartTime = DateUtil.parse4Hms(node.getStartTime());
			Date nodeEndTime = DateUtil.parse4Hms(node.getEndTime());

			// 修改
			int limitType = node.getLimitType();
			// 节点极限时间长度,转换成毫秒
			BigDecimal smallLimitTime = new BigDecimal(node.getSmallLimitTime()).multiply(
					new BigDecimal(ChargeRuleConstant.SIXTY).multiply(new BigDecimal(ChargeRuleConstant.ONE_THOUSAND)));
			BigDecimal largeLimitTime = new BigDecimal(node.getLargeLimitTime()).multiply(
					new BigDecimal(ChargeRuleConstant.SIXTY).multiply(new BigDecimal(ChargeRuleConstant.ONE_THOUSAND)));

			// 赋值节点极限金额
			BigDecimal smallLimitToll = new BigDecimal(node.getSmallLimitToll());
			// 赋值节点极限金额
			BigDecimal largeLimitToll = new BigDecimal(node.getLargeLimitToll());

			// 赋值节点中的周期时长
			BigDecimal period = new BigDecimal(node.getPeriod()).multiply(
					new BigDecimal(ChargeRuleConstant.SIXTY).multiply(new BigDecimal(ChargeRuleConstant.ONE_THOUSAND)));

			// 判断是否属于此节点
			if (nodeStartTime.getTime() < nodeEndTime.getTime() && nodeStartTime.getTime() <= startHms.getTime()
					&& startHms.getTime() < nodeEndTime.getTime()) {

				// 获得节点结束BD
				BigDecimal nodeEndTimeBD = new BigDecimal(nodeEndTime.getTime());
				// 获得节点中的开始时间
				BigDecimal startHmsBD = new BigDecimal(startHms.getTime());

				// 获得开始时间BD
				BigDecimal startTimeBD = new BigDecimal(startTime.getTime());

				// 节点结束时间-节点中的开始时间
				BigDecimal currentTimes = nodeEndTimeBD.subtract(startHmsBD);

				// 出口时间-当前节点时间 = 相差时间
				BigDecimal contrastTime = endTimeBD.subtract(startTimeBD);
				
				// 计算的节点时间
				BigDecimal chargeNodeTime = new BigDecimal(ChargeRuleConstant.ZERO);
				
				// 判断是否为免费日
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(startTime);
				if(freeDayOfWeek.contains(calendar.get(Calendar.DAY_OF_WEEK)))
				{
					// 节点增加1天
					BigDecimal tomorrowBigDecimal =  startTimeBD.add(new BigDecimal(ChargeRuleConstant.ONE_DAY));
					// 第二天的时间
					Date tomorrowDate = new Date(tomorrowBigDecimal.longValue());
					String tomorrowStr = DateUtil.get4yMd(tomorrowDate);
					// 获得当天截止24点的时长
					BigDecimal dayTime = new BigDecimal(DateUtil.parse4yMd(tomorrowStr).getTime()).subtract(startTimeBD);
					// 判断是否比免费时间大，如果大于免费时间那么计算时间为免费时间
//					if(dayTime.compareTo(new BigDecimal(freeTime)) > 0)
//					{
						chargeNodeTime = new BigDecimal(freeTime);
//					}//否则截止时长为计算时长
//					else
//					{
//						chargeNodeTime = dayTime;
//					}
					
					// 总时长比对到当天24点的时长
					// 总时长比对到当天24点的时长少，待减时长等于总时长
					if(contrastTime.compareTo(dayTime) <= 0)
					{
						currentTimes = contrastTime;
					}// 否则等于到当天截止时长
					else
					{
						currentTimes = dayTime;
					}
					
					// 总时长是否比计算节点时间小,小则按照总时长计算
					if(contrastTime.compareTo(chargeNodeTime) <= 0)
					{
						chargeNodeTime = contrastTime;
					}
				}// 判断不是免费日
				else
				{
					// 赋值计算的节点时长
					chargeNodeTime = currentTimes;
					// 比对节点时长是否大于相差时间，是的赋值为相差时间
					if (currentTimes.compareTo(contrastTime) > 0) {
						currentTimes = contrastTime;
						chargeNodeTime = contrastTime;
					}
				}
				
				
				// 最大周期金额和时间有效 ，并且超过周期时长
				/*
				 * if(maxCycleBD.compareTo(new
				 * BigDecimal(ChargeRuleConstant.ZERO)) >0 &&
				 * maxTollBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO))
				 * >0 && addNodesTime.add(currentTimes).compareTo(maxCycleBD) >=
				 * 0) { currentTimes = maxCycleBD.subtract(addNodesTime); }
				 */

				// 累计周期时长增加
				// addNodesTime = addNodesTime.add(currentTimes);
				
				// 时段次数
				BigDecimal periodTime = null;
				
				// 
				if(chargeNodeTime.compareTo(new BigDecimal(freeTime)) > 0)
				{
					periodTime = chargeNodeTime.subtract(new BigDecimal(freeTime)).divide(period, 0, RoundingMode.UP);
				}
				else
				{
					periodTime = new BigDecimal(0);
				}
				
				// 单次节点计算费用
				BigDecimal currentToll = new BigDecimal(ChargeRuleConstant.ZERO);
				
				// 小车
				if (carType == ChargeRuleConstant.SMALL_CAR) {
					// 累加小车节点金额
					currentToll = currentToll.add(periodTime.multiply(new BigDecimal(node.getSmallToll())));

					// 判断最大上限类型：1时间，2金额
					if (limitType == 1) {
						
						if (smallLimitTime.compareTo(chargeNodeTime) <= 0) {
							//极限节点次数
							BigDecimal limitPeriodTime= smallLimitTime.subtract(new BigDecimal(freeTime))
									.divide(period, 0, RoundingMode.UP);
							
							totalToll = totalToll.add(limitPeriodTime.multiply(new BigDecimal(node.getSmallToll())));
						}
						else
						{
							totalToll = totalToll.add(currentToll);
						}
						// 更新免费时间
						if(new BigDecimal(freeTime).compareTo(currentTimes) <= 0)
						{
							freeTime = 0;
						}
						else
						{
							freeTime = new BigDecimal(freeTime).subtract(currentTimes).longValue();
						}
					}
					else if (limitType == 2) {
						
						if(freeTime > 0)
						{
							String beforeDate = DateUtil.get4yMd(startTime);
							// 开始时间+免费时间
							startTime = new Date(startTimeBD.add(new BigDecimal(freeTime)).longValue());
							// 判断是否减去免费时长后有跨天
							if(!beforeDate.equals(DateUtil.get4yMd(startTime)) && changeStatus)
							{
								changeCycle--;
							}
							freeTime=0;
							if (i == num - 1) {
								i = -1;
							}
							continue;
						}
						
						// 判断金额是否超过节点最大金额
						if (smallLimitToll.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
								&& currentToll.compareTo(smallLimitToll) >= 0) {
							totalToll = totalToll.add(smallLimitToll);
						} else {
							totalToll = totalToll.add(currentToll);
						}
					}
					else
					{
						totalToll = totalToll.add(currentToll);
						// 更新免费时间
						if(new BigDecimal(freeTime).compareTo(currentTimes) <= 0)
						{
							freeTime = 0;
						}
						else
						{
							freeTime = new BigDecimal(freeTime).subtract(currentTimes).longValue();
						}
					}
				}

				// 大车
				if (carType == ChargeRuleConstant.LARGE_CAR || carType == ChargeRuleConstant.LORRY) {
					// 累加大车节点金额
					currentToll = currentToll.add(periodTime.multiply(new BigDecimal(node.getLargeToll())));

					if (limitType == 1) {
						if (largeLimitTime.compareTo(currentTimes) <= 0) {
							
							
							
							
							//极限节点次数
							BigDecimal limitPeriodTime = largeLimitTime.subtract(new BigDecimal(freeTime))
									.divide(period, 0, RoundingMode.UP);
							totalToll = totalToll.add(limitPeriodTime.multiply(new BigDecimal(node.getLargeToll())));
						}
						else
						{
							totalToll = totalToll.add(currentToll);
						}
						// 更新免费时间
						if(new BigDecimal(freeTime).compareTo(currentTimes) <= 0)
						{
							freeTime = 0;
						}
						else
						{
							freeTime = new BigDecimal(freeTime).subtract(currentTimes).longValue();
						}
					}
					else if (limitType == 2) {
						
						if(freeTime > 0)
						{
							String beforeDate = DateUtil.get4yMd(startTime);
							// 开始时间+免费时间
							startTime = new Date(startTimeBD.add(new BigDecimal(freeTime)).longValue());
							// 判断是否减去免费时长后有跨天
							if(!beforeDate.equals(DateUtil.get4yMd(startTime)) && changeStatus)
							{
								changeCycle--;
							}
							freeTime=0;
							if (i == num - 1) {
								i = -1;
							}
							continue;
						}
						if (largeLimitToll.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
								&& currentToll.compareTo(largeLimitToll) >= 0) {
							totalToll = totalToll.add(largeLimitToll);
						} else {
							totalToll = totalToll.add(currentToll);
						}
					}
					else
					{
						totalToll = totalToll.add(currentToll);
						// 更新免费时间
						if(new BigDecimal(freeTime).compareTo(currentTimes) <= 0)
						{
							freeTime = 0;
						}
						else
						{
							freeTime = new BigDecimal(freeTime).subtract(currentTimes).longValue();
						}
					}
				}

				// 判断金额是否超过节点最大金额
				/*
				 * if (limitToll.compareTo(new
				 * BigDecimal(ChargeRuleConstant.ZERO)) > 0 &&
				 * currentToll.compareTo(limitToll) >= 0) { totalToll =
				 * totalToll.add(limitToll); } else { totalToll =
				 * totalToll.add(currentToll); }
				 */

				// 开始时间+(节点结束时间-节点中的开始时间)
				BigDecimal addNodeTime = startTimeBD.add(currentTimes);
				// 赋值开始时间
				startTime = new Date(addNodeTime.longValue());

				// totalToll = totalToll.add(nodeToll);
				// 跳出循环
				
			}

			// 0点
			Date zeroTime = DateUtil.parse4Hms("00:00:00");
			// 24点
			Date twelTime = DateUtil.parse4Hms("24:00:00");

			// 判断是否属于此节点,节点跨0点的情况
			if (nodeStartTime.getTime() >= nodeEndTime.getTime() && ((nodeStartTime.getTime() <= startHms.getTime()
					&& startHms.getTime() < twelTime.getTime())
					|| (zeroTime.getTime() <= startHms.getTime() && startHms.getTime() < nodeEndTime.getTime()))) {

				// 获得节点结束BD
				// 获得节点中的开始时间
				// 获得开始时间BD
				BigDecimal startTimeBD = new BigDecimal(startTime.getTime());

				// 获得跨0点的时长
				BigDecimal addNodeTime = new BigDecimal(ChargeRuleConstant.ZERO);
				// 获得跨0点的时长
				BigDecimal chargeNodeTime = new BigDecimal(ChargeRuleConstant.ZERO);
				
				// 判断开始<=前台时间 && 当前时间<=24点
				if (nodeStartTime.getTime() <= startHms.getTime() && startHms.getTime() < twelTime.getTime()) {
					// 24点-当前时间 
					addNodeTime = addNodeTime
							.add(new BigDecimal(twelTime.getTime()).subtract(new BigDecimal(startHms.getTime())));
					// 赋值计算时间
//					chargeNodeTime = addNodeTime;
					
					// 判断是否为免费周期
				    Date beforeNight = new Date(startTimeBD.longValue());
				    Calendar calendar = Calendar.getInstance();
				    calendar.setTime(beforeNight);    
				    
				    // 判断如果不在免费时间里
					if(!freeDayOfWeek.contains(calendar.get(Calendar.DAY_OF_WEEK)))
					{
						Date afterToday = new Date(startTimeBD.longValue()+ChargeRuleConstant.ONE_DAY);
						String afterTodayStr = DateUtil.get4yMd(afterToday);
						Date night = DateUtil.parse4yMd(afterTodayStr);
						calendar.setTime(night);
						// 判断是否增加剩余0点后的节点时长
						if((changeCycle > 1 || !changeStatus) && !freeDayOfWeek.contains(calendar.get(Calendar.DAY_OF_WEEK)))
						{
							// 节点结束时间-0点
							addNodeTime = addNodeTime
									.add(new BigDecimal(nodeEndTime.getTime()).subtract(new BigDecimal(zeroTime.getTime())));
						}
						chargeNodeTime = addNodeTime;
					}
					
				}

				// 判断是否在0点后为当前开始时间
				if (zeroTime.getTime() <= startHms.getTime() && startHms.getTime() < nodeEndTime.getTime()) {
					
					// 判断是否为免费周期
				    Date afterNight = new Date(startTimeBD.longValue());
				    Calendar calendar = Calendar.getInstance();
				    calendar.setTime(afterNight);
					if(freeDayOfWeek.contains(calendar.get(Calendar.DAY_OF_WEEK)))
					{
						BigDecimal afterNightBD = new BigDecimal(afterNight.getTime());
						// 节点增加1天
						BigDecimal tomorrowBigDecimal =  afterNightBD.add(new BigDecimal(ChargeRuleConstant.ONE_DAY));
						// 第二天的时间
						Date tomorrowDate = new Date(tomorrowBigDecimal.longValue());
						String tomorrowStr = DateUtil.get4yMd(tomorrowDate);
						addNodeTime = new BigDecimal(DateUtil.parse4yMd(tomorrowStr).getTime()).subtract(afterNightBD);
					}
					else
					{
						// 节点结束时间-当前开始时间
						addNodeTime = addNodeTime
								.add(new BigDecimal(nodeEndTime.getTime()).subtract(new BigDecimal(startHms.getTime())));
						chargeNodeTime = addNodeTime;
					}
					
				}

				// 出口时间-当前节点时间 = 相差时间
				BigDecimal contrastTime = endTimeBD.subtract(startTimeBD);

				// 比对节点时长是否大于相差时间，是的赋值为相差时间
				if (addNodeTime.compareTo(contrastTime) > 0) {
					addNodeTime = contrastTime;
				}
				
				
				// 比对节点时长是否大于计算时间，是的赋值为相差时间
				if(chargeNodeTime.compareTo(contrastTime) > 0)
				{
					chargeNodeTime = contrastTime;
				}
				
				// 最大周期金额和时间有效 ，并且超过周期时长
				/* 
				 * if(maxCycleBD.compareTo(new
				 * BigDecimal(ChargeRuleConstant.ZERO)) >0 &&
				 * maxTollBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO))
				 * >0 && addNodesTime.add(addNodeTime).compareTo(maxCycleBD) >=
				 * 0) { addNodeTime = maxCycleBD.subtract(addNodesTime); }
				 */

				// 累计周期时长增加
				// addNodesTime = addNodesTime.add(addNodeTime);

//				BigDecimal periodTime = addNodeTime.subtract(new BigDecimal(freeTime)).divide(period, 0, RoundingMode.UP);
				
				BigDecimal periodTime = null;
				
				if(chargeNodeTime.compareTo(new BigDecimal(freeTime)) > 0)
				{
					periodTime = chargeNodeTime.subtract(new BigDecimal(freeTime)).divide(period, 0, RoundingMode.UP);
				}
				else
				{
					periodTime = new BigDecimal(0);
				}
				
				// 节点增长的计算费用
				BigDecimal currentToll = new BigDecimal(ChargeRuleConstant.ZERO);
				if (carType == ChargeRuleConstant.SMALL_CAR) {
					// 增长金额
					currentToll = currentToll.add(periodTime.multiply(new BigDecimal(node.getSmallToll())));

					if (limitType == 1) {
						if (smallLimitTime.compareTo(addNodeTime) <= 0) {
							//极限节点次数
							BigDecimal limitPeriodTime= smallLimitTime.subtract(new BigDecimal(freeTime))
									.divide(period, 0, RoundingMode.UP);
							totalToll = totalToll.add(limitPeriodTime.multiply(new BigDecimal(node.getSmallToll())));
							
						}
						else
						{
							totalToll = totalToll.add(currentToll);
						}
						// 更新免费时间
						if(new BigDecimal(freeTime).compareTo(addNodeTime) <= 0)
						{
							freeTime = 0;
						}
						else
						{
							freeTime = new BigDecimal(freeTime).subtract(addNodeTime).longValue();
						}
					}
					else if (limitType == 2) {
						
						if(freeTime > 0)
						{
							
							String beforeDate = DateUtil.get4yMd(startTime);
							// 开始时间+免费时间
							startTime = new Date(startTimeBD.add(new BigDecimal(freeTime)).longValue());
							// 判断是否减去免费时长后有跨天
							if(!beforeDate.equals(DateUtil.get4yMd(startTime)) && changeStatus)
							{
								changeCycle--;
							}
							freeTime=0;
							if (i == num - 1) {
								i = -1;
							}
							continue;
						}
						
						
						// 判断小车费用是否超过了最大收费
						if (smallLimitToll.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
								&& currentToll.compareTo(smallLimitToll) >= 0) {
							totalToll = totalToll.add(smallLimitToll);
						} else {
							// 累加节点金额
							totalToll = totalToll.add(currentToll);
						}
					}
					else
					{
						totalToll = totalToll.add(currentToll);
						// 更新免费时间
						if(new BigDecimal(freeTime).compareTo(addNodeTime) <= 0)
						{
							freeTime = 0;
						}
						else
						{
							freeTime = new BigDecimal(freeTime).subtract(addNodeTime).longValue();
						}
					}
				}

				if (carType == ChargeRuleConstant.LARGE_CAR || carType == ChargeRuleConstant.LORRY) {
					// 增长金额
					currentToll = currentToll.add(periodTime.multiply(new BigDecimal(node.getLargeToll())));

					if (limitType == 1) {
						if (largeLimitTime.compareTo(addNodeTime) <= 0) {
							//极限节点次数
							BigDecimal limitPeriodTime= largeLimitTime.subtract(new BigDecimal(freeTime))
									.divide(period, 0, RoundingMode.UP);
							totalToll = totalToll.add(limitPeriodTime.multiply(new BigDecimal(node.getLargeToll())));
						}
						else
						{
							totalToll = totalToll.add(currentToll);
						}
						// 更新免费时间
						if(new BigDecimal(freeTime).compareTo(addNodeTime) <= 0)
						{
							freeTime = 0;
						}
						else
						{
							freeTime = new BigDecimal(freeTime).subtract(addNodeTime).longValue();
						}
					}
					else if (limitType == 2) {
						if(freeTime > 0)
						{
							String beforeDate = DateUtil.get4yMd(startTime);
							// 开始时间+免费时间
							startTime = new Date(startTimeBD.add(new BigDecimal(freeTime)).longValue());
							// 判断是否减去免费时长后有跨天
							if(!beforeDate.equals(DateUtil.get4yMd(startTime)) && changeStatus)
							{
								changeCycle--;
							}
							freeTime=0;
							if (i == num - 1) {
								i = -1;
							}
							continue;
						}
						if (largeLimitToll.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
								&& currentToll.compareTo(largeLimitToll) >= 0) {
							totalToll = totalToll.add(largeLimitToll);
						} else {
							// 累加节点金额
							totalToll = totalToll.add(currentToll);
						}
					}
					else
					{
						totalToll = totalToll.add(currentToll);
						// 更新免费时间
						if(new BigDecimal(freeTime).compareTo(addNodeTime) <= 0)
						{
							freeTime = 0;
						}
						else
						{
							freeTime = new BigDecimal(freeTime).subtract(addNodeTime).longValue();
						}
					}
				}

				// 判断是否节点最大金额小于节点计算金额，取值节点最大金额
				/*
				 * if (limitToll.compareTo(new
				 * BigDecimal(ChargeRuleConstant.ZERO)) > 0 &&
				 * currentToll.compareTo(limitToll) >= 0) { totalToll =
				 * totalToll.add(limitToll); } else { // 累加节点金额 totalToll =
				 * totalToll.add(currentToll); }
				 */

				// 赋值开始时间
				startTime = new Date(startTimeBD.add(addNodeTime).longValue());
				
				// 判断是否存在变更的收费周期
				if(changeStatus)
				{
					// 减少周期时长
					changeCycle--;
					// 判断使用的周期次数是否截止
					if(changeCycle == 0 && startTime.getTime() < endTime.getTime())
					{
						// 根据时间点、车型和计费规则id增加计费
						totalToll = totalToll.add(new BigDecimal(calculateToll(startTime,endTime,carType,changeId)));
						break;
					}
				}
			}

			// 判断是否超过最大周期，超过累积的最大周期时计算费用
			/*
			 * if(maxTollBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) >
			 * 0 && addNodesTime.compareTo(maxCycleBD) >= 0 &&
			 * nodeToll.compareTo(maxTollBD) >= 0) { // 赋值最大周期金额 totalToll =
			 * totalToll.add(maxTollBD); nodeToll = nodeToll.subtract(nodeToll);
			 * addNodesTime = addNodesTime.subtract(addNodesTime); }
			 * 
			 * //判断是否超过最大周期，超过累积的最大周期时计算费用 if((maxTollBD.compareTo(new
			 * BigDecimal(ChargeRuleConstant.ZERO)) > 0 &&
			 * addNodesTime.compareTo(maxCycleBD) >= 0 &&
			 * nodeToll.compareTo(maxTollBD) < 0) || maxTollBD.compareTo(new
			 * BigDecimal(ChargeRuleConstant.ZERO)) <= 0) { // 复制累计的节点金额
			 * totalToll = totalToll.add(nodeToll); nodeToll =
			 * nodeToll.subtract(nodeToll); addNodesTime =
			 * addNodesTime.subtract(addNodesTime); }
			 */

			// 判断是否入口节点大于出口节点
			if (startTime.getTime() >= endTime.getTime()) {
				break;
			}

			// 循环迭代节点
			if (i == num - 1) {
				i = -1;
			}
		}
		// 判断时间点是否超过结束时间
		// }
		// 判断最大周期金额是否有效并判断是否节点累加金额大于最大周期金额
		/*
		 * if (maxTollBD.compareTo(new BigDecimal(ChargeRuleConstant.ZERO)) > 0
		 * && nodeToll.compareTo(maxTollBD) >= 0) { // 赋值最大周期金额 totalToll =
		 * totalToll.add(maxTollBD); } else { // 复制累计的节点金额 totalToll =
		 * totalToll.add(nodeToll); }
		 */
		return totalToll.longValue();
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
	private long calculateToll(Date startTime, Date endTime,int carType,String charge_code) {
		//获得按时间规则方式1
		ChargeRuleFactory chargeRuleFactory = new ChargeRuleFactory();
		ChargeRuleXml chargeRuleXml = chargeRuleFactory.createChargeRule(charge_code);
		long toll = chargeRuleXml.calculateToll(startTime, endTime,carType);
		return toll;
	}
	
	
	
	
}
