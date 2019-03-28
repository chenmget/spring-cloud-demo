package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BrandGetResp implements Serializable{

	private static final long serialVersionUID = 1L;

    
    @ApiModelProperty(value = "品牌ID")
    private String brandId;

	@ApiModelProperty(value = "品牌名称")
    private String name;
	
	@ApiModelProperty(value = "品牌编码")
	private String brandCode;
	
	@ApiModelProperty(value = "品牌详细描述")
	private String brandDesc;

	/**
	 * 外部品牌编码
	 */
	@ApiModelProperty(value = "外部品牌编码")
	private String brandCodeOuter;

	/**
	 * isDeleted
	 */
	@ApiModelProperty(value = "isDeleted")
	private String isDeleted;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String createStaff;

	/**
	 * createDate
	 */
	@ApiModelProperty(value = "createDate")
	private java.util.Date createDate;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人")
	private String updateStaff;

	/**
	 * updateDate
	 */
	@ApiModelProperty(value = "updateDate")
	private java.util.Date updateDate;
}
