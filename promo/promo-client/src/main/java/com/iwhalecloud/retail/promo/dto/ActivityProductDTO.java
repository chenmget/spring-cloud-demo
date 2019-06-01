package com.iwhalecloud.retail.promo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ActivityProduct
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型act_activity_product, 对应实体ActivityProduct类")
public class ActivityProductDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * ID
  	 */
	@ApiModelProperty(value = "ID")
  	private String id;
	
	/**
  	 * 营销活动code
  	 */
	@ApiModelProperty(value = "营销活动Id")
  	private String marketingActivityId;
	
	/**
  	 * 产品基础表
  	 */
	@ApiModelProperty(value = "产品基础表")
  	private String productBaseId;
	
	/**
  	 * 产品ID
  	 */
	@ApiModelProperty(value = "产品ID")
  	private String productId;
	
	/**
  	 * 	活动中的商品价格 前置补贴活动保存产品的地包强制供货价 限时抢购活动保存产品的抢购价
  	 */
	@ApiModelProperty(value = "活动中的商品价格 前置补贴活动保存产品的地包强制供货价 限时抢购活动保存产品的抢购价")
  	private Long price;
	
	/**
  	 * 预付价格
  	 */
	@ApiModelProperty(value = "预付价格")
  	private Long prePrice;
	
	/**
  	 * 返利金额
  	 */
	@ApiModelProperty(value = "返利金额")
  	private Long rebatePrice;
	
	/**
  	 * 优惠方式为直减时，保存减免的金额
  	 */
	@ApiModelProperty(value = "优惠方式为直减时，保存减免的金额")
  	private Long discountAmount;
	
	/**
  	 * 数量
  	 */
	@ApiModelProperty(value = "数量")
  	private Long num;
	
	/**
  	 * 达量
  	 */
	@ApiModelProperty(value = "达量")
  	private Long reachAmount;
	
//	/**
//  	 * 支付方式
//  	 */
//	@ApiModelProperty(value = "支付方式")
//  	private java.lang.String payType;
	
	/**
  	 * 供应商编码
  	 */
	@ApiModelProperty(value = "供应商编码")
  	private String supplierCode;

//	/**
//  	 * 记录首次创建的员工标识。
//  	 */
//	@ApiModelProperty(value = "记录首次创建的员工标识。")
//  	private java.lang.String createStaff;
//
//	/**
//  	 * 记录首次创建的时间。
//  	 */
//	@ApiModelProperty(value = "记录首次创建的时间。")
//  	private java.util.Date createDate;
//
//	/**
//  	 * 记录每次修改的员工标识。
//  	 */
//	@ApiModelProperty(value = "记录每次修改的员工标识。")
//  	private java.lang.String updateStaff;
//
//	/**
//  	 * 记录每次修改的时间。
//  	 */
//	@ApiModelProperty(value = "记录每次修改的时间。")
//  	private java.util.Date updateDate;
	/**
	 * 记录首次创建的员工标识。
	 */
	@ApiModelProperty(value = "创建人。")
	private String creator;

	/**
	 * 记录首次创建的时间。
	 */
	@ApiModelProperty(value = "创建时间。")
	private java.util.Date gmtCreate;

	/**
	 * 记录每次修改的员工标识。
	 */
	@ApiModelProperty(value = "修改人。")
	private String modifier;

	/**
	 * 记录每次修改的时间。
	 */
	@ApiModelProperty(value = "修改时间。")
	private java.util.Date gmtModified;
	/**
	 * 是否删除：0未删、1删除。
	 */
	@ApiModelProperty(value = "是否删除：0未删、1删除。")
	private String isDeleted;
	/**
	 * 产品名称
	 */
	@ApiModelProperty(value = "产品名称")
	private String unitName;
	/**
	 * 产品编码
	 */
	@ApiModelProperty(value = "产品编码")
	private String sn;

	/**
	 * 是否限制产品参与总数量
	 1.限制
	 0.不限制
	 */
	@ApiModelProperty(value = "产品参与总数量限制标识")
	private String numLimitFlg;
	
	/**
	 * 产品类型
	 */
	@ApiModelProperty(value = "产品类型")
	private String typeName;
	
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
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称")
    private String brandName;
    
    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    private String unitTypeName;
    
    /**
     * productName
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;
}
