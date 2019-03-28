package com.iwhalecloud.retail.warehouse.dto.request;

import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
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
@ApiModel(value = "组装串码")
public class ResourceInstItemReq implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 产品ID
	 */
	@ApiModelProperty(value = "产品ID")
	private String productId;

	/**
	 * 串码实列集
	 */
	@ApiModelProperty(value = "串码实列集")
	private List<ResourceInstListResp> resourceInstListRespList;

}
