package com.iwhalecloud.retail.partner.dto;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 供应商供货产品信息
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型PAR_SUPPLY_PRODUCT_REL, 对应实体SupplyProduct类")
public class SupplyProductRelDTO extends PageVO {

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
  	 * PRODUCT_ID
  	 */
	@ApiModelProperty(value = "PRODUCT_ID")
  	private java.lang.String productId;

	/**
  	 * SUPPLIER_PRICE
  	 */
	@ApiModelProperty(value = "SUPPLIER_PRICE")
  	private java.lang.String supplierPrice;

}
