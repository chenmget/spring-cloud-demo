package com.iwhalecloud.retail.partner.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Manufacturer
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型par_manufacturer, 对应实体Manufacturer类")
public class ManufacturerDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 厂商ID
  	 */
	@ApiModelProperty(value = "厂商ID")
  	private String manufacturerId;
	
	/**
  	 * 厂商编码
  	 */
	@ApiModelProperty(value = "厂商编码")
  	private String manufacturerCode;
	
	/**
  	 * 厂商名称
  	 */
	@ApiModelProperty(value = "厂商名称")
  	private String manufacturerName;
	
	/**
  	 * 厂商级别
  	 */
	@ApiModelProperty(value = "厂商级别")
  	private String manufacturerLevel;
	
	/**
  	 * 状态
  	 */
	@ApiModelProperty(value = "状态")
  	private String status;
	
	/**
  	 * 关联账号id
  	 */
	@ApiModelProperty(value = "关联账号id")
  	private String userId;
	
  	
}
