package com.project.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class PostOrGet {

	public static final String POST_FORM_SUBMIT_TYPE = "application/x-www-form-urlencoded";

	public static String sendPost(String url, String param, String contentType) {
		if (contentType == null)
			contentType = "text/html; charset=utf-8";
		OutputStreamWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		URLConnection conn = null;
		try {
			URL realUrl = new URL(url);
//			_FakeX509TrustManager.allowAllSSL();
			conn = realUrl.openConnection();
			conn.setRequestProperty("content-type", contentType);
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
			out.write(param);
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

			String line = null;
			while ((line = in.readLine()) != null) {
				result.append(line + "\\n");
			}
			if (result != null) {
				result = result.delete(result.length() - 2, result.length());
			}
			line = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "error";
		} finally {
			conn = null;
			try {
				if (out != null) {
					out.close();
				}
				if (in != null)
					in.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}


	public static String sendGet(String url) throws IOException{
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
	
	
	/**
	 * 简单判断字符串是否为json类型,是json类型返回true
	 */
	public static boolean ifIsJson(String str){
		if(str.startsWith("{") && str.endsWith("}")){
				return true;	
		}
		return false;
	}
	
}