package com.iwhalecloud.retail.workflow.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Z
 * e 2019/1/5
 */
@Data
public class RouteReq implements Serializable {


    @ApiModelProperty(value = "当前节点")
    private String curNodeId;

    @ApiModelProperty(value = "流程id")
    private String processId;



}
