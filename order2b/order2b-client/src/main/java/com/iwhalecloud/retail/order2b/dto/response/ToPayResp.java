package com.iwhalecloud.retail.order2b.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ToPayResp implements Serializable {

    private String orgLoginCode ;
    private String platCode     ;
    private String orderId      ;
    private String orderAmount  ;
    private String payType      ;
    private String synNoticeUrl ;
    private String asynNoticeUrl;
    private String payAccount   ;
    private String cardUserName ;
    private String certNo       ;
    private String certType     ;
    private String mobile       ;
    private String perentFlag   ;
    private String cardType     ;
    private String bankCode     ;
    private String comment1     ;
    private String comment2     ;
    private String signStr      ;
    private String aesKey       ;
    private String channel      ;

    private String payFormActionUrl ;
}
