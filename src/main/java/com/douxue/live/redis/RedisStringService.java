/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.redis;


import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**  
 * ClassName: RedisStringService <br/>  
 * Function: redis 字符串类型数据操作接口. <br/>    
 * date: 2015年6月26日 上午10:32:47 <br/>  
 *  
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0 
 */
public interface RedisStringService extends RedisKeyService{

    public boolean set(String key, Object obj);

    public boolean set(String key, int cacheTimeSeconds, Object obj);

    /**  
     * incrBy:将指定key的值增加delta， delta可为负数. <br/>  
     * @author dun  
     * @param key
     * @param delta
     * @return  
     * @since JDK 1.6  
     */
    public Long incrBy(String key, final long delta);
    

    /**  
     * get:读取指定key的值并转换为指定类型 < <br/>  
     * @author dun  
     * @param key
     * @param clazz 索要转换的类型
     * @return  
     * @since JDK 1.6  
     */
    public <T> T get(String key, Class<T> clazz);
    
    /**  
     * get:读取指定key的值并转换为指定类型 . <br/>
     * @see com.ws.study.redis.stringconvertor.StringConverter#fromString(String, Type)
     * @author dun  
     * @param key
     * @param type 指定类型
     * @return  
     * @since JDK 1.6  
     */
    public <T> T get(String key, Type type);
    
    /**  
     * mSet:批量设置键值对 <br/>  
     * @author dun  
     * @param mSetKeyValueMap 
     * @since JDK 1.6  
     */
    public void mSet(Map<String, Object> mSetKeyValueMap);


    /**  
     * mGet:批量获取指定key的值，值为字符串未做数据转换. <br/>  
     * @author dun  
     * @param keys
     * @return  
     * @since JDK 1.6  
     */
    public List<String> mGet(Collection<String> keys);

    /**  
     * mGet:批量获取指定key的值，并转换为指定类型. <br/>  
     * @author dun  
     * @param keys
     * @param clazz 指定类型
     * @return  
     * @since JDK 1.6  
     */
    public <T> List<T> mGet(Collection<String> keys, Class<T> clazz) ;
    
    /**  
     * mGet:批量获取指定key的值，并转换为指定类型，使用于泛型类型. <br/>  
     * @see com.ws.study.redis.stringconvertor.StringConverter#fromString(String, Type)
     * @author dun  
     * @param keys
     * @param type 指定类型
     * @return  
     * @since JDK 1.6  
     */
    public <T> List<T> mGet(Collection<String> keys, Type type) ;

}
