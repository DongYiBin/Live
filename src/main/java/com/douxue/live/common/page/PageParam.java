/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.common.page;

import java.io.Serializable;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
public class PageParam<P> implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static int DEFAULT_PAGE_No = 1;
	public final static int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 以Bean形式包装查询条件（不含参数：页号，每页记录数，总记录数，总分页数，是否有前一页，否有下一页）
	 */
	private P p;

	/**
	 * 跳转页数，页数是从第一页是从1开始计算的
	 */
	private int pageNo = DEFAULT_PAGE_No;

	/**
	 * 每页的记录数(每页尺寸)
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 总记录数
	 */
	private int dataTotal = 0;

	public PageParam() {

	}

	public PageParam(P p, int pageNo, int pageSize) {
		super();
		this.p = p;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public P getP() {
		return p;
	}

	public void setP(P p) {
		this.p = p;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getDataTotal() {
		return dataTotal;
	}

	public void setDataTotal(int dataTotal) {
		this.dataTotal = dataTotal;
	}

	@Override
	public String toString() {
		return "PageParam [p=" + p + ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", dataTotal=" + dataTotal + "]";
	}

}
