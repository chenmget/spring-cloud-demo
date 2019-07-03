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
	 * 验证结果 1. 有异常; 0. 无异常
	 */
	@ApiModelProperty(value = "验证结果  1. 有异常; 0. 无异常")
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

	@ApiModelProperty(value = "记录营销资源申请单明细标识")
	private String mktResReqDetailId;

	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
	private String statusCd;

	@ApiModelProperty(value = "状态说明")
	private String remark;

	@ApiModelProperty(value = "产品ID")
	private String mktResId;

	@ApiModelProperty(value = "productName")
	private String productName;

	@ApiModelProperty(value = "产品类型名称")
	private String typeName;

	@ApiModelProperty(value = "产品型号")
	private String unitType;

	@ApiModelProperty(value = "采购类型")
	private String purchaseType;

	@ApiModelProperty(value = "规格1")
	private String attrValue1;

	@ApiModelProperty(value = "规格2")
	private String attrValue2;

	@ApiModelProperty(value = "规格3")
	private String attrValue3;

	@ApiModelProperty(value = "规格4")
	private String attrValue4;

	@ApiModelProperty(value = "规格5")
	private String attrValue5;

	@ApiModelProperty(value = "规格6")
	private String attrValue6;

	@ApiModelProperty(value = "规格7")
	private String attrValue7;

	@ApiModelProperty(value = "规格8")
	private String attrValue8;

	@ApiModelProperty(value = "规格9")
	private String attrValue9;

	@ApiModelProperty(value = "规格10")
	private String attrValue10;

	@ApiModelProperty(value = "typeId")
	private String typeId;

}
