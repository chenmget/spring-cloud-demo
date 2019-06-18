package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ProdTagRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "查询ProdTagRel类集合")
public class TagRelListResp implements Serializable {

	private static final long serialVersionUID = 7754952843932505795L;

	@ApiModelProperty(value = "relId")
  	private String relId;
  	
	@ApiModelProperty(value = "tagId")
  	private String tagId;
  	
	@ApiModelProperty(value = "productBaseId")
  	private String productBaseId;

	@ApiModelProperty(value = "productId")
  	private String productId;

}
