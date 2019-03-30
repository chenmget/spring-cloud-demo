package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order2b.dto.model.order.SettleRecordOrderDTO;
import com.iwhalecloud.retail.order2b.manager.DeliveryManager;
import com.iwhalecloud.retail.order2b.service.SettleRecordOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

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
    public List<SettleRecordOrderDTO> getSettleRecordOrder(List<String> orderIds) {
        return deliveryManager.getSettleRecordOrder(orderIds);
    }
}
