package com.iwhalecloud.retail.goods.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

/**
 * 规格值新增对象
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "规格值新增对象")
public class ProdSpecValuesAddReq implements Serializable {
    
  	private static final long serialVersionUID = 1L;
  
	@ApiModelProperty(value = "规格ID")
  	private String specId;
  	
	@ApiModelProperty(value = "值名称")
  	private String specValue;
  	
	@ApiModelProperty(value = "值类型")
  	private Long specType;
  	
}
