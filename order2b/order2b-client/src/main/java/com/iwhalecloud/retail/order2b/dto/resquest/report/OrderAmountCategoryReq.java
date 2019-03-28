package com.iwhalecloud.retail.order2b.dto.resquest.report;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 获取某个时间段（今天、本月、本年）的订单销售额类别 请求
 * supplierId、partnerId两个属性值不能同时为空
 */
@Data
public class OrderAmountCategoryReq extends OrderRequest implements Serializable {
    private static final long serialVersionUID = -4779723203327257237L;

    private String supplierId; // 供货商ID
    private String partnerId; // 分销商ID
    private List<Integer> statusList; // 状态
    private Date startDate; // 开始日期
    private Date endDate; // 截止日期 （为空就查 开始日期 到 当前日期）
    private int queryType = 0; // 查询类别：0：今天  1：本月  2：本年  默认0
}
