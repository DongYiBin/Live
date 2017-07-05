/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.redis;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public interface RedisListService extends RedisKeyService {
    
    /**  
     * size:获取指定的集合的元素个数 . <br/>  
     * @author dun  
     * @param key
     * @return  
     * @since JDK 1.6  
     */
    public Long size(String key) ;
    
    /**  
     * getListValues:从指定链表中获取指定位置的元素. <br/>
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。<br/>
     * 也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。  <br/>
     * @author dun  
     * @param key
     * @param start 开始位置
     * @param end  结束位置
     * @return  
     * @since JDK 1.6  
     */
    public List<String> getListValues(String key, long start, long end);
    
    /**  
     * getListValues:从指定链表中获取指定位置的元素，转化为指定类型. <br/>
     * @author dun  
     * @param key
     * @param start  开始位置
     * @param end 结束位置
     * @param clazz 指定类型
     * @return  
     * @since JDK 1.6  
     */
    public <T> List<T> getListValues(String key, long start, long end, Class<T> clazz);
    
    /**  
     * getListValues:从指定链表中获取指定位置的元素，转化为指定类型，使用于泛型类型. <br/>
     * @author dun  
     * @param key
     * @param start  开始位置
     * @param end 结束位置
     * @param type 指定类型
     * @return  
     * @since JDK 1.6  
     */
    public <T> List<T> getListValues(String key, long start, long end, Type type);
    
    /**  
     * pushListValueFromRight:从指定链表的右边添加一个对象. <br/>  
     * @author dun  
     * @param key
     * @param value
     * @return  
     * @since JDK 1.6  
     */
    public long pushListValueFromRight(String key, Object value);

    /**  
     * pushListValueFromLeft:从指定链表的左边添加一个对象. <br/>  
     * @author dun  
     * @param key
     * @param value
     * @return  
     * @since JDK 1.6  
     */
    public long pushListValueFromLeft(String key, Object value);

    /**  
     * popListValueFromLeft:从指定链表的左边取出一个元素. <br/>  
     * @author dun  
     * @param key
     * @return  
     * @since JDK 1.6  
     */
    public String popListValueFromLeft(String key);
    
    /**  
     * popListValueFromLeft:从指定链表的左边取出一个元素，并转换为指定类型. <br/>  
     * @author dun  
     * @param key
     * @param clazz
     * @return  
     * @since JDK 1.6  
     */
    public <T> T popListValueFromLeft(String key, Class<T> clazz);
    
    /**  
     * popListValueFromLeft:从指定链表的左边取出一个元素，并转换为指定类型，使用于泛型类型. <br/>
     * @author dun  
     * @param key
     * @param type
     * @return  
     * @since JDK 1.6  
     */
    public <T> T popListValueFromLeft(String key, Type type);
    
    /**  
     * popListValueFromRight:从指定链表的右边取出一个元素. <br/>  
     * @author dun  
     * @param key
     * @return  
     * @since JDK 1.6  
     */
    public String popListValueFromRight(String key);
    
    /**  
     * popListValueFromRight:从指定链表的右边取出一个元素，并转换为指定类型. <br/>  
     * @author dun  
     * @param key
     * @param clazz
     * @return  
     * @since JDK 1.6  
     */
    public <T> T popListValueFromRight(String key, Class<T> clazz);
    
    /**  
     * popListValueFromRight:从指定链表的右边取出一个元素，并转换为指定类型，适用于泛型类型. <br/>
     * @author dun  
     * @param key
     * @param type
     * @return  
     * @since JDK 1.6  
     */
    public <T> T popListValueFromRight(String key, Type type);

}
