package com.iwhalecloud.retail.rights.dto.response;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryCouponInstRespDTO extends PageVO {
	
	
	private static final long serialVersionUID = 1L;

	/**
  	 * 优惠券实例标识
  	 */
	@ApiModelProperty(value = "优惠券实例标识")
  	private java.lang.String couponInstId;
  	
	/**
	 * 优惠券实名称 （mkt_res_coupon 表字段）
	 */
	@ApiModelProperty(value = "优惠券实名称")
	private java.lang.String mktResName;
	
  	/**
  	 * 优惠券实例编码
  	 */
	@ApiModelProperty(value = "优惠券实例编码")
  	private java.lang.String couponInstNbr;
  	
  	/**
  	 * 优惠券标识
  	 */
	@ApiModelProperty(value = "优惠券标识")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 记录优惠券使用后最终的优惠额度信息
  	 */
	@ApiModelProperty(value = "记录优惠券使用后最终的优惠额度信息")
  	private java.lang.Long couponAmount;
  	
  	/**
  	 * 优惠券赠送的客户统一账号
  	 */
	@ApiModelProperty(value = "优惠券赠送的客户统一账号")
  	private java.lang.String custAcctId;
  	
  	/**
  	 * 生效时间
  	 */
	@ApiModelProperty(value = "生效时间")
  	private java.util.Date effDate;
  	
  	/**
  	 * 失效时间
  	 */
	@ApiModelProperty(value = "失效时间")
  	private java.util.Date expDate;
  	
  	/**
  	 * 状态LOVB=RES-C-0046
未使用、已使用、已过期
  	 */
	@ApiModelProperty(value = "状态LOVB=RES-C-0046未使用、已使用、已过期")
  	private java.lang.String statusCd;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
	
	/**
  	 * 每次修改的时间
  	 */
	@ApiModelProperty(value = "每次修改的时间")
  	private java.util.Date updateDate;
}
