package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.entity.Promotion;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderInfoModel extends Order implements Serializable{

    private String supplierName;

    private Integer receiveNum;
    private Integer deliveryNum;

    List<OrderItem> orderItems;

    private List<Promotion> promotionList;

    private double directReductionAmount;
    private double acCouponAmount;
}
