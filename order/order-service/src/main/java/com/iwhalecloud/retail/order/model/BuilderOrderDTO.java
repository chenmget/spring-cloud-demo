package com.iwhalecloud.retail.order.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BuilderOrderDTO implements Serializable {

    private OrderEntity order;

    private List<OrderItemEntity> orderItem;

    private DeliverEntity deliver;
}
