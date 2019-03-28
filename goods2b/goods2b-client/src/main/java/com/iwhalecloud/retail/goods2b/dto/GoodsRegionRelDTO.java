package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * GoodsRegionRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_goods_region_rel, 对应实体GoodsRegionRel类")
public class GoodsRegionRelDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * relId
  	 */
	@ApiModelProperty(value = "relId")
  	private String relId;
	
	/**
  	 * goodsId
  	 */
	@ApiModelProperty(value = "goodsId")
  	private String goodsId;
	
	/**
  	 * regionId
  	 */
	@ApiModelProperty(value = "regionId")
  	private String regionId;
	
	/**
  	 * regionName
  	 */
	@ApiModelProperty(value = "regionName")
  	private String regionName;
	
  	
}
