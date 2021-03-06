package com.iwhalecloud.retail.order2b.dto.model.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "厅店排名统计DTO")
public class ReportOrderShopRankDTO implements Serializable {

    @ApiModelProperty(value = "厅店ID")
    private String shopId;

    @ApiModelProperty(value="厅店名称")
    private String shopName;

    @ApiModelProperty(value = "统计总计")
    private Double reportAmount;
}
