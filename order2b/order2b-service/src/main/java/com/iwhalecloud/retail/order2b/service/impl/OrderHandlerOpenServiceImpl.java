package com.iwhalecloud.retail.order2b.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.busiservice.CancelOrderService;
import com.iwhalecloud.retail.order2b.busiservice.UpdateOrderFlowService;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateOrderStatusRequest;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.reference.TaskManagerReference;
import com.iwhalecloud.retail.order2b.service.OrderHandlerOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderHandlerOpenServiceImpl implements OrderHandlerOpenService {


    @Autowired
    private UpdateOrderFlowService updateOrderFlowService;

    @Autowired
    private CancelOrderService cancelOrderService;

    @Autowired
    private TaskManagerReference taskManagerReference;

    @Autowired
    private OrderManager orderManager;


    @Override
    public ResultVO updateOrder(UpdateOrderStatusRequest request) {
        ResultVO resultVO = new ResultVO();
        CommonResultResp resp = new CommonResultResp();
        Order order = orderManager.getOrderById(request.getOrderId());
        if (order == null) {
            resultVO.setResultMsg("未查询到订单");
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resultVO;
        }


        ActionFlowType actionFlowType = ActionFlowType.matchOpCode(request.getFlowType());
        switch (actionFlowType) {

            case ORDER_HANDLER_SJXR: //订单确认
                resp = updateOrderFlowService.checkFlowType(request, order);
                if (resp.isFailure()) {
                    resultVO.setResultMsg(resp.getResultMsg());
                    resultVO.setResultCode(resp.getResultCode());
                    return resultVO;
                }
                request.setOrderCat(order.getOrderCat());
                resp = updateOrderFlowService.merchantConfirm(request);
                if (resp.isFailure()) {
                    resultVO.setResultCode(resp.getResultCode());
                    resultVO.setResultMsg(resp.getResultMsg());
                    return resultVO;
                }

                taskManagerReference.updateTask(order.getOrderId(), request.getUserId());
                String message="待付款";
                if(OrderManagerConsts.ORDER_CAT_1.equals(order.getOrderCat())){
                    message="待付定金";
                }
                taskManagerReference.addTaskByHandlerOne(message,order.getOrderId(), request.getUserId(), order.getCreateUserId());

                break;
            case ORDER_HANDLER_QX:  //取消

                if(OrderManagerConsts.ORDER_CAT_1.equals(order.getOrderCat())){
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resultVO.setResultMsg("预售订单不能取消");
                    return resultVO;
                }

                if (order.getStatus().equals(OrderAllStatus.ORDER_STATUS_2.getCode())  //待支付
                        || order.getStatus().equals(OrderAllStatus.ORDER_STATUS_12.getCode()) //待确认
                        ) {
                    cancelOrderService.cancelOrder(request);
                } else {
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resultVO.setResultMsg("只有待支付、待确认订单才能取消");
                    return resultVO;
                }

                break;
            default:
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resultVO.setResultMsg(ActionFlowType.NULL.getDesc());
                return resultVO;
        }
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }

}
