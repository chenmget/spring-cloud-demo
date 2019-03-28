package com.iwhalecloud.retail.order.manager;

import com.iwhalecloud.retail.order.dto.response.OrderAmountCategoryRespDTO;
import com.iwhalecloud.retail.order.dto.resquest.OrderAmountCategoryReq;
import com.iwhalecloud.retail.order.dto.resquest.order.OrderCountOrAmountReq;
import com.iwhalecloud.retail.order.mapper.OrderSummaryMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrderSummaryManager {
    @Resource
    private OrderSummaryMapper orderSummaryMapper;

    /**
     * 获取订单数量统计
     */
    public int getOrderCount(OrderCountOrAmountReq req){
        return orderSummaryMapper.getOrderCount(req);
    }

    /**
     * 获取订单金额统计
     */
    public float getOrderAmount(OrderCountOrAmountReq req){
        return orderSummaryMapper.getOrderAmount(req);
    }

//    public int getTodayTodoOrderCount(TodayTodoOrderCountReq req){
//        return orderSummaryMapper.getTodayTodoOrderCount(req);
//    }

//    public int getTodayOrderCount(TodayOrderCountReq req){
//        return orderSummaryMapper.getTodayOrderCount(req);
//    }

//    public float getTodayOrderAmount(TodayOrderAmountReq req){
//        return orderSummaryMapper.getTodayOrderAmount(req);
//    }

//    public List<PastWeekOrderAmountRespDTO> getPastWeekOrderAmount(PastWeekOrderAmountReq req){
//        return orderSummaryMapper.getPastWeekOrderAmount(req);
//    }

    public List<OrderAmountCategoryRespDTO> getOrderAmountCategory(OrderAmountCategoryReq req){
        return orderSummaryMapper.getOrderAmountCategory(req);
    }

}
