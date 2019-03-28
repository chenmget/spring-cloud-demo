package com.iwhalecloud.retail.order2b.busiservice.impl;


import com.iwhalecloud.retail.order2b.busiservice.UpdateOrderFlowService;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.consts.order.TypeStatus;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.order.AdvanceOrderDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderZFlowDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PayOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateApplyStatusRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateOrderStatusRequest;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderApply;
import com.iwhalecloud.retail.order2b.manager.AdvanceOrderManager;
import com.iwhalecloud.retail.order2b.manager.AfterSaleManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.manager.OrderZFlowManager;
import com.iwhalecloud.retail.order2b.model.OrderUpdateAttrModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

@Service
@Slf4j
public class UpdateOrderFlowServiceImpl implements UpdateOrderFlowService {

    @Autowired
    private OrderZFlowManager orderZFlowManager;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private AfterSaleManager afterSaleManager;

    @Autowired
    private AdvanceOrderManager advanceOrderManager;

    @Override
    public CommonResultResp checkFlowType(UpdateOrderStatusRequest request,Order order) {

        CommonResultResp resp=new CommonResultResp();
        if(StringUtils.isEmpty(order.getStatus())){
             order = orderManager.getOrderById(request.getOrderId());
            if (order == null) {
                resp.setResultMsg("未查询到订单");
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                return resp;
            }
        }

        if(OrderAllStatus.ORDER_STATUS_10_.getCode().equals(order.getStatus())){
            resp.setResultMsg("订单已经取消,不能重复操作");
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resp;
        }

       String flowType= orderZFlowManager.selectCurrentFlowType(request.getOrderId());
       if(StringUtils.isEmpty(flowType) || !request.getFlowType().equals(flowType)){
           resp.setResultMsg("环节不匹配:"+request.getFlowType());
           resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
           return resp;
       }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp checkFlowTypeApply(UpdateApplyStatusRequest request, OrderApply apply) {
        CommonResultResp resp=new CommonResultResp();
        if(StringUtils.isEmpty(apply.getApplyState())){
            apply=afterSaleManager.selectOrderApplyById(request.getOrderApplyId());
            if (apply == null) {
                resp.setResultMsg("未找到申请单");
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                return resp;
            }
        }

        if(OrderAllStatus.ORDER_STATUS_10_.getCode().equals(apply.getApplyState())){
            resp.setResultMsg("订单已经取消,不能重复操作");
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resp;
        }

        String flowType= orderZFlowManager.selectCurrentFlowType(request.getOrderApplyId());
        if(StringUtils.isEmpty(flowType) || !request.getFlowType().equals(flowType)){
            resp.setResultMsg("环节不匹配:"+request.getFlowType());
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resp;
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    @Transactional
    public CommonResultResp pay(PayOrderRequest request) {
        CommonResultResp resp=new CommonResultResp();
        //更新订单数据
        OrderUpdateAttrModel updateAttrModel = new OrderUpdateAttrModel();
        updateAttrModel.setOrderId(request.getOrderId());
        updateAttrModel.setPayTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        updateAttrModel.setStatus(OrderAllStatus.ORDER_STATUS_4.getCode());
        updateAttrModel.setPayType(request.getPayType());
        updateAttrModel.setPayMoney(request.getPaymoney());
        updateAttrModel.setPayStatus(request.getPayStatus());
        updateAttrModel.setPayCode(request.getPayCode());
        orderManager.updateOrderAttr(updateAttrModel);

        //更新流程
        OrderZFlowDTO zFlowDTO = new OrderZFlowDTO();
        zFlowDTO.setOrderId(request.getOrderId());
        zFlowDTO.setHandlerId(request.getUserId());
        zFlowDTO.setFlowType(request.getFlowType());
        orderZFlowManager.updateFlowList(zFlowDTO);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    @Transactional
    public CommonResultResp advancePay(PayOrderRequest request) {
        CommonResultResp resp=new CommonResultResp();
        // 更新预售订单数据
        AdvanceOrderDTO dto = new AdvanceOrderDTO();
        dto.setOrderId(request.getOrderId());
        dto.setAdvancePayStatus(request.getPayStatus());
        dto.setAdvancePayCode(request.getPayCode());
        dto.setAdvancePayType(request.getPayType());
        dto.setAdvancePayTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        dto.setAdvancePayMoney(String.valueOf(request.getPaymoney()));
        advanceOrderManager.updateAdvanceOrderAttr(dto);

        // 更新订单状态
        OrderUpdateAttrModel updateAttrModel = new OrderUpdateAttrModel();
        updateAttrModel.setOrderId(request.getOrderId());
        // 订单状态更新为待支付尾款
        updateAttrModel.setStatus(OrderAllStatus.ORDER_STATUS_14.getCode());
        updateAttrModel.setPayMoney(request.getPaymoney());
        updateAttrModel.setPayStatus(request.getPayStatus());
        updateAttrModel.setPayType(request.getPayType());
        orderManager.updateOrderAttr(updateAttrModel);

        //更新流程
        OrderZFlowDTO zFlowDTO = new OrderZFlowDTO();
        zFlowDTO.setOrderId(request.getOrderId());
        zFlowDTO.setHandlerId(request.getUserId());
        zFlowDTO.setFlowType(request.getFlowType());
        orderZFlowManager.updateFlowList(zFlowDTO);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp restPay(PayOrderRequest request) {
        CommonResultResp resp=new CommonResultResp();
        long currentTimeMillis = System.currentTimeMillis();
        // 更新预售订单数据
        AdvanceOrderDTO dto = new AdvanceOrderDTO();
        dto.setOrderId(request.getOrderId());
        dto.setRestPayStatus(request.getPayStatus());
        dto.setRestPayCode(request.getPayCode());
        dto.setRestPayType(request.getPayType());
        dto.setRestPayTime(String.valueOf(new Timestamp(currentTimeMillis)));
        dto.setRestPayMoney(String.valueOf(request.getPaymoney()));
        advanceOrderManager.updateAdvanceOrderAttr(dto);

        // 更新订单信息
        OrderUpdateAttrModel updateAttrModel = new OrderUpdateAttrModel();
        updateAttrModel.setOrderId(request.getOrderId());
        // 订单状态更新为已受理代发货
        updateAttrModel.setStatus(OrderAllStatus.ORDER_STATUS_4.getCode());
        updateAttrModel.setPayTime(String.valueOf(new Timestamp(currentTimeMillis)));
        updateAttrModel.setPayType(request.getPayType());
        updateAttrModel.setPayMoney(request.getPaymoney());
        updateAttrModel.setPayStatus(request.getPayStatus());
        updateAttrModel.setPayCode(request.getPayCode());
        orderManager.updateOrderAttr(updateAttrModel);

        //更新流程
        OrderZFlowDTO zFlowDTO = new OrderZFlowDTO();
        zFlowDTO.setOrderId(request.getOrderId());
        zFlowDTO.setHandlerId(request.getUserId());
        zFlowDTO.setFlowType(request.getFlowType());
        orderZFlowManager.updateFlowList(zFlowDTO);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    @Transactional
    public CommonResultResp sendGoods(OrderUpdateAttrModel updateAttrModel) {

        CommonResultResp resultVO = new CommonResultResp();
        updateAttrModel.setShipStatus(TypeStatus.TYPE_02.getCode());
        updateAttrModel.setShipTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        updateAttrModel.setStatus(OrderAllStatus.ORDER_STATUS_5.getCode());
        orderManager.updateOrderAttr(updateAttrModel);

        OrderZFlowDTO zFlowDTO = new OrderZFlowDTO();
        zFlowDTO.setOrderId(updateAttrModel.getOrderId());
        zFlowDTO.setHandlerId(updateAttrModel.getShipUserId());
        zFlowDTO.setFlowType(ActionFlowType.ORDER_HANDLER_FH.getCode());
        orderZFlowManager.updateFlowList(zFlowDTO);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }


    @Override
    @Transactional
    public CommonResultResp sureReciveGoods(UpdateOrderStatusRequest request) {
        CommonResultResp resp=new CommonResultResp();

        //更新订单数据
        OrderUpdateAttrModel updateAttrModel = new OrderUpdateAttrModel();
        updateAttrModel.setOrderId(request.getOrderId());
        updateAttrModel.setStatus(OrderAllStatus.ORDER_STATUS_6.getCode());
        updateAttrModel.setReceiveTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        orderManager.updateOrderAttr(updateAttrModel);


        OrderZFlowDTO zFlowDTO = new OrderZFlowDTO();
        zFlowDTO.setOrderId(request.getOrderId());
        zFlowDTO.setHandlerId(request.getUserId());
        zFlowDTO.setFlowType(request.getFlowType());
        orderZFlowManager.updateFlowList(zFlowDTO);

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp evaluate(UpdateOrderStatusRequest request) {
        CommonResultResp resp=new CommonResultResp();
        //更新订单数据
        OrderUpdateAttrModel updateAttrModel = new OrderUpdateAttrModel();
        updateAttrModel.setOrderId(request.getOrderId());
        updateAttrModel.setStatus(OrderAllStatus.ORDER_STATUS_10.getCode());
        orderManager.updateOrderAttr(updateAttrModel);


        OrderZFlowDTO zFlowDTO = new OrderZFlowDTO();
        zFlowDTO.setOrderId(request.getOrderId());
        zFlowDTO.setFlowType(request.getFlowType());
        zFlowDTO.setHandlerId(request.getUserId());
        orderZFlowManager.updateFlowList(zFlowDTO);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp cancelOrder(UpdateOrderStatusRequest request) {
        CommonResultResp resp = new CommonResultResp();
        OrderUpdateAttrModel dto = new OrderUpdateAttrModel();
        dto.setOrderId(request.getOrderId());
        dto.setStatus(OrderAllStatus.ORDER_STATUS_10_.getCode());
        orderManager.updateOrderAttr(dto);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp deleteOrder(UpdateOrderStatusRequest request) {
        OrderUpdateAttrModel dto = new OrderUpdateAttrModel();
        dto.setOrderId(request.getOrderId());
        CommonResultResp resultVO = new CommonResultResp();
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }

    @Override
    public CommonResultResp merchantConfirm(UpdateOrderStatusRequest request) {
        CommonResultResp resp=new CommonResultResp();
        //更新订单数据
        OrderUpdateAttrModel updateAttrModel = new OrderUpdateAttrModel();
        updateAttrModel.setOrderId(request.getOrderId());
//        if(StringUtils.isEmpty(request.getConfirmType())){
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
//            resp.setResultMsg("审批意见不能为空");
//            return resp;
//        }

//        if("1".equals(request.getConfirmType())){
//            updateAttrModel.setStatus(OrderAllStatus.ORDER_STATUS_2.getCode());
//
//        }else if("2".equals(request.getConfirmType())){
//            updateAttrModel.setStatus(OrderAllStatus.ORDER_STATUS_12_.getCode());
//        }else{
//            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
//            resp.setResultMsg("审批意见不匹配");
//            return resp;
//        }
        if(OrderManagerConsts.ORDER_CAT_1.equals(request.getOrderCat())){
            updateAttrModel.setStatus(OrderAllStatus.ORDER_STATUS_13.getCode());
        }else{
            updateAttrModel.setStatus(OrderAllStatus.ORDER_STATUS_2.getCode());
        }
        orderManager.updateOrderAttr(updateAttrModel);


        OrderZFlowDTO zFlowDTO = new OrderZFlowDTO();
        zFlowDTO.setOrderId(request.getOrderId());
        zFlowDTO.setHandlerId(request.getUserId());
        zFlowDTO.setFlowType(request.getFlowType());
        orderZFlowManager.updateFlowList(zFlowDTO);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

}
