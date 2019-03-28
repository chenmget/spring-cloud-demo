package com.iwhalecloud.retail.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * ProdSpecification
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_specification, 对应实体ProdSpecification类")
public class ProdSpecificationDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 规格ID
  	 */
	@ApiModelProperty(value = "规格ID")
  	private String specId;
	
	/**
  	 * 规格名称
  	 */
	@ApiModelProperty(value = "规格名称")
  	private String specName;
	
	/**
  	 * 规格类型
  	 */
	@ApiModelProperty(value = "规格类型")
  	private Long specType;
	
	/**
  	 * 规格描述
  	 */
	@ApiModelProperty(value = "规格描述")
  	private String specMemo;

	/**
	 * 规格值列表
	 */
	@ApiModelProperty(value = "规格值列表")
	private List<ProdSpecValuesDTO> valueList;

}
