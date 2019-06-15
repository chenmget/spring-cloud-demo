package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Goods
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_goods, 对应实体Goods类")
public class GoodsDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 商品ID
  	 */
	@ApiModelProperty(value = "商品ID")
  	private String goodsId;
	
	/**
  	 * 商品名称
  	 */
	@ApiModelProperty(value = "商品名称")
  	private String goodsName;
	
	/**
  	 * 商品编码
  	 */
	@ApiModelProperty(value = "商品编码")
  	private String sn;

	/**
	 * 类型Id
	 */
	@ApiModelProperty(value = "类型Id")
	private String typeId;
	
	/**
  	 * 品牌ID
  	 */
	@ApiModelProperty(value = "品牌ID")
  	private String brandId;
	
	/**
  	 * 分类ID
  	 */
	@ApiModelProperty(value = "分类ID")
  	private String goodsCatId;
	
	/**
  	 * 上下架状态
  	 */
	@ApiModelProperty(value = "上下架状态")
  	private Integer marketEnable;
	
	/**
  	 * 查看次数
  	 */
	@ApiModelProperty(value = "查看次数")
  	private Long viewCount;
	
	/**
  	 * 购买次数
  	 */
	@ApiModelProperty(value = "购买次数")
  	private Long buyCount;

	/**
	 * 市场价
	 */
	@ApiModelProperty(value = "市场价")
	private Double mktprice;
	
	/**
  	 * 删除标识
  	 */
	@ApiModelProperty(value = "删除标识")
  	private Integer isDeleted;
	
	/**
  	 * 供货商ID
  	 */
	@ApiModelProperty(value = "供货商ID")
  	private String supplierId;
	
	/**
  	 * 审批状态
  	 */
	@ApiModelProperty(value = "审批状态")
  	private String auditState;
	
	/**
  	 * 搜索关键字
  	 */
	@ApiModelProperty(value = "搜索关键字")
  	private String searchKey;
	
	/**
  	 * 卖点
  	 */
	@ApiModelProperty(value = "卖点")
  	private String sellingPoint;

	/**
	 * 卖点
	 */
//	@ApiModelProperty(value = "卖点")
//	private String sallingPoint;
	
	/**
  	 * 生效日期
  	 */
	@ApiModelProperty(value = "生效日期")
  	private java.util.Date effDate;
	
	/**
  	 * 失效日期
  	 */
	@ApiModelProperty(value = "失效日期")
  	private java.util.Date expDate;
	
	/**
  	 * 创建人
  	 */
	@ApiModelProperty(value = "创建人")
  	private String createStaff;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createDate;
	
	/**
  	 * 修改人
  	 */
	@ApiModelProperty(value = "修改人")
  	private String updateStaff;
	
	/**
  	 * 修改时间
  	 */
	@ApiModelProperty(value = "修改时间")
  	private java.util.Date updateDate;

	/**
	 * 是否商家确认
	 */
	@ApiModelProperty(value = "是否商家确认")
	private Integer isMerchantConfirm;

	/**
	 * 是否预售商品
	 */
	@ApiModelProperty(value = "是否预售商品,商品是否为预售商品，预售商品可以无库存发布\n" +
			"1.是 0.否")
	private Integer isAdvanceSale;

	/**
	 * 是否预售商品
	 */
	@ApiModelProperty(value = "是否前置补贴商品 1.是 0.否")
	private Integer isSubsidy;

	/**
	 * 商家类型
	 */
	@ApiModelProperty(value = "商家类型")
	private String merchantType;

	/**
	 * 支付方式
	 */
	@ApiModelProperty(value = "支付方式")
	private String payments;
}
