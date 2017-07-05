/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.redis;

import java.lang.reflect.Type;
import java.util.Set;

/**  
 * Function: redis 集合数据类型操作接口。 <br/>
 * redis内部通过字节数组来判断是否相同，来做集合中元素去重，所以使用需保证相同对象序列化中的字符串相同。
 * 该组件中使用stringconverter来控制对象到字符串的转化，使用中请注入合适的stringconverter来保证相同的对象能够序列为相同的字符串。
 * Date: 2015年6月26日 下午4:30:09 <br/>  
 *  
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
public interface RedisSetService extends RedisKeyService {
    
    /**  
     * add:向指定集合添加元素. <br/>  
     * @author dun  
     * @param key
     * @param values
     * @return  成功添加到集合的元素个数，不包含被忽略的元素
     * @since JDK 1.6  
     */
    public Long add(String key, Object... values);

    /**  
     * size:获取指定的集合的元素个数 . <br/>  
     * @author dun  
     * @param key
     * @return  
     * @since JDK 1.6  
     */
    public Long size(String key) ;
    
    /**  
     * isMember:判断某个元素是否在集合中. <br/>  
     * @author dun  
     * @param key
     * @param value
     * @return  
     * @since JDK 1.6  
     */
    public Boolean isMember(String key, Object value);
   
    /**  
     * remove:从指定集合中删除元素. <br/>  
     * @author dun  
     * @param key
     * @param values
     * @return  被成功移除的元素的数量，不包括被忽略的元素
     * @since JDK 1.6  
     */
    public Long remove(String key, Object... values);
    
    /**  
     * members:获取某个集合中所有的元素,值为未做转换的字符串. <br/>  
     * @author dun  
     * @param key
     * @return  
     * @since JDK 1.6  
     */
    public Set<String> members(String key);
    
    /**  
     * members:获取某个集合中所有的元素，并转换为指定类型. <br/>  
     * @author dun  
     * @param key
     * @param clazz
     * @return  
     * @since JDK 1.6  
     */
    public <T> Set<T> members(String key, Class<T> clazz);
    
    /**  
     * members:获取某个集合中所有的元素，并转换为指定类型，适用于泛型类型 <br/>  
     * @author dun  
     * @param key
     * @param type
     * @return  
     * @since JDK 1.6  
     */
    public <T> Set<T> members(String key, Type type);
    
}
