package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

@Data
public class ChangeMktResRegionReqDTO extends AbstractRequest implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * 变动列表
	 */
	@NotEmpty
	@ApiModelProperty(value = "变动列表")
	private java.util.List mktResRegionList;
  	
  	/**
  	 * 优惠券标识
  	 */
	@ApiModelProperty(value = "优惠券标识")
  	private String mktResId;
  	
  	/**
  	 * 适用区域标识
  	 */
	@ApiModelProperty(value = "适用区域标识")
  	private Long applyRegionId;
  	
  	
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
