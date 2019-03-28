package com.iwhalecloud.retail.pay.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayOrderInfoDTO implements Serializable,BasePay {

    private String over_time;
    private String order_desc;
    private String order_date;
    private String order_id;
}
