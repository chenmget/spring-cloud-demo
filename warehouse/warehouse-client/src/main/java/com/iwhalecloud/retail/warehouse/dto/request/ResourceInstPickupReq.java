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
	 * 记录营销资源实例编码。
	 */
	@ApiModelProperty(value = "串码主键集合")
	private List<String> mktResInstIds;

	/**
	 * 串码集合。
	 */
	@ApiModelProperty(value = "串码集合")
	private List<String> mktResInstNbrs;

	/**
	 * 营销资源标识，记录product_id
	 */
	@ApiModelProperty(value = "营销资源标识，记录product_id")
	private String mktResId;

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
	 * 指向公共管理区域标识
	 */
	@ApiModelProperty(value = "指向公共管理区域标识")
	private String regionId;

	/**
	 * 营销资源实例的销售价格
	 */
	@ApiModelProperty(value = "营销资源实例的销售价格")
	private java.lang.Double costPrice;

	/**
	 * 商家Id
	 */
	@ApiModelProperty(value = "商家Id")
	private String merchantId;

	/**
	 * 固网供货商id
	 */
	@ApiModelProperty(value = "固网供货商id")
	private String supplycode;

	/**
	 * 供货商名称
	 */
	@ApiModelProperty(value = "供货商名称")
	private String supplyname;

	/**
	 * 更新人
	 */
	@ApiModelProperty(value = "更新人")
	private String updateStaff;

}
