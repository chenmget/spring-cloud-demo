package com.iwhalecloud.retail.partner.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 *
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "可供代理商查询")
public class SupplierReq extends PageVO {


	@ApiModelProperty(value = "供应商ID")
	private String supplierId;


}
