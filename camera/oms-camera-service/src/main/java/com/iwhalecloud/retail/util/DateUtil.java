package com.iwhalecloud.retail.util;

import org.springframework.util.StringUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @desc 时间工具类(最新时间工具类，后续时间相关转换方法尽量写至此类)
 * @author qin.guoquan
 * @date 2011-10-25 上午11:53:07
 */
public class DateUtil {
	//系统默认日期格式
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd";
	public static final String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_3 = "yyyyMMdd";
	public static final String DATE_FORMAT_4 = "yyMMdd";
	public static final String DATE_FORMAT_5 = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_6 = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_FORMAT_7 = "yyyyMMddHHmmssSSS";
	public static final String DATE_FORMAT_8 = "yyMMddHHmmss";
	public static final String DATE_FORMAT_9 = "yyMMddHHmm";
	public static final String DATE_FORMAT_10 = "yyyyMM";
	public static final String DATE_FORMAT_11 = "yyyyMM";
	public static final String DATE_FORMAT_12 = "dd";
	
	

	public final static String YEAR = "year";
	public final static String MONTH = "month";
	public final static String DAY = "day";
	public final static String HOUR = "hour";
	public final static String MINUTE = "minute";
	public final static String SECOND = "second";
	public final static String WEEK = "week";
	
	public static String getTime1() throws Exception {
	    return getTime(DATE_FORMAT_1);
	}
	
    public static String getTime2() throws Exception {
        return getTime(DATE_FORMAT_2);
    }
    
    public static String getTime3() throws Exception {
        return getTime(DATE_FORMAT_3);
    }
    
    public static String getTime4() throws Exception {
        return getTime(DATE_FORMAT_4);
    }
    
    public static String getTime5() throws Exception {
        return getTime(DATE_FORMAT_5);
    }
    
    public static String getTime6() throws Exception {
        return getTime(DATE_FORMAT_6);
    } 
    
    public static String getTime7() throws Exception {
        return getTime(DATE_FORMAT_7);
    } 
    
    public static String getTime8() throws Exception {
        return getTime(DATE_FORMAT_8);
    } 
    
    public static String getTime9() throws Exception {
        return getTime(DATE_FORMAT_9);
    }
    
    public static String getTime10() throws Exception {
        return getTime(DATE_FORMAT_10);
    } 
	
    /**
     * 获取当前服务器时间，按指定格式返回。
     * 
     * @param format
     * @return
     * @throws Exception
     */
	public static String getTime(String format) throws Exception {
	    return new SimpleDateFormat(format).format(new Date(System.currentTimeMillis()));
	}


	/**
	 * 转换输入日期格式
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static String formatDate(Date date, String pattern) throws Exception {

		if (date == null) {
			return "";
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
		String str = dateFormator.format(date);

		return str;
	}

	/**
	 * 转换输入日期 按照指定的格式返回。
	 * 
	 * @param date
	 * @param pattern
	 *            格式类型，通过系统常量中定义，如：CapConstants.DATE_FORMAT_8
	 * @param loc
	 *            locale
	 * @return
	 */
	public static String formatDate(Date date, String pattern, Locale loc) throws Exception {
		if (pattern == null || date == null) {
			return "";
		}
		String newDate = "";
		if (loc == null) {
			loc = Locale.getDefault();
		}
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, loc);
			newDate = sdf.format(date);
		}
		return newDate;
	}

	/**
	 * 将字符时间从一个格式转换成另一个格式。时间的格式，最好通过系统常量定义。 如： String dateStr = "2006-10-9
	 * 12:09:08"; DateFormatUtils.convertDateFormat(dateStr,
	 * CapConstants.DATE_TIME_FORMAT, CapConstants.DATE_FORMAT_8);
	 * 
	 * @param sdate
	 * @param patternFrom
	 *            格式类型，通过系统常量中定义，如：CapConstants.DATE_FORMAT_8
	 * @param patternTo
	 *            格式类型，通过系统常量中定义，如：CapConstants.DATE_FORMAT_8
	 * @return
	 */
	public static String convertDateFormat(String dateStr, String patternFrom, String patternTo) throws Exception {

		if (dateStr == null || patternFrom == null || patternTo == null) {
			return "";
		}

		String newDate = "";

		try {

			Date dateFrom = parseStrToDate(dateStr, patternFrom);
			newDate = formatDate(dateFrom, patternTo);

		} catch (Exception e) {
		}

		return newDate;
	}

	/**
	 * 将时间串按照默认格式CapConstants.DATE_FORMAT，格式化成Date。
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseStrToDate(String dateStr) throws Exception {

		if (null == dateStr || "".equals(dateStr)) {
			return null;
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_1);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		return tDate;
	}

	public static String parseDateStrToDateTimeStr(String dateStr) throws Exception {

		if (null == dateStr || "".equals(dateStr)) {
			return null;
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_1);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));
		return formatDate(tDate, DateUtil.DATE_FORMAT_2);

	}

	/**
	 * 将时间串按照默认格式CapConstants.DATE_FORMAT_1，格式化成Date。
	 *
	 * @param dateStr
	 * @return
	 */
	public static Calendar parseStrToCalendar(String dateStr) throws Exception {

		if (null == dateStr || "".equals(dateStr)) {
			return null;
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_1);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		Locale loc = Locale.getDefault();
		Calendar cal = new GregorianCalendar(loc);
		cal.setTime(tDate);

		return cal;
	}

	public static Calendar parseStrToCalendar(String dateStr, String pattern) throws Exception {

		if (null == dateStr || "".equals(dateStr)) {
			return null;
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		Locale loc = Locale.getDefault();
		Calendar cal = new GregorianCalendar(loc);
		cal.setTime(tDate);

		return cal;
	}

	/**
	 * 将时间串按照默认格式CapConstants.DATE_FORMAT_2，格式化成Date。
	 *
	 * @param dateStr
	 * @return
	 */
	public static Date parseStrToDateTime(String dateStr) throws Exception {

		if (null == dateStr || "".equals(dateStr)) {
			return null;
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		return tDate;
	}

	/**
	 * 将时间串按照指定格式，格式化成Date。
	 *
	 * @param dateStr
	 * @param pattern
	 *            格式类型，通过系统常量中定义，如：CapConstants.DATE_FORMAT_8
	 * @return
	 */

	public static Date parseStrToDate(String dateStr, String pattern) throws Exception {
		if (null == dateStr || "".equals(dateStr)) {
			return null;
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		return tDate;
	}

	/**
	 * 按时间格式相加：
	 * 输入要相加的时间基点（字符串型或时间类型），相加的时长（整型），格式（"year"年、"month"月、"day"日、”hour“时、”minute“分、”second“秒、"week"周）
	 * 输出相加后的时间（字符串型或时间类型）
	 *
	 * @param dateStr
	 * @param pattern
	 * @param step
	 * @param type
	 *            "year"年、"month"月、"day"日、”hour“时、”minute“分、”second“秒、"week"周
	 *            通过常量DateFormatUtils.YEAR等来设置.
	 * @return
	 */
	public static String addDate(String dateStr, String pattern, int step, String type) throws Exception {
		if (dateStr == null) {
			return dateStr;
		} else {
			Date date1 = DateUtil.parseStrToDate(dateStr, pattern);

			Locale loc = Locale.getDefault();
			Calendar cal = new GregorianCalendar(loc);
			cal.setTime(date1);

			if (DateUtil.WEEK.equals(type)) {

				cal.add(Calendar.WEEK_OF_MONTH, step);

			} else if (DateUtil.YEAR.equals(type)) {

				cal.add(Calendar.YEAR, step);

			} else if (DateUtil.MONTH.equals(type)) {

				cal.add(Calendar.MONTH, step);

			} else if (DateUtil.DAY.equals(type)) {

				cal.add(Calendar.DAY_OF_MONTH, step);

			} else if (DateUtil.HOUR.equals(type)) {

				cal.add(Calendar.HOUR, step);

			} else if (DateUtil.MINUTE.equals(type)) {

				cal.add(Calendar.MINUTE, step);

			} else if (DateUtil.SECOND.equals(type)) {

				cal.add(Calendar.SECOND, step);

			}

			return DateUtil.formatDate(cal.getTime(), pattern);
		}
	}

	/**
	 * 按时间格式相减：
	 * 输入要相加的时间基点（字符串型或时间类型），相加的时长（整型），格式（"year"年、"month"月、"day"日、”hour“时、”minute“分、”second“秒、"week"周）
	 * 输出相加后的时间（字符串型或时间类型）
	 *
	 * @param dateStr
	 * @param pattern
	 * @param step
	 * @param type
	 *            "year"年、"month"月、"day"日、”hour“时、”minute“分、”second“秒、"week"周
	 * @return
	 */
	public static String minusDate(String dateStr, String pattern, int step, String type) throws Exception {

		return addDate(dateStr, pattern, (0 - step), type);

	}

	/**
	 * 日期增加天数
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDay(Date date, int days) throws Exception {
		if (date == null) {
			return date;
		} else {
			Locale loc = Locale.getDefault();
			Calendar cal = new GregorianCalendar(loc);
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, days);
			return cal.getTime();
		}
	}

	public static int getDays(Date date1, Date date2) throws Exception {
		if (date1 == null || date2 == null) {
			return 0;
		} else {
			return (int) ((date2.getTime() - date1.getTime()) / 0x5265c00L);
		}
	}

	/**
	 * 日期比较大小
	 *
	 * @param dateStr1
	 * @param dateStr2
	 * @param pattern
	 * @return
	 */
	public static int compareDate(String dateStr1, String dateStr2, String pattern) throws Exception {

		Date date1 = DateUtil.parseStrToDate(dateStr1, pattern);
		Date date2 = DateUtil.parseStrToDate(dateStr2, pattern);

		return date1.compareTo(date2);

	}

	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getFirstDayInMonth(String dateStr, String pattern) throws Exception {
		Calendar cal = DateUtil.parseStrToCalendar(dateStr);
		//int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		cal.add(Calendar.DAY_OF_MONTH, (1 - day));

		return DateUtil.formatDate(cal.getTime(), pattern);
	}

	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getLastDayInMonth(String dateStr, String pattern) throws Exception {
		Calendar cal = DateUtil.parseStrToCalendar(dateStr);
		//int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		int maxDayInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int step = maxDayInMonth - day;

		cal.add(Calendar.DAY_OF_MONTH, step);

		return DateUtil.formatDate(cal.getTime(), pattern);
	}

	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getFirstDayInWeek(String dateStr, String pattern) throws Exception {
		Calendar cal = DateUtil.parseStrToCalendar(dateStr);
		int day = cal.get(Calendar.DAY_OF_WEEK);

		cal.add(Calendar.DAY_OF_MONTH, (1 - day));

		return DateUtil.formatDate(cal.getTime(), pattern);
	}

	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getLastDayInWeek(String dateStr, String pattern) throws Exception {
		Calendar cal = DateUtil.parseStrToCalendar(dateStr);
		int day = cal.get(Calendar.DAY_OF_WEEK);

		cal.add(Calendar.DAY_OF_MONTH, (6 - day));

		return DateUtil.formatDate(cal.getTime(), pattern);
	}

	public static long calendarDayPlus(String dateStr1, String dateStr2) throws Exception {

		if (dateStr1 == null || dateStr2 == null || dateStr1.equals("") || dateStr2.equals("")) {
			return 0;
		}
		Date date1 = DateUtil.parseStrToDate(dateStr1);
		Date date2 = DateUtil.parseStrToDate(dateStr2);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		long t1 = c1.getTimeInMillis();
		long t2 = c2.getTimeInMillis();
		long day = (t2 - t1) / (1000 * 60 * 60 * 24);
		return day;
	}

	/**
	 * @param dateStr1
	 * @param dateStr2
	 * @return
	 */
	public static int calendarPlus(String dateStr1, String dateStr2) throws Exception {

		if (dateStr1 == null || dateStr2 == null || dateStr1.equals("") || dateStr2.equals("")) {
			return 0;
		}

		Calendar cal1 = DateUtil.parseStrToCalendar(dateStr1);

		Calendar cal2 = DateUtil.parseStrToCalendar(dateStr2);

		int dataStr1Year = cal1.get(Calendar.YEAR);
		int dataStr2Year = cal2.get(Calendar.YEAR);

		int dataStr1Month = cal1.get(Calendar.MONTH);
		int dataStr2Month = cal2.get(Calendar.MONTH);

		return ((dataStr2Year * 12 + dataStr2Month + 1) - (dataStr1Year * 12 + dataStr1Month));

	}

	public static java.sql.Timestamp getTimestamp(String dateStr) throws Exception {
		java.text.DateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
		java.sql.Timestamp ts = null;
		try {
			java.util.Date date = df.parse(dateStr);
			ts = new java.sql.Timestamp(date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}

	/**
	 * 将Date转换成统一的日期时间格式文本。
	 *
	 * @return
	 */
	public static String getFormatedDateTime(java.sql.Date date) throws Exception {
		if (null == date) {
			return "";
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
		return dateFormator.format(new java.sql.Date(date.getTime()));
	}

	/**
	 * 通过统一的格式将文本转换成Date。输入为日期和时间。
	 *
	 * @return
	 */
	public static java.sql.Date parseDateTime(String sdate) throws Exception {
		if (null == sdate || "".equals(sdate)) {
			return null;
		}

		// 只有日期类型
		if (sdate.length() <= 10) {
			return parseDate(sdate);
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);

		java.util.Date tDate = dateFormator.parse(sdate, new ParsePosition(0));
		if (tDate == null) {
			return null;
		}

		return new java.sql.Date(tDate.getTime());

	}

	/**
	 * 通过统一的格式将文本转换成Date。输入为日期。
	 *
	 * @return
	 */
	public static java.sql.Date parseDate(String sdate) throws Exception {
		if (null == sdate || "".equals(sdate)) {
			return null;
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_1);

		java.util.Date tDate = dateFormator.parse(sdate, new ParsePosition(0));
		if (tDate == null) {
			return null;
		}

		return new java.sql.Date(tDate.getTime());
	}
	
	/**
	 * String 转 Timestamp
	 * @param date
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static java.sql.Timestamp parseDate2Timestamp(String date, String format) {
		try {
			if (StringUtils.isEmpty(format)) {
				format = DateUtil.DATE_FORMAT_2;
			}
			java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
			return ts.valueOf(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * Timestamp 转 String 
	 * @param tt
	 * @return
	 * @throws Exception
	 */
	public static String parseTimestamp2Str(java.sql.Timestamp tt, String format) {
		try {
			if (StringUtils.isEmpty(format)) format = DateUtil.DATE_FORMAT_2;
			SimpleDateFormat df = new SimpleDateFormat(format);
			java.sql.Timestamp now = new java.sql.Timestamp(tt.getTime());//获取系统当前时间
			String str = df.format(now);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取下个月的第一天
	 * @return
	 */
    public static String nextMonthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_1+" 00:00:00");
        return sdf.format(date);
    }
    
   /* public static String nextMonthFirstDate(String dateStr, String pattern) {
    	String pattern0 = pattern;
    	if (null == pattern || "".equals(pattern))
    		pattern0 = DATE_FORMAT_2;
		if (null == dateStr || "".equals(dateStr))
			return null;
		SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT_1);
		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));
		Locale loc = Locale.getDefault();
		Calendar cal = new GregorianCalendar(loc);
		cal.setTime(tDate);
		int year=cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour=cal.get(Calendar.HOUR);
		int minute=cal.get(Calendar.MINUTE);
		int second=cal.get(Calendar.SECOND);
		day=1;hour=0;minute=0;second=0;
		cal.clear();
		cal.set(year, month+1, day, hour, minute, second);
		return DateFormatUtils.formatDate(cal.getTime(), pattern0);
    }*/
	
    /**
     * ZX add 2016年4月20日 17:26:01
     * 格式 yyyy-MM-dd
     * 获取当前日志的前一个月日期
     * @return
     */
    public static String currDateBeforeOneMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);    //得到前一天
		calendar.add(Calendar.MONTH, -1);    //得到前一个月
		calendar.add(Calendar.DAY_OF_MONTH, -1);    //得到前一个月
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		String month_s = "";
		if (month < 10) {
			month_s = "0"+month;
		} else {
			month_s = String.valueOf(month);
		}
		int day = calendar.get(Calendar.DAY_OF_MONTH)+1;
		String day_s = "";
		if (day < 10) {
			day_s = "0"+day;
		} else {
			day_s = String.valueOf(day);
		}
		return year + "-" +month_s + "-" + day_s;
	}
    /**
     * ZX add 2016年4月20日 17:26:01
     * 格式 yyyy-MM-dd
     * 获取当前日志的前N个月日期
     * @return
     */
    public static String currDateBeforeNMonth(int n_date, int n_month, int n_day_of_month) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -n_date);    //得到前一天
		calendar.add(Calendar.MONTH, -n_month);    //得到前一个月
		calendar.add(Calendar.DAY_OF_MONTH, -n_day_of_month);    //得到前一个月
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		String month_s = "";
		if (month < 10) {
			month_s = "0"+month;
		} else {
			month_s = String.valueOf(month);
		}
		int day = calendar.get(Calendar.DAY_OF_MONTH)+1;
		String day_s = "";
		if (day < 10) {
			day_s = "0"+day;
		} else {
			day_s = String.valueOf(day);
		}
		return year + "-" +month_s + "-" + day_s;
	}
    
    /**
	 * 获取当月剩余天数（含当天）
	 * @return
	 */
	public static int getMonthSurplusDays() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String format = sdf.format(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		calendar.roll(Calendar.DATE, -Integer.parseInt(format));// 日期回滚
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * 获取当月总天数
	 * @return
	 */
	public static int getMonthTotalDays() {
		Calendar calendar = Calendar.getInstance();
		calendar.get(Calendar.DATE);
		Calendar monthsday = Calendar.getInstance();
		monthsday.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		monthsday.roll(Calendar.DATE, -1);// 日期回滚
		return monthsday.get(Calendar.DATE);
	}
	
	 /**
	  * 获取今天是星期几
	  * @param dt
	  * @return 星期天为7，星期一为1
	  */
    public static int getWeekOfDate(Date dt) {
    	int[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
		}

        return weekDays[w];
    }
	/**
	 * 校验目标时间是否在起始时间和结束时间范围内
	 * @param startDateStr 起始时间
	 * @param endDateStr 结束时间
	 * @param targetDateStr 目标时间
	 * @return
	 * @throws Exception
	 */
    public    static boolean compare(String startDateStr, String endDateStr, String targetDateStr) throws Exception {
    	boolean result=false;
    	SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_2);
        Date startDate = df.parse(startDateStr);
        Date endDate = df.parse(endDateStr);
        Date targetDate = df.parse(targetDateStr);
        if (targetDate.getTime() >= startDate.getTime()&&targetDate.getTime() <= endDate.getTime()) {
    	   result=true;
        }
	  return result; 
    }
    public static void main(String[] args) throws Exception {
    	
    	System.out.println(DateUtil.formatDate(  DateUtil.addDay(new Date(),-1),  DateUtil.DATE_FORMAT_1));
    	
    	
    	System.out.println(DateUtil.formatDate(new Date(),DateUtil.DATE_FORMAT_12));
/*	    utils.SystemUtils.printLog(new Date());
	    utils.SystemUtils.printLog(getTime1());
	    utils.SystemUtils.printLog(getTime2());
	    utils.SystemUtils.printLog(getTime3());
	    utils.SystemUtils.printLog(getTime4());
	    utils.SystemUtils.printLog(getTime5());
	    utils.SystemUtils.printLog(getTime6());*/
	    
	    //utils.SystemUtils.printLog(parseDate2Timestamp("2014-07-07 12:12:25", null));
    	String startDateStr="2014-07-07 12:12:25";
    	String endDateStr="2014-07-09 12:12:25";
    	String targetDateStr="2014-07-07 12:12:25";
    	boolean r=DateUtil.compare(startDateStr, endDateStr, targetDateStr);
    	System.out.println(r);
	}

}
