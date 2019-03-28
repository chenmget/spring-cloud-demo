package com.iwhalecloud.retail.web.controller.b2b.warehouse.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "串码调拨")
public class ResourceInstAllocateReqDTO implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;


	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
	@NotEmpty(message = "串码不能为空")
  	private List<String> mktResInstIds;

	/**
  	 * 源仓库标识不能为空
  	 */
	@ApiModelProperty(value = "源仓库标识不能为空")
	@NotBlank(message = "源仓库标识不能为空")
  	private String mktResStoreId;
	/**
	 * 目标仓库标识不能为空
	 */
	@ApiModelProperty(value = "目标仓库标识不能为空")
	@NotBlank(message = "目标仓库标识不能为空")
	private String destStoreId;

	/**
	 * 营销资源标识，记录product_id
	 */
	@ApiModelProperty(value = "营销资源标识，记录product_id")
	private String mktResId;

	/**
	 * 串码集合。
	 */
	@ApiModelProperty(value = "串码集合。")
	private List<String> mktResInstNbrs;

	/**
	 * 本地网
	 */
	@ApiModelProperty(value = "本地网")
	private String lanId;

	/**
	 * 公共管理区域标识
	 */
	@ApiModelProperty(value = "公共管理区域标识")
	private String regionId;
}
