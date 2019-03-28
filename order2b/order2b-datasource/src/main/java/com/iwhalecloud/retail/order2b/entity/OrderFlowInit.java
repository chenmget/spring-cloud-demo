package com.iwhalecloud.retail.order2b.entity;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 *
 */
public class OrderFlowInit implements Serializable {

    private Long id;
    /**
     * 1:b2c
     * 2:b2b
     */
    private String typeCode;
    /**
     * 付款环节：
     *
     */
    private String payType;
    private String typeCodeName;
    private String flowList;
    /**
     * 0:正常订单
     * 1：预售单
     */
    private String orderType;
    /**
     * b2b
     * 0：不需要买家确认
     * 1：买家确认
     */
    private String bindType;
    private String serviceType;

    private String sourceFrom;


}
