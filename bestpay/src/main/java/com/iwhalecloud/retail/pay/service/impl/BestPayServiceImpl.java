package com.iwhalecloud.retail.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order2b.dto.response.ToPayResp;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.*;
import com.iwhalecloud.retail.order2b.service.BestPayService;
import com.iwhalecloud.retail.pay.config.BusConfig;
import com.iwhalecloud.retail.pay.request.BestpayCertificate;
import com.iwhalecloud.retail.pay.request.PayRequest;
import com.iwhalecloud.retail.pay.util.AESUtil;
import com.iwhalecloud.retail.pay.util.Base64Utils;
import com.iwhalecloud.retail.pay.util.RsaCipher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

@Service
@Slf4j
public class BestPayServiceImpl implements BestPayService {

    @Autowired
    private BestpayCertificate bestpayCertificate;

    @Autowired
    private BusConfig busConfig;

    @Override
    public ToPayResp handlePayData(ToBestPayReq toBestPayReq) {
        log.info("请求参数:{}", toBestPayReq);
        ToPayResp ret = new ToPayResp();
        String aesKey = RandomStringUtils.randomAlphanumeric(16);
        Cipher cipher = null;
        try {
            cipher = AESUtil.initDecryptCipher(aesKey, "AES/CBC/PKCS5Padding", 1, "1234567892546398");
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] aesKeyByte = new byte[0];
        try {
            aesKeyByte = RsaCipher.enDecryptByRsa(aesKey.getBytes("utf-8"), this.bestpayCertificate.getBestpayPublicKey(), 1);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String aesEncodedKey = Base64Utils.encode(aesKeyByte);
        ret.setAesKey(aesEncodedKey);
        String ORGLOGINCODE = toBestPayReq.getOrgLoginCode();
        byte[] encryptORGLOGINCODE = new byte[0];
        try {
            encryptORGLOGINCODE = AESUtil.encrypt(ORGLOGINCODE, cipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ORGLOGINCODE = Base64.encodeBase64String(encryptORGLOGINCODE);
        ret.setOrgLoginCode(ORGLOGINCODE);
        String PAYACCOUNT = toBestPayReq.getOrderAmount();
        byte[] encryptPAYACCOUNT = new byte[0];
        try {
            encryptPAYACCOUNT = AESUtil.encrypt(PAYACCOUNT, cipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PAYACCOUNT = Base64.encodeBase64String(encryptPAYACCOUNT);
        ret.setPayAccount(PAYACCOUNT);
        String CARDUSERNAME = "";
        byte[] encryptCARDUSERNAME = new byte[0];
        try {
            encryptCARDUSERNAME = AESUtil.encrypt(CARDUSERNAME, cipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CARDUSERNAME = Base64.encodeBase64String(encryptCARDUSERNAME);
        ret.setCardUserName(CARDUSERNAME);
        String CERTNO = "";
        byte[] encryptCERTNO = new byte[0];
        try {
            encryptCERTNO = AESUtil.encrypt(CERTNO, cipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CERTNO = Base64.encodeBase64String(encryptCERTNO);
        ret.setCertNo(CERTNO);
        String MOBILE = "";
        byte[] encryptMOBILE = new byte[0];
        try {
            encryptMOBILE = AESUtil.encrypt(MOBILE, cipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MOBILE = Base64.encodeBase64String(encryptMOBILE);
        ret.setMobile(MOBILE);
        String paytype = "1";
        StringBuilder signData = new StringBuilder();
        signData.append("ORGLOGINCODE=").append(ORGLOGINCODE)
                .append("&PLATCODE=")
                .append(busConfig.getPLATCODE())
                .append("&ORDERID=")
                .append(toBestPayReq.getOrderId())
                .append("&ORDERAMOUNT=")
                .append(toBestPayReq.getOrderAmount())
                .append("&PAYTYPE=")
                .append(paytype)
                .append("&SYNNOTICEURL=")
                .append(busConfig.getSYNNOTICEURL())
                .append("&ASYNNOTICEURL=")
                .append(busConfig.getASYNNOTICEURL())
                .append("&PAYACCOUNT=")
                .append(PAYACCOUNT)
                .append("&CARDUSERNAME=")
                .append(CARDUSERNAME)
                .append("&CERTNO=")
                .append(CERTNO)
                .append("&CERTTYPE=")
                .append("")
                .append("&MOBILE=")
                .append(MOBILE)
                .append("&PERENTFLAG=")
                .append("")
                .append("&CARDTYPE=")
                .append("")
                .append("&BANKCODE=")
                .append("")
                .append("&COMMENT1=")
                .append("")
                .append("&COMMENT2=")
                .append("");

        String origonSignStr = signData.toString();
        log.info("待加签原文:{}", origonSignStr);
        byte[] signByte = new byte[0];
        try {
            signByte = RsaCipher.sign(origonSignStr.getBytes("utf-8"), this.bestpayCertificate.getMerchantPrivateKey());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sign = Base64Utils.encode(signByte);
        log.info("加签值SIGNSTR:{}", sign);
        ret.setSignStr(sign);
        ret.setOrderId(toBestPayReq.getOrderId());
        ret.setOrderAmount(toBestPayReq.getOrderAmount());
        ret.setPlatCode(busConfig.getPLATCODE());
        ret.setSynNoticeUrl(busConfig.getSYNNOTICEURL());
        ret.setAsynNoticeUrl(busConfig.getASYNNOTICEURL());
        ret.setPayFormActionUrl(busConfig.getURL());
        ret.setPayType(paytype);
        return ret;
    }
}
