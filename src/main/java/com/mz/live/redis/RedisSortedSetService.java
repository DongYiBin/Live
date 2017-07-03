package com.mz.live.redis;

import java.lang.reflect.Type;
import java.util.Set;

/**  
 * ClassName: RedisSortedSetService <br/>  
 * Function: redis有序集合操作接口. <br/>
 * date: 2015年6月26日 上午10:36:49 <br/>  
 *  
 * @author dun  
 * @version   
 * @since JDK 1.6  
 */
public interface RedisSortedSetService extends RedisKeyService {
    /**  
     * size:获取有序集合的元素个数. <br/>  
     * @author dun  
     * @param key
     * @return  
     * @since JDK 1.6  
     */
    public Long size(String key);
    /**  
     * addValuetoSortedSet:将一个对象添加到有序集合中. <br/>  
     * @author dun  
     * @param key 有序集合的key
     * @param value 要添加的对象
     * @param score 对象对应的分数（用来排序）
     * @return  
     * @since JDK 1.6  
     */
    public Boolean addValuetoSortedSet(String key, Object value, double score);
    
    /**  
     * removeValuesfromSortedSet:从有序集合中删除对象. <br/>   
     * @author dun  
     * @param key
     * @param values
     * @return  
     * @since JDK 1.6  
     */
    public Long removeValuesfromSortedSet(String key, Object... values);
    
    /**  
     * getValuesfromSortedSet:从有序集合中获取排名start到end的对象. <br/>   
     * @author dun  
     * @param key 有序集合的key
     * @param start 
     * @param end
     * @return  linkedHashSet 顺序与对象的排序一致，元素对象被序列化后的字符串
     * @since JDK 1.6  
     */
    public Set<String> getValuesfromSortedSet(String key, long start, long end);
    
    /**  
     * getValuesfromSortedSet:从有序集合中获取排名start到end的对象. <br/>   
     * @author dun  
     * @param key 有序集合的key
     * @param start 
     * @param end
     * @param clazz 对象的类型
     * @return  linkedHashSet 顺序与对象的排序一致，元素为对象
     * @since JDK 1.6  
     */
    public <T> Set<T> getValuesfromSortedSet(String key, long start, long end, Class<T> clazz);
    
    /**  
     * getValuesfromSortedSet:从有序集合中获取排名start到end的对象. <br/>   
     * @author dun  
     * @param key 有序集合的key
     * @param start 
     * @param end
     * @param type 对象的类型
     * @return  linkedHashSet 顺序与对象的排序一致，元素为对象
     * @since JDK 1.6  
     */
    public <T> Set<T> getValuesfromSortedSet(String key, long start, long end, Type type);

}
