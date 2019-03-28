package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2019/1/16
 */
@Data
public class BuyCountCheckDTO implements Serializable {

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private String productId;

    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量")
    private Long buyCount;
}
