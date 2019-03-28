package com.iwhalecloud.retail.order2b.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SequenceTools {

    public static final String DATE_FORMAT_7 = "yyyyMMddHHmmssSSS";

    /**
     * 生成随机数  时间格式+随机数
     * @param dateFormatType 时间格式
     * @param randomNum 随机数个数
     * @return
     */
    public static String getDateRandom(String dateFormatType, int randomNum) {//365*24
        StringBuilder randomBuffer = new StringBuilder();
        randomBuffer.append(new SimpleDateFormat(dateFormatType).format(new Date(System.currentTimeMillis())));
        randomBuffer.append(getServer_id());randomNum+=2;
        while (randomBuffer.length() - dateFormatType.length() <= randomNum) {
            randomBuffer.append(Math.round(Math.random() * 99));
        }
        return randomBuffer.toString().substring(0, dateFormatType.length() + randomNum);
    }

    public static String getServer_id(){
        String serviceid="00";
        if(!StringUtils.isEmpty(System.getProperty("serviceid"))){
            serviceid=System.getProperty("serviceid");
        }
        return serviceid;
    }

    public static String seqId15(){
        StringBuilder randomBuffer = new StringBuilder();
        String str=String.valueOf(System.currentTimeMillis());
        randomBuffer.append(str);
        randomBuffer.append(Math.round(Math.random() * 99));
        return randomBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(getDateRandom(DATE_FORMAT_7,4));
        System.out.println(getDateRandom(DATE_FORMAT_7,4).length());
    }

    /**
     *  增加（减少）minute 分钟
     *  增加（减少）day天
     */
    public static String AddMinuteADay(int minute,int day,String stringToDate ){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义要输出日期字符串的格式
        Date startTime = new Date();
        try {
            startTime = sdf.parse(stringToDate);//parse方法将字符创转换为时间对象，格式：Mon Jun 26 08:23:21 CST 2017
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();//获得一个Calendar实例对象
        c.setTime(startTime);//设置为当前时间
        c.add(Calendar.DATE,day);//可以将时间设置为7天前，正数时为当前日期之后
        c.add(Calendar.MINUTE, minute);//
        Date endTime = c.getTime();  //时间对象endTime格式，例如：Wed Jun 14 00:00:00 CST 2017
        String endTimeSting  = sdf.format(endTime);//endTimeString转为指定格式字符串，如：2017-06-14 00:00:00
        return endTimeSting;
    }

}
