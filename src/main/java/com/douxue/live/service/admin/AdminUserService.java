/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.service.admin;

import com.douxue.live.dao.entity.AdminUser;

/**
 * adminuser的service层
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
public interface AdminUserService {

	AdminUser findUser(AdminUser user);

}
