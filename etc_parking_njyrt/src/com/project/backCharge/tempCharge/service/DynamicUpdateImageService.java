package com.project.backCharge.tempCharge.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backCharge.tempCharge.model.ChargeEntryBean;
import com.project.backCharge.tempCharge.model.TempCostBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.mybatis.service.BaseService;

public class DynamicUpdateImageService extends BaseService {

	private final static Logger log = LogManager.getLogger(DynamicUpdateImageService.class);


	public DynamicUpdateImageService() {
		super();
		super.setIBATIS_NAME_SPACE("backCharge.tempCharge.tempCost");
	}

	public void init() {
		// 定时任务
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
//						process();
						//涟水县计费规则（写死）
						updateImageList();
//						System.gc();
						// 60秒循环一次
						Thread.sleep(60000);
					} catch (Exception e) {
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
		}).start();
	}


	/**
	 * 
	 * @Title : processType
	 * @功能描述: 第二种计费规则（写死）
	 * @传入参数：@throws ParseException
	 * @返回类型：void
	 * @throws ：
	 */
	private void updateImageList() throws ParseException {
		//  获得临时表中车辆数据
		List<TempCostBean> tempCosts = findTempCostListByBeforeTime();

		// 迭代停车场车辆
		for (TempCostBean tempCost : tempCosts) {
			// 更新图片
			updateImage(tempCost);
		}
		tempCosts = null;
	}
	
	
	/**
	 * 
	 * @Title : findTempCostList
	 * @功能描述: TODO
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	public List<TempCostBean> findTempCostListByBeforeTime() {
		// TODO Auto-generated method stub
		return super.findList(ns("findTempCostListByBeforeTime"));
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
		 if(((now.getTime()) - entryDate.getTime()) >= 300000 )
		 {
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
		log.info("查找车牌"+mvLicense);
		ChargeEntryBean chargeEntryBean = null;
		
		if(chargeEntryBeans != null && chargeEntryBeans.size() != 0)
		{
			log.info("车牌数量"+chargeEntryBeans.size());
			chargeEntryBean = chargeEntryBeans.get(chargeEntryBeans.size()-1);
		}
//		ChargeEntryBean chargeEntryBean = findObj("backCharge.tempCharge.chargeEntry.findEntryImage", map)-;
		
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
