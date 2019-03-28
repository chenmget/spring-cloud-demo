package com.iwhalecloud.retail.system.dto.response;

import com.iwhalecloud.retail.system.dto.UserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "系统用户登录返回")
public class UserLoginResp implements Serializable {
    private static final long serialVersionUID = -7938294452391821627L;

    //属性 begin
    /**
     * 用户信息
     */
    @ApiModelProperty(value = "用户信息")
    private UserDTO userDTO;

    /**
     * 登陆错误信息提示
     */
    @ApiModelProperty(value = "登陆错误信息")
    private String errorMessage;

    /**
     * 是否登陆成功 默认false  不成功
     */
    @ApiModelProperty(value = "是否登陆成功")
    private Boolean isLoginSuccess = false;

}
