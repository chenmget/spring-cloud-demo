package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品分货规则提货传输对象
 *
 * @author z
 * @date 2019/1/31
 */
@Data
public class GoodsRulePurchaseDTO implements Serializable {

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "提货数量，如果是购买传证书，如果是退换传负数")
    private Long purchasedNum;

    @ApiModelProperty(value = "商家ID")
    private String merchantId;
}
