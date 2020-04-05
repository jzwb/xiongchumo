package com.xcm.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utils - 日期
 */
public class DateTimeUtils extends DateUtils {

	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String PART_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 字符串转日期
	 *
	 * @param xDate
	 * @param xFormat
	 * @return
	 */
	public static Date parseString2Date(String xDate, String xFormat) {
		while (!isNotDate(xDate)) {
			xFormat = StringUtils.isNotEmpty(xFormat) ? xFormat : PART_DATE_FORMAT;
			SimpleDateFormat sdf = new SimpleDateFormat(xFormat);
			Date date = null;
			try {
				date = sdf.parse(xDate);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
			return date;
		}
		return null;
	}

	/**
	 * 日期转字符串
	 *
	 * @param date
	 * @param xFormat
	 * @return
	 */
	public static String getFormatDate(Date date, String xFormat) {
		date = date == null ? new Date() : date;
		xFormat = StringUtils.isNotEmpty(xFormat) ? xFormat : FULL_DATE_FORMAT;
		SimpleDateFormat sdf = new SimpleDateFormat(xFormat);
		return sdf.format(date);
	}


	/**
	 * 获取俩个日期之间相差天数
	 *
	 * @param dateX
	 * @param dateY
	 * @return
	 */
	public static int getDiffDays(Date dateX, Date dateY) {
		if ((dateX == null) || (dateY == null)) {
			return 0;
		}
		int dayX = (int) (dateX.getTime() / (60 * 60 * 1000 * 24));
		int dayY = (int) (dateY.getTime() / (60 * 60 * 1000 * 24));
		return dayX > dayY ? dayX - dayY : dayY - dayX;
	}

	/**
	 * 处理日期
	 *
	 * @param date        日期
	 * @param clearHour   清除小时
	 * @param clearMinute 清除分钟
	 * @param clearSecond 清除秒
	 * @return
	 */
	public static Date parse(Date date, boolean clearHour, boolean clearMinute, boolean clearSecond) {
		if (date == null) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (clearHour) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
		}
		if (clearMinute) {
			calendar.set(Calendar.MINUTE, 0);
		}
		if (clearSecond) {
			calendar.set(Calendar.SECOND, 0);
		}
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 是否是时间
	 *
	 * @param xDate
	 * @return
	 */
	public static boolean isNotDate(String xDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(PART_DATE_FORMAT);
		try {
			if (StringUtils.isEmpty(xDate)) {
				return true;
			}
			sdf.parse(xDate);
			return false;
		} catch (ParseException e) {
			return true;
		}
	}
}