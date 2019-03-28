package com.iwhalecloud.retail.order2b.manager;

import com.iwhalecloud.retail.order2b.dto.resquest.report.OrderStatisticsMatureReq;
import com.iwhalecloud.retail.order2b.mapper.OrderOperatingDayStatisticsMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class OrderOperatingDayStatisticsManager {
    @Resource
    private OrderOperatingDayStatisticsMapper orderOperatingDayStatisticsMapper;

    public int getOrderCountByTimeAndRole(OrderStatisticsMatureReq req){
        Integer count= orderOperatingDayStatisticsMapper.getOrderCountByTimeAndRole(req);
       if (count ==null) {
           return 0;
       }
        return count;
    }

    public double getOrderAmountByTimeAndRole(OrderStatisticsMatureReq req){
        Double amount=orderOperatingDayStatisticsMapper.getOrderAmountByTimeAndRole(req);
        if (amount==null){
            return 0.0;
        }
        return amount;
    }
}
