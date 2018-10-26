package com.project.backMng.admin.member.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.admin.member.model.CarInfoBean;
import com.project.common.tool.AppConst;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.mybatis.service.BaseService;

public class CarInfoMngServiceImpl extends BaseService implements CarInfoMngService {

	private final static Logger log = LogManager.getLogger(CarInfoMngServiceImpl.class);

	public CarInfoMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.member.CarInfoMng");
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<CarInfoBean> findList(ObjectMap queryParam, Page page) {
		page.setRecordCount(super.findInteger(ns("findCount"), queryParam));
		return super.findList(ns("findList"), queryParam, page);
	}

	/**
	 * 根据车牌、车牌颜色查询车辆
	 */
	@Override
	public CarInfoBean findInfo(String mvLicense,String carColor) {
		ObjectMap o = ObjectMap.newInstance();
		o.put("mv_license", mvLicense);
		o.put("car_color", carColor);
		return super.findObj(ns("findInfo"), o);
	}

	/**
	 * 
	 * @Title : save 新增车辆信息
	 * @功能描述: TODO
	 * @传入参数：@param bean @返回值：
	 * @throws ：
	 */
	@Override
	public String save(CarInfoBean bean) {

		// 校验车牌或者obu编号不能同时为空
		/*if ((bean.getMvLicense() == null || "".equals(bean.getMvLicense().trim()))
				&& (bean.getObuId() == null || "".equals(bean.getObuId().trim()))) {
			return "添加车牌或者obu编号不能为空!";
		}*/
		
		//判断车辆牌照是否为空
		if (bean.getMvLicense() == null || "".equals(bean.getMvLicense().trim()))
		{
			return "请输入车牌号！";
		}
		
		// 校验会员编号是否填写
		if(bean.getMemberId() == null ||  "".equals(bean.getMemberId().trim()))
		{
			return "请输入会员编号!";
		}
		
		//根据车牌号和车牌颜色查询车辆个数
		ObjectMap o = ObjectMap.newInstance();
		o.put("mv_license", bean.getMvLicense().trim());
		o.put("car_color", bean.getCarColor());

		// 检查系统中是否存在相同车牌号
		if (bean.getMvLicense() != null) {
			int countByMvLicense = super.findInteger(ns("checkUniqueMvlicense"), o);
			if (countByMvLicense > 0) {
				return "系统中已有相同车辆牌照！";
			}
		}

		// 检查系统中是否存在相同车牌号
		if (bean.getObuId() != null && !bean.getObuId().trim().equals("")) {
			int countByObuId = super.findInteger(ns("checkUniqueObuId"), bean);
			if (countByObuId > 0) {
				return "系统中已有相同obu编号！";
			}
		}
		

		// 检查系统中是否存在会员,如果没有填写会员id则跳过
//		if (bean.getMemberId() != null && !"".equals(bean.getMemberId().trim())) {
			// 查询该会员是否存在
			int count = super.findInteger("backMng.admin.member.MemberInfoMng.findCountByMemberId",
					bean.getMemberId().trim());
			if (count == 0) {
				return "系统中不存在该会员编号！";
			}
//		}

		// TODO Auto-generated method stub
		bean.setDeleteFlag(AppConst.DELETE_FLAG.NO);
		// 保存到T_LOGIN_USER数据表
//		ObjectMap o = ObjectMap.newInstance();
		o.put("obu_id", bean.getObuId().trim());
		o.put("member_id", bean.getMemberId().trim());
		o.put("license", bean.getLicense().trim());
		o.put("type", bean.getType().trim());
		o.put("s_comment", bean.getsComment().trim());
		o.put("delete_flag", bean.getDeleteFlag());
		super.insert(ns("insertCarInfo"), o);
		return "success";
	}

	@Override
	public String update(CarInfoBean bean) {
		int countByObuId = super.findInteger(ns("checkUniqueObuId"), bean);
		if (countByObuId > 0) {
			return "系统中已有相同obu编号！";
		}
		int count = super.findInteger("backMng.admin.member.MemberInfoMng.findCountByMemberId",
				bean.getMemberId().trim());
		if (count == 0) {
			return "系统中不存在该会员编号！";
		}
		super.update(ns("update"), bean);
		return "success";
	}

	@Override
	public void delete(String mvLicense,String carColor) {
		// 删除车辆
		ObjectMap o = ObjectMap.newInstance();
		o.put("mv_license", mvLicense);
		o.put("car_color", carColor);
		super.delete(ns("deleteCar"), o);
	}

}
