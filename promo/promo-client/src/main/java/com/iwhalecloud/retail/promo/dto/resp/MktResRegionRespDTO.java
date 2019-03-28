package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录营销资源适用地区。
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型MKT_RES_REGION, 对应实体MktResRegion类")
public class MktResRegionRespDTO implements Serializable {
	
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	
  	/**
  	 * 营销资源适用地区标识
  	 */
	@ApiModelProperty(value = "营销资源适用地区标识")
  	private String mktResRegionId;
	
	/**
  	 * 营销资源标识
  	 */
	@ApiModelProperty(value = "营销资源标识")
  	private String mktResId;
  	
  	/**
  	 * 适用区域标识
  	 */
	@ApiModelProperty(value = "适用区域标识")
  	private Long applyRegionId;
  	
  	/**
  	 * 营销资源名称
  	 */
	@ApiModelProperty(value = "营销资源名称")
  	private String mktResName;
	
	/**
  	 * 适用区域名称
  	 */
	@ApiModelProperty(value = "适用区域名称")
  	private String regionName;
  	
  	
	
}
