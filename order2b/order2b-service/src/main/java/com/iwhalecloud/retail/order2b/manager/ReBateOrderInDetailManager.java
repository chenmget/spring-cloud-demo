package com.iwhalecloud.retail.order2b.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.response.OrderItemDetailReBateResp;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.ReBateOrderInDetailReq;
import com.iwhalecloud.retail.order2b.mapper.OrderItemDetailMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lhr 2019-03-30 15:49:30
 */
@Component
public class ReBateOrderInDetailManager {

    @Resource
    private OrderItemDetailMapper orderItemDetailMapper;

    public Page<OrderItemDetailReBateResp> queryOrderItemDetailByOrderId(ReBateOrderInDetailReq req){
        Page<OrderItemDetailReBateResp> page = new Page<OrderItemDetailReBateResp>(req.getPageNo(), req.getPageSize());
        return orderItemDetailMapper.queryOrderItemDetailByOrderId(page,req);
    }

}
