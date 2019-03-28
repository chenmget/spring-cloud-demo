package com.iwhalecloud.retail.goods.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 规格删除对象
 * @author he.sw
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "规格删除对象")
public class ProdSpecificationDeleteReq implements Serializable {
	
  	private static final long serialVersionUID = 1L;
  
	@ApiModelProperty(value = "规格ID数组")
  	private String[] idArray;
  
  	
}
