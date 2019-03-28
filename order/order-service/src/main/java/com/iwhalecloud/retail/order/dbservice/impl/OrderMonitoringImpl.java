package com.iwhalecloud.retail.order.dbservice.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order.dto.model.OrderZFlowModel;
import com.iwhalecloud.retail.order.manager.OrderCoreManager;
import com.iwhalecloud.retail.order.manager.OrderZFlowManager;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;
import com.iwhalecloud.retail.order.service.OrderMonitoring;
import com.iwhalecloud.retail.pay.dto.SmsSendDTO;
import com.iwhalecloud.retail.pay.manager.SmsSendManager;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class OrderMonitoringImpl implements OrderMonitoring {

    @Resource
    private OrderZFlowManager orderZFlowManager;

    @Resource
    private OrderCoreManager orderCoreManager;

    @Resource
    private SmsSendManager smsSendManager;

    @Override
    public List<OrderZFlowModel> orderExecutionMonitoring(OrderZFlowModel dto) {
        List<OrderZFlowModel> orderZFlowDTOList = orderZFlowManager.selectFlowList(dto);
        return orderZFlowDTOList;
    }

    @Override
    public int orderRemind() {

        return 0;
    }

    @Override
    public int orderReminder() {
        // 用户下单购买商品，付款成功后，如果超过2天尚未发货，可在小程序订单管理页面上点击催单按钮。会立即发送编号3的订单提醒。
        String flowType = "H";
        String remindFlag = "0000000100";
        int interval = 2 * 24 * 60;
        List<OrderZFlowModel> orderZFlowDTOList = orderZFlowManager.orderReminderList(remindFlag, flowType, interval);
        for (OrderZFlowModel orderZFlowDTO : orderZFlowDTOList) {
            SmsSendDTO smsSendDTO = new SmsSendDTO();
            smsSendDTO.setSmsContent("【湖南电信】您好，" + orderZFlowDTO.getOrderId() + "订单需要处理发货，请及时操作。");
            int ret = smsSendManager.saveSms(smsSendDTO);
            if (ret == 1) {
                orderZFlowDTO.setRemindFlag(remindFlag);
                int retUpdateFlag = orderZFlowManager.orderReminderUpdate(orderZFlowDTO);
            }
        }
        return 0;
    }

    @Override
    public int orderTimeoutMonitoring() {
        return 0;
    }

    @Override
    public int orderRevocationMonitoring() {
        // 待支付的订单超过30分钟后，仍未支付，自动跳转状态为已取消-超时取消。
        String flowType = "C";
        int interval = 30;
        String remindFlag = "0000010000";
        List<OrderZFlowModel> orderZFlowDTOList = orderZFlowManager.orderReminderList(remindFlag, flowType, interval);
        for (OrderZFlowModel orderZFlowDTO : orderZFlowDTOList) {
            OrderUpdateAttrEntity dto = new OrderUpdateAttrEntity();
            dto.setOrderId(orderZFlowDTO.getOrderId());
            dto.setStatus(OrderAllStatus.ORDER_STATUS_10_.getCode());

            orderZFlowDTO.setRemindFlag(remindFlag);
            orderCoreManager.updateOrderStatus(dto);
            int retUpdateFlag = orderZFlowManager.orderReminderUpdate(orderZFlowDTO);
        }
        return 0;
    }
}
