package com.iwhalecloud.retail.order2b.model;

import lombok.Data;

import java.security.PrivateKey;
import java.security.PublicKey;

@Data
public class BestpayConfigModel {

    private PrivateKey merchantPrivateKey;

    private PublicKey merchantPublicKey;

    private PublicKey bestpayPublicKey;

    private String orgLoginCode;

    private String platCode;

    private String synNoticeUrl;

    private String asynNoticeUrl;

    private String url;
}
