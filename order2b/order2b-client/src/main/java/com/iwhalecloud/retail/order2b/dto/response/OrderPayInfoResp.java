package com.iwhalecloud.retail.order2b.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderPayInfoResp implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 支付流水ID
     */
    @ApiModelProperty(value = "支付流水ID ")
    private java.lang.String id;

    /**
     * 记录应用类型：B2B：hnyhj_b2b；B2C：hnyhj_b2c；收钱哆：hnsqd
     */
    @ApiModelProperty(value = "记录应用类型：B2B：hnyhj_b2b；B2C：hnyhj_b2c；收钱哆：hnsqd")
    private java.lang.String platformType;

    /**
     * 记录支付类型：1001 线下支付，1002 扫码支付，1003 POS支付，1004 收银台支付，1005 云货架2B线下支付
     */
    @ApiModelProperty(value = "记录支付类型：1001 线下支付，1002 扫码支付，1003 POS支付，1004 收银台支付，1005 云货架2B线下支付")
    private java.lang.String requestType;

    /**
     * 支付对应的订单编号
     */
    @ApiModelProperty(value = "支付对应的订单编号")
    private java.lang.String orderId;

    /**
     * 用于区分支付对接平台：1001 翼支付，1002 巨龙支付，1003 翼支付企业版
     */
    @ApiModelProperty(value = "用于区分支付对接平台：1001 翼支付，1002 巨龙支付，1003 翼支付企业版")
    private java.lang.String payPlatformId;

    /**
     * 记录支付对接平台流水
     */
    @ApiModelProperty(value = "记录支付对接平台流水")
    private java.lang.String payPlatformOrderid;

    /**
     * 记录支付接口类型：1001 聚合支付码GET方式，1002 聚合支付码POST方式，1003 收银台支付（专用于云货架2B支付），1004 线下支付（专用于云货架）
     */
    @ApiModelProperty(value = "记录支付接口类型：1001 聚合支付码GET方式，1002 聚合支付码POST方式，1003 收银台支付（专用于云货架2B支付），1004 线下支付（专用于云货架）")
    private java.lang.String payType;

    /**
     * 用于区分付费/退费：1001 收费，1002 退费，1003 预付费
     */
    @ApiModelProperty(value = "用于区分付费/退费：1001 收费，1002 退费，1003 预付费 ")
    private java.lang.String operationType;

    /**
     * 用于区分客户支付终端：1001 二维码，1002 POS，1003 其他
     */
    @ApiModelProperty(value = "用于区分客户支付终端：1001 二维码，1002 POS，1003 其他")
    private java.lang.String terminalType;

    /**
     * 记录支付终端设备编号
     */
    @ApiModelProperty(value = "记录支付终端设备编号")
    private java.lang.String terminalId;

    /**
     * 创建该日志的操作员编号
     */
    @ApiModelProperty(value = "创建该日志的操作员编号")
    private java.lang.String createStaff;

    /**
     * 支付商家在收钱哆/云货架登记的商家编码
     */
    @ApiModelProperty(value = "支付商家在收钱哆/云货架登记的商家编码")
    private java.lang.String commercialId;

    /**
     * 支付商家在收钱哆/云货架登记的翼支付账号
     */
    @ApiModelProperty(value = "支付商家在收钱哆/云货架登记的翼支付账号")
    private java.lang.String wingId;

    /**
     * 支付商家在翼支付登记的商家编码
     */
    @ApiModelProperty(value = "支付商家在翼支付登记的商家编码")
    private java.lang.String merchantId;

    /**
     * 使用的付款账号
     */
    @ApiModelProperty(value = "使用的付款账号")
    private java.lang.String winpayId;

    /**
     * 订单实际金额
     */
    @ApiModelProperty(value = "订单实际金额")
    private java.lang.Long orderMoney;

    /**
     * 支付平台支付时产生的手续费
     */
    @ApiModelProperty(value = "支付平台支付时产生的手续费")
    private java.lang.Long chargeMoney;

    /**
     * 默认使用CNY（人民币）
     */
    @ApiModelProperty(value = "默认使用CNY（人民币）")
    private java.lang.String moneyType;

    /**
     * 用于登记订单支付状态：0 订单生成，1 支付中，2 支付成功， 3 业务处理完成
     */
    @ApiModelProperty(value = "用于登记订单支付状态：0 订单生成，1 支付中，2 支付成功， 3 业务处理完成")
    private java.lang.String payStatus;

    /**
     * 支付日志创建时间
     */
    @ApiModelProperty(value = "支付日志创建时间")
    private java.util.Date createDate;

    /**
     * 支付日志更新时间，首次创建时等于创建时间
     */
    @ApiModelProperty(value = "支付日志更新时间，首次创建时等于创建时间")
    private java.util.Date updateDate;

    /**
     * 登记请求信息
     */
    @ApiModelProperty(value = "登记请求信息")
    private java.lang.String payData;

    /**
     * 加密后的请求信息
     */
    @ApiModelProperty(value = "加密后的请求信息")
    private java.lang.String payDataMd;

    /**
     * 支付日志更新时所使用的用户ID，首次创建时为创建用户
     */
    @ApiModelProperty(value = "支付日志更新时所使用的用户ID，首次创建时为创建用户")
    private java.lang.String updateStaff;

    /**
     * 接收方银行编码
     */
    @ApiModelProperty(value = "接收方银行编码")
    private java.lang.String recBankId;

    /**
     * 接收方银行账号
     */
    @ApiModelProperty(value = "接收方银行账号")
    private java.lang.String recAccount;

    /**
     * 接收方账号名称
     */
    @ApiModelProperty(value = "接收方账号名称")
    private java.lang.String recAccountName;
}
