package com.iwhalecloud.retail.pay.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayParamsRequest implements Serializable {

    private Double paymoney;

    private String payChannel;

    private String payType;

    private String bankId;

    private String orderId;

    private String openId;

}
