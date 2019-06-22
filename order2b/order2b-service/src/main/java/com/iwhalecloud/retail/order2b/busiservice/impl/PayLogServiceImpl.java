package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.authpay.PayAuthorizationService;
import com.iwhalecloud.retail.order2b.busiservice.BPEPPayLogService;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.PayConsts;
import com.iwhalecloud.retail.order2b.dto.model.pay.PayLogDTO;
import com.iwhalecloud.retail.order2b.dto.model.pay.PayOperationLogDTO;
import com.iwhalecloud.retail.order2b.dto.response.OrderPayInfoResp;
import com.iwhalecloud.retail.order2b.dto.response.ToPayResp;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.*;
import com.iwhalecloud.retail.order2b.manager.PayLogManager;
import com.iwhalecloud.retail.order2b.manager.PayOperationLogManager;
import com.iwhalecloud.retail.order2b.model.BestpayConfigModel;
import com.iwhalecloud.retail.order2b.model.SaveLogModel;
import com.iwhalecloud.retail.order2b.reference.BestPayManagerReference;
import com.iwhalecloud.retail.order2b.util.AESUtil;
import com.iwhalecloud.retail.order2b.util.Base64Utils;
import com.iwhalecloud.retail.order2b.util.RsaCipher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class PayLogServiceImpl implements BPEPPayLogService {

    @Autowired
    private PayLogManager payLogManager;

    @Autowired
    private BestpayConfigModel bestpayConfig;

    @Autowired
    private PayOperationLogManager payOperationLogManager;

    @Autowired
    private BestPayManagerReference bestPayManagerReference;

    @Override
    public ToPayResp handlePayData(String orderId, String orderAmount, String orgLoginCode,String operationType) {
        ToPayResp toPayResp = new ToPayResp();
        String payId = IdWorker.getIdStr();
        orderAmount = d2l(orderAmount);
        ToBestPayReq toBestPayReq = new ToBestPayReq();
        toBestPayReq.setOrderId(payId);
        toBestPayReq.setOrderAmount(orderAmount);
        toBestPayReq.setOperationType(operationType);
        toBestPayReq.setOrgLoginCode(orgLoginCode);
        toPayResp = bestPayManagerReference.handlePayData(toBestPayReq);
        log.info("BestPayEnterprisePaymentOpenServiceImpl.toPay bestPayManagerReference.handlePayData req={} resp={}", JSON.toJSONString(toBestPayReq), JSON.toJSONString(toPayResp));
        SaveLogModel saveLogModel = new SaveLogModel();
        saveLogModel.setPayId(payId);
        saveLogModel.setOrderId(orderId);
        saveLogModel.setOrderAmount(orderAmount);
        saveLogModel.setPayStatus(PayConsts.PAY_STATUS_0);
        saveLogModel.setRequestType(PayConsts.REQUEST_TYPE_1004);
        saveLogModel.setOperationType(operationType);
        int i = saveLog(saveLogModel);
        return toPayResp;
    }

    @Override
    public int offLinePay(OffLinePayReq req) {
        log.info("BestPayEnterprisePaymentOpenServiceImpl.offLinePay req={}", JSON.toJSONString(req));
        Double orderAmout = (Double.parseDouble(req.getOrderAmount()));
        SaveLogModel saveLogModel = new SaveLogModel();
        saveLogModel.setPayId(IdWorker.getIdStr());
        saveLogModel.setOrderId(req.getOrderId());
        saveLogModel.setOrderAmount(String.valueOf(orderAmout.longValue()));
        saveLogModel.setPayStatus(req.getPayStatus());
        saveLogModel.setRequestType(PayConsts.REQUEST_TYPE_1001);
        saveLogModel.setPayData(req.getPayData());
        saveLogModel.setPayDataMd(req.getPayDataMd());
        saveLogModel.setRecBankId(req.getRecBankId());
        saveLogModel.setRecAccount(req.getRecAccount());
        saveLogModel.setOperationType(req.getOperationType());
        saveLogModel.setRecAccountName(req.getRecAccountName());
        return saveLog(saveLogModel);
    }
    
    @Override
    public void UpdateOrdOrderStatus(UpdateOrdOrderReq req){
    	this.payOperationLogManager.UpdateOrdOrderStatus(req);
    }
    
 // TODO:1、预授权支付 
    @Override
    public ResultVO openToBookingPay(OffLinePayReq req) {
        ResultVO resultVO = new ResultVO();
        log.info("BestPayEnterprisePaymentOpenServiceImpl.openToBookingPay req={}", JSON.toJSONString(req));
        Map<String, Object> resultMap = payAuthorizationService.authorizationApplication(req.getOrderId(), req.getOperationType());
        if(!(Boolean) resultMap.get("flag")){
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg((String)resultMap.get("msg"));
            return resultVO;
        }
        Double orderAmout = (Double.parseDouble(req.getOrderAmount()));
        SaveLogModel saveLogModel = new SaveLogModel();
        saveLogModel.setPayId(IdWorker.getIdStr());
        saveLogModel.setOrderId(req.getOrderId());
        saveLogModel.setOrderAmount(String.valueOf(orderAmout.longValue()));
        saveLogModel.setPayStatus(req.getPayStatus());
        saveLogModel.setRequestType(PayConsts.REQUEST_TYPE_1006);
        saveLogModel.setPayData(req.getPayData());
        saveLogModel.setPayDataMd(req.getPayDataMd());
        saveLogModel.setRecBankId(req.getRecBankId());
        saveLogModel.setRecAccount(req.getRecAccount());
        saveLogModel.setOperationType(req.getOperationType());
        saveLogModel.setRecAccountName(req.getRecAccountName());
        saveYZFLog(saveLogModel);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultMsg("支付成功");
        return resultVO;
    }
    
    @Override
    public boolean checkNotifyData(AsynNotifyReq req) {
        try {
            StringBuilder signData = new StringBuilder();
            signData.append("ORGLOGINCODE=").append(checkIsNull(req.getORGLOGINCODE()));
            signData.append("&PLATCODE=").append(checkIsNull(req.getPLATCODE()));
            signData.append("&CUSTCODE=").append(checkIsNull(req.getCUSTCODE()));
            signData.append("&ORDERID=").append(checkIsNull(req.getORDERID()));
            signData.append("&TRSSEQ=").append(checkIsNull(req.getTRSSEQ()));
            signData.append("&ORDERAMOUNT=").append(checkIsNull(req.getORDERAMOUNT()));
            signData.append("&FEE=").append(checkIsNull(req.getFEE()));
            signData.append("&TRANSDATE=").append(checkIsNull(req.getTRANSDATE()));
            signData.append("&ORDERSTATUS=").append(checkIsNull(req.getORDERSTATUS()));
            signData.append("&BANKCODE=").append(checkIsNull(req.getBANKCODE()));
            signData.append("&PERENTFLAG=").append(checkIsNull(req.getPERENTFLAG()));
            signData.append("&COMMENT1=").append(checkIsNull(req.getCOMMENT1()));
            signData.append("&COMMENT2=").append(checkIsNull(req.getCOMMENT2()));
            String origonSignStr = signData.toString();
            log.info("PayLogServiceImpl.checkNotifyData origonSignStr:={}", origonSignStr);
            byte[] signByte = RsaCipher.sign(origonSignStr.getBytes("utf-8"), bestpayConfig.getMerchantPrivateKey());
            String sign = Base64Utils.encode(signByte);
            log.info("PayLogServiceImpl.checkNotifyData SIGNSTR:={}", sign);
            if (req.getSIGNSTR().equals(sign.replaceAll("\\+", " "))) {
                return true;
            }
        } catch (Exception ex) {
            log.error("BestPayEnterprisePaymentImpl.toPay", ex);
            return false;
        }
        return false;
    }

    private String checkIsNull(String val){
        if (StringUtils.isEmpty(val)) {
            return "";
        } else {
            return val;
        }
    }

    @Override
    public OrderPayInfoResp qryOrderPayInfoById(String orderId) {
        return payLogManager.qryOrderPayInfoById(orderId);
    }

    @Override
    public OrderPayInfoResp qryOrderPayInfo(OrderPayInfoReq req) {
        return payLogManager.qryOrderPayInfo(req);
    }

    @Autowired
    private PayAuthorizationService payAuthorizationService;

    

    @Override
    public int saveLog(SaveLogModel saveLogModel) {
        log.info("PayLogServiceImpl.saveLog req={} ",JSON.toJSONString(saveLogModel));
        PayLogDTO payLogDTO = new PayLogDTO();
        payLogDTO.setId(saveLogModel.getPayId());
        payLogDTO.setOrderId(saveLogModel.getOrderId());
        payLogDTO.setChargeMoney(Long.parseLong(saveLogModel.getOrderAmount()));
        payLogDTO.setPlatformType(PayConsts.PLATFORM_TYPE_HNYHJ_B2B);
        payLogDTO.setRequestType(PayConsts.REQUEST_TYPE_1001);
        payLogDTO.setPayPlatformId(PayConsts.PAY_PLATFORM_ID_1001);
        payLogDTO.setPayType(PayConsts.PAY_TYPE_1004);
        payLogDTO.setOperationType(saveLogModel.getOperationType());
        payLogDTO.setTerminalId(PayConsts.TERMINAL_TYPE_1003);
        payLogDTO.setPayStatus(saveLogModel.getPayStatus());
        payLogDTO.setRequestType(saveLogModel.getRequestType());
        payLogDTO.setPayDataMd(saveLogModel.getPayDataMd());
        payLogDTO.setPayData(saveLogModel.getPayData());
        payLogDTO.setRecBankId(saveLogModel.getRecBankId());
        payLogDTO.setRecAccount(saveLogModel.getRecAccount());
        payLogDTO.setRecAccountName(saveLogModel.getRecAccountName());
        payLogDTO.setCreateStaff("1");
        payLogDTO.setCreateDate(new Date());
        int i = this.payLogManager.savaPayLog(payLogDTO);
        log.info("PayLogServiceImpl.saveLog payLogManager.savaPayLog req={}, resp={}", JSON.toJSONString(payLogDTO), i);
        PayOperationLogDTO payOperationLogDTO = new PayOperationLogDTO();
        payOperationLogDTO.setOrderId(saveLogModel.getOrderId());
        payOperationLogDTO.setPayId(saveLogModel.getPayId());
        payOperationLogDTO.setPayPlatform(PayConsts.PAY_PLATFORM_ID_1001);
        payOperationLogDTO.setOperationType(saveLogModel.getOperationType());
        payOperationLogDTO.setPayStatus(saveLogModel.getPayStatus());
        payOperationLogDTO.setCreateStaff("1");
        payOperationLogDTO.setCreateDate(new Date());
        int j = this.payOperationLogManager.savaPayOperationLog(payOperationLogDTO);
        log.info("PayLogServiceImpl.saveLog payOperationLogManager.savaPayOperationLog req={}, resp={}", JSON.toJSONString(payOperationLogDTO), i);
        return j;
    }

    @Override
    public int saveYZFLog(SaveLogModel saveLogModel) {
        log.info("PayLogServiceImpl.saveLog req={} ",JSON.toJSONString(saveLogModel));
        PayLogDTO payLogDTO = new PayLogDTO();
        payLogDTO.setId(saveLogModel.getPayId());
        payLogDTO.setOrderId(saveLogModel.getOrderId());
        payLogDTO.setChargeMoney(Long.parseLong(saveLogModel.getOrderAmount()));
        payLogDTO.setPlatformType(PayConsts.PLATFORM_TYPE_HNYHJ_B2B);
        payLogDTO.setRequestType(PayConsts.REQUEST_TYPE_1006);
        payLogDTO.setPayPlatformId(PayConsts.PAY_PLATFORM_ID_1001);
        payLogDTO.setPayType(PayConsts.PAY_TYPE_1006);
        payLogDTO.setOperationType(saveLogModel.getOperationType());
        payLogDTO.setTerminalId(PayConsts.TERMINAL_TYPE_1003);
        payLogDTO.setPayStatus(saveLogModel.getPayStatus());
        payLogDTO.setRequestType(saveLogModel.getRequestType());
        payLogDTO.setPayDataMd(saveLogModel.getPayDataMd());
        payLogDTO.setPayData(saveLogModel.getPayData());
        payLogDTO.setRecBankId(saveLogModel.getRecBankId());
        payLogDTO.setRecAccount(saveLogModel.getRecAccount());
        payLogDTO.setRecAccountName(saveLogModel.getRecAccountName());
        payLogDTO.setCreateStaff("1");
        payLogDTO.setCreateDate(new Date());
        int i = this.payLogManager.savaPayLog(payLogDTO);
        log.info("PayLogServiceImpl.saveLog payLogManager.savaPayLog req={}, resp={}", JSON.toJSONString(payLogDTO), i);
        PayOperationLogDTO payOperationLogDTO = new PayOperationLogDTO();
        payOperationLogDTO.setOrderId(saveLogModel.getOrderId());
        payOperationLogDTO.setPayId(saveLogModel.getPayId());
        payOperationLogDTO.setPayPlatform(PayConsts.PAY_PLATFORM_ID_1001);
        payOperationLogDTO.setOperationType(saveLogModel.getOperationType());
        payOperationLogDTO.setPayStatus(saveLogModel.getPayStatus());
        payOperationLogDTO.setCreateStaff("1");
        payOperationLogDTO.setCreateDate(new Date());
        int j = this.payOperationLogManager.savaPayOperationLog(payOperationLogDTO);
        log.info("PayLogServiceImpl.saveLog payOperationLogManager.savaPayOperationLog req={}, resp={}", JSON.toJSONString(payOperationLogDTO), i);
        return j;
    }
    
    private String d2l(String d) {
        double d1 = Double.parseDouble(d);
        return String.valueOf(Math.round(d1));
    }

    @Override
    public int updateLog(SaveLogModel saveLogModel) {
        log.info("PayLogServiceImpl.updateLog saveLogModel={}", JSON.toJSONString(saveLogModel));
        PayLogDTO payLogDTO = new PayLogDTO();
        payLogDTO.setId(saveLogModel.getPayId());
        double d = Double.parseDouble(saveLogModel.getOrderAmount());
        payLogDTO.setChargeMoney(Math.round(d * 100));
        payLogDTO.setPayStatus(saveLogModel.getPayStatus());
        payLogDTO.setUpdateDate(new Date());
        payLogDTO.setUpdateStaff("1");
        int i = this.payLogManager.updatePayLogId(payLogDTO);
        log.info("PayLogServiceImpl.updateLog payLogManager.updatePayLogId req={}, resp={}", JSON.toJSONString(payLogDTO), i);
        PayOperationLogDTO payOperationLogDTO = new PayOperationLogDTO();
        payOperationLogDTO.setOrderId(saveLogModel.getOrderId());
        payOperationLogDTO.setPayId(saveLogModel.getPayId());
        payOperationLogDTO.setPayPlatform(PayConsts.PAY_PLATFORM_ID_1001);
        payOperationLogDTO.setOperationType(saveLogModel.getOperationType());
        payOperationLogDTO.setPayStatus(saveLogModel.getPayStatus());
        payOperationLogDTO.setCreateStaff("1");
        payOperationLogDTO.setCreateDate(new Date());
        i = this.payOperationLogManager.savaPayOperationLog(payOperationLogDTO);
        log.info("PayLogServiceImpl.updateLog payOperationLogManager.savaPayOperationLog req={}, resp={}", JSON.toJSONString(payOperationLogDTO), i);

        return 0;
    }

}
