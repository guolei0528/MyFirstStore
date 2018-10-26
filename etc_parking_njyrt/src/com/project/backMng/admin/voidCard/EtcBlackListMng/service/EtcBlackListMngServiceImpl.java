package com.project.backMng.admin.voidCard.EtcBlackListMng.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.admin.voidCard.EtcBlackListMng.action.EtcBlackListMngAction;
import com.project.backMng.admin.voidCard.EtcBlackListMng.model.EtcBlackListBean;
import com.project.backMng.admin.voidCard.EtcVoidCardMng.model.EtcVoidCardBean;
import com.project.tools.RedisConst;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;
import com.redoak.jar.util.OakRedisUtil;

public class EtcBlackListMngServiceImpl extends BaseService implements EtcBlackListMngService {

	private final static Logger log = LogManager.getLogger(EtcBlackListMngServiceImpl.class);

	private final static String[] provinces = { "32", "11", "12", "13", "14", "21", "22", "31", "33", "34", "35",
			"36", "37", "41", "42", "43", "44", "50", "51", "52", "53", "61", "62", "63", "64", "15", "23", "45",
			"65" };

	public EtcBlackListMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.voidCard.EtcBlackListMng");
		// TODO Auto-generated constructor stub
	}

	/**
	 * 查询etc黑名单列表
	 */
	@Override
	public List<EtcBlackListBean> findList(ObjectMap queryParam, Page page) {
		// page.setRecordCount(super.findInteger(ns("findCount"), queryParam));
		// Date startTime = new Date();
		int etcCount = 0;
		
		queryParam.put("skipSize",(page.getCurrentPage()-1)*page.getPageSize());
		queryParam.put("pageSize", (page.getCurrentPage())*page.getPageSize()-1);
		// 判断是否有选择省份查询
		if (queryParam.getStr("province") == null || "0".equals(queryParam.getStr("province").trim())) {
			// 遍历所有省份
			for (int i = 0; i < provinces.length; i++) {
				queryParam.put("province", provinces[i]);
				etcCount = etcCount + super.findInteger(ns("findCount"), queryParam);
			}
			page.setRecordCount(etcCount);

			// 判断其他条件是否都为空
			if ((queryParam.getStr("query_CardID") == null || "".equals(queryParam.getStr("query_CardID").trim()))
					&& (queryParam.getStr("query_License") == null
							|| "".equals(queryParam.getStr("query_License").trim()))
					&& (queryParam.getStr("query_OBUID") == null
							|| "".equals(queryParam.getStr("query_OBUID").trim()))) {
				// 只查询第一个省份
				queryParam.put("province", provinces[0]);
				return super.findList(ns("findList"), queryParam, page);
			}
			// 有其他条件时，遍历所有表查询出结果返回
			else {
				List<EtcBlackListBean> beans = null;
				// 遍历所有省份
				for (int i = 0; i < provinces.length; i++) {
					queryParam.put("province", provinces[i]);
					beans = super.findList(ns("findList"), queryParam, page);
					if (beans != null && beans.size() != 0) {
						return beans;
					}
				}
				return beans;

			}

		} else {
			// 根据条件查省份
			etcCount = etcCount + super.findInteger(ns("findCount"), queryParam);
		}

		// Date countTime = new Date();
		// log.info("etc黑名单count时间："+(countTime.getTime()-startTime.getTime()));
		page.setRecordCount(etcCount);
		List<EtcBlackListBean> beans = super.findList(ns("findList"), queryParam, page);
		// log.info("etc黑名单count时间后到查询时间："+(System.currentTimeMillis()-countTime.getTime()));
		return beans;
	}

	/**
	 * 加载etc黑名单
	 */
	@Override
	public void synchronizeList() {
		int i = 1;
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("pageSize", 100);
		// 车牌加obuid的字符串
		StringBuilder sb = new StringBuilder();
		while (true) {
			objectMap.put("skipSize", 100 * (i - 1));
			List<EtcBlackListBean> tempList = super.findList(ns("findEtcBlackListByPageSkipSize"), objectMap);
			if (tempList == null || tempList.size() == 0) {
				break;
			} else {

				for (EtcBlackListBean temp : tempList) {

					if (temp.getLicense() != null && !"".equals(temp.getLicense())) {
						sb.append(temp.getLicense());
					}

					sb.append("|");

					if (temp.getObuID() != null && !"".equals(temp.getObuID())) {
						sb.append(temp.getObuID());
					}

					OakRedisUtil.setObject((RedisConst.voidCard.ETC + ":" + sb.toString()), temp);
					sb.delete(0, sb.length());
				}
				i++;
			}
		}
	}

	@Override
	public void findEtcList(ObjectMap queryParam) {
		ObjectMap query1 = ObjectMap.newInstance();
		query1.put("query_License", queryParam.get("query_License"));
		ObjectMap query2 = ObjectMap.newInstance();
		query2.put("query_CardID", queryParam.get("query_CardID"));
		ObjectMap query3 = ObjectMap.newInstance();
		query3.put("query_OBUID", queryParam.get("query_OBUID"));

		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		logger.info(start + "查询ETC黑名单数据库开始时间");
		int count = super.findInteger(ns("findEtcList"), queryParam);
		// int count2 = super.findInteger(ns("findEtcList"), query2);
		// int count3 = super.findInteger(ns("findEtcList"), query3);
		long end = System.currentTimeMillis();
		logger.info(end + "查询ETC黑名单数据库结束时间" + ",count1=" + count);
		// logger.info(end+"查询ETC黑名单数据库结束时间"+",count1="+count1+"count2="+count2+"count3="+count3);
		logger.info(end - start + "查询使用时间");
	}
}
