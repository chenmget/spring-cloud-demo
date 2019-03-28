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
@ApiModel(value = "对应模型par_merchant_limit, 对应实体MerchantLimit类")
public class MerchantLimitDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 商家ID
  	 */
	@ApiModelProperty(value = "商家ID")
  	private String merchantId;
	
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
