package com.iwhalecloud.retail.warehouse.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author he.sw
 * @date 2019/05/08 21:07
 */
@Data
@ApiModel(value = "流程选择返回")
public class SelectProcessResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 申请单状态
     */
    @ApiModelProperty(value = "requestStatusCd")
    private String requestStatusCd;

    /**
     * 流程的processId
     */
    @ApiModelProperty(value = "processId")
    private String processId;

}
