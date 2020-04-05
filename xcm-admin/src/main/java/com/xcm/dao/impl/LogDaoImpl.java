package com.xcm.dao.impl;

import com.xcm.dao.LogDao;
import com.xcm.model.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

/**
 * Dao - 日志
 */
@Repository
public class LogDaoImpl extends BaseDaoImpl<Log, Long> implements LogDao {

    @Override
    public List<Object[]> pv(String startDate, String endDate) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return Collections.emptyList();
        }
        String sql = "SELECT DATE_FORMAT(create_date,'%Y-%m-%d') AS date,count(1) AS c FROM sys_log WHERE create_date >= ? AND create_date <= ? GROUP BY date ORDER BY c DESC";
        Query query = entityManager.createNativeQuery(sql).setParameter(1, startDate).setParameter(2, endDate);
        return query.getResultList();
    }

    @Override
    public List<Object[]> uv(String startDate, String endDate) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            return Collections.emptyList();
        }
        String sql = "SELECT DATE_FORMAT(create_date,'%Y-%m-%d') AS date,count(DISTINCT ip) AS c FROM sys_log WHERE create_date >= ? AND create_date <= ? GROUP BY date ORDER BY c DESC";
        Query query = entityManager.createNativeQuery(sql).setParameter(1, startDate).setParameter(2, endDate);
        return query.getResultList();
    }
}