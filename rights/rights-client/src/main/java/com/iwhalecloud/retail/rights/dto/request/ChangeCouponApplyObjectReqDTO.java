package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

@Data
public class ChangeCouponApplyObjectReqDTO extends AbstractRequest implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * 变动列表ChangeMktResRegionReqDTO.java
	 */
	@NotEmpty
	@ApiModelProperty(value = "变动列表")
	private java.util.List couponApplyObjectList;
	
  	/**
  	 * 优惠券标识
  	 */
	@ApiModelProperty(value = "优惠券标识")
  	private String mktResId;
  	
  	/**
  	 * 适用对象类型LOVB=RES-C-0044
商品、销售品、积分等
  	 */
	@ApiModelProperty(value = "适用对象类型LOVB=RES-C-0044商品、销售品、积分等")
  	private Long objType;
  	
  	/**
  	 * 适用对象标识
  	 */
	@ApiModelProperty(value = "适用对象标识兑换关系")
  	private Long objId;
  	
  	/**
  	 * 变更动作。0新增 1删除
  	 */
	@ApiModelProperty(value = "变更动作。0新增 1删除")
  	private String changeAction;
  	
  	/**
  	 * 记录首次创建的员工标识。
  	 */
	@NotNull
	@ApiModelProperty(value = "记录首次创建的员工标识。")
  	private String createStaff;
  	
  	
}
