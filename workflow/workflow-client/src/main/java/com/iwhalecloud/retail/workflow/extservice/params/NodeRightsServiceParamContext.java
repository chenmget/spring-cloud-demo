package com.iwhalecloud.retail.workflow.extservice.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 节点权限服务参数容器
 * @author z
 * @date 2019/3/26
 */
@Data
public class NodeRightsServiceParamContext extends ServiceParamContext {

    @ApiModelProperty(value = "角色ID")
    private String roleId;
}
