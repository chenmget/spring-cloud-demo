package com.iwhalecloud.retail.web.controller.pay.param;

import lombok.Data;

@Data
public class ToPayRequest {
    String payChannel;

    String payType;

    String bankId;

    String orderId;

    String openId;
}
