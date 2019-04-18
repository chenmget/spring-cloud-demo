package com.iwhalecloud.retail.order2b.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iwhalecloud.retail.order2b.config.TableTimeValidate;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ord_order")
public class Order implements Serializable {

    private String lanId;

    private String orderId;
    private String sn;
    private String orderType;
    private String supplierId;
    private String supplierName;
    private String userId;
    private String userName;
    private String merchantId;
    private String status;
    @TableTimeValidate
    private String createTime;
    private String createUserId;
    private String paymentId;
    private String paymentName;
    private String paymentType;
    private String payStatus;
    private String payName;
    private String payType;
    private String payCode;
    private Double payMoney;
    @TableTimeValidate
    private String payTime;
    private String shipStatus;
    private String shipType;
    private Double shipAmount;
    private String receiveName;
    private String receiveAddr;
    private String receiveAddrId;
    private String receiveZip;
    private String receiveEmail;
    private String receiveMobile;
    @TableTimeValidate
    private String receiveTime;
    private Double goodsAmount;

    private Double orderAmount;
    private Integer goodsNum;
    private Double couponAmount;
    private String remark;
    private String sourceFrom;
    private String typeCode;
    private String batchId;
    private String invoiceType;
    private String invoiceTitle;
    private String taxPayerNo;
    private String registerAddress;
    private String registerPhone;
    private String registerBank;
    private String registerBankAcct;
    private Integer isNeedShip;
    private String couponCode;
    private String couponDesc;
    private String workId;
    private Integer isDelete;
    private String getGoodsCode;

    private String shopId;
    private String shopName;

    private String orderCat;
    private String supplierCode;
    private String merchantCode;
    private String buyerCode;

    private String payTransId;

}
