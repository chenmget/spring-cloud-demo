package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.entity.OrderItemDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrderItemInfoModel extends OrderItem {

    List<OrderItemDetail> detailList;
}
