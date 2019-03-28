package com.iwhalecloud.retail.order2b.manager;


import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.consts.order.OrderServiceType;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderZFlowDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CreateOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderApplyReq;
import com.iwhalecloud.retail.order2b.entity.OrderFlowInit;
import com.iwhalecloud.retail.order2b.entity.ZFlow;
import com.iwhalecloud.retail.order2b.mapper.OrderZFlowMapper;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrderZFlowManager {


    @Resource
    private OrderZFlowMapper orderZFlowMapper;

    public List<OrderZFlowDTO> selectFlowList(ZFlow dto) {
        return orderZFlowMapper.selectFlowList(dto);
    }

    public int updateFlowList(OrderZFlowDTO dto) {
        ZFlow zFlow = new ZFlow();
        BeanUtils.copyProperties(dto, zFlow);
        return orderZFlowMapper.updateFlowList(zFlow);
    }

    public int insertFlowList(CreateOrderRequest request, BuilderOrderModel model) {
        OrderFlowInit req = new OrderFlowInit();
        req.setTypeCode(String.valueOf(request.getTypeCode()));
        req.setServiceType(OrderServiceType.ORDER_SHIP_TYPE_1.getCode());
        req.setSourceFrom(request.getSourceFrom());
        /**
         * 商家确认
         */
        if (OrderManagerConsts.IS_MERCHANT_CONFIRM.equals(request.getIsMerchantConfirm())) {
            req.setBindType("1");
        } else {
            req.setBindType("0");
        }

        req.setOrderType(model.getOrderCat());
        /**
         * 预售订单
         */
        if (OrderManagerConsts.ORDER_CAT_1.equals(req.getOrderType())) {
            req.setPayType(model.getAdvancePayType());
        } else {
            req.setPayType("0");
        }
        List<OrderFlowInit> initList = selectFlowInit(req);
        if (CollectionUtils.isEmpty(initList)) {
            return -1;
        }
        List<ZFlow> list = JSON.parseArray(initList.get(0).getFlowList(), ZFlow.class);
        for (ZFlow item : list) {
            item.setSourceFrom(request.getSourceFrom());
            item.setOrderId(model.getOrderId());
            item.setHandlerId(request.getUserId());
        }
        return orderZFlowMapper.insertFlowList(list);
    }

    public int insertAfterSaleFlowList(OrderApplyReq request, String orderId) {
        OrderFlowInit req = new OrderFlowInit();
        req.setTypeCode("2");
        req.setBindType("0");
        req.setOrderType("0");
        req.setPayType("0");
        req.setSourceFrom(request.getSourceFrom());
        req.setServiceType(request.getServiceType());
        List<OrderFlowInit> initList = selectFlowInit(req);
        if (CollectionUtils.isEmpty(initList)) {
            return -1;
        }
        List<ZFlow> list = JSON.parseArray(initList.get(0).getFlowList(), ZFlow.class);
        for (ZFlow item : list) {
            item.setSourceFrom(request.getSourceFrom());
            item.setOrderId(orderId);
            item.setHandlerId(request.getUserId());
        }
        return orderZFlowMapper.insertFlowList(list);
    }

    /**
     * 初始化订单流程
     */
    public List<OrderFlowInit> selectFlowInit(OrderFlowInit dt) {
        return orderZFlowMapper.selectFlowInit(dt);
    }


    public String selectCurrentFlowType(String orderId) {
        ZFlow dto = new ZFlow();
        dto.setOrderId(orderId);
        dto = orderZFlowMapper.currentFlow(dto);
        if (dto == null) {
            return null;
        }
        return dto.getFlowType();
    }


}
