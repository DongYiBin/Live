/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.common;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.RequestInfo;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public abstract class ThreadContext {
    private static final String KEY_REQUEST_INFO = "requestInfo";
    private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal();

    public ThreadContext() {
    }

    public static void bind() {
        threadLocal.set(new HashMap());
    }

    public static void unbind() {
        ((Map)threadLocal.get()).clear();
        threadLocal.remove();
    }

    public static RequestInfo getRequestInfo() {
        RequestInfo requestInfo = (RequestInfo)getValue("requestInfo");
        return requestInfo;
    }

    public static void setRequestInfo(RequestInfo requestInfo) {
        put("requestInfo", requestInfo);
    }

    private static Object getValue(String key) {
        return threadLocal != null && threadLocal.get() != null?((Map)threadLocal.get()).get(key):null;
    }

    private static void put(String key, Object value) {
        if(StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("key must not be empty");
        } else if(value == null) {
            remove(key);
        } else {
            ((Map)threadLocal.get()).put(key, value);
        }
    }

    public static Object remove(String key) {
        Object value = ((Map)threadLocal.get()).remove(key);
        return value;
    }
}
