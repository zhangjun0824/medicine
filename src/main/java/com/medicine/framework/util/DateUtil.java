package com.medicine.framework.util;


import com.medicine.framework.exception.BusinessException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// TODO: Auto-generated Javadoc

/**
 * The Class DateUtil.
 *
 * @author zy
 * @version 1.0
 */
public class DateUtil {

	public static final int FIRST_DAY_OF_WEEK = Calendar.MONDAY; // 中国周一是一周的第一天
	
	/**
	 * To format.
	 *
	 * @param date the date
	 * @param format the format
	 * @return the date
	 */
	public static Date toFormat(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(date);
		try {
			return formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * To format string.
	 *
	 * @param date the date
	 * @param format the format
	 * @return the string
	 */
	public static String toFormatString(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	
	/**
	 * To format.
	 *
	 * @param date the date
	 * @param timeFormat the time format
	 * @param time the time
	 * @return the date
	 */
	public static Date toFormat(Date date, String timeFormat, String time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);

		SimpleDateFormat newformatter = new SimpleDateFormat("yyyy-MM-dd " + timeFormat);
		try {
			return newformatter.parse(dateString + " " + time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * To max day.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static Date toMaxDay(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);

		SimpleDateFormat newformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return newformatter.parse(dateString + " " + "23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * To day.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static Date toDay(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		try {
			return formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * From string.
	 *
	 * @param dateString the date string
	 * @param dateFormat the date format
	 * @return the date
	 */
	public static Date FromString(String dateString,String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		
		try {
			return formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * @param strDate
	 * @return
	 */
	public static Date parseDate(String strDate) throws BusinessException {
		return parseDate(strDate, null);
	}

	/**
	 * parseDate
	 *
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String strDate, String pattern) throws BusinessException {
		Date date = null;
		try {
			if (pattern == null) {
				pattern = "yyyy-MM-dd";
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			date = format.parse(strDate);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return date;
	}

	/**
	 * format date
	 *
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) throws BusinessException {
		return formatDate(date, null);
	}

	/**
	 * format date
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) throws BusinessException {
		String strDate = null;
		try {
			if (pattern == null) {
				pattern = "yyyy-MM-dd";
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			strDate = format.format(date);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return strDate;
	}
	/**
	 *
	 * @param strDate
	 * @return
	 */
	public static Date parseDateTime(String strDate) throws BusinessException {
		return parseDateTime(strDate, null);
	}

	/**
	 * parseDate
	 *
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date parseDateTime(String strDate, String pattern) throws BusinessException {
		Date date = null;
		try {
			if (pattern == null) {
				pattern = "yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			date = format.parse(strDate);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return date;
	}

	/**
	 * format date
	 *
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) throws BusinessException {
		return formatDateTime(date, null);
	}

	/**
	 * format date
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDateTime(Date date, String pattern) throws BusinessException {
		String strDate = null;
		try {
			if (pattern == null) {
				pattern = "yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			strDate = format.format(date);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return strDate;
	}
	/**
	 * 取得日期：年
	 *
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 取得日期：月
	 *
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		return month + 1;
	}

	/**
	 * 取得日期：日
	 *
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int da = c.get(Calendar.DAY_OF_MONTH);
		return da;
	}

	/**
	 * 取得当天日期是周几
	 *
	 * @param date
	 * @return
	 */
	public static int getWeekDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int week_of_year = c.get(Calendar.DAY_OF_WEEK);
		return week_of_year - 1;
	}

	/**
	 * 取得一年的第几周
	 *
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int week_of_year = c.get(Calendar.WEEK_OF_YEAR);
		return week_of_year;
	}

	/**
	 * getWeekBeginAndEndDate
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getWeekBeginAndEndDate(Date date, String pattern) throws BusinessException {
		Date monday = getMondayOfWeek(date);
		Date sunday = getSundayOfWeek(date);
		return formatDate(monday, pattern) + " - " + formatDate(sunday, pattern);
	}

	/**
	 * 根据日期取得对应周周一日期
	 *
	 * @param date
	 * @return
	 */
	public static Date getMondayOfWeek(Date date) {
		Calendar monday = Calendar.getInstance();
		monday.setTime(date);
		monday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
		monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return monday.getTime();
	}

	/**
	 * 根据日期取得对应周周日日期
	 *
	 * @param date
	 * @return
	 */
	public static Date getSundayOfWeek(Date date) {
		Calendar sunday = Calendar.getInstance();
		sunday.setTime(date);
		sunday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
		sunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return sunday.getTime();
	}

	/**
	 * 取得月的剩余天数
	 *
	 * @param date
	 * @return
	 */
	public static int getRemainDayOfMonth(Date date) {
		int dayOfMonth = getDayOfMonth(date);
		int day = getPassDayOfMonth(date);
		return dayOfMonth - day;
	}

	/**
	 * 取得月已经过的天数
	 *
	 * @param date
	 * @return
	 */
	public static int getPassDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 取得月天数
	 *
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 取得月第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * 取得月最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * 取得季度第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDateOfQuarter(Date date) {
		return getFirstDateOfMonth(getQuarterDate(date)[0]);
	}

	/**
	 * 取得季度最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDateOfQuarter(Date date) {
		return getLastDateOfMonth(getQuarterDate(date)[2]);
	}

	/**
	 * 取得季度天数
	 *
	 * @param date
	 * @return
	 */
	public static int getDayOfQuarter(Date date) {
		int day = 0;
		Date[] quarterDates = getQuarterDate(date);
		for (Date date2 : quarterDates) {
			day += getDayOfMonth(date2);
		}
		return day;
	}

	/**
	 * 取得季度剩余天数
	 *
	 * @param date
	 * @return
	 */
	public static int getRemainDayOfQuarter(Date date) {
		return getDayOfQuarter(date) - getPassDayOfQuarter(date);
	}

	/**
	 * 取得季度已过天数
	 *
	 * @param date
	 * @return
	 */
	public static int getPassDayOfQuarter(Date date) {
		int day = 0;

		Date[] quarterDates = getQuarterDate(date);

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);

		if (month == Calendar.JANUARY || month == Calendar.APRIL
				|| month == Calendar.JULY || month == Calendar.OCTOBER) {// 季度第一个月
			day = getPassDayOfMonth(quarterDates[0]);
		} else if (month == Calendar.FEBRUARY || month == Calendar.MAY
				|| month == Calendar.AUGUST || month == Calendar.NOVEMBER) {// 季度第二个月
			day = getDayOfMonth(quarterDates[0])
					+ getPassDayOfMonth(quarterDates[1]);
		} else if (month == Calendar.MARCH || month == Calendar.JUNE
				|| month == Calendar.SEPTEMBER || month == Calendar.DECEMBER) {// 季度第三个月
			day = getDayOfMonth(quarterDates[0]) + getDayOfMonth(quarterDates[1])
					+ getPassDayOfMonth(quarterDates[2]);
		}
		return day;
	}

	/**
	 * 取得季度月
	 *
	 * @param date
	 * @return
	 */
	public static Date[] getQuarterDate(Date date) {
		Date[] quarter = new Date[3];

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		int nQuarter = getQuarter(date);
		if (nQuarter == 1) {// 第一季度
			c.set(Calendar.MONTH, Calendar.JANUARY);
			quarter[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.FEBRUARY);
			quarter[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.MARCH);
			quarter[2] = c.getTime();
		} else if (nQuarter == 2) {// 第二季度
			c.set(Calendar.MONTH, Calendar.APRIL);
			quarter[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.MAY);
			quarter[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.JUNE);
			quarter[2] = c.getTime();
		} else if (nQuarter == 3) {// 第三季度
			c.set(Calendar.MONTH, Calendar.JULY);
			quarter[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.AUGUST);
			quarter[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.SEPTEMBER);
			quarter[2] = c.getTime();
		} else if (nQuarter == 4) {// 第四季度
			c.set(Calendar.MONTH, Calendar.OCTOBER);
			quarter[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.NOVEMBER);
			quarter[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.DECEMBER);
			quarter[2] = c.getTime();
		}
		return quarter;
	}

	/**
	 *
	 * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
	 *
	 * @param date
	 * @return
	 */
	public static int getQuarter(Date date) {

		int quarter = 0;

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		switch (month) {
			case Calendar.JANUARY:
			case Calendar.FEBRUARY:
			case Calendar.MARCH:
				quarter = 1;
				break;
			case Calendar.APRIL:
			case Calendar.MAY:
			case Calendar.JUNE:
				quarter = 2;
				break;
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.SEPTEMBER:
				quarter = 3;
				break;
			case Calendar.OCTOBER:
			case Calendar.NOVEMBER:
			case Calendar.DECEMBER:
				quarter = 4;
				break;
			default:
				break;
		}
		return quarter;
	}
}
