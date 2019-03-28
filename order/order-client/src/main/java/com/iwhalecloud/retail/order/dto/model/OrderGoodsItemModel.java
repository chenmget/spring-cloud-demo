package com.iwhalecloud.retail.order.dto.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderGoodsItemModel implements Serializable {

    @ApiModelProperty(value = "商品productId")
    private String productId;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNum;
}
