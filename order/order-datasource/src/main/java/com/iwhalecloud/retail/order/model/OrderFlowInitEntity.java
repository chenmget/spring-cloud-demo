package com.iwhalecloud.retail.order.model;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 *
 */
public class OrderFlowInitEntity implements Serializable {

    private Long id;
    private String typeCode;
    private String payType;
    private String typeCodeName;
    private String flowList;
    private String orderType;
    private String bindType;


}
