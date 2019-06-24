package com.iwhalecloud.retail.pay.request;

import java.security.PrivateKey;
import java.security.PublicKey;

public class BestpayCertificate
{
    private PrivateKey merchantPrivateKey;
    private PublicKey merchantPublicKey;
    private PublicKey bestpayPublicKey;

    public PrivateKey getMerchantPrivateKey()
    {
        return this.merchantPrivateKey;
    }

    public void setMerchantPrivateKey(PrivateKey merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
    }

    public PublicKey getBestpayPublicKey() {
        return this.bestpayPublicKey;
    }

    public void setBestpayPublicKey(PublicKey bestpayPublicKey) {
        this.bestpayPublicKey = bestpayPublicKey;
    }

    public PublicKey getMerchantPublicKey() {
        return this.merchantPublicKey;
    }

    public void setMerchantPublicKey(PublicKey merchantPublicKey) {
        this.merchantPublicKey = merchantPublicKey;
    }

    public BestpayCertificate(PrivateKey merchantPrivateKey, PublicKey merchantPublicKey, PublicKey bestpayPublicKey) {
        this.merchantPrivateKey = merchantPrivateKey;
        this.merchantPublicKey = merchantPublicKey;
        this.bestpayPublicKey = bestpayPublicKey;
    }
}