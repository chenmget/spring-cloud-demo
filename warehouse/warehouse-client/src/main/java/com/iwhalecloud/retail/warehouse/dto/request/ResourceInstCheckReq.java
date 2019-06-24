package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "串码校验类")
public class ResourceInstCheckReq implements Serializable {


	private static final long serialVersionUID = -2024838499123412492L;
	/**
	 * 商家ID
	 */
	@ApiModelProperty(value = "商家ID")
	private String merchantId;

	/**
	 * 校验状态
	 */
	@ApiModelProperty(value = "状态")
	@NotNull
	private String checkStatusCd;

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
	private List<String> mktResInstNbrs;

	@ApiModelProperty(value = "记录营销资源申请单明细标识")
	private List<String> mktResReqDetailIds;

	@ApiModelProperty(value = "审核说明")
	private String remark;

	@ApiModelProperty(value = "审核对象id")
	private String updateStaff;

	@ApiModelProperty(value = "审核对象名字")
	private String updateStaffName;



}
