package com.iwhalecloud.retail.member.pojo.request;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class LoginByNameReq {

	@ApiModelProperty(value = "使用会员账号登录")
	private String userName;
	
	@ApiModelProperty(value = "会员账号是Email")
	private String email;
	
	@ApiModelProperty(value = "登录密码")
	private String pwd;
	
}
