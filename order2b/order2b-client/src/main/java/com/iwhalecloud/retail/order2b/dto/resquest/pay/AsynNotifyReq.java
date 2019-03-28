package com.iwhalecloud.retail.order2b.dto.resquest.pay;

import lombok.Data;

import java.io.Serializable;

@Data
public class AsynNotifyReq implements Serializable {

    /**
     * 登录号
     */
    private String ORGLOGINCODE ;

    /**
     * 平台号
     */
    private String PLATCODE     ;

    /**
     * 客户号
     */
    private String CUSTCODE     ;

    /**
     * 订单/交易流水号 商户生成，需保证唯一
     */
    private String ORDERID      ;

    /**
     * 翼支付订单号
     */
    private String TRSSEQ       ;

    /**
     * 订单金额 单位：元
     */
    private String ORDERAMOUNT  ;

    /**
     * 手续费 单位：元
     */
    private String FEE  ;

    /**
     * 交易日期	19	yyyy-mm-dd 24hh:mm:ss
     */
    private String TRANSDATE      ;

    /**
     * 订单状态	1	限定值：-2|-1|0|1	-2：初始化；-1：处理中；0：失败；1：成功
     */
    private String ORDERSTATUS ;

    /**
     * 银行编码
     */
    private String BANKCODE;

    /**
     * 对公对私标志
     */
    private String PERENTFLAG   ;

    /**
     * 备注1
     */
    private String COMMENT1     ;

    /**
     * 备注2
     */
    private String COMMENT2     ;

    /**
     * 加密串
     */
    private String SIGNSTR      ;

    /**
     * 白条金额 单位:元
     */
    private String IOUSAMOUNT ;

    /**
     * 订单类型 15:白条支付
     */
    private String ORDERTYPE     ;

}
