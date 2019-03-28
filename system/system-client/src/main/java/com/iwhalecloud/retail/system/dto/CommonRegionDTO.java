package com.iwhalecloud.retail.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * CommonRegion
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "本地区域对象  对应模型sys_common_region, 对应实体CommonRegion类")
public class CommonRegionDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 公共管理区域标识
  	 */
	@ApiModelProperty(value = "公共管理区域标识")
	private String regionId;
	
	/**
  	 * 记录上级区域标识。
  	 */
	@ApiModelProperty(value = "记录上级区域标识。")
  	private String parRegionId;
	
	/**
  	 * 记录区域名称。
  	 */
	@ApiModelProperty(value = "记录区域名称。")
  	private String regionName;
	
	/**
  	 * 记录区域编码。
  	 */
	@ApiModelProperty(value = "记录区域编码。")
  	private String regionNbr;
	
	/**
  	 * 记录区域类型。LOVB=LOC-0001
  	 */
	@ApiModelProperty(value = "记录区域类型。LOVB=LOC-0001")
  	private String regionType;
	
	/**
  	 * 记录区域描述。
  	 */
	@ApiModelProperty(value = "记录区域描述。")
  	private String regionDesc;
	
	/**
  	 * 记录区域的级别。LOVB=LOC-C-0004
  	 */
	@ApiModelProperty(value = "记录区域的级别。LOVB=LOC-C-0004")
  	private String regionLevel;
	
	/**
  	 * 区号
  	 */
	@ApiModelProperty(value = "区号")
  	private String areaCode;
	
	/**
  	 * 路径名称
  	 */
	@ApiModelProperty(value = "路径名称")
  	private String pathName;
	
	/**
  	 * 路径编码
  	 */
	@ApiModelProperty(value = "路径编码")
  	private String pathCode;
	
  	
}
