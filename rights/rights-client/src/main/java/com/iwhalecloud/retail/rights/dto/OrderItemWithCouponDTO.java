package com.iwhalecloud.retail.rights.dto;

import com.iwhalecloud.retail.rights.dto.response.CouponInstDetailDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("订单项信息 带出优惠信息")
public class OrderItemWithCouponDTO implements Serializable {

    @ApiModelProperty("订单项ID")
    private String orderItemId;

    @ApiModelProperty("商品id")
    private String goodsId;

    @ApiModelProperty("商品对应的产品ID")
    private String productId;

    @ApiModelProperty("商品购买数量")
    private Integer num;

    @ApiModelProperty("商品价格")
    private Double price;

    @ApiModelProperty("商品价格减免金额（参与直减活动产品 会有值）")
    private Double priceDiscount;

    @ApiModelProperty("订单项已经绑定选中的优惠券列表")
    private List<CouponInstDetailDTO> bingCouponList;

    /**  下面参数  不用其他地方传过来 ***/

    @ApiModelProperty("订单使用优惠券后的总减免金额")
    private Double discount;

    @ApiModelProperty("当前订单的最小的抵扣上限，取绑定优惠券列表中的最小的抵扣上限（为null或0 表示还没有绑定优惠券）")
    private Double maxValue;

    @ApiModelProperty("当前订单的最大的抵扣下限，取绑定优惠券列表中的最大的抵扣下限（为null或0 表示还没有绑定优惠券）")
    private Double minValue;


}
