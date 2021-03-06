package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.authpay.PayAuthorizationService;
import com.iwhalecloud.retail.order2b.busiservice.BPEPPayLogService;
import com.iwhalecloud.retail.order2b.busiservice.PayService;
import com.iwhalecloud.retail.order2b.busiservice.UpdateOrderFlowService;
import com.iwhalecloud.retail.order2b.consts.PayConsts;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.consts.order.OrderPayType;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.response.OffLinePayResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderPayInfoResp;
import com.iwhalecloud.retail.order2b.dto.response.ToPayResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.PayOrderRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateOrderStatusRequest;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.*;
import com.iwhalecloud.retail.order2b.entity.AdvanceOrder;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.manager.AdvanceOrderManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.manager.OrderZFlowManager;
import com.iwhalecloud.retail.order2b.model.SaveLogModel;
import com.iwhalecloud.retail.order2b.reference.BestPayManagerReference;
import com.iwhalecloud.retail.order2b.reference.MemberInfoReference;
import com.iwhalecloud.retail.order2b.reference.TaskManagerReference;
import com.iwhalecloud.retail.order2b.service.BestPayEnterprisePaymentService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountListReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BestPayEnterprisePaymentOpenServiceImpl implements BestPayEnterprisePaymentService {

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private MemberInfoReference memberInfoReference;

    @Autowired
    private BPEPPayLogService bpepPayLogService;

    @Autowired
    private PayService payService;

    @Autowired
    private AdvanceOrderManager advanceOrderManager;

    @Autowired
    private UpdateOrderFlowService updateOrderFlowService;

    @Autowired
    private PayAuthorizationService payAuthorizationService;

    @Autowired
    private TaskManagerReference taskManagerReference;
    
    @Autowired
    private OrderZFlowManager orderZFlowManager;

    @Autowired
    private BestPayManagerReference bestPayManagerReference;
    
    @Value("${pay.type}")
    private String payType;

    @Override
    public ResultVO<ToPayResp> toPay(ToPayReq req) {

        log.info("BestPayEnterprisePaymentImpl.toPay req={}", JSON.toJSONString(req));
        Order order = orderManager.getOrderById(req.getOrderId());
        if (null == order) {
            log.info("BestPayEnterprisePaymentImpl.toPay orderManager.getOrderById resp is null. req={}", JSON.toJSONString(req));
            return ResultVO.error("订单不存在");
        } else {
            log.info("BestPayEnterprisePaymentImpl.toPay orderManager.getOrderById req={} resp={}", JSON.toJSONString(req), JSON.toJSONString(order));
        }

        /**
         * 校验订单状态（线上）
         */
        UpdateOrderStatusRequest statusRequest = new UpdateOrderStatusRequest();
        BeanUtils.copyProperties(req, statusRequest);
        statusRequest.setFlowType(req.getOperationType());
        CommonResultResp checkResp = updateOrderFlowService.checkFlowType(statusRequest, order);
        if (checkResp.isFailure()) {
            return ResultVO.error(checkResp.getResultMsg());
        }
        String amount = "0";
        if (req.getOperationType().equals(ActionFlowType.ORDER_HANDLER_ZF.getCode())) {
            amount = req.getOrderAmount();
        } else {
            AdvanceOrder advanceOrder = advanceOrderManager.getAdvanceOrderByOrderId(order.getOrderId());
            if (req.getOperationType().equals(ActionFlowType.ORDER_HANDLER_DJZF.getCode())) {
                amount = String.valueOf(advanceOrder.getAdvanceAmount());
            } else if (req.getOperationType().equals(ActionFlowType.ORDER_HANDLER_WKZF.getCode())) {
                amount = String.valueOf(advanceOrder.getRestAmount());
            } else {
                return ResultVO.error("订单支付类型有误");
            }
        }
        MerchantAccountListReq merchantAccountListReq = new MerchantAccountListReq();
        merchantAccountListReq.setMerchantId(order.getMerchantId());
        merchantAccountListReq.setAccountType(PartnerConst.MerchantAccountTypeEnum.BEST_PAY.getType());
        ResultVO<List<MerchantAccountDTO>> merchantAccountList = memberInfoReference.listMerchantAccount(merchantAccountListReq);
        log.info("BestPayEnterprisePaymentImpl.toPay merchantAccountService.listMerchantAccount req={} resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantAccountList.getResultData().size()));
        if (!merchantAccountList.isSuccess() || CollectionUtils.isEmpty(merchantAccountList.getResultData())) {
            return ResultVO.error("账号没有配置");
        }
        String orgLoginCode = merchantAccountList.getResultData().get(0).getAccount();
        String operationType = req.getOperationType();

        // 预授权支付
        if ("pay_auth".equals(payType)) {
            //查找到当前用户的翼支付账户
            String loginCode = payAuthorizationService.findPayAccountByOrderId(req.getOrderId()); //account
            if(StringUtils.isBlank(loginCode)){
                return ResultVO.error("买家翼支付账号没有配置。");
            }
            // 通过订单找到供应商订单账号，金额
            Map<String, Object> resultMap = payAuthorizationService.findReptAccountAndMoneyByOrderId(req.getOrderId());
            if(StringUtils.isBlank(resultMap.get("account").toString())){
                return ResultVO.error("商家翼支付账号没有配置。");
            }
            //----lws
            Map<String, Object> resultCall = payAuthorizationService.authorizationApplication(req.getOrderId(), operationType);
            
            ResultVO resultVO = new ResultVO();
            OffLinePayReq payReq = new OffLinePayReq();
        	payReq.setOrderId(order.getOrderId());
        	payReq.setOrderAmount(String.valueOf(order.getOrderAmount()));
        	payReq.setOperationType(req.getOperationType());
            resultVO = bpepPayLogService.openToBookingPay(payReq);
            
          //更新订单状态
            UpdateOrdOrderReq updateOrdOrderReq = new UpdateOrdOrderReq();
            updateOrdOrderReq.setOrderId(order.getOrderId());
            updateOrdOrderReq.setPayTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
            updateOrdOrderReq.setPayType("1");
            updateOrdOrderReq.setPaymentName("线上支付");
            updateOrdOrderReq.setPaymentType("1");
            updateOrdOrderReq.setPayStatus("1");
            updateOrdOrderReq.setStatus("4");
            bpepPayLogService.UpdateOrdOrderStatus(updateOrdOrderReq);
            
            taskManagerReference.updateTask(order.getOrderId(), order.getUserId());
            taskManagerReference.addTaskByHandleList("待发货",order.getOrderId(), order.getUserId(), order.getMerchantId());

            //更新流程
//            OrderZFlowDTO zFlowDTO = new OrderZFlowDTO();
//            zFlowDTO.setOrderId(order.getOrderId());
//            zFlowDTO.setHandlerId(order.getUserId());
//            zFlowDTO.setFlowType(req.getOperationType());
//            orderZFlowManager.updateFlowList(zFlowDTO);
            
            
            if((Boolean) resultCall.get("flag")){
                ToPayResp tpr = new ToPayResp();
                tpr.setOrderId(req.getOrderId());
                return ResultVO.success(tpr);
            }else{
                return ResultVO.error(resultCall.get("flag").toString());
            }
        } else {
            ToPayResp resp = bpepPayLogService.handlePayData(order.getOrderId(), amount, orgLoginCode, operationType, req.getNet());
            return ResultVO.success(resp);
        }

    }

    @Override
    public ResultVO asynNotify(AsynNotifyReq req) {
        log.info("BestPayEnterprisePaymentOpenServiceImpl.asynNotify req={}", JSON.toJSONString(req));
        String orderStatus = req.getORDERSTATUS();
        // 支付成功
        OrderPayInfoResp orderPayInfoResp = this.bpepPayLogService.qryOrderPayInfoById(req.getORDERID());
        if (null == orderPayInfoResp) {
            return ResultVO.error("通知异常:没有支付订单");
        }
        if ("1".equals(orderStatus)) {
            String orderId = orderPayInfoResp.getOrderId();
            String payId = req.getORDERID();
            // 已通知过，幂等操作; 否则先修改支付状态
            if (PayConsts.PAY_STATUS_2.equals(orderPayInfoResp.getPayStatus())) {
                return ResultVO.success();
            } else {
                SaveLogModel saveLogModel = new SaveLogModel();
                saveLogModel.setPayId(payId);
                saveLogModel.setOrderId(orderId);
                saveLogModel.setOrderAmount(req.getORDERAMOUNT());
                saveLogModel.setPayStatus(PayConsts.PAY_STATUS_2);
                saveLogModel.setOperationType(orderPayInfoResp.getOperationType());
                int i = bpepPayLogService.updateLog(saveLogModel);
            }
            // 修改订单相关状态
            PayOrderRequest payOrderRequest = new PayOrderRequest();
            payOrderRequest.setOrderId(orderId);
            payOrderRequest.setFlowType(orderPayInfoResp.getOperationType());
            payOrderRequest.setPaymoney(Double.parseDouble(req.getORDERAMOUNT())*100);
            payOrderRequest.setPayType(OrderPayType.PAY_TYPE_1.getCode());
            payService.pay(payOrderRequest);
        } else if ("0".equals(orderStatus)) {
            // 已通知过，幂等操作
            if (PayConsts.PAY_STATUS_4.equals(orderPayInfoResp.getPayStatus())) {
                return ResultVO.success();
            } else {
                String orderId = orderPayInfoResp.getOrderId();
                String payId = req.getORDERID();
                SaveLogModel saveLogModel = new SaveLogModel();
                saveLogModel.setPayId(payId);
                saveLogModel.setOrderId(orderId);
                saveLogModel.setOrderAmount(req.getORDERAMOUNT());
                saveLogModel.setPayStatus(PayConsts.PAY_STATUS_4);
                saveLogModel.setOperationType(orderPayInfoResp.getOperationType());
                int i = bpepPayLogService.updateLog(saveLogModel);
            }
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO<OrderPayInfoResp> qryOrderPayInfo(OrderPayInfoReq req) {
        log.info("BestPayEnterprisePaymentOpenServiceImpl.qryOrderPayInfo req={}", JSON.toJSONString(req));
        return ResultVO.success(this.bpepPayLogService.qryOrderPayInfo(req));
    }

    @Override
    public ResultVO<OffLinePayResp> offLinePay(OffLinePayReq req) {
        log.info("BestPayEnterprisePaymentOpenServiceImpl.offLinePay req={}", JSON.toJSONString(req));
        int i = bpepPayLogService.offLinePay(req);
        return ResultVO.success();
    }

}
