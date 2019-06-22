package com.iwhalecloud.retail.oms.dto.resquest.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value="运营人员工作台查询")
public class ReportOperationDeskReq implements Serializable {

    @ApiModelProperty(value = "销售量状态")
    private List<Integer> orderCountStatuses;

    @ApiModelProperty(value = "销售额状态")
    private List<Integer> orderAmountStatuses;

    @ApiModelProperty(value = "订单量状态")
    private List<Integer> saleCountStatuses;

    @ApiModelProperty(value = "事件代码（不填）")
    private String eventCode;

    @ApiModelProperty(value = "统计开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "统计结束时间")
    private Date endTime;

    @ApiModelProperty(value = "区域代码（选填）")
    private String areaCode;

    @ApiModelProperty(value = "排行位数")
    private Integer rankNum;

    @ApiModelProperty(value = "图表展示类型（查询下部数据必填）")
    private String reportType;

}
