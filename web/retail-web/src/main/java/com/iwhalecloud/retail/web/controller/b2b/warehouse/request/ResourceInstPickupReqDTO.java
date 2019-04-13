package com.iwhalecloud.retail.web.controller.b2b.warehouse.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "串码领用")
public class ResourceInstPickupReqDTO implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 串码集合。
	 */
	@ApiModelProperty(value = "串码集合")
	@NotEmpty(message = "串码不能为空")
	private List<String> mktResInstNbrs;
	/**
	 * lanId
	 */
	@NotBlank(message = "lanId不能为空")
	@ApiModelProperty(value = "lanId")
	private String lanId;

}
