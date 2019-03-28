package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
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
@ApiModel(value = "对应模型prod_tag_tel, 对应实体ProdTagTel类")
public class TagTelBatchAddReq extends AbstractRequest implements Serializable {

  	private static final long serialVersionUID = -6726369119748942436L;
  
  	/**
  	 * tagList
  	 */
	@ApiModelProperty(value = "tagList")
  	private List<String> tagList;

  	/**
  	 * productId
  	 */
	@ApiModelProperty(value = "productId")
  	private String productId;
  	
}
