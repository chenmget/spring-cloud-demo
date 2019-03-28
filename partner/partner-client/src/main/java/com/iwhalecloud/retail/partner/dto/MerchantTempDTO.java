package com.iwhalecloud.retail.partner.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * MemchantTemp
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型par_merchant_temp, 对应实体MerchantTemp类")
public class MerchantTempDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 主键
  	 */
	@ApiModelProperty(value = "主键")
  	private String id;
	
	/**
  	 * 每行参数串
  	 */
	@ApiModelProperty(value = "每行参数串")
  	private String txtStr;
	
	/**
  	 * 批次
  	 */
	@ApiModelProperty(value = "批次")
  	private String patch;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createDate;
	
	/**
  	 * 状态
  	 */
	@ApiModelProperty(value = "状态")
  	private String status;
	
	/**
  	 * 处理时间
  	 */
	@ApiModelProperty(value = "处理时间")
  	private String handleDate;
	
	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String note;
	
  	
}
