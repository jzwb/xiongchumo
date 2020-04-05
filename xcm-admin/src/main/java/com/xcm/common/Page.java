package com.xcm.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用 - 分页
 */
public class Page<T> implements Serializable {

	private final Pageable pageable;//分页信息
	private final List<T> content = new ArrayList<>();//内容
	private final long total;//总记录数

	/**
	 * 初始化对象
	 */
	public Page() {
		this.total = 0L;
		this.pageable = new Pageable();
	}

	/**
	 * 初始化对象
	 *
	 * @param content  内容
	 * @param total    总记录数
	 * @param pageable 分页信息
	 */
	public Page(List<T> content, long total, Pageable pageable) {
		this.content.addAll(content);
		this.total = total;
		this.pageable = pageable;
	}

	/**
	 * 获取页码
	 *
	 * @return
	 */
	public int getPageNumber() {
		return pageable.getPageNumber();
	}

	/**
	 * 获取每页记录数
	 *
	 * @return
	 */
	public int getPageSize() {
		return pageable.getPageSize();
	}

	/**
	 * 获取搜索属性
	 *
	 * @return
	 */
	public String getSearchProperty() {
		return pageable.getSearchProperty();
	}

	/**
	 * 获取搜索值
	 *
	 * @return
	 */
	public String getSearchValue() {
		return pageable.getSearchValue();
	}

	/**
	 * 获取排序属性
	 *
	 * @return
	 */
	public String getOrderProperty() {
		return pageable.getOrderProperty();
	}

	/**
	 * 获取排序方向
	 *
	 * @return
	 */
	public Order.Direction getOrderDirection() {
		return pageable.getOrderDirection();
	}

	/**
	 * 获取排序
	 *
	 * @return
	 */
	public List<Order> getOrders() {
		return pageable.getOrders();
	}

	/**
	 * 获取筛选
	 *
	 * @return
	 */
	public List<Filter> getFilters() {
		return pageable.getFilters();
	}

	/**
	 * 获取总页数
	 *
	 * @return
	 */
	public int getTotalPages() {
		return (int) Math.ceil((double) getTotal() / (double) getPageSize());
	}

	/**
	 * 获取内容
	 *
	 * @return
	 */
	public List<T> getContent() {
		return content;
	}

	/**
	 * 获取总记录数
	 *
	 * @return
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * 获取分页信息
	 *
	 * @return
	 */
	public Pageable getPageable() {
		return pageable;
	}
}