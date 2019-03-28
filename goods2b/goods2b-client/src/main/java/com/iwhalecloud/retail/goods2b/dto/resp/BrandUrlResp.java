package com.iwhalecloud.retail.goods2b.dto.resp;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BrandUrlResp extends PageVO{

	private static final long serialVersionUID = 1L;

    
    @ApiModelProperty(value = "品牌ID")
    private String brandId;

	@ApiModelProperty(value = "品牌名称")
    private String name;
	
	@ApiModelProperty(value = "品牌图片")
	private String fileUrl;

	@ApiModelProperty(value = "品牌编码")
	private String brandCode;

	@ApiModelProperty(value = "品牌描述")
	private String brandDesc;

	@ApiModelProperty(value = "外部品牌编码")
	private String brandCodeOuter;

	@ApiModelProperty(value = "创建人")
	private String createStaff;

	@ApiModelProperty(value = "归属厂家ID")
	private String manufacturerId;

	@ApiModelProperty(value = "归属厂家名称")
	private String manufacturerName;

}
