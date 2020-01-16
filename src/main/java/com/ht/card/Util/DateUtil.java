package com.ht.card.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static final SimpleDateFormat SDF_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat SDF_yyyyMMddHHmmss = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public static String getNowToDay () {
		return formatToDay(new Date());
	}
	
	public static String getNowToSecond() {
		return formatToSecond(new Date());
	}
	
	public static String formatToDay(Date date) {
		return SDF_yyyyMMdd.format(date);
	}
	
	public static String formatToSecond(Date date) {
		return SDF_yyyyMMddHHmmss.format(date);
	}
	
	/**
	 * 返回两个时间比较大的那个
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static synchronized Date getMaxDate(Date date1, Date date2) {
	    if (null == date1) return date2;
	    if (null == date2) return date1;
	    return date1.getTime() > date2.getTime() ? date1 : date2;
	}
}
