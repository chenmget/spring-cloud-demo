package com.iwhalecloud.retail.rights.dto.request;

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
@ApiModel(value = "通用查询对象")
public class CommonQueryByMktResIdReqDTO extends PageVO {

    
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "优惠券编号")
    private String mktResId;

}