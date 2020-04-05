package com.xcm.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用 - 分页信息
 */
public class Pageable implements Serializable {

	private static final int DEFAULT_PAGE_NUMBER = 1;//默认页码
	private static final int DEFAULT_PAGE_SIZE = 50;//默认每页记录数
	private static final int MAX_PAGE_SIZE = 1000;//最大每页记录数

	private int pageNumber = DEFAULT_PAGE_NUMBER;//页码
	private int pageSize = DEFAULT_PAGE_SIZE;//每页记录数
	private String searchProperty;//搜索属性
	private String searchValue;//搜索值
	private String orderProperty;//排序属性
	private Order.Direction orderDirection;//排序方向
	private List<Filter> filters = new ArrayList<>();//筛选
	private List<Order> orders = new ArrayList<>();//排序

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		if (pageNumber < 1) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public String getSearchProperty() {
		return searchProperty;
	}

	public void setSearchProperty(String searchProperty) {
		this.searchProperty = searchProperty;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getOrderProperty() {
		return orderProperty;
	}

	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}

	public Order.Direction getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(Order.Direction orderDirection) {
		this.orderDirection = orderDirection;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	/**
	 * 初始化对象
	 */
	public Pageable() {
	}

	/**
	 * 初始化对象
	 *
	 * @param pageNumber 页码
	 * @param pageSize   每页记录数
	 */
	public Pageable(Integer pageNumber, Integer pageSize) {
		if (pageNumber != null && pageNumber >= 1) {
			this.pageNumber = pageNumber;
		}
		if (pageSize != null && pageSize >= 1 && pageSize <= MAX_PAGE_SIZE) {
			this.pageSize = pageSize;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Pageable other = (Pageable) obj;
		return new EqualsBuilder().append(getPageNumber(), other.getPageNumber()).append(getPageSize(), other.getPageSize()).append(getSearchProperty(), other.getSearchProperty()).append(getSearchValue(), other.getSearchValue()).append(getOrderProperty(), other.getOrderProperty()).append(getOrderDirection(), other.getOrderDirection()).append(getFilters(), other.getFilters())
				.append(getOrders(), other.getOrders()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPageNumber()).append(getPageSize()).append(getSearchProperty()).append(getSearchValue()).append(getOrderProperty()).append(getOrderDirection()).append(getFilters()).append(getOrders()).toHashCode();
	}
}