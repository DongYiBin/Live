package com.mz.live.service.user;

import com.mz.live.common.RestResult;

public interface UserService {

	RestResult register(String account, String password);

	RestResult login(String account, String password);

}
