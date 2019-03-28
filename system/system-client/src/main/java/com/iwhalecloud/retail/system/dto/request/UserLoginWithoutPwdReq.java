package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/3/5 09:27
 * @description
 */

@Data
@ApiModel("系统用户登陆请求参数")
public class UserLoginWithoutPwdReq implements Serializable {
    private static final long serialVersionUID = 4808836850777951738L;

    /**
     * 账号
     */
    @NotEmpty(message = "用户账号不能为空")
    @ApiModelProperty(value = "账号")
    private String loginName;

    /**
     * 密码
     */
//    @NotEmpty(message = "用户密码不能为空")
    @ApiModelProperty(value = "密码")
    private String loginPwd;

    /**
     * 平台标识：0交易平台；1管理平台
     */
//    @NotEmpty(message = "平台标识不能为空")
    @ApiModelProperty(value = "平台标识")
    private String platformFlag;

    /**
     * 登录类型：
     * 1. 统一门户工号（对应值：portal）
     * 2. 云货架账号（对应值：yhj）
     */
    @ApiModelProperty(value = "登录类型")
    private String loginType;
}

