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
@ApiModel(value = "删除商家导入串码请求对象")
public class ResourceUploadTempDelReq implements Serializable{

  	private static final long serialVersionUID = 1L;

	/**
	 * 营销资源导入批次。
	 */
	@ApiModelProperty(value = "营销资源导入批次。")
	private String mktResUploadBatch;

	/**
  	 * 串码
  	 */
	@ApiModelProperty(value = "串码")
  	private List<String> mktResInstNbrList;
	/**
  	 * 状态
  	 */
	@ApiModelProperty(value = "状态")
  	private String result;

}
