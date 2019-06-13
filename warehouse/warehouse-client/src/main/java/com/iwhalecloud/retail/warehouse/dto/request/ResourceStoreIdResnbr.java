package com.iwhalecloud.retail.warehouse.dto.request;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResourceStoreIdResnbr implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 串码
	 */
	@ApiModelProperty(value = "串码")
	private String mktResInstNbr;
	
	/**
	 * 仓库ID
	 */
	@ApiModelProperty(value = "仓库ID")
	private String mktResStoreId;
	
}
