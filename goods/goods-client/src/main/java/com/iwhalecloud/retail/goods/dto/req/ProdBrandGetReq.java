package com.iwhalecloud.retail.goods.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class ProdBrandGetReq implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "品牌ID")
	@NotBlank(message = "品牌ID不能为空")
	String brandId;
	
	// 原逻辑此参数查询缓存中的数据
	@NotBlank(message = "商品ID不能为空")
	@ApiModelProperty(value = "商品ID")
	String goodsId;
	
}
