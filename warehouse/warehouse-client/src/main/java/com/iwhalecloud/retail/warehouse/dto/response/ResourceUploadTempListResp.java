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
@ApiModel(value = "对应模型mkt_res_inst, 对应实体ResourceInst类")
public class ResourceUploadTempListResp implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "营销资源导入流水号")
	private String mktResUploadSeq;

	/**
	 * 营销资源导入批次。
	 */
	@ApiModelProperty(value = "营销资源导入批次。")
	private String mktResUploadBatch;

	/**
	 * 营销资源实例编码
	 */
	@ApiModelProperty(value = "营销资源实例编码")
	private String mktResInstNbr;

	/**
	 * 导入时间
	 */
	@ApiModelProperty(value = "导入时间")
	private java.util.Date uploadDate;

	/**
	 * 验证结果 1. 通过; 0. 不通过
	 */
	@ApiModelProperty(value = "验证结果 1. 通过; 0. 不通过")
	private String result;

	/**
	 * 验证描述，记录出错的原因
	 */
	@ApiModelProperty(value = "验证描述，记录出错的原因")
	private String resultDesc;

	/**
	 * 记录首次创建的用户标识。
	 */
	@ApiModelProperty(value = "首次创建的用户")
	private String createStaff;

	/**
	 * 记录首次创建的时间。
	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
	private java.util.Date createDate;

}
