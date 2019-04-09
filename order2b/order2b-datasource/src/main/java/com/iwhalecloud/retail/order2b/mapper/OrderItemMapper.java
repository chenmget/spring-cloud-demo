package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.model.OrderItemInfoModel;
import com.iwhalecloud.retail.order2b.model.OrderItemModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    int saveOrderItem(List<OrderItem> dto);

    List<OrderItem> selectOrderItemByOrderId(OrderItem orderId);

    int updateOrderItemByItemId(OrderItem item);

    List<OrderItemInfoModel> selectOrderItemInfoListById(OrderItem orderId);

    OrderItem selectOrderItemByItemId(OrderItem itemId);

    List<OrderItem> selectOrderItem(OrderItemModel orderItem);

}
