package com.iwhalecloud.retail.web.controller.b2b.rights.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 优惠券实例查询
 * @author he.sw
 * @date 2018.11.14
 */

@Data
@ApiModel(value = "优惠券实例查询请求参数对象")
public class QueryCouponInstReq extends PageVO {

    
	private static final long serialVersionUID = 1L;
	
	
	@ApiModelProperty(value = "状态")
	private String statusCd;

}
