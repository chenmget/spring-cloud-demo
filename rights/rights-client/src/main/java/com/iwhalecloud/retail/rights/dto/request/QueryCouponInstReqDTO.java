package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 优惠券实例查询
 * @author he.sw
 * @date 2018.11.14
 */

@Data
@ApiModel(value = "优惠券实例查询请求参数对象")
public class QueryCouponInstReqDTO extends AbstractRequest implements Serializable{

    
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "客户编号")
    private String custNum;
	
	@ApiModelProperty(value = "状态")
  	private java.lang.String statusCd;

}