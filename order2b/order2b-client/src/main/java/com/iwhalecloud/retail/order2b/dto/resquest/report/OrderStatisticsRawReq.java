package com.iwhalecloud.retail.order2b.dto.resquest.report;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "订单统计请求")
public class OrderStatisticsRawReq extends OrderRequest implements Serializable {

    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    @ApiModelProperty(value = "是否为买家")
    private Boolean isBuyer;

    @ApiModelProperty(value = "是否取今天数据")
    private Boolean isToday;

    @ApiModelProperty(value = "状态列表")
    private List<String> statusList;
}
