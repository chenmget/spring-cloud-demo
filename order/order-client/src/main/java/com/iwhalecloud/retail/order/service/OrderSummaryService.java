package com.iwhalecloud.retail.order.service;

import com.iwhalecloud.retail.order.dto.response.OrderAmountCategoryRespDTO;
import com.iwhalecloud.retail.order.dto.response.PastWeekOrderAmountRespDTO;
import com.iwhalecloud.retail.order.dto.resquest.*;

import java.util.List;

/**
 * @author zwl
 * @date 2018-11-6
 * 订单汇总相关服务
 */
public interface OrderSummaryService {

    /**
     * 获取今天的待处理订单数
     * @return
     */
    int getTodayTodoOrderCount(TodayTodoOrderCountReq req);

    /**
     * 获取今天的订单数
     * @return
     */
    int getTodayOrderCount(TodayOrderCountReq req);

    /**
     * 获取今天的订单销售额
     * @return
     */
    float getTodayOrderAmount(TodayOrderAmountReq req);

    /**
     * 获取过去一周的订单销售额
     * @return
     */
    List<PastWeekOrderAmountRespDTO> getPastWeekOrderAmount(PastWeekOrderAmountReq req);

    /**
     * 获取某个时间段（今天、本月、本年）的订单销售额类别
     * @return
     */
    List<OrderAmountCategoryRespDTO> getOrderAmountCategory(OrderAmountCategoryReq req);
}
