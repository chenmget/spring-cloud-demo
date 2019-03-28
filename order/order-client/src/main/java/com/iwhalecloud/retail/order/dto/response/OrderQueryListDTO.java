package com.iwhalecloud.retail.order.dto.response;

import com.iwhalecloud.retail.order.dto.model.DeliveryModel;
import com.iwhalecloud.retail.order.dto.model.OrderItemModel;
import com.iwhalecloud.retail.order.dto.model.OrderModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderQueryListDTO implements Serializable {

    private List<DeliveryModel> delivery;

    private List<OrderItemModel> orderItems;

    private OrderModel order;

}
