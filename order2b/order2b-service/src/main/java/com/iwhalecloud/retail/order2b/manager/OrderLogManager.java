package com.iwhalecloud.retail.order2b.manager;

import com.iwhalecloud.retail.order2b.entity.OrderLog;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.order2b.mapper.OrderLogMapper;


@Component
public class OrderLogManager{
    @Resource
    private OrderLogMapper orderLogMapper;

    public int insertInto(OrderLog orderLog){
        return orderLogMapper.insert(orderLog);
    }
    
    
    
}
