package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryCouponInstProvRecNumReqDTO extends AbstractRequest implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * 营销资源标识
	 */
	@ApiModelProperty(value = "记录营销资源名称")
	private java.lang.String mktResId;
	
	/**
	 * 循环周期类型：月
	 */
	@ApiModelProperty(value = "循环周期类型：月")
	private java.lang.Boolean timePeriodMonth;
	
	/**
	 * 循环周期类型：周
	 */
	@ApiModelProperty(value = "循环周期类型：周")
	private java.lang.Boolean timePeriodWeek;
	
	/**
	 * 领取用户
	 */
	@ApiModelProperty(value = "领取用户")
	private java.lang.String custNum;
	
	
	
}
