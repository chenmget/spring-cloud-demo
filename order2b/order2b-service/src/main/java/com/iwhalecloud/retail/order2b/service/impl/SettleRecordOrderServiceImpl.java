package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order2b.dto.model.order.SettleRecordOrderDTO;
import com.iwhalecloud.retail.order2b.manager.DeliveryManager;
import com.iwhalecloud.retail.order2b.service.SettleRecordOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/29.
 */
@Slf4j
@Component("settleRecordOrderService")
@Service
public class SettleRecordOrderServiceImpl implements SettleRecordOrderService {
    @Autowired
    private DeliveryManager deliveryManager;

    @Override
    public List<SettleRecordOrderDTO> getSettleRecordOrder(List<String> orderIds,String lanId) {
        List<SettleRecordOrderDTO> settleRecordOrderDTOs = deliveryManager.getSettleRecordOrder(orderIds,lanId);
        if(!CollectionUtils.isEmpty(settleRecordOrderDTOs)){
            Map<String,Integer> orderIdNumMap = this.frequencyOfListElements(settleRecordOrderDTOs);
            if(MapUtils.isNotEmpty(orderIdNumMap)){
                for(SettleRecordOrderDTO settleRecordOrderDTO : settleRecordOrderDTOs){
                    String orderId = settleRecordOrderDTO.getOrderId();
                    if(orderIdNumMap.containsKey(orderId)){
                        settleRecordOrderDTO.setCouponPrice(settleRecordOrderDTO.getCouponPrice()/orderIdNumMap.get(orderId));
                    }
                }
            }
        }

        return settleRecordOrderDTOs;
    }

    private static  Map<String,Integer> frequencyOfListElements(List<SettleRecordOrderDTO> settleRecordOrderDTOs){
        if (settleRecordOrderDTOs == null || settleRecordOrderDTOs.size() == 0) return null;
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (SettleRecordOrderDTO settleRecordOrderDTO : settleRecordOrderDTOs) {
            Integer count = map.get(settleRecordOrderDTO.getOrderId());
            map.put(settleRecordOrderDTO.getOrderId(), (count == null) ? 1 : count + 1);
        }
        return map;
    }
}
