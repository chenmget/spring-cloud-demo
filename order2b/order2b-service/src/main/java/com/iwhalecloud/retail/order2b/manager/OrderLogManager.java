package com.iwhalecloud.retail.order2b.manager;

import com.iwhalecloud.retail.order2b.dto.model.order.OrderLogDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderLogGetReq;
import com.iwhalecloud.retail.order2b.entity.OrderLog;
import com.iwhalecloud.retail.order2b.mapper.OrderLogMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class OrderLogManager{
    @Resource
    private OrderLogMapper orderLogMapper;

    public int insertInto(OrderLog orderLog){
        return orderLogMapper.insert(orderLog);
    }

    /**
     * 根据条件查询订单日志
     *
     * @param req 条件入参
     * @return 订单日志集合
     */
    public List<OrderLogDTO> queryOrderLogByCondition(OrderLogGetReq req) {
        return orderLogMapper.queryOrderLogByCondition(req);
    }


}
