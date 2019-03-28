package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.OrderWorkPlatformShowResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderStatisticsReq;

/**
 * 订单统计
 */
public interface OrderStatisticsService {

    /**
     * 根据商家ID获取的订单统计数据
     * @return
     */
    ResultVO<OrderWorkPlatformShowResp> getOrderWorkPlatformShowTotal(OrderStatisticsReq req);

}
