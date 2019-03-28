package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "订单发货：串码校验")
public class ValidResourceInstItem implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 记录营销资源实例编码。
	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
	private List<String> mktResInstNbr;

	/**
	 * 商家类型
	 */
	@ApiModelProperty(value = "商家类型")
	private String productId;

}
