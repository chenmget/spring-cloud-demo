package com.iwhalecloud.retail.order2b.dto.model.order;

import com.iwhalecloud.retail.order2b.dto.base.SelectModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeliveryDTO extends SelectModel implements Serializable{

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
    private String shippingTime;
    private String outHouseId;
    private String postFee;
    private String logiReceiver;
    private String logiReceiverPhone;
    private String userRecieveTime;
    private String deliveryType;
    private String shipDesc;

    public String getCreateTime() {
        return timeFormat(createTime);
    }
}
