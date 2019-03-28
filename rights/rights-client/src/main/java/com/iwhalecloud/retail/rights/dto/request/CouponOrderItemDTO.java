package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("优惠券对应订单项")
public class CouponOrderItemDTO  extends AbstractRequest implements Serializable {

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
