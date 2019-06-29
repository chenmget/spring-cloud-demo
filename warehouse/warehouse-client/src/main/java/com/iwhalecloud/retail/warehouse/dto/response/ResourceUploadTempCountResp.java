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
public class ResourceUploadTempCountResp implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 上传成功条数
	 */
	@ApiModelProperty(value = "上传成功条数")
	private Integer passNum;

	/**
	 * 上传失败条数
	 */
	@ApiModelProperty(value = "上传失败条数")
	private Integer failNum;

	@ApiModelProperty(value = "营销资源导入批次。")
	private String mktResUploadBatch;

}
