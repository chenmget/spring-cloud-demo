package com.iwhalecloud.retail.order.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderFlowItemEntity implements Serializable{

    private String goodsId;

    /**
     *  7 合约机
     */
    private String typeId;

    /**
     * 序列号
     */
    private String imei;

    /**
     * 供货商
     */
    private String shipUserId;

    private String productId;

}
