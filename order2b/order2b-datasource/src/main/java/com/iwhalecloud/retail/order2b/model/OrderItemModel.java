package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.entity.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderItemModel extends OrderItem {

    private List<String> lanIdList;
    private List<String> orderIdList;
}
