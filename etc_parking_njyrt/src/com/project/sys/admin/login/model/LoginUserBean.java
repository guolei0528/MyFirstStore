package com.project.sys.admin.login.model;

public class LoginUserBean {

    private String userId;

    private String roleId;

    private String loginName;

    private String password;

    private String name;

    private String sex;

    private String userOperator;

    private Integer userShift;

    private Integer operatorShift;

    private String phone;

    private Integer inUseFlag;

    private Integer deleteFlag;
    
    private String areaId;
    
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserOperator() {
		return userOperator;
	}

	public void setUserOperator(String userOperator) {
		this.userOperator = userOperator;
	}

	public Integer getUserShift() {
		return userShift;
	}

	public void setUserShift(Integer userShift) {
		this.userShift = userShift;
	}

	public Integer getOperatorShift() {
		return operatorShift;
	}

	public void setOperatorShift(Integer operatorShift) {
		this.operatorShift = operatorShift;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getInUseFlag() {
		return inUseFlag;
	}

	public void setInUseFlag(Integer inUseFlag) {
		this.inUseFlag = inUseFlag;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	
}
