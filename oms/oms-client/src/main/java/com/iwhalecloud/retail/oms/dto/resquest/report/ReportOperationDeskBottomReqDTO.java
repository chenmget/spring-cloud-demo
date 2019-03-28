package com.iwhalecloud.retail.oms.dto.resquest.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="运行人员工作台下部查询")
public class ReportOperationDeskBottomReqDTO implements Serializable {

    @ApiModelProperty(value = "查询起始时间",required = true)
    private Date beginTime;

    @ApiModelProperty(value = "查询终止时间",required = false)
    private Date endTime;

    @ApiModelProperty(value = "图表展示类型")
    private String reportType;

}
