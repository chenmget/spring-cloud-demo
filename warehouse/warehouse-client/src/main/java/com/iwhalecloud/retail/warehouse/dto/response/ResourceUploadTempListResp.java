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
	 * CT码
	 */
	@ApiModelProperty(value = "CT码")
	private String ctCode;

	/**
	 * SN码
	 */
	@ApiModelProperty(value = "SN码")
	private java.lang.String snCode;

	/**
	 * 网络终端（包含光猫、机顶盒、融合终端）记录MAC码
	 */
	@ApiModelProperty(value = "macCode")
	private java.lang.String macCode;

}
