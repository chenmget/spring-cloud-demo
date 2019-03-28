package com.iwhalecloud.retail.goods.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class ProdBrandUpdateReq implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@ApiModelProperty(value = "品牌ID")
    private String brandId;

	@ApiModelProperty(value = "品牌名称")
    private String name;
	
	@ApiModelProperty(value = "品牌描述")
    private String brief;
	
	@ApiModelProperty(value = "品牌网站")
    private String url;
	
	@ApiModelProperty(value = "品牌编码")
    private String brandCode;
}
