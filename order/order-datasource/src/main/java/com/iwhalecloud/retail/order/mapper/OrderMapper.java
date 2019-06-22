package com.iwhalecloud.retail.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order.dto.model.OrderModel;
import com.iwhalecloud.retail.order.dto.resquest.SelectOrderRequest;
import com.iwhalecloud.retail.order.entity.Order;
import com.iwhalecloud.retail.order.model.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author autoCreate
 * @Class: OrderMapper
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    int saveOrder(OrderEntity order);
    
    OrderModel getOrderById(String orderId);
    List<OrderModel> selectOrderListById(List<String> orderId);

    IPage<OrderModel> selectMemberOrderList(Page<OrderModel> page,@Param("order") SelectOrderRequest select);
    IPage<OrderModel> selectManagerOrderList(Page<OrderModel> page,@Param("order") SelectOrderRequest select);

    
    /**
     * 订单表写入揽装录入ID
     * @param orderId
     * @param recommendIds
     * @return
     */
    Integer updateRecommdId(@Param("orderId") String orderId, @Param("recommendIds") String recommendIds);

}