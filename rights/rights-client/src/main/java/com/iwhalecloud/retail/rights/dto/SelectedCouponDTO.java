package com.iwhalecloud.retail.rights.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "已选中的优惠券对象")
public class SelectedCouponDTO implements Serializable {
    private static final long serialVersionUID = -6154046698092558718L;

    @ApiModelProperty("优惠券实例id")
    private String couponInstId;

    @ApiModelProperty("当前优惠券使用在的那个订单项id")
    private String orderItemId;

//    @ApiModelProperty("优惠券实例")
//    private CouponInstDetailDTO couponInst;
//
//    @ApiModelProperty("当前优惠券使用在的那个订单项")
//    private CouponOrderItemDTO bindOrderItem;

}
