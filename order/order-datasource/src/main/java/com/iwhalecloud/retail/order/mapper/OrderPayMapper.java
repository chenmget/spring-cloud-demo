package com.iwhalecloud.retail.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.pay.entity.OrderPay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: OrderPayMapper
 * @author autoCreate
 */
@Mapper
public interface OrderPayMapper extends BaseMapper<OrderPay>{
   public OrderPay getOrderPayByOrderId(@Param("orderId") String orderId);
}