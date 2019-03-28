package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CheckCouponReq extends AbstractRequest implements Serializable{

    private List<CouponCheckReqDTO> couponInstList;

    @ApiModelProperty("用户id")
    private String memberId;

    @ApiModelProperty("商家id")
    private String merchantId;

    @ApiModelProperty("订单项列表")
    private List<CouponOrderItemDTO> orderItems;

}
