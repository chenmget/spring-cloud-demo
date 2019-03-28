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
@ApiModel(value = "对应模型prod_tag_tel, 对应实体ProdTagTel类")
public class TagTelDeleteByProductIdReq extends AbstractRequest implements Serializable {

  	private static final long serialVersionUID = 4254747364311649561L;
  
  	/**
  	 * productId
  	 */
	@ApiModelProperty(value = "productId")
  	private String productId;

  	
}
