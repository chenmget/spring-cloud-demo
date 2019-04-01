package com.iwhalecloud.retail.goods2b.utils;

import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.exception.RetailTipException;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @Author My
 * @Date 2018/12/27
 **/
@Data
public class ReflectUtils implements Serializable {

    /**
     *
     * @param obj
     * @param notCheckField
     * @return
     * @throws Exception
     */
    public static boolean isAllFieldNull(Object obj, String notCheckField){
        boolean flag = true;
        try{
            // 得到类对象
            Class stuCla = (Class) obj.getClass();
            // 得到属性集合
            Field[] fs = stuCla.getDeclaredFields();
            // 遍历属性
            String serialVersionUID = "serialVersionUID";
            for (Field f : fs) {
                // 设置属性是可以访问的(私有的也可以)
                f.setAccessible(true);
                // 得到此属性的值
                Object val = f.get(obj);
                // 只要有1个属性不为空,那么就不是所有的属性值都为空
                String fieldName = f.getName();
                if(val!=null && !fieldName.equals(notCheckField)&& !fieldName.equals(serialVersionUID)) {
                    flag = false;
                    break;
                }
            }
        }catch (Exception e){
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "产品拓展属性值异常");
        }
        return flag;
    }
}
