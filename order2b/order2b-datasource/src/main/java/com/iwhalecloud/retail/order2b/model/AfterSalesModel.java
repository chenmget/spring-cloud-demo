package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.entity.OrderApply;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class AfterSalesModel extends OrderApply {

    OrderItem orderItems;

    private String handlerName;
}
