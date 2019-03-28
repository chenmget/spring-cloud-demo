package com.iwhalecloud.retail.order2b.dto.resquest.pay;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderPayInfoReq implements Serializable {

    private String orderId;
}
