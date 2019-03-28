package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import com.iwhalecloud.retail.dto.PageVO;


/**
 * 厅店查询请求对象
 * @author Z
 *
 */

@Data
@ApiModel(value = "厅店分页查询")
public class PartnerShopQueryPageReq extends PageVO {
    
	private static final long serialVersionUID = -8899373161801134999L;
	
	/**
  	 * 厅店名称
  	 */
	@ApiModelProperty(value = "厅店名称",required = true)
  	private java.lang.String name;

	/**
	 * 厅店id
	 */
	@ApiModelProperty(value = "厅店id")
	private java.lang.String partnerShopId;
	
	/**
  	 * 厅店地址
  	 */
	@ApiModelProperty(value = "厅店地址") 
  	private java.lang.String address;
	
	/**
  	 * 厅店类型：1-旗舰店  2-社区店
  	 */
	@ApiModelProperty(value = "厅店类型：1-旗舰店  2-社区店",required = true)
  	private java.lang.String netType;
	
	/**
  	 * 0申请 1-营业 2-停业
  	 */
	@ApiModelProperty(value = "厅店状态：0申请 1-营业 2-停业")
  	private java.lang.String state;

//	@ApiModelProperty(value = "归属代理商")
//  	private java.lang.Long partnerId;

	/**
	 * 区域
	 */
	@ApiModelProperty(value = "区域")
	private String areaCode;
    
}

