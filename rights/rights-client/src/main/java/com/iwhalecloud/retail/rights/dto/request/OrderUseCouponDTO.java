package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderUseCouponDTO extends AbstractRequest implements Serializable {

    private String couponInstId;

    @ApiModelProperty("优惠券名称")
    private String 	mktResName;

    @ApiModelProperty("优惠券编码")
    private String mktResNbr;

    @ApiModelProperty("优惠券标识")
    private String mktResId;

    @ApiModelProperty("选择状态：1选中，2不选中")
    private String checkType;

    private String orderItemId;

    private String goodsId;

    private String productId;

    private Integer num;

    private Double price;

    @ApiModelProperty("减免金额")
    private Double discount;
}
