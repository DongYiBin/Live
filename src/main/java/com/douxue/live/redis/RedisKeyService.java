/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.redis;

import java.util.Collection;

/**  
 * Function: redis与键值相关的操作接口. <br/>
 * Date: 2015年6月26日 下午2:09:53 <br/>  
 *  
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public interface RedisKeyService {
     
    /**  
     * expired:设置某个key的时效时间. <br/>  
     * @author dun  
     * @param Key
     * @param timeInSeconds
     * @return  
     * @since JDK 1.6  
     */
    public boolean expired(String Key, long timeInSeconds);
    
    /**  
     * exists:判断指定key是否存在. <br/>  
     * @author dun  
     * @param key
     * @return  
     * @since JDK 1.6  
     */
    public boolean exists(String key);
    
    /**  
     * delete:删除指定key. <br/>  
     * @author dun  
     * @param key
     * @return  
     * @since JDK 1.6  
     */
    public boolean delete(String key);
    
    /**  
     * delete:批量删除key. <br/>
     * notice:redis 2.8.8 没有批量删除key的命令，所以这接口实际上一个一个地执行redis删除key的命令。  
     * @author dun  
     * @param keys
     * @return  
     * @since JDK 1.6  
     */
    public boolean delete(Collection<String> keys);
    
}
