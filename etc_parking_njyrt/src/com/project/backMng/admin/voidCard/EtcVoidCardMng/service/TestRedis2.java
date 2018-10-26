package com.project.backMng.admin.voidCard.EtcVoidCardMng.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.tools.RedisConst;

import redis.clients.jedis.Jedis;

public class TestRedis2 {
	private final static Logger log=LogManager.getLogger(TestRedis2.class);
	private final static int num = 7000000;
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost",6379);
		jedis.auth("itsmoe");
		log.info("insertETC");
		
		Date dstart = new Date();
		try{
			for(int i=0;i<=num ;i++)
			{
				log.info(i);
				jedis.set(RedisConst.voidCard.ETC+":"+System.currentTimeMillis()+i, "value"+System.currentTimeMillis()+i);
			}
			Date dend = new Date();
			System.out.println("使用时间（毫秒）："+(dstart.getTime()-dend.getTime()));
		}
		finally
		{
			jedis.close();
		}
	}
}
