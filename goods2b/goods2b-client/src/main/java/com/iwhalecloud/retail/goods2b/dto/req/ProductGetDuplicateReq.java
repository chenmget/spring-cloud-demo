package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.goods2b.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ProdProduct
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "根据产品编码和产品名称获取存在的产品数")
public class ProductGetDuplicateReq extends PageVO {

  	private static final long serialVersionUID = 1L;


	/**
	 * 产品编码和产品名称都不为空ture,否在false
	 */
	@ApiModelProperty(value = "产品基本信息ID")
	private Boolean bothNotNull = false;

	/**
	 * 产品编码
	 */
	@ApiModelProperty(value = "产品编码")
	private String sn;

	/**
	 * 机型名称
	 */
	@ApiModelProperty(value = "机型名称")
	private String unitName;

	@ApiModelProperty(value = "产品Id")
	private String productId;

}
