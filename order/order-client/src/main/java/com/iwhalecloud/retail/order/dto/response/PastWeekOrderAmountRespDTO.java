package com.iwhalecloud.retail.order.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 获取过去一周的订单销售额 返回对象
 */
@Data
public class PastWeekOrderAmountRespDTO implements Serializable {
    private static final long serialVersionUID = 147849639463665456L;

    private String orderDate; // 订单日期 yyyy-MM-dd
    private float timeStamp; // yyyy-MM-dd日期时间戳
    private float orderAmount; // 该日期内的 订单销售额
    private String desc;
    private String proportion; //环比
    private int upOrDown; // 1:上升 -1：下降  0：没变化

}
