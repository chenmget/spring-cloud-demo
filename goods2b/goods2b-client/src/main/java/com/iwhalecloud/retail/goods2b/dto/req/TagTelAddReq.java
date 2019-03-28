package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * TagTel
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_tag_tel, 对应实体TagTel类")
public class TagTelAddReq extends AbstractRequest implements Serializable {
    /**表名常量*/
  	private static final long serialVersionUID = 6509069708315626834L;
  
  	/**
  	 * tagId
  	 */
	@ApiModelProperty(value = "tagId")
  	private String tagId;
  	
  	/**
  	 * productId
  	 */
	@ApiModelProperty(value = "productId")
  	private String productId;
  	
}
