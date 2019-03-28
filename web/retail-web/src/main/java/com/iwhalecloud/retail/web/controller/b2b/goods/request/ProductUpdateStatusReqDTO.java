package com.iwhalecloud.retail.web.controller.b2b.goods.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * ProdProduct
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_product, 对应实体ProdProduct类")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductUpdateStatusReqDTO implements Serializable {
  	private static final long serialVersionUID = 1L;
  

	@ApiModelProperty(value = "商品ID")
	@NotBlank(message = "ID不能为空")
  	private String goodsId;
  	
	@ApiModelProperty(value = "产品状态")
  	private String status;

	/**
	 * 来源
	 */
	@ApiModelProperty(value = "来源")
	private String sourceFrom;
  	
}
