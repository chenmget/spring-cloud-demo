package com.iwhalecloud.retail.order.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderFlowEntity implements Serializable {

    private String orderId;

    private List<OrderFlowItemEntity> item;
}
