package com.iwhalecloud.retail.order2b.config;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order2b.entity.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class TelDBDefValueConfig {


    public static final String TEL_DB_DEF_TIME = "1970-01-02 00:00:00";

    public void setTableDefTime(Object object) {
        try {
            if (object instanceof List) {
                List list = (List) object;
                for (Object obj : list) {
                    setDefTime(obj);
                }
            } else {
                setDefTime(object);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDefTime(Object object) {
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                       TableTimeValidate config=field.getAnnotation(TableTimeValidate.class);
            if(config ==null){
                continue;
            }
            field.setAccessible(true);
            if (StringUtils.isEmpty(field.get(object))) {
                field.set(object, config.defTime());
            }
            field.setAccessible(false);
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List list = new ArrayList();
        Order order = new Order();
        order.setOrderType("1");
        list.add(order);
        new TelDBDefValueConfig().setTableDefTime(order);
        System.out.println(JSON.toJSONString(list));
    }


}
