package com.iwhalecloud.retail.order.manager;


import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order.consts.order.OrderPayType;
import com.iwhalecloud.retail.order.dto.resquest.BuilderOrderRequest;
import com.iwhalecloud.retail.order.dto.model.OrderZFlowModel;
import com.iwhalecloud.retail.order.mapper.OrderZFlowMapper;
import com.iwhalecloud.retail.order.model.OrderFlowInitEntity;
import com.iwhalecloud.retail.order.model.ZFlowEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderZFlowManager {

    @Resource
    private OrderZFlowMapper orderZFlowMapper;

    public List<OrderZFlowModel> selectFlowList(OrderZFlowModel dto) {

        return orderZFlowMapper.selectFlowList(dto);
    }

    public int updateFlowList(OrderZFlowModel dto) {
        return orderZFlowMapper.updateFlowList(dto);
    }

    public int insertFlowList(BuilderOrderRequest request, String orderId) {
        OrderFlowInitEntity req = new OrderFlowInitEntity();
        req.setBindType(request.getBindType());
        req.setOrderType(request.getOrderType());
        req.setPayType(request.getPayType());
        req.setTypeCode(String.valueOf(request.getTypeCode()));
        List<OrderFlowInitEntity> initList = selectFlowInit(req);
        if (CollectionUtils.isEmpty(initList)) {
            return -1;
        }
        List<ZFlowEntity> list = JSON.parseArray(initList.get(0).getFlowList(),ZFlowEntity.class);
        for (ZFlowEntity item : list) {
            item.setOrderId(orderId);
            item.setBindType(request.getBindType());
            item.setHandlerId(request.getMemberId());
        }
        return orderZFlowMapper.insertFlowList(list);
    }

    /**
     * 初始化订单流程
     */
    private List<OrderFlowInitEntity> selectFlowInit(OrderFlowInitEntity dt) {
        return orderZFlowMapper.selectFlowInit(dt);
    }


    public String selectCurrentFlowType(String orderId) {
        OrderZFlowModel dto = new OrderZFlowModel();
        dto.setOrderId(orderId);
        return orderZFlowMapper.currentFlow(dto);
    }

    public List<OrderZFlowModel> orderReminderList(String remindFlag, String flowType, int interval) {
        return orderZFlowMapper.orderReminderList(remindFlag, flowType, interval);
    }

    public int orderReminderUpdate(OrderZFlowModel dto) {
        return orderZFlowMapper.orderReminderUpdate(dto);
    }

}
