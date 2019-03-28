package com.iwhalecloud.retail.member.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "会员登录请求")
public class MemberLoginReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "使用会员账号登录")
	private String userName;
	
	@ApiModelProperty(value = "登录密码")
	private String password;
	
	@ApiModelProperty(value = "使用手机号码登录")
	private String mobile;
	
}
