package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import com.iwhalecloud.retail.order2b.model.CartItemModel;
import com.iwhalecloud.retail.order2b.model.CreateOrderLogModel;

import java.util.List;

public interface AdvanceSaleOrderBuilderService {


    CommonResultResp checkRule(PreCreateOrderReq request, List<CartItemModel> list);

    CommonResultResp<CreateOrderLogModel> orderOtherHandler(CreateOrderRequest request, List<BuilderOrderModel> list);

    CommonResultResp rollback(List<BuilderOrderModel> list, CreateOrderLogModel log);
}
