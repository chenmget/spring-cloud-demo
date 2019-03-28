package com.iwhalecloud.retail.order.dto.resquest;

import lombok.Data;

import java.io.Serializable;

/**
 * 获取今天的订单销售额 请求
 * 两个属性值不能同时为空
 */
@Data
public class TodayOrderAmountReq implements Serializable {

    private static final long serialVersionUID = -552837348266891116L;

    private String supplierId; // 供货商ID
    private String partnerId; // 分销商ID
}
