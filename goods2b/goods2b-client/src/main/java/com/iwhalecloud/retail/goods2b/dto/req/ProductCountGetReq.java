package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdProduct
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "通过厂商ID查询产品数量入参")
public class ProductCountGetReq extends AbstractRequest implements Serializable {

  	private static final long serialVersionUID = -8381618311225779668L;

	/**
	 * manufacturerId
	 */
	@ApiModelProperty(value = "manufacturerId")
	private String manufacturerId;


}
