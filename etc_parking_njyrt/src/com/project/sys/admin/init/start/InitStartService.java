package com.project.sys.admin.init.start;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.project.backMng.admin.voidCard.EtcBlackListMng.service.EtcBlackListMngService;
import com.project.backMng.admin.voidCard.EtcVoidCardMng.service.EtcVoidCardMngService;
import com.project.backMng.admin.voidCard.ParkVoidCardMng.service.ParkVoidCardMngService;
import com.project.backMng.admin.voidCard.PoliceVoidCardMng.service.PoliceVoidCardMngService;
import com.project.backMng.admin.voidCard.TxbVoidCardMng.service.TxbVoidCardMngService;
import com.project.sys.admin.init.start.model.ParamTimeBean;
import com.project.tools.DateUtil;
import com.project.tools.RedisConst;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.mybatis.service.BaseService;
import com.redoak.jar.tag.model.ColEnum;
import com.redoak.jar.util.ColEnumUtil;
import com.redoak.jar.util.OakRedisUtil;
import com.redoak.jar.util.PropertiesUtil;

public class InitStartService extends BaseService{
	private TxbVoidCardMngService txbVoidCardMngService;
	private PoliceVoidCardMngService policeVoidCardMngService;
	private ParkVoidCardMngService parkVoidCardMngService;
	private EtcVoidCardMngService etcVoidCardMngService;
	private EtcBlackListMngService etcBlackListMngService;
	
	private final static int PARK_VOID_CARD_CODE = 1001;
	private final static int POLICE_VOID_CARD_CODE = 1002;
	private final static int TXB_VOID_CARD_CODE = 1003;
	private final static int ETC_VOID_CARD_CODE = 52;
	
	public EtcVoidCardMngService getEtcVoidCardMngService() {
		return etcVoidCardMngService;
	}

	public void setEtcVoidCardMngService(EtcVoidCardMngService etcVoidCardMngService) {
		this.etcVoidCardMngService = etcVoidCardMngService;
	}

	public ParkVoidCardMngService getParkVoidCardMngService() {
		return parkVoidCardMngService;
	}

	public void setParkVoidCardMngService(ParkVoidCardMngService parkVoidCardMngService) {
		this.parkVoidCardMngService = parkVoidCardMngService;
	}

	public PoliceVoidCardMngService getPoliceVoidCardMngService() {
		return policeVoidCardMngService;
	}

	public void setPoliceVoidCardMngService(PoliceVoidCardMngService policeVoidCardMngService) {
		this.policeVoidCardMngService = policeVoidCardMngService;
	}

	public TxbVoidCardMngService getTxbVoidCardMngService() {
		return txbVoidCardMngService;
	}

	public void setTxbVoidCardMngService(TxbVoidCardMngService txbVoidCardMngService) {
		this.txbVoidCardMngService = txbVoidCardMngService;
	}

	public EtcBlackListMngService getEtcBlackListMngService() {
		return etcBlackListMngService;
	}

	public void setEtcBlackListMngService(EtcBlackListMngService etcBlackListMngService) {
		this.etcBlackListMngService = etcBlackListMngService;
	}

	public InitStartService(){
		super();
		super.setIBATIS_NAME_SPACE("sys.init.InitStart");
	}
	
	
	public void init(){
		
//		super.insert(ns("insertServerLogs"),null);
		//加载枚举值的信息
		List<ColEnum> colList=super.findList(ns("selectColEnum"));
		if(colList!=null){
			for(ColEnum c:colList){
				ColEnumUtil.addData(c);
			}
			ColEnumUtil.finishLoad();
		}
		//加载PROPERTIES的信息
		List<ObjectMap> list=super.findList(ns("selectProps"));
		if(list!=null){
			for(ObjectMap prop:list){
				PropertiesUtil.put(prop.getStr("PROP_KEY"),prop.getStr("PROP_VALUE"));
			}
		}
		
		insertStartServer();
		//定时任务
		/**new Thread(new Runnable() {
			public void run() {
				
				while(true){
					try {
//						process();
						logger.info("运行系统记录:"+DateUtil.get4yMdHms(new Date()));
						//60秒循环一次
						Thread.sleep(60000);
				} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error(e.getMessage());
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} 
			}
		}).start();**/
		
		/**new Thread(new Runnable() {
			
			@Override
			public void run() {
//				txbVoidCardMngService.synchronizeList();
//				policeVoidCardMngService.synchronizeList();
//				parkVoidCardMngService.synchronizeList();
//				etcVoidCardMngService.synchronizeList();
//				etcBlackListMngService.synchronizeList();
//				etcVoidCardMngService.insertETC();
//				etcVoidCardMngService.testETC(card);
//				initWhite();
			}
		}).start(); */
	}
	
	/**
	 * 
	   * @Title : insertStartServer 
	   * @功能描述: 记录启动服务
	   * @传入参数：
	   * @返回类型：void 
	   * @throws ：
	 */
	private void insertStartServer()
	{
		logger.info("记录启动服务:"+DateUtil.get4yMdHms(new Date()));
		super.insert(ns("insertServerLogs"),null);
	}
	
	private void process(){
		//定时任务需要处理的操作
//		System.out.println(DateUtil.get4yMdHms(new Date(System.currentTimeMillis())));
		//检查黑名单是否有更新
		List<ParamTimeBean> paramTimeBeans = super.findList(ns("checkParamTimeVoidCard"));
		if(paramTimeBeans == null || paramTimeBeans.size() ==  0)
		{
			return;
		}
		
		//遍历下发表检查黑名单是否更新，检查版本号
		for(ParamTimeBean paramTimeBean:paramTimeBeans)
		{
			//本地黑名单
			if(paramTimeBean.getPara_type() == PARK_VOID_CARD_CODE)
			{
				//批量模糊删除黑名单数据
				Set<String> keys = OakRedisUtil.getKeys(RedisConst.voidCard.PARK + "*");
				OakRedisUtil.deleteAll(keys);
				parkVoidCardMngService.synchronizeList();
			}
			
			//公安黑名单
			if(paramTimeBean.getPara_type() == POLICE_VOID_CARD_CODE)
			{
				//批量模糊删除黑名单数据
				Set<String> keys = OakRedisUtil.getKeys(RedisConst.voidCard.POLICE + "*");
				OakRedisUtil.deleteAll(keys);
				policeVoidCardMngService.synchronizeList();
			}
			
			//通行宝黑名单
			if(paramTimeBean.getPara_type() == TXB_VOID_CARD_CODE)
			{
				//批量模糊删除黑名单数据
				Set<String> keys = OakRedisUtil.getKeys(RedisConst.voidCard.TXB + "*");
				OakRedisUtil.deleteAll(keys);
				txbVoidCardMngService.synchronizeList();
			}
			
			//通行宝黑名单
			if(paramTimeBean.getPara_type() == ETC_VOID_CARD_CODE)
			{
				//批量模糊删除黑名单数据
				Set<String> keys = OakRedisUtil.getKeys(RedisConst.voidCard.TXB + "*");
				OakRedisUtil.deleteAll(keys);
				etcVoidCardMngService.synchronizeList();
				
			}
		}
		
//		OakRedisUtil.delete(keys);
		
		
	}
	
	public void initWhite(){
		List<String> list = super.findList(ns("validWhiteList"));
		if(list==null){
			list=new ArrayList<String>();
		}
		OakRedisUtil.setObject(RedisConst.voidCard.WHITE_LIST, list);
	}
}
