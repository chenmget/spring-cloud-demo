package com.iwhalecloud.retail.oms.consts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private static String  strFormatYMR = "yyyy-MM-dd";

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
     * String字符串转换为java.util.Date格式日期
     * @param strDate
     * @param dateFormat
     * @return
     */
    public static java.util.Date strToUtilDate(String strDate, String dateFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        java.util.Date date = null;
        try {
            date = sf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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
}
