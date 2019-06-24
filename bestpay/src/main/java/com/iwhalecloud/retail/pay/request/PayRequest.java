package com.iwhalecloud.retail.pay.request;

public class PayRequest
{
    private String ORGLOGINCODE;
    private String PLATCODE;
    private String ORDERID;
    private String ORDERAMOUNT;
    private String PAYTYPE;
    private String SYNNOTICEURL;
    private String ASYNNOTICEURL;
    private String PAYACCOUNT;
    private String CARDUSERNAME;
    private String CERTNO;
    private String CERTTYPE;
    private String MOBILE;
    private String PERENTFLAG;
    private String CARDTYPE;
    private String BANKCODE;
    private String COMMENT1;
    private String COMMENT2;
    private String SIGNSTR;
    private String AESKEY;
    private String CHANNEL;

    public String getORGLOGINCODE()
    {
        return this.ORGLOGINCODE;
    }

    public void setORGLOGINCODE(String ORGLOGINCODE) {
        this.ORGLOGINCODE = ORGLOGINCODE;
    }

    public String getPLATCODE() {
        return this.PLATCODE;
    }

    public void setPLATCODE(String PLATCODE) {
        this.PLATCODE = PLATCODE;
    }

    public String getORDERID() {
        return this.ORDERID;
    }

    public void setORDERID(String ORDERID) {
        this.ORDERID = ORDERID;
    }

    public String getORDERAMOUNT() {
        return this.ORDERAMOUNT;
    }

    public void setORDERAMOUNT(String ORDERAMOUNT) {
        this.ORDERAMOUNT = ORDERAMOUNT;
    }

    public String getPAYTYPE() {
        return this.PAYTYPE;
    }

    public void setPAYTYPE(String PAYTYPE) {
        this.PAYTYPE = PAYTYPE;
    }

    public String getSYNNOTICEURL() {
        return this.SYNNOTICEURL;
    }

    public void setSYNNOTICEURL(String SYNNOTICEURL) {
        this.SYNNOTICEURL = SYNNOTICEURL;
    }

    public String getASYNNOTICEURL() {
        return this.ASYNNOTICEURL;
    }

    public void setASYNNOTICEURL(String ASYNNOTICEURL) {
        this.ASYNNOTICEURL = ASYNNOTICEURL;
    }

    public String getPAYACCOUNT() {
        return this.PAYACCOUNT;
    }

    public void setPAYACCOUNT(String PAYACCOUNT) {
        this.PAYACCOUNT = PAYACCOUNT;
    }

    public String getCARDUSERNAME() {
        return this.CARDUSERNAME;
    }

    public void setCARDUSERNAME(String CARDUSERNAME) {
        this.CARDUSERNAME = CARDUSERNAME;
    }

    public String getCERTNO() {
        return this.CERTNO;
    }

    public void setCERTNO(String CERTNO) {
        this.CERTNO = CERTNO;
    }

    public String getCERTTYPE() {
        return this.CERTTYPE;
    }

    public void setCERTTYPE(String CERTTYPE) {
        this.CERTTYPE = CERTTYPE;
    }

    public String getMOBILE() {
        return this.MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getPERENTFLAG() {
        return this.PERENTFLAG;
    }

    public void setPERENTFLAG(String PERENTFLAG) {
        this.PERENTFLAG = PERENTFLAG;
    }

    public String getCARDTYPE() {
        return this.CARDTYPE;
    }

    public void setCARDTYPE(String CARDTYPE) {
        this.CARDTYPE = CARDTYPE;
    }

    public String getBANKCODE() {
        return this.BANKCODE;
    }

    public void setBANKCODE(String BANKCODE) {
        this.BANKCODE = BANKCODE;
    }

    public String getCOMMENT1() {
        return this.COMMENT1;
    }

    public void setCOMMENT1(String COMMENT1) {
        this.COMMENT1 = COMMENT1;
    }

    public String getCOMMENT2() {
        return this.COMMENT2;
    }

    public void setCOMMENT2(String COMMENT2) {
        this.COMMENT2 = COMMENT2;
    }

    public String getSIGNSTR() {
        return this.SIGNSTR;
    }

    public void setSIGNSTR(String SIGNSTR) {
        this.SIGNSTR = SIGNSTR;
    }

    public String getAESKEY() {
        return this.AESKEY;
    }

    public void setAESKEY(String AESKEY) {
        this.AESKEY = AESKEY;
    }

    public String getCHANNEL() {
        return this.CHANNEL;
    }

    public void setCHANNEL(String CHANNEL) {
        this.CHANNEL = CHANNEL;
    }

    @Override
    public String toString()
    {
        return "PayRequest{ORGLOGINCODE='" + this.ORGLOGINCODE + '\'' + ", PLATCODE='" + this.PLATCODE + '\'' + ", ORDERID='" + this.ORDERID + '\'' + ", ORDERAMOUNT='" + this.ORDERAMOUNT + '\'' + ", PAYTYPE='" + this.PAYTYPE + '\'' + ", SYNNOTICEURL='" + this.SYNNOTICEURL + '\'' + ", ASYNNOTICEURL='" + this.ASYNNOTICEURL + '\'' + ", PAYACCOUNT='" + this.PAYACCOUNT + '\'' + ", CARDUSERNAME='" + this.CARDUSERNAME + '\'' + ", CERTNO='" + this.CERTNO + '\'' + ", CERTTYPE='" + this.CERTTYPE + '\'' + ", MOBILE='" + this.MOBILE + '\'' + ", PERENTFLAG='" + this.PERENTFLAG + '\'' + ", CARDTYPE='" + this.CARDTYPE + '\'' + ", BANKCODE='" + this.BANKCODE + '\'' + ", COMMENT1='" + this.COMMENT1 + '\'' + ", COMMENT2='" + this.COMMENT2 + '\'' + ", SIGNSTR='" + this.SIGNSTR + '\'' + ", AESKEY='" + this.AESKEY + '\'' + ", CHANNEL='" + this.CHANNEL + '\'' + '}';
    }
}