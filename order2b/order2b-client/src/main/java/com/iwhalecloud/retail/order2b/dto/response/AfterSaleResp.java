package com.iwhalecloud.retail.order2b.dto.response;

import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.consts.order.OrderServiceType;
import com.iwhalecloud.retail.order2b.dto.base.SelectModel;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class AfterSaleResp extends SelectModel implements Serializable {

    private String orderApplyId;
    private String serviceType;
    private String serviceTypeName;
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
    private String createTime;
    private String provinceId;
    private String cityId;
    private String regionId;
    private String province;
    private String city;
    private String region;
    private String address;
    private String applyState;
    private String applyStateName;
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
    private String refundDesc;
    private String refundImgUrl;

    private String handlerName;
    private String auditDesc;


    OrderItemDTO orderItems;

    public String getCreateTime() {
        return timeFormat(createTime);
    }

    public String getServiceTypeName() {
        return OrderServiceType.matchOpCode(serviceTypeName).getName();
    }

    public String getApplyStateName() {
        return OrderAllStatus.matchOpCode(applyStateName).getName();
    }
}
