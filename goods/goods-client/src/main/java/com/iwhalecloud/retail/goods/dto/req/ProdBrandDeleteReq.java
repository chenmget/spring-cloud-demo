package com.iwhalecloud.retail.goods.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class ProdBrandDeleteReq implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "品牌ID")
	@NotBlank(message = "品牌ID不能为空")
	String brandId;
	
}
