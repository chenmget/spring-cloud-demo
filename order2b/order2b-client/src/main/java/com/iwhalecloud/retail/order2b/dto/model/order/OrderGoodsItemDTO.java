package com.iwhalecloud.retail.order2b.dto.model.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Data
public class OrderGoodsItemDTO implements Serializable {

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("订单项ID")
    private String orderItemId;

    @ApiModelProperty("商品id")
    private String goodsId;

    @ApiModelProperty("商品对应的产品ID")
    private String productId;

    @ApiModelProperty("商品购买数量")
    private Integer num; // 商品数量

    @ApiModelProperty("商品价格")
    private Double price;

    @ApiModelProperty("商品价格减免金额（参与直减活动产品 会有值）")
    private Double priceDiscount;

    @ApiModelProperty("订单项总的减免金额")
    private Double discount;
}
