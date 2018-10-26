package com.project.backMng.admin.voidCard.ParkVoidCardMng.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.admin.voidCard.ParkVoidCardMng.model.ParkVoidCardBean;
import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.project.backMng.platuser.sys.ParkInfoMng.model.ParkInfoBean;
import com.project.tools.RedisConst;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.redoak.jar.base.mybatis.service.BaseService;
import com.redoak.jar.util.OakRedisUtil;

public class ParkVoidCardMngServiceImpl extends BaseService implements ParkVoidCardMngService{

	private final static Logger log=LogManager.getLogger(ParkVoidCardMngServiceImpl.class);

	public ParkVoidCardMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.voidCard.ParkVoidCardMng");
		// TODO Auto-generated constructor stub
	}

	public List<ParkVoidCardBean> findList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		if(page==null){
			return super.findList(ns("findList"),queryParam);
		}
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		return super.findList(ns("findList"),queryParam,page);
	}

	public ParkVoidCardBean findInfo(String mv_license,String plate_color) {
		// TODO Auto-generated method stub
		ObjectMap map=ObjectMap.newInstance();
		map.put("mv_license", mv_license);
		map.put("plate_color", plate_color);
		return super.findObj(ns("findInfo"),map);
	}

	public void save(ParkVoidCardBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
//		bean.setID(UuidGenerator.generateUUID());
		super.insert(ns("insert"), bean);
		updateCache(bean,"insert");
	}

	public void update(ParkVoidCardBean bean, SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("update"), bean);
		updateCache(bean,"update");
	}

	public void delete(ObjectMap map,SysLoginUserBean userBean) {
		// TODO Auto-generated method stub
		super.update(ns("delete"), map);
		ParkVoidCardBean bean=new ParkVoidCardBean();
		bean.setmv_license(map.getStr("mv_license"));
		bean.setPlate_color(map.getStr("plate_color"));
		updateCache(bean, "delete");
	}

	@Override
	public List<ParkInfoBean> findParkList() {
		// TODO Auto-generated method stub
		return super.findList(ns("findParkList"));
	}

	@Override
	public List<AreaInfoBean> findAreaList(String park_id) {
		// TODO Auto-generated method stub
		return super.findList(ns("findAreaList"),park_id);
	}
	
	/*public void synchronizeList(){
		int i=1;
		ObjectMap objectMap=ObjectMap.newInstance();
		List<String> list=new ArrayList<String>();
		objectMap.put("pageSize", 500);
		while(true){
			objectMap.put("skipSize", 500*(i-1));
			List<String> tempList = super.findList(ns("synchronizeList"),objectMap);
			if(tempList==null || tempList.size()==0){
				break;
			}else{
				list.addAll(tempList);
				i++;
			}
		}
		OakRedisUtil.setObject(RedisConst.voidCard.PARK, list);
	}*/
	
	@Override
	public void synchronizeList() {
		int i=1;
		ObjectMap objectMap=ObjectMap.newInstance();
		objectMap.put("pageSize", 100);
		while(true){
			objectMap.put("skipSize", 100*(i-1));
			List<ParkVoidCardBean> tempList = super.findList(ns("findParkVoidByPageSkipSize"),objectMap);
			if(tempList==null || tempList.size()==0){
				break;
			}else{
				for(ParkVoidCardBean temp:tempList)
				{
					OakRedisUtil.setObject((RedisConst.voidCard.PARK+":"+temp.getmv_license()+"|"+temp.getPlate_color()), 
							temp);
				}
				i++;
			}
		}
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
		List<ParkVoidCardBean> parkVoidCards = new ArrayList<ParkVoidCardBean>();
		ObjectMap objectMap = ObjectMap.newInstance();
		objectMap.put("pageSize", 100);
		while (true) {
			objectMap.put("skipSize", 100 * (i - 1));
			List<ParkVoidCardBean> tempList = super.findList(ns("findParkVoidByPageSkipSize"), objectMap);
			if (tempList == null || tempList.size() == 0) {
				break;
			} else {
				for (ParkVoidCardBean temp : tempList) {
					List<ParkVoidCardBean> parkVoidCardBeans = (List<ParkVoidCardBean>) SerializeUtil
							.unserialize((byte[]) OakRedisUtil.getObject((RedisConst.voidCard.PARK + ":"
									+ temp.getmv_license() + "|" + temp.getPlate_color())));
					//判断获取的数据库数据是否为空
					if(parkVoidCardBeans == null)
					{
						parkVoidCards.clear();
						parkVoidCardBeans = parkVoidCards;
					}
					for (ParkVoidCardBean bean : parkVoidCardBeans) {
						if (temp.getin_time().equals(bean.getin_time())
								&& temp.getcancel_time().equals(bean.getcancel_time())) {
							flag = true;
							break;
						} 
					}
					if(!flag)
					{
						parkVoidCardBeans.add(temp);
						OakRedisUtil.setObject(
								(RedisConst.voidCard.PARK + ":" + temp.getmv_license() + "|" + temp.getPlate_color()),
								SerializeUtil.serialize(parkVoidCardBeans));
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
	
	
	
	/*
	public void updateCache(ParkVoidCardBean bean,String nameSpace){
		List <String> list= OakRedisUtil.getObject(RedisConst.voidCard.PARK);
		String mvc=bean.getmv_license()+"|"+bean.getPlate_color();
		if(list==null){
			list=new ArrayList<String>();
		}
		if("insert".equals(nameSpace)){
			list.add(mvc);
			log.info("parkVoid.insertCache");
		}
		if("update".equals(nameSpace)){
			for(int i=0;i<list.size();i++){
				if(list.get(i).equals(mvc)){
					list.remove(i);
					list.add(i,mvc);
					log.info("parkVoid.updateCache");
					break;
				}
			}
			
		}
		if("delete".equals(nameSpace)){
			for(int i=0;i<list.size();i++){
				if(list.get(i).equals(mvc)){
					list.remove(i);
					log.info("parkVoid.updateCache");
					break;
				}
			}
		}
		OakRedisUtil.setObject(RedisConst.voidCard.PARK, list);
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
	public void updateCache(ParkVoidCardBean bean, String nameSpace) {
		//增加
		if ("insert".equals(nameSpace)) {
			OakRedisUtil.setObject(
					(RedisConst.voidCard.PARK + ":" + bean.getmv_license() + "|" + bean.getPlate_color()),
					bean);
			log.info("parkVoid.insertCache");
		}
		//修改
		if ("update".equals(nameSpace)) {
			OakRedisUtil.setObject(
					(RedisConst.voidCard.PARK + ":" + bean.getmv_license() + "|" + bean.getPlate_color()),
					bean);
			log.info("parkVoid.updateCache");
			
		}
		//删除
		if ("delete".equals(nameSpace)) {
			OakRedisUtil.delete(RedisConst.voidCard.PARK + ":" + bean.getmv_license() + "|" + bean.getPlate_color());
			log.info("parkVoid.deleteCache");
		}
	}
	
	
	/*public void updateCache(ParkVoidCardBean bean,String nameSpace){
		List <String> list= OakRedisUtil.getObject(RedisConst.voidCard.PARK);
		String mvc=bean.getmv_license()+"|"+bean.getPlate_color();
		if(list==null){
			list=new ArrayList<String>();
		}
		if("insert".equals(nameSpace)){
			list.add(mvc);
			log.info("parkVoid.insertCache");
		}
		if("update".equals(nameSpace)){
			for(int i=0;i<list.size();i++){
				if(list.get(i).equals(mvc)){
					list.remove(i);
					list.add(i,mvc);
					log.info("parkVoid.updateCache");
					break;
				}
			}
			
		}
		if("delete".equals(nameSpace)){
			for(int i=0;i<list.size();i++){
				if(list.get(i).equals(mvc)){
					list.remove(i);
					log.info("parkVoid.updateCache");
					break;
				}
			}
		}
		OakRedisUtil.setObject(RedisConst.voidCard.PARK, list);
	}*/
	
}