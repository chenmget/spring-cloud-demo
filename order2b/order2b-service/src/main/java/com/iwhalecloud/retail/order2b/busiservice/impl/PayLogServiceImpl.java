package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.iwhalecloud.retail.order2b.busiservice.BPEPPayLogService;
import com.iwhalecloud.retail.order2b.config.BestpayCertificate;
import com.iwhalecloud.retail.order2b.config.BusConfig;
import com.iwhalecloud.retail.order2b.consts.PayConsts;
import com.iwhalecloud.retail.order2b.dto.model.pay.PayLogDTO;
import com.iwhalecloud.retail.order2b.dto.model.pay.PayOperationLogDTO;
import com.iwhalecloud.retail.order2b.dto.response.OrderPayInfoResp;
import com.iwhalecloud.retail.order2b.dto.response.ToPayResp;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.AsynNotifyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.OffLinePayReq;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.OrderPayInfoReq;
import com.iwhalecloud.retail.order2b.manager.PayLogManager;
import com.iwhalecloud.retail.order2b.manager.PayOperationLogManager;
import com.iwhalecloud.retail.order2b.model.SaveLogModel;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PayLogServiceImpl implements BPEPPayLogService {

    @Autowired
    private PayLogManager payLogManager;

    private BestpayCertificate bestpayCertificate;

    @Autowired
    private BusConfig busConfig;

    @Autowired
    private PayOperationLogManager payOperationLogManager;


    @Override
    public ToPayResp handlePayData(String orderId, String orderAmount, String orgLoginCode,String operationType) {
        ToPayResp toPayResp = new ToPayResp();
        String payId = IdWorker.getIdStr();
        orderAmount = d2l(orderAmount);
        try{
            toPayResp.setOrgLoginCode(orgLoginCode);
            toPayResp.setPlatCode(busConfig.getPLATCODE());
            toPayResp.setSynNoticeUrl(busConfig.getSYNNOTICEURL());
            toPayResp.setAsynNoticeUrl(busConfig.getASYNNOTICEURL());
            toPayResp.setPayFormActionUrl(busConfig.getURL());
            toPayResp.setOrderId(payId);
            toPayResp.setOrderAmount(orderAmount);
            // 1:收银台支付
            toPayResp.setPayType("1");
            Map<String, String> ret = new HashMap<>();
            String aesKey = RandomStringUtils.randomAlphanumeric(16);
            log.info("BestPayEnterprisePaymentOpenServiceImpl.toPay aesKey is aesKey={}", aesKey);
            //用随机生成的AES密钥
            Cipher cipher = AESUtil.initDecryptCipher(aesKey, AESUtil.AES_CBC_PKCS5, Cipher.ENCRYPT_MODE, AESUtil.IV);
            //对16位加密因子翼支付公钥RSA加密，然后Base64
            byte[] aesKeyByte = RsaCipher.enDecryptByRsa(aesKey.getBytes("utf-8"), bestpayCertificate.getBestpayPublicKey(), Cipher.ENCRYPT_MODE);
            String aesEncodedKey = Base64Utils.encode(aesKeyByte);
            toPayResp.setAesKey(aesEncodedKey);
            byte[] encryptORGLOGINCODE = AESUtil.encrypt(orgLoginCode, cipher);
            orgLoginCode = Base64.encodeBase64String(encryptORGLOGINCODE);
            toPayResp.setOrgLoginCode(orgLoginCode);
            String PAYACCOUNT = toPayResp.getPayAccount();
            byte[] encryptPAYACCOUNT = AESUtil.encrypt(paraCheck(PAYACCOUNT), cipher);
            PAYACCOUNT = Base64.encodeBase64String(encryptPAYACCOUNT);
            toPayResp.setPayAccount(PAYACCOUNT);
            String CARDUSERNAME = toPayResp.getCardUserName();
            byte[] encryptCARDUSERNAME = AESUtil.encrypt(paraCheck(CARDUSERNAME), cipher);
            CARDUSERNAME = Base64.encodeBase64String(encryptCARDUSERNAME);
            toPayResp.setCardUserName(CARDUSERNAME);
            String CERTNO = toPayResp.getCertNo();
            byte[] encryptCERTNO = AESUtil.encrypt(paraCheck(CERTNO), cipher);
            CERTNO = Base64.encodeBase64String(encryptCERTNO);
            toPayResp.setCertNo(CERTNO);
            String MOBILE = toPayResp.getMobile();
            byte[] encryptMOBILE = AESUtil.encrypt(paraCheck(MOBILE), cipher);
            MOBILE = Base64.encodeBase64String(encryptMOBILE);
            toPayResp.setMobile(MOBILE);
            StringBuilder signData = new StringBuilder();
            signData.append("ORGLOGINCODE=").append(orgLoginCode)
                    .append("&PLATCODE=").append(toPayResp.getPlatCode())
                    .append("&ORDERID=").append(toPayResp.getOrderId())
                    .append("&ORDERAMOUNT=").append(orderAmount)
                    .append("&PAYTYPE=").append(toPayResp.getPayType());

            if ( toPayResp.getSynNoticeUrl() == null){
                signData.append("&SYNNOTICEURL=").append("");
            }else {
                signData.append("&SYNNOTICEURL=").append(toPayResp.getSynNoticeUrl());
            }

            if ( toPayResp.getAsynNoticeUrl() == null){
                signData.append("&ASYNNOTICEURL=").append("");
            }else {
                signData.append("&ASYNNOTICEURL=").append(toPayResp.getAsynNoticeUrl());
            }

            if ( PAYACCOUNT == null){
                signData.append("&PAYACCOUNT=").append("");
            }else {
                signData.append("&PAYACCOUNT=").append(PAYACCOUNT);
            }

            if ( CARDUSERNAME == null){
                signData.append("&CARDUSERNAME=").append("");
            }else {
                signData.append("&CARDUSERNAME=").append(CARDUSERNAME);
            }

            if ( CERTNO == null){
                signData.append("&CERTNO=").append("");
            }else {
                signData.append("&CERTNO=").append(CERTNO);
            }

            if ( toPayResp.getCertType() == null){
                signData.append("&CERTTYPE=").append("");
            }else {
                signData.append("&CERTTYPE=").append(toPayResp.getCertType());
            }

            if ( MOBILE == null){
                signData.append("&MOBILE=").append("");
            }else {
                signData.append("&MOBILE=").append(MOBILE);
            }

            if ( toPayResp.getPerentFlag() == null){
                signData.append("&PERENTFLAG=").append("");
            }else {
                signData.append("&PERENTFLAG=").append(toPayResp.getPerentFlag());
            }

            if ( toPayResp.getCardType() == null){
                signData.append("&CARDTYPE=").append("");
            }else {
                signData.append("&CARDTYPE=").append(toPayResp.getCardType());
            }

            if ( toPayResp.getBankCode() == null){
                signData.append("&BANKCODE=").append("");
            }else {
                signData.append("&BANKCODE=").append(toPayResp.getBankCode());
            }

            if ( toPayResp.getComment1() == null){
                signData.append("&COMMENT1=").append("");
            }else {
                signData.append("&COMMENT1=").append(toPayResp.getComment1());
            }

            if ( toPayResp.getComment2() == null){
                signData.append("&COMMENT2=").append("");
            }else {
                signData.append("&COMMENT2=").append(toPayResp.getComment2());
            }
            String origonSignStr = signData.toString();
            log.info("PayLogServiceImpl.handlePayData origonSignStr:{}", origonSignStr);
            byte[] signByte = RsaCipher.sign(origonSignStr.getBytes("utf-8"), bestpayCertificate.getMerchantPrivateKey());
            String sign = Base64Utils.encode(signByte);
            log.info("PayLogServiceImpl.handlePayData SIGNSTR:{}", sign);
            toPayResp.setSignStr(sign);
        }catch (Exception ex){
            log.error("BestPayEnterprisePaymentImpl.toPay", ex);
        }
        log.info("BestPayEnterprisePaymentOpenServiceImpl.toPay resp is resp={}", JSON.toJSONString(toPayResp));
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
    public boolean checkNotifyData(AsynNotifyReq req) {
        try {
            StringBuilder signData = new StringBuilder();
            if (StringUtils.isNotEmpty(req.getORGLOGINCODE())) {
                signData.append("ORGLOGINCODE=").append(req.getORGLOGINCODE());
            } else {
                signData.append("ORGLOGINCODE=").append("");
            }
            if (StringUtils.isNotEmpty(req.getPLATCODE())) {
                signData.append("&PLATCODE=").append(req.getPLATCODE());
            } else {
                signData.append("&PLATCODE=").append("");
            }
            if (StringUtils.isNotEmpty(req.getCUSTCODE())) {
                signData.append("&CUSTCODE=").append(req.getCUSTCODE());
            } else {
                signData.append("&CUSTCODE=").append("");
            }
            if (StringUtils.isNotEmpty(req.getORDERID())) {
                signData.append("&ORDERID=").append(req.getORDERID());
            } else {
                signData.append("&ORDERID=").append("");
            }
            if (StringUtils.isNotEmpty(req.getTRSSEQ())) {
                signData.append("&TRSSEQ=").append(req.getTRSSEQ());
            } else {
                signData.append("&TRSSEQ=").append("");
            }
            if (StringUtils.isNotEmpty(req.getORDERAMOUNT())) {
                signData.append("&ORDERAMOUNT=").append(req.getORDERAMOUNT());
            } else {
                signData.append("&ORDERAMOUNT=").append("");
            }
            if (StringUtils.isNotEmpty(req.getFEE())) {
                signData.append("&FEE=").append(req.getFEE());
            } else {
                signData.append("&FEE=").append("");
            }
            if (StringUtils.isNotEmpty(req.getTRANSDATE())) {
                signData.append("&TRANSDATE=").append(req.getTRANSDATE());
            } else {
                signData.append("&TRANSDATE=").append("");
            }
            if (StringUtils.isNotEmpty(req.getORDERSTATUS())) {
                signData.append("&ORDERSTATUS=").append(req.getORDERSTATUS());
            } else {
                signData.append("&ORDERSTATUS=").append("");
            }
            if (StringUtils.isNotEmpty(req.getBANKCODE())) {
                signData.append("&BANKCODE=").append(req.getBANKCODE());
            } else {
                signData.append("&BANKCODE=").append("");
            }
            if (StringUtils.isNotEmpty(req.getPERENTFLAG())) {
                signData.append("&PERENTFLAG=").append(req.getPERENTFLAG());
            } else {
                signData.append("&PERENTFLAG=").append("");
            }
            if (StringUtils.isNotEmpty(req.getCOMMENT1())) {
                signData.append("&COMMENT1=").append(req.getCOMMENT1());
            } else {
                signData.append("&COMMENT1=").append("");
            }
            if (StringUtils.isNotEmpty(req.getCOMMENT2())) {
                signData.append("&COMMENT2=").append(req.getCOMMENT2());
            } else {
                signData.append("&COMMENT2=").append("");
            }

            String origonSignStr = signData.toString();
            log.info("PayLogServiceImpl.checkNotifyData origonSignStr:={}", origonSignStr);
            byte[] signByte = RsaCipher.sign(origonSignStr.getBytes("utf-8"), bestpayCertificate.getMerchantPrivateKey());
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

    @Override
    public OrderPayInfoResp qryOrderPayInfoById(String orderId) {
        return payLogManager.qryOrderPayInfoById(orderId);
    }

    @Override
    public OrderPayInfoResp qryOrderPayInfo(OrderPayInfoReq req) {
        return payLogManager.qryOrderPayInfo(req);
    }

    @Override
    public int saveLog(SaveLogModel saveLogModel) {
        log.info("PayLogServiceImpl.saveLog req={} ",JSON.toJSONString(saveLogModel));
        PayLogDTO payLogDTO = new PayLogDTO();
        payLogDTO.setId(saveLogModel.getPayId());
        payLogDTO.setOrderId(saveLogModel.getOrderId());
        payLogDTO.setChargeMoney(Long.parseLong(saveLogModel.getOrderAmount()));
        payLogDTO.setPlatformType(PayConsts.PLATFORM_TYPE_HNYHJ_B2B);
        payLogDTO.setRequestType(PayConsts.REQUEST_TYPE_1004);
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
        //payOperationLogDTO.setOperationType(PayConsts.OPERATION_TYPE_1001);
        payOperationLogDTO.setOperationType(saveLogModel.getOperationType());
        payOperationLogDTO.setPayStatus(saveLogModel.getPayStatus());
        payOperationLogDTO.setCreateStaff("1");
        payOperationLogDTO.setCreateDate(new Date());
        int j = this.payOperationLogManager.savaPayOperationLog(payOperationLogDTO);
        log.info("PayLogServiceImpl.saveLog payOperationLogManager.savaPayOperationLog req={}, resp={}", JSON.toJSONString(payOperationLogDTO), i);
        return 0;
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

    private String paraCheck(String param){
        if (null == param) {
            return "";
        } else {
            return param;
        }

    }
}
