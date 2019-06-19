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
public class ResourceNbrUploadTempListResp implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "串码")
	private String mktResInstNbr;

	@ApiModelProperty(value = "营销资源导入批次。")
	private String mktResUploadBatch;

	@ApiModelProperty(value = "申请单号")
	private String reqCode;

	@ApiModelProperty(value = "产品类型")
	private String typeName;

	@ApiModelProperty(value = "品牌名称")
	private String brandName;

	@ApiModelProperty(value = "机型名称")
	private String unitName;

	@ApiModelProperty(value = "验证结果 1. 通过; 0. 不通过")
	private String result;

	/**
	 * 验证描述，记录出错的原因
	 */
	@ApiModelProperty(value = "验证描述，记录出错的原因")
	private String resultDesc;

}
