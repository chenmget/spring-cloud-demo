package com.iwhalecloud.retail.member.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
@Slf4j
public class DateFormatUtils {

	public static boolean isSameMonth(Date date1, Date date2) {
        try {
         Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);

            boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                    .get(Calendar.YEAR);
            boolean isSameMonth = isSameYear
                    && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);

            return isSameMonth;
     } catch (Exception e) {
         log.error("[RatingEngine] error occurred: ERROR ", e);
     }
     return false;


    }

}
