package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhou.zc
 * @date 2019年03月06日
 * @Description:查询前置活动补录记录
 */
@Data
public class QueryActSupRecordReq extends AbstractPageReq implements Serializable {

    private static final long serialVersionUID = 4395794751157458446L;

    @ApiModelProperty(value = "活动名称")
    private String name;

    @ApiModelProperty(value = "活动编码")
    private String code;

    @ApiModelProperty(value = "补录状态")
    private String status;

    @ApiModelProperty(value = "补录时间段")
    private String supStartDate;

    @ApiModelProperty(value = "补录时间段")
    private String supEndDate;

    @ApiModelProperty(value = "活动开始时间段")
    private String actBeginStartDate;

    @ApiModelProperty(value = "活动开始时间段")
    private String actBeginEndDate;

    @ApiModelProperty(value = "活动结束时间段")
    private String actFinishStartDate;

    @ApiModelProperty(value = "活动结束时间段")
    private String actFinishEndDate;

    @ApiModelProperty(value = "补录记录id")
    private String recordId;
}
