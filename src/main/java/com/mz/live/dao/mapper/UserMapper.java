package com.mz.live.dao.mapper;

import com.mz.live.dao.entity.User;

public interface UserMapper {
	int deleteByPrimaryKey(String userId);

	int insertSelective(User record);

	User selectByPrimaryKey(String userId);

	int updateByPrimaryKeySelective(User record);

	User findOneUser(User user);
}