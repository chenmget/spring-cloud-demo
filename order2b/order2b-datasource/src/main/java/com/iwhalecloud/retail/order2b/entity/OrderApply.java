package com.iwhalecloud.retail.order2b.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iwhalecloud.retail.order2b.config.TableTimeValidate;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ord_order_apply")
public class OrderApply implements Serializable {

    private String lanId;

    private String orderApplyId;
    private String serviceType;
    private Integer submitNum;
    private String applyProof;
    private String returnReson;
    private String questionDesc;
    private String uploadImgUrl;
    private String goodReturnType;
    private String refundType;
    private String refundValue;
    private String bankInfo;
    private String accountHolderName;
    private String bankAccount;
    private String linkman;
    private String phoneNum;
    private String handlerId;
    private String applicantId;
    private String createUserId;
    @TableTimeValidate
    private String createTime;
    private String provinceId;
    private String cityId;
    private String regionId;
    private String province;
    private String city;
    private String region;
    private String address;
    private String applyState;
    private String orderId;
    private String orderItemId;
    private Double returnedAccount;
    private String returnedType;
    private String returnedKind;
    private String sourceFrom;
    private Double shipPrice;
    private Double depreciationPrice;
    private Double payPrice;
    private String workId;
    private Integer batchId;
    private String supplierName;
    private String refundImgUrl;
    private String refundDesc;

    private String auditDesc;
}
