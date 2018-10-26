package com.project.backMng.admin.ChargeRuleMng.config.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.project.tools.DateUtil;

public class SpecTLChargeRuleBean {

	private static int FREE_COUNT = 5;

	/**
	 * 
	 * @Title : calculateToll
	 * @功能描述: 计算费用
	 * @传入参数：@param startTime
	 * @传入参数：@param endTime
	 * @传入参数：@param carType
	 * @传入参数：@param count
	 * @传入参数：@return
	 * @返回类型：long
	 * @throws ：
	 */
	public Map calculateToll(Date startTime, Date endTime, int carType, int count) {
		Map map = new HashMap();
		long toll = 0l;
		/*Date zeroTime = DateUtil.parse4Hms("00:00:00");
		Date sixTime = DateUtil.parse4Hms("06:00:00");
		Date fiveTime = DateUtil.parse4Hms("05:00:00");

		Date startHms = DateUtil.parse4Hms(DateUtil.get4Hms(startTime));
		Date startYmd = DateUtil.parse4yMd(DateUtil.get4yMd(startTime));
		Date endHms = DateUtil.parse4Hms(DateUtil.get4Hms(endTime));
		Date endYmd = DateUtil.parse4yMd(DateUtil.get4yMd(endTime));*/

		// 判断停了几天
		int day = getBetweenDay(startTime, endTime);

		for (;day > -1; day--) {
			if (day > 0) {
				Date dtTmp = new Date(DateUtil.parse4yMd(DateUtil.get4yMd(startTime)).getTime() + 86400000);
				toll += onceToll(startTime, dtTmp);
			} else {
				toll += onceToll(startTime, endTime);
			}

			startTime = new Date(DateUtil.parse4yMd(DateUtil.get4yMd(startTime)).getTime() + 86400000);

			if (count < FREE_COUNT && toll > 0) {
				count++;
				toll = 0;
			}
		}

		map.put("count", count);
		map.put("toll", toll);
		return map;

		// 判断开始时间是否大于5点
		/*if (startHms.getTime() > fiveTime.getTime()) {
			startTime = new Date(startYmd.getTime() + 86400000);

			startHms = DateUtil.parse4Hms(DateUtil.get4Hms(startTime));
			startYmd = DateUtil.parse4yMd(DateUtil.get4yMd(startTime));
			// 判断入口时间是否大于出口时间
			if (startTime.getTime() > endTime.getTime()) {
				map.put("count", count);
				map.put("toll", toll);
				return map;
			}

		}

		// 判断开始时间是否在0点至6点中
		if (startHms.getTime() >= zeroTime.getTime() && startHms.getTime() < sixTime.getTime()) {
			// 判断入口时间和出口时间都在当天
			if (endYmd.getTime() == startYmd.getTime()) {
				// 判断出口是否大于6点
				if (endHms.getTime() >= sixTime.getTime()) {
					// 判断是否小于5次
					if (count < FREE_COUNT) {
						// 判断开始时间是否比5点小，小则加一
						if (startTime
								.compareTo(DateUtil.parse4yMdHms(DateUtil.get4yMd(startTime) + " 05:00:00")) == -1) {
							count++;
						}
					} else {
						Date calEndTime = DateUtil.parse4yMdHms(DateUtil.get4yMd(startTime) + " 06:00:00");
						// 判断是否6点比结束时间小
						if (calEndTime.getTime() < endTime.getTime()) {
							toll += onceToll(startTime, calEndTime);
						} else {
							toll += onceToll(startTime, endTime);
						}
					}
				} else {
					// 判断是否小于5次
					if (count < FREE_COUNT) {
						count++;
					} else {

						toll = onceToll(startTime, endTime);
					}
				}
				// map.put("count", count);
				// map.put("toll", toll);
				// return 0;
			} else {
				// 增加到第二天0点
				// Calendar cal = Calendar.getInstance();
				// cal.setTime(startYmd);
				// cal.add(Calendar.DATE,1);
				// startTime = cal.getTime();
				int day = getBetweenDay(startTime, endTime);
				// 判断开始时间是否小于6点，如果小于则累计加1
				// if(startTime.compareTo(DateUtil.parse4yMdHms(DateUtil.get4yMd(startTime)+"
				// 05:00:00")) == 1)
				// {
				// day--;
				// }
				// if(count < FREE_COUNT)
				// {
				// 判断相差的天数是否小于可免费次数
				if (day < (FREE_COUNT - count)) {
					// 免费次数减少
					count = count + day;
					// 收费金额为0
					toll = 0;
				}
				// 判断相差的天数是否大于免费时间
				else {
					// 免费次数减少
					day = FREE_COUNT - count;
					count = FREE_COUNT;
					if (day > 0) {
						// if(startTime.compareTo(DateUtil.parse4yMdHms(DateUtil.get4yMd(startTime)+"
						// 05:00:00")) == 1)
						// {
						// day++;
						// }
						startTime = new Date(startYmd.getTime() + day * 86400000);
					}

					// 获得减去免费时长后的天数
					// int compare = getBetweenDay(startTime,endTime);

					// 迭代循环是否入口时间大于出口时间
					while (startTime.getTime() < endTime.getTime()) {
						Date calEndTime = DateUtil.parse4yMdHms(DateUtil.get4yMd(startTime) + " 06:00:00");
						// 判断是否6点比结束时间小
						if (calEndTime.getTime() < endTime.getTime()) {
							toll += onceToll(startTime, calEndTime);
						} else {
							toll += onceToll(startTime, endTime);
						}
						startTime = DateUtil.parse4yMd(DateUtil.get4yMd(startTime));
						// startYmd =
						// DateUtil.parse4yMd(DateUtil.get4yMd(startTime));
						Calendar cal = Calendar.getInstance();
						cal.setTime(startTime);
						cal.add(Calendar.DATE, 1);
						startTime = cal.getTime();
					}

				}
				// }

			}
		}
		map.put("count", count);
		map.put("toll", toll);
		return map;*/
	}

	/**
	 * 
	 * @Title : onceToll
	 * @功能描述: 计算单次的费用
	 * @传入参数：@return
	 * @返回类型：int
	 * @throws ：
	 */
	private long onceToll(Date startTime, Date endTime) {
		long toll = 0l;

		// 判断入口时间是否大于5点
		if (startTime.getTime() >= DateUtil.parse4yMdHms(DateUtil.get4yMd(startTime) + " 05:00:00").getTime())
			return toll;

		// 判断出口时间是否大于6点，如果大于6点重置为6点
		if (endTime.getTime() > DateUtil.parse4yMdHms(DateUtil.get4yMd(startTime) + " 06:00:00").getTime())
			endTime = DateUtil.parse4yMdHms(DateUtil.get4yMd(startTime) + " 06:00:00");

		// 如果停车时长小于1小时
		startTime = new Date(startTime.getTime() + 3600000);
		if (compareTime(startTime, endTime))
			return toll;

		// 如果停车时间大于1小时，小于2小时
		startTime = new Date(startTime.getTime() + 3600000);
		toll += 600;
		if (compareTime(startTime, endTime))
			return toll;

		// 停车超过2小时
		// 迭代循环计费
		do {
			startTime = new Date(startTime.getTime() + 1800000);
			toll += 200;
		} while (!compareTime(startTime, endTime));
		if (toll > 2000) {
			return 2000;
		}
		return toll;
	}

	/**
	 * 
	 * @Title : compareTime
	 * @功能描述: TODO
	 * @传入参数：@param startTime
	 * @传入参数：@param endTime
	 * @传入参数：@return
	 * @返回类型：boolean
	 * @throws ：
	 */
	private boolean compareTime(Date startTime, Date endTime) {
		// 判断入口时间是否大于出口时间
		if (startTime.getTime() >= endTime.getTime()) {
			return true;
		}

		return false;
	}

	// 判断相差几天
	public int getBetweenDay(Date date1, Date date2) {
		Calendar d1 = new GregorianCalendar();
		d1.setTime(date1);
		Calendar d2 = new GregorianCalendar();
		d2.setTime(date2);
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			// d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}
}
