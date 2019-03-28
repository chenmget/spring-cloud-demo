package com.iwhalecloud.retail.member.dto.response;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class MemberIsExistsResp implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="是否存在")
	private boolean exists;
}
