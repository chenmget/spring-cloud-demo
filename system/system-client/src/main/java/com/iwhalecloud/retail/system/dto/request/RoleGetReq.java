package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("根据条件查找角色")
public class RoleGetReq implements Serializable {
    private static final long serialVersionUID = 4207885862767577071L;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色名称")
    private String roleId;
}
