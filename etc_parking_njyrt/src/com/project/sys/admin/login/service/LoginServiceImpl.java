package com.project.sys.admin.login.service;


import java.util.ArrayList;
import java.util.List;

import com.project.backMng.admin.sys.model.UserBean;
import com.project.sys.admin.login.model.LoginUserBean;
import com.project.sys.admin.login.model.UserSessionTree;
import com.redoak.jar.base.model.OakTreeModel;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.mybatis.service.BaseService;
import com.redoak.jar.util.StringUtil;

public class LoginServiceImpl extends BaseService implements LoginService{
	
	public LoginServiceImpl() {
		// TODO Auto-generated constructor stub
		super.setIBATIS_NAME_SPACE("sys.login.admin");
	}
	
	/**public SysLoginUserBean findLoginUser(String LOGIN_NAME, String PASSWORD) {
		// TODO Auto-generated method stub
		ObjectMap param=ObjectMap.newInstance();
		param.put("LOGIN_NAME", LOGIN_NAME);
		param.put("PASSWORD", PASSWORD);
		return super.findObj(ns("findUser"),param);
	}*/

	/**
	 * 
	   * @Title : findLoginUser 查询登陆用户是否密码正确
	   * @功能描述: TODO
	   * @传入参数：@param LOGIN_NAME 登陆用户名
	   * @传入参数：@param PASSWORD 登陆用户密码
	   * @传入参数：@return
	   * @返回值：
	   * @throws ：
	 */
	public LoginUserBean findLoginUser(String LOGIN_NAME, String PASSWORD) {
		// TODO Auto-generated method stub
		ObjectMap param=ObjectMap.newInstance();
		param.put("login_name", LOGIN_NAME);
		param.put("password", PASSWORD);
		return super.findObj(ns("findLoginUser"),param);
	}
	
	
	public List<OakTreeModel> findAdminTreeList(String parentId, Integer userType, String userId) {
		// TODO Auto-generated method stub
		ObjectMap param=ObjectMap.newInstance();
		param.put("USER_TYPE", userType);
		param.put("PARENT_ID", parentId);
		param.put("USER_ID", userId);
		return super.findList(ns("findAdminTreeList"),param);
	}

	@Override
	public List<String> loadProvinceList() {
		// TODO Auto-generated method stub
		return super.findList(ns("selectProvinceList"));
	}

	@Override
	public List<String> loadCityList(String PROVINCE_NAME) {
		// TODO Auto-generated method stub
		return super.findList(ns("loadCityList"),PROVINCE_NAME);
	}

	@Override
	public ObjectMap loadCity(String CITY_NAME) {
		// TODO Auto-generated method stub
		return super.findObj(ns("loadCityInfo"),CITY_NAME);
	}

	@Override
	public List<UserSessionTree> findAllTreeListByUserType(ObjectMap queryParam) {
		// TODO Auto-generated method stub
		List<OakTreeModel> list=super.findList(ns("findAllTreeMenuListByRoleId"),queryParam);
		
		List<UserSessionTree> result=new ArrayList<UserSessionTree>();
		if(list!=null){
			//树节点的列表，添加父节点
			for(OakTreeModel m :list){
				if(StringUtil.isEmpty(m.getParentId())==true){
					UserSessionTree tree=new UserSessionTree();
					tree.setNode(m);
					result.add(tree);
				}
			}
			//树子节点的列表，添加子节点
			for(UserSessionTree tree :result){
				for(OakTreeModel m :list){
					if(StringUtil.isEmpty(m.getParentId())==false
							&&tree.getNode().getId().equals(m.getParentId())){
						tree.getChildNodeList().add(m);
					}
				}
			}
		}
		return result;
	}

	@Override
	public String findUserInUserFlag(String userId) {
		return super.findString(ns("findUserInUserFlag"), userId);
	}

}
