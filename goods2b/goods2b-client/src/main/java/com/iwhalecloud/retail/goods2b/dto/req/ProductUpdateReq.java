package com.iwhalecloud.retail.goods2b.dto.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

/**
 * ProdProduct
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_product, 对应实体ProdProduct类")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductUpdateReq extends AbstractRequest implements Serializable {
  	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "productId")
	@NotBlank(message = "ID不能为空")
	private String productId;

	/**
	 * 地包供货价下限
	 */
	@ApiModelProperty(value = "地包供货价下限")
	private Long localSupplyFeeLower;

	/**
	 * 地包供货价上限
	 */
	@ApiModelProperty(value = "地包供货价上限")
	private Long localSupplyFeeUpper;

	/**
	 * 非地包供货价下限
	 */
	@ApiModelProperty(value = "非地包供货价下限")
	private Long supplyFeeLower;

	/**
	 * 非地包供货价上限
	 */
	@ApiModelProperty(value = "非地包供货价上限")
	private Long supplyFeeUpper;

	/**
	 * 产品编码
	 */
	@ApiModelProperty(value = "产品编码")
	private String sn;

	/**
	 * 产品名称
	 */
	@ApiModelProperty(value = "产品名称")
	private String unitName;

	/**
	 * 销售价
	 */
	@ApiModelProperty(value = "销售价")
	private Double cost;

	/**
	 * sku
	 */
	@ApiModelProperty(value = "sku")
	private String sku;

	/**
	 * 颜色
	 */
	@ApiModelProperty(value = "颜色")
	private String color;

	/**
	 * 容量
	 */
	@ApiModelProperty(value = "容量")
	private String memory;

	/**
	 * 屏幕尺寸
	 */
	@ApiModelProperty(value = "屏幕尺寸")
	private String inch;

	/**
	 * 规格1
	 */
	@ApiModelProperty(value = "规格1")
	private String attrValue1;

	/**
	 * 规格2
	 */
	@ApiModelProperty(value = "规格2")
	private String attrValue2;

	/**
	 * 规格3
	 */
	@ApiModelProperty(value = "规格3")
	private String attrValue3;

	/**
	 * 规格4
	 */
	@ApiModelProperty(value = "规格4")
	private String attrValue4;

	/**
	 * 规格5
	 */
	@ApiModelProperty(value = "规格5")
	private String attrValue5;

	/**
	 * 规格6
	 */
	@ApiModelProperty(value = "规格6")
	private String attrValue6;

	/**
	 * 规格7
	 */
	@ApiModelProperty(value = "规格7")
	private String attrValue7;

	/**
	 * 规格8
	 */
	@ApiModelProperty(value = "规格8")
	private String attrValue8;

	/**
	 * 规格9
	 */
	@ApiModelProperty(value = "规格9")
	private String attrValue9;

	/**
	 * 规格10
	 */
	@ApiModelProperty(value = "规格10")
	private String attrValue10;

	/**
	 * 状态:01 待提交，02审核中，03 已挂网，04 已退市
	 */
	@ApiModelProperty(value = "状态:01 待提交，02审核中，03 已挂网，04 已退市")
	private String status;

	/**
	 * isDeleted
	 */
	@ApiModelProperty(value = "isDeleted")
	private String isDeleted;

	/**
	 * 归属厂家
	 */
	@ApiModelProperty(value = "归属厂家")
	private String manufacturerId;

	/**
	 * updateDate
	 */
	@ApiModelProperty(value = "updateDate")
	private java.util.Date updateDate;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人")
	private String updateStaff;

//	/**
//	 * 来源
//	 */
//	@ApiModelProperty(value = "来源")
//	private String sourceFrom;

	/**
	 * 产品图片
	 */
	@ApiModelProperty(value = "产品图片")
	private List<FileAddReq> fileAddReqs;

	@ApiModelProperty(value = "审核状态")
	private String auditState;


}
