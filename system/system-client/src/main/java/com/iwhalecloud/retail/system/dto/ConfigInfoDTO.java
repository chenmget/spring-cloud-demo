package com.iwhalecloud.retail.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ConfigInfo
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型SYS_CONFIG_INFO, 对应实体ConfigInfo类")
public class ConfigInfoDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * cfId
  	 */
	@ApiModelProperty(value = "cfId")
  	private String cfId;
	
	/**
  	 * cfDesc
  	 */
	@ApiModelProperty(value = "cfDesc")
  	private String cfDesc;
	
	/**
  	 * cfValue
  	 */
	@ApiModelProperty(value = "cfValue")
  	private String cfValue;
	
	/**
  	 * subSystem
  	 */
	@ApiModelProperty(value = "subSystem")
  	private String subSystem;
	
  	
}
