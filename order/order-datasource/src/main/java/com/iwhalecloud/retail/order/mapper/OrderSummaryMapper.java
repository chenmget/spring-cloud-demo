package com.iwhalecloud.retail.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order.dto.response.OrderAmountCategoryRespDTO;
import com.iwhalecloud.retail.order.dto.resquest.OrderAmountCategoryReq;
import com.iwhalecloud.retail.order.dto.resquest.order.OrderCountOrAmountReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单汇总相关查询
 */
@Mapper
public interface OrderSummaryMapper extends BaseMapper {

    // 获取供应商一段时间内 订单数量
    int getOrderCount(OrderCountOrAmountReq req);

    // 获取供应商一段时间内 订单金额
    float getOrderAmount(OrderCountOrAmountReq req);


    // 获取今天的待办订单数量
//    int getTodayTodoOrderCount(TodayTodoOrderCountReq req);

    // 获取今天的订单数量
//    int getTodayOrderCount(TodayOrderCountReq req);

    // 获取今天的订单销售额
//    float getTodayOrderAmount(TodayOrderAmountReq req);

    // 获取过去一周的订单销售额
//    List<PastWeekOrderAmountRespDTO> getPastWeekOrderAmount(PastWeekOrderAmountReq req);

    // 获取某个时间段（今天、本月、本年）的订单销售额类别
    List<OrderAmountCategoryRespDTO> getOrderAmountCategory(OrderAmountCategoryReq req);

}
