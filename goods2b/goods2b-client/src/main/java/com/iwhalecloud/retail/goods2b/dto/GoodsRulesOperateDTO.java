package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class GoodsRulesOperateDTO implements Serializable {

    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "提货数量")
    private Long drawNum;

    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    @ApiModelProperty(value = "商家类型：1 厂商    2 地包商    3 省包商   4 零售商")
    private java.lang.String merchantType;

}
