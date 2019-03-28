package com.iwhalecloud.retail.partner.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 供应商供货关系信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型PAR_SUPPLY_REL, 对应实体SupplyRel类")
public class SupplyRelDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * REL_ID
  	 */
	@ApiModelProperty(value = "REL_ID")
  	private java.lang.String relId;
	
	/**
  	 * SUPPLIER_ID
  	 */
	@ApiModelProperty(value = "SUPPLIER_ID")
  	private java.lang.String supplierId;
	
	/**
  	 * PARTNER_ID
  	 */
	@ApiModelProperty(value = "PARTNER_ID")
  	private java.lang.String partnerId;
	
}
