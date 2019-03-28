package com.iwhalecloud.retail.web.controller.b2b.goods.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrandAddReqDTO implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "品牌名称")
    private String name;
	
	@ApiModelProperty(value = "品牌详细描述")
    private String brandDesc;
	
	@ApiModelProperty(value = "品牌编码")
	@NotBlank(message = "品牌编码不能为空")
    private String brandCode;

	@ApiModelProperty(value = "外部品牌编码")
	private String brandCodeOuter;

	@ApiModelProperty(value = "品牌图片")
	@NotBlank(message = "品牌图片不能为空")
	private String fileUrl;

	@ApiModelProperty(value = "归属厂家ID")
	private String manufacturerId;

	@ApiModelProperty(value = "归属厂家名称")
	private String manufacturerName;
}
