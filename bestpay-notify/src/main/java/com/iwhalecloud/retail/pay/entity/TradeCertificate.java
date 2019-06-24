package com.iwhalecloud.retail.pay.entity;

import java.security.PrivateKey;
import java.security.PublicKey;

public class TradeCertificate {
    private PrivateKey merchantPrivateKey;
    private PublicKey bestpayPublicKey;
    private String iv;

    public PrivateKey getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public void setMerchantPrivateKey(PrivateKey merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
    }

    public PublicKey getBestpayPublicKey() {
        return bestpayPublicKey;
    }

    public void setBestpayPublicKey(PublicKey bestpayPublicKey) {
        this.bestpayPublicKey = bestpayPublicKey;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public TradeCertificate(PrivateKey merchantPrivateKey, PublicKey bestpayPublicKey, String iv) {
        this.merchantPrivateKey = merchantPrivateKey;
        this.bestpayPublicKey = bestpayPublicKey;
        this.iv=iv;
    }
}
