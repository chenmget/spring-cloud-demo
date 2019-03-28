package com.iwhalecloud.retail.pay.util;

import com.iwhalecloud.retail.order.util.MD5Util;
import com.iwhalecloud.retail.pay.dto.BasePay;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
public class PayUtils {

    //由业务渠道提供，建议：yyyyMMddhhmmss + 6位随机数
    public static String CreateSeq() {
        String d = dateToStr(new Date(), "yyyyMMddhhmmss");
        Random random = new Random();
        int r = random.nextInt(999999);
        return d + r;
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


    //    签名示例方法：
//    将参数集合非空参数值的参数按照参数名ASCII码从小到大排序（字典序，参数名区分大小写），使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值。
//    注：传送的sign参数不参与签名。
    public static String createSign(Map objMap, String key) {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        for (Object k : objMap.keySet()) {
            Object v =  objMap.get(k);
            if(v instanceof String){
                if (null != v && !"".equals(v)) {
//                    parameters.put(k, JSON.toJSONString(v));
                    parameters.put(k.toString(), v);
                }
            }
        }
        StringBuffer sbkey = new StringBuffer();
        Set es = parameters.entrySet();  //所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            //空值不传递，不参与签名组串
            if (null != v && !"".equals(v)) {
                sbkey.append(k + "=" + v + "&");
            }
        }
        sbkey = sbkey.append("key=" + key);
        log.info("pay_TO_string:" + sbkey.toString());
        //MD5加密,结果转换为大写字符
        String md5 = null;
        try {
            md5 = MD5Util.MD5(sbkey.toString()).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    /**
     * 对象字段转map,用于签名
     * @param paramsMap
     * @param obj
     */
    public static void payObjectToMap(Map<String, Object> paramsMap,Object obj){
        //object 转 map
        if (obj == null) {
            return ;
        }
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                if( field.get(obj) instanceof BasePay){
                    payObjectToMap(paramsMap,field.get(obj));
                }else{
                    paramsMap.put(field.getName(), field.get(obj));
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
    }

}
