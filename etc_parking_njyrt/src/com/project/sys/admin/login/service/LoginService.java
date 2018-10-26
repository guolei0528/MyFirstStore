package com.project.sys.admin.login.service;


import java.util.List;

import com.project.backMng.admin.sys.model.UserBean;
import com.project.sys.admin.login.model.LoginUserBean;
import com.project.sys.admin.login.model.UserSessionTree;
import com.redoak.jar.base.model.OakTreeModel;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.SysLoginUserBean;

public interface LoginService {
	/**
	 * 查询系统登录用户
	 * @param LOGIN_NAME
	 * @param PASSWORD
	 * @return
	 */
	public LoginUserBean findLoginUser(String LOGIN_NAME,String PASSWORD);
	/**
	 * 加载ADMIN的树列表
	 * @param parentId
	 * @param userType
	 * @return
	 */
	public List<OakTreeModel> findAdminTreeList(String parentId,Integer userType,String userId);
	/**
	 * 加载省份的信息
	 * @return
	 */
	public List<String> loadProvinceList();
	/**
	 * 加载市的信息
	 * @param PROVINCE_NAME
	 * @return
	 */
	public List<String> loadCityList(String PROVINCE_NAME);
	/**
	 * 加载市的信息
	 * @param PROVINCE_NAME
	 * @return
	 */
	public ObjectMap loadCity(String CITY_NAME);
	/**
	 * 查询会话树列表
	 * @param USER_TYPE
	 * @return
	 */
	public List<UserSessionTree> findAllTreeListByUserType(ObjectMap queryParam);

	public String findUserInUserFlag(String userId);
}
