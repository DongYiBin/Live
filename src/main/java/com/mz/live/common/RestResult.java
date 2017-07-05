/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.common;
//

// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mz.live.common.constant.ErrorCode;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResult implements Serializable {
	private static final long serialVersionUID = -1809195782514717593L;
	public static final RestResult OK = new RestResult();
	public static final RestResult FAIL = new RestResult(ErrorCode.ERROR_SERVICE_FAIL.getValue(),ErrorCode.ERROR_SERVICE_FAIL.getName());
	public static final int SUCCESS_CODE = 1;
	public static final String SUCCESS_MSG = "success";
	public static final int FAILED_CODE = 0;
	private int code;
	private String msg;
	private String originalCode;
	private String detailMsg;
	private Object data;

	public RestResult(ErrorCode error) {
		this(error.getValue(), error.getName(), (Object) null);
	}
	
	public RestResult() {
		this(1, "success", (Object) null);
	}

	public RestResult(Object data) {
		this(1, "success", data);
	}

	public RestResult(int code, String msg) {
		this(code, msg, (Object) null);
	}

	public RestResult(int code, Object data) {
		this(code, "success", data);
	}

	public RestResult(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOriginalCode() {
		return this.originalCode;
	}

	public RestResult setOriginalCode(String originalCode) {
		this.originalCode = originalCode;
		return this;
	}

	public String getDetailMsg() {
		return this.detailMsg;
	}

	public RestResult setDetailMsg(String detailMsg) {
		this.detailMsg = detailMsg;
		return this;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
