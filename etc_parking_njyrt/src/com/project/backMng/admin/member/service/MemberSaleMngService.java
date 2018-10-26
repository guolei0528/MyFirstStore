package com.project.backMng.admin.member.service;

import java.util.List;

import com.project.backMng.admin.member.model.MemberSaleBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface MemberSaleMngService {

	public List<MemberSaleBean> findList(ObjectMap queryParam,Page page);
	
	public MemberSaleBean findInfo(ObjectMap queryParam);
	
	public String save(MemberSaleBean bean);
	
	public void update(MemberSaleBean bean);
	
	public void delete(ObjectMap deleteParam);
}
