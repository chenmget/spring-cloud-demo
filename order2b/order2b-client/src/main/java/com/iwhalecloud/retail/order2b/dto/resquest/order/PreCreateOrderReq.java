package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.annotation.GroupCheckValidate;
import com.iwhalecloud.retail.order2b.annotation.NullCheckValidate;
import com.iwhalecloud.retail.order2b.dto.base.MRequest;
import com.iwhalecloud.retail.order2b.dto.model.order.CouponInsDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderGoodsItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PreCreateOrderReq extends MRequest implements Serializable {

    /**
     * 订单信息
     */
    @ApiModelProperty(value = "用户id,等于userid", hidden = true)
    private String memberId;

    @ApiModelProperty("商家id(卖家的）")
    @NullCheckValidate(message = "merchantId 字段不能为空")
    private String merchantId;

    /**
     * 购买方式
     */
    @ApiModelProperty(value = "【必填】下单类型：购物车：GWCGM,立即购买:LJGM")
    private String sourceType;
    @ApiModelProperty(value = "【立即购买必填】商品列表")
    @GroupCheckValidate(key = "sourceType", value = "LJGM", isList = true, isObject = false, message = "goodsItem 立即购买必填")
    private List<OrderGoodsItemDTO> goodsItem;

    /**
     * 优惠
     */
    private List<CouponInsDTO> couponInsList;

    @ApiModelProperty(value = "预售单=1，普通单=0",hidden = true)
    private List<String> orderCatList;

    @ApiModelProperty("预售订单，活动id")
    private String activityId;

    @ApiModelProperty(value = "商家确认：0:不需要(默认)，1需要",hidden = true)
    private String isMerchantConfirm;


}
