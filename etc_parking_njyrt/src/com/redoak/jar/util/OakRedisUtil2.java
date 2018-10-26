package com.redoak.jar.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class OakRedisUtil2 {
	
	//日志信息
	protected static Logger logger =LogManager.getLogger(OakRedisUtil.class);
     
    private static JedisPool jedisPool = null;
     
    /**
     * 过期时间一分钟
     */
    public final static int EXRP_1_MIN = 60;
    /**
     * 过期时间十分钟
     */
    public final static int EXRP_10_MIN = 60;
    /**
     * 过期时间一小时
     */
    public final static int EXRP_ONE_HOUR = 60*60;          //一小时
    /**
     * 过期时间一天
     */
    public final static int EXRP_ONE_DAY = 60*60*24;        //一天
    /**
     * 过期时间一个月
     */
    public final static int EXRP_ONE_MONTH = 60*60*24*30;   //一个月
     
    /**
     * 初始化Redis连接池
     */
    private static void initialPool(){
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(20);
            config.setMaxIdle(10);
            config.setMaxWaitMillis(30000);
            
            config.setTestOnBorrow(true);
            
            String ADDRESS=PropertiesUtil.get("JREDIS.HOST");
            int PORT=Integer.parseInt(PropertiesUtil.get("JREDIS.PORT"));
            int TIMEOUT=Integer.parseInt(PropertiesUtil.get("JREDIS.TIMEOUT"));
            
            String PASSWORD=PropertiesUtil.get("JREDIS.PASSWORD");
            if(StringUtil.isEmpty(PASSWORD)){
            	jedisPool = new JedisPool(config, ADDRESS, PORT, TIMEOUT);
            }else{
            	jedisPool = new JedisPool(config, ADDRESS, PORT, TIMEOUT,PASSWORD);
            }
            
        } catch (Exception e) {
            logger.error("First create JedisPool error : "+e);
        }
    }
     
     
    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
        if (jedisPool == null) {  
            initialPool();
        }
    }
 
     
    /**
     * 同步获取Jedis实例
     * @return Jedis
     */
    private synchronized static Jedis getJedis() {  
        if (jedisPool == null) {  
            poolInit();
        }
        Jedis jedis = null;
        try {  
            if (jedisPool != null) {  
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {  
            logger.error("Get jedis error : "+e);
        }
        return jedis;
    }  
     
     
    /**
     * 释放jedis资源
     * @param jedis
     */
    private static void releaseResource(final Jedis jedis) {
        if (jedis != null && jedisPool !=null) {
//        	jedis.quit();
//        	if(jedis.isConnected()){
//        		jedis.disconnect();
//        	}
        	jedis.close();
        }
    }
    /**
     * 序列化对象
     * @param object
     * @return
     */
    private static byte[] serialObject(Object object){
    	if(object==null){
    		return null;
    	}
    
    	ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
        	logger.error("序列化对象失败"+object.toString());
        }finally {
			if(oos!=null){
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(baos!=null){
				try {
					baos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
					
		}
        return null;
    }
    /**
     * 添加了反序列化对象的功能
     * @param bytes
     * @return
     */
    @SuppressWarnings({ "unchecked", "unused" })
	private static <T> T deSerialObject(byte[] bytes){
    	if(bytes==null||bytes.length==0){
    		return null;
    	}
    	ByteArrayInputStream bais = null;
    	ObjectInputStream ois=null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            
            return (T) ois.readObject();
        } catch (Exception e) {
        	logger.error("反序列化对象失败"+bytes);
        }finally {
        	if(ois!=null){
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bais!=null){
				try {
					bais.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        return null;
    }
     
     
    /**
     * 设置 String
     * @param key
     * @param value
     */
    public static boolean set(String key ,String value){
    	Jedis s=null;
        try {
        	s=getJedis();
            s.set(key,value);
        } catch (Exception e) {
            logger.error("Set key error : "+e);
            return false;
        }finally {
			if(s!=null){
				releaseResource(s);
			}
		}
        return true;
    }
    /**
     * 设置 String
     * @param key
     * @param value
     */
    public static boolean set(String key ,Integer value){
    	Jedis s=null;
        try {
        	s=getJedis();
            s.set(key,String.valueOf(value));
        } catch (Exception e) {
            logger.error("Set key error : "+e);
            return false;
        }finally {
			if(s!=null){
				releaseResource(s);
			}
		}
        return true;
    }
    /**
     * 
     * @param key
     * @param value
     * @return
     */
    public static boolean setObject(String key ,Object value){
    	Jedis s=null;
        try {
        	s=getJedis();
            s.set(key.getBytes(),serialObject(value));
        } catch (Exception e) {
            logger.error("Set key error : "+e);
            return false;
        }finally {
			if(s!=null){
				releaseResource(s);
			}
		}
        return true;
    }
    
    /**
     * 
     * @param key
     * @param value
     * @return
     */
    public static boolean setMapObject(Map<String,String> maps){
    	Jedis s=null;
        try {
        	s=getJedis();
           for(Entry<String, String> entry : maps.entrySet())
           {
        	   s.set(entry.getKey().getBytes(),serialObject(entry.getValue().toString()));
           }
        } catch (Exception e) {
            logger.error("Set key error : "+e);
            return false;
        }finally {
			if(s!=null){
				releaseResource(s);
			}
		}
        return true;
    }
    /**
     * 
     * @param key
     * @param object
     * @return
     */
    public static boolean setObject(String key ,int seconds,Object object){
    	Jedis s=null;
        try {
        	s=getJedis();
            s.setex(key.getBytes(), seconds, serialObject(object));
        } catch (Exception e) {
            logger.error("Set key error : "+e);
            return false;
        }finally {
			if(s!=null){
				releaseResource(s);
			}
		}
        return true;
    }
    /**
     * 设置 过期时间
     * @param key
     * @param seconds 以秒为单位
     * @param value
     */
    public static boolean set(String key ,int seconds,String value){
        Jedis s=null;
        try {
        	s=getJedis();
            s.setex(key,seconds,value);
        } catch (Exception e) {
            logger.error("Set key error : "+e);
            return false;
        }finally {
			if(s!=null){
				releaseResource(s);
			}
		}
        return true;
    }
    
    public static boolean set(String key ,int seconds,Integer value){
        Jedis s=null;
        try {
        	s=getJedis();
            s.setex(key,seconds,String.valueOf(value));
        } catch (Exception e) {
            logger.error("Set key error : "+e);
            return false;
        }finally {
			if(s!=null){
				releaseResource(s);
			}
		}
        return true;
    }
     
    /**
     * 获取String值
     * @param key
     * @return value
     */
    public static int delete(String key){
    	Jedis s=null;
    	try{
    		s=getJedis();
    		if(s == null || s.exists(key)==false){
                return 0;
            }else{
            	s.del(key);
            	return 1;
            }
    	}catch(Exception e){
    		logger.error("get key error:"+key);
    		return 0;
    	}finally {
			releaseResource(s);
		}
    }
    
    
    /**
     * 获取String值
     * @param key
     * @return value
     */
    public static int deleteAll(Set<String> keys){
    	Jedis s=null;
    	try{
    		s=getJedis();
    		if(s == null){
                return 0;
            }else{
            	for(String key:keys)
            	{
            		s.del(key);
            	}
            	return 1;
            }
    	}catch(Exception e){
    		logger.error("get keys error:keyAll");
    		return 0;
    	}finally {
			releaseResource(s);
		}
    }
    
    
    /**
     * 获取模糊查询是否存在
     * @param pattern
     * @return value
     */
    public static Set<String> getKeys(String pattern){
    	Jedis s=null;
    	try{
    		s=getJedis();
    		if(s == null){
                return null;
            }else{
            	return s.keys(pattern);
            }
    	}catch(Exception e){
    		logger.error("get pattern error:"+pattern);
    	}finally {
			releaseResource(s);
		}
    	return null;
    }
    
    /**
     * 获取数据
     * @param key
     * @return value
     */
    public static boolean exists(String key){
    	Jedis s=null;
    	try{
    		s=getJedis();
    		if(s == null){
                return false;
            }else{
            	return s.exists(key);
            }
    	}catch(Exception e){
    		logger.error("get exists error:"+key);
    	}finally {
			releaseResource(s);
		}
    	return false;
    }
    
    /**
     * 获取数据
     * @param key
     * @return value
     */
    public static String getString(String key){
    	Jedis s=null;
    	try{
    		s=getJedis();
    		if(s == null || s.exists(key)==false){
                return null;
            }else{
            	return s.get(key);
            }
    	}catch(Exception e){
    		logger.error("get key error:"+key);
    	}finally {
			releaseResource(s);
		}
    	return null;
    }
    /**
     * 获取数据
     * @param key
     * @return value
     */
    public static Integer getInt(String key){
    	String value=getString(key);
    	if(StringUtil.isEmpty(value)){
    		return null;
    	}else{
    		return Integer.parseInt(value);
    	}
    }
    /**
     * 获取数据
     * @param key
     * @return value
     */
    public static <T> T getObject(String key){
    	Jedis s=null;
    	try{
    		s=getJedis();
    		if(s == null || s.exists(key)==false){
                return null;
            }else{
            	byte[] bytes=s.get(key.getBytes());
            	return deSerialObject(bytes);
            }
    	}catch(Exception e){
    		logger.error("get key error:"+key);
    	}finally {
			releaseResource(s);
		}
    	return null;
    }
    
    
    public static void main(String[] args){
    	long s=System.currentTimeMillis();
//    	for(int i=0;i<10;i++){
//    		OakRedisUtil.set(String.valueOf(i), String.valueOf(i));
//    	}
    	for(int i=0;i<10;i++){
    		List<Integer> list=new ArrayList<Integer>();
    		list.add(i);
    		list.add(i+1);
    		if(i<5){
    			OakRedisUtil.setObject(String.valueOf(i),10, list);
    		}else{
    			OakRedisUtil.setObject(String.valueOf(i), list);
    		}
    		
    	}
    	
    	for(int i=0;i<10;i++){
    		List<Integer> list=OakRedisUtil.getObject(String.valueOf(i));
    		if(list!=null){
    			System.out.println("set data:"+list.get(0)+" , "+list.get(1));
    		}
    	}
    	
//    	for(int i=0;i<5;i++){
//    		OakRedisUtil.delete(String.valueOf(i));
//    	}
    	try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	for(int i=0;i<10;i++){
    		List<Integer> list=OakRedisUtil.getObject(String.valueOf(i));
    		if(list!=null){
    			System.out.println("get data:"+list.get(0)+" , "+list.get(1));
    		}else{
    			System.out.println("no data:");
    		}
    	}
    	
    }
}
