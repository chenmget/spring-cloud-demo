package com.iwhalecloud.retail.goods.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 规格查询返回对象
 * @author he.sw
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_specification, 对应实体ProdSpecification类")
public class ProdSpecificationGetResp implements Serializable {
	
  	private static final long serialVersionUID = 1L;
  
	@ApiModelProperty(value = "规格ID")
  	private String specId;
  	
	@ApiModelProperty(value = "规格名称")
  	private String specName;
  	
	@ApiModelProperty(value = "规格类型")
  	private Long specType;
  	
	@ApiModelProperty(value = "规格描述")
  	private String specMemo;
  	
	@ApiModelProperty(value = "状态")
  	private Long disabled;
  	
}
