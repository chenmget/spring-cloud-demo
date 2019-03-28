package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryRightsReqDTO extends PageVO {
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * 记录营销资源名称
	 */
	@ApiModelProperty(value = "记录营销资源名称")
	private java.lang.String mktResName;
	
	/**
	 * 权益类别
	 */
	@ApiModelProperty(value = "权益类别")
	private java.lang.Long mktResTypeId;
	
	
	
}
