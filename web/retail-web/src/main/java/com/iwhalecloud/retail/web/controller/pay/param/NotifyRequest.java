package com.iwhalecloud.retail.web.controller.pay.param;

import lombok.Data;

@Data
public class NotifyRequest {
    /**
     * 业务渠道支付请求流水
     */
    String request_seq;

    /**
     * 支付单id
     */
    String pay_id;

    /**
     * 支付平台请求支付渠道交易流水
     */
    String trans_seq;

    /**
     * 支付渠道流水号
     */
    String up_trans_seq;

    /**
     * 支付渠道请求银行流水号
     */
    String pay_chanl_req_trans_seq;

    /**
     * 银行流水号
     */
    String bank_trans_seq;

    /**
     * 支付金额
     */
    String pay_amount;

    /**
     * 帐期
     */
    String checkup_date;

    /**
     * 附加信息
     */
    String attach;

    /**
     * 业务订单号
     */
    String order_id;

    /**
     * 数字签名
     */
    String sign;
}
