package com.xcm.service.impl;

import com.xcm.dao.LogDao;
import com.xcm.model.Log;
import com.xcm.service.LogService;
import com.xcm.util.DateTimeUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service - 日志
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<Log, Long> implements LogService {

    @Autowired
    private LogDao logDao;

    @Autowired
    public void setBaseDao(LogDao LogDao) {
        super.setBaseDao(logDao);
    }

    @Override
    public Map<String, Object> analysis(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return Collections.emptyMap();
        }
        String startDateStr = DateTimeUtils.getFormatDate(startDate, DateTimeUtils.PART_DATE_FORMAT) + " 00:00:00";
        String endDateStr = DateTimeUtils.getFormatDate(endDate, DateTimeUtils.PART_DATE_FORMAT) + " 23:59:59";
        int days = DateTimeUtils.getDiffDays(startDate, endDate);
        long[] pvArr = new long[days];
        long[] uvArr = new long[days];
        List<String> dateArr = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            String date = DateTimeUtils.getFormatDate(DateUtils.addDays(startDate, i), DateTimeUtils.PART_DATE_FORMAT);
            dateArr.add(date);
        }
        List<Object[]> pv = logDao.pv(startDateStr, endDateStr);
        for (Object[] aPv : pv) {
            String date = aPv[0].toString();
            long count = Long.parseLong(aPv[1].toString());
            int index = dateArr.indexOf(date);
            if (index < 0) {
                continue;
            }
            pvArr[index] = count;
        }
        List<Object[]> uv = logDao.uv(startDateStr, endDateStr);
        for (Object[] anUv : uv) {
            String date = anUv[0].toString();
            long count = Long.parseLong(anUv[1].toString());
            int index = dateArr.indexOf(date);
            if (index < 0) {
                continue;
            }
            uvArr[index] = count;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("date", dateArr);
        data.put("pv", pvArr);
        data.put("uv", uvArr);
        return data;
    }
}