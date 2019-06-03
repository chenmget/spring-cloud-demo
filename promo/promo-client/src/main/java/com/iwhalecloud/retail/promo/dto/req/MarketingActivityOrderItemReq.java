package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @date 2019/5/28
 * 订单算费-优惠活动减免
 */
@Data
public class MarketingActivityOrderItemReq implements Serializable {


    @NotEmpty(message = "产品ID不能为空")
    @ApiModelProperty(value = "产品ID")
    private String productId;
    @NotEmpty(message = "购买数量不能为空")
    @ApiModelProperty(value = "购买数量")
    private Integer num;
    @ApiModelProperty(value = "参与活动数量")
    private Double price;
    @NotEmpty(message = "订单ID不能为空")
    @ApiModelProperty(value = "订单ID")
    private String orderId;
    @NotEmpty(message = "订单项编号不能为空")
    @ApiModelProperty(value = "订单项编号")
    private String orderItemId;
    @ApiModelProperty(value = "商品Id")
    private String goodsId;
    @ApiModelProperty("活动类型")
    private String activityTypeCode;
    @ApiModelProperty("优惠类型")
    private String promotionTypeCode;
}
