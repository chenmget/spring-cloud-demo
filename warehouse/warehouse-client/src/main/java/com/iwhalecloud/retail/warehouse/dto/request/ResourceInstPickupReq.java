package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "串码领用")
public class ResourceInstPickupReq implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 串码集合。
	 */
	@ApiModelProperty(value = "串码集合")
	private List<String> mktResInstNbrs;

	/**
	 * 营销资源仓库标识
	 */
	@ApiModelProperty(value = "营销资源仓库标识")
	private String mktResStoreId;

	/**
	 * 记录本地网标识。
	 */
	@ApiModelProperty(value = "记录本地网标识。")
	private String lanId;

	/**
	 * 商家Id
	 */
	@ApiModelProperty(value = "商家Id")
	private String merchantId;

	/**
	 * 更新人
	 */
	@ApiModelProperty(value = "更新人")
	private String updateStaff;
	
	/**
	 * 政企为1，集采为2
	 */
	@ApiModelProperty(value = "是政企还是集采串码")
	private String isGovOrJC;//政企为1，集采为2

}
