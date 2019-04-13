package com.iwhalecloud.retail.warehouse.dto.request;

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
@ApiModel(value = "串码入库校验类")
public class ResourceInstValidReq implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
	@NotEmpty(message = "串码不能为空")
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
	@NotBlank(message = "仓库不能为空")
  	private String mktResStoreId;

	/**
	 * 产品类型
	 */
	@ApiModelProperty(value = "产品类型")
	private String typeId;

	/**
	 * 校验商家类型
	 */
	@ApiModelProperty(value = "校验商家类型")
	private String merchantType;

}
