package com.project.backMng.admin.member.service;

import java.util.List;

import com.project.backMng.admin.member.model.CarInfoBean;
import com.project.backMng.admin.member.model.MemberBean;
import com.project.backMng.admin.sys.model.UserBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface MemberInfoMngService {

	public List<MemberBean> findList(ObjectMap queryParam,Page page);
	
	public MemberBean findInfo(String id);
	
	public String save(MemberBean bean);
	
	public void update(MemberBean bean);
	
	public void delete(String memberId);
	
	public List<CarInfoBean> findCarInfo(String memberId);
}
