package com.iwhalecloud.retail.pay.controller;

import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;

import com.iwhalecloud.retail.pay.request.BestpayCertificate;
import com.iwhalecloud.retail.pay.request.PayRequest;
import com.iwhalecloud.retail.pay.util.AESUtil;
import com.iwhalecloud.retail.pay.util.Base64Utils;
import com.iwhalecloud.retail.pay.util.RsaCipher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/trans"})
@Slf4j
public class PayController
{

    @Autowired
    private BestpayCertificate bestpayCertificate;

    @RequestMapping({"/pay"})
    @ResponseBody
    public Map<?, ?> recharge(PayRequest payRequest)
            throws Exception
    {
        log.info("请求参数：{}", payRequest);
        Map ret = new HashMap();
        String aesKey = RandomStringUtils.randomAlphanumeric(16);

        Cipher cipher = AESUtil.initDecryptCipher(aesKey, "AES/CBC/PKCS5Padding", 1, "1234567892546398");

        byte[] aesKeyByte = RsaCipher.enDecryptByRsa(aesKey.getBytes("utf-8"), this.bestpayCertificate.getBestpayPublicKey(), 1);
        String aesEncodedKey = Base64Utils.encode(aesKeyByte);
        ret.put("AESKEY", aesEncodedKey);
        String ORGLOGINCODE = payRequest.getORGLOGINCODE();
        byte[] encryptORGLOGINCODE = AESUtil.encrypt(ORGLOGINCODE, cipher);
        ORGLOGINCODE = Base64.encodeBase64String(encryptORGLOGINCODE);
        ret.put("ORGLOGINCODE", ORGLOGINCODE);

        String PAYACCOUNT = payRequest.getPAYACCOUNT();
        byte[] encryptPAYACCOUNT = AESUtil.encrypt(PAYACCOUNT, cipher);
        PAYACCOUNT = Base64.encodeBase64String(encryptPAYACCOUNT);
        ret.put("PAYACCOUNT", PAYACCOUNT);

        String CARDUSERNAME = payRequest.getCARDUSERNAME();
        byte[] encryptCARDUSERNAME = AESUtil.encrypt(CARDUSERNAME, cipher);
        CARDUSERNAME = Base64.encodeBase64String(encryptCARDUSERNAME);
        ret.put("CARDUSERNAME", CARDUSERNAME);

        String CERTNO = payRequest.getCERTNO();
        byte[] encryptCERTNO = AESUtil.encrypt(CERTNO, cipher);
        CERTNO = Base64.encodeBase64String(encryptCERTNO);
        ret.put("CERTNO", CERTNO);

        String MOBILE = payRequest.getMOBILE();
        byte[] encryptMOBILE = AESUtil.encrypt(MOBILE, cipher);
        MOBILE = Base64.encodeBase64String(encryptMOBILE);
        ret.put("MOBILE", MOBILE);

        StringBuilder signData = new StringBuilder();
        signData.append("ORGLOGINCODE=").append(ORGLOGINCODE)
                .append("&PLATCODE=")
                .append(payRequest.getPLATCODE())
                .append("&ORDERID=")
                .append(payRequest.getORDERID())
                .append("&ORDERAMOUNT=")
                .append(payRequest.getORDERAMOUNT())
                .append("&PAYTYPE=")
                .append(payRequest.getPAYTYPE())
                .append("&SYNNOTICEURL=")
                .append(payRequest.getSYNNOTICEURL())
                .append("&ASYNNOTICEURL=")
                .append(payRequest.getASYNNOTICEURL())
                .append("&PAYACCOUNT=")
                .append(PAYACCOUNT)
                .append("&CARDUSERNAME=")
                .append(CARDUSERNAME)
                .append("&CERTNO=")
                .append(CERTNO)
                .append("&CERTTYPE=")
                .append(payRequest.getCERTTYPE())
                .append("&MOBILE=")
                .append(MOBILE)
                .append("&PERENTFLAG=")
                .append(payRequest.getPERENTFLAG())
                .append("&CARDTYPE=")
                .append(payRequest.getCARDTYPE())
                .append("&BANKCODE=")
                .append(payRequest.getBANKCODE())
                .append("&COMMENT1=")
                .append(payRequest.getCOMMENT1())
                .append("&COMMENT2=")
                .append(payRequest.getCOMMENT2());

        String origonSignStr = signData.toString();
        log.info("待加签原文:{}", origonSignStr);
        byte[] signByte = RsaCipher.sign(origonSignStr.getBytes("utf-8"), this.bestpayCertificate.getMerchantPrivateKey());
        String sign = Base64Utils.encode(signByte);
        log.info("加签值SIGNSTR:{}", sign);
        ret.put("SIGNSTR", sign);
        return ret;
    }
}