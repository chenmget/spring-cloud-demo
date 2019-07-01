package com.iwhalecloud.retail.system.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author My
 * @Date 2018/11/2
 **/
public class DateUtils {

    /**
     * 年-月-日 时-分-秒
     */
    private static String  strFormatYMRSFM = "yyyy-MM-dd HH:mm:ss";
    /**
     * 年-月-日
     */
    private static String  strFormatYMR = "yyyyMMdd";

    /**
     * 年月日时分秒
     */
    private static String  strFormatYMDHMS = "yyyyMMddHHmmss";

    /**
     * 获取当前系统时间（String）
     * 年-月-日 时-分-秒
     * @return
     */
    public static String currentSysTimeForStr(){
        java.sql.Timestamp date=new java.sql.Timestamp(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat(strFormatYMRSFM);
        return df.format(date);
    }

    /**
     * 获取当前系统时间（String）
     * 年-月-日
     * @return
     */
    public static String getYesterdayTimeForStr() {
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date yesterday = cal.getTime();
        SimpleDateFormat sp=new SimpleDateFormat(strFormatYMR);
        return sp.format(yesterday);
    }

    /**
     * 获取当前系统时间（String）
     * 年-月-日
     * @return
     */
    public static String getCurrentTime() {
        java.sql.Timestamp date=new java.sql.Timestamp(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat(strFormatYMDHMS);
        return df.format(date);
    }

    /**
     * 获取当前系统时间（Date）
     * 年-月-日 时-分-秒
     * @return
     */
    public static Date currentSysTimeForDate(){
        String timeStr = currentSysTimeForStr();
        return strToUtilDate(timeStr);
    }
    /**
     * 将java.util.Date对象转化为String字符串
     * @param date
     * @param strFormat
     * @return
     */
    public static String dateToStr(java.util.Date date, String strFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(strFormat);
        String str = sf.format(date);
        return str;
    }
    public static Date getLastMonth(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat(strFormatYMRSFM);
        Calendar  calendar= Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);

        return calendar.getTime();
    }
    public static String getMonthLastDay(Date date){
        Calendar  calendar= Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = df.format(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, -1);
        String dayLast = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(dayLast);
        dayLast = endStr.toString();
        return dayLast;

    }


    /**
     * String字符串转换为java.util.Date格式日期
     * @param strDate
     * @return
     */
    public static java.util.Date strToUtilDate(String strDate) {
        SimpleDateFormat sf = new SimpleDateFormat(strFormatYMRSFM);
        java.util.Date date = null;
        try {
            date = sf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * date2比date1多的天数:比较是基于年月日做的比较,不计算时分秒
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) // 不同年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
                {
                    timeDistance += 366;
                } else // 不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else // 同一年
        {
            // System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2 - day1;
        }
    }
}
