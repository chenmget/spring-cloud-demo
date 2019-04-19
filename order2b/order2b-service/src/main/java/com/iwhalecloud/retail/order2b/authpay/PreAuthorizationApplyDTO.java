package com.iwhalecloud.retail.order2b.authpay;

import com.iwhalecloud.retail.order2b.authpay.annotation.AES;
import lombok.Data;

/**
 * Created by jiyou on 2019/4/15.
 * <p>
 * 支付授权请求
 */
@Data
public class PreAuthorizationApplyDTO {

    private String reqSeq;  // 支付请求流水
    @AES
    private String loginCode;   // 登录号

    private String platCode;    // 平台号

    private String requestTime; // 请求时间

    private String reqIp;       // 请求方IP地址

    private String trsSummary;  //  交易摘要

    private String trsMemo;     //  交易备注

    private String extUserId;   //  外部用户标识

    private String externalId;  //  外部订单号

    private String currencyCode;    //  币种编码

    private String transactionAmount;   //  交易金额
    @AES
    private String payeeLoginCode;  //  收款方登录号

    private String originalTransSeq;    // 申请时的交易流水号

}
