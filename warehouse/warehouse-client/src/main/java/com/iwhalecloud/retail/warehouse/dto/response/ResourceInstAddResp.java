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
@ApiModel(value = "串码入库返回对象")
public class ResourceInstAddResp implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 重复的串码
	 */
	@ApiModelProperty(value = "重复的串码")
  	private List<String> existNbrs;

	/**
	 * 入库失败的串码
	 */
	@ApiModelProperty(value = "入库失败的串码")
  	private List<String> putInFailNbrs;

}
