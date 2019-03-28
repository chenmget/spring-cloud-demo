package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProdCatComplexAddReq implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "catId")
    private String catId;

	@ApiModelProperty(value = "goodsId")
    private String goodsId;

	@ApiModelProperty(value = "商品名称")
    private String goodsName;

    
}