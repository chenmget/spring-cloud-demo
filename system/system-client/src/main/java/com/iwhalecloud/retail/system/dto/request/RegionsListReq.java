package com.iwhalecloud.retail.system.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

@Data
public class RegionsListReq implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "查询方式")
	private List<String> regionGrades;
	
	@ApiModelProperty(value = "父级编号")
	@NotBlank(message = "regionParentId不能为空")
	private String regionParentId;
	

}
