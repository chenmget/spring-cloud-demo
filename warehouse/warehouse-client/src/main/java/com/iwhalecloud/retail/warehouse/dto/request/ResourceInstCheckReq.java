package com.iwhalecloud.retail.warehouse.dto.request;

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
@ApiModel(value = "串码校验类")
public class ResourceInstCheckReq {

  	private static final long serialVersionUID = 1L;

	/**
	 * 商家ID
	 */
	@ApiModelProperty(value = "商家ID")
	private String merchantId;

	/**
	 * 校验状态
	 */
	@ApiModelProperty(value = "状态")
	private List<String> checkStatusCd;

	/**
	 * 仓库ID
	 */
	@ApiModelProperty(value = "仓库ID")
	private String mktResStoreId;

	/**
	 * 商家类型
	 */
	@ApiModelProperty(value = "商家类型")
	private List<String> merchantTypes;

	/**
	 * 记录营销资源实例编码。
	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
	private List<String> mktResInstNbr;

}
