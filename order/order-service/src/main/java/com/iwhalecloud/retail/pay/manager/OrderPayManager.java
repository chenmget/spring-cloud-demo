package com.iwhalecloud.retail.pay.manager;

import com.iwhalecloud.retail.order.mapper.OrderPayHisMapper;
import com.iwhalecloud.retail.order.mapper.OrderPayMapper;
import com.iwhalecloud.retail.pay.entity.OrderPay;
import com.iwhalecloud.retail.pay.entity.OrderPayHis;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Component
public class OrderPayManager{
    @Resource
    private OrderPayMapper orderPayMapper;
    @Resource
    private OrderPayHisMapper orderPayHisMapper;
    
    public int saveOrderPay(OrderPay orderPay){
        orderPay.setCreateDate(new Date());
        int ret = orderPayMapper.insert(orderPay);
        return ret;
    }

    public int updateOrderPay(OrderPay orderPay){
        //先备份后修改
        OrderPay op = orderPayMapper.selectById(orderPay.getTransactionId());
        OrderPayHis his = new OrderPayHis();
        BeanUtils.copyProperties(op, his);
        orderPayHisMapper.insert(his);
        orderPay.setUpdateDate(new Date());
        int ret = orderPayMapper.updateById(orderPay);
        return 1;
    }

    public OrderPay getOrderPay(String transactionId){
       return orderPayMapper.selectById(transactionId);
    }

    public OrderPay getOrderPayByOrderId(String orderId){
        return orderPayMapper.getOrderPayByOrderId(orderId);
    }
    
}
