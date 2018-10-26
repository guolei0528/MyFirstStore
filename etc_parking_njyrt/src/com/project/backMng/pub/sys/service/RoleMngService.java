package com.project.backMng.pub.sys.service;

import java.util.List;
import java.util.Map;

import com.redoak.jar.base.model.OakTreeModel;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;

public interface RoleMngService {
	
	public List<ObjectMap> findList(ObjectMap queryParam,Page page);
	
	public ObjectMap findInfo(String id);
	
	public void save(ObjectMap objectMap);
	
	public void update(ObjectMap objectMap);
	
	public void delete(String id);
	/**
	 * 查询树列表信息
	 * @param objectMap
	 * @return
	 */
	public List<OakTreeModel> findTreeList(ObjectMap objectMap);
	/**
	 * 查询所有的角色信息
	 * @param USER_TYPE
	 * @return
	 */
//	public List<ObjectMap> findAllRoleByType(Integer USER_TYPE);
	/**
	 * 查询微信的主页面
	 * @param USER_TYPE
	 * @return
	 */
	public List<ObjectMap> findWxMainPage(Integer USER_TYPE);
	/**
	 * 查询微信的主页面
	 * @param USER_TYPE
	 * @return
	 */
	public List<ObjectMap> findEditWxMainPage(String ROLE_ID,Integer USER_TYPE);
	/**
	 * 查询微信的主页面
	 * @param ROLE_ID
	 * @return
	 */
	public List<ObjectMap> findRelatedWxMainPage(String ROLE_ID);


	public void saveRoleUser(String ROLE_ID, String USER_ID);

	public void editRoleUser(String ROLE_ID, String USER_ID);

	public ObjectMap findByRoleUserID(String UserID);
	
	public List<ObjectMap> findAllRoleByType(Integer USER_TYPE);
	
	public List<ObjectMap> findAllRoleByType(Integer USER_TYPE, String OWNER_ID);

	public List<Map> findByOwnerId(String OWNER_ID);
	
	/**
	 * 
	   * @Title : findRoleById 根据id查询用户
	   * @功能描述: TODO
	   * @传入参数：@return
	   * @返回类型：List<Map> 
	   * @throws ：
	 */
	public List<Map> findRoleById();
}