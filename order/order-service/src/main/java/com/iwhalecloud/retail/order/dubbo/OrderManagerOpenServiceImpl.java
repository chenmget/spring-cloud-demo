package com.iwhalecloud.retail.order.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order.dbservice.OrderInitVerifyService;
import com.iwhalecloud.retail.order.dbservice.OrderZFlowService;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.model.OrderCollectionModel;
import com.iwhalecloud.retail.order.dto.resquest.*;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;
import com.iwhalecloud.retail.order.ropservice.BuilderOrderManagerService;
import com.iwhalecloud.retail.order.service.OrderManagerOpenService;
import com.iwhalecloud.retail.order.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderManagerOpenServiceImpl implements OrderManagerOpenService {

    @Autowired
    private BuilderOrderManagerService builderOrderManagerService;

    @Autowired
    private OrderInitVerifyService orderInitVerifyService;

    @Autowired
    private OrderZFlowService orderZFlowService;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    public CommonResultResp builderOrder(BuilderOrderRequest request) {
        //参数校验
        CommonResultResp resp = Utils.validatorCheck(request);
        if (!resp.isSuccess()) {
            return resp;
        }
        //本地下单
        resp = builderOrderManagerService.builderOrderLocal(request);

        if (!OmsCommonConsts.RESULE_CODE_SUCCESS.equals(resp.getResultCode())) {
            return resp;
        }


        List<OrderCollectionModel> list = (List<OrderCollectionModel>) resp.getResultData();
        List<String> orderList = new ArrayList<>();
        List<OrderUpdateAttrEntity> orderStatusList = new ArrayList<>();
        for (OrderCollectionModel item : list) {
            OrderUpdateAttrEntity dto = new OrderUpdateAttrEntity();
            dto.setOrderId(item.getOrder_id());
            dto.setPayType(request.getPayType());
            orderStatusList.add(dto);
            orderList.add(dto.getOrderId());
        }
        /**
         * 事务控制
         */
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
        try {

            builderOrderManagerService.orderInitInfo(orderStatusList, request);

            builderOrderManagerService.orderSuccess(orderList, request);
            //提交事务
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            //回滚电商平台订单，把订单状态视为异常单 status=99;
            e.printStackTrace();
            builderOrderManagerService.orderException(orderList, request);
            resp.setResultMsg(e.getMessage());
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
        }
        return resp;
    }

    @Override
    public CommonResultResp updateOrderStatus(UpdateOrderStatusRequest request) {
        OrderUpdateAttrEntity dto = new OrderUpdateAttrEntity();
        dto.setFlowType(request.getFlowType());
        dto.setOrderId(request.getOrderId());
        CommonResultResp resp = orderInitVerifyService.checkOrderStatus(dto);
        if (!resp.isSuccess()) {
            return resp;
        }
        try {
            ActionFlowType actionFlowType = ActionFlowType.matchOpCode(request.getFlowType());
            switch (actionFlowType) {
                case ORDER_HANDLER_SH:  //收货
                    resp = orderZFlowService.sureReciveGoods(request);
                    break;
                case ORDER_HANDLER_QX: //取消订单
                    resp = orderZFlowService.cancelOrder(request);
                    break;
                case ORDER_HANDLER_PJ: //评价
                    resp = orderZFlowService.evaluate(request);
                    break;
                case ORDER_HANDLER_SC: //删除
                    resp = orderZFlowService.deleteOrder(request);
                    break;
                default:
                    resp.setResultMsg(ActionFlowType.NULL.getDesc());
                    resp.setResultCode(ActionFlowType.NULL.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg(e.getMessage());
        }
        return resp;
    }

    @Override
    public CommonResultResp sendGoods(SendGoodsRequest request) {
        OrderUpdateAttrEntity dto = new OrderUpdateAttrEntity();
        dto.setFlowType(request.getFlowType());
        dto.setOrderId(request.getOrderId());
        request.setFlowType(ActionFlowType.ORDER_HANDLER_FH.getCode());
        CommonResultResp resp = orderInitVerifyService.checkOrderStatus(dto);
        if (!resp.isSuccess()) {
            return resp;
        }
        //调用电商能力，发货
        CommonResultResp resultResp = orderZFlowService.sendGoods(request);

        return resultResp;
    }


    @Override
    public CommonResultResp orderHandler(OrderInfoEntryRequest request) {
        CommonResultResp resp = new CommonResultResp();
        ActionFlowType actionFlowType = ActionFlowType.matchOpCode(request.getFlowType());
        switch (actionFlowType) {
            case ORDER_HANDLER_CHRR://串码录入
                //校验
                orderInitVerifyService.checkMobileString(request);
                //串码录入
                if (!CollectionUtils.isEmpty(request.getItem())) {
                    resp = orderZFlowService.addCMRR(request);
                }
                break;
            case ORDER_HANDLER_LZRR: ////揽装录入
                resp = orderZFlowService.addLZRR(request);
                break;
            case ORDER_HANDLER_DDBL://订单补录(合约机)
                resp = orderZFlowService.addDDBR(request);
                break;
            default:
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resp.setResultMsg("flowType 不匹配，未找到服务");
        }
        return resp;
    }

    @Override
    public CommonResultResp payOrder(PayOrderRequest request) {
        OrderUpdateAttrEntity dto = new OrderUpdateAttrEntity();
        dto.setFlowType(request.getFlowType());
        dto.setOrderId(request.getOrderId());
        CommonResultResp resp = orderInitVerifyService.checkOrderStatus(dto);
        if (!resp.isSuccess()) {
            return resp;
        }
        ActionFlowType actionFlowType = ActionFlowType.matchOpCode(request.getFlowType());
        switch (actionFlowType) {
            case ORDER_HANDLER_ZF: //支付
                resp = orderZFlowService.pay(request);
                break;
            case NULL:
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resp.setResultMsg("flowType 不是支付类型，未找到服务");
        }
        return resp;

    }

}
