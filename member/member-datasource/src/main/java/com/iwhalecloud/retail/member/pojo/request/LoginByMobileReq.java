package com.iwhalecloud.retail.member.pojo.request;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class LoginByMobileReq {
	
	@ApiModelProperty(value = "会员账号是mobile")
	private String mobile;
	
	@ApiModelProperty(value = "登录密码")
	private String pwd;
	
}
