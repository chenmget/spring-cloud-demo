package com.iwhalecloud.retail.member.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BindingDeleteReq {

	/**
	 * id
	 */
	@ApiModelProperty(value = "id")
	private java.lang.String id;
	
	/**
	 * memberId
	 */
	@ApiModelProperty(value = "memberId")
	private java.lang.String memberId;
	
  	/**
  	 * targetType
  	 */
	@ApiModelProperty(value = "targetType")
  	private java.lang.Integer targetType;
  	
  	/**
  	 * targetId
  	 */
	@ApiModelProperty(value = "targetId")
  	private java.lang.String targetId;
  	
  	/**
  	 * uname
  	 */
	@ApiModelProperty(value = "uname")
  	private java.lang.String uname;
}
