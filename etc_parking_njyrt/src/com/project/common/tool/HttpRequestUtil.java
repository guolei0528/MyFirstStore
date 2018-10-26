package com.project.common.tool;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

public class HttpRequestUtil {
	
	private final static Log log=LogFactory.getLog(HttpRequestUtil.class);
	
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
	public static String post(String url,String postData) throws UnsupportedEncodingException, IOException{
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

       out.writeBytes(postData);
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
	
	
}
