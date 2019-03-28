package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.cart.CartOrderAmountDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import com.iwhalecloud.retail.order2b.model.CartItemModel;

import java.util.List;

public interface SelectCartGoodsService {

    /**
     * 根据供应商拆单
     */
    List<BuilderOrderModel> demolitionOrderBySupperId(List<CartItemModel> cartGoodsModel);


    /**
     * 构建订单项
     */
    BuilderOrderModel builderOrderItems( List<CartItemModel> productIds);

    /**
     * 订单总金额
     */
    CommonResultResp<CartOrderAmountDTO> selectOrderAmount(BuilderOrderModel builder);

    /**
     * 拼装购物车数据
     */
    CommonResultResp<List<CartItemModel>> builderCartData(PreCreateOrderReq request);
}
