package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
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
public class ProductUpdateStatusReq extends AbstractRequest implements Serializable {
  	private static final long serialVersionUID = 1L;
  

	@ApiModelProperty(value = "商品ID")
	@NotBlank(message = "ID不能为空")
  	private String goodsId;
  	
	@ApiModelProperty(value = "产品状态")
  	private String status;

	@ApiModelProperty(value = "产品是否删除")
	private String isDeleted;

//	@ApiModelProperty(value = "来源")
//	private String sourceFrom;
  	
}
