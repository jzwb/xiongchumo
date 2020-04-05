package com.xcm.dao;

import com.xcm.model.Log;
import java.util.List;

/**
 * Dao - 日志
 */
public interface LogDao extends BaseDao<Log, Long> {

    /**
     * PV
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    List<Object[]> pv(String startDate, String endDate);

    /**
     * UV
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    List<Object[]> uv(String startDate, String endDate);
}