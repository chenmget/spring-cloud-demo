package com.iwhalecloud.retail.partner.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * MerchantLimit
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型par_merchant_limit2, 对应实体MerchantLimit2类")
public class MerchantLimit2DTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * lanId
  	 */
	@ApiModelProperty(value = "lanId")
  	private String lanId;
	
	/**
  	 * 限额
  	 */
	@ApiModelProperty(value = "限额")
  	private Long maxSerialNum;
	
	/**
  	 * 本月已使用
  	 */
	@ApiModelProperty(value = "本月已使用")
  	private Long serialNumUsed;
	
  	
}
