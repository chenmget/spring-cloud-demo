package com.iwhalecloud.retail.order2b.dto.model.cart;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CartOrderAmountDTO  implements Serializable{

    private Double goodsAmount;

    private Double orderAmount;
    private Integer goodsNum;
    private Double couponAmount;

    @ApiModelProperty("定金")
    private Double advanceAmount;

    @ApiModelProperty("尾款")
    private Double restAmount;
}
