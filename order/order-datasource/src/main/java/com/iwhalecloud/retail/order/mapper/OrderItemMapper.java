package com.iwhalecloud.retail.order.mapper;

import com.iwhalecloud.retail.order.config.WhaleCloudDBKeySequence;
import com.iwhalecloud.retail.order.dto.model.OrderItemModel;
import com.iwhalecloud.retail.order.model.OrderItemEntity;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OrderItemMapper {

    @WhaleCloudDBKeySequence
    int saveOrderItem(List<OrderItemEntity> dto);

    List<OrderItemModel> selectOrderItemByOrderId(String orderId);
    List<OrderItemModel> selectOrderItemByOrderIds(List<String> orderId);

    int updateDeliverGoodsNum(OrderUpdateAttrEntity dto);
}
