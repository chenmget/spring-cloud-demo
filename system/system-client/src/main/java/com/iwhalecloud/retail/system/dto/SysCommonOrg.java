package com.iwhalecloud.retail.system.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysCommonOrg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 地市
	 */
	@ApiModelProperty(value = "lanIdList")
	private List<String> lanIdList;
	
	
}
