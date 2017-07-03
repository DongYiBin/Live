package com.mz.live.dao.mapper;

import com.mz.live.dao.entity.AdminUser;

public interface AdminUserMapper {
    int deleteByPrimaryKey(String adminId);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(String adminId);

    int updateByPrimaryKeySelective(AdminUser record);

	AdminUser findUser(AdminUser user);
}