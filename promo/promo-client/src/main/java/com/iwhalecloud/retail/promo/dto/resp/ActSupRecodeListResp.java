package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhou.zc
 * @date 2019年03月06日
 * @Description:查询前置补录的记录
 */
@Data
public class ActSupRecodeListResp implements Serializable {

    private static final long serialVersionUID = -4820336403513126364L;

    @ApiModelProperty(value = "补录记录")
    private String recordId;

    @ApiModelProperty(value = "补录时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "补录人")
    private String creator;

    @ApiModelProperty(value = "活动code")
    private String code;

    @ApiModelProperty(value = "活动名称")
    private String name;

    @ApiModelProperty(value = "活动开始时间")
    private Date startTime;

    @ApiModelProperty(value = "活动结束时间")
    private Date endTime;

    @ApiModelProperty(value = "补录说明")
    private String description;

    @ApiModelProperty(value = "补录状态")
    private String status;

    @ApiModelProperty(value = "申请凭据")
    private String applyProof;
}
