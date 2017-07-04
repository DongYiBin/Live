/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.redis;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public interface RedisHashService extends RedisKeyService {

    /**  
     * setHashValue:設置指定hash指定域的值. <br/> 
     * @author dun 
     * @param key
     * @param field
     * @param value
     * @return  
     * @since JDK 1.6  
     */
    public boolean setHashValue(String key, String field, Object value);
    
    /**  
     * getHashValue:讀取指定hash指定域的值. <br/>  
     * @author dun 
     * @param key
     * @param field
     * @return  
     * @since JDK 1.6  
     */
    public String getHashValue(String key, String field);
    
    /**  
     * getHashValue:讀取指定hash指定域的值，并转换为指定类型. <br/> 
     * @author dun 
     * @param key
     * @param field
     * @param clazz
     * @return  
     * @since JDK 1.6  
     */
    public <T> T getHashValue(String key, String field, Class<T> clazz);
    
    /**  
     * getHashValue:讀取指定hash指定域的值，并转换为指定类型，适合泛型类型. <br/>
     * @author dun 
     * @param key
     * @param field
     * @param type
     * @return  
     * @since JDK 1.6  
     */
    public <T> T getHashValue(String key, String field, Type type);
    
    /**  
     * getHashMutiplValues:批量获取指定hash某些域的值. <br/>  
     * @author dun 
     * @param Key
     * @param fields
     * @return  
     * @since JDK 1.6  
     */
    public List<String> getHashMutiplValues(String Key, Collection<String> fields);
    
    /**  
     * getHashMutiplValues:批量获取指定hash某些域的值，并转换为指定类型. <br/>  
     * @author dun 
     * @param Key
     * @param fields
     * @param clazz
     * @return  
     * @since JDK 1.6  
     */
    public <T> List<T> getHashMutiplValues(String Key, Collection<String> fields, Class<T> clazz);
    /**  
     * getHashMutiplValues:批量获取指定hash某些域的值，并转换为指定类型，适合泛型类型. <br/>
     * @author dun 
     * @param Key
     * @param fields
     * @param type
     * @return  
     * @since JDK 1.6  
     */
    public <T> List<T> getHashMutiplValues(String Key, Collection<String> fields, Type type);
    
    /**  
     * deleteHashValue:删除指定hash的指定域. <br/>  
     * @author dun 
     * @param Key
     * @param field
     * @return  
     * @since JDK 1.6  
     */
    public boolean deleteHashValue(String Key, String field);
    
    /**  
     * size:获取hash的元素个数. <br/>  
     * @author dun 
     * @param Key
     * @return  
     * @since JDK 1.6  
     */
    public long size(String Key);
    
    /**  
     * getHashAllValue:获取hash中所有的元素. <br/>  
     * @author dun 
     * @param Key
     * @return  
     * @since JDK 1.6  
     */
    public Map<String, String> getHashAllValue(String Key);
  
    /**  
     * getHashAllValue:获取hash中所有的元素，并将value转换为指定类型. <br/>  
     * @author dun 
     * @param Key
     * @param clazz
     * @return  
     * @since JDK 1.6  
     */
    public <T> Map<String, T> getHashAllValue(String Key, Class<T> clazz);
    
    /**  
     * getHashAllValue:获取hash中所有的元素，并将value转换为指定类型. <br/>
     * @author dun 
     * @param Key
     * @param type
     * @return  
     * @since JDK 1.6  
     */
    public <T> Map<String, T> getHashAllValue(String Key, Type type);



    public Long incrBy(String key , String field, long delta );

}
