package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * PartnerShop
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "厅店状态修改")
public class PartnerShopStateUpdateReq implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin

	/**
	 * 厅店id
	 */
	@NotEmpty(message = "厅店ID不能为空")
	@ApiModelProperty(value = "厅店id")
	private java.lang.String partnerShopId;

	/**
  	 * 0申请 1-营业 2-停业
  	 */
	@NotEmpty(message = "厅店状态不能为空")
	@ApiModelProperty(value = "厅店状态：0-申请 1-营业 2-停业")
  	private java.lang.String state;
}
