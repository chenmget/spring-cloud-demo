package com.iwhalecloud.retail.goods.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdSpecValues
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_spec_values, 对应实体ProdSpecValues类")
public class ProdSpecValuesGetReq implements Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
	@ApiModelProperty(value = "值ID")
  	private String specValueId;
  	
	@ApiModelProperty(value = "规格ID")
  	private String specId;
  	
	@ApiModelProperty(value = "值名称")
  	private String specValue;
  	
	@ApiModelProperty(value = "值类型")
  	private Long specType;
  	
}
