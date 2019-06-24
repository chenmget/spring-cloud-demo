package com.iwhalecloud.retail.member.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class MemberIsExistsReq implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户名")
	@NotBlank
	private String uname;
	
	@ApiModelProperty(value = "电话号码")
	private String mobile;
}
