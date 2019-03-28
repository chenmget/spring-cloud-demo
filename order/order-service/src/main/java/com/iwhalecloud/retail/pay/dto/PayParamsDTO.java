package com.iwhalecloud.retail.pay.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayParamsDTO implements Serializable ,BasePay {

    private String account_id;
    private String div_details;
    private String notify_url;// http;////10.45.108.41:8083/api/pay/notifyUrl,
    private String client_ip;// 127.0.0.1,/
    private String request_time;// 20160525165648,
    private String bank_id;// ALIPAY,
    private String busi_channel;// 10011,
    private String ped_cnt;// ,
    private String pay_type;// JSAPI,
    private String limit_pay;// ,
    private String request_seq;// 20160525165648415590,
    private String pay_amount;// 1,
    private String pay_channel;// 1,
    private PayAttachInfoDTO attach_info;
    private PayOrderInfoDTO order_info;
}
