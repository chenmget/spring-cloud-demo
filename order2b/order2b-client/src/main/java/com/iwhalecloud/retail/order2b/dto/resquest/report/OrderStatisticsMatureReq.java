package com.iwhalecloud.retail.order2b.dto.resquest.report;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "订单统计请求")
public class OrderStatisticsMatureReq extends OrderRequest implements Serializable {

    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    @ApiModelProperty(value = "是否为当月")
    private Boolean isThisMonth;

    @ApiModelProperty(value = "是否为当年")
    private Boolean isThisYear;

    @ApiModelProperty(value = "是否为买家")
    private Boolean isBuyer;

    @ApiModelProperty(value = "是否为供应商")
    private Boolean isSupplier;
}
