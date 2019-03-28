package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.goods2b.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BrandGetReq extends PageVO {

	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "品牌ID")
    private String brandId;

	@ApiModelProperty(value = "品牌名称")
    private String brandName;

	@ApiModelProperty(value = "品牌编码")
    private String brandCode;

	@ApiModelProperty(value = "外部品牌编码")
	private String brandCodeOuter;

	@ApiModelProperty(value = "创建人")
	private String createStaff;

	@ApiModelProperty(value = "归属厂家ID")
	private String manufacturerId;

	@ApiModelProperty(value = "归属厂家名称")
	private String manufacturerName;

}
