package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/3/25 16:24
 * @description
 */

@Data
@ApiModel(value = "用户密码重置")
public class UserResetPasswordReq implements Serializable {


    @ApiModelProperty(value = "重置用户id")
    private String updateUserId;

    @ApiModelProperty(value = "重置用户密码")
    private String updatePassword;
}

