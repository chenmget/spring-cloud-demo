package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class BrandUpdateReq extends AbstractRequest implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "品牌ID")
	@NotBlank(message = "ID不能为空")
    private String brandId;

	@ApiModelProperty(value = "品牌名称")
    private String name;
	
	@ApiModelProperty(value = "品牌详细描述")
    private String brandDesc;
	
	@ApiModelProperty(value = "品牌编码")
    private String brandCode;

	@ApiModelProperty(value = "外部品牌编码")
	private String brandCodeOuter;

	@ApiModelProperty(value = "修改人")
	private String updateStaff;

	@ApiModelProperty(value = "品牌图片")
	private String fileUrl;

	@ApiModelProperty(value = "归属厂家ID")
	private String manufacturerId;

	@ApiModelProperty(value = "归属厂家名称")
	private String manufacturerName;
}
