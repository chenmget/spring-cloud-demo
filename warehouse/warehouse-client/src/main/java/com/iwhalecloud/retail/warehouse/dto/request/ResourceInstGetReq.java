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
@ApiModel(value = "对应模型mkt_res_inst, 对应实体ResourceInst类")
public class ResourceInstGetReq {

  	private static final long serialVersionUID = 1L;

	/**
	 * 商家ID
	 */
	@ApiModelProperty(value = "商家ID")
	private String merchantId;

	/**
	 * 默认查询可用状态
	 */
	@ApiModelProperty(value = "状态")
	private String statusCd;

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
	private String mktResInstNbr;

}
