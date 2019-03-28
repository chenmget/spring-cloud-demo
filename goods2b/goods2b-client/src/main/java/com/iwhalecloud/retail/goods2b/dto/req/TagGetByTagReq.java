package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
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
@ApiModel(value = "对应模型prod_tag_rel, 对应实体ProdTagRel类")
public class TagGetByTagReq extends AbstractRequest implements Serializable {

  	private static final long serialVersionUID = -832849759986625344L;

  	/**
  	 * tagType
  	 */
	@ApiModelProperty(value = "tagType")
  	private String tagType;

	/**
	 * tagName
	 */
	@ApiModelProperty(value = "tagName")
	private String tagName;

}
