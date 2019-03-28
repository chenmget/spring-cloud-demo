package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import com.iwhalecloud.retail.order2b.model.CartItemModel;
import com.iwhalecloud.retail.order2b.model.CreateOrderLogModel;

import java.util.List;

public interface OrdinaryOrderBuilderService {

    /**
     * 规则校验
     */
    CommonResultResp checkRule(PreCreateOrderReq request, List<CartItemModel> list);


    /**
     *  第三方接口，操作订单
     */
    CommonResultResp<CreateOrderLogModel> orderOtherHandler(CreateOrderRequest request, List<BuilderOrderModel> list);

    /**
     * 回滚
     */
    CommonResultResp rollback(List<BuilderOrderModel> list, CreateOrderLogModel log);
}
