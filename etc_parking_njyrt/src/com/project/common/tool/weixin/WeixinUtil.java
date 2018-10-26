package com.project.common.tool.weixin;

import java.awt.Menu;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.redoak.jar.util.PropertiesUtil;


/**
 * 公众平台通用接口工具类
 * 
 */
public class WeixinUtil {
	
	private static Logger logger = LogManager.getLogger(WeixinUtil.class.getName());
	
    // 第三方用户唯一凭证   
//    public final static String appId = "wxe9df3721e446ee7c";  
    // 第三方用户唯一凭证密钥   
//    public final static String appSecret = "4c77e62452a9cb9a2caafb566de4d01b";  
    
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 获取用户基本信息
	public final static String user_info_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	public final static String access_token_OAuth2_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	public static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    public static AccessToken accessToken = null;
    
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		}
		return jsonObject;
	}



	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public synchronized static AccessToken getAccessToken() {
		// 返回旧的access_token
		
		if(null != accessToken){
			Date tokengetDateold = accessToken.getTokengetDate();
			int expiresIn = accessToken.getExpiresIn();
			
			Date nowDate = new Date();
			long between = (nowDate.getTime() - tokengetDateold.getTime()) / 1000;// 除以1000是为了转换成秒
			// 时间还在"expires_in":7200-200秒之内时，返回旧的access_token
			if(between<expiresIn-200){
				return accessToken;
			}
		}
		
		// 获取新的access_token
		String requestUrl = access_token_url.replace("APPID", getAppId()).replace(
				"APPSECRET", getAppSecret());
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				if(accessToken==null){
					accessToken = new AccessToken();
				}
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				accessToken.setTokengetDate(new Date()); //获得access_token的时间
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				logger.error("获取token失败  error:{}", e);
			}
		}
		return accessToken;
	}
	
	/**
	 * 通过code换取网页授权access_token
	 * 如果网页授权的作用域为snsapi_base，则本步骤中获取到网页授权access_token的同时，也获取到了openid，snsapi_base式的网页授权流程即到此为止。 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static Map<String,String> getAccessTokenByOauth2(String code) {
		Map<String,String> map=new HashMap<String,String>();
         String openid ="";
         String accessToken="";
		// 获取新的access_token
		String requestUrl = access_token_OAuth2_url.replace("APPID", getAppId()).replace(
				"SECRET", getAppSecret()).replace("CODE",code);
		// {"access_token":"ACCESS_TOKEN","expires_in":7200}
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			logger.info("accessTokenByAuth2:"+jsonObject.toString());
			try {
				openid =jsonObject.getString("openid");
				accessToken=jsonObject.getString("access_token");
			} catch (JSONException e) {
				// 获取token失败
				logger.error("获取网页授权openid失败  error:{}", e);
			}
		}
		map.put("openid", openid);
		map.put("access_token", accessToken);
		return map;
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static JSONObject getuserInfo(String accessToken,String openid) {

		String requestUrl = user_info_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
		// {"access_token":"ACCESS_TOKEN","expires_in":7200}
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				//将建json对象转换为 UserInfo_bean用户基本信息对象
				logger.info(jsonObject.toString());
			} catch (JSONException e) {
				// 获取token失败
				logger.error("获取用户信息失败  error:{}", e);
			}
		}
		return jsonObject;
		
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;
        //删除旧的菜单
		result = urlOption(menu, menu_delete_url,accessToken);
		//创建新的菜单
		if(0 == result){
			result = urlOption(menu, menu_create_url,accessToken);
		}
		return result;
	}

	/**
	 * 菜单操作的URL
	 * 
	 * @param menu 菜单实例
	 * @param urlOpt 操作的URL
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	private static  int urlOption(Menu menu,String urlOpt, String accessToken) {
		int result = 0;
		// 拼装创建菜单的url
		String url = urlOpt.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				logger.error("创建菜单失败 errcode:{} errmsg:{}");
			}
		}
		return result;
	} 
	
	public static String md5Encode(String sourceStr){
		MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = null;
		try {
			byteArray = sourceStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
	}
	
	public static String getAppId(){
		return PropertiesUtil.get("appId");
	}
	
	public static String getAppSecret(){
		return PropertiesUtil.get("appSecret");
	}
}
