package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ProdProduct
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "根据产品ID和品牌ID获取产品")
public class ProductAndBrandGetReq implements Serializable {

  	private static final long serialVersionUID = 1L;


	/**
	 * 品牌ID集合
	 */
	@ApiModelProperty(value = "品牌ID集合")
	private List<String> brandIdList;

	/**
	 * 产品ID集合
	 */
	@ApiModelProperty(value = "产品ID集合")
	private List<String> productIdList;

}
