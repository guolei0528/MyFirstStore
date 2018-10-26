package com.project.backMng.admin.ChargeRuleMng.config.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleConstant;
import com.project.backMng.admin.ChargeRuleMng.config.ChargeRuleXml;
import com.project.tools.DateUtil;

public class TCChargeRuleXmlBean implements ChargeRuleXml {

	// 基础配置信息
	private TCChargeRuleSettingBean tcSetting;

	// 节点配置信息
	private List<TCChargeRuleNodeBean> tcNode;


	public TCChargeRuleSettingBean getTcSetting() {
		return tcSetting;
	}


	public void setTcSetting(TCChargeRuleSettingBean tcSetting) {
		this.tcSetting = tcSetting;
	}


	public List<TCChargeRuleNodeBean> getTcNode() {
		return tcNode;
	}


	public void setTcNode(List<TCChargeRuleNodeBean> tcNode) {
		this.tcNode = tcNode;
	}


	/**
	 * 
	 * @Title : calculateToll
	 * @功能描述: 根据次数计算费用
	 * @传入参数：@param startTime
	 * @传入参数：@param endTime
	 * @传入参数：@param carType
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public long calculateToll(Date startTime, Date endTime, int carType) {

		TCChargeRuleSettingBean setting = this.getTcSetting();

		List<TCChargeRuleNodeBean> nodes = this.getTcNode();

		// 接受时间-开始时间=总时长
		long totalTime = endTime.getTime() - startTime.getTime();

		// 判断是否小于免费时间,小于免费时间金额为0，分*60*1000=毫秒
		if (totalTime < setting.getFreeTime() * ChargeRuleConstant.SIXTY * ChargeRuleConstant.ONE_THOUSAND) {
			return 0;
		}

		// 最大周期时长：
//		BigDecimal maxCycleBD = new BigDecimal(setting.getMaxCycle()).multiply(
//				new BigDecimal(ChargeRuleConstant.SIXTY).multiply(new BigDecimal(ChargeRuleConstant.ONE_THOUSAND)));
		// 最大周期金额:
//		BigDecimal maxTollBD = new BigDecimal(setting.getMaxToll());
		// 总时长减去免费时间=剩余时间
//		long excludeFreeTime = totalTime
//				- setting.getFreeTime() * ChargeRuleConstant.SIXTY * ChargeRuleConstant.ONE_THOUSAND;
		// 获得BigDecimal的非免费时间毫秒数和最大周期毫秒数
//		BigDecimal excludeFreeTimeBD = new BigDecimal(excludeFreeTime);
		// 总的通行费用
		BigDecimal totalToll = new BigDecimal(ChargeRuleConstant.ZERO);

		// 获得节点数
		int num = setting.getNum();
		// 赋值次票一次停车时长
		int timesUserMax = setting.getTimesUserMax();
		// 最大周期计费规则金额指向的节点
		int maxChargeNode = setting.getMaxChargeNode();
		// 获得次票一次停车时长BD
		BigDecimal timesUserMaxBD = new BigDecimal(timesUserMax).multiply(
				new BigDecimal(ChargeRuleConstant.SIXTY).multiply(new BigDecimal(ChargeRuleConstant.ONE_THOUSAND)));

		// 结束的BigDecimal
		BigDecimal endTimeBD = new BigDecimal(endTime.getTime());
		
		// 跨节点的计算金额
		BigDecimal stepToll = new BigDecimal(getStepToll(maxChargeNode, startTime, endTime, nodes, carType));
		
		
		// 迭代节点
		for (int i = 0; i < num; i++) {
			// 获得开始节点时间
			String startHmsStr = DateUtil.get4Hms(startTime);
			Date startHms = DateUtil.parse4Hms(startHmsStr);
			// 获得当前节点数据
			TCChargeRuleNodeBean node = nodes.get(i);

			// 判断节点与
			// 获得节点开始时间
			Date nodeStartTime = DateUtil.parse4Hms(node.getStartTime());
			Date nodeEndTime = DateUtil.parse4Hms(node.getEndTime());

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

				// 最大跨段时间-开始时间=跨段剩余时间
//				BigDecimal otherTimes = timesUserMaxBD.subtract(startHmsBD);
				
				// 出口时间-当前节点时间 = 相差时间
				BigDecimal contrastTime = endTimeBD.subtract(startTimeBD);
				
				// 单次节点计算费用
				BigDecimal currentToll = new BigDecimal(ChargeRuleConstant.ZERO);

				// 节点时间大于横跨时间并且横跨时间大于剩余时间，取剩余时间
				if(currentTimes.compareTo(timesUserMaxBD) >=0 
						&& timesUserMaxBD.compareTo(contrastTime) >0)
				{
					currentTimes = contrastTime;
					// 小车
					if (carType == ChargeRuleConstant.SMALL_CAR) {
						// 累加小车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getSmallToll()));
					}
					// 大车
					if (carType == ChargeRuleConstant.LARGE_CAR || carType == ChargeRuleConstant.LORRY) {
						// 累加大车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getLargeToll()));
					}
				}
				
				// 节点时间小于横跨时间并且节点时间大于剩余时间，取剩余时间
				else if(currentTimes.compareTo(timesUserMaxBD) <=0 
						&& currentTimes.compareTo(contrastTime) >0)
				{
					currentTimes = contrastTime;
					// 小车
					if (carType == ChargeRuleConstant.SMALL_CAR) {
						// 累加小车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getSmallToll()));
					}

					// 大车
					if (carType == ChargeRuleConstant.LARGE_CAR || carType == ChargeRuleConstant.LORRY) {
						// 累加大车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getLargeToll()));
					}
				}
				
				// 剩余时间大于横跨时间并且横跨时间大于节点时间，取横跨时间，按照跨时间计费
				else if(contrastTime.compareTo(timesUserMaxBD) >=0 
						&& timesUserMaxBD.compareTo(currentTimes) >0)
				{
					currentTimes = timesUserMaxBD;
					// 累加大车节点金额
					currentToll = currentToll.add(stepToll);
				}
				
				// 剩余时间大于横跨时间并且横跨时间小于节点时间，取横跨时间,按照节点计费
				else if(contrastTime.compareTo(timesUserMaxBD) >=0 
						&& timesUserMaxBD.compareTo(currentTimes) <=0)
				{
					currentTimes = timesUserMaxBD;
					// 小车
					if (carType == ChargeRuleConstant.SMALL_CAR) {
						// 累加小车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getSmallToll()));
					}

					// 大车
					if (carType == ChargeRuleConstant.LARGE_CAR || carType == ChargeRuleConstant.LORRY) {
						// 累加大车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getLargeToll()));
					}
				}
				else
				{
					// 小车
					if (carType == ChargeRuleConstant.SMALL_CAR) {
						// 累加小车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getSmallToll()));
					}

					// 大车
					if (carType == ChargeRuleConstant.LARGE_CAR || carType == ChargeRuleConstant.LORRY) {
						// 累加大车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getLargeToll()));
					}
				}
				

				// 判断金额是否超过节点最大金额
				totalToll = totalToll.add(currentToll);

				// 开始时间+(节点结束时间-节点中的开始时间)
				BigDecimal addNodeTime = startTimeBD.add(currentTimes);
				// 赋值开始时间
				startTime = new Date(addNodeTime.longValue());
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
					// 节点结束时间-0点
					addNodeTime = addNodeTime
							.add(new BigDecimal(nodeEndTime.getTime()).subtract(new BigDecimal(zeroTime.getTime())));
				}

				// 出口时间-当前节点时间 = 相差时间
				BigDecimal contrastTime = endTimeBD.subtract(startTimeBD);
				
				// 节点增长的计算费用
				BigDecimal currentToll = new BigDecimal(ChargeRuleConstant.ZERO);
				
				// 节点时间大于横跨时间并且横跨时间大于剩余时间，取剩余时间
				if(addNodeTime.compareTo(timesUserMaxBD) >=0 
						&& timesUserMaxBD.compareTo(contrastTime) >0)
				{
					addNodeTime = contrastTime;
					// 小车
					if (carType == ChargeRuleConstant.SMALL_CAR) {
						// 累加小车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getSmallToll()));
					}
					// 大车
					if (carType == ChargeRuleConstant.LARGE_CAR || carType == ChargeRuleConstant.LORRY) {
						// 累加大车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getLargeToll()));
					}
				}
				
				// 节点时间小于横跨时间并且节点时间大于剩余时间，取剩余时间
				else if(addNodeTime.compareTo(timesUserMaxBD) <=0 
						&& addNodeTime.compareTo(contrastTime) >0)
				{
					addNodeTime = contrastTime;
					// 小车
					if (carType == ChargeRuleConstant.SMALL_CAR) {
						// 累加小车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getSmallToll()));
					}

					// 大车
					if (carType == ChargeRuleConstant.LARGE_CAR || carType == ChargeRuleConstant.LORRY) {
						// 累加大车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getLargeToll()));
					}
				}
				
				// 剩余时间大于横跨时间并且横跨时间大于节点时间，取横跨时间，按照跨时间计费
				else if(contrastTime.compareTo(timesUserMaxBD) >=0 
						&& timesUserMaxBD.compareTo(addNodeTime) >0)
				{
					addNodeTime = timesUserMaxBD;
					// 累加大车节点金额
					currentToll = currentToll.add(stepToll);
				}
				
				// 剩余时间大于横跨时间并且横跨时间小于节点时间，取横跨时间,按照节点计费
				else if(contrastTime.compareTo(timesUserMaxBD) >=0 
						&& timesUserMaxBD.compareTo(addNodeTime) <=0)
				{
					addNodeTime = timesUserMaxBD;
					// 小车
					if (carType == ChargeRuleConstant.SMALL_CAR) {
						// 累加小车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getSmallToll()));
					}

					// 大车
					if (carType == ChargeRuleConstant.LARGE_CAR || carType == ChargeRuleConstant.LORRY) {
						// 累加大车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getLargeToll()));
					}
				}
				else
				{
					// 小车
					if (carType == ChargeRuleConstant.SMALL_CAR) {
						// 累加小车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getSmallToll()));
					}

					// 大车
					if (carType == ChargeRuleConstant.LARGE_CAR || carType == ChargeRuleConstant.LORRY) {
						// 累加大车节点金额
						currentToll = currentToll.add(new BigDecimal(node.getLargeToll()));
					}
				}
				

					// 累加节点金额
				totalToll = totalToll.add(currentToll);

				// 赋值开始时间
				startTime = new Date(startTimeBD.add(addNodeTime).longValue());
			}

			// 判断是否入口节点大于出口节点
			if (startTime.getTime() >= endTime.getTime()) {
				break;
			}

			// 循环迭代节点
			if (i == num - 1) {
				i = -1;
			}
		}

		return totalToll.longValue();
	}
	
	
	// 返回跨时间段的计费金额
	private long getStepToll(int maxChargeNode,Date startTime,Date endTime,List<TCChargeRuleNodeBean> nodes,int carType)
	{
		//开始时间转换成时分秒
		String startHmsStr = DateUtil.get4Hms(startTime);
		Date startHms = DateUtil.parse4Hms(startHmsStr);
		
		//结束时间转换成时分秒
		String endHmsStr = DateUtil.get4Hms(endTime);
		Date endHms = DateUtil.parse4Hms(endHmsStr);
		
		// 0点
		Date zeroTime = DateUtil.parse4Hms("00:00:00");
		// 24点
		Date twelTime = DateUtil.parse4Hms("24:00:00");

		// 迭代规则节点
		for(TCChargeRuleNodeBean node:nodes)
		{
			
			// 指向节点比较
			if(node.getId() == maxChargeNode)
			{
				
				if(ChargeRuleConstant.SMALL_CAR == carType)
				{
					return node.getSmallToll();
				}
				
				if(ChargeRuleConstant.LARGE_CAR == carType || carType == ChargeRuleConstant.LORRY)
				{
					return node.getLargeToll();
				}
				
			}
			
			
			//判断入口节点
			if(ChargeRuleConstant.ENTRY_CHARGE_NODE == maxChargeNode)
			{
				
				//节点开始时间
				Date nodeStartTime = DateUtil.parse4Hms(node.getStartTime());
				
				//结束时间
				Date nodeEndTime = DateUtil.parse4Hms(node.getEndTime());
				
				//判断开始时间是否在节点时间之内
				if((nodeStartTime.getTime() < nodeEndTime.getTime() && nodeStartTime.getTime() <= startHms.getTime()
						&& startHms.getTime() < nodeEndTime.getTime())
						|| (nodeStartTime.getTime() >= nodeEndTime.getTime() && ((nodeStartTime.getTime() <= startHms.getTime()
						&& startHms.getTime() <= twelTime.getTime())
						|| (zeroTime.getTime() <= startHms.getTime() && startHms.getTime() < nodeEndTime.getTime()))))
				{
					if(ChargeRuleConstant.SMALL_CAR == carType)
					{
						return node.getSmallToll();
					}
					
					if(ChargeRuleConstant.LARGE_CAR == carType || carType == ChargeRuleConstant.LORRY)
					{
						return node.getLargeToll();
					}
				}
			}
			
			
			//判断入口节点
			if(ChargeRuleConstant.EXIT_CHARGE_NODE == maxChargeNode)
			{
				
				//节点开始时间
				Date nodeStartTime = DateUtil.parse4Hms(node.getStartTime());
				
				//结束时间
				Date nodeEndTime = DateUtil.parse4Hms(node.getEndTime());
				
				//判断开始时间是否在节点时间之内
				//判断开始时间是否在节点时间之内
				if((nodeStartTime.getTime() < nodeEndTime.getTime() && nodeStartTime.getTime() <= endHms.getTime()
						&& endHms.getTime() < nodeEndTime.getTime())
						|| (nodeStartTime.getTime() >= nodeEndTime.getTime() && ((nodeStartTime.getTime() <= endHms.getTime()
						&& endHms.getTime() <= twelTime.getTime())
						|| (zeroTime.getTime() <= endHms.getTime() && endHms.getTime() < nodeEndTime.getTime()))))
				{
					if(ChargeRuleConstant.SMALL_CAR == carType)
					{
						return node.getSmallToll();
					}
					
					if(ChargeRuleConstant.LARGE_CAR == carType || carType == ChargeRuleConstant.LORRY)
					{
						return node.getLargeToll();
					}
				}
			}
			
		}
		return 0;
	}

}
