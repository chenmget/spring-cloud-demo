package com.iwhalecloud.retail.order2b.config;

import java.security.PrivateKey;
import java.security.PublicKey;

public class BestpayCertificate {
    private PrivateKey merchantPrivateKey;
    private PublicKey merchantPublicKey;
    private PublicKey bestpayPublicKey;

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

    public PublicKey getMerchantPublicKey() {
        return merchantPublicKey;
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
