package com.iwhalecloud.retail.order.dto.resquest;

import lombok.Data;

import java.io.Serializable;

/**
 * 获取今天的待办订单数量请求
 * 两个属性值不能同时为空
 */
@Data
public class TodayTodoOrderCountReq implements Serializable {
    private static final long serialVersionUID = -8171970080951383795L;

    private String supplierId; // 供货商ID
    private String partnerId; // 分销商ID
}
