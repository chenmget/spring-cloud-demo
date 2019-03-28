package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.cart.CartItemDTO;
import com.iwhalecloud.retail.order2b.dto.response.CartListResp;
import com.iwhalecloud.retail.order2b.dto.resquest.cart.AddCartReq;
import com.iwhalecloud.retail.order2b.dto.resquest.cart.DeleteCartReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.model.CartItemModel;

import java.util.List;

public interface BuilderCartService {

    /**
     * 分货规则校验
     */
    CommonResultResp goodsRulesCheck(AddCartReq req);

    List<CartListResp> pkgGoodsInf(List<CartItemDTO> itemList);

    List<CartItemModel> selectCart(CreateOrderRequest request);

    ResultVO<Boolean> deleteCart(DeleteCartReq req);
}
