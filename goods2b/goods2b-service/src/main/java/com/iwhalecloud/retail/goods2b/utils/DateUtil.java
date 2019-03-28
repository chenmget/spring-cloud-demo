package com.iwhalecloud.retail.goods2b.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by fadeaway on 2018/12/27.
 */
public class DateUtil {

    /**
     * 获取传入时间后num年的时间。
     *
     * @param date
     * @param num
     * @return
     * @
     */
    public static Date getNextYearTime(Date date, Integer num)  {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)+num);
        Date nextDate=cal.getTime();

        return nextDate;
    }


}
