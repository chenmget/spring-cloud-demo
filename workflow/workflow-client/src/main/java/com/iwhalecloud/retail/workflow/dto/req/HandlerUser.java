package com.iwhalecloud.retail.workflow.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2019/1/10
 */
@Data
public class HandlerUser implements Serializable {

    @ApiModelProperty(value = "当前节点处理用户ID")
    private String handlerUserId;

    @ApiModelProperty(value = "当前节点处理用户名称")
    private String handlerUserName;
}
