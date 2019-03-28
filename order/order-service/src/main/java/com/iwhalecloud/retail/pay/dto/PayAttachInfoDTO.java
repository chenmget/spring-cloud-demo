package com.iwhalecloud.retail.pay.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayAttachInfoDTO implements Serializable ,BasePay{

    private String tmnum;// ,
    private String product_id;// ,
    private String attach;// ,
    private String busi_code;// 0001,
    private String product_desc;// ,
    private String cust_id;// qianggsheng
}
