package com.xcm.service;

import com.xcm.model.Log;

import java.util.Date;
import java.util.Map;

/**
 * Service - 日志
 */
public interface LogService extends BaseService<Log, Long> {

    /**
     * 分析pv、uv
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    Map<String, Object> analysis(Date startDate, Date endDate);
}
