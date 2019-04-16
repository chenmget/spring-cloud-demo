package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mzl
 * @date 2019/4/15
 */
@Data
@ApiModel
public class SupplierGroundGoodsDTO implements java.io.Serializable {

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    /**
     * 供货商ID
     */
    @ApiModelProperty(value = "供货商ID")
    private String supplierId;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private String productId;

    /**
     * 上架数量
     */
    @ApiModelProperty(value = "上架数量")
    private Long supplyNum;

    /**
     * 提货价
     */
    @ApiModelProperty(value = "提货价")
    private Double deliveryPrice;
}
