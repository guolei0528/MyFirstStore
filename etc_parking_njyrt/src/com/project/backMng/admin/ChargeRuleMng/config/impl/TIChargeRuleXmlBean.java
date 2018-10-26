package com.project.backMng.admin.ChargeRuleMng.config.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleConstant;
import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleXml;
import com.project.tools.DateUtil;

public class TIChargeRuleXmlBean implements ChargeRuleXml {

	// 基础配置信息
	private TIChargeRuleSettingBean tiSetting;

	// 节点配置信息
	private List<TIChargeRuleNodeBean> tiNode;

	public TIChargeRuleSettingBean getTiSetting() {
		return tiSetting;
	}

	public void setTiSetting(TIChargeRuleSettingBean tiSetting) {
		this.tiSetting = tiSetting;
	}

	public List<TIChargeRuleNodeBean> getTiNode() {
		return tiNode;
	}

	public void setTiNode(List<TIChargeRuleNodeBean> tiNode) {
		this.tiNode = tiNode;
	}

	@Override
	public long calculateToll(Date startTime, Date endTime, int carType) {
		TIChargeRuleSettingBean setting = this.getTiSetting();
		List<TIChargeRuleNodeBean> nodes = this.getTiNode();

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
			TIChargeRuleNodeBean node = nodes.get(i);
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

				// 比对节点市场是否大于相差时间，是的赋值为相差时间
				if (currentTimes.compareTo(contrastTime) > 0) {
					currentTimes = contrastTime;
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
				if(currentTimes.compareTo(new BigDecimal(freeTime)) > 0)
				{
					periodTime = currentTimes.subtract(new BigDecimal(freeTime)).divide(period, 0, RoundingMode.UP);
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
						if (smallLimitTime.compareTo(currentTimes) <= 0) {
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
							// 开始时间+免费时间
							startTime = new Date(startTimeBD.add(new BigDecimal(freeTime)).longValue());
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
							BigDecimal limitPeriodTime= largeLimitTime.subtract(new BigDecimal(freeTime))
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
							// 开始时间+免费时间
							startTime = new Date(startTimeBD.add(new BigDecimal(freeTime)).longValue());
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
					&& startHms.getTime() <= twelTime.getTime())
					|| (zeroTime.getTime() <= startHms.getTime() && startHms.getTime() < nodeEndTime.getTime()))) {

				// 获得节点结束BD
				// 获得节点中的开始时间
				// 获得开始时间BD
				BigDecimal startTimeBD = new BigDecimal(startTime.getTime());

				// 获得跨0点的时长
				BigDecimal addNodeTime = new BigDecimal(ChargeRuleConstant.ZERO);
				// 判断开始<=前台时间 && 当前时间<=24点
				if (nodeStartTime.getTime() <= startHms.getTime() && startHms.getTime() <= twelTime.getTime()) {
					// 24点-当前时间
					addNodeTime = addNodeTime
							.add(new BigDecimal(twelTime.getTime()).subtract(new BigDecimal(startHms.getTime())));
					// 节点结束时间-0点
					addNodeTime = addNodeTime
							.add(new BigDecimal(nodeEndTime.getTime()).subtract(new BigDecimal(zeroTime.getTime())));
				}

				// 判断是否在0点后为当前开始时间
				if (zeroTime.getTime() <= startHms.getTime() && startHms.getTime() < nodeEndTime.getTime()) {
					// 节点结束时间-当前开始时间
					addNodeTime = addNodeTime
							.add(new BigDecimal(nodeEndTime.getTime()).subtract(new BigDecimal(startHms.getTime())));
				}

				// 出口时间-当前节点时间 = 相差时间
				BigDecimal contrastTime = endTimeBD.subtract(startTimeBD);

				// 比对节点市场是否大于相差时间，是的赋值为相差时间
				if (addNodeTime.compareTo(contrastTime) > 0) {
					addNodeTime = contrastTime;
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
				
				if(addNodeTime.compareTo(new BigDecimal(freeTime)) > 0)
				{
					periodTime = addNodeTime.subtract(new BigDecimal(freeTime)).divide(period, 0, RoundingMode.UP);
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
							// 开始时间+免费时间
							startTime = new Date(startTimeBD.add(new BigDecimal(freeTime)).longValue());
							freeTime=0;
							if (i == num - 1) {
								i = -1;
							}
							continue;
						}
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
							// 开始时间+免费时间
							startTime = new Date(startTimeBD.add(new BigDecimal(freeTime)).longValue());
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

}
