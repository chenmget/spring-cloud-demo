package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

@Data
public class RegionsGetReq implements Serializable{

	private static final long serialVersionUID = -3987009847937209690L;
	
	public static final String ID_COND_TYPE = "0";//标识
	public static final String NAME_COND_TYPE = "1";//名称
	
	@ApiModelProperty(value = "查询条件方式  查询方式：0【ID】、1【NAME】默认0")
	private String regionCondType = ID_COND_TYPE;
	
	@ApiModelProperty(value ="父级编号")
	private String regionParentId;
	
	@ApiModelProperty(value ="区域标识")
	private String regionId;
	
	@ApiModelProperty(value ="区域本地化名称")
	private String regionName;
	
	@ApiModelProperty(value ="等级")
	private String regionGrade;
	
}
