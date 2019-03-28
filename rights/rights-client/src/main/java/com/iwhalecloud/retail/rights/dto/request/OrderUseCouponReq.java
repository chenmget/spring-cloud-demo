package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderUseCouponReq extends AbstractRequest implements Serializable{

    private List<ProductOrderItemDTO> productOrderItemDTOS;

    private List<CouponOrderItemDTO> orderItems;

    @ApiModelProperty("用户id")
    private String memberId;

    @ApiModelProperty("商家id")
    private String merchantId;


}
