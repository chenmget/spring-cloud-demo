package com.iwhalecloud.retail.warehouse.dto.request;

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
public class RetreatStorageReq implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;


  	//属性 begin
	/**
	 * 记录营销资源申请单标识
	 */
	@ApiModelProperty(value = "记录营销资源申请单标识")
	private String mktResReqId;

}
