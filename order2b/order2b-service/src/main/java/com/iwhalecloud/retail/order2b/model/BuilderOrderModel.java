package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.entity.AdvanceOrder;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.entity.Promotion;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BuilderOrderModel implements Serializable {

    private String orderId;

    private Order order;

    private List<OrderItem> orderItem;

    private String supperName;

    private String supperId;

    private List<Promotion> promotionList;

    private String sourceFrom;

    private AdvanceOrder advanceOrder;

    private String orderCat;

    private String advancePayType;
}
