package com.iwhalecloud.retail.order.model;

import com.iwhalecloud.retail.order.config.WhaleCloudDBKeySequence;
import lombok.Data;

import java.io.Serializable;

@Data
@WhaleCloudDBKeySequence(keySeqName = "deliveryId")
public class DeliverEntity implements Serializable {

    private String deliveryId;
    private String type;
    private String orderId;
    private String memberId;
    private String shipType;

    private String logiId;
    private String logiName;
    private String shipName;
    private String logiNo;
    private String provinceId;
    private String province;
    private String cityId;
    private String city;
    private String regionId;
    private String region;


    private String shipAddr;
    private String shipZip;
    private String shipTel;
    private String shipMobile;
    private String shipEmail;
    private String createTime;


    private String shipStatus;
}
