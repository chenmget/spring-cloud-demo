package com.iwhalecloud.retail.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * Goods
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型GoodsDTO, 对应实体Goods类")
public class GoodsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

  	//属性 begin
	/**
	 *商品id
	 */
	@ApiModelProperty(value = "商品id")
	private String goodsId;
	/**
	 *商品名称
	 */
	@ApiModelProperty(value = "商品名称")
	private String goodsName;
	/**
	 *商品图片地址
	 */
	@ApiModelProperty(value = "商品图片地址")
	private String imageFile;
}
