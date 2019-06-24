package com.iwhalecloud.retail.member.pojo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MemberUpdateReq implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "memberId")
  	private java.lang.String memberId;
	
	@ApiModelProperty(value = "上次登陆时间")
	private java.util.Date lastLoginTime;

	@ApiModelProperty(value = "登陆次数")
	private java.lang.Integer loginCount;
	
	
}
