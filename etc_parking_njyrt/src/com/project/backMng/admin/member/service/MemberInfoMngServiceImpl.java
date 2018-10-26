package com.project.backMng.admin.member.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.backMng.admin.member.model.CarInfoBean;
import com.project.backMng.admin.member.model.MemberBean;
import com.project.backMng.admin.sys.model.UserBean;
import com.project.common.tool.AppConst;
import com.project.tools.UuidGenerator;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.ResultBean;
import com.redoak.jar.base.mybatis.service.BaseService;
import com.redoak.jar.util.PropertiesUtil;

public class MemberInfoMngServiceImpl extends BaseService implements MemberInfoMngService {
	private final static Logger log = LogManager.getLogger(MemberInfoMngServiceImpl.class);

	public MemberInfoMngServiceImpl() {
		super();
		setIBATIS_NAME_SPACE("backMng.admin.member.MemberInfoMng");
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<MemberBean> findList(ObjectMap queryParam, Page page) {
		// TODO Auto-generated method stub
		page.setRecordCount(super.findInteger(ns("findCount"), queryParam));
		return super.findList(ns("findList"), queryParam, page);
	}

	/**
	 * 
	 * @Title : findInfo 根据会员id查询会员信息
	 * @功能描述: TODO
	 * @传入参数：@param id
	 * @传入参数：@return @返回值：
	 * @throws ：
	 */
	@Override
	public MemberBean findInfo(String memberId) {
		// TODO Auto-generated method stub
		return super.findObj(ns("findInfo"),memberId);
	}
	
	/**
	 * 根据员工id返回车辆
	 */
	@Override
	public List<CarInfoBean> findCarInfo(String memberId)
	{
		//返回车辆
		return super.findList(("backMng.admin.member.CarInfoMng.findCarInfo"),memberId);
	}

	/**
	 * 
	 * @Title : save
	 * @功能描述: TODO
	 * @传入参数：@param bean
	 * @返回类型：void
	 * @throws ：
	 */
	public String save(MemberBean bean) {
		
		// 检查系统中是否存在相同会员编号
		if(bean.getMemberId() == null || "".equals(bean.getMemberId().trim()))
		{
			return "请输入会员编号";
		}
		
		//校验会员是否存在
        int countByMemberId = super.findInteger(ns("countByMemberId"), bean.getMemberId());
			if (countByMemberId > 0) {
				return "系统中已有相同会员编号！";
		}
		
		bean.setDeleteFlag(AppConst.DELETE_FLAG.NO);
		// 保存到T_LOGIN_USER数据表
		ObjectMap o = ObjectMap.newInstance();
		o.put("member_id", bean.getMemberId());
		o.put("name", bean.getName());
		o.put("sex", bean.getSex());
		o.put("phone", bean.getPhone());
		o.put("location", bean.getLocation());
		o.put("birthday", bean.getBirthday());
		o.put("delete_flag", bean.getDeleteFlag());
		Date date = new Date();
		o.put("create_time", date);
		o.put("last_modify_time", date);
		super.insert(ns("insertMember"), o);
		return "success";
	}

    /**
     * 更新会员信息表
     */
	@Override
	public void update(MemberBean bean) {
		// 检查系统中是否存在相同会员编号
		/*if(bean.getMemberId() == null || "".equals(bean.getMemberId().trim()))
		{
			return "请输入会员编号";
		}
		
		//校验会员是否存在
        int countByMemberId = super.findInteger(ns("countByMemberId"), bean.getMemberId());
			if (countByMemberId > 0) {
				return "系统中已有相同会员编号！";
		}*/
		super.update(ns("update"), bean);
	}

	@Override
	public void delete(String memberId) {
		//删除会员
		super.delete(ns("deleteMember"), memberId);		
	}
}
