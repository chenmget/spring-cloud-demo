package com.iwhalecloud.retail.order2b.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iwhalecloud.retail.order2b.config.TableTimeValidate;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ord_delivery")
public class Delivery implements Serializable{

    private String lanId;

    private String deliveryId;
    private String type;
    private String orderId;
    private String userid;
    private String money;
    private String shipType;
    private String isProtect;
    private String protectPrice;
    private String logiId;
    private String logiName;
    private String logiNo;
    private String shipName;
    private String provinceId;
    private String cityId;
    private String regionId;
    private String region;
    private String city;
    private String province;
    private String shipAddr;
    private String shipZip;
    private String shipTel;
    private String shipMobile;
    private String shipEmail;
    private String opName;

    @TableTimeValidate
    private String createTime;
    private String reason;
    private String remark;
    private Integer shipNum;
    private String printStatus;
    private String weight;
    private String shipStatus;
    private Integer batchId;
    private String houseId;
    private String shippingCompany;
    private String nShippingAmount;
    @TableTimeValidate
    private String shippingTime;
    private String outHouseId;
    private String postFee;
    private String logiReceiver;
    private String logiReceiverPhone;
    @TableTimeValidate
    private String userRecieveTime;
    private String deliveryType;
    private String shipDesc;
    private String orderApplyId;

    private String sourceFrom;

}
