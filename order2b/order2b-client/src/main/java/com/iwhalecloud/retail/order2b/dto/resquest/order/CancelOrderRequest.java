package com.iwhalecloud.retail.order2b.dto.resquest.order;

import lombok.Data;

import java.io.Serializable;

@Data
public class CancelOrderRequest extends  UpdateOrderStatusRequest implements Serializable{

    private  String cancelReason;
}
