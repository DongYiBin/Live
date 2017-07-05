/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.douxue.live.common.page;

/**
 * 分页查询时返回的对象
 * @author Tencent Cloud
 * @author CETC55
 * @since 2017/7/3
 * @version v1.0
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageData<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static int DEFAULT_PAGE_No = 1;
	public final static int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 存储返回的List结果
	 */
	private List<T> pageData = new ArrayList<T>();

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
	private int dataTotal;

	/**
	 * 总页数
	 */
	private int pageTotal;

	/**
	 * 是否有上一页
	 */
	private boolean prevPage = false;

	/**
	 * 是否有下一页
	 */
	private boolean nextPage = false;

	private int pageNumStart;// 页码显示开始listbegin;
	private int pageNumEnd;// 页码显示结束listend;
	private int showPageNum = 5;// 显示页码个数，默认是5

	/**
	 * 有些反序列化需要使用默认构造函数
	 */
	public PageData() {

	}

	/**
	 * 创建一个分页数据对象
	 * 
	 * @param pageNo
	 *            页号，从1开始
	 * @param pageSize
	 *            每页记录数
	 * @param dataTotal
	 *            总记录数
	 * @author: hualong
	 */
	public PageData(int pageNo, int pageSize, int dataTotal) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.dataTotal = dataTotal;
		this.calcPageTotal();// 计算总页数;
		refresh();
		setPageNumEnd(pageNumEnd);
		setPageNumStart(pageNumStart);
	}

	/**
	 * 创建一个分页数据对象
	 * 
	 * @param pageNo
	 *            页号，从1开始
	 * @param pageSize
	 *            每页记录数
	 * @param dataTotal
	 *            总记录数
	 * @param pageData
	 *            当前页数据列表
	 * @author: hualong
	 */
	public PageData(int pageNo, int pageSize, int dataTotal, List<T> pageData) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.dataTotal = dataTotal;
		this.pageData = pageData;
		this.calcPageTotal();// 计算总页数;
		refresh();
		setPageNumEnd(pageNumEnd);
		setPageNumStart(pageNumStart);
	}

	/**
	 * 取总页数
	 */
	private void calcPageTotal() {
		if (dataTotal == 0) {
			this.pageTotal = 0;
		} else {
			if (dataTotal % pageSize == 0) {
				this.pageTotal = dataTotal / pageSize;
			} else {
				this.pageTotal = dataTotal / pageSize + 1;
			}
		}
	}

	/**
	 * 刷新当前分页对象数据
	 */
	private void refresh() {
		if (pageTotal <= 1) {
			prevPage = false;
			nextPage = false;
		} else if (pageNo == 1) {
			prevPage = false;
			nextPage = true;
		} else if (pageNo >= pageTotal) {
			prevPage = true;
			nextPage = false;
		} else {
			prevPage = true;
			nextPage = true;
		}
	}

	/**
	 * 查询时为入参，查询返回时为List结果
	 * 
	 * @return
	 */
	public List<T> getPageData() {
		return pageData;
	}

	/**
	 * 设置查询入参或返回结果
	 * 
	 * @param pageData
	 *            查询时为入参，查询返回时为List结果
	 */
	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}

	/**
	 * 返回每页记录数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页记录数
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 返回页号，第一页为1
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置页号，第一页为1
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 返回当前页记录总数
	 */
	public int getDataTotal() {
		return dataTotal;
	}

	/**
	 * 设置当前页记录总数
	 */
	public void setDataTotal(int dataTotal) {
		this.dataTotal = dataTotal;
		this.calcPageTotal();// 计算总页数
		this.refresh();
	}

	/**
	 * 返回分页总数
	 */
	public int getPageTotal() {
		return pageTotal;
	}

	/**
	 * 设置分页总数
	 */
	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	/**
	 * 返回是否有前一页，有为true否为false
	 */
	public boolean hasPrevPage() {
		return prevPage;
	}

	/**
	 * 设置是否有前一页，有为true否为false
	 */
	public void setPrevPage(boolean prevPage) {
		this.prevPage = prevPage;
	}

	/**
	 * 返回是否有后一页，有为true否为false
	 */
	public boolean hasNextPage() {
		return nextPage;
	}

	/**
	 * 设置是否有后一页，有为true否为false
	 */
	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}

	/**
	 * 获取跳转页第一条数据在数据集的位置
	 */
	public int getStartOfPage() {
		if (pageNo == 0) {
			pageNo = 1;// 页数是从第一页是从1开始计算的
		}
		return (pageNo - 1) * pageSize;
	}

	public boolean isPrevPage() {
		return prevPage;
	}

	public boolean isNextPage() {
		return nextPage;
	}

	/**
	 * 取得List的第N页的subList
	 * 
	 * @param list
	 *            要分页的list
	 * @param pageSize
	 * @param pageNo
	 * @return List
	 */
	private static <T> List<T> subList(List<T> list, int pageNo, int pageSize) {
		// 初始化每页尺寸和页号
		pageSize = (pageSize <= 0 ? 10 : pageSize);
		pageNo = (pageNo <= 0 ? 1 : pageNo);

		// 计算开始结束位置
		int begin = (pageSize * (pageNo - 1) > list.size() ? list.size() : pageSize * (pageNo - 1));
		int end = (pageSize * pageNo > list.size() ? list.size() : pageSize * pageNo);

		// 返回分页数据
		return new ArrayList<T>(list.subList(begin, end));
	}

	/**
	 * 取得List的第N页的DataPage对象
	 * 
	 * @param list
	 *            要分页的list
	 * @param pageNo
	 *            第几页，第一页为1
	 * @param pageSize
	 *            每页记录数
	 * @return List 一页的数据
	 */
	public static <T> PageData<T> pageList(List<T> list, int pageNo, int pageSize) {
		List<T> l = subList(list, pageNo, pageSize);
		return new PageData<T>(pageNo, pageSize, list.size(), l);
	}

	public int getPageNumStart() {
		return pageNumStart;
	}

	public void setPageNumStart(int pageNumStart) {
		// 显示页数的一半
		int halfPage = (int) Math.ceil((double) showPageNum / 2);
		if (halfPage >= pageNo) {
			this.pageNumStart = 1;
		} else {
			if (pageNo + halfPage > pageTotal) {
				this.pageNumStart = (pageTotal - showPageNum + 1) <= 0 ? 1 : (pageTotal - showPageNum + 1);
			} else {
				this.pageNumStart = pageNo - halfPage + 1;
			}
		}
	}

	public int getPageNumEnd() {
		return pageNumEnd;
	}

	public void setPageNumEnd(int pageNumEnd) {
		// 显示页数的一半
		int halfPage = (int) Math.ceil((double) showPageNum / 2);
		if (halfPage >= pageNo) {
			// this.pageNumEnd = showPageNum;
			this.pageNumEnd = showPageNum > pageTotal ? pageTotal : showPageNum;
		} else {
			if (pageNo + halfPage >= pageTotal) {
				this.pageNumEnd = pageTotal;
			} else {
				this.pageNumEnd = pageNo + halfPage;
			}
		}
	}

	public int getShowPageNum() {
		return showPageNum;
	}

	public void setShowPageNum(int showPageNum) {
		this.showPageNum = showPageNum;
	}

	@Override
	public String toString() {
		return String.format("PageParam [p=%s, pageNo=%s, pageSize=%s, dataTotal=%s, pageTotal=%s, prevPage=%s, nextPage=%s, pageNumStart=%s, pageNumEnd=%s]", pageData, pageNo, pageSize, dataTotal, pageTotal, prevPage, nextPage, pageNumStart,
				pageNumEnd);
	}

//	public static void main(String[] args) {
	// List<String> l = new ArrayList<String>();
	// l.add("123456");
	// l.add("654321");
	// l.add("654321");
	// l.add("654321");
	// l.add("654321");
	//
	// PageData<String> p = pageList(l, 3, 2);
	// System.out.println(p);
	// }
}
