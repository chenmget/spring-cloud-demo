package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateCouponSupplyRuleReqDTO extends AbstractRequest implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	
	/**
  	 * 优惠券领取规则标识，主键
  	 */
	@NotNull
	@ApiModelProperty(value = "优惠券领取规则标识，主键")
  	private Long supplyRuleId;
  	
  	/**
  	 * 优惠券标识 主键
  	 */
	@ApiModelProperty(value = "优惠券标识 主键")
  	private String mktResId;
  	
  	/**
  	 * 可领取优惠券的客户范围，可以是客户分群、或自定义的范围
  	 */
	@ApiModelProperty(value = "可领取优惠券的客户范围，可以是客户分群、或自定义的范围")
  	private String custRange;
  	
  	/**
  	 * 客户可领取的总次数
  	 */
	@ApiModelProperty(value = "客户可领取的总次数")
  	private Long maxNum;
  	
  	/**
  	 * 客户在一个周期内可领取的次数
  	 */
	@ApiModelProperty(value = "客户在一个周期内可领取的次数")
  	private Long supplyNum;
	
	/**
  	 * beginTime
  	 */
	@ApiModelProperty(value = "beginTime")
  	private java.util.Date beginTime;
  	
  	/**
  	 * endTime
  	 */
	@ApiModelProperty(value = "endTime")
  	private java.util.Date endTime;
  	
  	/**
  	 * 循环周期类型：月循环、周循环、不循环LOVB=RES-C-0052
  	 */
	@ApiModelProperty(value = "循环周期类型：月循环、周循环、不循环LOVB=RES-C-0052")
  	private String cycleType;
  	
  	/**
  	 * 领取规则描述
  	 */
	@ApiModelProperty(value = "领取规则描述")
  	private String supplyRuleDesc;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;
  	
	/**
  	 * 修改人
  	 */
	@NotNull
	@ApiModelProperty(value = "修改人")
  	private String updateStaff;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private java.util.Date updateDate;

	@ApiModelProperty(value = "是不限制券总数量")
	private String numLimitFlg;

	@ApiModelProperty(value = "是否限制券的单用户领取数量")
	private String supplyLimitFlg;
}
