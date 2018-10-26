package com.project.common.tool.weixin;

import java.util.Date;

/** 
 * 微信通用接口凭证 
 *  
 * @author liufeng 
 * @date 2013-08-08 
 */  
public class AccessToken {  
    // 获取到的凭证   
    private String token;  
    // 凭证有效时间，单位：秒   
    private int expiresIn;  
    // 凭证获得的时间点
    private Date tokengetDate;  
  
    public Date getTokengetDate() {
		return tokengetDate;
	}

	public void setTokengetDate(Date tokengetDate) {
		this.tokengetDate = tokengetDate;
	}

	public String getToken() {  
        return token;  
    }  
  
    public void setToken(String token) {  
        this.token = token;  
    }  
  
    public int getExpiresIn() {  
        return expiresIn;  
    }  
  
    public void setExpiresIn(int expiresIn) {  
        this.expiresIn = expiresIn;  
    }  
}  
