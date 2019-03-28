package com.iwhalecloud.retail.oms.dto.response.report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportOperationDeskTopRespDTO {

    @ApiModelProperty(value = "orderCount")
    private int orderCount;

    @ApiModelProperty(value = "orderAmount")
    private double orderAmount;

    @ApiModelProperty(value = "cartCount")
    private int cartCount;

    @ApiModelProperty(value = "transRate")
    private String transRate;
}
