package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ProdTagRel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "查询ProdTagRel类集合请求参数")
public class TagRelListReq implements Serializable {

	private static final long serialVersionUID = 7754952843932505795L;

  	/**
  	 * tagId
  	 */
	@ApiModelProperty(value = "tagId")
  	private String tagId;
  	
  	/**
  	 * productBaseId
  	 */
	@ApiModelProperty(value = "productBaseId")
  	private String productBaseId;

  	/**
  	 * productId
  	 */
	@ApiModelProperty(value = "productId")
  	private String productId;

	/**
	 * 关联ID
	 */
	@ApiModelProperty(value = "权限对应关联ID")
	private List<String> relTagIdList;

}
