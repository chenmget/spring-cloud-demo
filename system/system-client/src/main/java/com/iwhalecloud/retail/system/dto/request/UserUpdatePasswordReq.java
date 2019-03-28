package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ApiModel(value = "用户密码修改")
public class UserUpdatePasswordReq implements Serializable {

    private static final long serialVersionUID = -6140404327076413096L;

    @ApiModelProperty(value = "用户ID")
//    @NotEmpty(message = "用户ID不能为空")
    private String userId; // 用户ID

    @ApiModelProperty(value = "新密码")
    @NotEmpty(message = "新密码不能为空")
    private String newPassword; // 新密码

    @ApiModelProperty(value = "旧密码")
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword; // 旧密码

}