package com.iwhalecloud.retail.order.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 获取某个时间段（今天、本月、本年）的订单销售额类别 返回
 */
@Data
public class OrderAmountCategoryRespDTO implements Serializable {
    private static final long serialVersionUID = 6217551750432530827L;


    private String catName; // 类别名称
    private float orderAmount; // 该日期内的该类别的 订单销售额
    private String proportion; // 占比
}
