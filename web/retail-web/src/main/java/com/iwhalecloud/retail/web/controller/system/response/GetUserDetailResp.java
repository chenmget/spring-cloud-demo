package com.iwhalecloud.retail.web.controller.system.response;

import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.UserRoleDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "获取用户详情返回对象")
public class GetUserDetailResp {
    @ApiModelProperty(value = "用户信息")
    private UserDTO user;

    @ApiModelProperty(value = "用户角色信息列表")
    private List<UserRoleDTO> userRoleList;
}
