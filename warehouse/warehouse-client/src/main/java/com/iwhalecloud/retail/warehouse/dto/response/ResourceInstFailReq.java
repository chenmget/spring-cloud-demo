package com.iwhalecloud.retail.warehouse.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "串码入库返回对象")
public class ResourceInstFailReq implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 导入失败的串码
	 */
	@ApiModelProperty(value = "导入失败的串码")
  	private String mktResInstNbr;

	/**
	 * 导入状态
	 */
	@ApiModelProperty(value = "导入状态")
  	private String result;

	/**
	 * 状态描述
	 */
	@ApiModelProperty(value = "状态描述")
  	private String resultDesc;

}
