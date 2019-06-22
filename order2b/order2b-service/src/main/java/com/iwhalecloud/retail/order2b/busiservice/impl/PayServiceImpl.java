package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.authpay.PayAuthorizationService;
import com.iwhalecloud.retail.order2b.busiservice.BPEPPayLogService;
import com.iwhalecloud.retail.order2b.busiservice.PayService;
import com.iwhalecloud.retail.order2b.busiservice.UpdateOrderFlowService;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.consts.order.OrderPayType;
import com.iwhalecloud.retail.order2b.consts.order.TypeStatus;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PayOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.OffLinePayReq;
import com.iwhalecloud.retail.order2b.entity.AdvanceOrder;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.manager.AdvanceOrderManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.model.OrderUpdateAttrModel;
import com.iwhalecloud.retail.order2b.reference.TaskManagerReference;
import com.iwhalecloud.retail.order2b.util.CurrencyUtil;
import com.iwhalecloud.retail.system.common.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
public class PayServiceImpl implements PayService {


    @Autowired
    private UpdateOrderFlowService updateOrderFlowService;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private AdvanceOrderManager advanceOrderManager;

    @Autowired
    private TaskManagerReference taskManagerReference;

    @Autowired
    private BPEPPayLogService bpepPayLogService;
    
    @Autowired
    private PayAuthorizationService payAuthorizationService;

    //支付方式的入口
    @Override
    public ResultVO pay(PayOrderRequest request) {
        ResultVO resultVO = new ResultVO();
        Order order = orderManager.getOrderById(request.getOrderId());
        if (null == order) {
            resultVO.setResultMsg("支付,未查询到订单");
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resultVO;
        }
        CommonResultResp resp = updateOrderFlowService.checkFlowType(request, order);
        if (resp.isFailure()) {
            resultVO.setResultMsg(resp.getResultMsg());
            resultVO.setResultCode(resp.getResultCode());
            return resultVO;
        }
        ActionFlowType actionFlowType = ActionFlowType.matchOpCode(request.getFlowType());
        switch (actionFlowType) {
            case ORDER_HANDLER_ZF: //订单支付
                resultVO = orderPay(request, order);
                break;
            case ORDER_HANDLER_DJZF://定金支付
                resultVO = advanceOrderPay(request, order);
                break;
            case ORDER_HANDLER_WKZF://尾款支付
                resultVO=restPay(request,order);
                break;
            default:
                resultVO.setResultMsg("flowType 不匹配");
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                return resultVO;
        }
        return resultVO;
    }

    @Override
    public ResultVO orderPay(PayOrderRequest request, Order order) {
        ResultVO resultVO = new ResultVO();
        CommonResultResp resp = new CommonResultResp();
        OrderPayType orderPayType = OrderPayType.matchOpCode(request.getPayType());

        if (StringUtils.isEmpty(request.getPaymoney())) {
            resultVO.setResultMsg("订单金额不能为空");
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resultVO;
        }

        if (order.getPayMoney() == null) {
            order.setPayMoney(0.0);
        }
        switch (orderPayType) {
            case PAY_TYPE_1: //翼支付（翼支付的预授权支付 pay_type=1,线上支付）
            	String loginCode = payAuthorizationService.findPayAccountByOrderId(order.getOrderId()); //account
                if(loginCode == null){
                    return ResultVO.error("买家翼支付账号没有配置。");
                }
                // 通过订单找到供应商订单账号，金额
                Map<String, Object> resultMap = payAuthorizationService.findReptAccountAndMoneyByOrderId(order.getOrderId());
                if(resultMap.get("account").toString() == null){
                    return ResultVO.error("商家翼支付账号没有配置。");
                }
                OffLinePayReq req = new OffLinePayReq();
                req.setOrderId(order.getOrderId());
                req.setOrderAmount(String.valueOf(order.getOrderAmount()));
                req.setOperationType(request.getFlowType());
                resultVO = bpepPayLogService.openToBookingPay(req);
                break;
            case PAY_TYPE_4: //线下支付
                OffLinePayReq offLinePayReq = new OffLinePayReq();
                offLinePayReq.setOrderId(order.getOrderId());
                offLinePayReq.setOrderAmount(String.valueOf(order.getOrderAmount()));
                offLinePayReq.setPayData(request.getPayRemark());
                offLinePayReq.setPayDataMd(request.getRefundImgUrl());
                offLinePayReq.setRecAccount(request.getRecAccount());
                offLinePayReq.setOperationType(request.getFlowType());
                offLinePayReq.setRecAccountName(request.getRecAccountName());
                offLinePayReq.setRecBankId(request.getRecBankId());
                int resultVO1 = bpepPayLogService.offLinePay(offLinePayReq);
                break;
            case NULL:
                resultVO.setResultMsg("支付类型错误");
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                return resultVO;

        }

        double payTotalMoney = CurrencyUtil.add(order.getPayMoney(), request.getPaymoney());
        if (payTotalMoney != order.getOrderAmount()) {
            request.setPayStatus(TypeStatus.TYPE_11.getCode());
        } else {
            request.setPayStatus(TypeStatus.TYPE_01.getCode());
        }
        request.setPaymoney(payTotalMoney);
        resp = updateOrderFlowService.pay(request);
        if (resp.isFailure()) {
            resultVO.setResultMsg(resp.getResultMsg());
            resultVO.setResultCode(resp.getResultCode());
            return resultVO;
        }
        taskManagerReference.updateTask(order.getOrderId(), request.getUserId());
        taskManagerReference.addTaskByHandleList("待发货",order.getOrderId(), request.getUserId(), order.getMerchantId());
        resultVO.setResultMsg(resp.getResultMsg());
        resultVO.setResultCode(resp.getResultCode());
        return resultVO;
    }

    @Override
    public ResultVO advanceOrderPay(PayOrderRequest request, Order order) {
        ResultVO resultVO = new ResultVO();
        CommonResultResp resp = new CommonResultResp();
        // 定金金额
        Double payMoney = request.getPaymoney();
        if (StringUtils.isEmpty(payMoney)) {
            return ResultVO.error("定金金额金额不能为空");
        }

        AdvanceOrder advanceOrder = advanceOrderManager.getAdvanceOrderByOrderId(order.getOrderId());
        if (Objects.isNull(advanceOrder)) {
            return ResultVO.error("未找到预售订单信息");
        }
        // 定金支付开始时间
        String advancePayBegin = advanceOrder.getAdvancePayBegin();
        // 定金支付结束时间
        String advancePayEnd = advanceOrder.getAdvancePayEnd();
        Date beginDate = DateUtils.strToUtilDate(advancePayBegin);
        Date endDate = DateUtils.strToUtilDate(advancePayEnd);
        long currentTimeMillis = System.currentTimeMillis();
        if (beginDate.getTime() > currentTimeMillis || endDate.getTime() < currentTimeMillis) {
            return ResultVO.error("不在支付定金时间内");
        }
        if (advanceOrder.getAdvancePayMoney() == null) {
            advanceOrder.setAdvancePayMoney("0.0");
        }
        // 支付方式
        OrderPayType orderPayType = OrderPayType.matchOpCode(request.getPayType());
        switch (orderPayType) {
            //翼支付
            case PAY_TYPE_1:
            	OffLinePayReq req = new OffLinePayReq();
                req.setOrderId(order.getOrderId());
                req.setOrderAmount(String.valueOf(order.getOrderAmount()));
                req.setOperationType(request.getFlowType());
                resultVO = bpepPayLogService.openToBookingPay(req);
                break;
            //线下支付
            case PAY_TYPE_4:
                request.setPayStatus(TypeStatus.TYPE_01.getCode());
                OffLinePayReq offLinePayReq = new OffLinePayReq();
                offLinePayReq.setOrderId(order.getOrderId());
                offLinePayReq.setOrderAmount(String.valueOf(request.getPaymoney()));
                offLinePayReq.setPayData(request.getPayRemark());
                offLinePayReq.setPayDataMd(request.getRefundImgUrl());
                offLinePayReq.setOperationType(request.getFlowType());
                offLinePayReq.setRecAccount(request.getRecAccount());
                offLinePayReq.setRecAccountName(request.getRecAccountName());
                offLinePayReq.setRecBankId(request.getRecBankId());
                int resultVO1 = bpepPayLogService.offLinePay(offLinePayReq);
                break;
            case NULL:
                return ResultVO.error("支付类型错误");
            default:
                break;
        }
        double payTotalMoney = CurrencyUtil.add(Double.valueOf(advanceOrder.getAdvancePayMoney()), payMoney);
        if (payTotalMoney >= advanceOrder.getAdvanceAmount()) {
            request.setPayStatus(TypeStatus.TYPE_01.getCode());
        } else {
            request.setPayStatus(TypeStatus.TYPE_11.getCode());
        }
        request.setPaymoney(payTotalMoney);
        resp = updateOrderFlowService.advancePay(request);
        if (resp.isFailure()) {
            resultVO.setResultMsg(resp.getResultMsg());
            resultVO.setResultCode(resp.getResultCode());
            return resultVO;
        }
        /**
         * 一次性支付
         */
        if(order.getOrderAmount()<=(request.getPaymoney())){
            // 更新订单状态
            OrderUpdateAttrModel updateAttrModel = new OrderUpdateAttrModel();
            updateAttrModel.setOrderId(request.getOrderId());
            updateAttrModel.setPayType(request.getPayType());
            updateAttrModel.setPayCode(request.getPayCode());
            updateAttrModel.setPayTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
            updateAttrModel.setPayStatus(request.getPayStatus());
            updateAttrModel.setStatus(OrderAllStatus.ORDER_STATUS_4.getCode());
            orderManager.updateOrderAttr(updateAttrModel);
            taskManagerReference.updateTask(order.getOrderId(), request.getUserId());
            taskManagerReference.addTaskByHandleList("待发货",order.getOrderId(), request.getUserId(), order.getMerchantId());
        }else{
            taskManagerReference.updateTask(order.getOrderId(), request.getUserId());
            taskManagerReference.addTaskByHandleList("待付尾款",order.getOrderId(), request.getUserId(), order.getUserId());
        }
        resultVO.setResultMsg(resp.getResultMsg());
        resultVO.setResultCode(resp.getResultCode());
        return resultVO;
    }

    @Override
    public ResultVO restPay(PayOrderRequest request, Order order) {
        ResultVO resultVO = new ResultVO();

        if (StringUtils.isEmpty( request.getPaymoney())) {
            return ResultVO.error("尾款金额金额不能为空");
        }

        AdvanceOrder advanceOrder = advanceOrderManager.getAdvanceOrderByOrderId(order.getOrderId());
        if (Objects.isNull(advanceOrder)) {
            return ResultVO.error("未找到预售订单信息");
        }
        // 定金支付开始时间
        String restPayBegin = advanceOrder.getRestPayBegin();
        // 定金支付结束时间
        String restPayEnd = advanceOrder.getRestPayEnd();
        Date beginDate = DateUtils.strToUtilDate(restPayBegin);
        Date endDate = DateUtils.strToUtilDate(restPayEnd);
        long currentTimeMillis = System.currentTimeMillis();
        if (beginDate.getTime() > currentTimeMillis || endDate.getTime() < currentTimeMillis) {
            return ResultVO.error("不在支付尾款时间内");
        }
        if (advanceOrder.getRestPayMoney() == null) {
            advanceOrder.setRestPayMoney("0.0");
        }
        // 支付方式
        OrderPayType orderPayType = OrderPayType.matchOpCode(request.getPayType());
        switch (orderPayType) {
            //翼支付
            case PAY_TYPE_1:
            	OffLinePayReq req = new OffLinePayReq();
                req.setOrderId(order.getOrderId());
                req.setOrderAmount(String.valueOf(order.getOrderAmount()));
                req.setOperationType(request.getFlowType());
                resultVO = bpepPayLogService.openToBookingPay(req);
                break;
            //线下支付
            case PAY_TYPE_4:
                request.setPayStatus(TypeStatus.TYPE_01.getCode());
                OffLinePayReq offLinePayReq = new OffLinePayReq();
                offLinePayReq.setOrderId(order.getOrderId());
                offLinePayReq.setOrderAmount(String.valueOf(request.getPaymoney()));
                offLinePayReq.setPayData(request.getPayRemark());
                offLinePayReq.setOperationType(request.getFlowType());
                offLinePayReq.setPayDataMd(request.getRefundImgUrl());
                offLinePayReq.setRecAccount(request.getRecAccount());
                offLinePayReq.setRecAccountName(request.getRecAccountName());
                offLinePayReq.setRecBankId(request.getRecBankId());
                int resultVO1 = bpepPayLogService.offLinePay(offLinePayReq);
                break;
            case NULL:
                return ResultVO.error("支付类型错误");
            default:
                break;
        }
        double payTotalMoney = CurrencyUtil.add(Double.valueOf(advanceOrder.getRestPayMoney()),request.getPaymoney());
        if (payTotalMoney >= advanceOrder.getRestAmount()) {
            request.setPayStatus(TypeStatus.TYPE_01.getCode());
        } else {
            request.setPayStatus(TypeStatus.TYPE_11.getCode());
        }
        request.setPaymoney(payTotalMoney);
        CommonResultResp resp = updateOrderFlowService.restPay(request);
        if (resp.isFailure()) {
            resultVO.setResultMsg(resp.getResultMsg());
            resultVO.setResultCode(resp.getResultCode());
            return resultVO;
        }
        taskManagerReference.updateTask(order.getOrderId(), request.getUserId());
        taskManagerReference.addTaskByHandleList("待发货",order.getOrderId(), request.getUserId(), order.getMerchantId());
        resultVO.setResultMsg(resp.getResultMsg());
        resultVO.setResultCode(resp.getResultCode());
        return resultVO;
    }
}
