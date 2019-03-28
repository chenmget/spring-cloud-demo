package com.iwhalecloud.retail.web.controller.member.requst;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel(value = "登录请求对象")
public class LoginReq implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "手机号不能为空")
	@ApiModelProperty(value = "手机号")
	private String mobile; // 手机号

    @NotEmpty(message = "密码或验证码不能为空")
    @ApiModelProperty(value = "密码或验证码")
    private String pwd; // 密码或验证码

    @NotEmpty(message = "登陆类型不能为空")
    @ApiModelProperty(value = "登陆类型 1：密码登陆   2：验证码登陆")
    private String loginType; // 登陆类型： 1：密码登陆   2：验证码登陆

}
