package com.iframe.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @Project: framework
 * @Title: DealDate.java
 * @Description: 针对日期方面的各种方法。
 * @Company:
 * @Author: 刘守建
 * @version 1.0
 */
public class DealDate {

	/**
	 * 判断参数是否为合法日期
	 * 
	 * @param str
	 *            日期字符串，格式为 yyyy-MM-dd
	 * @return 返回boolean true表示日期合法，false表示日期不合法
	 */
	public static boolean checkDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			formatter.parse(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据日期格式，判断参数是否为合法日期
	 * 
	 * @param str
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return 返回boolean true表示日期合法，false表示日期不合法
	 */
	public static boolean checkDate(String str, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			formatter.parse(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 将日期对象转换成指定格式的字符串
	 * 
	 * @param date
	 *            日期对象
	 * @param format
	 *            日期格式
	 * @return 返回的日期
	 * 
	 */
	public static String dateToString(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String senddate = sdf.format(date);
		return senddate;
	}

	/**
	 * 取得格式为yyyy-MM-dd HH:mm:ss 的系统日期
	 * 
	 * @return String 返回的日期
	 */
	public static String getDate() {
		Date date = new Date(); // 当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String senddate = sdf.format(date);
		return senddate;
	}

	/**
	 * 取得指定格式的系统日期
	 * 
	 * @param format
	 *            日期格式，如 "yyyy-MM-dd HH:mm"
	 * @return String 返回的日期
	 * 
	 */
	public static String getDate(String format) {
		Date date = new Date(); // 当前日期
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String senddate = sdf.format(date);
		return senddate;
	}

	/**
	 * 将字符串转换为Date类型，字符串默认格式为yyyy-MM-dd
	 * 
	 * @param str
	 *            日期字符串
	 * @return 返回的日期
	 */
	public static Date stringToDate(String str) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = formatter.parse(str);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将字符串转换为Date类型，字符串默认格式为HH：mm：ss
	 * 
	 * @param str
	 *            日期字符串
	 * @return 返回的日期
	 */
	public static Date stringToHour(String str) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		try {
			date = formatter.parse(str);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param str
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return 返回的日期
	 */
	public static Date stringToDate(String str, String format) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			date = formatter.parse(str);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param str
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @param locale
	 * @return 返回的日期
	 */
	public static Date stringToDate(String str, String format, Locale locale) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
		try {
			date = formatter.parse(str);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 计算两个日期相差的小时数
	 * 
	 * @param strDate1
	 *            开始日期
	 * @param strDate2
	 *            结束日期
	 * @return 返回两个日期相差的小时数
	 */
	public static float discrepancyByHour(String strDate1, String strDate2) {
		Date d1 = stringToHour(strDate1);
		Date d2 = stringToHour(strDate2);
		if (d1 == null || d2 == null) {
			return 0;
		}
		long l1 = d1.getTime();
		long l2 = d2.getTime();
		double difference = (l2 - l1) / (1000.0 * 60.0 * 60.0);
		float discrepancy = Float.parseFloat(String.valueOf(difference));
		return discrepancy;
	}

	/**
	 * 计算两个日期相差的天数
	 * 
	 * @param strDate1
	 *            开始日期
	 * @param strDate2
	 *            结束日期
	 * @return 返回两个日期相差的天数
	 */
	public static int discrepancy(String strDate1, String strDate2) {
		Date d1 = stringToDate(strDate1);
		Date d2 = stringToDate(strDate2);
		if (d1 == null || d2 == null) {
			return 0;
		}
		long l1 = d1.getTime();
		long l2 = d2.getTime();
		long difference = (l2 - l1) / (1000 * 60 * 60 * 24);
		int discrepancy = Integer.parseInt(String.valueOf(difference));
		return discrepancy + 1;
	}

	/**
	 * 计算两个日期相差的小时数
	 * 
	 * @param strDate1
	 *            开始日期
	 * @param strDate2
	 *            结束日期
	 * @return 返回两个日期相差的小时数
	 */
	public static int discrepancyByMM(String strDate1, String strDate2) {
		Date d1 = stringToDate(strDate1);
		Date d2 = stringToDate(strDate2);
		if (d1 == null || d2 == null) {
			return 0;
		}
		long l1 = d1.getTime();
		long l2 = d2.getTime();
		long difference = (l2 - l1) / (1000 * 60 * 60);
		int discrepancy = Integer.parseInt(String.valueOf(difference));
		return discrepancy + 1;
	}

	/**
	 * 计算两个日期相差的毫秒
	 * 
	 * @param strDate1
	 *            开始日期
	 * @param strDate2
	 *            结束日期
	 * @return 返回两个日期相差的天数
	 */
	public static long disMillisec(String strDate1, String strDate2) {
		Date d1 = stringToDate(strDate1, "yyyy-MM-dd HH:mm:ss");
		Date d2 = stringToDate(strDate2, "yyyy-MM-dd HH:mm:ss");
		if (d1 == null || d2 == null) {
			return 0;
		}
		return (d1.getTime() - d2.getTime());
	}

	/**
	 * 比较两个日期大小
	 * 
	 * @param strDate1
	 * @param strDate2
	 * @return 如果strDate2大于strDate1返回大于0的值，strDate2等于strDate1返回值0,
	 *         strDate2小于strDate1返回小于0的值
	 */
	public static int compareDate(String strDate1, String strDate2) {
		int value = 0;
		Date d1 = stringToDate(strDate1, "yyyy-MM-dd HH:mm:ss");
		Date d2 = stringToDate(strDate2, "yyyy-MM-dd HH:mm:ss");
		if (d1 == null || d2 == null) {
			return value;
		}
		value = d2.compareTo(d1);
		return value;
	}

	/**
	 * 根据日期取得星期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @return 返回星期
	 */
	public static int getWeek(String strDate) {
		Date date = stringToDate(strDate);
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(date.getTime());
		int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return week;
	}

	public static String getWeekString(String strDate) {
		String ret = "星期";
		int value = getWeek(strDate);
		switch (value) {
		case 1:
			ret += "一";
			break;
		case 2:
			ret += "二";
			break;
		case 3:
			ret += "三";
			break;
		case 4:
			ret += "四";
			break;
		case 5:
			ret += "五";
			break;
		case 6:
			ret += "六";
			break;
		case 0:
			ret += "日";
			break;
		}
		return ret;
	}

	/**
	 * 根据指定日期的月份的最大日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @return 返回星期
	 */
	public static int getMonthMaxDay(String strDate) {
		Date date = stringToDate(strDate, "yyyy-MM");
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(date.getTime());
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maxDay;
	}

	/**
	 * 根据指定日期的月份所有日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @return 返回星期
	 */
	public static String[] getMonths(String strDate) {
		Date date = stringToDate(strDate);
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(date.getTime());
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		String[] monthInfo = new String[maxDay];
		for (int i = 1; i <= maxDay; i++) {
			java.text.Format formatter3 = new SimpleDateFormat(
					"yyyy-MM-" + (i <= 9 ? "0" + i : i + ""));
			monthInfo[i - 1] = formatter3.format(calendar.getTime());
		}
		return monthInfo;
	}

	/**
	 * 取得系统前某天的日期
	 * 
	 * @param number
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String getBeforeDay(int number, String format) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -number);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(c.getTime());
	}

	/**
	 * 取得指定日期的前N天的日期
	 * 
	 * @param number
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String getBeforeDay(Date date, int number, String format) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, -number);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(c.getTime());
	}

	/**
	 * 取得当前日期的后N天的日期
	 * 
	 * @param number
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String getBehindDay(int number) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, number);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(c.getTime());
	}

	/**
	 * 取得当前日期的后N天的日期
	 * 
	 * @param number
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String getBehindDay(int number, String format) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, number);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(c.getTime());
	}
	
	/**
	 * 取得当前日期的后N天的日期
	 * 
	 * @param number
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String getBehindDay(long date,int number, String format) {
		Date dates = new Date(date);
		Calendar c = Calendar.getInstance();
		c.setTime(dates);
//		Calendar c = new GregorianCalendar(dates.getYear(), dates.getMonth(), dates.getDay());
//		Calendar c = new GregorianCalendar(dates.get, dates.getMonth(),
//				dates.getDay(), dates.getHours(), dates.getMinutes(),
//				dates.getSeconds());
		c.add(Calendar.DAY_OF_MONTH, number);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(c.getTime());
	}

	/**
	 * 取得指定日期的前N小时或后N小时的日期
	 * 
	 * @param number
	 *            可以负数
	 * @return
	 */
	public static String getBeforeTime(int number) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, number);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(c.getTime());
	}

	/**
	 * 取得指定日期的前N分或后N分的日期
	 * 
	 * @param number
	 *            可以负数
	 * @return
	 */
	public static String getBeforeMinute(int number) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, number);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(c.getTime());
	}

	/**
	 * 取得指定日期的前N分或后N分的日期
	 * 
	 * @param number
	 *            可以负数
	 * @return
	 */
	public static String getBeforeMinuteByHour(String strDate, int number) {
		Date date = stringToDate(strDate, "HH:mm:ss");
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(date.getTime());
		c.add(Calendar.MINUTE, number);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(c.getTime());
	}

	/**
	 * 取得指定日期的前N分或后N分的日期
	 * 
	 * @param number
	 *            可以负数
	 * @return
	 */
	public static String getBeforeMinute(String strDate, int number) {
		Date date = stringToDate(strDate, "yyyy-MM-dd HH:mm:ss");
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(date.getTime());
		c.add(Calendar.MINUTE, number);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(c.getTime());
	}

	/**
	 * 取得当前日期的后N月的日期
	 * 
	 * @param number
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String getBehindMonth(int number, String format) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, number);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String senddate = sdf.format(c.getTime());
		return senddate;
	}

	/**
	 * 取得指定日期的前后N月的日期
	 * @param date
	 * @param number
	 * @param format
	 * @return
	 */
	public static String getBehindMonth(long date, int number, String format) {
		Date dates = new Date(date);
		Calendar c = Calendar.getInstance();
		c.setTime(dates);
		c.add(Calendar.MONTH, number);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String senddate = sdf.format(c.getTime());
		return senddate;
	}

	/**
	 * 取得当前日期的后N年的日期
	 * 
	 * @param number
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String getBehindYear(int number, String format) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, number);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String senddate = sdf.format(c.getTime());
		return senddate;
	}

	/**
	 * long转string日期
	 * 
	 * @param number
	 *            可以负数
	 * @return
	 */
	public static String getLongByTime(long number) {
		Date date = new Date(number); // 当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);
		return strDate;
	}

	/**
	 * 转换日期格式
	 * 
	 * @param number
	 *            可以负数
	 * @return
	 */
	public static String getTime(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(stringToDate(date, "yyyy-MM-dd HH:mm:ss"));
		return strDate;
	}

	/**
	 * 取得当前日期的前N月的日期
	 * 
	 * @param number
	 * @return
	 */
	public static String getBeforeMonth(int number) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MONTH, -number);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}

	/**
	 * 取得当前日期的前N月的日期
	 * 
	 * @param number
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String getBeforeMonth(int number, String format) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MONTH, -number);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(c.getTime());
	}

	/**
	 * 取得当前日期的前N月的日期
	 * 
	 * @param number
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String getBeforeMonth(Date date, int number, String format) {
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(date.getTime());
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MONTH, -number);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(c.getTime());
	}

	/**
	 * 取得系统前某小时的日期
	 * 
	 * @param number
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String getBeforeHours(int number, String format) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, -number);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(c.getTime());
	}

	/**
	 * 取得当前日期的前N年的日期
	 * 
	 * @param number
	 * @param format
	 * @return
	 */
	public static String getBeforeYear(int number, String format) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -number);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(c.getTime());
	}

	/**
	 * 根据用户的生日,计算用户的年龄
	 * 
	 * @param time
	 * @return
	 */
	public static int getUserAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			return 0;
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		if (null == birthDay) {
			return 0;
		} else {
			cal.setTime(birthDay);

			int yearBirth = cal.get(Calendar.YEAR);
			int monthBirth = cal.get(Calendar.MONTH);
			int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

			int age = yearNow - yearBirth;

			if (monthNow <= monthBirth) {
				if (monthNow == monthBirth) {
					// monthNow==monthBirth
					if (dayOfMonthNow < dayOfMonthBirth) {
						age--;
					}
				} else {
					// monthNow>monthBirth
					age--;
				}
			}
			return age;
		}
	}

	/**
	 * 获取系统时间时间值
	 * 
	 * @param
	 * @return
	 */
	public static String getTime() {
		Date date = new Date(); // 当前日期
		long time1 = date.getTime() / 1000;
		return String.valueOf(time1);
	}

}
