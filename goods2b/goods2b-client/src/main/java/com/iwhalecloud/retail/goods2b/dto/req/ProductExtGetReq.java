package com.iwhalecloud.retail.goods2b.dto.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProductExt
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_product_ext, 对应实体ProductExt类")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductExtGetReq extends AbstractRequest implements Serializable {

  	private static final long serialVersionUID = -1021143075027818466L;
  

	/**
	 * 产品基本信息ID
	 */
	@ApiModelProperty(value = "产品基本信息ID")
	private String productBaseId;
  	
}
