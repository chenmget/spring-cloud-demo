package com.iwhalecloud.retail.goods.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProdGoodsCatsListByIdReq implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "分类ID")
	private String catId;
	
	@ApiModelProperty(value = "分类父级ID")
	private String parentCatId;
}
