package com.iwhalecloud.retail.member.dto.request;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BindingAddReq implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "会员ID")
	private java.lang.String memberId;
	
	@ApiModelProperty(value = "第三方平台类型: 1:微信; 2:支付宝; 3: QQ  ...")
  	private java.lang.Integer targetType = 1;
  	
	@ApiModelProperty(value = "第三方账号特定ID（如微信的openId）")
  	private java.lang.String targetId;
  	
	@ApiModelProperty(value = "会员账号")
  	private java.lang.String uname;
}
