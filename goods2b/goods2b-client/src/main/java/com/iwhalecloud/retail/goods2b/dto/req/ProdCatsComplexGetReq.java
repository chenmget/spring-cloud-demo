package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProdCatsComplexGetReq implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "分类ID")
	private String catId;
	
}
