package com.iframe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 系统日期和时间工具类
 * @author ld
 *
 */
public class CalendarUtils {

	private CalendarUtils() {
	}
	
	public static Calendar createCalendar(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar;
	}
	
	public static Calendar clearTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar;
	}
	
	public static long clearTime(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);

		return clearTime(calendar).getTimeInMillis();
	}
	
	public static String dayOfWeekString(String[] dayOfWeekStringArray, int dayOfWeek) {
		return dayOfWeekStringArray[dayOfWeek - 1];
	}
	
	public static Calendar YYYYMMDDHHMMSSToCalendar(String str) {
		if (str != null) {
			Calendar cal = Calendar.getInstance();
			try {
				SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
				cal.setTime(FORMAT.parse(str));
				return cal;
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	
	public static String toYYYYMMDDHHMMSS(Calendar source) {
		if (source == null) {
			return "Can't toYYYYMMDDHHMMSS source null";
		}
		int YYYY = source.get(Calendar.YEAR);
		int MM = source.get(Calendar.MONTH);
		int DD = source.get(Calendar.DAY_OF_MONTH);
		int HH = source.get(Calendar.HOUR_OF_DAY);
		int mm = source.get(Calendar.MINUTE);
		int SS = source.get(Calendar.SECOND);

		return YYYY + "-" + MM + "-" + DD + " " + HH + ":" + mm + ":" + SS;
	}

	public static String toYYYYMMDD(Calendar source) {
		if (source == null) {
			return "Can't toYYYYMMDDHHMMSS source null";
		}
		int YYYY = source.get(Calendar.YEAR);
		int MM = source.get(Calendar.MONTH);
		int DD = source.get(Calendar.DAY_OF_MONTH);

		return YYYY + "-" + MM + "-" + DD;
	}

	public static String toYYYYMM(Calendar source) {
		if (source != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
			return format.format(source.getTime());
		}
		return null;
	}

	public static String toHHMM(Calendar source) {
		if (source == null) {
			return "Can't HH:mm, source null";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
		//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
		String strDate = sdf.format(source.getTime());

		return strDate;
	}
	
	private static int[] rang = new int[] { 1000,// just
			1000 * 60,// just
			1000 * 60 * 3,// 3m
			1000 * 60 * 10,// 10m
			1000 * 60 * 30,// 30m
			1000 * 60 * 60,// 1h
			1000 * 60 * 60 * 2,// 2h
			1000 * 60 * 60 * 3,// 3h
			1000 * 60 * 60 * 6,// 6h
			1000 * 60 * 60 * 24,// 1d
			1000 * 60 * 60 * 24 * 3,// 3day
			1000 * 60 * 60 * 24 * 7,// week
	};

	public static int getPassTime(Calendar source) {
		Calendar now = Calendar.getInstance();

		long time = now.getTimeInMillis() - source.getTimeInMillis();
		
		if(time<0){
			return 0;
		}

		for (int i = rang.length - 1; i >= 0; i--) {
			if (time > rang[i]) {
				return i;
			}
		}
		return rang.length - 1;
	}
}
