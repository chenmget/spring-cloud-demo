package com.iwhalecloud.retail.goods2b.helper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.utils.SpringContextUtils;
import org.springframework.context.ApplicationContext;

/**
 * @author Z
 * @date 2018/12/4
 */
public class AttrSpecHelper {

    /***
     * 下划线命名转为驼峰命名
     *
     * @param para 下划线命名的字符串
     */

    public static String underlineToUpper(String para) {
        StringBuilder result=new StringBuilder();
        String a[]=para.split("_");
        for(String s:a){
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     *        驼峰命名的字符串
     */
    public static String humpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        int temp=0;//定位
        for(int i=0;i<para.length();i++){
            if(Character.isUpperCase(para.charAt(i))){
                sb.insert(i+temp, "_");
                temp+=1;
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 根据表明获取对应的manager bean对象
     * @param tableName 表名称
     * @return
     */
    public static BaseMapper getMapperBean(String tableName) {
        ApplicationContext ctx = SpringContextUtils.getApplicationContext();

        String fullBeanName = underlineToUpper(tableName) + "Mapper";

        if (ctx.containsBean(fullBeanName)) {
            return (BaseMapper)ctx.getBean(fullBeanName);
        }

        if (!tableName.contains("_")) {
            return null;
        }

        String trimBeanName = underlineToUpper(tableName.substring(tableName.indexOf("_")+1)) + "Mapper";
        if (ctx.containsBean(trimBeanName)) {
            return (BaseMapper)ctx.getBean(trimBeanName);
        }

        return null;
    }
}
