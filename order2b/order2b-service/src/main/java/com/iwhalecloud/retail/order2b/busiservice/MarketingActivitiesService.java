package com.iwhalecloud.retail.order2b.busiservice;

import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PreCreateOrderReq;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import com.iwhalecloud.retail.order2b.model.CreateOrderLogModel;

/**
 * 营销活动
 */
public interface MarketingActivitiesService {
    /**
     * 算费参与活动
     * (model:订单拆单的话，只对第一个订单参与优惠券)
     */
    CommonResultResp orderActivityAmount(PreCreateOrderReq request, BuilderOrderModel model);

    /**
     * 使用优惠券
     */
    CommonResultResp userCoupon(PreCreateOrderReq request, BuilderOrderModel model, CreateOrderLogModel logModel);

    /**
     * 下单参与活动
     */
    CommonResultResp marketingActivities(PreCreateOrderReq request, BuilderOrderModel model, CreateOrderLogModel logModel);
}
