package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "调拨批量查询参数")
public class ResourceInstBatchReq implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;
	/**
  	 * 串码
  	 */
	@ApiModelProperty(value = "串码")
  	private List<String> mktResInstNbrs;

	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
	@NotBlank(message = "营销资源仓库标识不能为空")
  	private String mktResStoreId;

	/**
	 * 在调用接口是设置，兼容多个地方调用
	 */
	@ApiModelProperty(value = "实列状态")
	private java.lang.String statusCd;

	/**
	 * 产品编码
	 */
	@ApiModelProperty(value = "产品编码")
	private List<String> mktResIdList;
}
