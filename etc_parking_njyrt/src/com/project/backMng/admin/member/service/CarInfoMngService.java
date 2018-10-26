package com.project.backMng.admin.member.service;

import java.util.List;

import com.project.backMng.admin.member.model.CarInfoBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;

public interface CarInfoMngService {


	public List<CarInfoBean> findList(ObjectMap queryParam,Page page);
	
	public CarInfoBean findInfo(String mvLicense,String carColor);
	
	public String save(CarInfoBean bean);
	
	public String update(CarInfoBean bean);
	
	public void delete(String mvLicense,String carColor);
	
	
}
