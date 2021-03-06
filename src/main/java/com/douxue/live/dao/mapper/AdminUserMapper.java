/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.dao.mapper;

import com.douxue.live.dao.entity.AdminUser;

/**
 * mybatis中数据库操作接口
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
public interface AdminUserMapper {
    int deleteByPrimaryKey(String adminId);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(String adminId);

    int updateByPrimaryKeySelective(AdminUser record);

	AdminUser findUser(AdminUser user);
}