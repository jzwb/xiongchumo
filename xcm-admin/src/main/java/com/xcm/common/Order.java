package com.xcm.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 通用 - 排序
 */
public class Order implements Serializable {

	private static final Direction DEFAULT_DIRECTION = Direction.desc;//默认方向

	/**
	 * 方向
	 */
	public enum Direction {

		asc("递增"),
		desc("递减");

		Direction(String description) {
			this.description = description;
		}

		public String description;

		public String getDescription() {
			return description;
		}
	}

	private String property;//属性
	private Direction direction = DEFAULT_DIRECTION;//方向

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * 初始化对象
	 */
	public Order() {
	}

	/**
	 * 初始化对象
	 *
	 * @param property  属性
	 * @param direction 方向
	 */
	public Order(String property, Direction direction) {
		this.property = property;
		this.direction = direction;
	}

	/**
	 * 返回递增排序
	 *
	 * @param property 属性
	 * @return
	 */
	public static Order asc(String property) {
		return new Order(property, Direction.asc);
	}

	/**
	 * 返回递减排序
	 *
	 * @param property 属性
	 * @return
	 */
	public static Order desc(String property) {
		return new Order(property, Direction.desc);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (this == obj)
			return true;
		Order other = (Order) obj;
		return new EqualsBuilder().append(getProperty(), other.getProperty()).append(getDirection(), other.getDirection()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getProperty()).append(getDirection()).toHashCode();
	}
}