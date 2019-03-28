package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.iwhalecloud.retail.order2b.busiservice.CancelOrderService;
import com.iwhalecloud.retail.order2b.busiservice.CreateOrderService;
import com.iwhalecloud.retail.order2b.busiservice.SelectOrderService;
import com.iwhalecloud.retail.order2b.busiservice.UpdateOrderFlowService;
import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.config.WhaleCloudKeyGenerator;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CancelOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateOrderStatusRequest;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderLog;
import com.iwhalecloud.retail.order2b.manager.OrderLogManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.model.BuilderOrderModel;
import com.iwhalecloud.retail.order2b.model.CreateOrderLogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;


@Service
public class CancelOrderServiceImpl implements CancelOrderService {

    @Autowired
    private UpdateOrderFlowService updateOrderFlowService;

    @Autowired
    private CreateOrderService createOrderService;

    @Autowired
    private SelectOrderService selectOrderService;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private OrderLogManager orderLogManager;

    @Autowired
    private WhaleCloudKeyGenerator whaleCloudKeyGenerator;

    @Override
    public CommonResultResp cancelOrder(UpdateOrderStatusRequest request) {
        CommonResultResp resp = new CommonResultResp();
        Order order = orderManager.getOrderById(request.getOrderId());
        if (order == null) {
            resp.setResultMsg("未查询到订单");
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resp;
        }

        if (OrderAllStatus.ORDER_STATUS_10_.getCode().equals(order.getStatus())) {
            resp.setResultMsg("订单已经取消,不能重复操作");
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resp;
        }
        updateOrderFlowService.cancelOrder(request);

        CreateOrderLogModel logModel = new CreateOrderLogModel();
        List<BuilderOrderModel> list = selectOrderService.builderOrderInfoRollback(request.getOrderId(), logModel);
        if (!CollectionUtils.isEmpty(list)) {
            createOrderService.builderFinishOnFailure(list, logModel);
        }

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }


    @Transactional
    @Override
    public CommonResultResp autoCancelOrder(CancelOrderRequest request) {
        CommonResultResp resultVO = new CommonResultResp();
        request.setFlowType(ActionFlowType.ORDER_HANDLER_QX.getCode());

        Order order = orderManager.getOrderById(request.getOrderId());

        if (order == null) {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("未查询到订单");
            return resultVO;
        }

        if (OrderAllStatus.ORDER_STATUS_13.getCode().equals(order.getStatus()) || //待付定金
                OrderAllStatus.ORDER_STATUS_14.getCode().equals(order.getStatus())  //待付尾款
                ) {
            resultVO = cancelOrder(request);


        } else {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultCode("只有待付定金，待付尾款的订单能取消");
            return resultVO;
        }

        /**
         * 记录日志
         */
        OrderLog orderLog = new OrderLog();
        orderLog.setLogId(whaleCloudKeyGenerator.tableKeySeq(DBTableSequence.ORD_ORDER_LOG.getCode()));
        orderLog.setChangeAction(request.getFlowType());
        orderLog.setChangeReson(request.getCancelReason());
        orderLog.setOrderId(request.getOrderId());
        orderLog.setCreateTime(new Date());
        orderLog.setPreStatus(order.getStatus());
        orderLog.setPostStatus(OrderAllStatus.ORDER_STATUS_10_.getCode());
        orderLog.setUserId(request.getUserId());
        orderLogManager.insertInto(orderLog);

        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }
}
