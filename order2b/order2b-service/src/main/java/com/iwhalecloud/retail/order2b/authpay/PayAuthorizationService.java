package com.iwhalecloud.retail.order2b.authpay;

import com.iwhalecloud.retail.order2b.authpay.handler.BestpayHandler;
import com.iwhalecloud.retail.order2b.authpay.handler.TradeCertificate;
import com.iwhalecloud.retail.order2b.authpay.service.v3.BestpayServiceV3;
import com.iwhalecloud.retail.order2b.authpay.util.CertificateUtil;
import com.iwhalecloud.retail.order2b.busiservice.BPEPPayLogService;
import com.iwhalecloud.retail.order2b.consts.PayConsts;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.entity.AdvanceOrder;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.mapper.AdvanceOrderMapper;
import com.iwhalecloud.retail.order2b.mapper.OrderMapper;
import com.iwhalecloud.retail.order2b.model.SaveLogModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Created by jiyou on 2019/4/17.
 *
 * 翼支付预支付接口
 *
 */
@Slf4j
@Service
public class PayAuthorizationService {

    @Value("${pay.payAuthUrl}")
    private String payAuthUrl;

    @Value("${pay.payConfirmUrl}")
    private String payConfirmUrl;

    @Value("${pay.payCancelUrl}")
    private String payCancelUrl;

    @Value("${pay.platCode}")
    private String platCode;

    @Value("${pay.platformCode}")
    private String platformCode;

    @Value("${pay.ipStr}")
    private String ipStr;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AdvanceOrderMapper advanceOrderMapper;

    @Autowired
    private BPEPPayLogService bpepPayLogService;

    /**
     * 授权申请
     */
    public boolean authorizationApplication(String orderId, String operationType) {

        Order order = new Order();
        order.setOrderId(orderId);
        Order resOrder = orderMapper.getOrderById(order);
        if("1".equals(resOrder.getOrderCat())){ //预售
            AdvanceOrder advanceOrder = new AdvanceOrder();
            advanceOrder.setOrderId(orderId);
            AdvanceOrder resAdvanceOrder = advanceOrderMapper.selectAdvanceOrderByOrderId(advanceOrder);

            boolean flag = true;
            if("0".equals(resAdvanceOrder.getAdvancePayStatus())){ // 支付定金
                String payAdvanceMoney = resAdvanceOrder.getAdvanceAmount().toString();
                String payAdvMoney = payAdvanceMoney.substring(0, payAdvanceMoney.indexOf('.'));
                String reqSeq = "PRE" + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
                boolean advFlag = call(payAuthUrl, orderId, operationType, reqSeq, null, payAdvMoney);
                if(advFlag){
                    // 保存订单交易流水
                    advanceOrderMapper.updateAdvanceTransId(orderId, reqSeq);
                }
                flag = flag & advFlag;
            }
            if("1".equals(resAdvanceOrder.getAdvancePayStatus()) && "0".equals(resAdvanceOrder.getRestPayStatus())){   // 支付尾款
                String payRestMoney = resAdvanceOrder.getRestPayMoney().toString();
                String payResMoney = payRestMoney.substring(0, payRestMoney.indexOf('.'));
                String reqSeq = "PRE" + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
                boolean resFlag = call(payAuthUrl, orderId, operationType, reqSeq, null, payResMoney);
                if(resFlag){
                    // 保存订单交易流水
                    advanceOrderMapper.updateRestTransId(orderId, reqSeq);
                }
                flag = flag & resFlag;
            }
            return flag;
        }else { // 普通
            String payMoney = resOrder.getOrderAmount().toString();
            String resPayMoney = payMoney.substring(0, payMoney.indexOf('.'));
            String reqSeq = "PRE" + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
            boolean advFlag = call(payAuthUrl, orderId, operationType, reqSeq, null, resPayMoney);
            if(advFlag){
                // 保存订单交易流水
                orderMapper.updatePayTransId(orderId, reqSeq);
            }
            return advFlag;
        }

    }

    /**
     * 授权确认
     */
    public boolean authorizationConfirmation(String orderId){
        return this.AuthorizationConfAndCancellation(orderId, payConfirmUrl);
    }

    /**
     * 授权取消
     */
    public boolean AuthorizationCancellation(String orderId) {
        return this.AuthorizationConfAndCancellation(orderId, payCancelUrl);
    }

    public boolean AuthorizationConfAndCancellation(String orderId, String callUrl) {

        Order order = new Order();
        order.setOrderId(orderId);
        Order resOrder = orderMapper.getOrderById(order);

        if("1".equals(resOrder.getOrderCat())){ //预售
            AdvanceOrder advanceOrder = new AdvanceOrder();
            advanceOrder.setOrderId(orderId);
            AdvanceOrder resAdvanceOrder = advanceOrderMapper.selectAdvanceOrderByOrderId(advanceOrder);

            // 预售确认标识
            boolean flag = true;

            String advanceTransId = resAdvanceOrder.getAdvanceTransId();
            if("1".equals(resAdvanceOrder.getAdvancePayStatus()) && advanceTransId != null && !"".equals(advanceTransId)){ // 已授权定金,确认／取消定金
                String payAdvanceMoney = resAdvanceOrder.getAdvanceAmount().toString();
                String payAdvMoney = payAdvanceMoney.substring(0, payAdvanceMoney.indexOf('.'));
                String reqSeq = "PRE" + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
                boolean advFlag = call(callUrl, orderId, null, reqSeq, resAdvanceOrder.getAdvanceTransId(), payAdvMoney);
                flag = advFlag & flag;
            }

            String restTransId = resAdvanceOrder.getRestTransId();
            if("1".equals(resAdvanceOrder.getRestPayStatus()) && restTransId != null && !"".equals(restTransId)){   // 已授权尾款,确认／取消尾款
                String payRestMoney = resAdvanceOrder.getRestPayMoney().toString();
                String payResMoney = payRestMoney.substring(0, payRestMoney.indexOf('.'));
                String reqSeq = "PRE" + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
                boolean resFlag = call(callUrl, orderId, null, reqSeq, resAdvanceOrder.getRestTransId(), payResMoney);
                flag = resFlag & flag;
            }
            return flag;
        }else { // 普通
            String payMoney = resOrder.getOrderAmount().toString();
            String resPayMoney = payMoney.substring(0, payMoney.indexOf('.'));
            String payTransId = resOrder.getPayTransId();
            if(payTransId != null && !"".equals(payTransId)){
                String reqSeq = "PRE" + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
                return call(callUrl, orderId, null, reqSeq, payTransId, resPayMoney);
            }
        }

        return false;

    }

    private boolean call(String callUrl, String orderId, String operationType, String reqSeq, String originalTransSeq, String payMoney){

        TradeCertificate certificate = CertificateUtil.getTradeCertificate(CertificateUtil.KEYSTORETYPE_JKS, true);
        BestpayHandler bestpayHandler = new BestpayHandler(certificate);
        BestpayServiceV3 bestpayService = new BestpayServiceV3(bestpayHandler);

        //查找到当前用户的翼支付账户
        String loginCode = orderMapper.findPayAccountByOrderId(orderId); //account

        // 通过订单找到供应商订单账号，金额
        Map<String, Object> result = orderMapper.findReptAccountAndMoneyByOrderId(orderId);

        PreAuthorizationApplyDTO preAuthorizationApplyRequest = new PreAuthorizationApplyDTO();
        preAuthorizationApplyRequest.setReqSeq(reqSeq);
        preAuthorizationApplyRequest.setLoginCode(loginCode);// 登录号
        preAuthorizationApplyRequest.setPlatCode(platCode);
        preAuthorizationApplyRequest.setRequestTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        preAuthorizationApplyRequest.setReqIp(ipStr);
        preAuthorizationApplyRequest.setTrsSummary("summary");
        preAuthorizationApplyRequest.setTrsMemo("memo");
        preAuthorizationApplyRequest.setExternalId("EXT_ORDER_ID_" + orderId); // 订单号
        preAuthorizationApplyRequest.setCurrencyCode("RMB");
        preAuthorizationApplyRequest.setTransactionAmount(payMoney); // 交易金额
        preAuthorizationApplyRequest.setPayeeLoginCode(result.get("account").toString()); //收款方登录号
        preAuthorizationApplyRequest.setOriginalTransSeq(originalTransSeq);

        boolean b = false;
        try {
            b = bestpayService.invoke(callUrl, preAuthorizationApplyRequest, platformCode, certificate.getIv());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        SaveLogModel saveLogModel = new SaveLogModel();
        saveLogModel.setPayId(reqSeq);
        saveLogModel.setOrderId(orderId);
        saveLogModel.setOrderAmount(payMoney);
        saveLogModel.setPayStatus(PayConsts.PAY_STATUS_0);
        saveLogModel.setRequestType(PayConsts.REQUEST_TYPE_1004);
        saveLogModel.setOperationType(operationType);
        bpepPayLogService.saveLog(saveLogModel);

        return b;
    }

}