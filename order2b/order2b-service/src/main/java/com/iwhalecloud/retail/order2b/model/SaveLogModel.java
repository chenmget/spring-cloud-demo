package com.iwhalecloud.retail.order2b.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SaveLogModel implements Serializable {

    /**
     * 支付id
     */
    String payId;

    /**
     * 业务ID
     */
    String orderId;

    /**
     * 业务金额
     */
    String orderAmount;

    /**
     * 支付状态
     */
    String payStatus;

    /**
     * 请求类型
     */
    String requestType;

    /**
     * 操作类型
     */
    String operationType;

    /**
     * 付款凭证地址
     */
    String payData;

    String payDataMd;

    /**
     * 接收方银行编码
     */
    private java.lang.String recBankId;

    /**
     * 接收方银行账号
     */
    private java.lang.String recAccount;

    /**
     * 接收方账号名称
     */
    private java.lang.String recAccountName;

}
