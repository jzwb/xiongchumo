package com.xcm.dao.impl;

import com.xcm.common.Filter;
import com.xcm.common.Order;
import com.xcm.common.Page;
import com.xcm.common.Pageable;
import com.xcm.dao.BaseDao;
import com.xcm.model.OrderEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao - 基类
 */
public abstract class BaseDaoImpl<T, ID extends Serializable> implements BaseDao<T, ID> {

	/**
	 * 实体类类型
	 */
	private Class<T> entityClass;

	/**
	 * 别名数
	 */
	private static volatile long aliasCount = 0;

	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Type type = getClass().getGenericSuperclass();
		Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
		entityClass = (Class<T>) parameterizedType[0];
	}

	public T find(ID id) {
		if (id != null)
			return entityManager.find(entityClass, id);
		return null;
	}

	public T find(ID id, LockModeType lockModeType) {
		if (id != null) {
			if (lockModeType != null)
				return entityManager.find(entityClass, id, lockModeType);
			else
				return entityManager.find(entityClass, id);
		}
		return null;
	}

	public List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return findList(criteriaQuery, first, count, filters, orders);
	}

	public List<Object[]> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders, String[] properties) {
		if (properties == null || properties.length <= 0) {
			return null;
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<T> root = criteriaQuery.from(entityClass);
		List<Selection<?>> list = new ArrayList<Selection<?>>();
		for (int i = 0, len = properties.length; i < len; i++) {
			list.add(root.get(properties[i]));
		}
		criteriaQuery.multiselect(list);
		return findList(criteriaQuery, root, first, count, filters, orders);
	}

	@Override
	public List<Map<String, Object>> findListMap(Integer first, Integer count, List<Filter> filters, List<Order> orders, String... properties) {
		List<Object[]> list = findList(first, count, filters, orders, properties);
		if (list == null || list.isEmpty()) {
			return Collections.emptyList();
		}
		List<Map<String, Object>> listMap = new ArrayList<>();
		if (properties.length == 1) {
			for (Object object : list) {
				Map<String, Object> map = new HashMap<>();
				map.put(properties[0], object);
				listMap.add(map);
			}
		} else {
			for (Object[] objects : list) {
				Map<String, Object> map = new HashMap<>();
				for (int i = 0; i < properties.length; i++) {
					map.put(properties[i], objects[i]);
				}
				listMap.add(map);
			}
		}
		return listMap;
	}

	public Page<T> findPage(Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return findPage(criteriaQuery, pageable);
	}

	public Page<Object[]> findPage(Pageable pageable, String[] properties) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<T> root = criteriaQuery.from(entityClass);
		List<Selection<?>> list = new ArrayList<Selection<?>>();
		for (int i = 0, len = properties.length; i < len; i++) {
			list.add(root.get(properties[i]));
		}
		criteriaQuery.multiselect(list);
		return findPage(criteriaQuery, pageable, root);
	}

	@Override
	public Page<Map<String, Object>> findPageMap(Pageable pageable, String... properties) {
		Page<Object[]> page = findPage(pageable, properties);
		long total = page.getTotal();
		List<Object[]> list = page.getContent();
		if (list == null || list.isEmpty()) {
			return new Page<>(Collections.emptyList(), total, pageable);
		}
		List<Map<String, Object>> listMap = new ArrayList<>();
		if (properties.length == 1) {
			for (Object object : list) {
				Map<String, Object> map = new HashMap<>();
				map.put(properties[0], object);
				listMap.add(map);
			}
		} else {
			for (Object[] objects : list) {
				Map<String, Object> map = new HashMap<>();
				for (int i = 0; i < properties.length; i++) {
					map.put(properties[i], objects[i]);
				}
				listMap.add(map);
			}
		}
		return new Page<Map<String, Object>>(listMap, total, pageable);
	}

	public long count(Filter... filters) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return count(criteriaQuery, filters != null ? Arrays.asList(filters) : null);
	}

	public void persist(T entity) {
		entityManager.persist(entity);
	}

	public T merge(T entity) {
		return entityManager.merge(entity);
	}

	public void remove(T entity) {
		if (entity != null)
			entityManager.remove(entity);
	}

	public void refresh(T entity) {
		if (entity != null)
			entityManager.refresh(entity);
	}

	public void refresh(T entity, LockModeType lockModeType) {
		if (entity != null) {
			if (lockModeType != null)
				entityManager.refresh(entity, lockModeType);
			else
				entityManager.refresh(entity);
		}
	}

	@SuppressWarnings("unchecked")
	public ID getIdentifier(T entity) {
		return (ID) entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
	}

	public boolean isManaged(T entity) {
		return entityManager.contains(entity);
	}

	public void detach(T entity) {
		entityManager.detach(entity);
	}

	public void lock(T entity, LockModeType lockModeType) {
		if (entity != null && lockModeType != null)
			entityManager.lock(entity, lockModeType);
	}

	public void clear() {
		entityManager.clear();
	}

	public void flush() {
		entityManager.flush();
	}

	protected List<T> findList(CriteriaQuery<T> criteriaQuery, Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Root<T> root = getRoot(criteriaQuery);
		addRestrictions(criteriaQuery, filters);
		addOrders(criteriaQuery, orders);
		if (criteriaQuery.getOrderList().isEmpty()) {
			if (OrderEntity.class.isAssignableFrom(entityClass))
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(OrderEntity.ORDER_PROPERTY_NAME)));
			else
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(OrderEntity.CREATE_DATE_PROPERTY_NAME)));
		}
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		if (first != null)
			query.setFirstResult(first);
		if (count != null)
			query.setMaxResults(count);
		return query.getResultList();
	}

	protected List<Object[]> findList(CriteriaQuery<Object[]> criteriaQuery, Root<T> root, Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		addRestrictions(criteriaQuery, root, filters);
		addOrders(criteriaQuery, root, orders);
		if (criteriaQuery.getOrderList().isEmpty()) {
			if (OrderEntity.class.isAssignableFrom(entityClass))
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(OrderEntity.ORDER_PROPERTY_NAME)));
			else
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(OrderEntity.CREATE_DATE_PROPERTY_NAME)));
		}
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		if (first != null)
			query.setFirstResult(first);
		if (count != null)
			query.setMaxResults(count);
		return query.getResultList();
	}

	protected Page<T> findPage(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
		if (pageable == null)
			pageable = new Pageable();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Root<T> root = getRoot(criteriaQuery);
		addRestrictions(criteriaQuery, pageable);
		addOrders(criteriaQuery, pageable);
		if (criteriaQuery.getOrderList().isEmpty()) {
			if (OrderEntity.class.isAssignableFrom(entityClass))
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(OrderEntity.ORDER_PROPERTY_NAME)));
			else
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(OrderEntity.CREATE_DATE_PROPERTY_NAME)));
		}
		long total = count(criteriaQuery, null);
		int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
		if (totalPages < pageable.getPageNumber())
			pageable.setPageNumber(totalPages);
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		return new Page<T>(query.getResultList(), total, pageable);
	}

	protected Page<T> findPageByIds(CriteriaQuery<T> criteriaQuery, Page<Object[]> page) {
		Pageable pageable = page.getPageable();
		if (pageable == null) {
			pageable = new Pageable();
		}
		if (page.getContent() == null || page.getTotal() <= 0) {
			return new Page<T>(new ArrayList<T>(), page.getTotal(), pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		if (criteriaQuery == null) {
			criteriaQuery = criteriaBuilder.createQuery(entityClass);
			criteriaQuery.select(criteriaQuery.from(entityClass));
		}
		Root<T> root = getRoot(criteriaQuery);
		addOrders(criteriaQuery, pageable);
		if (criteriaQuery.getOrderList().isEmpty()) {
			if (OrderEntity.class.isAssignableFrom(entityClass))
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(OrderEntity.ORDER_PROPERTY_NAME)));
			else
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(OrderEntity.CREATE_DATE_PROPERTY_NAME)));
		}
		List<Object[]> objs = page.getContent();
		List<Long> ids = new ArrayList<Long>();
		for (int j = 0; j < objs.size(); j++) {
			ids.add(Long.valueOf(String.valueOf(objs.get(j))));
		}
		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, root.get("id").in(ids));
		criteriaQuery.where(restrictions);
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		return new Page<T>(query.getResultList(), page.getTotal(), pageable);
	}

	protected Page<Object[]> findPage(CriteriaQuery<Object[]> criteriaQuery, Pageable pageable, Root<T> root) {
		if (pageable == null)
			pageable = new Pageable();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		addRestrictions(criteriaQuery, pageable, root);
		addOrders(criteriaQuery, pageable, root);
		if (criteriaQuery.getOrderList().isEmpty()) {
			if (OrderEntity.class.isAssignableFrom(entityClass))
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(OrderEntity.ORDER_PROPERTY_NAME)));
			else
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(OrderEntity.CREATE_DATE_PROPERTY_NAME)));
		}
		long total = count(criteriaQuery, null, root);
		int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
		if (totalPages < pageable.getPageNumber())
			pageable.setPageNumber(totalPages);
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		return new Page<Object[]>(query.getResultList(), total, pageable);
	}

	protected Long count(CriteriaQuery<T> criteriaQuery, List<Filter> filters) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		addRestrictions(criteriaQuery, filters);
		CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
		for (Root<?> root : criteriaQuery.getRoots()) {
			Root<?> dest = countCriteriaQuery.from(root.getJavaType());
			dest.alias(getAlias(root));
			copyJoins(root, dest);
		}
		Root<?> countRoot = getRoot(countCriteriaQuery, criteriaQuery.getResultType());
		countCriteriaQuery.select(criteriaBuilder.count(countRoot));

		if (criteriaQuery.getGroupList() != null)
			countCriteriaQuery.groupBy(criteriaQuery.getGroupList());
		if (criteriaQuery.getGroupRestriction() != null)
			countCriteriaQuery.having(criteriaQuery.getGroupRestriction());
		if (criteriaQuery.getRestriction() != null)
			countCriteriaQuery.where(criteriaQuery.getRestriction());
		return entityManager.createQuery(countCriteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
	}

	protected Long count(CriteriaQuery<Object[]> criteriaQuery, List<Filter> filters, Root<T> root) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		addRestrictions(criteriaQuery, root, filters);
		CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
		for (Root<?> rootItem : criteriaQuery.getRoots()) {
			Root<?> dest = countCriteriaQuery.from(rootItem.getJavaType());
			dest.alias(getAlias(rootItem));
			copyJoins(rootItem, dest);
		}
		Root<?> countRoot = root;
		countCriteriaQuery.select(criteriaBuilder.count(countRoot));

		if (criteriaQuery.getGroupList() != null)
			countCriteriaQuery.groupBy(criteriaQuery.getGroupList());
		if (criteriaQuery.getGroupRestriction() != null)
			countCriteriaQuery.having(criteriaQuery.getGroupRestriction());
		if (criteriaQuery.getRestriction() != null)
			countCriteriaQuery.where(criteriaQuery.getRestriction());
		return entityManager.createQuery(countCriteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
	}

	private synchronized String getAlias(Selection<?> selection) {
		if (selection != null) {
			String alias = selection.getAlias();
			if (alias == null) {
				if (aliasCount >= 1000)
					aliasCount = 0;
				alias = "shopxxGeneratedAlias" + aliasCount++;
				selection.alias(alias);
			}
			return alias;
		}
		return null;
	}

	private Root<T> getRoot(CriteriaQuery<T> criteriaQuery) {
		if (criteriaQuery != null)
			return getRoot(criteriaQuery, criteriaQuery.getResultType());
		return null;
	}

	private Root<T> getRoot(CriteriaQuery<?> criteriaQuery, Class<T> clazz) {
		if (criteriaQuery != null && criteriaQuery.getRoots() != null && clazz != null) {
			for (Root<?> root : criteriaQuery.getRoots()) {
				if (clazz.equals(root.getJavaType()))
					return (Root<T>) root.as(clazz);
			}
		}
		return null;
	}

	private void copyJoins(From<?, ?> from, From<?, ?> to) {
		for (Join<?, ?> join : from.getJoins()) {
			Join<?, ?> toJoin = to.join(join.getAttribute().getName(), join.getJoinType());
			toJoin.alias(getAlias(join));
			copyJoins(join, toJoin);
		}
		for (Fetch<?, ?> fetch : from.getFetches()) {
			Fetch<?, ?> toFetch = to.fetch(fetch.getAttribute().getName());
			copyFetches(fetch, toFetch);
		}
	}

	private void copyFetches(Fetch<?, ?> from, Fetch<?, ?> to) {
		for (Fetch<?, ?> fetch : from.getFetches()) {
			Fetch<?, ?> toFetch = to.fetch(fetch.getAttribute().getName());
			copyFetches(fetch, toFetch);
		}
	}

	private void addRestrictions(CriteriaQuery<T> criteriaQuery, List<Filter> filters) {
		if (criteriaQuery == null || filters == null || filters.isEmpty())
			return;
		Root<T> root = getRoot(criteriaQuery);
		if (root == null)
			return;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		for (Filter filter : filters) {
			if (filter == null || StringUtils.isEmpty(filter.getProperty()))
				continue;
			if (filter.getOperator() == Filter.Operator.eq && filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String)
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(criteriaBuilder.lower(root.<String>get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
				else
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(filter.getProperty()), filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.ne && filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String)
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(criteriaBuilder.lower(root.<String>get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
				else
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get(filter.getProperty()), filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.gt && filter.getValue() != null) {
				if (filter.getValue() instanceof Date) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThan(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
				}
			} else if (filter.getOperator() == Filter.Operator.lt && filter.getValue() != null) {
				if (filter.getValue() instanceof Date) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThan(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lt(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
				}

			} else if (filter.getOperator() == Filter.Operator.ge && filter.getValue() != null) {
				if (filter.getValue() instanceof Date) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
				}
			} else if (filter.getOperator() == Filter.Operator.le && filter.getValue() != null) {
				if (filter.getValue() instanceof Date) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
				}
			} else if (filter.getOperator() == Filter.Operator.like && filter.getValue() != null && filter.getValue() instanceof String) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get(filter.getProperty()), (String) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.in && filter.getValue() != null) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).in(filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.isNull) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNull());
			} else if (filter.getOperator() == Filter.Operator.isNotNull) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNotNull());
			}
		}
		criteriaQuery.where(restrictions);
	}

	private void addRestrictions(CriteriaQuery<Object[]> criteriaQuery, Root<T> root, List<Filter> filters) {
		if (criteriaQuery == null || filters == null || filters.isEmpty())
			return;
		if (root == null)
			return;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		for (Filter filter : filters) {
			if (filter == null || StringUtils.isEmpty(filter.getProperty()))
				continue;
			if (filter.getOperator() == Filter.Operator.eq && filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String)
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(criteriaBuilder.lower(root.<String>get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
				else
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(filter.getProperty()), filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.ne && filter.getValue() != null) {
				if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String)
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(criteriaBuilder.lower(root.<String>get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
				else
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get(filter.getProperty()), filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.gt && filter.getValue() != null) {
				if (filter.getValue() instanceof Date) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThan(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
				}
			} else if (filter.getOperator() == Filter.Operator.lt && filter.getValue() != null) {
				if (filter.getValue() instanceof Date) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThan(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lt(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
				}

			} else if (filter.getOperator() == Filter.Operator.ge && filter.getValue() != null) {
				if (filter.getValue() instanceof Date) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
				}
			} else if (filter.getOperator() == Filter.Operator.le && filter.getValue() != null) {
				if (filter.getValue() instanceof Date) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
				} else {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
				}
			} else if (filter.getOperator() == Filter.Operator.like && filter.getValue() != null && filter.getValue() instanceof String) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get(filter.getProperty()), (String) filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.in && filter.getValue() != null) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).in(filter.getValue()));
			} else if (filter.getOperator() == Filter.Operator.isNull) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNull());
			} else if (filter.getOperator() == Filter.Operator.isNotNull) {
				restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNotNull());
			}
		}
		criteriaQuery.where(restrictions);
	}

	private void addRestrictions(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
		if (criteriaQuery == null || pageable == null)
			return;
		Root<T> root = getRoot(criteriaQuery);
		if (root == null)
			return;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		if (StringUtils.isNotEmpty(pageable.getSearchProperty()) && StringUtils.isNotEmpty(pageable.getSearchValue()))
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get(pageable.getSearchProperty()), "%" + pageable.getSearchValue() + "%"));
		if (pageable.getFilters() != null) {
			for (Filter filter : pageable.getFilters()) {
				if (filter == null || StringUtils.isEmpty(filter.getProperty()))
					continue;
				if (filter.getOperator() == Filter.Operator.eq && filter.getValue() != null) {
					if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String)
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(criteriaBuilder.lower(root.<String>get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
					else
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(filter.getProperty()), filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.ne && filter.getValue() != null) {
					if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String)
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(criteriaBuilder.lower(root.<String>get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
					else
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get(filter.getProperty()), filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.gt && filter.getValue() != null) {
					if (filter.getValue() instanceof Date) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThan(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
					}
				} else if (filter.getOperator() == Filter.Operator.lt && filter.getValue() != null) {
					if (filter.getValue() instanceof Date) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThan(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lt(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
					}

				} else if (filter.getOperator() == Filter.Operator.ge && filter.getValue() != null) {
					if (filter.getValue() instanceof Date) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
					}
				} else if (filter.getOperator() == Filter.Operator.le && filter.getValue() != null) {
					if (filter.getValue() instanceof Date) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
					}
				} else if (filter.getOperator() == Filter.Operator.like && filter.getValue() != null && filter.getValue() instanceof String) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get(filter.getProperty()), (String) filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.in && filter.getValue() != null) {
					restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).in(filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.isNull) {
					restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNull());
				} else if (filter.getOperator() == Filter.Operator.isNotNull) {
					restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNotNull());
				}
			}
		}
		criteriaQuery.where(restrictions);
	}

	private void addRestrictions(CriteriaQuery<Object[]> criteriaQuery, Pageable pageable, Root<T> root) {
		if (criteriaQuery == null || pageable == null)
			return;
		if (root == null)
			return;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		Predicate restrictions = criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder.conjunction();
		if (StringUtils.isNotEmpty(pageable.getSearchProperty()) && StringUtils.isNotEmpty(pageable.getSearchValue()))
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get(pageable.getSearchProperty()), "%" + pageable.getSearchValue() + "%"));
		if (pageable.getFilters() != null) {
			for (Filter filter : pageable.getFilters()) {
				if (filter == null || StringUtils.isEmpty(filter.getProperty()))
					continue;
				if (filter.getOperator() == Filter.Operator.eq && filter.getValue() != null) {
					if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String)
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(criteriaBuilder.lower(root.<String>get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
					else
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(filter.getProperty()), filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.ne && filter.getValue() != null) {
					if (filter.getIgnoreCase() != null && filter.getIgnoreCase() && filter.getValue() instanceof String)
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(criteriaBuilder.lower(root.<String>get(filter.getProperty())), ((String) filter.getValue()).toLowerCase()));
					else
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get(filter.getProperty()), filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.gt && filter.getValue() != null) {
					if (filter.getValue() instanceof Date) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThan(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
					}
				} else if (filter.getOperator() == Filter.Operator.lt && filter.getValue() != null) {
					if (filter.getValue() instanceof Date) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThan(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lt(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
					}

				} else if (filter.getOperator() == Filter.Operator.ge && filter.getValue() != null) {
					if (filter.getValue() instanceof Date) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
					}
				} else if (filter.getOperator() == Filter.Operator.le && filter.getValue() != null) {
					if (filter.getValue() instanceof Date) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get(filter.getProperty()), (Date) filter.getValue()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get(filter.getProperty()), (Number) filter.getValue()));
					}
				} else if (filter.getOperator() == Filter.Operator.like && filter.getValue() != null && filter.getValue() instanceof String) {
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get(filter.getProperty()), (String) filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.in && filter.getValue() != null) {
					restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).in(filter.getValue()));
				} else if (filter.getOperator() == Filter.Operator.isNull) {
					restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNull());
				} else if (filter.getOperator() == Filter.Operator.isNotNull) {
					restrictions = criteriaBuilder.and(restrictions, root.get(filter.getProperty()).isNotNull());
				}
			}
		}
		criteriaQuery.where(restrictions);
	}

	private void addOrders(CriteriaQuery<T> criteriaQuery, List<Order> orders) {
		if (criteriaQuery == null || orders == null || orders.isEmpty())
			return;
		Root<T> root = getRoot(criteriaQuery);
		if (root == null)
			return;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		if (!criteriaQuery.getOrderList().isEmpty())
			orderList.addAll(criteriaQuery.getOrderList());
		for (Order order : orders) {
			String orderProperty = order.getProperty();
			if (StringUtils.isNotBlank(orderProperty)) {
				String[] properties = StringUtils.split(orderProperty, ".");
				Path<Object> path = root.get(properties[0]);
				if (properties.length > 1) {
					for (int i = 1; i < properties.length; i++) {
						path = path.get(properties[i]);
					}
				}
				if (order.getDirection() == Order.Direction.asc) {
					orderList.add(criteriaBuilder.asc(path));
				} else if (order.getDirection() == Order.Direction.desc) {
					orderList.add(criteriaBuilder.desc(path));
				}
			}
		}
		criteriaQuery.orderBy(orderList);
	}

	private void addOrders(CriteriaQuery<Object[]> criteriaQuery, Root<T> root, List<Order> orders) {
		if (criteriaQuery == null || orders == null || orders.isEmpty())
			return;
		if (root == null)
			return;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		if (!criteriaQuery.getOrderList().isEmpty())
			orderList.addAll(criteriaQuery.getOrderList());
		for (Order order : orders) {
			String orderProperty = order.getProperty();
			if (StringUtils.isNotBlank(orderProperty)) {
				String[] properties = StringUtils.split(orderProperty, ".");
				Path<Object> path = root.get(properties[0]);
				if (properties.length > 1) {
					for (int i = 1; i < properties.length; i++) {
						path = path.get(properties[i]);
					}
				}
				if (order.getDirection() == Order.Direction.asc) {
					orderList.add(criteriaBuilder.asc(path));
				} else if (order.getDirection() == Order.Direction.desc) {
					orderList.add(criteriaBuilder.desc(path));
				}
			}
		}
		criteriaQuery.orderBy(orderList);
	}

	private void addOrders(CriteriaQuery<T> criteriaQuery, Pageable pageable) {
		if (criteriaQuery == null || pageable == null)
			return;
		Root<T> root = getRoot(criteriaQuery);
		if (root == null)
			return;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		if (!criteriaQuery.getOrderList().isEmpty())
			orderList.addAll(criteriaQuery.getOrderList());
		if (StringUtils.isNotEmpty(pageable.getOrderProperty()) && pageable.getOrderDirection() != null) {
			if (pageable.getOrderDirection() == Order.Direction.asc)
				orderList.add(criteriaBuilder.asc(root.get(pageable.getOrderProperty())));
			else if (pageable.getOrderDirection() == Order.Direction.desc)
				orderList.add(criteriaBuilder.desc(root.get(pageable.getOrderProperty())));
		}
		if (pageable.getOrders() != null) {
			for (Order order : pageable.getOrders()) {
				String orderProperty = order.getProperty();
				if (StringUtils.isNotBlank(orderProperty)) {
					String[] properties = StringUtils.split(orderProperty, ".");
					Path<Object> path = root.get(properties[0]);
					if (properties.length > 1) {
						for (int i = 1; i < properties.length; i++) {
							path = path.get(properties[i]);
						}
					}
					if (order.getDirection() == Order.Direction.asc)
						orderList.add(criteriaBuilder.asc(path));
					else if (order.getDirection() == Order.Direction.desc)
						orderList.add(criteriaBuilder.desc(path));
				}
			}
		}
		criteriaQuery.orderBy(orderList);
	}

	private void addOrders(CriteriaQuery<Object[]> criteriaQuery, Pageable pageable, Root<T> root) {
		if (criteriaQuery == null || pageable == null)
			return;
		if (root == null)
			return;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		if (!criteriaQuery.getOrderList().isEmpty())
			orderList.addAll(criteriaQuery.getOrderList());
		if (StringUtils.isNotEmpty(pageable.getOrderProperty()) && pageable.getOrderDirection() != null) {
			if (pageable.getOrderDirection() == Order.Direction.asc)
				orderList.add(criteriaBuilder.asc(root.get(pageable.getOrderProperty())));
			else if (pageable.getOrderDirection() == Order.Direction.desc)
				orderList.add(criteriaBuilder.desc(root.get(pageable.getOrderProperty())));
		}
		if (pageable.getOrders() != null) {
			for (Order order : pageable.getOrders()) {
				String orderProperty = order.getProperty();
				if (StringUtils.isNotBlank(orderProperty)) {
					String[] properties = StringUtils.split(orderProperty, ".");
					Path<Object> path = root.get(properties[0]);
					if (properties.length > 1) {
						for (int i = 1; i < properties.length; i++) {
							path = path.get(properties[i]);
						}
					}
					if (order.getDirection() == Order.Direction.asc)
						orderList.add(criteriaBuilder.asc(path));
					else if (order.getDirection() == Order.Direction.desc)
						orderList.add(criteriaBuilder.desc(path));
				}
			}
		}
		criteriaQuery.orderBy(orderList);
	}
}