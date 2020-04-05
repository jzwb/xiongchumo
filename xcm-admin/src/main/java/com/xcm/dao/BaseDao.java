package com.xcm.dao;

import com.xcm.common.Filter;
import com.xcm.common.Order;
import com.xcm.common.Page;
import com.xcm.common.Pageable;

import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Dao - 基类
 */
public interface BaseDao<T, ID extends Serializable> {

	/**
	 * 查找实体对象
	 * 
	 * @param id ID
	 * @return 实体对象，若不存在则返回null
	 */
	T find(ID id);

	/**
	 * 查找实体对象
	 * 
	 * @param id ID
	 * @param lockModeType 锁定方式
	 * @return 实体对象，若不存在则返回null
	 */
	T find(ID id, LockModeType lockModeType);

	/**
	 * 查找实体对象集合
	 * 
	 * @param first 起始记录
	 * @param count 数量
	 * @param filters 筛选
	 * @param orders 排序
	 * @return 实体对象集合
	 */
	List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders);
	
	/**
	 * 获取实体类指定的字段属性集合
	 * 
	 * @param first 起始记录
	 * @param count 数量
	 * @param filters 筛选
	 * @param orders 排序
	 * @param properties 目标字段属性
	 * @return Object数组集合
	 */
	List<Object[]> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders, String[] properties);

	/**
	 *
	 * 获取实体类指定的字段属性集合
	 * @param first 起始记录
	 * @param count 数量
	 * @param filters 筛选
	 * @param orders 排序
	 * @param properties 目标字段属性
	 * @return Map<String,Object>集合
	 */
	List<Map<String, Object>> findListMap(Integer first, Integer count, List<Filter> filters, List<Order> orders, String... properties);

	/**
	 * 查找实体对象分页
	 * 
	 * @param pageable 分页信息
	 * @return 实体对象分页
	 */
	Page<T> findPage(Pageable pageable);
	
	/**
	 * 获取实体类指定的指定的字段属性集合（分页）
	 * @param pageable
	 * @param properties
	 * @return
	 */
	Page<Object[]> findPage(Pageable pageable, String[] properties);

	/**
	 * 获取实体类指定的指定的字段属性集合（分页）
	 * @param pageable
	 * @param properties
	 * @return
	 */
	Page<Map<String, Object>> findPageMap(Pageable pageable, String... properties);

	/**
	 * 查询实体对象数量
	 * 
	 * @param filters 筛选
	 * @return 实体对象数量
	 */
	long count(Filter... filters);

	/**
	 * 持久化实体对象
	 * 
	 * @param entity 实体对象
	 */
	void persist(T entity);

	/**
	 * 合并实体对象
	 * 
	 * @param entity 实体对象
	 * @return 实体对象
	 */
	T merge(T entity);

	/**
	 * 移除实体对象
	 * 
	 * @param entity 实体对象
	 */
	void remove(T entity);

	/**
	 * 刷新实体对象
	 * 
	 * @param entity 实体对象
	 */
	void refresh(T entity);

	/**
	 * 刷新实体对象
	 * 
	 * @param entity 实体对象
	 * @param lockModeType 锁定方式
	 */
	void refresh(T entity, LockModeType lockModeType);

	/**
	 * 获取实体对象ID
	 * 
	 * @param entity 实体对象
	 * @return 实体对象ID
	 */
	ID getIdentifier(T entity);

	/**
	 * 判断是否为托管状态
	 * 
	 * @param entity 实体对象
	 * @return 是否为托管状态
	 */
	boolean isManaged(T entity);

	/**
	 * 设置为游离状态
	 * 
	 * @param entity 实体对象
	 */
	void detach(T entity);

	/**
	 * 锁定实体对象
	 * 
	 * @param entity 实体对象
	 * @param lockModeType 锁定方式
	 */
	void lock(T entity, LockModeType lockModeType);

	/**
	 * 清除缓存
	 */
	void clear();

	/**
	 * 同步数据
	 */
	void flush();

}