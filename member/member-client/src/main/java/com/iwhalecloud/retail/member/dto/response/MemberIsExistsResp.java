package com.iwhalecloud.retail.member.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MemberIsExistsResp implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="是否存在")
	private boolean exists;
}
