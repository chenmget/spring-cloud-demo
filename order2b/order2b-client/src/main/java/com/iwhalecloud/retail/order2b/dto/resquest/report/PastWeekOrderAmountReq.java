package com.iwhalecloud.retail.order2b.dto.resquest.report;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取过去一周的订单销售额 请求
 * 两个属性值不能同时为空
 */
@Data
public class PastWeekOrderAmountReq extends OrderRequest implements Serializable {

    private static final long serialVersionUID = 976268339208293792L;

    private String supplierId; // 供货商ID
    private String partnerId; // 分销商ID
}
