package com.iwhalecloud.retail.order.dto.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SendGoodsItemModel implements Serializable {

    @ApiModelProperty(value = "工号id")
    private String userId;

    @ApiModelProperty(value = "合约机必填，手机串号")
    private String mobileString;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "产品id")
    private String productId;

}
