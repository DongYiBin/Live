/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.common.constant;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public enum ErrorCode {
	ERROR_SYSTEM(-1,"系统错误"),
	ERROR_SERVICE_FAIL(-2,"服务失败"),
	ERROR_MISS_PARAM(-3,"缺失参数"),
	
	ERROR_ACCOUNT_STOP(10000,"账号被禁用"),
	ERROR_ACCOUNT_IS_EXIST(10001,"用户名已经存在"),
	ERROR_PHONE_IS_EXIST(10002,"手机号已经存在"),
	ERROR_PWD_IS_NULL(10003,"密码不能为空"),
	ERROR_ACCOUNT_OR_PWD(10005,"用户名或者密码错误"),
	ERROR_SMS_CODE(10006,"短信验证码不正确或者失效"),
	ERROR_OPENID_IS_IS_EXIST(10007,"openId已经存在"),
	ERROR_USER_ROLE(10008,"权限不足"),
	ERROR_NO_REGISTER_PHONE(10009,"该手机号未注册"),
	
	ERROR_PHONE_FORMAT(10100,"手机号码格式错误"),
	
	ERROR_TARGET_USER_NO_EXIST(10200,"目标用户不存在"),
	ERROR_NULL_DATA(102001,"未知数据")
	;

	private Integer value;
	private String name;

	private ErrorCode(Integer value,String name) {
		this.name = name;
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}
}
