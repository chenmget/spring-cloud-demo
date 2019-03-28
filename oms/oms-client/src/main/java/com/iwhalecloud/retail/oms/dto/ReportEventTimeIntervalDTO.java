package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "时间区间统计DTO")
public class ReportEventTimeIntervalDTO implements Serializable {

    @ApiModelProperty(value = "时间戳")
    private Long timeStamp;

    @ApiModelProperty(value = "统计数量")
    private Double reportAmount;
}
