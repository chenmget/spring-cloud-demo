package com.iwhalecloud.retail.order2b.dto.model.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CouponInsDTO implements Serializable {

    /**
     * 优惠券信息
     */
    @ApiModelProperty(value = "优惠券实例id")
    private String couponCode;
    @ApiModelProperty(value = "优惠券名称")
    private String couponDesc;
    @ApiModelProperty("优惠类型：1. 卡券" +
            "2. 红包" +
            "3. 返利" +
            "4. 价保款")
    private String couponType;

    @ApiModelProperty("优惠券标识：和营销活动关联字段")
    private String couponMKId;


    private String productId;
}
