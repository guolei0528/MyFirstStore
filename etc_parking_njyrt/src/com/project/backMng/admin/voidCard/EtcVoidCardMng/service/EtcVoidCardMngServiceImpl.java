package com.project.backMng.admin.voidCard.EtcVoidCardMng.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.admin.voidCard.EtcVoidCardMng.model.EtcVoidCardBean;
import com.project.backMng.admin.voidCard.ParkVoidCardMng.model.ParkVoidCardBean;
import com.project.backMng.admin.voidCard.ParkVoidCardMng.service.ParkVoidCardMngServiceImpl;
import com.project.tools.RedisConst;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.redoak.jar.base.mybatis.service.BaseService;
import com.redoak.jar.util.OakRedisUtil;

import redis.clients.jedis.Jedis;

public class EtcVoidCardMngServiceImpl extends BaseService implements EtcVoidCardMngService{

	private final static Logger log=LogManager.getLogger(EtcVoidCardMngServiceImpl.class);


	public EtcVoidCardMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.voidCard.EtcVoidCardMng");
		// TODO Auto-generated constructor stub
	}

	public List<EtcVoidCardBean> findList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		return super.findList(ns("findList"),queryParam,page);
	}

	public EtcVoidCardBean findInfo(String card,String card_network) {
		// TODO Auto-generated method stub
		ObjectMap map=ObjectMap.newInstance();
		map.put("card_id", card);
		map.put("card_network", card_network);
		
		return super.findObj(ns("findInfo"),map);
	}

	public void save(EtcVoidCardBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
//		bean.setID(UuidGenerator.generateUUID());
		super.insert(ns("insert"), bean);
		updateCache(bean,"insert");
	}

	public void update(EtcVoidCardBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("update"), bean);
		updateCache(bean,"update");
	}

	public void delete(String id,SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("delete"), id);
	}
	
/*	public void synchronizeList(){
		int i=1;
		ObjectMap objectMap=ObjectMap.newInstance();
		List<EtcVoidCardBean> list=new ArrayList<EtcVoidCardBean>();
		objectMap.put("pageSize", 500);
		while(true){
			objectMap.put("skipSize", 500*(i-1));
			List<EtcVoidCardBean> tempList = super.findList(ns("synchronizeList"),objectMap);
			if(tempList==null || tempList.size()==0){
				break;
			}else{
				list.addAll(tempList);
				i++;
			}
		}
		OakRedisUtil.setObject(RedisConst.voidCard.ETC, list);
	}*/
	
	@Override
	public void synchronizeList() {
		int i=1;
		ObjectMap objectMap=ObjectMap.newInstance();
		objectMap.put("pageSize", 100);
		while(true){
			objectMap.put("skipSize", 100*(i-1));
			List<EtcVoidCardBean> tempList = super.findList(ns("findEtcVoidByPageSkipSize"),objectMap);
			if(tempList==null || tempList.size()==0){
				break;
			}else{
				for(EtcVoidCardBean temp:tempList)
				{
					OakRedisUtil.setObject((RedisConst.voidCard.ETC+":"+temp.getcard_id()+"|"+temp.getmv_license()), 
							temp);
				}
				i++;
			}
		}
//		EtcVoidCardBean txb = OakRedisUtil.getObject(RedisConst.voidCard.TXB+":苏A10001"+"|0");
//		System.out.println(txb.getmv_license()+"|"+txb.getPlate_color());
//		Set<String> existsFlag = OakRedisUtil.getKeys(RedisConst.voidCard.ETC+":1*");
//		Set<String> existsFlag2 = OakRedisUtil.getKeys(RedisConst.voidCard.ETC+":11325122*");
//		System.out.println(existsFlag.size()+"--"+existsFlag);
//		System.out.println(existsFlag2.size()+"--"+existsFlag2);
	}
	
	/**
	 * 
	 * @Title : synchronizeList
	 * @功能描述: 序列化数据 @传入参数： @返回值：
	 * @throws ：
	 */
	/*@Override
	public void synchronizeList() {
		int i = 1;
		boolean flag = false;
		//生成新的list对象，避免for循环中重复new
		List<EtcVoidCardBean> etcVoidCards = new ArrayList<EtcVoidCardBean>();
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("pageSize", 100);
		while (true) {
			objectMap.put("skipSize", 100 * (i - 1));
			List<EtcVoidCardBean> tempList = super.findList(ns("findEtcVoidByPageSkipSize"), objectMap);
			if (tempList == null || tempList.size() == 0) {
				break;
			} else {
				for (EtcVoidCardBean temp : tempList) {
					List<EtcVoidCardBean> etcVoidCardBeans = (List<EtcVoidCardBean>) SerializeUtil
							.unserialize((byte[]) OakRedisUtil.getObject((RedisConst.voidCard.ETC + ":"
									+ temp.getmv_license() + "|" + temp.getmv_license_colour())));
					//判断获取的数据库数据是否为空
					if(etcVoidCardBeans == null)
					{
						etcVoidCards.clear();
						etcVoidCardBeans = etcVoidCards;
					}
					for (EtcVoidCardBean bean : etcVoidCardBeans) {
						if (temp.getintime().equals(bean.getintime())
								&& temp.getcancel_time().equals(bean.getcancel_time())) {
							flag = true;
							break;
						} 
					}
					if(!flag)
					{
						etcVoidCardBeans.add(temp);
						OakRedisUtil.setObject(
								(RedisConst.voidCard.ETC + ":" + temp.getmv_license() + "|" + temp.getmv_license_colour()),
								SerializeUtil.serialize(etcVoidCardBeans));
					}
					flag = false;
//					OakRedisUtil.setObject(
//							(RedisConst.voidCard.TXB + ":" + temp.getmv_license() + "|" + temp.getPlate_color()),
//							SerializeUtil.serialize(temp));
				}
				i++;
			}
		}
		// log.info("通行宝value--------"+OakRedisUtil.getObject("TXB_VOID_CARD:苏A00001|0"));
		// TxbVoidCardBean aa=
		// (TxbVoidCardBean)SerializeUtil.unserialize((byte[])OakRedisUtil.getObject("TXB_VOID_CARD:苏A00001|0"));
		// log.info("aa--------"+aa.getmv_license()+"|"+aa.getPlate_color());
		// OakRedisUtil.setObject(RedisConst.voidCard.TXB, list);

	}*/
	
	/**
	 * 
	   * @Title : updateCache 
	   * @功能描述: 更新redis缓存数据
	   * @传入参数：@param bean
	   * @传入参数：@param nameSpace
	   * @返回类型：void 
	   * @throws ：
	 */
	public void updateCache(EtcVoidCardBean bean, String nameSpace) {
		//增加
		if ("insert".equals(nameSpace)) {
			OakRedisUtil.setObject(
					(RedisConst.voidCard.ETC + ":" + bean.getcard_id() + "|" + bean.getmv_license()),
					bean);
			log.info("etcVoid.insertCache");
		}
		//修改
		if ("update".equals(nameSpace)) {
			OakRedisUtil.setObject(
					(RedisConst.voidCard.ETC + ":" + bean.getcard_id() + "|" + bean.getmv_license()),
					bean);
			log.info("etcVoid.updateCache");
			
		}
		//删除
		if ("delete".equals(nameSpace)) {
			OakRedisUtil.delete(RedisConst.voidCard.ETC + ":" + bean.getcard_id() + "|" + bean.getmv_license());
			log.info("etcVoid.deleteCache");
		}
	}
	
	private final static int num = 7000000;
	
	@Override
	public void insertETC() {
		
//		Jedis jedis = new Jedis("localhost",6379);
//		jedis.auth("itsmoe");
//		log.info("insertETC");
//		
//		Date dstart = new Date();
//		try{
//			for(int i=0;i<=num ;i++)
//			{
//				
//				if(i == 5000)
//				{
//						Thread.sleep(1000);
//				}
//				
//				log.info(i);
//				jedis.set(RedisConst.voidCard.ETC+":"+System.currentTimeMillis()+i, "value"+System.currentTimeMillis()+i);
//			}
//			Date dend = new Date();
//			System.out.println("使用时间（毫秒）："+(dstart.getTime()-dend.getTime()));
//		}
//		catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		finally
//		{
//			jedis.close();
//		}
		
		int i=0;
		Date dstart = new Date();
		for(;i<=num ;i++)
		{
		
//				Thread.sleep(2);
//			log.info(i);
			EtcVoidCardBean bean = new EtcVoidCardBean();
			bean.setcard_id("卡"+System.currentTimeMillis()+i);
			bean.setmv_license("苏"+System.currentTimeMillis()+i);
			bean.setcard_type("卡类型"+System.currentTimeMillis());
			bean.setcard_network("网络编号"+System.currentTimeMillis());
			bean.setcard_status("状态类型"+System.currentTimeMillis());
			bean.setissuer_id("发行方id"+System.currentTimeMillis());
			bean.setelectronic_id("电子id"+System.currentTimeMillis());
			bean.setmv_license_colour("颜色"+System.currentTimeMillis());
			bean.setissuer_version("发行版本"+System.currentTimeMillis());
			bean.setban_type("ban"+System.currentTimeMillis());
			bean.setissuer_effective_time(""+System.currentTimeMillis());
			bean.setvalid_flag("校验类型"+System.currentTimeMillis());
			bean.setvalid_time(""+System.currentTimeMillis());
			bean.setlock_card("锁卡"+System.currentTimeMillis());
			bean.setcontact_info("连接信息"+System.currentTimeMillis());
			bean.sets_comment("备注"+System.currentTimeMillis());
			bean.setversion("版本"+System.currentTimeMillis());
			
//			log.info(RedisConst.voidCard.ETC+":"+bean.getcard_id()+"|"+bean.getmv_license());
			OakRedisUtil.setObject((RedisConst.voidCard.ETC+":"+bean.getcard_id()+"|"+bean.getmv_license()), 
					bean);
				
		}
		Date dend = new Date();
		System.out.println("使用时间（毫秒）："+(dend.getTime()-dstart.getTime())/1000/60);
//		log.info("数据插入完成！！");
//		Date dstart = new Date();
//		OakRedisUtil.setMapObject(maps);
//		Date dend = new Date();
////		System.out.println("i====="+i);
//		System.out.println("使用时间（毫秒）："+(dstart.getTime()-dend.getTime()));
		
		
		
//		long s = System.currentTimeMillis();
		
//		Set<String> count = OakRedisUtil.getKeys((RedisConst.voidCard.ETC+":*"+"1000000"));
//		long e = System.currentTimeMillis();
//		System.out.println("开始时间（毫秒）："+s+"-----"+count);
//		System.out.println("结束时间（毫秒）："+e+"-----"+count);
//		System.out.println("使用时间（毫秒）："+(e-s)+"-----"+count);
	}
	
	@Override
	public void testETC(String card) {
//		long s = System.currentTimeMillis();
//		Set<String> count = OakRedisUtil.getKeys((RedisConst.voidCard.ETC+":*"+"1000000"));
//		long e = System.currentTimeMillis();
//		System.out.println("getKeys开始时间（毫秒）："+s+"-----"+count);
//		System.out.println("getKeys结束时间（毫秒）："+e+"-----"+count);
//		System.out.println("getKeys使用时间（毫秒）："+(e-s)+"-----"+count);
		
//		long s1 = System.currentTimeMillis();
//		EtcVoidCardBean etcVoidCardBean = OakRedisUtil.getObject(card);
//		long e1 = System.currentTimeMillis();
//		System.out.println("getObject开始时间（毫秒）："+s1+"-----"+(etcVoidCardBean != null?etcVoidCardBean.getmv_license():null));
//		System.out.println("getObject结束时间（毫秒）："+e1+"-----"+(etcVoidCardBean != null?etcVoidCardBean.getmv_license():null));
//		System.out.println("getObject使用时间（毫秒）："+(e1-s1)+"-----"+(etcVoidCardBean != null?etcVoidCardBean.getmv_license():null));
		
		
		long s1 = System.currentTimeMillis();
		boolean flag = OakRedisUtil.exists(card);
		long e1 = System.currentTimeMillis();
		System.out.println("getObject开始时间（毫秒）："+s1+"-----"+flag);
		System.out.println("getObject结束时间（毫秒）："+e1+"-----"+flag);
		System.out.println("getObject使用时间（毫秒）："+(e1-s1)+"-----"+flag);

	}
}