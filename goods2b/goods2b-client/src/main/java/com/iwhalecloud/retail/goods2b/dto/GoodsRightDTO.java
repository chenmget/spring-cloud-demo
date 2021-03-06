package com.iwhalecloud.retail.goods2b.dto;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * GoodsRight
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ES_GOODS_RIGHTS, 对应实体GoodsRight类")
public class GoodsRightDTO extends AbstractRequest implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * goodsRightsId
  	 */
	@ApiModelProperty(value = "goodsRightsId")
  	private java.lang.String goodsRightsId;
	
	/**
  	 * goodsId
  	 */
	@ApiModelProperty(value = "goodsId")
  	private java.lang.String goodsId;
	
	/**
  	 * rightsId
  	 */
	@ApiModelProperty(value = "rightsId")
  	private java.lang.String rightsId;
	
	/**
  	 * rightsName
  	 */
	@ApiModelProperty(value = "rightsName")
  	private java.lang.String rightsName;
	
	/**
  	 * status
  	 */
	@ApiModelProperty(value = "status")
  	private java.lang.String status;
	
  	
}
