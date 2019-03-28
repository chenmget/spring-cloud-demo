package com.iwhalecloud.retail.rights.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CouponInstModel {

    @ApiModelProperty("优惠券实例")
    private String couponInst;

    @ApiModelProperty("是否选中")
    private String isCheck;

    @ApiModelProperty("是否是当前操作的")
    private String isNowCheck;

    @ApiModelProperty("是否可用;这个要更新")
    private String isUse;

}
