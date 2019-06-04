package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.iwhalecloud.retail.order2b.authpay.PayAuthorizationService;
import com.iwhalecloud.retail.order2b.busiservice.AfterSaleTHTKService;
import com.iwhalecloud.retail.order2b.busiservice.AfterSalesTHService;
import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.config.WhaleCloudKeyGenerator;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.consts.order.OrderServiceType;
import com.iwhalecloud.retail.order2b.consts.order.TypeStatus;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderZFlowDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PayOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateApplyStatusRequest;
import com.iwhalecloud.retail.order2b.entity.*;
import com.iwhalecloud.retail.order2b.manager.AfterSaleManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.manager.OrderZFlowManager;
import com.iwhalecloud.retail.order2b.manager.PromotionManager;
import com.iwhalecloud.retail.order2b.mapper.OrderMapper;
import com.iwhalecloud.retail.order2b.model.OrderItemDetailModel;
import com.iwhalecloud.retail.order2b.model.OrderUpdateAttrModel;
import com.iwhalecloud.retail.order2b.model.PromotionModel;
import com.iwhalecloud.retail.order2b.reference.GoodsManagerReference;
import com.iwhalecloud.retail.order2b.reference.ResNbrManagerReference;
import com.iwhalecloud.retail.order2b.reference.TaskManagerReference;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AfterSaleTHTKServiceImpl implements AfterSaleTHTKService {


    @Autowired
    private AfterSalesTHService afterSalesTHService;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private AfterSaleManager afterSaleManager;

    @Autowired
    private WhaleCloudKeyGenerator whaleCloudKeyGenerator;

    @Autowired
    private OrderZFlowManager orderZFlowManager;

    @Autowired
    private ResNbrManagerReference resNbrManagerReference;

    @Autowired
    private TaskManagerReference taskManagerReference;

    @Autowired
    private GoodsManagerReference goodsManagerReference;

    @Autowired
    private PromotionManager promotionManager;

    @Autowired
    private OrderMapper orderMapper;
    
    @Override
    public CommonResultResp tkCheck(OrderApplyReq req) {

        CommonResultResp resp = new CommonResultResp();
        if (req.getSubmitNum() == null || req.getSubmitNum() < 1) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("提交数量必须大于0");
            return resp;
        }

        Order order = orderManager.getOrderById(req.getOrderId());
        OrderItem orderItem = orderManager.getOrderItemById(req.getOrderItemId());

        if (order == null || orderItem == null) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("未找到订单或订单项");
            return resp;
        }

        if (orderItem.getReturnNum() == null) {
            orderItem.setReturnNum(0);
        }
        if (req.getSubmitNum() > (orderItem.getNum() - orderItem.getReturnNum())) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("退货退款的数量不能超过订单的数量");
            return resp;
        }


        OrderServiceType serviceType = OrderServiceType.matchOpCode(req.getServiceType());
        String handlerId = order.getMerchantId();
        switch (serviceType) {
            case ORDER_SHIP_TYPE_3: //换货
                resp = afterSalesTHService.check(req);
                if (resp.isFailure()) {
                    return resp;
                }

                CommonResultResp<String> hhObject = goodsManagerReference.getHHObject(orderItem.getProductId(), handlerId);
                if (hhObject.isSuccess()) {
                    handlerId = hhObject.getResultData();
                } else {
                    resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resp.setResultMsg(hhObject.getResultMsg());
                    return resp;
                }
                break;
            case ORDER_SHIP_TYPE_4://退货

                resp = checkActivity(req, order);
                if (resp.isFailure()) {
                    return resp;
                }
                resp = afterSalesTHService.check(req);
                if (resp.isFailure()) {
                    return resp;
                }

                break;
            case ORDER_SHIP_TYPE_2://退款
                resp = checkActivity(req, order);
                if (resp.isFailure()) {
                    return resp;
                }
                break;
            default:
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resp.setResultMsg("serviceType 不符合");
                return resp;
        }

        /**
         * 申请单只能申请一次，完成之后才能再次申请
         */
        OrderApply orderApply = afterSaleManager.getLastOrderApplyByOrderId(req.getOrderId());
        if (orderApply != null) {
            if (OrderAllStatus.ORDER_STATUS_21_.getCode().equals(orderApply.getApplyState()) //不通过
                    || OrderAllStatus.ORDER_STATUS_1_.getCode().equals(orderApply.getApplyState()) //已退货
                    || OrderAllStatus.ORDER_STATUS_2_.getCode().equals(orderApply.getApplyState()) //已退款
                    || OrderAllStatus.ORDER_STATUS_3_.getCode().equals(orderApply.getApplyState()) //已换货
                    || OrderAllStatus.ORDER_STATUS_10_.getCode().equals(orderApply.getApplyState()) //取消
                    ) {

                resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            } else {
                String message = "";
                OrderServiceType orderSer = OrderServiceType.matchOpCode(orderApply.getServiceType());
                switch (orderSer) {
                    case ORDER_SHIP_TYPE_2: //退款
                        message = "该串码已经退款流程中";
                        break;
                    case ORDER_SHIP_TYPE_3://换货
                        message = "该串码已经换货流程中";
                        break;
                    case ORDER_SHIP_TYPE_4://退货
                        message = "该串码已经退货流程中";
                        break;
                    default:
                        message = "你有待完成的申请单";
                        break;

                }
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resp.setResultMsg(message + ",请勿重复提交!");
                return resp;
            }
        }
        req.setSupplierName(order.getSupplierName());
        /**
         *  如果是退款退货，处理人是商家
         *  如果是换货，要根据商品的配置，来确定处理人是厂商还是商家
         */
        req.setHandlerId(handlerId);

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp checkActivity(OrderApplyReq req, Order order) {

        CommonResultResp resp = new CommonResultResp();
        if (OrderManagerConsts.ORDER_CAT_1.equals(order.getOrderCat())) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("预售订单不能退货退款");
            return resp;
        }

        if (StringUtils.isEmpty(req.getReturnedAccount()) ||
                StringUtils.isEmpty(req.getBankInfo()) ||
                StringUtils.isEmpty(req.getAccountHolderName())
                ) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("退货退款必须填银行账号信息！");
            return resp;
        }

        PromotionModel moreq = new PromotionModel();
        moreq.setOrderId(order.getOrderId());
        moreq.setMktActType(OrderManagerConsts.ACTIVITY_TYPE_1002);
        List<Promotion> promotion = promotionManager.selectPromotion(moreq);
        if (!CollectionUtils.isEmpty(promotion)) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("参与前置补贴的订单不能退货退款");
        }

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public OrderApply initRequestToApply(OrderApplyReq req) {
        OrderApply orderApply = afterSaleManager.getLastOrderApplyByOrderId(req.getOrderId());
        if (orderApply == null) {
            //供货商名称
            orderApply = new OrderApply();
            orderApply.setBatchId(0);
        }
        BeanUtils.copyProperties(req, orderApply);
        orderApply.setOrderApplyId(whaleCloudKeyGenerator.tableKeySeq(DBTableSequence.ORD_ORDER_APPLY.getCode()));
        orderApply.setCreateUserId(req.getUserId());
        orderApply.setApplicantId(req.getUserCode());
        orderApply.setHandlerId(req.getHandlerId());
        orderApply.setSupplierName(req.getSupplierName());
        Integer batchId = orderApply.getBatchId() == null ? 1 : orderApply.getBatchId()+1;
        orderApply.setBatchId(batchId);
        orderApply.setApplyState(OrderAllStatus.ORDER_STATUS_21.getCode());
        orderApply.setCreateTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        orderApply.setSourceFrom(req.getSourceFrom());
        return orderApply;
    }

    @Override
    public CommonResultResp builderOrderApply(OrderApplyReq req, OrderApply orderApply) {
        CommonResultResp resp = new CommonResultResp();
        afterSaleManager.insertInto(orderApply);
        orderZFlowManager.insertAfterSaleFlowList(req, orderApply.getOrderApplyId());
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    @Transactional
    public CommonResultResp handlerApply(UpdateApplyStatusRequest request, OrderApply orderApply) {
        CommonResultResp resp = new CommonResultResp();
        ActionFlowType actionFlowType = ActionFlowType.matchOpCode(request.getFlowType());
        OrderApply applyReq = new OrderApply();
        applyReq.setOrderApplyId(orderApply.getOrderApplyId());
        String message = "";
        switch (actionFlowType) {
            case ORDER_HANDLER_TKSH: //退货退款审核

                if (TypeStatus.TYPE_20.getCode().equals(request.getConfirmType())) {  //不通过
                    applyReq.setApplyState(OrderAllStatus.ORDER_STATUS_21_.getCode());
                    applyReq.setAuditDesc(request.getRemark());

                    /**
                     * 解冻
                     */
                    if (OrderServiceType.ORDER_SHIP_TYPE_4.getCode().equals(orderApply.getServiceType())) {
                        OrderItemDetailModel model = new OrderItemDetailModel();
                        model.setOrderApplyId(orderApply.getOrderApplyId());
                        List<OrderApplyDetail> resNbrs = afterSaleManager.selectOrderItem(model);
                        List<String> nrsNbrs = new ArrayList<>(resNbrs.size());
                        for (OrderApplyDetail d : resNbrs) {
                            nrsNbrs.add(d.getResNbr());
                        }
                        resp = resNbrManagerReference.resNbrDJ(orderApply.getApplicantId(), ResourceConst.STATUSCD.AVAILABLE.getCode(),
                                nrsNbrs, resNbrs.get(0).getProductId());
                        if (resp.isFailure()) {
                            return resp;
                        }
                    }
                    /**
                     * 更新我的代办
                     */
                    taskManagerReference.updateTask(orderApply.getOrderApplyId(), request.getUserId());

                } else if (TypeStatus.TYPE_10.getCode().equals(request.getConfirmType())) {//   通过

                    OrderServiceType orderServiceType = OrderServiceType.matchOpCode(orderApply.getServiceType());
                    switch (orderServiceType) {
                        case ORDER_SHIP_TYPE_2: //退款
                            message = "待退款";
                            applyReq.setApplyState(OrderAllStatus.ORDER_STATUS_28.getCode()); // 待商家退款
                            break;
                        case ORDER_SHIP_TYPE_4: //退货
                            message = "待买家退货";
                            applyReq.setApplyState(OrderAllStatus.ORDER_STATUS_22.getCode()); //  待买家退货
                            break;
                        default:
                            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                            resp.setResultMsg("serviceCode 不符合");
                            return resp;
                    }
                    /**
                     * 更新我的代办
                     */
                    taskManagerReference.updateTask(orderApply.getOrderApplyId(), request.getUserId());
                    taskManagerReference.addTaskByHandlerOne(message, orderApply.getOrderApplyId(), request.getUserId(), orderApply.getApplicantId());
                } else {
                    resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resp.setResultMsg("confirmType 不匹配");
                    return resp;
                }
                break;
            case ORDER_HANDLER_HHSH: //换货审核
                if (TypeStatus.TYPE_20.getCode().equals(request.getConfirmType())) {  //不通过
                    applyReq.setApplyState(OrderAllStatus.ORDER_STATUS_21_.getCode());
                    applyReq.setAuditDesc(request.getRemark());

                    OrderItemDetailModel model = new OrderItemDetailModel();
                    model.setOrderApplyId(orderApply.getOrderApplyId());
                    List<OrderApplyDetail> resNbrs = afterSaleManager.selectOrderItem(model);
                    List<String> nrsNbrs = new ArrayList<>(resNbrs.size());
                    for (OrderApplyDetail d : resNbrs) {
                        nrsNbrs.add(d.getResNbr());
                    }
                    resp = resNbrManagerReference.resNbrDJ(orderApply.getApplicantId(), ResourceConst.STATUSCD.AVAILABLE.getCode(),
                            nrsNbrs, resNbrs.get(0).getProductId());
                    if (resp.isFailure()) {
                        return resp;
                    }
                    /**
                     * 更新我的代办
                     */
                    taskManagerReference.updateTask(orderApply.getOrderApplyId(), request.getUserId());

                } else if (TypeStatus.TYPE_10.getCode().equals(request.getConfirmType())) {//   通过
                    applyReq.setApplyState(OrderAllStatus.ORDER_STATUS_22.getCode());
                    /**
                     * 更新我的代办
                     */
                    taskManagerReference.updateTask(orderApply.getOrderApplyId(), request.getUserId());
                    taskManagerReference.addTaskByHandlerOne("待买家退货", orderApply.getOrderApplyId(), request.getUserId(), orderApply.getApplicantId());
                } else {
                    resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resp.setResultMsg("confirmType 不匹配");
                    return resp;
                }
                break;
            case ORDER_HANDLER_TKWC://退款完成（买家确认收款）

                if (OrderServiceType.ORDER_SHIP_TYPE_4.getCode().equals(orderApply.getServiceType())) {//退货
                    applyReq.setApplyState(OrderAllStatus.ORDER_STATUS_1_.getCode()); //完成
                } else { //退款
                    applyReq.setApplyState(OrderAllStatus.ORDER_STATUS_2_.getCode()); //完成
                }

                //更新订单状态
                int submit = afterSaleManager.selectAllSubmit(orderApply.getOrderId());
                Order item = orderManager.getOrderById(orderApply.getOrderId());
                if (item != null) {
                    if (item.getGoodsNum() - (submit+orderApply.getSubmitNum()) == 0) {
                        OrderUpdateAttrModel orderUpdateAttrModel = new OrderUpdateAttrModel();
                        orderUpdateAttrModel.setOrderId(orderApply.getOrderId());
                        orderUpdateAttrModel.setStatus(applyReq.getApplyState());
                        orderManager.updateOrderAttr(orderUpdateAttrModel);
                    }
                }

                /**
                 * 更新订单项
                 */
                OrderItem orderItem = new OrderItem();
                orderItem.setItemId(orderApply.getOrderItemId());
                orderItem.setReturnNum(orderApply.getSubmitNum());
                orderManager.updateOrderItemByItemId(orderItem);
                /**
                 * 更新我的代办
                 */
                taskManagerReference.updateTask(orderApply.getOrderApplyId(), request.getUserId());
                break;
            case ORDER_HANDLER_QX: //取消
                applyReq.setApplyState(OrderAllStatus.ORDER_STATUS_10_.getCode()); //完成

                /**
                 * 解冻
                 */
                OrderItemDetailModel model = new OrderItemDetailModel();
                model.setOrderApplyId(orderApply.getOrderApplyId());
                List<OrderApplyDetail> resNbrs = afterSaleManager.selectOrderItem(model);
                List<String> nrsNbrs = new ArrayList<>(resNbrs.size());
                for (OrderApplyDetail d : resNbrs) {
                    nrsNbrs.add(d.getResNbr());
                }
                if (!CollectionUtils.isEmpty(resNbrs)) {
                    resp = resNbrManagerReference.resNbrDJ(orderApply.getApplicantId(), ResourceConst.STATUSCD.AVAILABLE.getCode(),
                            nrsNbrs, resNbrs.get(0).getProductId());
                    if (resp.isFailure()) {
                        return resp;
                    }
                }

                /**
                 * 更新我的代办
                 */
                taskManagerReference.updateTask(orderApply.getOrderApplyId(), request.getUserId());
                break;
            default:
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resp.setResultMsg("serviceType 不匹配");
                return resp;

        }

        /**
         * 更新申请单
         */
        afterSaleManager.updateApplyState(applyReq);
        OrderZFlowDTO zFlowDTO = new OrderZFlowDTO();
        zFlowDTO.setOrderId(request.getOrderApplyId());
        zFlowDTO.setHandlerId(request.getUserId());
        zFlowDTO.setFlowType(request.getFlowType());
        orderZFlowManager.updateFlowList(zFlowDTO);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp returnAmount(PayOrderRequest request) {
        CommonResultResp resp = new CommonResultResp();
        request.setFlowType(ActionFlowType.ORDER_HANDLER_SJTK.getCode());
        OrderApply orderApply = new OrderApply();
        orderApply.setOrderApplyId(request.getOrderApplyId());
        orderApply.setRefundDesc(request.getPayRemark());
        orderApply.setApplyState(OrderAllStatus.ORDER_STATUS_29.getCode()); //待买家确认收款
        orderApply.setRefundImgUrl(request.getRefundImgUrl());
        
        //如果是翼支付的订单所有的货退款
        if("1".equals(request.getPayType())) {
        	String orderId = request.getOrderId();
            List<OrderItem> list = orderManager.selectOrderItemsList(orderId);
            int num = 0;//订单数量
//            int receiveNum = 0;//收货数量
            int returnNum = 0;//退货数量
            for(int i=0;i<list.size();i++) {
            	OrderItem orderItem = list.get(i);
            	num += orderItem.getNum();
//            	receiveNum += orderItem.getReceiveNum();
            	returnNum += orderItem.getReturnNum();
            }
            
//            if(num == (receiveNum+returnNum)) {//如果订单数量=收货数量+退货数量
            if(num == returnNum) {
            	Order order = new Order();
                order.setOrderId(orderId);
                Order resOrder = orderMapper.getOrderById(order);
                String payMoney = resOrder.getOrderAmount().toString();
                String resPayMoney = payMoney.substring(0, payMoney.indexOf('.'));
                String payTransId = resOrder.getPayTransId();
                String callUrl = "ord.operbalance.preAuthConfirm";
                String reqSeq = "PRE" + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
            	PayAuthorizationService payAuthorizationService = new PayAuthorizationService();
            	Map<String, Object> resultCall = payAuthorizationService.call(callUrl, orderId, null, reqSeq, payTransId, resPayMoney);
            	boolean flag = (boolean)resultCall.get("flag");
            	int update = afterSaleManager.updateApplyState(orderApply);
                if (update < 1 || !flag) {
                    resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resp.setResultMsg("商家退款失败");
                    return resp;
                }
            }
            
        }else {
        	/**
             * 更新申请单
             */
            int update = afterSaleManager.updateApplyState(orderApply);
            if (update < 1) {
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resp.setResultMsg("商家退款失败");
                return resp;
            }
        }
        
        OrderZFlowDTO zFlowDTO = new OrderZFlowDTO();
        zFlowDTO.setOrderId(request.getOrderApplyId());
        zFlowDTO.setHandlerId(request.getUserId());
        zFlowDTO.setFlowType(request.getFlowType());
        orderZFlowManager.updateFlowList(zFlowDTO);

        /**
         * 更新我的代办
         */
        taskManagerReference.updateTask(orderApply.getOrderApplyId(), request.getUserId());
        taskManagerReference.addTaskByHandlerOne("待收款", orderApply.getOrderApplyId(), request.getUserId(), orderApply.getApplicantId());
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }
    
}
