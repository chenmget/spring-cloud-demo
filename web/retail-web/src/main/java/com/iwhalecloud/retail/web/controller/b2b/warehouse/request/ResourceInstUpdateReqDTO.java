package com.iwhalecloud.retail.web.controller.b2b.warehouse.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_inst, 对应实体ResourceInst类")
public class ResourceInstUpdateReqDTO implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 记录营销资源实例编码。
	 */
	@NotEmpty(message = "串码不能为空")
	@ApiModelProperty(value = "记录营销资源实例编码。")
	private List<String> mktResInstNbrs;

	/**
	 * 商家Id
	 */
	@ApiModelProperty(value = "商家Id。")
	private String merchantId;

	/**
	 * 串码、产品类型键值对
	 */
	@ApiModelProperty(value = "串码、产品类型键值对")
	private Map<String, String> nbrAndTypeId;

}
