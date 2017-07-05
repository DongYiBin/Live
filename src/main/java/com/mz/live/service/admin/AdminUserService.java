/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.service.admin;

import com.mz.live.dao.entity.AdminUser;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
public interface AdminUserService {

	AdminUser findUser(AdminUser user);

}
