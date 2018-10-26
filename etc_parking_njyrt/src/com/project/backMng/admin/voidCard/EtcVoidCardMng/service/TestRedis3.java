package com.project.backMng.admin.voidCard.EtcVoidCardMng.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.backMng.admin.voidCard.EtcVoidCardMng.model.EtcVoidCardBean;
import com.project.tools.RedisConst;
import com.redoak.jar.util.OakRedisUtil;

public class TestRedis3 {
	private final static Logger log=LogManager.getLogger(TestRedis3.class);
	
	private final static int num = 7000000;
	public static void main(String[] args) {
		int i=0;
		Date dstart = new Date();
		for(;i<=num ;i++)
		{
			log.info(i);
			EtcVoidCardBean bean = new EtcVoidCardBean();
			bean.setcard_id("卡"+System.currentTimeMillis()+i);
			bean.setmv_license("苏"+System.currentTimeMillis()+i);
			bean.setcard_type("卡类型"+System.currentTimeMillis());
			bean.setcard_network("网络编号"+System.currentTimeMillis());
			bean.setcard_status("状态类型"+System.currentTimeMillis());
			bean.setissuer_id("发行方id"+System.currentTimeMillis());
			bean.setelectronic_id("电子id"+System.currentTimeMillis());
			bean.setmv_license_colour("颜色"+System.currentTimeMillis());
			bean.setissuer_version("发行版本"+System.currentTimeMillis());
			bean.setban_type("ban"+System.currentTimeMillis());
			bean.setissuer_effective_time(""+System.currentTimeMillis());
			bean.setvalid_flag("校验类型"+System.currentTimeMillis());
			bean.setvalid_time(""+System.currentTimeMillis());
			bean.setlock_card("锁卡"+System.currentTimeMillis());
			bean.setcontact_info("连接信息"+System.currentTimeMillis());
			bean.sets_comment("备注"+System.currentTimeMillis());
			bean.setversion("版本"+System.currentTimeMillis());
			
			log.info(RedisConst.voidCard.ETC+":"+bean.getcard_id()+"|"+bean.getmv_license());
			OakRedisUtil.setObject((RedisConst.voidCard.ETC+":"+bean.getcard_id()+"|"+bean.getmv_license()), 
					bean);
				
		}
		Date dend = new Date();
		System.out.println("使用时间（毫秒）："+(dstart.getTime()-dend.getTime()));
	}
}
