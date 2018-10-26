package com.project.common.tool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.redoak.jar.base.model.ResultBean;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import net.sf.json.JSONObject;

public class ShortMessageTemplate {
	
	private final static Logger log=LogManager.getLogger(ShortMessageTemplate.class);
	
	private final static String REQUEST_URL="https://eco.taobao.com/router/rest"; 
	
	public final static String VALID_CODE="SMS_24845106";
	
	private static Map<String,String[]> TEMPLATE_KEY_MAP=new HashMap<String,String[]>(); 
	
	private static void init(){
		
		TEMPLATE_KEY_MAP.put(VALID_CODE, new String[]{"名驹综合服务平台","23485100","c3096a6596fbc6570dd7587dd3040985"});
	}
	
	private static String getKey(String TEMPLATE_ID){
		if(TEMPLATE_KEY_MAP.size()==0){
			init();
		}
		return TEMPLATE_KEY_MAP.get(TEMPLATE_ID)[1];
	}
	
	private static String getSecret(String TEMPLATE_ID){
		if(TEMPLATE_KEY_MAP.size()==0){
			init();
		}
		return TEMPLATE_KEY_MAP.get(TEMPLATE_ID)[2];
	}
	
	public static ResultBean sendValidCode(String PHONE_NO,String TEMPLATE_ID,JSONObject jsonObject){
		long time=new Date().getTime();
		
		log.info("开始发送验证码，序号："+time+"，手机号："+PHONE_NO+"，模板："+TEMPLATE_ID+"，内容："+jsonObject);
		String KEY=getKey(TEMPLATE_ID);
		String SECRET=getSecret(TEMPLATE_ID);
		TaobaoClient client = new DefaultTaobaoClient(REQUEST_URL, KEY, SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(TEMPLATE_KEY_MAP.get(TEMPLATE_ID)[0]);
		req.setSmsParamString(jsonObject.toString());
		req.setRecNum(PHONE_NO);
		req.setSmsTemplateCode(TEMPLATE_ID);
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}
		log.info("收到响应，序号："+time+rsp.getBody());
		if(rsp.isSuccess()){
			return ResultBean.newSuccessResult("短信已发送，请注意查收");
		}else{
			return ResultBean.newErrorResult("短信发送失败，请稍后再试");
		}
	}
	
	public static AlibabaAliqinFcSmsNumSendResponse sendTaskSms(String PHONE_NO,String TEMPLATE_ID,String PARAM_JSON){
		long time=new Date().getTime();
		
		JSONObject jsonObject=null;
		if(StringUtil.isEmpty(PARAM_JSON)){
			jsonObject=new JSONObject();
		}else{
			jsonObject=JSONObject.fromObject(PARAM_JSON);
		}
		
		log.info("开始发送验证码，序号："+time+"，手机号："+PHONE_NO+"，模板："+TEMPLATE_ID+"，参数内容："+jsonObject);
		String KEY=getKey(TEMPLATE_ID);
		String SECRET=getSecret(TEMPLATE_ID);
		TaobaoClient client = new DefaultTaobaoClient(REQUEST_URL, KEY, SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(TEMPLATE_KEY_MAP.get(TEMPLATE_ID)[0]);
		req.setSmsParamString(jsonObject.toString());
		req.setRecNum(PHONE_NO);
		req.setSmsTemplateCode(TEMPLATE_ID);
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}
		log.info("收到响应，序号："+time+rsp.getBody());
		return rsp;
	}
	
	public static void main(String[] args){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("number", "1355");
		ShortMessageTemplate.sendValidCode("13605170458", ShortMessageTemplate.VALID_CODE, jsonObject);
	}
}
