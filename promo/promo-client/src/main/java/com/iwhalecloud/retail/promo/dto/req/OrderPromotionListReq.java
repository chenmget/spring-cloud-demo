package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "订单优惠券列表请求参数")
public class OrderPromotionListReq  implements Serializable {

    private static final long serialVersionUID = 5542339445557380806L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "订单项列表")
    private List<OrderItem> orderItems;

//    @ApiModelProperty(value = "通知类型 1：业务类 2：热点消息 3：通知公告")
//    private String noticeType;

//    @ApiModelProperty(value = "优惠类型：10减免20券30返利40赠送50红包")
//    private java.lang.Byte promotionType;

    @Data
    @ApiModel(value = "订单项详情")
    public static class OrderItem implements Serializable {

        private static final long serialVersionUID = 6204493527956805802L;

        @ApiModelProperty(value = "商品ID")
        private String goodsId;

        @ApiModelProperty(value = "产品ID")
        private String productId;

        @ApiModelProperty(value = "价钱")
        private Double price;

        @ApiModelProperty(value = "数量")
        private Long amount;
    }
}
