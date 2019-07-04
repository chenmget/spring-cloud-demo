package com.iwhalecloud.retail.order2b.dto.response.purapply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PurApplyItemAndProductBaseInfoResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 申请单项ID
	 */
	@ApiModelProperty(value = "applyItemId")
	private String applyItemId;

	/**
	 * 申请单ID
	 */
	@ApiModelProperty(value = "applyId")
	private String applyId;

	/**
	 * 产品ID
	 */
	@ApiModelProperty(value = "productId")
	private String productId;

	/**
	 * 采购数量
	 */
	@ApiModelProperty(value = "purNum")
	private String purNum;

	/**
	 * 采购价格
	 */
	@ApiModelProperty(value = "purPrice")
	private String purPrice;

	/**
	 * 采购类型
	 */
	@ApiModelProperty(value = "purType")
	private String purType;

	/**
	 * 初始采购类型
	 */
	@ApiModelProperty(value = "oringinPurType")
	private String oringinPurType;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "statusCd")
	private String statusCd;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "createStaff")
	private String createStaff;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "createDate")
	private String createDate;

	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "updateStaff")
	private String updateStaff;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "updateDate")
	private String updateDate;

	/**
	 * 状态时间
	 */
	@ApiModelProperty(value = "statusDate")
	private String statusDate;

	//产品信息

	//产品信息字段

	private  String productName; // 产品名称
	private  String productBaseId; // 产品基础Id


	private  String corporationPrice;// 政企价格
	private  String sn;// 产品25位编码


	private String unitType;//产品型号
	private String unitTypeName; //型号名称

	private String brandName;// 品牌名称

	private String color; //颜色
	private  String memory;//内存

	private  String attrValue1;//容量-规格1 字段




}
