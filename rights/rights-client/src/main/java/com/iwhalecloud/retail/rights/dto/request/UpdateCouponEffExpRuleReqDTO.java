package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateCouponEffExpRuleReqDTO extends AbstractRequest implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	
	/**
  	 * 优惠券生失效规则标识，唯一主键
  	 */
	@NotNull
	@ApiModelProperty(value = "优惠券生失效规则标识，唯一主键")
  	private Long effExpRuleId;
  	
  	/**
  	 * 优惠券领用后，可以使用的有效期时长
  	 */
	@ApiModelProperty(value = "优惠券领用后，可以使用的有效期时长")
  	private Long periodTime;
  	
  	/**
  	 * 有效期时间单位。LOVB=OFF-C-0036
  	 */
	@ApiModelProperty(value = "有效期时间单位。LOVB=OFF-C-0036")
  	private String periodUnit;
  	
  	/**
  	 * 优惠券领用后，可以使用的开始时间
  	 */
	@ApiModelProperty(value = "优惠券领用后，可以使用的开始时间")
  	private java.util.Date effDate;
  	
  	/**
  	 * 优惠券领用后，可以使用的截止时间
  	 */
	@ApiModelProperty(value = "优惠券领用后，可以使用的截止时间")
  	private java.util.Date expDate;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;
  	
	/**
	 * 记录首次创建的员工标识。
	 */
	@NotNull
	@ApiModelProperty(value = "记录首次创建的员工标识。")
	private String updateStaff;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private java.util.Date updateDate;
  	
}
