package com.iwhalecloud.retail.order2b.dto.resquest.report;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取今天的订单数量请求
 * 两个不能同时为空
 */
@Data
public class TodayOrderCountReq extends OrderRequest implements Serializable {

    private static final long serialVersionUID = -8994566224518478320L;

    private String supplierId; // 供货商ID
    private String partnerId; // 分销商ID
}
