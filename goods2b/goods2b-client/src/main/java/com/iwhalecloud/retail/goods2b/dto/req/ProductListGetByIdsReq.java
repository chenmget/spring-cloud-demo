package com.iwhalecloud.retail.goods2b.dto.req;
import com.iwhalecloud.retail.dto.AbstractRequest;
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
@ApiModel(value = "通过厂商ID查询产品数量入参")
public class ProductListGetByIdsReq extends AbstractRequest implements Serializable {

  	private static final long serialVersionUID = -8381618311225779668L;

	/**
	 * productIdList
	 */
	@ApiModelProperty(value = "productIdList")
	private List<String> productIdList;


}
