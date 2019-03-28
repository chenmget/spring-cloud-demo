package com.iwhalecloud.retail.order.dto.model;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@Data
public class DeliveryModel implements Serializable {

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

    public String getCreateTime() {
        return timeFormat(createTime);
    }

    private String timeFormat(String payTime){
        if(StringUtils.isEmpty(payTime)){
            return payTime;
        }
        payTime=payTime.replace(".0","");
        return payTime;
    }

}
