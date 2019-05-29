package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_inst, 对应实体ResourceInst类")
public class ProductResourceInstGetReq extends AbstractRequest implements Serializable{

  	private static final long serialVersionUID = 1L;


	/**
  	 * 产品ID
  	 */
	@ApiModelProperty(value = "产品ID")
  	private String productId;
	/**
  	 * 品牌
  	 */
	@ApiModelProperty(value = "产品基本信息表里的品牌ID")
  	private String brandId;

	/**
  	 * 产品表里的产品编码
  	 */
	@ApiModelProperty(value = "产品表里的产品编码")
  	private String sn;
	/**
  	 * 产品基本信息表里的型号名称
  	 */
	@ApiModelProperty(value = "产品基本信息表里的型号名称")
  	private String unitTypeName;
	/**
  	 * 产品名称
  	 */
	@ApiModelProperty(value = "产品名称")
  	private String unitName;
	/**
  	 * 产品基本表名称
  	 */
	@ApiModelProperty(value = "产品基本表名称")
  	private String productName;

}
