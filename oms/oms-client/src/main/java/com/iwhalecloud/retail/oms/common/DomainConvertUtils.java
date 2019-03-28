package com.iwhalecloud.retail.oms.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DomainConvertUtils {

    public static Map<String, Object> domainConvertToMap(Object domain) throws Exception {
        Map<String, Object> domainMap = new HashMap<>();
        Field[] fields = domain.getClass().getDeclaredFields();

        for (Field field : fields){
            String fieledName = field.getName();
            String getMethodName = "get" + toUpperCaseFirstOne(fieledName);
            Method getMethod = domain.getClass().getMethod(getMethodName);
            Object valueObj = getMethod.invoke(domain);
            domainMap.put(fieledName, valueObj);
        }
        return domainMap;
    }

    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

}
