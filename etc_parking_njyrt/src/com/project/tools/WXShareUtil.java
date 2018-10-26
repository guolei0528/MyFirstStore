package com.project.tools;

import net.sf.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 微信分享工具
 * Created by liuchao on 2015/1/22.
 */
public class WXShareUtil {
    /**
     * 调用接口地址
     */
    private interface IWxapiURL {
        String URL_ACCESS_TOCKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        String URL_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
        String URL_MEDIA_DOWN = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
        String URL_USER_INFO ="https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
    }

    private interface IInvalidListener {
        JSONObject handle() throws Exception;
    }

    private static final String REQUEST_ENCODE = "UTF-8";
    //region 错误码对应中文解释，造福广大码农❤
    private static Map<String, String> code_info = null;

    static {
        code_info = new HashMap<String, String>();
        code_info.put("-1", "系统繁忙，此时请开发者稍候再试");
        code_info.put("0", "请求成功");
        code_info.put("40001", "获取access_token时AppSecret错误，或者access_token无效。请开发者认真比对AppSecret的正确性，或查看是否正在为恰当的公众号调用接口");
        code_info.put("40002", "不合法的凭证类型");
        code_info.put("40003", "不合法的OpenID，请开发者确认OpenID（该用户）是否已关注公众号，或是否是其他公众号的OpenID");
        code_info.put("40004", "不合法的媒体文件类型");
        code_info.put("40005", "不合法的文件类型");
        code_info.put("40006", "不合法的文件大小");
        code_info.put("40007", "不合法的媒体文件id");
        code_info.put("40008", "不合法的消息类型");
        code_info.put("40009", "不合法的图片文件大小");
        code_info.put("40010", "不合法的语音文件大小");
        code_info.put("40011", "不合法的视频文件大小");
        code_info.put("40012", "不合法的缩略图文件大小");
        code_info.put("40013", "不合法的AppID，请开发者检查AppID的正确性，避免异常字符，注意大小写");
        code_info.put("40014", "不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口");
        code_info.put("40015", "不合法的菜单类型");
        code_info.put("40016", "不合法的按钮个数");
        code_info.put("40017", "不合法的按钮个数");
        code_info.put("40018", "不合法的按钮名字长度");
        code_info.put("40019", "不合法的按钮KEY长度");
        code_info.put("40020", "不合法的按钮URL长度");
        code_info.put("40021", "不合法的菜单版本号");
        code_info.put("40022", "不合法的子菜单级数");
        code_info.put("40023", "不合法的子菜单按钮个数");
        code_info.put("40024", "不合法的子菜单按钮类型");
        code_info.put("40025", "不合法的子菜单按钮名字长度");
        code_info.put("40026", "不合法的子菜单按钮KEY长度");
        code_info.put("40027", "不合法的子菜单按钮URL长度");
        code_info.put("40028", "不合法的自定义菜单使用用户");
        code_info.put("40029", "不合法的oauth_code");
        code_info.put("40030", "不合法的refresh_token");
        code_info.put("40031", "不合法的openid列表");
        code_info.put("40032", "不合法的openid列表长度");
        code_info.put("40033", "不合法的请求字符，不能包含\\uxxxx格式的字符");
        code_info.put("40035", "不合法的参数");
        code_info.put("40038", "不合法的请求格式");
        code_info.put("40039", "不合法的URL长度");
        code_info.put("40050", "不合法的分组id");
        code_info.put("40051", "分组名字不合法");
        code_info.put("41001", "缺少access_token参数");
        code_info.put("41002", "缺少appid参数");
        code_info.put("41003", "缺少refresh_token参数");
        code_info.put("41004", "缺少secret参数");
        code_info.put("41005", "缺少多媒体文件数据");
        code_info.put("41006", "缺少media_id参数");
        code_info.put("41007", "缺少子菜单数据");
        code_info.put("41008", "缺少oauth code");
        code_info.put("41009", "缺少openid");
        code_info.put("42001", "access_token超时，请检查access_token的有效期，请参考基础支持-获取access_token中，对access_token的详细机制说明");
        code_info.put("42002", "refresh_token超时");
        code_info.put("42003", "oauth_code超时");
        code_info.put("43001", "需要GET请求");
        code_info.put("43002", "需要POST请求");
        code_info.put("43003", "需要HTTPS请求");
        code_info.put("43004", "需要接收者关注");
        code_info.put("43005", "需要好友关系");
        code_info.put("44001", "多媒体文件为空");
        code_info.put("44002", "POST的数据包为空");
        code_info.put("44003", "图文消息内容为空");
        code_info.put("44004", "文本消息内容为空");
        code_info.put("45001", "多媒体文件大小超过限制");
        code_info.put("45002", "消息内容超过限制");
        code_info.put("45003", "标题字段超过限制");
        code_info.put("45004", "描述字段超过限制");
        code_info.put("45005", "链接字段超过限制");
        code_info.put("45006", "图片链接字段超过限制");
        code_info.put("45007", "语音播放时间超过限制");
        code_info.put("45008", "图文消息超过限制");
        code_info.put("45009", "接口调用超过限制");
        code_info.put("45010", "创建菜单个数超过限制");
        code_info.put("45015", "回复时间超过限制");
        code_info.put("45016", "系统分组，不允许修改");
        code_info.put("45017", "分组名字过长");
        code_info.put("45018", "分组数量超过上限");
        code_info.put("46001", "不存在媒体数据");
        code_info.put("46002", "不存在的菜单版本");
        code_info.put("46003", "不存在的菜单数据");
        code_info.put("46004", "不存在的用户");
        code_info.put("47001", "解析JSON/XML内容错误");
        code_info.put("48001", "api功能未授权，请确认公众号已获得该接口，可以在公众平台官网-开发者中心页中查看接口权限");
        code_info.put("50001", "用户未授权该api");
        code_info.put("61451", "参数错误(invalid parameter)");
        code_info.put("61452", "无效客服账号(invalid kf_account)");
        code_info.put("61453", "客服帐号已存在(kf_account exsited)");
        code_info.put("61454", "客服帐号名长度超过限制(仅允许10个英文字符，不包括@及@后的公众号的微信号)(invalid kf_acount length)");
        code_info.put("61455", "客服帐号名包含非法字符(仅允许英文+数字)(illegal character in kf_account)");
        code_info.put("61456", "客服帐号个数超过限制(10个客服账号)(kf_account count exceeded)");
        code_info.put("61457", "无效头像文件类型(invalid file type)");
        code_info.put("61450", "系统错误(system error)");
        code_info.put("61500", "日期格式错误");
        code_info.put("61501", "日期范围错误");
    }
    //endregion

    private String access_token = null;
    private String access_tokenba = null;
    private long accesstocken_timestamp = 0;
    private long accesstocken_expiresIn = 0;
    private String jsapi_ticket = null;
    private long jsapiticket_timestamp = 0;
    private long jsapiticket_expiresIn = 0;
    private String appId;
    private String appSecret;
    private long timestamp;
    private String nonceStr;


    //region 单例模式
    private static WXShareUtil instance = null;
    private WXShareUtil(String appId, String appSceret){
        this.appId = appId;
        this.appSecret = appSceret;
    }

    public static WXShareUtil getInstance(String appId, String appSceret){
        if (null == instance) instance = new WXShareUtil(appId, appSceret);
        return instance;
    }
    //endregion


    /**
     * * 获取根据当前公众号获取微信jssdk的配置信息
     *
     * @param currUrl 使用jssdk的url
     * @return 配置信息的json格式字符串，请在前台获取构造的字符串并且调用wx.config();方法进行jssdk的配置
     * @throws Exception
     */
    public String genJSSDKConf(String currUrl,String openid) throws Exception {
        String json = "{\n" +
                "    \"debug\": false,\n" +
                "    \"appId\": \"%s\",\n" +
                "    \"timestamp\": %d,\n" +
                "    \"nonceStr\": \"%s\",\n" +
                "    \"signature\": \"%s\",\n" +
                "    \"jsApiList\": [\n" +
                "       \"onMenuShareTimeline\",\n" +
                "       \"onMenuShareAppMessage\",\n" +
                "       \"onMenuShareQQ\",\n" +
                "       \"onMenuShareWeibo\",\n" +
                "       \"startRecord\",\n" +
                "       \"stopRecord\",\n" +
                "       \"onVoiceRecordEnd\",\n" +
                "       \"playVoice\",\n" +
                "       \"pauseVoice\",\n" +
                "       \"stopVoice\",\n" +
                "       \"onVoicePlayEnd\",\n" +
                "       \"uploadVoice\",\n" +
                "       \"downloadVoice\",\n" +
                "       \"chooseImage\",\n" +
                "       \"previewImage\",\n" +
                "       \"uploadImage\",\n" +
                "       \"downloadImage\",\n" +
                "       \"translateVoice\",\n" +
                "       \"getNetworkType\",\n" +
                "       \"openLocation\",\n" +
                "       \"getLocation\",\n" +
                "       \"hideOptionMenu\",\n" +
                "       \"showOptionMenu\",\n" +
                "       \"hideMenuItems\",\n" +
                "       \"showMenuItems\",\n" +
                "       \"hideAllNonBaseMenuItem\",\n" +
                "       \"showAllNonBaseMenuItem\",\n" +
                "       \"closeWindow\",\n" +
                "       \"scanQRCode\",\n" +
                "       \"chooseWXPay\",\n" +
                "       \"openProductSpecificView\",\n" +
                "       \"addCard\",\n" +
                "       \"chooseCard\",\n" +
                "       \"openCard\"\n" +
                "    ]\n" +
                "}";
    	if(openid.equals("ogaJkw4qEc7F03jQ3hEE_f-aopZ00")){
    	access_token ="test";
    	}else{
    		access_token=access_tokenba;
    	}

        
        /*获取加密签名*/
        //获取access_token
        if (null == access_token || System.currentTimeMillis() >= accesstocken_timestamp + accesstocken_expiresIn) {
            this.getAccessTocken();
            //刷新access_tocken后ticket跟着立即刷新
            this.getJsapiTicket();
        }
        try{
        JSONObject getuserinfo = getuserinfo(openid);
        System.out.println(getuserinfo.toString());
        }catch(Exception e){
        	System.out.println(e.getMessage());
        	// 有错误的信息就重新取
/*            this.getAccessTocken();
            //刷新access_tocken后ticket跟着立即刷新
            this.getJsapiTicket();*/
        }
        //获取jsapi_ticket
        if (null == jsapi_ticket || System.currentTimeMillis() >= jsapiticket_timestamp + jsapiticket_expiresIn) {
            //处理时效不统一的情况
            this.getJsapiTicket();
        }
        //jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com
        String signature = this.SHA1(String.format("jsapi_ticket=%s&noncestr=%s&timestamp=%d&url=%s", jsapi_ticket, nonceStr, timestamp, currUrl));
        json = String.format(json, this.appId, timestamp, nonceStr, signature);
        return json;
    }

    /**
     * SHA-1加密
     * @param decript 明文
     * @return 密文
     * @throws Exception
     */
    private String SHA1(String decript) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.update(decript.getBytes());
        byte messageDigest[] = digest.digest();
        // Create Hex String
        StringBuilder hexString = new StringBuilder();
        // 字节数组转换为 十六进制 数
        for (byte byt : messageDigest) {
            String shaHex = Integer.toHexString(byt & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString();
    }

    /**
     * 生成盐
     * @return 盐
     */
    private String genNonceStr(){
        //生成随机字符串
        Random random = new Random(System.currentTimeMillis());
        StringBuilder sbNonceStr = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int type = random.nextInt(3);
            switch (type) {
                //"0" - "9" 48 - 57
                case 0: sbNonceStr.append((char) (random.nextInt(10) + 48));
                    break;
                //"A" - "Z"  65 - 90
                case 1: sbNonceStr.append((char) (random.nextInt(26) + 65));
                    break;
                //"a" - "z"  97-122
                case 2: sbNonceStr.append((char) (random.nextInt(26) + 97));
                    break;
            }
        }
        return sbNonceStr.toString();
    }

    /**
     * 获取access_tocken
     * @throws Exception
     */
    private void getAccessTocken() throws Exception {
        String url = String.format(IWxapiURL.URL_ACCESS_TOCKEN, this.appId, this.appSecret);
        JSONObject respJson = this.requestWXAPI(url, null);
        access_token = respJson.getString("access_token");
        access_tokenba=access_token;
        accesstocken_timestamp = System.currentTimeMillis();
        accesstocken_expiresIn = respJson.getInt("expires_in") * 1000;
    }

    private void getJsapiTicket() throws Exception {
        String url = String.format(IWxapiURL.URL_JSAPI_TICKET, access_token);
        //对access_token过期的处理
        JSONObject respJson = this.requestWXAPI(url, new IInvalidListener() {
            public JSONObject handle() throws Exception {
                //过期处理
                String url = String.format(IWxapiURL.URL_ACCESS_TOCKEN, appId, appSecret);
                JSONObject respJson = requestWXAPI(url, null);
                access_token = respJson.getString("access_token");
                return requestWXAPI(url, null);
            }
        });
        jsapi_ticket = respJson.getString("ticket");
        jsapiticket_timestamp = System.currentTimeMillis();
        jsapiticket_expiresIn = respJson.getInt("expires_in") * 1000;
        //生成时间戳
        timestamp = System.currentTimeMillis() / 1000;
        nonceStr = this.genNonceStr();
    }


    /**
     * 访问目标url并返回响应内容
     * @param strURL 目标url
     * @return 响应的内容
     */
    private JSONObject requestWXAPI(String strURL, IInvalidListener listener) throws Exception {
        InputStream inputStream = null;
        JSONObject respJson = null;
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置通信头
            connection.setRequestProperty("content-type", "textml; charset="+WXShareUtil.REQUEST_ENCODE);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();
            //开始读取响应内容
            if (200 == connection.getResponseCode()) {
                //读取响应数据
                inputStream = connection.getInputStream();
                ByteArrayOutputStream memory = new ByteArrayOutputStream();
                // 读取数据
                byte[] buffer = new byte[2048];
                int readSize = inputStream.read(buffer, 0, 2048);
                while(readSize > 0) {
                    memory.write(buffer, 0 ,readSize);
                    readSize = inputStream.read(buffer, 0, 2048);
                }
                String respStr = new String(memory.toByteArray(), WXShareUtil.REQUEST_ENCODE);
                respJson = JSONObject.fromObject(respStr);

                String error_code = respJson.containsKey("errcode") ? respJson.getString("errcode") : null;
                //错误信息处理
                if(null != error_code && !"0".equals(error_code)){
                    //如果返回码为40014（不合法的access_token）并且有对应处理监听则处理，否则报错
                    if ("40014".equals(error_code) && null != listener) {
                        respJson = listener.handle();
                    } else {
                        throw new Exception("调用微信API错误！返回信息：error_code:" + error_code + ", error_msg:"+code_info.get(error_code));
                    }
                } else {
                    return respJson;
                }
            } else {
                throw new Exception("访问微信时发生错误！错误信息：respCode:"+connection.getResponseCode()+", respMsg:"+connection.getResponseMessage());
            }
        } finally {
            if (null != inputStream) inputStream.close();
        }
        return respJson;
    }
    
    /**
     * 下载图片到本地服务器
     * @param strURL 目标url
     * @return 响应的内容
     */
    private ByteArrayOutputStream  requestWXAPI2(String strURL, IInvalidListener listener) throws Exception {
        InputStream inputStream = null;
//        JSONObject respJson = null;
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置通信头
            connection.setRequestProperty("content-type", "textml; charset="+WXShareUtil.REQUEST_ENCODE);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();
            //开始读取响应内容
            if (200 == connection.getResponseCode()) {
                //读取响应数据
                inputStream = connection.getInputStream();
                ByteArrayOutputStream memory = new ByteArrayOutputStream();
                // 读取数据
                byte[] buffer = new byte[2048];
                int readSize = inputStream.read(buffer, 0, 2048);
                while(readSize > 0) {
                    memory.write(buffer, 0 ,readSize);
                    readSize = inputStream.read(buffer, 0, 2048);
                }
                /*String respStr = new String(memory.toByteArray(), WXShareUtil.REQUEST_ENCODE);
                respJson = JSONObject.fromObject(respStr);

                String error_code = respJson.containsKey("errcode") ? respJson.getString("errcode") : null;
                //错误信息处理
                if(null != error_code && !"0".equals(error_code)){
                    //如果返回码为40014（不合法的access_token）并且有对应处理监听则处理，否则报错
                    if ("40014".equals(error_code) && null != listener) {
                        respJson = listener.handle();
                    } else {
                        throw new Exception("调用微信API错误！返回信息：error_code:" + error_code + ", error_msg:"+code_info.get(error_code));
                    }
                } else {
                    return respJson;
                }*/
                return memory;
            } else {
                throw new Exception("访问微信时发生错误！错误信息：respCode:"+connection.getResponseCode()+", respMsg:"+connection.getResponseMessage());
            }
        } finally {
            if (null != inputStream) {
            	inputStream.close();
            };
        }
    }

    public ByteArrayOutputStream getdown(String media_id,String openid) throws Exception {
    	if(openid.equals("ogaJkw4qEc7F30jQ3hEE_f-aopZ00")){
    	access_token =access_token+"test";
    	}else{
    		access_token=access_tokenba;
    	}
    	
        if (null == access_token || System.currentTimeMillis() >= accesstocken_timestamp + accesstocken_expiresIn) {
            this.getAccessTocken();
            //刷新access_tocken后ticket跟着立即刷新
            this.getJsapiTicket();
        }
        try{
        JSONObject getuserinfo = getuserinfo(openid);
        System.out.println(getuserinfo.toString());
        }catch(Exception e){
        	System.out.println(e.getMessage());
        	// 有错误的信息就重新取
/*            this.getAccessTocken();
            //刷新access_tocken后ticket跟着立即刷新
            this.getJsapiTicket();*/
        }
    	String url = String.format(IWxapiURL.URL_MEDIA_DOWN, access_token, media_id);
    	ByteArrayOutputStream requestWXAPI2 = this.requestWXAPI2(url, null);
        return requestWXAPI2;
    }
    
    public JSONObject getuserinfo(String openid) throws Exception {
    	String url = String.format(IWxapiURL.URL_USER_INFO, access_token, openid);
    	JSONObject requestWXAPI = this.requestWXAPI(url, null);
    	return requestWXAPI;
    }
}
