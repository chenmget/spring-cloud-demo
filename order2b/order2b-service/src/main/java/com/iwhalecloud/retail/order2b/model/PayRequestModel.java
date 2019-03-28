package com.iwhalecloud.retail.order2b.model; /**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2018 All Rights Reserved.
 */

/**
 * @author yangbo2018
 * @version Id: com.bestpay.request.PayRequest.java, v 0.1 2018/2/12 14:56 yangbo2018 Exp $$
 */
public class PayRequestModel {
    private String ORGLOGINCODE ;
    private String PLATCODE     ;
    private String ORDERID      ;
    private String ORDERAMOUNT  ;
    private String PAYTYPE      ;
    private String SYNNOTICEURL ;
    private String ASYNNOTICEURL;
    private String PAYACCOUNT   ;
    private String CARDUSERNAME ;
    private String CERTNO       ;
    private String CERTTYPE     ;
    private String MOBILE       ;
    private String PERENTFLAG   ;
    private String CARDTYPE     ;
    private String BANKCODE     ;
    private String COMMENT1     ;
    private String COMMENT2     ;
    private String SIGNSTR      ;
    private String AESKEY       ;
    private String CHANNEL      ;

    public String getORGLOGINCODE() {
        return ORGLOGINCODE;
    }

    public void setORGLOGINCODE(String ORGLOGINCODE) {
        this.ORGLOGINCODE = ORGLOGINCODE;
    }

    public String getPLATCODE() {
        return PLATCODE;
    }

    public void setPLATCODE(String PLATCODE) {
        this.PLATCODE = PLATCODE;
    }

    public String getORDERID() {
        return ORDERID;
    }

    public void setORDERID(String ORDERID) {
        this.ORDERID = ORDERID;
    }

    public String getORDERAMOUNT() {
        return ORDERAMOUNT;
    }

    public void setORDERAMOUNT(String ORDERAMOUNT) {
        this.ORDERAMOUNT = ORDERAMOUNT;
    }

    public String getPAYTYPE() {
        return PAYTYPE;
    }

    public void setPAYTYPE(String PAYTYPE) {
        this.PAYTYPE = PAYTYPE;
    }

    public String getSYNNOTICEURL() {
        return SYNNOTICEURL;
    }

    public void setSYNNOTICEURL(String SYNNOTICEURL) {
        this.SYNNOTICEURL = SYNNOTICEURL;
    }

    public String getASYNNOTICEURL() {
        return ASYNNOTICEURL;
    }

    public void setASYNNOTICEURL(String ASYNNOTICEURL) {
        this.ASYNNOTICEURL = ASYNNOTICEURL;
    }

    public String getPAYACCOUNT() {
        return PAYACCOUNT;
    }

    public void setPAYACCOUNT(String PAYACCOUNT) {
        this.PAYACCOUNT = PAYACCOUNT;
    }

    public String getCARDUSERNAME() {
        return CARDUSERNAME;
    }

    public void setCARDUSERNAME(String CARDUSERNAME) {
        this.CARDUSERNAME = CARDUSERNAME;
    }

    public String getCERTNO() {
        return CERTNO;
    }

    public void setCERTNO(String CERTNO) {
        this.CERTNO = CERTNO;
    }

    public String getCERTTYPE() {
        return CERTTYPE;
    }

    public void setCERTTYPE(String CERTTYPE) {
        this.CERTTYPE = CERTTYPE;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getPERENTFLAG() {
        return PERENTFLAG;
    }

    public void setPERENTFLAG(String PERENTFLAG) {
        this.PERENTFLAG = PERENTFLAG;
    }

    public String getCARDTYPE() {
        return CARDTYPE;
    }

    public void setCARDTYPE(String CARDTYPE) {
        this.CARDTYPE = CARDTYPE;
    }

    public String getBANKCODE() {
        return BANKCODE;
    }

    public void setBANKCODE(String BANKCODE) {
        this.BANKCODE = BANKCODE;
    }

    public String getCOMMENT1() {
        return COMMENT1;
    }

    public void setCOMMENT1(String COMMENT1) {
        this.COMMENT1 = COMMENT1;
    }

    public String getCOMMENT2() {
        return COMMENT2;
    }

    public void setCOMMENT2(String COMMENT2) {
        this.COMMENT2 = COMMENT2;
    }

    public String getSIGNSTR() {
        return SIGNSTR;
    }

    public void setSIGNSTR(String SIGNSTR) {
        this.SIGNSTR = SIGNSTR;
    }

    public String getAESKEY() {
        return AESKEY;
    }

    public void setAESKEY(String AESKEY) {
        this.AESKEY = AESKEY;
    }

    public String getCHANNEL() {
        return CHANNEL;
    }

    public void setCHANNEL(String CHANNEL) {
        this.CHANNEL = CHANNEL;
    }

    @Override
    public String toString() {
        return "PayRequest{" +
                "ORGLOGINCODE='" + ORGLOGINCODE + '\'' +
                ", PLATCODE='" + PLATCODE + '\'' +
                ", ORDERID='" + ORDERID + '\'' +
                ", ORDERAMOUNT='" + ORDERAMOUNT + '\'' +
                ", PAYTYPE='" + PAYTYPE + '\'' +
                ", SYNNOTICEURL='" + SYNNOTICEURL + '\'' +
                ", ASYNNOTICEURL='" + ASYNNOTICEURL + '\'' +
                ", PAYACCOUNT='" + PAYACCOUNT + '\'' +
                ", CARDUSERNAME='" + CARDUSERNAME + '\'' +
                ", CERTNO='" + CERTNO + '\'' +
                ", CERTTYPE='" + CERTTYPE + '\'' +
                ", MOBILE='" + MOBILE + '\'' +
                ", PERENTFLAG='" + PERENTFLAG + '\'' +
                ", CARDTYPE='" + CARDTYPE + '\'' +
                ", BANKCODE='" + BANKCODE + '\'' +
                ", COMMENT1='" + COMMENT1 + '\'' +
                ", COMMENT2='" + COMMENT2 + '\'' +
                ", SIGNSTR='" + SIGNSTR + '\'' +
                ", AESKEY='" + AESKEY + '\'' +
                ", CHANNEL='" + CHANNEL + '\'' +
                '}';
    }
}
