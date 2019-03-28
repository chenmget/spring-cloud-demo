package com.iwhalecloud.retail.web.controller.b2b.order.dto.response;

import com.iwhalecloud.retail.order2b.dto.model.cart.CartOrderAmountDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.PromotionDTO;
import com.iwhalecloud.retail.rights.dto.OrderCouponDTO;
import com.iwhalecloud.retail.rights.dto.OrderItemWithCouponDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: lin.wenhui@iwhalecloud.com
 * @Date: 2019/2/25 20:07
 * @Description:
 */
@Data
public class CalcAmountResp implements Serializable {

    private static final long serialVersionUID = -1570751980434304987L;

    //订单项列表
    private List<OrderItemWithCouponDTO> orderItemList;

    //优惠券列表
    private List<OrderCouponDTO> couponList;

    private CartOrderAmountDTO orderAmount;

    private List<PromotionDTO> promotionList;

}

