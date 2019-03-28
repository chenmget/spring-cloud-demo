package com.iwhalecloud.retail.order2b.dto.resquest.report;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取今天的订单销售额 请求
 * 两个属性值不能同时为空
 */
@Data
public class TodayOrderAmountReq extends OrderRequest implements Serializable {

    private static final long serialVersionUID = -552837348266891116L;

    private String supplierId; // 供货商ID
    private String partnerId; // 分销商ID
}
