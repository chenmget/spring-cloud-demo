package com.iwhalecloud.retail.web.controller.b2b.warehouse.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@ApiModel(value = "对应模型mkt_res_inst, 对应实体ResourceInst类")
public class ResourceInstUpdateByIdReqDTO implements Serializable {

  	private static final long serialVersionUID = 1L;

	/**
	 * 记录营销资源实例编码。
	 */
	@NotEmpty(message = "串码主键不能为空")
	@ApiModelProperty(value = "记录营销资源实例编码。")
	private List<String> mktResInstIdList;

	/**
	 * 商家Id
	 */
	@ApiModelProperty(value = "商家Id")
	private String merchantId;

}
