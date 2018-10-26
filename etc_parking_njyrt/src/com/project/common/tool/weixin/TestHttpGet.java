package com.project.common.tool.weixin;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.redoak.jar.util.PropertiesUtil;

import net.sf.json.JSONObject;

public class TestHttpGet {
	
	private final static Log log=LogFactory.getLog(TestHttpGet.class);
	
	private static Long priorTokenTime=null;
	
//	private static String access_token=null;
	
	private static Long priorCardTokenTime=null;
	
	private static String priorCard_api_token=null;
	
	private static Long priorJsApiTokenTime=null;
	
	private static String priorJs_api_token=null;
	
	public static String sendHttpRequest(String url) throws IOException{
		URL getUrl=new URL(url);
		HttpURLConnection connection=(HttpURLConnection)getUrl.openConnection();
		connection.connect();
		
		//设置连接属性   
//		connection.setDoOutput(true);// 使用 URL 连接进行输出   
//		connection.setDoInput(true);// 使用 URL 连接进行输入   
//		connection.setUseCaches(false);// 忽略缓存   
//		connection.setRequestMethod("POST");// 设置URL请求方法   

		
		InputStream is=connection.getInputStream();
		byte[] result=new byte[1024];
		int index=0;
		
		StringBuffer sb=new StringBuffer();
		while((index=is.read(result))!=-1){
			sb.append(new String(result,0,index,"UTF-8"));
		}
		connection.disconnect();
		
		
		return sb.toString();
	}
	
	public static String post(String url,JSONObject json) throws UnsupportedEncodingException, IOException{
		URL getUrl=new URL(url);
		HttpURLConnection connection=(HttpURLConnection)getUrl.openConnection();
		
		connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		
		connection.connect();
		
		//POST请求
        DataOutputStream out = new DataOutputStream(
                connection.getOutputStream());

       out.writeBytes(json.toString());
        out.flush();
        out.close();
        
		//读取响应的结果信息
		InputStream is=connection.getInputStream();
		byte[] result=new byte[1024];
		int index=0;
		
		StringBuffer sb=new StringBuffer();
		while((index=is.read(result))!=-1){
			sb.append(new String(result,0,index,"UTF-8"));
		}
		connection.disconnect();
		
		return sb.toString();
	}
	
	public static String post(String url,String str) throws UnsupportedEncodingException, IOException{
		URL getUrl=new URL(url);
		HttpURLConnection connection=(HttpURLConnection)getUrl.openConnection();
		
		connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		
		connection.connect();
		
		//POST请求
        DataOutputStream out = new DataOutputStream(
                connection.getOutputStream());

       out.writeBytes(str.toString());
        out.flush();
        out.close();
        
		//读取响应的结果信息
		InputStream is=connection.getInputStream();
		byte[] result=new byte[1024];
		int index=0;
		
		StringBuffer sb=new StringBuffer();
		while((index=is.read(result))!=-1){
			sb.append(new String(result,0,index,"UTF-8"));
		}
		connection.disconnect();
		
		return sb.toString();
	}
	/**
	 * 获取ACCESS_TOKEN
	 * @return
	 * @throws IOException
	 */
//	public static String getAccessToken() throws IOException{
//		return access_token;
//		/*long currTime=System.currentTimeMillis();
//		if(priorTokenTime==null||currTime-priorTokenTime>7200*1000){
//			String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WeixinUtil.appId+"&secret="+WeixinUtil.appSecret;
//
//			String access_tokenStr=sendHttpRequest(url);
//			
//			Gson gson=new Gson();
//			Map<String,String> tokenMap=gson.fromJson(access_tokenStr, new TypeToken<Map<String,String>>() {
//			}.getType());
//			
//			priorTokenTime=System.currentTimeMillis();
//			access_token=tokenMap.get("access_token");
//			return access_token;
//		}else{
//			return access_token;
//		}*/
//		
//	}
	
	/**
	 * 获取卡JSAPI接口TICKET
	 * @return
	 * @throws IOException
	 */
	public static String getJsApiTicket(String accessToken) throws IOException{
		if("1".equals(PropertiesUtil.get("accessTokeFromGate"))){
			//如果是招行的系统，采用网关的模式
			String ticket=null;
			for(int i=0;i<3;i++){
				String conf_appId=WeixinUtil.getAppId();
				String conf_secret=WeixinUtil.getAppSecret();
				long timestamp=System.currentTimeMillis();
				
				String md5=WeixinUtil.md5Encode(conf_appId+conf_secret+timestamp);
				String url=PropertiesUtil.get("gate_domain")+"/gate/getJsApiTicket.action?appId="+conf_appId+"&timestamp="+timestamp+"&signature="+md5;
				
				try {
					String token=TestHttpGet.sendHttpRequest(url);
					JSONObject object=JSONObject.fromObject(token);
					Integer result=(Integer) object.get("result");
					ticket=(String) object.get("ticket");
					break;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			return ticket;
		}else{
			long currTime=System.currentTimeMillis();
			if(priorJsApiTokenTime==null||currTime-priorJsApiTokenTime>7200*1000){
				String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";

				String access_tokenStr=sendHttpRequest(url);
				
				JSONObject jsonObject=JSONObject.fromObject(access_tokenStr);
				
				priorJsApiTokenTime=System.currentTimeMillis();
				priorJs_api_token=(String) jsonObject.get("ticket");
				return priorJs_api_token;
			}else{
				return priorJs_api_token;
			}
		}
	}
	
	/**
	 * 获取卡API接口getCardApiTicket
	 * @return
	 * @throws IOException
	 */
	public static String getCardApiTicket() throws IOException{
		String accessToken=WeixinUtil.getAccessToken().getToken();
		long currTime=System.currentTimeMillis();
		if(priorCardTokenTime==null||currTime-priorCardTokenTime>7200*1000){
			String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=wx_card";

			String access_tokenStr=sendHttpRequest(url);
			
			JSONObject jsonObject=JSONObject.fromObject(access_tokenStr);
			
			priorCardTokenTime=System.currentTimeMillis();
			priorCard_api_token=(String) jsonObject.get("ticket");
			return priorCard_api_token;
		}else{
			return priorCard_api_token;
		}
	}
		
	/**
	 * JSSDK签名
	 * @param appId
	 * @param noncestr
	 * @param timestamp
	 * @param url
	 * @return
	 */
    private static Map<String, String> signJsApi(String jsapi_ticket,String noncestr,String timestamp, String url) {
        Map<String, String> ret = new HashMap<String, String>();

        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + noncestr +
                  "&timestamp=" + timestamp +
                  "&url="+url;
        
        log.info("signature str:"+string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            log.info("signature:"+signature);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("appId", WeixinUtil.getAppId());
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", noncestr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }
    
    public static String signCardApi(List<String> toSignStr) {
        String string1="";
        String signature = "";

        Collections.sort(toSignStr,new Comparator<Object>() {

			@Override
			public int compare(Object o1, Object o2) {
				// TODO Auto-generated method stub
				if(o1!=null&&o2!=null){
					String str1=(String)o1;
					String str2=(String)o2;
					
					return str1.compareTo(str2);
				}
				return 0;
			}
		});
        for(String s:toSignStr){
        	if(s!=null){
        		string1=string1+s;
        	}
        }
        
        //注意这里参数名必须全部小写，且必须有序
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return signature;
    }
    
    
    /**
     * BYTE转
     * @param hash
     * @return
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    /**
     * 创建随机数
     * @return
     */
    public static String create_nonce_str() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    /**
     * 创建TIMESTAMP
     * @return
     */
    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    /**
     * 调用微信API签名的方法
     * @param jsapi_ticket
     * @param timestamp
     * @return
     * @throws IOException 
     */
    public static Map<String,String> wxCommonJsApiSign() throws IOException{
//    	String access_token=WeixinUtil.getAccessToken().getToken();
//    	String jsapi_ticket=TestHttpGet.getJsApiTicket(access_token);
//    	
//    	String url=PropertiesUtil.get("domain_name")+ServletActionContext.getRequest().getRequestURI();
//    	
////    	Enumeration<String> paramNameEnum=ServletActionContext.getRequest().getParameterNames();
////		Map<String,String> map=new HashMap<>();
////		if(paramNameEnum!=null&&paramNameEnum.hasMoreElements()){
////			while(paramNameEnum.hasMoreElements()){
////				String key=paramNameEnum.nextElement();
////				map.put(key, ServletActionContext.getRequest().getParameter(key));
////			}
////			url=url+"?"+sortMapByKey(map);
////		}
//    	String paramStr=ServletActionContext.getRequest().getQueryString();
//    	if(!StringUtil.isEmpty(paramStr)){
//    		url=url+"?"+paramStr;
//    	}
//		Map<String,String> result=signJsApi(jsapi_ticket,create_nonce_str(),create_timestamp(), url);
//		return result;
    	return null;
    }
    /**
     * 对MAP的值进行KEY=VALUE&KEY2=VALUE2的形式进行组装
     * @param map
     * @return
     */
    private static String sortMapByKey(Map<String,String> map){
    	StringBuffer STR_BUFFER=new StringBuffer();
    	
    	if(map!=null){
    		List<String> keyList=new ArrayList<String>();
    		for(String s:map.keySet()){
    			keyList.add(s);
    		}
    		Collections.sort(keyList,new Comparator<Object>() {
    			@Override
    			public int compare(Object o1, Object o2) {
    				// TODO Auto-generated method stub
    				if(o1!=null&&o2!=null){
    					String str1=(String)o1;
    					String str2=(String)o2;
    					
    					return str1.compareTo(str2);
    				}
    				return 0;
    			}
    		});
    		int length=keyList.size();
    		for(int i=0;i<length;i++){
    			STR_BUFFER.append(keyList.get(i)+"="+map.get(keyList.get(i)));
    			if(i<length-1){
    				STR_BUFFER.append("&");
    			}
    		}
    	}
    	return STR_BUFFER.toString();
    }
	
	
}
