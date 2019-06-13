package com.iwhalecloud.retail.warehouse.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "串码对象")
public class ResourceInstCheckResp implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 串码
	 */
	@ApiModelProperty(value = "串码")
  	private String mktResInstNbr;

	/**
	 * 产品ID
	 */
	@ApiModelProperty(value = "产品ID")
  	private String mktResId;

}
