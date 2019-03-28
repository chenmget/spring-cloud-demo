package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 删除供应商供货关系信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "删除供应商供货关系信息")
public class SupplyRelDeleteReq implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "relIds")
	private List<String> relIds;

  	
}
