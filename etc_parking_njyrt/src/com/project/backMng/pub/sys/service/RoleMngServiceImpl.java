package com.project.backMng.pub.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.common.tool.AppConst;
import com.redoak.jar.base.model.OakTreeModel;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;
import com.redoak.jar.base.mybatis.service.BaseService;
import com.redoak.jar.util.StringUtil;

public class RoleMngServiceImpl extends BaseService implements RoleMngService{

	private final static Logger log=LogManager.getLogger(RoleMngServiceImpl.class);

	public RoleMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.pub.RoleMng");
	}

		// TODO Auto-generated method stub
	public List<ObjectMap> findList(ObjectMap queryParam, Page page) {
		page.setRecordCount(super.findInteger(ns("findCount"),queryParam));
		return super.findList(ns("findList"),queryParam,page);
	}

	public ObjectMap findInfo(String id) {
		// TODO Auto-generated method stub
		return super.findObj(ns("findInfo"),id);
	}

	public void save(ObjectMap objectMap) {
		// TODO Auto-generated method stub
		objectMap.put("delete_flag", AppConst.DELETE_FLAG.NO);
		super.insert(ns("insert"), objectMap);
		
		saveRoleMenu(objectMap);
	}

	public void update(ObjectMap objectMap) {
		// TODO Auto-generated method stub
		super.update(ns("update"), objectMap);
		
		saveRoleMenu(objectMap);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		super.update(ns("delete"), id);
	}

	public void saveRoleMenu(ObjectMap objectMap){
		super.delete(ns("deleteRoleMenu"),objectMap.get("role_id"));
		String CHECKED_NODE_IDS=(String) objectMap.get("NODE_ID_STR");
		if(StringUtil.isEmpty(CHECKED_NODE_IDS)==false){
			String[] arrays=CHECKED_NODE_IDS.split(",");
			if(arrays.length==0){
				return ;
			}
			for(String s:arrays){
				if(StringUtil.isEmpty(s)==false){
					ObjectMap bean=ObjectMap.newInstance();
					bean.put("role_id", objectMap.get("role_id"));
					bean.put("menu_id", s);
					super.insert(ns("insertRoleMenu"),bean);
				}
			}
		}
	}

	@Override
	public List<OakTreeModel> findTreeList(ObjectMap objectMap) {
		// TODO Auto-generated method stub
		List<OakTreeModel> resultList=new ArrayList<OakTreeModel>();
		String QUERY_TYPE=objectMap.getStr("QUERY_TYPE");
		
		if("add".equals(QUERY_TYPE)){
			List<OakTreeModel> list=super.findList(ns("selectAddTreeMap"),objectMap);
			if(list!=null){
				resultList.addAll(list);
			}
		}else if("edit".equals(QUERY_TYPE)){
			List<OakTreeModel> list=super.findList(ns("selectEditTreeMap"),objectMap);
			if(list!=null){
				resultList.addAll(list);
			}
		}else if("view".equals(QUERY_TYPE)){
			System.out.println(objectMap.get("ROLE_ID"));
			List<OakTreeModel> list=super.findList(ns("selectViewTreeMap"),objectMap);
			if(list!=null){
				resultList.addAll(list);
			}
		}
		return resultList;
	}

	

	@Override
	public List<ObjectMap> findWxMainPage(Integer USER_TYPE) {
		// TODO Auto-generated method stub
		if(USER_TYPE==AppConst.USER_TYPE.SYS_ADMIN||USER_TYPE==AppConst.USER_TYPE.PLAT_USER){
			USER_TYPE=AppConst.USER_TYPE.PLAT_USER;
		}
		return super.findList(ns("findWxMainPage"),USER_TYPE);
	}

	@Override
	public List<ObjectMap> findEditWxMainPage(String ROLE_ID,Integer USER_TYPE) {
		// TODO Auto-generated method stub
		if(USER_TYPE==AppConst.USER_TYPE.SYS_ADMIN||USER_TYPE==AppConst.USER_TYPE.PLAT_USER){
			USER_TYPE=AppConst.USER_TYPE.PLAT_USER;
		}
		
		ObjectMap queryParam=ObjectMap.newInstance();
		queryParam.put("ROLE_ID", ROLE_ID);
		queryParam.put("USER_TYPE", USER_TYPE);
		
		return super.findList(ns("findEditWxMainPage"),queryParam);
	}

	@Override
	public List<ObjectMap> findRelatedWxMainPage(String ROLE_ID) {
		// TODO Auto-generated method stub
		return super.findList(ns("findRelatedWxMainPage"),ROLE_ID);
	}
	@Override
	public void editRoleUser(String ROLE_ID,String USER_ID){
		Map<String,String> map=new HashMap<String,String>();
		map.put("ROLE_ID", ROLE_ID);
		map.put("USER_ID", USER_ID);
		super.update(ns("updateRoleUser"), map);
	}
	
	@Override
	public void saveRoleUser(String ROLE_ID,String USER_ID){
		Map<String,String> map=new HashMap<String,String>();
		map.put("ROLE_ID", ROLE_ID);
		map.put("USER_ID", USER_ID);
		super.insert(ns("saveRoleUser"), map);
	}
	
	@Override
	public ObjectMap findByRoleUserID(String UserID){
		Map<String,String> map=new HashMap<String,String>();
		map.put("USER_ID", UserID);
		ObjectMap mapObj=super.findObj(ns("findByUserId"), map);
		return mapObj;
	}
	@Override
	public List<Map> findByOwnerId(String OWNER_ID){
		Map<String,String> map=new HashMap<String,String>();
		map.put("OWNER_ID", OWNER_ID);
		List<Map> list = super.findList(ns("findList"), map);
		return list;
	}
	

	@Override
	public List<Map> findRoleById() {
		Map<String,String> map=new HashMap<String,String>();
		List<Map> list = super.findList(ns("findAllList"), map);
		return list;
	}
	
	@Override
	public List<ObjectMap> findAllRoleByType(Integer USER_TYPE,String OWNER_ID) {
		// TODO Auto-generated method stub
		Map map=new HashMap<String,String>();
		map.put("USER_TYPE", USER_TYPE);
		map.put("OWNER_ID", OWNER_ID);
		return super.findList(ns("selectAllUsableRoleByOwnerID"),map);
	}

	@Override
	public List<ObjectMap> findAllRoleByType(Integer USER_TYPE) {
		// TODO Auto-generated method stub
		return super.findList(ns("selectAllUsableRole"), USER_TYPE);
	}

	
	
}