package com.xcm.dao;

import com.xcm.model.Producer;

import java.util.List;

/**
 * Dao - 生产商
 */
public interface ProducerDao extends BaseDao<Producer, Long> {

    /**
     * 列表
     *
     * @param pageNumber 页码
     * @param pageSize   页数量
     * @param type       生产商类型
     * @param sortType   排序类型
     * @return
     */
    List<Producer> findList(Integer pageNumber, Integer pageSize, Producer.Type type, Producer.SortType sortType);
}