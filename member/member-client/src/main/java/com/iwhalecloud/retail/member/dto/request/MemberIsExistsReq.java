package com.iwhalecloud.retail.member.dto.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class MemberIsExistsReq implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户名")
	@NotBlank
	private String uname;
	
	@ApiModelProperty(value = "电话号码")
	private String mobile;
}
