package com.iwhalecloud.retail.goods2b.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Goods
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("prod_goods")
@ApiModel(value = "对应模型prod_goods, 对应实体Goods类")
@KeySequence(value="seq_prod_goods_id",clazz = String.class)
public class Goods implements Serializable {
    /**表名常量*/
    public static final String TNAME = "prod_goods";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 商品ID
  	 */
	@TableId
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
	 * 支付方式
	 */
	@ApiModelProperty(value = "支付方式")
	private String payments;

	/**
	 * 是否分货
	 */
	@ApiModelProperty(value = "是否分货")
	private Integer isAllot;

	/**
	 * 是否预售商品
	 */
	@ApiModelProperty(value = "是否预售商品,商品是否为预售商品，预售商品可以无库存发布\n" +
			"1.是 0.否")
	private Integer isAdvanceSale;

	/**
	 * 来源平台
	 */
	@ApiModelProperty(value = "来源平台")
	private String sourceFrom;

	/**
	 * 商品发布对象
	 */
	@ApiModelProperty(value = "商品发布对象")
	private String targetType;

	/**
	 * 商家类型
	 */
	@ApiModelProperty(value = "商家类型")
	private String merchantType;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** 商品ID */
        goodsId,
        /** 商品名称 */
        goodsName,
        /** 商品编码 */
        sn,
		/** 类型ID */
		typeId,
        /** 品牌ID */
        brandId,
        /** 分类ID */
        goodsCatId,
        /** 上下架状态 */
        marketEnable,
        /** 查看次数 */
        viewCount,
        /** 购买次数 */
        buyCount,
        /** 删除标识 */
        isDeleted,
        /** 供货商ID */
        supplierId,
        /** 审批状态 */
        auditState,
        /** 搜索关键字 */
        searchKey,
        /** 卖点 */
        sellingPoint,
        /** 生效日期 */
        effDate,
        /** 失效日期 */
        expDate,
        /** 创建人 */
        createStaff,
        /** 创建时间 */
        createDate,
        /** 修改人 */
        updateStaff,
        /** 修改时间 */
        updateDate,
		/** 是否商家确认 */
		isMerchantConfirm,
		/** 支付方式 */
		payments,
		/** 是否分货 */
		isAllot,
		/** 来源平台 */
		sourceFrom,
		/** 商品发布对象 */
		targetType,
		/** 商家类型 */
		merchantType
    }

	

}
